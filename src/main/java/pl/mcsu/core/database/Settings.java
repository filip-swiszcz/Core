package pl.mcsu.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import pl.mcsu.core.Core;

public class Settings {

    public final HikariDataSource hikariDataSource = new HikariDataSource(getHikariConfig());

    private HikariConfig getHikariConfig() {
        String name = Core.getInstance().getMySQLName();
        String user = Core.getInstance().getMySQLUser();
        String password = Core.getInstance().getMySQLPassword();
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/" + name);
        hikariConfig.setUsername(user);
        hikariConfig.setPassword(password);
        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "250");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        hikariConfig.addDataSourceProperty("useServerPrepStmts", "true");
        return hikariConfig;
    }

}
