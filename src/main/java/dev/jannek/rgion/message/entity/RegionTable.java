package dev.jannek.rgion.message.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import javax.persistence.Id;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "region_manager_region")
public class RegionTable {

    @Id
    private int id;

    private String world;

    private double fromX;
    private double fromY;
    private double fromZ;

    private double toX;
    private double toY;
    private double toZ;

    public Location getFrom() {
        return new Location(Bukkit.getWorld(getWorld()), getFromX(), getFromY(), getFromZ());
    }

    public Location getTo() {
        return new Location(Bukkit.getWorld(getWorld()), getToX(), getToY(), getToZ());
    }

}
