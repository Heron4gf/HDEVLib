package it.hdev.hDEVLib.simpledatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySQLDatabase extends SQLDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MySQLDatabase.class);

    public MySQLDatabase(String host, int port, String database, String username, String password) {
        super("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            logger.info("MySQL JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            logger.error("MySQL JDBC Driver not found.", e);
            throw new RuntimeException("MySQL JDBC Driver not found.", e);
        }
    }
}
