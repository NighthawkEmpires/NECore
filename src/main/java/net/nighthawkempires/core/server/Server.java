package net.nighthawkempires.core.server;

public class Server {

    public static final Server HUB = new Server("HUB");
    public static final Server SUR = new Server("SURVIVAL");
    public static final Server FRB = new Server("FREEBUILD");
    public static final Server PRS = new Server("PRISON");
    public static final Server MIN = new Server("MINIGAME");
    public static final Server TEST = new Server("TEST");

    private String name;

    public Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Server[] values() {
        return new Server[]{HUB, SUR, FRB, PRS, MIN, TEST};
    }

    public static Server valueOf(String name) {
        for (Server server : values()) {
            if (server.getName().equalsIgnoreCase(name)) {
                return server;
            }
        }
        return null;
    }

    public ServerTypes getFrom(Server server) {
        for (ServerTypes types : ServerTypes.values()) {
            if (types.getServer() == server) {
                return types;
            }
        }
        return ServerTypes.HUB;
    }

    public enum ServerTypes {
        HUB(Server.HUB),
        SURVIVAL(Server.SUR),
        FREEBUILD(Server.FRB),
        PRISON(Server.PRS),
        MINIGAMES(Server.MIN),
        TEST(Server.TEST);

        private Server server;

        ServerTypes(Server server) {
            this.server = server;
        }

        public Server getServer() {
            return server;
        }
    }
}
