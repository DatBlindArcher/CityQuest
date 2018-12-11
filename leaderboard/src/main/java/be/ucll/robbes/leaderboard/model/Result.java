package be.ucll.robbes.leaderboard.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private int score;
    private String player;
    private UUID gameId;

    public Result() {
        //default empty constructor
    }

    public Result(int score, String player, UUID gameId) {
        setScore(score);
        setPlayer(player);
        setGameId(gameId);
    }

    public Result(int score, String player) {
        setScore(score);
        setPlayer(player);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}
