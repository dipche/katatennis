package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Match;

public interface IMatchService {

    Match findMatch(Integer matchId);

    String updateMatch(Match matchToUpdate);

    Iterable<Match> getAllMatches();
}
