package fr.gso.katatennis.repository;

import fr.gso.katatennis.domain.model.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer> {

    Optional<Player> findByName(String name);

    List<Player> findAllByMatchId(Integer matchId);
}
