package net.nighthawkempires.core.task;

import net.nighthawkempires.core.NECore;
import org.bukkit.scheduler.BukkitRunnable;

public class SQLTask extends BukkitRunnable {

    public void run() {
        NECore.getConnector().ping();
    }
}
