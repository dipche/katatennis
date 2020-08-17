package fr.gso.katatennis.repository;

import fr.gso.katatennis.domain.model.TennisSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TennisSetRepository extends CrudRepository<TennisSet, Integer> {

    List<TennisSet> findAllByMatchId(Integer matchId);
}
