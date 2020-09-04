package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.TennisSet;
import fr.gso.katatennis.domain.service.TennisSetService;
import fr.gso.katatennis.repository.TennisSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TennisSetServiceImpl implements TennisSetService {

    @Autowired
    private TennisSetRepository tennisSetRepository;

    public Integer computeMatchNextSetNumber(Integer matchId){
        List<TennisSet> sets = tennisSetRepository.findAllByMatchId(matchId);
        return sets.size()+1;
    }

    public TennisSet updateASet(TennisSet tennisSetToUpdate){
        return tennisSetRepository.save(tennisSetToUpdate);
    }

    public List<TennisSet> findMatchSets(Integer matchId){
        return tennisSetRepository.findAllByMatchId(matchId);
    }
}
