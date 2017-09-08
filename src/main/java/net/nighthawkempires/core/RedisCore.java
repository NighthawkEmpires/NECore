package net.nighthawkempires.core;

import net.nighthawkempires.core.chat.redis.RChatListener;
import net.nighthawkempires.core.chat.redis.RMSGListener;
import org.bukkit.Bukkit;
import org.redisson.Config;
import org.redisson.Redisson;
import org.redisson.core.RTopic;

import java.util.logging.Level;

public class RedisCore {

    private static RedisCore instance;
    private final Redisson redis;
    public static RTopic<String> redis_chat;
    public static RTopic<String> redis_msg;
    private static String server_channel;
    private static String server_id;

    public RedisCore(NECore core) {
        instance = this;

        server_id = NECore.getSettings().redisServerId;
        server_channel = NECore.getSettings().redisChannel;

        Config config = new Config();
        config.useSingleServer().setAddress(NECore.getSettings().redisAddress);
        redis = Redisson.create(config);

        redis_msg = redis.getTopic("msg.topic");
        redis_chat = redis.getTopic(server_channel + "$" + "chat.topic");

        try {
            redis.getBucket("test").exists();
            Bukkit.getLogger().log(Level.INFO, "[NECore] Redis connection established!");
            redis_chat.addListener(new RChatListener(this));
            redis_msg.addListener(new RMSGListener());
        } catch (Exception e) {
            Bukkit.getLogger().severe("[NECore] Could not establish redis connection, disabling features.");
            NECore.getSettings().useRedis = false;
        }
    }

    public Redisson getRedis() {
        return redis;
    }

    public String getServerChannel() {
        return server_channel;
    }

    public String getServerId() {
        return server_id;
    }

    public static RedisCore getInstance() {
        return instance;
    }
}
