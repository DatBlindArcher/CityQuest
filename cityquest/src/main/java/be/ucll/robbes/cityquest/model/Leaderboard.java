package be.ucll.robbes.cityquest.model;

import java.util.UUID;

public class Leaderboard {

    private Entry[] entries;

    public Leaderboard(Entry[] entries)
    {
        setEntries(entries);
    }

    public Entry[] getEntries() {
        return entries;
    }

    public void setEntries(Entry[] entries) {
        this.entries = entries;
    }

    public class Entry
    {
        private UUID id;
        private int score;
        private String player;
        private UUID gameId;

        public Entry() {
            //default empty constructor
        }

        public Entry(int score, String player, UUID gameId) {
            setScore(score);
            setPlayer(player);
            setGameId(gameId);
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
}
