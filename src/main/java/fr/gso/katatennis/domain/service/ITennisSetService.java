package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.TennisSet;

import java.util.List;

public interface ITennisSetService {

    Long computeMatchNextSetNumber(Integer matchId);

    String updateASet(TennisSet tennisSetToUpdate);

    List<TennisSet> findMatchSets(Integer matchId);
}
