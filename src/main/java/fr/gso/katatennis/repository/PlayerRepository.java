package fr.gso.katatennis.repository;

import fr.gso.katatennis.domain.model.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Optional<Player> findByName(String name);

}
