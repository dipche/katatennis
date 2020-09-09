package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.GameStatus;
import fr.gso.katatennis.domain.model.Player;

import java.util.List;
import java.util.Optional;

public interface PlayerService {

    Player findPlayerByName(String name);

    Player findPlayerById(Integer plyId);

    Player registerPlayer(Player player);

    Iterable<Player> findAllPlayers();

    List<Player> findMatchPlayers(Integer matchId);

    List<Player> buildPlayersUpdate(GameStatus currentGameStatus, GameStatus nextGameStatus, Player player1,
                                    Player player2, boolean hasPlayer1WonTheGame, boolean hasPlayer2WonTheGame);

    boolean isMatchFinished(List<Player> players);

    Optional<Player> findThePlayerOfMatch(List<Player> players);

    List<Player> updatePlayersAtEndOfSet(Integer matchId, GameStatus currentGameStatus);
}
