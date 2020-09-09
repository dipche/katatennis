package fr.gso.katatennis.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class StartMatchJsonCommand {

    private final Integer matchId;

    private final Integer player1Id;

    private final Integer player2Id;

    public StartMatchJsonCommand(@JsonProperty("matchId") Integer matchId,
                                 @JsonProperty("player1Id") Integer player1Id,
                                 @JsonProperty("player2Id") Integer player2Id) {
        this.matchId = matchId;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
    }


    public Integer getMatchId() {
        return matchId;
    }

    public Integer getPlayer1Id() {
        return player1Id;
    }

    public Integer getPlayer2Id() {
        return player2Id;
    }
}
