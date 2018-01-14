package net.nighthawkempires.core.bungee;

import com.google.common.io.*;
import net.nighthawkempires.core.NECore;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BungeeManager implements PluginMessageListener {

    private boolean online;
    private String name;
    private String ip;
    private int port;

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) return;
        ByteArrayDataInput input = ByteStreams.newDataInput(message);

        String subchannel = input.readUTF();

        if (subchannel.equals("ServerIP")) {
            name = input.readUTF();
            ip = input.readUTF();
            port = input.readUnsignedShort();
            online = online(ip, port);
        }
    }

    public void ping(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ServerIP");
        output.writeUTF(server);
        player.sendPluginMessage(NECore.getPlugin(), "BungeeCord", output.toByteArray());
    }

    public void connect(Player player, String server) {
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF("ServerIP");
        output.writeUTF(server);
        player.sendPluginMessage(NECore.getPlugin(), "BungeeCord", output.toByteArray());

        output = ByteStreams.newDataOutput();
        ByteArrayDataOutput finalOutput = output;
        new BukkitRunnable() {
            @Override
            public void run() {
                if (online) {
                    finalOutput.writeUTF("Connect");
                    finalOutput.writeUTF(server);
                    player.sendPluginMessage(NECore.getPlugin(), "BungeeCord", finalOutput.toByteArray());
                    online = false;
                }
            }
        }.runTaskLater(NECore.getPlugin(), 10);
    }

    public String getName() {
        return name;
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public boolean online(String ip, int port) {
        try {
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(ip, port), 20);
            socket.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
