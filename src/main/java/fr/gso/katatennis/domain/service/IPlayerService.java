package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Player;

import java.util.List;

public interface IPlayerService {

    Player findPlayerByName(String name);

    Player findPlayerById(Integer plyId);

    String registerPlayer(Player player);

    Iterable<Player> findAllPlayers();

    List<Player> findMatchPlayers(Integer matchId);
}
