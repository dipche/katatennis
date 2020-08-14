package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Match;
import fr.gso.katatennis.domain.model.MatchStatus;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.repository.MatchRepository;
import fr.gso.katatennis.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private PlayerRepository playerRepository;

    public Match findMatch(Integer matchId){
        Optional<Match> match = matchRepository.findById(matchId);
        return match.orElseGet(Match::new);
    }

    public String updateMatch(Match matchToUpdate){
        Match matchSaved = matchRepository.save(matchToUpdate);
        return "Match " + "with id " + matchSaved.getId() + " saved";
    }

    public Iterable<Match> getAllMatches(){
        return matchRepository.findAll();
    }


    private String buildMatchScoreDisplay(Integer matchId){
        //Aggregate all data to build a so good JSON
        Match match = findMatch(matchId);

        Set<Player> players = playerRepository.findAllByMatchId(matchId);

        return "{ Player 1 : {}" +
                "Player 2 : {}" +
                "Score : {}" +
                "Current game status : {}" +
                "Match Status : {}" +
                "}";
    }

    //TODO to compute after each player service, then register this status
    private MatchStatus computeMatchStatus(){

        return null;
    }
}
