package it.hdev.hDEVLib.simpledatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class SQLDatabase extends AbstractDatabase {
    protected HikariDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(SQLDatabase.class);

    public SQLDatabase(String url, String username, String password) {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        this.dataSource = new HikariDataSource(config);
        logger.info("HikariCP DataSource initialized.");
    }

    @Override
    protected boolean establishConnection() throws DatabaseException {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(2);
        } catch (SQLException e) {
            logger.error("Failed to establish SQL connection.", e);
            throw new DatabaseException("Failed to establish SQL connection.", e);
        }
    }

    @Override
    protected boolean closeConnection() throws DatabaseException {
        try {
            dataSource.close();
            logger.info("SQL connection pool closed.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to close SQL connection pool.", e);
            throw new DatabaseException("Failed to close SQL connection pool.", e);
        }
    }

    @Override
    public void saveData(String table, String key, Serializable serializable) throws DatabaseException {
        String query = "INSERT INTO " + table + " (key, value) VALUES (?, ?) ON DUPLICATE KEY UPDATE value = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, key);
            stmt.setObject(2, serializable);
            stmt.setObject(3, serializable);
            stmt.executeUpdate();
        } catch (SQLException e) {
            logger.error("Failed to save data to SQL Database.", e);
            throw new DatabaseException("Failed to save data to SQL Database.", e);
        }
    }

    @Override
    public Object getData(String table, String key) throws DatabaseException {
        String query = "SELECT value FROM " + table + " WHERE key = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, key);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getObject("value") : null;
            }
        } catch (SQLException e) {
            logger.error("Failed to retrieve data from SQL Database.", e);
            throw new DatabaseException("Failed to retrieve data from SQL Database.", e);
        }
    }
}
