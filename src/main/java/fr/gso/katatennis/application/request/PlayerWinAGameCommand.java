package fr.gso.katatennis.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWinAGameCommand {

    private final String playerName;

    private final Integer matchId;

    public PlayerWinAGameCommand(@JsonProperty("playerName")String playerName, @JsonProperty("matchId")Integer matchId) {
        this.playerName = playerName;
        this.matchId = matchId;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getMatchId() {
        return matchId;
    }
}
