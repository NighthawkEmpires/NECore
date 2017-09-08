package net.nighthawkempires.core.server;

public class Server {

    public static final Server HUB = new Server("hub");
    public static final Server NS = new Server("nighthawksurvival");
    public static final Server CREATIVE = new Server("creative");

    private String name;

    public Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Server[] values() {
        return new Server[] { HUB, NS, CREATIVE };
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
