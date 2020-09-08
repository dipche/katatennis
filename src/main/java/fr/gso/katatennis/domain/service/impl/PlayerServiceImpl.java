package fr.gso.katatennis.domain.service.impl;

import fr.gso.katatennis.domain.model.GameScore;
import fr.gso.katatennis.domain.model.GameStatus;
import fr.gso.katatennis.domain.model.Player;
import fr.gso.katatennis.domain.service.PlayerService;
import fr.gso.katatennis.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final static int NUM_SETS_TO_WIN_MATCH = 3;


    @Autowired
    private PlayerRepository playerRepository;

    public Player findPlayerByName(String name){
        Optional<Player> player = playerRepository.findByName(name);
        return player.orElseGet(Player::new);
    }

    public Player findPlayerById(Integer plyId){
        Optional<Player> player = playerRepository.findById(plyId);
        return player.orElseGet(Player::new);
    }

    public Player registerPlayer(Player player){
        return playerRepository.save(player);
    }

    public Iterable<Player> findAllPlayers(){
        return playerRepository.findAll();
    }

    public List<Player> findMatchPlayers(Integer matchId){
        return playerRepository.findAllByMatchId(matchId).stream()
                .sorted()
                .collect(Collectors.toList());
    }


    public List<Player> buildPlayersUpdate(GameStatus currentGameStatus, GameStatus nextGameStatus, Player player1, Player player2, boolean hasPlayer1WonTheGame, boolean hasPlayer2WonTheGame){

        List<Player> players = new ArrayList<>();

        if(currentGameStatus == GameStatus.DEUCE && nextGameStatus == GameStatus.ADVANTAGE){

           if(hasPlayer1WonTheGame && !hasPlayer2WonTheGame){

               Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                       player1.getCurrentGameScore(), player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                       Boolean.TRUE);
               players.add(player1ToUpdate);

               Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                       player2.getCurrentGameScore(), player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                       Boolean.FALSE);
               players.add(player2ToUpdate);
           }

           if(!hasPlayer1WonTheGame && hasPlayer2WonTheGame){

               Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                       player1.getCurrentGameScore(), player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                       Boolean.FALSE);
               players.add(player1ToUpdate);

               Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                       player2.getCurrentGameScore(), player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                       Boolean.TRUE);
               players.add(player2ToUpdate);
           }
        }

        if(currentGameStatus == GameStatus.ADVANTAGE && nextGameStatus == GameStatus.STANDARD){

            if(hasPlayer1WonTheGame && !hasPlayer2WonTheGame){

                Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                        computeNextScore(player1.getCurrentGameScore()).getScore(), player1.getCurrentSetScore()+1, player1.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player1ToUpdate);

                Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                        computeNextScore(player2.getCurrentGameScore()).getScore(), player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player2ToUpdate);
            }

            if(!hasPlayer1WonTheGame && hasPlayer2WonTheGame){

                Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                        computeNextScore(player1.getCurrentGameScore()).getScore(), player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player1ToUpdate);

                Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                        computeNextScore(player2.getCurrentGameScore()).getScore(), player2.getCurrentSetScore()+1, player2.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player2ToUpdate);
            }
        }

        if(currentGameStatus == GameStatus.ADVANTAGE && nextGameStatus == GameStatus.DEUCE){

            if(hasPlayer1WonTheGame || hasPlayer2WonTheGame){

                Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                        player1.getCurrentGameScore(), player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player1ToUpdate);

                Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                        player2.getCurrentGameScore(), player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player2ToUpdate);
            }
        }

        if(currentGameStatus == GameStatus.STANDARD && nextGameStatus == GameStatus.STANDARD){

            if(hasPlayer1WonTheGame && !hasPlayer2WonTheGame){
                Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                        computeNextScore(player1.getCurrentGameScore()).getScore(), player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player1ToUpdate);

                Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                        player2.getCurrentGameScore(), player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player2ToUpdate);
            }

            if(!hasPlayer1WonTheGame && hasPlayer2WonTheGame){
                Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                        player1.getCurrentGameScore(), player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player1ToUpdate);

                Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                        computeNextScore(player2.getCurrentGameScore()).getScore(), player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player2ToUpdate);
            }
        }

        if(currentGameStatus == GameStatus.TIE_BREAK && nextGameStatus == GameStatus.TIE_BREAK){

            if(hasPlayer1WonTheGame && !hasPlayer2WonTheGame){
                Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                        player1.getCurrentGameScore()+1, player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player1ToUpdate);

                Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                        player2.getCurrentGameScore(), player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player2ToUpdate);
            }

            if(!hasPlayer1WonTheGame && hasPlayer2WonTheGame){
                Player player1ToUpdate = new Player(player1.getId(), player1.getName(), player1.getMatchId(),
                        player1.getCurrentGameScore(), player1.getCurrentSetScore(), player1.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player1ToUpdate);

                Player player2ToUpdate = new Player(player2.getId(), player2.getName(), player2.getMatchId(),
                        player2.getCurrentGameScore()+1, player2.getCurrentSetScore(), player2.getNumbersOfWonSet(),
                        Boolean.FALSE);
                players.add(player2ToUpdate);
            }

        }
        return players;
    }

    private GameScore computeNextScore(Integer playerScore){
        GameScore nextScore = null;

        if(playerScore.equals(GameScore.FORTY.getScore())){
            nextScore = GameScore.ZERO;
        }
        if(playerScore.equals(GameScore.ZERO.getScore())){
            nextScore = GameScore.FIFTEEN;
        }
        if(playerScore.equals(GameScore.FIFTEEN.getScore())){
            nextScore = GameScore.THIRTY;
        }
        if(playerScore.equals(GameScore.THIRTY.getScore())){
            nextScore = GameScore.FORTY;
        }
        return nextScore;
    }


    public boolean isMatchFinished(List<Player> players) {
        return players.stream().anyMatch(p -> p.getNumbersOfWonSet() == NUM_SETS_TO_WIN_MATCH);
    }

    public Optional<Player> findThePlayerOfMatch(List<Player> players) {
        return players.stream()
                .filter(p -> p.getNumbersOfWonSet() == NUM_SETS_TO_WIN_MATCH)
                .findFirst();
    }
}
