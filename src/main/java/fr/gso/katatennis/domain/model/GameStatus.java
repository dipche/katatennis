package fr.gso.katatennis.domain.model;

public enum GameStatus {

    DEUCE("DEUCE"), ADVANTAGE("ADVANTAGE");

    private String status ;

    GameStatus(String status) {
        this.status = status ;
    }

    public String getStatus() {
        return  this.status;
    }
}
