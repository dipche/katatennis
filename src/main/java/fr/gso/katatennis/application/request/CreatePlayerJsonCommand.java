package fr.gso.katatennis.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePlayerJsonCommand {

    private final String playerName;

    public CreatePlayerJsonCommand(@JsonProperty("playerName")String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
