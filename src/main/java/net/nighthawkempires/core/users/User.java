package net.nighthawkempires.core.users;

import java.util.List;
import java.util.UUID;

public class User {

    private double balance;
    private int deaths;
    private int tokens;
    private int kills;
    private List<String> servers;
    private String address;
    private String displayName;
    private String joinDate;
    private String name;
    private UUID uuid;

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public double getBalance() {
        return balance;
    }

    public void addBalance(double amount) {
        this.balance = balance + amount;
    }

    public void removeBalance(double amount) {
        this.balance = balance - amount;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addDeath() {
        deaths++;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
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

    public int getKills() {
        return kills;
    }

    public void addKill() {
        kills++;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
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
