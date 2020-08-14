package fr.gso.katatennis.repository;

import fr.gso.katatennis.domain.model.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends CrudRepository<Match, Integer> {
}
