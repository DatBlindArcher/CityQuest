package be.ucll.robbes.cityquest.model;

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
}
