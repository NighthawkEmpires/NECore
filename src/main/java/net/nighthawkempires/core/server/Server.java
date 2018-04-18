package net.nighthawkempires.core.server;

public class Server {

    public static final Server HUB = new Server("HUB");
    public static final Server SURVIVAL = new Server("SURVIVAL");
    public static final Server FREEBUILD = new Server("FREEBUILD");
    public static final Server PRISON = new Server("PRISON");
    public static final Server MINIGAME = new Server("MINIGAME");
    public static final Server TEST = new Server("TEST");

    private String name;

    public Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Server[] values() {
        return new Server[]{HUB, SURVIVAL, FREEBUILD, PRISON, MINIGAME, TEST};
    }

    public static Server valueOf(String name) {
        for (Server server : values()) {
            if (server.getName().equalsIgnoreCase(name)) {
                return server;
            }
        }
        return null;
    }

    public ServerType getFrom(Server server) {
        for (ServerType types : ServerType.values()) {
            if (types.getServer() == server) {
                return types;
            }
        }
        return ServerType.HUB;
    }

    public enum ServerType {
        HUB(Server.HUB),
        SURVIVAL(Server.SURVIVAL),
        FREEBUILD(Server.FREEBUILD),
        PRISON(Server.PRISON),
        MINIGAME(Server.MINIGAME),
        TEST(Server.TEST);

        private Server server;

        ServerType(Server server) {
            this.server = server;
        }

        public Server getServer() {
            return server;
        }
    }
}
