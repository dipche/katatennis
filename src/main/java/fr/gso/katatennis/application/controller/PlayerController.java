package fr.gso.katatennis.application.controller;

import fr.gso.katatennis.application.request.CreatePlayerJsonCommand;
import fr.gso.katatennis.application.request.PlayerWinAGameCommand;
import fr.gso.katatennis.application.response.CreatePlayerJsonResponse;
import fr.gso.katatennis.application.response.MatchScoreDisplayJsonResponse;
import fr.gso.katatennis.domain.model.*;
import fr.gso.katatennis.domain.service.GameService;
import fr.gso.katatennis.domain.service.MatchService;
import fr.gso.katatennis.domain.service.PlayerService;
import fr.gso.katatennis.domain.service.TennisSetService;
import fr.gso.katatennis.repository.GameRepository;
import fr.gso.katatennis.repository.PlayerRepository;
import fr.gso.katatennis.repository.TennisSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private GameService gameService;

    @Autowired
    private TennisSetService tennisSetService;

    @Autowired
    private TennisSetRepository tennisSetRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @GetMapping(path = "/view/{name}")
    public Player viewPlayer(@PathVariable(value = "name") String name) {
        return playerService.findPlayerByName(name);
    }


    @PostMapping(path = "/add")
    public @ResponseBody
    CreatePlayerJsonResponse addNewPlayer(@RequestBody CreatePlayerJsonCommand createPlayerJsonCommand) {
        Player player = new Player(createPlayerJsonCommand.getPlayerName());
        Player playerCreated = playerService.registerPlayer(player);
        return new CreatePlayerJsonResponse(playerCreated.getId(), playerCreated.getName());
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    List<Player> getAllPlayers() {
        return (List<Player>) playerService.findAllPlayers();
    }


    @PostMapping(path = "/play/{matchId}")
    public @ResponseBody
    MatchScoreDisplayJsonResponse winAGame(@PathVariable(value = "matchId") Integer matchId,
                                           @RequestBody PlayerWinAGameCommand playerWinAGameCommand) {

        //TODO is match status in progress ?
        Match startedMatch = matchService.findMatch(matchId);

        if (startedMatch.getId() == null)
            throw new IllegalStateException("Unrecognised match id");

        if (startedMatch.getStatus() != MatchStatus.IN_PROGRESS)
            throw new IllegalStateException("Match status must be IN PROGRESS");

        List<Player> players = playerService.findMatchPlayers(matchId);

        if (Collections.emptyList().equals(players))
            throw new IllegalStateException("No players registered to match");

        boolean isPlayerRegisteredToMatch = players
                .stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(playerWinAGameCommand.getPlayerName()));

        if (!isPlayerRegisteredToMatch)
            throw new IllegalStateException("Player " + playerWinAGameCommand.getPlayerName() + " not registered to match id : " + startedMatch.getId());

        TennisSet currentSet = tennisSetRepository.findFirstByMatchIdOrderByNumberDesc(matchId);

        Game currentGame = gameRepository.findFirstByTennisSetNumberOrderByIdDesc(currentSet.getNumber());

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        boolean hasPlayer1WonTheGame = playerWinAGameCommand.getPlayerName().equalsIgnoreCase(player1.getName());
        boolean hasPlayer2WonTheGame = playerWinAGameCommand.getPlayerName().equalsIgnoreCase(player2.getName());

        GameStatus nextGameStatus = gameService.computeNextGameStatus(currentGame.getStatus(), player1, player2,
                hasPlayer1WonTheGame, hasPlayer2WonTheGame);

        List<Player> playersToUpdate = playerService.buildPlayersUpdate(currentGame.getStatus(), nextGameStatus, player1,
                player2, hasPlayer1WonTheGame, hasPlayer2WonTheGame);

        playerRepository.saveAll(playersToUpdate);

        TennisSet currentSetToUpdate = new TennisSet(currentSet.getId(), currentSet.getNumber(),
                currentSet.getMatchId(), playersToUpdate.get(0).getCurrentSetScore(), playersToUpdate.get(1).getCurrentSetScore());

        currentSetToUpdate = tennisSetRepository.save(currentSetToUpdate);

        List<Player> playersFromDB = playerService.findMatchPlayers(matchId);

        boolean isMatchFinished = isMatchFinished(playersFromDB);

        if (isMatchFinished){
            Optional<Player> theWinner = findThePlayerOfMatch(playersFromDB);
            Player winner = theWinner.get();
            Match matchToEnd = matchService.findMatch(matchId);
            if (winner.getId() % 2 == 0){
                matchToEnd = new Match(matchToEnd, MatchStatus.PLAYER_2_WINS);
            }else{
                matchToEnd = new Match(matchToEnd, MatchStatus.PLAYER_1_WINS);
            }
            matchService.updateMatch(matchToEnd);
        } else {
            Game gameToSave = null;
            if (isSetFinished(currentSetToUpdate)){
                int nextSetNum =  tennisSetService.computeMatchNextSetNumber(matchId);
                gameToSave = new Game(nextSetNum, player1.getCurrentGameScore(), player2.getCurrentGameScore(), nextGameStatus);
            } else{
                gameToSave = new Game(currentSetToUpdate.getNumber(), player1.getCurrentGameScore(), player2.getCurrentGameScore(), nextGameStatus);
            }
            gameRepository.save(gameToSave);
        }

        return matchService.displayMatchScore(matchId)
                .map(t -> new MatchScoreDisplayJsonResponse(t.getPlayer1Name(), t.getPlayer2Name(),
                        t.getScore(), t.getCurrentGameStatus(), t.getMatchStatus(), matchId))
                .orElse(null);

    }

    private boolean isSetFinished(TennisSet aTennisSet) {
        return ((aTennisSet.getPlayer1SetScore() == 6 || aTennisSet.getPlayer2SetScore() == 6)
                && Math.abs(aTennisSet.getPlayer1SetScore()-aTennisSet.getPlayer2SetScore()) >= 2);
    }

    private boolean isMatchFinished(List<Player> players) {
        return players.stream().anyMatch(p -> p.getNumbersOfWonSet() == 3);
    }

    private Optional<Player> findThePlayerOfMatch(List<Player> players){
        return players.stream().filter(p -> p.getNumbersOfWonSet() == 3).findFirst();
    }

}
