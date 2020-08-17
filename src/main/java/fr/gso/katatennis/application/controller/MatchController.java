package fr.gso.katatennis.application.controller;

import fr.gso.katatennis.domain.model.Match;
import fr.gso.katatennis.domain.model.MatchStatus;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.model.TennisSet;
import fr.gso.katatennis.domain.service.IMatchService;
import fr.gso.katatennis.domain.service.IPlayerService;
import fr.gso.katatennis.domain.service.ITennisSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/match")
public class MatchController {

    @Autowired
    private IMatchService matchService;

    @Autowired
    private IPlayerService playerService;

    @Autowired
    private ITennisSetService tennisSetService;

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
        Match match = matchService.findMatch(matchId);

        Player player = playerService.findPlayerById(plyId1);
        Player playerToSave = new Player(player.getId(), player.getName(), match.getId(), 0, 0, 0);
        playerService.registerPlayer(playerToSave);

        player = playerService.findPlayerById(plyId2);
        playerToSave = new Player(player.getId(), player.getName(), match.getId(), 0, 0, 0);
        playerService.registerPlayer(playerToSave);

        long nextSetNumber = tennisSetService.computeMatchNextSetNumber(match.getId());
        TennisSet tennisSet = new TennisSet((int)nextSetNumber, match.getId(), 0, 0);
        tennisSetService.updateASet(tennisSet);

        //todo check players existence
        Match matchToSaved = new Match(match.getId(), plyId1, plyId2, MatchStatus.IN_PROGRESS);
        return matchService.updateMatch(matchToSaved);
    }
}
