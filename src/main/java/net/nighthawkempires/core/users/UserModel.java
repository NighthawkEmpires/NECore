package net.nighthawkempires.core.users;

import com.demigodsrpg.util.datasection.DataSection;
import com.demigodsrpg.util.datasection.Model;
import net.nighthawkempires.core.NECore;
import org.bukkit.Bukkit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserModel implements Model {

    private UUID uuid;
    private String name;
    private String displayName;
    private String joinDate;
    private String address;
    private int tokens;
    private boolean hub;
    private boolean survival;
    private boolean prison;
    private boolean minigame;
    private boolean freebuild;
    private boolean test;

    public UserModel(UUID uuid) {
        this.uuid = uuid;
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
        this.displayName = name;
        this.joinDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            this.address = Bukkit.getPlayer(uuid).getAddress().getHostString();
        } else {
            this.address = "";
        }
        this.tokens = 15;
        this.hub = false;
        this.survival = false;
        this.prison = false;
        this.minigame = false;
        this.freebuild = false;
        this.test = false;
    }

    public UserModel(String id, DataSection data) {
        this.uuid = UUID.fromString(id);
        this.name = Bukkit.getOfflinePlayer(uuid).getName();
        if (data.isString("display-name")) {
            this.displayName = data.getString("display-name");
        } else {
            this.displayName = name;
        }
        this.joinDate = data.getString("join-date");
        if (Bukkit.getOnlinePlayers().contains(Bukkit.getPlayer(uuid))) {
            this.address = Bukkit.getPlayer(uuid).getAddress().getHostString();
        } else if (data.isString("address")) {
            this.address = data.getString("address");
        } else {
            this.address = "";
        }
        this.tokens = data.getInt("tokens");
        this.hub = data.getBoolean("hub");
        this.survival = data.getBoolean("survival");
        this.prison = data.getBoolean("prison");
        this.minigame = data.getBoolean("minigame");
        this.freebuild = data.getBoolean("freebuild");
        this.test = data.getBoolean("test");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        NECore.getUserRegistry().register(this);
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
        NECore.getUserRegistry().register(this);
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
        NECore.getUserRegistry().register(this);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        NECore.getUserRegistry().register(this);
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
        NECore.getUserRegistry().register(this);
    }

    public boolean playedHub() {
        return hub;
    }

    public void setPlayedHub(boolean hub) {
        this.hub = hub;
        NECore.getUserRegistry().register(this);
    }

    public boolean playedSurvival() {
        return survival;
    }

    public void setPlayedSurvival(boolean survival) {
        this.survival = survival;
        NECore.getUserRegistry().register(this);
    }

    public boolean playedPrison() {
        return prison;
    }

    public void setPlayedPrison(boolean prison) {
        this.prison = prison;
        NECore.getUserRegistry().register(this);
    }

    public boolean playedFreebuild() {
        return freebuild;
    }

    public void setPlayedFreebuild(boolean freebuild) {
        this.freebuild = freebuild;
        NECore.getUserRegistry().register(this);
    }

    public boolean playedMinigames() {
        return minigame;
    }

    public void setPlayedMinigames(boolean minigame) {
        this.minigame = minigame;
        NECore.getUserRegistry().register(this);
    }

    public boolean playedTest() {
        return test;
    }

    public void setPlayedTest(boolean test) {
        this.test = true;
        NECore.getUserRegistry().register(this);
    }

    @Override
    public String getKey() {
        return uuid.toString();
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        if (!displayName.equals(name)) {
            map.put("diaplay-name", displayName);
        }

        map.put("join-date", joinDate);
        if (!address.equals("")) {
            map.put("address", address);
        }

        map.put("tokens", tokens);
        map.put("hub", hub);
        map.put("survival", survival);
        map.put("prison", prison);
        map.put("minigame", minigame);
        map.put("freebuild", freebuild);
        map.put("test", test);
        return map;
    }
}
