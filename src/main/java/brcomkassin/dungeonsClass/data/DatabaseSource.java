package brcomkassin.dungeonsClass.data;

import brcomkassin.dungeonsClass.DungeonsClassPlugin;
import brcomkassin.dungeonsClass.utils.Config;
import brcomkassin.dungeonsClass.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record DatabaseSource(
        @NotNull String driver,
        @NotNull String url,
        @NotNull String username,
        @NotNull String password) {

    public static DatabaseSource create(String driver, String url, String username, String password) {
        return new DatabaseSource(
                Objects.requireNonNull(driver),
                Objects.requireNonNull(url),
                Objects.requireNonNull(username),
                Objects.requireNonNull(password));
    }

    public static DatabaseSource create() {
        Config config = new Config(DungeonsClassPlugin.getInstance(), "database.yml");
        ConfigManager.add(config);
        ConfigurationSection section =
                Objects.requireNonNull(config.getConfigurationSection("MySQL"), "MySQL section not found");

        String host = section.getString("Host", "127.0.0.1");
        String database = section.getString("Database", "teste");
        String username = section.getString("Username", "root");
        String password = section.getString("Password", "");
        int port = section.getInt("Port", 3306);

        String url = "jdbc:mysql://" + host + ":" + port + "/" + database;

        return create(
                "com.mysql.cj.jdbc.Driver",
                url,
                username,
                password);
    }

    public HikariDataSource getSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setIdleTimeout(30000);
        config.setMaxLifetime(1800000);
        return new HikariDataSource(config);
    }

}
