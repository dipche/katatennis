package fr.gso.katatennis.repository;

import fr.gso.katatennis.domain.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Optional<Player> findByName(String name);

    Set<Player> findAllByMatchId(Integer matchId);
}
