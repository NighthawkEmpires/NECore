package net.nighthawkempires.core.ban;

import java.util.UUID;

public class Ban {

    private UUID uuid;
    private String address;
    private String reason;
    private String date;
    private UUID by;

    public Ban(UUID uuid, String address, String reason, String date, UUID by) {
        this.uuid = uuid;
        this.address = address;
        this.reason = reason;
        this.date = date;
        this.by = by;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getAddress() {
        return address;
    }

    public String getReason() {
        return reason;
    }

    public String getDate() {
        return date;
    }

    public UUID getBy() {
        return by;
    }
}
