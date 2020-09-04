package fr.gso.katatennis.application.controller;

import fr.gso.katatennis.application.request.StartMatchJsonCommand;
import fr.gso.katatennis.application.response.CreateMatchJsonResponse;
import fr.gso.katatennis.application.response.MatchScoreDisplayJsonResponse;
import fr.gso.katatennis.domain.model.Match;
import fr.gso.katatennis.domain.model.MatchStatus;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.model.TennisSet;
import fr.gso.katatennis.domain.service.MatchService;
import fr.gso.katatennis.domain.service.PlayerService;
import fr.gso.katatennis.domain.service.TennisSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/match")
public class MatchController {

    private final static String MATCH_INIT_SCORE = "(0-0)";

    private final static String MATCH_INIT_GAME_STATUS = "0-0";

    @Autowired
    private MatchService matchService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private TennisSetService tennisSetService;

    @GetMapping(path="/view")
    public Match viewMatchStatus(@RequestParam(value = "id") int id, @RequestParam(value = "status", defaultValue = "STARTS_SOON") String status) {
        return matchService.findMatch(id);
    }

    @PostMapping(path="/create")
    public @ResponseBody CreateMatchJsonResponse createMatch() {
        Match match = new Match(MatchStatus.STARTS_SOON);
        Match createdMatch = matchService.updateMatch(match);
        return new CreateMatchJsonResponse(createdMatch.getId());
    }

    @GetMapping(path="/all")
    public @ResponseBody List<Match> getAllMatches() {
        return matchService.getAllMatches();
    }

    @PostMapping(path="/start/{matchId}")
    public @ResponseBody MatchScoreDisplayJsonResponse startMatch(@PathVariable(value = "matchId") Integer matchId,
                                           @RequestBody StartMatchJsonCommand startMatchJsonCommand) throws IllegalStateException{
        Match match = matchService.findMatch(matchId);

        if (match.getId() == null)
            throw new IllegalStateException("match with Id : " + matchId + " not exists");


        Player player1 = playerService.findPlayerById(startMatchJsonCommand.getPlayer1Id());
        Player player1ToSave = new Player(player1.getId(), player1.getName(), match.getId(), 0, 0, 0);
        Player savedPlayer1 = playerService.registerPlayer(player1ToSave);

        Player player2 = playerService.findPlayerById(startMatchJsonCommand.getPlayer2Id());
        Player player2ToSave = new Player(player2.getId(), player2.getName(), match.getId(), 0, 0, 0);
        Player savedPlayer2 = playerService.registerPlayer(player2ToSave);

        int nextSetNumber = tennisSetService.computeMatchNextSetNumber(match.getId());
        TennisSet tennisSet = new TennisSet(nextSetNumber, match.getId(), 0, 0);
        tennisSetService.updateASet(tennisSet);

        Match matchToSaved = new Match(match.getId(), startMatchJsonCommand.getPlayer1Id(),
                startMatchJsonCommand.getPlayer2Id(), MatchStatus.IN_PROGRESS);
        Match matchSaved = matchService.updateMatch(matchToSaved);

        return matchService.displayMatchScore(matchSaved.getId())
                .map(t -> new MatchScoreDisplayJsonResponse(t.getPlayer1Name(), t.getPlayer2Name(), t.getScore(), t.getCurrentGameStatus(), t.getMatchStatus(), matchSaved.getId()))
                .orElse(new MatchScoreDisplayJsonResponse(savedPlayer1.getName(), savedPlayer2.getName(), MATCH_INIT_SCORE, MATCH_INIT_GAME_STATUS, matchSaved.getStatus().getStatus(), matchSaved.getId()));
    }
}
