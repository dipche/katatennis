package fr.gso.katatennis.application.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MatchScoreDisplayJsonResponse {

    @JsonProperty("Match Id")
    private Integer id;

    @JsonProperty("Player 1")
    private String player1Name;

    @JsonProperty("Player 2")
    private String player2Name;

    @JsonProperty("Score")
    private String score;

    @JsonProperty("Current game status")
    private String currentGameStatus;

    @JsonProperty("Match Status")
    private String status;

    public MatchScoreDisplayJsonResponse(String player1Name, String player2Name, String score,
                                         String currentGameStatus, String matchStatus, Integer matchId) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.score = score;
        this.currentGameStatus = currentGameStatus;
        this.status = matchStatus;
        this.id = matchId;
    }

    public Integer getId() {
        return id;
    }

    public String getPlayer1Name() {
        return player1Name;
    }

    public String getPlayer2Name() {
        return player2Name;
    }

    public String getScore() {
        return score;
    }

    public String getCurrentGameStatus() {
        return currentGameStatus;
    }

    public String getStatus() {
        return status;
    }
}
