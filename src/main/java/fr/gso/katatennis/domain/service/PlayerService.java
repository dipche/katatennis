package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Player;

import java.util.List;

public interface PlayerService {

    Player findPlayerByName(String name);

    Player findPlayerById(Integer plyId);

    Player registerPlayer(Player player);

    Iterable<Player> findAllPlayers();

    List<Player> findMatchPlayers(Integer matchId);
}
