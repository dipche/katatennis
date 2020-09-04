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

    public String updateASet(TennisSet tennisSetToUpdate){
        TennisSet tennisSet = tennisSetRepository.save(tennisSetToUpdate);
        return "Set " + "with number " + tennisSet.getNumber() + " saved";
    }

    public List<TennisSet> findMatchSets(Integer matchId){
        return tennisSetRepository.findAllByMatchId(matchId);
    }
}
