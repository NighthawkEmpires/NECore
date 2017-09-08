package net.nighthawkempires.core.announcer;

public class Announcement {

    private int id;
    private String[] lines;

    public Announcement(int id, String[] lines) {
        this.id = id;
        this.lines = lines;
    }

    public int getId() {
        return id;
    }

    public String[] getLines() {
        return lines.clone();
    }
}
