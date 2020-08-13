package fr.gso.katatennis.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private Integer player1Id;

    private Integer player2Id;

    private Integer scoreId;

    private MatchStatus status;

    public Match() {
    }

    public Match(Match matchToCopy, MatchStatus statusToSet){
        this.id = matchToCopy.getId();
        this.status = statusToSet;
    }

    public Match(Integer id, Integer player1Id, Integer player2Id, Integer scoreId, MatchStatus status) {
        this.id = id;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
        this.scoreId = scoreId;
        this.status = status;
    }

    public Match(MatchStatus status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public Integer getPlayer1Id() {
        return player1Id;
    }

    public Integer getPlayer2Id() {
        return player2Id;
    }

    public Integer getScoreId() {
        return scoreId;
    }

    public MatchStatus getStatus() {
        return status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        Match match = (Match) o;
        return Objects.equals(getId(), match.getId()) &&
                Objects.equals(getPlayer1Id(), match.getPlayer1Id()) &&
                Objects.equals(getPlayer2Id(), match.getPlayer2Id()) &&
                Objects.equals(getScoreId(), match.getScoreId()) &&
                getStatus() == match.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayer1Id(), getPlayer2Id(), getScoreId(), getStatus());
    }
}
