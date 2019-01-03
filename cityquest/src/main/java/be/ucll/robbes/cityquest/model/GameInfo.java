package be.ucll.robbes.cityquest.model;

public class GameInfo {
    private Game game;
    private Leaderboard leaderboard;

    public GameInfo(Game game, Leaderboard leaderboard)
    {
        setGame(game);
        setLeaderboard(leaderboard);
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Leaderboard getLeaderboard() {
        return leaderboard;
    }

    public void setLeaderboard(Leaderboard leaderboard) {
        this.leaderboard = leaderboard;
    }
}
