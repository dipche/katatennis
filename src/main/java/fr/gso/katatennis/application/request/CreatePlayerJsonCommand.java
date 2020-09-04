package fr.gso.katatennis.application.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreatePlayerJsonCommand {

    private final String playerName;

    public CreatePlayerJsonCommand(@JsonProperty("Player Name")String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }
}
