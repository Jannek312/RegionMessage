package dev.jannek.rgion.message;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.jannek.rgion.message.entity.MessageConfiguration;
import dev.jannek.rgion.message.listener.MovementListener;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

public class RegionMessage extends JavaPlugin {

    private static RegionMessage instance;

    private static final String MESSAGE_FILE = "messages.yml";
    private HikariDataSource hikariDataSource;

    @Override
    public void onLoad() {
        instance = this;
        super.onLoad();
        init();
    }

    @Override
    public void onEnable() {
        final PluginManager manager = getServer().getPluginManager();
        manager.registerEvents(new MovementListener(getMessageConfiguration()), this);

    }

    public static RegionMessage getInstance() {
        return instance;
    }

    public Connection getHikariDataSource() throws SQLException {
        return hikariDataSource.getConnection();
    }

    private void init() {
        initSQL();
    }

    private void initSQL() {
        final ConfigurationSection section = getConfig().getConfigurationSection("sql");
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(section.getString("jdbc"));
        hikariConfig.setUsername(section.getString("username"));
        hikariConfig.setPassword(section.getString("password"));

        this.hikariDataSource =  new HikariDataSource(hikariConfig);
    }

    private MessageConfiguration getMessageConfiguration() {
        final File file = new File(MESSAGE_FILE);
        final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
        final String greeting = configuration.getString("greeting");
        final String adoption = configuration.getString("adoption");
        return new MessageConfiguration(greeting, adoption);
    }
}
