package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Game;

import java.util.List;

public interface IGameService {
    List<Game> findSetGames(Integer setId);
}
