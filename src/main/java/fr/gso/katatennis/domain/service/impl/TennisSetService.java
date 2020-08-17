package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.TennisSet;
import fr.gso.katatennis.domain.service.ITennisSetService;
import fr.gso.katatennis.repository.TennisSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TennisSetService implements ITennisSetService {

    @Autowired
    private TennisSetRepository tennisSetRepository;

    public Long computeMatchNextSetNumber(Integer matchId){
        List<TennisSet> sets = tennisSetRepository.findAllByMatchId(matchId);
        return (long) (sets.size()+1);
    }

    public String updateASet(TennisSet tennisSetToUpdate){
        TennisSet tennisSet = tennisSetRepository.save(tennisSetToUpdate);
        return "Set " + "with number " + tennisSet.getNumber() + " saved";
    }

    public List<TennisSet> findMatchSets(Integer matchId){
        return tennisSetRepository.findAllByMatchId(matchId);
    }
}
