package fr.gso.katatennis.domain.model;

import javax.persistence.*;
import java.util.Objects;

@Entity(name="MATCH")
public class Match {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "MATCH_ID")
    private Integer id;

    @Column(name = "PLAYER_1_ID")
    private Integer player1Id;

    @Column(name = "PLAYER_2_ID")
    private Integer player2Id;

    @Column(name = "MATCH_STATUS")
    private MatchStatus status;

    public Match() {
    }

    public Match(Match matchToCopy, MatchStatus statusToSet){
        this.id = matchToCopy.getId();
        this.status = statusToSet;
    }

    public Match(Integer id, Integer player1Id, Integer player2Id, MatchStatus status) {
        this.id = id;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
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
                getStatus() == match.getStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPlayer1Id(), getPlayer2Id(), getStatus());
    }
}
