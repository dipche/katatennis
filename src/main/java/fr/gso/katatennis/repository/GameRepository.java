package fr.gso.katatennis.repository;

import fr.gso.katatennis.domain.model.Game;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {

    List<Game> findByTennisSetIdOrderByIdDesc(Integer setId);
}
