package fr.gso.katatennis.application.response;

public class ViewMatchJsonResponse {

    private Integer matchId;
    
    private String matchStatus;

    public ViewMatchJsonResponse(Integer matchId, String matchStatus) {
        this.matchId = matchId;
        this.matchStatus = matchStatus;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public String getMatchStatus() {
        return matchStatus;
    }
}
