package fr.gso.katatennis.application.response;

public class CreatePlayerJsonResponse {

    private Integer playerId;

    private String playerName;

    public CreatePlayerJsonResponse(Integer playerId, String playerName) {
        this.playerId = playerId;
        this.playerName = playerName;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public String getPlayerName() {
        return playerName;
    }
}
