package fr.gso.katatennis.application.controller;

import fr.gso.katatennis.application.request.CreatePlayerJsonCommand;
import fr.gso.katatennis.application.request.PlayerWinAGameCommand;
import fr.gso.katatennis.application.response.CreatePlayerJsonResponse;
import fr.gso.katatennis.application.response.MatchScoreDisplayJsonResponse;
import fr.gso.katatennis.domain.model.Match;
import fr.gso.katatennis.domain.model.MatchStatus;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.service.MatchService;
import fr.gso.katatennis.domain.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @GetMapping(path="/view/{name}")
    public Player viewPlayer(@PathVariable(value = "name") String name) {
        return playerService.findPlayerByName(name);
    }


    @PostMapping(path="/add")
    public @ResponseBody CreatePlayerJsonResponse addNewPlayer(@RequestBody CreatePlayerJsonCommand createPlayerJsonCommand) {
        Player player = new Player(createPlayerJsonCommand.getPlayerName());
        Player playerCreated = playerService.registerPlayer(player);
        return new CreatePlayerJsonResponse(playerCreated.getId(), playerCreated.getName());
    }

    @GetMapping(path="/all")
    public @ResponseBody
    List<Player> getAllPlayers() {
        return (List<Player>)playerService.findAllPlayers();
    }


    @PostMapping(path="/play/{matchId}")
    public @ResponseBody
    MatchScoreDisplayJsonResponse winAGame(@PathVariable(value = "matchId") Integer matchId,
                                           @RequestBody PlayerWinAGameCommand playerWinAGameCommand){

        //TODO is match status in progress ?
        Match startedMatch = matchService.findMatch(matchId);

        if (startedMatch.getId() == null)
            throw new IllegalStateException("Unrecognised match id");

        if (startedMatch.getStatus() != MatchStatus.IN_PROGRESS)
            throw new IllegalStateException("Match status must be IN PROGRESS");

        //TODO isplayer belong to match ?
        boolean isPlayerRegisteredToMatch = playerService.findMatchPlayers(matchId)
                .stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(playerWinAGameCommand.getPlayerName()));

        if(!isPlayerRegisteredToMatch)
            throw new IllegalStateException("Player " + playerWinAGameCommand.getPlayerName() + " not registered to match id : " + startedMatch.getId());

        //TODO update game score

        //TODO compute game status

        //TODO is game status is deuce, advantage or normal

        //TODO update set score

        //TODO update match score

        //TODO update match status

        //TODO display match score
        return null;
    }
}
