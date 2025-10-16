package com.example.weeklyplanner.persistence.db;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import java.sql.SQLException;

public final class DatabaseConfigurator {
    private static final String ENV = "DB_URL";
    private static final String DEFAULT_URL = "jdbc:sqlite:planner.db";
    private static final String PREFIX = "jdbc:sqlite:";
    private static volatile JdbcConnectionSource connectionSource;
    private static final Object LOCK = new Object();

    private DatabaseConfigurator() {}

    public static JdbcConnectionSource getDatabaseConnectionSource() throws SQLException {
        String database_url = resolveUrl();
        if (connectionSource == null) {
            return new JdbcConnectionSource(database_url);
        }
        return connectionSource;
    }

    private static String resolveUrl() {
        String env = System.getenv(ENV);
        if (env == null || env.isBlank()) return DEFAULT_URL;
        return env.trim();
    }
}
