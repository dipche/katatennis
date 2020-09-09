package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.Game;
import fr.gso.katatennis.domain.model.GameScore;
import fr.gso.katatennis.domain.model.GameStatus;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.service.GameService;
import fr.gso.katatennis.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final static int NUM_GAMES_TO_ENTER_TIEBREAK = 6;
    private final static int GAMES_GAP_TO_LEAVE_TIEBREAK = 2;
    private final static int MAX_GAMES_TO_WIN_TIEBREAK = 7;
    private final static int MIN_GAMES_TO_WIN_TIEBREAK = 6;

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findGameSets(Integer setId) {
        return this.gameRepository.findByTennisSetNumberOrderByIdDesc(setId);
    }

    public Game saveAGame(Game gameToUpdate) {
        return this.gameRepository.save(gameToUpdate);
    }

    public GameStatus computeNextGameStatus(GameStatus currentGameStatus, Player player1, Player player2, boolean hasPlayer1WonTheGame, boolean hasPlayer2WonTheGame) {
        GameStatus gameStatusToReturn = null;

        if (currentGameStatus == GameStatus.STANDARD && !player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
                && !player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()))
            gameStatusToReturn = GameStatus.STANDARD;

        if (currentGameStatus == GameStatus.STANDARD && player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
                && !player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer2WonTheGame)
            gameStatusToReturn = GameStatus.DEUCE;

        if (currentGameStatus == GameStatus.STANDARD && !player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
                && player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer1WonTheGame)
            gameStatusToReturn = GameStatus.DEUCE;

        if (currentGameStatus == GameStatus.DEUCE && player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
                && player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer1WonTheGame) {
            gameStatusToReturn = GameStatus.ADVANTAGE;
        }

        if (currentGameStatus == GameStatus.DEUCE && player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
                && player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer2WonTheGame) {
            gameStatusToReturn = GameStatus.ADVANTAGE;
        }

        if (currentGameStatus == GameStatus.ADVANTAGE && player1.getHasGameAdvantage() && !player2.getHasGameAdvantage() && hasPlayer1WonTheGame) {
            gameStatusToReturn = GameStatus.STANDARD;
        }

        if (currentGameStatus == GameStatus.ADVANTAGE && player2.getHasGameAdvantage() && !player1.getHasGameAdvantage() && hasPlayer2WonTheGame) {
            gameStatusToReturn = GameStatus.STANDARD;
        }

        if (currentGameStatus == GameStatus.ADVANTAGE && !player1.getHasGameAdvantage() && player2.getHasGameAdvantage() && hasPlayer1WonTheGame) {
            gameStatusToReturn = GameStatus.DEUCE;
        }

        if (currentGameStatus == GameStatus.ADVANTAGE && player1.getHasGameAdvantage() && !player2.getHasGameAdvantage() && hasPlayer2WonTheGame) {
            gameStatusToReturn = GameStatus.DEUCE;
        }

        if (isTieBreakGameStarting(player1, player2)) {
            gameStatusToReturn = GameStatus.TIE_BREAK;
        }

        if (currentGameStatus == GameStatus.TIE_BREAK && isTieBreakGameEnding(player1, player2)) {
            gameStatusToReturn = GameStatus.STANDARD;
        }

        return gameStatusToReturn;
    }

    public boolean isTieBreakGameStarting(Player player1, Player player2) {
        return player1.getCurrentSetScore() == NUM_GAMES_TO_ENTER_TIEBREAK
                && player2.getCurrentSetScore() == NUM_GAMES_TO_ENTER_TIEBREAK;
    }

    public boolean isTieBreakGameEnding(Player player1, Player player2) {

        return (player1.getCurrentGameScore() == MAX_GAMES_TO_WIN_TIEBREAK || player2.getCurrentGameScore() == MAX_GAMES_TO_WIN_TIEBREAK)
                && Math.abs(player1.getCurrentGameScore() - player2.getCurrentGameScore()) >= GAMES_GAP_TO_LEAVE_TIEBREAK || (player1.getCurrentGameScore() >= MIN_GAMES_TO_WIN_TIEBREAK && player2.getCurrentGameScore() >= MIN_GAMES_TO_WIN_TIEBREAK)
                && Math.abs(player1.getCurrentGameScore() - player2.getCurrentGameScore()) >= GAMES_GAP_TO_LEAVE_TIEBREAK;
    }
}
