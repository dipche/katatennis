package fr.gso.katatennis.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="PLAYER")
public class Player implements Comparable {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "PLAYER_ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "MATCH_ID")
    private Integer matchId;

    @Column(name = "GAME_SCORE")
    private Integer currentGameScore;

    @Column(name = "SET_SCORE")
    private Integer currentSetScore;

    @Column(name = "NUM_SET_WON")
    private Integer numbersOfWonSet;

    @Column(name = "HAS_ADVANTAGE")
    private Boolean hasGameAdvantage;

    public Player() {
    }

    public Player(Integer id, String name, Integer matchId, Integer currentGameScore, Integer currentSetScore, Integer numbersOfWonSet, Boolean hasGameAdvantage) {
        this.id = id;
        this.name = name;
        this.matchId = matchId;
        this.currentGameScore = currentGameScore;
        this.currentSetScore = currentSetScore;
        this.numbersOfWonSet = numbersOfWonSet;
        this.hasGameAdvantage = hasGameAdvantage;
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

    public Boolean getHasGameAdvantage() {
        return hasGameAdvantage;
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
                Objects.equals(getNumbersOfWonSet(), player.getNumbersOfWonSet()) &&
                Objects.equals(getHasGameAdvantage(), player.getHasGameAdvantage());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getMatchId(), getCurrentGameScore(), getCurrentSetScore(), getNumbersOfWonSet(), getHasGameAdvantage());
    }

    @Override
    public int compareTo(Object that) {
        Player aPlayer = (Player) that;
        return Integer.compare(this.id, aPlayer.id);
    }
}
