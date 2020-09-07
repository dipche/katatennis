package fr.gso.katatennis.domain.service;

import fr.gso.katatennis.domain.model.Game;
import fr.gso.katatennis.domain.model.GameStatus;
import fr.gso.katatennis.domain.model.Player;

import java.util.List;


public interface GameService {
    List<Game> findGameSets(Integer setId);

    Game saveAGame(Game gameToUpdate);

    GameStatus computeNextGameStatus(GameStatus currentGameStatus, Player player1, Player player2, boolean hasPlayer1WonTheGame, boolean hasPlayer2WonTheGame);
}
