package fr.gso.katatennis.application.response;

public class CreateMatchJsonResponse {

    private Integer matchId;

    public CreateMatchJsonResponse(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getMatchId() {
        return matchId;
    }
}
