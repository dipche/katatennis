package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.TennisSet;
import fr.gso.katatennis.repository.TennisSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TennisSetService {

    @Autowired
    private TennisSetRepository tennisSetRepository;

    public Long computeMatchNextSetNumber(Integer matchId){
        Set<TennisSet> sets = tennisSetRepository.findAllByMatchId(matchId);
        return (long) (sets.size()+1);
    }

    public String updateASet(TennisSet tennisSetToUpdate){
        TennisSet tennisSet = tennisSetRepository.save(tennisSetToUpdate);
        return "Set " + "with number " + tennisSet.getNumber() + " saved";
    }

    public Set<TennisSet> findMatchSets(Integer matchId){
        return tennisSetRepository.findAllByMatchId(matchId);
    }
}
