package net.nighthawkempires.core.server;

public class Server {

    public static final Server HUB = new Server("HUB");
    public static final Server SUR = new Server("SUR");
    public static final Server FRB = new Server("FRB");
    public static final Server PRS = new Server("PRS");
    public static final Server MIN = new Server("MIN");
    public static final Server TEST = new Server("TEST");

    private String name;

    public Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Server[] values() {
        return new Server[] { HUB, SUR, FRB, PRS, MIN, TEST };
    }

    public static Server valueOf(String name) {
        for (Server server : values()) {
            if (server.getName().equalsIgnoreCase(name)) {
                return server;
            }
        }
        return null;
    }
}
