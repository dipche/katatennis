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

        TennisSet currentSet = tennisSetRepository.findByMatchIdOrderByNumberDesc(matchId).stream().findFirst().get();

        Game currentGame = gameRepository.findByTennisSetNumberOrderByIdDesc(currentSet.getNumber()).stream().findFirst().get();

        Player player1 = players.get(0);
        Player player2 = players.get(1);

        boolean hasPlayer1WonTheGame = playerWinAGameCommand.getPlayerName().equalsIgnoreCase(player1.getName());
        boolean hasPlayer2WonTheGame = playerWinAGameCommand.getPlayerName().equalsIgnoreCase(player2.getName());

        GameStatus currentGameStatus = currentGame.getStatus();

        GameStatus nextGameStatus = gameService.computeNextGameStatus(currentGameStatus, player1, player2,
                hasPlayer1WonTheGame, hasPlayer2WonTheGame);

        List<Player> playersToUpdate;

        if (nextGameStatus == GameStatus.TIE_BREAK) {
            Player player1ForTieBreak = new Player(player1.getId(), player1.getName(), player1.getMatchId(), 0,
                    player1.getCurrentSetScore(), player1.getNumbersOfWonSet(), player1.getHasGameAdvantage());

            Player player2ForTieBreak = new Player(player2.getId(), player2.getName(), player2.getMatchId(), 0,
                    player2.getCurrentSetScore(), player2.getNumbersOfWonSet(), player2.getHasGameAdvantage());

            playersToUpdate = playerService.buildPlayersUpdate(currentGameStatus, nextGameStatus, player1ForTieBreak,
                    player2ForTieBreak, hasPlayer1WonTheGame, hasPlayer2WonTheGame);
        } else {
            playersToUpdate = playerService.buildPlayersUpdate(currentGameStatus, nextGameStatus, player1,
                    player2, hasPlayer1WonTheGame, hasPlayer2WonTheGame);
        }

        if (null != playersToUpdate) {
            playerRepository.saveAll(playersToUpdate);
            TennisSet currentSetToUpdate = new TennisSet(currentSet.getId(), currentSet.getNumber(),
                    currentSet.getMatchId(), playersToUpdate.get(0).getCurrentSetScore(), playersToUpdate.get(1).getCurrentSetScore());
            tennisSetRepository.save(currentSetToUpdate);
        }

        List<Player> playersFromDB = playerService.findMatchPlayers(matchId);
        boolean isMatchFinished = playerService.isMatchFinished(playersFromDB);
        Game gameToSave;

        if (isMatchFinished) {
            Optional<Player> theWinner = playerService.findThePlayerOfMatch(playersFromDB);
            Match matchToEnd = matchService.findMatch(matchId);
            if (theWinner.get().getId() % 2 == 0) {
                matchToEnd = new Match(matchToEnd, MatchStatus.PLAYER_2_WINS);
            } else {
                matchToEnd = new Match(matchToEnd, MatchStatus.PLAYER_1_WINS);
            }
            matchService.updateMatch(matchToEnd);
        } else {
            if (!tennisSetService.isSetFinished(currentSet)) {
                gameToSave = new Game(currentSet.getNumber(), playersFromDB.get(0).getCurrentGameScore(), playersFromDB.get(1).getCurrentGameScore(), nextGameStatus);
            } else {
                int nextSetNum = tennisSetService.computeMatchNextSetNumber(matchId);
                List<Player> playersAtEndOfTieBreak = playerService.updatePlayersAtEndOfSet(matchId, currentGameStatus);
                playerRepository.saveAll(playersAtEndOfTieBreak);
                TennisSet currentSetToUpdate = new TennisSet(currentSet.getId(), currentSet.getNumber(),
                        currentSet.getMatchId(), playersAtEndOfTieBreak.get(0).getCurrentSetScore(), playersAtEndOfTieBreak.get(1).getCurrentSetScore());
                tennisSetRepository.save(currentSetToUpdate);
                gameToSave = new Game(nextSetNum, playersAtEndOfTieBreak.get(0).getCurrentGameScore(), playersAtEndOfTieBreak.get(1).getCurrentGameScore(), nextGameStatus);
            }
            gameRepository.save(gameToSave);
        }

        return matchService.displayMatchScore(matchId)
                .map(t -> new MatchScoreDisplayJsonResponse(t.getPlayer1Name(), t.getPlayer2Name(),
                        t.getScore(), t.getCurrentGameStatus(), t.getMatchStatus(), matchId))
                .orElse(null);
    }

}
