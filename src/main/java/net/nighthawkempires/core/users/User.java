package net.nighthawkempires.core.users;

import java.util.UUID;

public class User {

    private boolean hub;
    private boolean sur;
    private boolean prs;
    private boolean min;
    private boolean frb;
    private boolean test;
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

    public boolean sur() {
        return sur;
    }

    public void setSur(boolean survival) {
        this.sur = survival;
    }

    public boolean prs() {
        return prs;
    }

    public void setPrs(boolean prs) {
        this.prs = prs;
    }

    public boolean frb() {
        return frb;
    }

    public void setFrb(boolean frb) {
        this.frb = frb;
    }

    public boolean min() {
        return min;
    }

    public void setMin(boolean min) {
        this.min = min;
    }

    public boolean test() {
        return test;
    }

    public void setTest(boolean test) {
        this.test = test;
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
