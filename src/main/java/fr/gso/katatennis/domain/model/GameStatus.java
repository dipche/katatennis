package fr.gso.katatennis.domain.model;

public enum GameStatus {

    STANDARD("STANDARD"),
    DEUCE("DEUCE"),
    ADVANTAGE("ADVANTAGE"),
    TIE_BREAK("TIE_BREAK");

    private String status ;

    GameStatus(String status) {
        this.status = status ;
    }

    public String getStatus() {
        return  this.status;
    }
}
