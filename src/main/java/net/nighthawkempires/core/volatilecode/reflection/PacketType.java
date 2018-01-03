package net.nighthawkempires.core.volatilecode.reflection;

public enum  PacketType {

    PlayOut("PlayOut"), PlayIn("PlayIn");

    public String prefix;

    PacketType(String prefix) {

        this.prefix = prefix;

    }
}
