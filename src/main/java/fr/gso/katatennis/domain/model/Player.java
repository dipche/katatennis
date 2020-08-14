package fr.gso.katatennis.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Player implements Comparable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String name;

    private Integer matchId;

    private Integer currentGameScore;

    private Integer currentSetScore;

    private Integer numbersOfWonSet;

    public Player() {
    }

    public Player(Integer id, String name, Integer matchId, Integer currentGameScore, Integer currentSetScore, Integer numbersOfWonSet) {
        this.id = id;
        this.name = name;
        this.matchId = matchId;
        this.currentGameScore = currentGameScore;
        this.currentSetScore = currentSetScore;
        this.numbersOfWonSet = numbersOfWonSet;
    }

    public Player(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public Integer getCurrentGameScore() {
        return currentGameScore;
    }

    public Integer getCurrentSetScore() {
        return currentSetScore;
    }

    public Integer getNumbersOfWonSet() {
        return numbersOfWonSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(getId(), player.getId()) &&
                Objects.equals(getName(), player.getName()) &&
                Objects.equals(getMatchId(), player.getMatchId()) &&
                Objects.equals(getCurrentGameScore(), player.getCurrentGameScore()) &&
                Objects.equals(getCurrentSetScore(), player.getCurrentSetScore()) &&
                Objects.equals(getNumbersOfWonSet(), player.getNumbersOfWonSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getMatchId(), getCurrentGameScore(), getCurrentSetScore(), getNumbersOfWonSet());
    }

    @Override
    public int compareTo(Object that) {
        Player aPlayer = (Player) that;
        return Integer.compare(this.id, aPlayer.id);
    }
}
