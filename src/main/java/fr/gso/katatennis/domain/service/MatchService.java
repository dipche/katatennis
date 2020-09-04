package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Match;
import fr.gso.katatennis.domain.model.MatchScoreDisplay;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    Match findMatch(Integer matchId);

    Match updateMatch(Match matchToUpdate);

    List<Match> getAllMatches();

    Optional<MatchScoreDisplay> displayMatchScore(Integer matchId);
}
