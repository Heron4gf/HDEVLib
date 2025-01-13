// src/main/java/it/hdev/hDEVLib/simpledatabase/SQLiteDatabase.java
package it.hdev.hDEVLib.simpledatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteDatabase extends SQLDatabase {
    private static final Logger logger = LoggerFactory.getLogger(SQLiteDatabase.class);

    /**
     * Constructs a SQLiteDatabase instance with the specified file path.
     *
     * @param filePath the path to the SQLite database file
     */
    public SQLiteDatabase(String filePath) {
        super("jdbc:sqlite:" + filePath, "", "");
        try {
            Class.forName("org.sqlite.JDBC");
            logger.info("SQLite JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            logger.error("SQLite JDBC Driver not found.", e);
            throw new RuntimeException("SQLite JDBC Driver not found.", e);
        }
    }

    @Override
    public void saveData(String table, String key, Serializable serializable) throws DatabaseException {
        String sql = "INSERT INTO " + table + " (key, value) VALUES (?, ?) " +
                "ON CONFLICT(key) DO UPDATE SET value = excluded.value";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, key);
            stmt.setObject(2, serializable);
            int rowsAffected = stmt.executeUpdate();
            logger.info("Data saved successfully in table: {}. Rows affected: {}", table, rowsAffected);
        } catch (SQLException e) {
            logger.error("Failed to save data to SQLite Database.", e);
            throw new DatabaseException("Failed to save data to SQLite Database.", e);
        }
    }
}
