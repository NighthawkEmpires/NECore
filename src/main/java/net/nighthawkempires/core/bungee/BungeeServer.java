package net.nighthawkempires.core.bungee;

import net.nighthawkempires.core.NECore;
import org.bukkit.entity.Player;

public class BungeeServer {

    private String name;
    private String ip;
    private int port;
    private boolean online;
    private BungeeServerType serverType;

    public BungeeServer(String name) {
        this.name = name;
    }

    public BungeeServer setType() {
        return this;
    }

    public BungeeServer build(Player player) {
        NECore.getBungeeManager().ping(player, getName());
        this.ip = NECore.getBungeeManager().getIp();
        this.port = NECore.getBungeeManager().getPort();
        this.online = NECore.getBungeeManager().online(getIP(), getPort());
        return this;
    }

    public String getName() {
        return name;
    }

    public String getIP() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public boolean isOnline() {
        return online;
    }

    public BungeeServerType getServerType() {
        return serverType;
    }

    public void connectPlayer(Player player) {
        NECore.getBungeeManager().connect(player, getName());
    }

    public enum BungeeServerType {
        HUB, SURVIVAL, FREEBUILD, PRISON, MINIGAME, TEST
    }
}
