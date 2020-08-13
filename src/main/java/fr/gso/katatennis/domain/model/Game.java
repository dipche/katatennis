package fr.gso.katatennis.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer tennisSetId;

    private Integer player1Score;

    private Integer player2Score;

    private GameStatus status;

    public Game(int id, int tennisSetId, int player1Score, int player2Score, GameStatus status) {
        this.id = id;
        this.tennisSetId = tennisSetId;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
        this.status = status;
    }

    public Game() {
    }

    public int getId() {
        return id;
    }

    public int getTennisSetId() {
        return tennisSetId;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
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
                tennisSetId.equals(game.tennisSetId) &&
                player1Score.equals(game.player1Score) &&
                player2Score.equals(game.player2Score) &&
                status == game.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tennisSetId, player1Score, player2Score, status);
    }
}
