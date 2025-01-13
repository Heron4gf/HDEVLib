package it.hdev.hDEVLib.simpledatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MariaDatabase extends SQLDatabase {
    private static final Logger logger = LoggerFactory.getLogger(MariaDatabase.class);

    public MariaDatabase(String host, int port, String database, String username, String password) {
        super("jdbc:mariadb://" + host + ":" + port + "/" + database, username, password);
        try {
            Class.forName("org.mariadb.jdbc.Driver");
            logger.info("MariaDB JDBC Driver loaded.");
        } catch (ClassNotFoundException e) {
            logger.error("MariaDB JDBC Driver not found.", e);
            throw new RuntimeException("MariaDB JDBC Driver not found.", e);
        }
    }
}
