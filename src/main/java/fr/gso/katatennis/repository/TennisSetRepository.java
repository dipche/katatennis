package fr.gso.katatennis.repository;

import fr.gso.katatennis.domain.model.TennisSet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TennisSetRepository extends CrudRepository<TennisSet, Integer> {

    Set<TennisSet> findAllByMatchId(Integer matchId);
}
