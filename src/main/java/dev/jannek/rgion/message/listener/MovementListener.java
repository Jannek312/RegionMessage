package dev.jannek.rgion.message.listener;

import dev.jannek.rgion.message.RegionMessage;
import dev.jannek.rgion.message.entity.Area;
import dev.jannek.rgion.message.entity.MessageConfiguration;
import dev.jannek.rgion.message.entity.RegionTable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovementListener implements Listener {

    private final MessageConfiguration messageConfiguration;
    private final List<Area> areas = new ArrayList<>();

    public MovementListener(final MessageConfiguration messageConfiguration) {
        this.messageConfiguration = messageConfiguration;
    }

    private void init() {
        areas.clear();

        try (final Connection connection = RegionMessage.getInstance().getHikariDataSource()) {
            final ResultSet resultSet = connection.prepareStatement("SELECT id, world, from_x, from_y, from_z, to_x, to_y, to_z FROM region_manager_region")
                    .executeQuery();

            while (resultSet.next()) {
                final RegionTable regionTable = new RegionTable();
                regionTable.setId(resultSet.getInt("id"));
                regionTable.setWorld(resultSet.getString("world"));

                regionTable.setFromY(resultSet.getDouble("from_x"));
                regionTable.setFromY(resultSet.getDouble("from_y"));
                regionTable.setFromZ(resultSet.getDouble("from_z"));

                regionTable.setToX(resultSet.getDouble("to_x"));
                regionTable.setToY(resultSet.getDouble("to_y"));
                regionTable.setToZ(resultSet.getDouble("to_z"));

                final Area area = new Area(regionTable.getFrom(), regionTable.getTo());
                this.areas.add(area);
            }

        } catch (SQLException exception) {
            System.err.printf("Error while trying to get content from database! %s%n", exception.getMessage());
        }
    }

    @EventHandler
    public void onMove(final PlayerMoveEvent event) {
        if (event.getFrom().getBlock().equals(event.getTo().getBlock())) return;

        for (final Area area : this.areas) {
            if(area.isInArea(event.getFrom())) {
                if(!area.isInArea(event.getTo())) {
                    event.getPlayer().sendMessage(messageConfiguration.getAdoption());
                }
            } else {
                if(area.isInArea(event.getTo())) {
                    event.getPlayer().sendMessage(messageConfiguration.getGreeting());
                }
            }
        }

    }

}
