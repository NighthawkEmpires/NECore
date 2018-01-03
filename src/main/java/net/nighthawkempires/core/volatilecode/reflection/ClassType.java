package net.nighthawkempires.core.volatilecode.reflection;

public enum  ClassType {

    NMS("net.minecraft.server"),
    CRAFTBUKKIT("org.bukkit.craftbukkit");

    private String pkg;

    ClassType(String pkg) {

        this.pkg = pkg;

    }

    public String getPackage() {

        return pkg;

    }
}
