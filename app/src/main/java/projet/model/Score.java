package projet.model;

/* IMPORTS */

import java.time.Instant;

public class Score {
    private final String player;
    private final int score;
    private final long timestamp;

    public Score(String player, int score) {
        this.player = player;
        this.score = score;
        this.timestamp = Instant.now().getEpochSecond();
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
