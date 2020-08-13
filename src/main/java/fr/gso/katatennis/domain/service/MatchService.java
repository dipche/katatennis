package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Match;
import fr.gso.katatennis.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MatchService {

    @Autowired
    private MatchRepository matchRepository;

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
}
