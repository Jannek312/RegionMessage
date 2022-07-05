package dev.jannek.rgion.message.entity;

import org.bukkit.Location;
import org.bukkit.util.Vector;


public class Area {

    private final Location start;
    private final Location end;

    public Area(final Location start, final Location end) {
        final Location newStart = start.clone();
        newStart.setX(Math.min(start.getX(), end.getX()));
        newStart.setY(Math.min(start.getY(), end.getY()));
        newStart.setZ(Math.min(start.getZ(), end.getZ()));

        final Location newEnd = end.clone();
        newEnd.setX(Math.max(start.getX(), end.getX()));
        newEnd.setY(Math.max(start.getY(), end.getY()));
        newEnd.setZ(Math.max(start.getZ(), end.getZ()));

        this.start = new Location(newStart.getWorld(), newStart.getBlockX(), newStart.getBlockY(), newStart.getBlockZ());
        this.end = new Location(newEnd.getWorld(), newEnd.getBlockX() + 1, newEnd.getBlockY() + 1, newEnd.getBlockZ() + 1);
    }

    public boolean isInArea(final Location location) {
        return isInArea(location.toVector());
    }

    public boolean isInArea(final Vector vector) {
        return vector.isInAABB(this.start.toVector(), this.end.toVector());
    }

}
