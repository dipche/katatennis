package fr.gso.katatennis.application.controller;

import fr.gso.katatennis.domain.model.Match;
import fr.gso.katatennis.domain.model.MatchStatus;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.service.MatchService;
import fr.gso.katatennis.domain.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @Autowired
    private PlayerService playerService;

    @GetMapping(path="/view")
    public Match viewMatchStatus(@RequestParam(value = "id") int id, @RequestParam(value = "status", defaultValue = "STARTS_SOON") String status) {
        return matchService.findMatch(id);
    }

    @PostMapping(path="/create")
    public @ResponseBody String createMatch() {
        Match match = new Match(MatchStatus.STARTS_SOON);
        return matchService.updateMatch(match);
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @PostMapping(path="/start/{matchId}")
    public @ResponseBody String startMatch(@PathVariable(value = "matchId") Integer matchId,
                                           @RequestParam(value = "playerId1") Integer plyId1,
                                           @RequestParam(value = "playerId2") Integer plyId2){
        //je recherche le match
        Match match = matchService.findMatch(matchId);


        Player player = playerService.findPlayerById(plyId1);
        Player playerToSave = new Player(player.getId(), player.getName(), match.getId(), 0, 0, 0);
        playerService.registerPlayer(playerToSave);

        player = playerService.findPlayerById(plyId2);
        playerToSave = new Player(player.getId(), player.getName(), match.getId(), 0, 0, 0);
        playerService.registerPlayer(playerToSave);

        //j'enregistre son statut
        //j'enregistre les deux participants au match
        //j'initialise le score
        //je retourne le json tel que la spec
        //todo manage score creation before saving this match
        //todo check players existence
        Match matchToSaved = new Match(match.getId(), plyId1, plyId2, null, MatchStatus.IN_PROGRESS);
        return matchService.updateMatch(matchToSaved);
    }
}
