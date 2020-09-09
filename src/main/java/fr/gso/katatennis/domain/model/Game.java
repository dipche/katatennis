package fr.gso.katatennis.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="GAME")
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "GAME_ID")
    private Integer id;

    @Column(name = "SET_NUMBER")
    private Integer tennisSetNumber;

    @Column(name = "PLAYER_1_SCORE")
    private Integer player1Score;

    @Column(name = "PLAYER_2_SCORE")
    private Integer player2Score;

    @Column(name = "GAME_STATUS")
    private GameStatus status;

    public Game(int tennisSetNumber, int player1Score, int player2Score, GameStatus status) {
        this.tennisSetNumber = tennisSetNumber;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.status = status;
    }

    public Game() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getTennisSetNumber() {
        return tennisSetNumber;
    }

    public Integer getPlayer1Score() {
        return player1Score;
    }

    public Integer getPlayer2Score() {
        return player2Score;
    }

    public GameStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Game)) return false;
        Game game = (Game) o;
        return id.equals(game.id) &&
                tennisSetNumber.equals(game.tennisSetNumber) &&
                player1Score.equals(game.player1Score) &&
                player2Score.equals(game.player2Score) &&
                status == game.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tennisSetNumber, player1Score, player2Score, status);
    }
}
