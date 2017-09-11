package net.nighthawkempires.core.users;

import java.util.List;
import java.util.UUID;

public class User {

    private boolean hub;
    private boolean survival;
    private int tokens;
    private String address;
    private String displayName;
    private String joinDate;
    private String name;
    private UUID uuid;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public boolean hub() {
        return hub;
    }

    public void setHub(boolean hub) {
        this.hub = hub;
    }

    public boolean survival() {
        return survival;
    }

    public void setSurvival(boolean survival) {
        this.survival = survival;
    }

    public int getTokens() {
        return tokens;
    }

    public void addTokens(int amount) {
        this.tokens = tokens + amount;
    }

    public void removeTokens(int amount) {
        this.tokens = tokens - amount;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getUUID() {
        return uuid;
    }

}
