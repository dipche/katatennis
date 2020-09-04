package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GameService {
    List<Game> findGameSets(Integer setId);

    Game saveAGame(Game gameToUpdate);
}
