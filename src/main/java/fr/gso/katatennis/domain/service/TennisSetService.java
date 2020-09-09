package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.TennisSet;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TennisSetService {

    Integer computeMatchNextSetNumber(Integer matchId);

    TennisSet updateASet(TennisSet tennisSetToUpdate);

    boolean isSetFinished(TennisSet aTennisSet);
}
