package fr.gso.katatennis.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class TennisSet implements Comparable{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer number;

    private Integer matchId;

    private Integer player1Score;

    private Integer player2Score;

    public TennisSet() {
    }

    public TennisSet(int id, int number, int matchId, int player1Score, int player2Score) {
        this.id = id;
        this.number = number;
        this.matchId = matchId;
        this.player1Score = player1Score;
        this.player2Score = player2Score;
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

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
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
                getPlayer1Score() == tennisSet.getPlayer1Score() &&
                getPlayer2Score() == tennisSet.getPlayer2Score();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNumber(), getMatchId(), getPlayer1Score(), getPlayer2Score());
    }
}
