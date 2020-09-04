package fr.gso.katatennis.domain.model;

public class MatchScoreDisplay {

    private String player1Name;

    private String player2Name;

    private String score;

    private String currentGameStatus;

    private String matchStatus;

    public MatchScoreDisplay(String player1Name, String player2Name, String score, String currentGameStatus, String matchStatus) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.score = score;
        this.currentGameStatus = currentGameStatus;
        this.matchStatus = matchStatus;
    }

    public MatchScoreDisplay(String player1Name, String player2Name, String score, String matchStatus) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.score = score;
        this.matchStatus = matchStatus;
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

    public String getMatchStatus() {
        return matchStatus;
    }
}
