package fr.gso.katatennis.domain.model;

public enum GameScore {
    ZERO(0),
    FIFTEEN(15),
    THIRTY(30),
    FORTY(40);

    private final int score;

    GameScore(int newScore) {
        this.score = newScore;
    }

    public int getScore() { return score; }

}
