package fr.gso.katatennis.domain.model;

public enum MatchStatus {

    STARTS_SOON("STARTS SOON"), IN_PROGRESS("IN PROGRESS"), PLAYER_1_WINS("PLAYER 1 WINS"), PLAYER_2_WINS("PLAYER 2 WINS");

    private String status ;

    MatchStatus(String status) {
        this.status = status ;
    }

    public String getStatus() {
        return  this.status;
    }
}
