package fr.gso.katatennis.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class TennisSet implements Comparable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private Integer number;

    @Column(nullable = false)
    private Integer matchId;

    private Integer player1SetScore;

    private Integer player2SetScore;

    public TennisSet() {
    }

    public TennisSet(Integer id, Integer number, Integer matchId, Integer player1SetScore, Integer player2SetScore) {
        this.id = id;
        this.number = number;
        this.matchId = matchId;
        this.player1SetScore = player1SetScore;
        this.player2SetScore = player2SetScore;
    }

    public TennisSet(Integer number, Integer matchId, Integer player1SetScore, Integer player2SetScore) {
        this.number = number;
        this.matchId = matchId;
        this.player1SetScore = player1SetScore;
        this.player2SetScore = player2SetScore;
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getMatchId() {
        return matchId;
    }

    public int getPlayer1SetScore() {
        return player1SetScore;
    }

    public int getPlayer2SetScore() {
        return player2SetScore;
    }

    @Override
    public int compareTo(Object that) {
        TennisSet aTennisSet = (TennisSet) that;
        return Integer.compare(this.number, aTennisSet.number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TennisSet)) return false;
        TennisSet tennisSet = (TennisSet) o;
        return getId() == tennisSet.getId() &&
                getNumber() == tennisSet.getNumber() &&
                getMatchId() == tennisSet.getMatchId() &&
                getPlayer1SetScore() == tennisSet.getPlayer1SetScore() &&
                getPlayer2SetScore() == tennisSet.getPlayer2SetScore();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getMatchId(), getPlayer1SetScore(), getPlayer2SetScore());
    }
}
