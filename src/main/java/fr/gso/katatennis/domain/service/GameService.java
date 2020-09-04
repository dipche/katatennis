package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Game;

import java.util.List;

public interface GameService {
    List<Game> findGameSets(Integer setId);
}
