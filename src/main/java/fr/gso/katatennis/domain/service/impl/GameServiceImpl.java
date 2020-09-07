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

    @Autowired
    private GameRepository gameRepository;

    public List<Game> findGameSets(Integer setId){
        return this.gameRepository.findByTennisSetNumberOrderByIdDesc(setId);
    }

    public Game findCurrentGame(Integer setId){
        return this.gameRepository.findFirstByTennisSetNumberOrderByIdDesc(setId);
    }

   public Game saveAGame(Game gameToUpdate){
        return this.gameRepository.save(gameToUpdate);
   }

   public GameStatus computeNextGameStatus(GameStatus currentGameStatus, Player player1, Player player2, boolean hasPlayer1WonTheGame, boolean hasPlayer2WonTheGame){
        GameStatus gameStatusToReturn = null;

        if(currentGameStatus == GameStatus.STANDARD && !player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
                && !player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()))
            gameStatusToReturn = GameStatus.STANDARD;

        if(currentGameStatus == GameStatus.STANDARD && player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
                && !player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer2WonTheGame)
            gameStatusToReturn = GameStatus.DEUCE;

        if(currentGameStatus == GameStatus.STANDARD && !player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
               && player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer1WonTheGame)
           gameStatusToReturn = GameStatus.DEUCE;

        if(currentGameStatus == GameStatus.DEUCE && player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
               && player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer1WonTheGame){
           gameStatusToReturn = GameStatus.ADVANTAGE;
           //TODO mettre à jour l'avantage sur le joueur 1
        }

        if(currentGameStatus == GameStatus.DEUCE && player1.getCurrentGameScore().equals(GameScore.FORTY.getScore())
               && player2.getCurrentGameScore().equals(GameScore.FORTY.getScore()) && hasPlayer2WonTheGame){
           gameStatusToReturn = GameStatus.ADVANTAGE;
           //TODO mettre à jour l'avantage sur le joueur 2
        }

        if(currentGameStatus == GameStatus.ADVANTAGE && player1.getHasGameAdvantage() && !player2.getHasGameAdvantage() && hasPlayer1WonTheGame){
           gameStatusToReturn = GameStatus.STANDARD;
           //TODO réénitialiser les scores game des deux joueurs ici
            //TODO score set player 1 increments
        }

        if(currentGameStatus == GameStatus.ADVANTAGE && player2.getHasGameAdvantage() && !player1.getHasGameAdvantage() && hasPlayer2WonTheGame){
           gameStatusToReturn = GameStatus.STANDARD;
           //TODO réénitialiser les scores des deux joueurs ici
            //TODO score set player 2 increments
        }

        if(currentGameStatus == GameStatus.ADVANTAGE && !player1.getHasGameAdvantage() && player2.getHasGameAdvantage() && hasPlayer1WonTheGame){
           gameStatusToReturn = GameStatus.DEUCE;
           //TODO udpate la perte d'avantage sur le player 2
        }

        if(currentGameStatus == GameStatus.ADVANTAGE && player1.getHasGameAdvantage() && !player2.getHasGameAdvantage() && hasPlayer2WonTheGame){
           gameStatusToReturn = GameStatus.DEUCE;
           //TODO update la perte d'avantage sur le Player 1
        }

        return gameStatusToReturn;
   }
}
