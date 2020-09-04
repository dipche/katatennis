package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.*;
import fr.gso.katatennis.domain.service.MatchService;
import fr.gso.katatennis.repository.GameRepository;
import fr.gso.katatennis.repository.MatchRepository;
import fr.gso.katatennis.repository.PlayerRepository;
import fr.gso.katatennis.repository.TennisSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static fr.gso.katatennis.domain.model.MatchStatus.IN_PROGRESS;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TennisSetRepository tennisSetRepository;

    @Autowired
    private GameRepository gameRepository;

    public Match findMatch(Integer matchId){
        Optional<Match> match = matchRepository.findById(matchId);
        return match.orElseGet(Match::new);
    }

    public Match updateMatch(Match matchToUpdate){
        return matchRepository.save(matchToUpdate);
    }

    public List<Match> getAllMatches(){
        return (List<Match>)matchRepository.findAll();
    }

    public Optional<MatchScoreDisplay> displayMatchScore(Integer matchId){
        return buildMatchScoreDisplay(matchId);
    }


    private Optional<MatchScoreDisplay> buildMatchScoreDisplay(Integer matchId){
        MatchScoreDisplay matchScoreDisplay = null;

        Match match = findMatch(matchId);

        List<Player> players = playerRepository.findAllByMatchId(match.getId())
                .stream()
                .sorted()
                .collect(Collectors.toList());

        List<TennisSet> sets = tennisSetRepository.findAllByMatchId(match.getId())
                .stream()
                .sorted()
                .collect(Collectors.toList());

        String matchScore = buildMatchScore(sets);

        if (!players.isEmpty() && !sets.isEmpty()) {

            TennisSet currentSet = sets.get(sets.size() - 1);
            Game currentGame = gameRepository.findByTennisSetIdOrderByIdDesc(currentSet.getId()).get(0);

            if(match.getStatus() == IN_PROGRESS ) {
                //TODO here, game status cannot be null or must be computed
                matchScoreDisplay = new MatchScoreDisplay(players.get(0).getName(),
                        players.get(1).getName(),
                        matchScore,
                        currentGame.getStatus().toString(),
                        match.getStatus().toString());
            } else {
                matchScoreDisplay = new MatchScoreDisplay(players.get(0).getName(),
                        players.get(1).getName(),
                        matchScore,
                        match.getStatus().toString());
            }
        }
        return Optional.ofNullable(matchScoreDisplay);
    }

    private String buildMatchScore(List<TennisSet> sets){
        StringBuilder scoreBuilder = new StringBuilder();
        for(TennisSet tennisSet : sets){
            scoreBuilder.append("(")
                    .append(tennisSet.getPlayer1SetScore())
                    .append("-")
                    .append(tennisSet.getPlayer2SetScore())
                    .append(")")
                    .append(" ");
        }
        return scoreBuilder.toString();
    }

    private String buildMatchScoreLambda(List<TennisSet> sets){
        return sets.stream()
                .map(tennisSet -> "(" + tennisSet.getPlayer1SetScore() + "-" + tennisSet.getPlayer2SetScore() + ")" + " ")
                .collect(Collectors.joining());
    }

    //TODO to compute after each player service, then register this status
    private MatchStatus computeMatchStatus(){

        return null;
    }

    private GameStatus computeGameStatus(){

        return null;
    }
}
