package net.nighthawkempires.core.announcer;

public class Announcement {

    private String name;
    private String[] lines;

    public Announcement(String name, String[] lines) {
        this.name = name;
        this.lines = lines;
    }

    public String getName() {
        return name;
    }

    public String[] getLines() {
        return lines.clone();
    }
}
