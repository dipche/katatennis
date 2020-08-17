package fr.gso.katatennis.domain.model;

public class MatchScoreDisplay {

    private String player1;

    private String player2;

    private String score;

    private String currentGameStatus;

    private String matchStatus;

    public MatchScoreDisplay(String player1, String player2, String score, String currentGameStatus, String matchStatus) {
        this.player1 = player1;
        this.player2 = player2;
        this.score = score;
        this.currentGameStatus = currentGameStatus;
        this.matchStatus = matchStatus;
    }

    public MatchScoreDisplay(String player1, String player2, String score, String matchStatus) {
        this.player1 = player1;
        this.player2 = player2;
        this.score = score;
        this.matchStatus = matchStatus;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
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
