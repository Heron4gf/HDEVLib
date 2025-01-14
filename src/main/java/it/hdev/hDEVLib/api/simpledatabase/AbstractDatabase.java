package it.hdev.hDEVLib.api.simpledatabase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDatabase implements SimpleDatabase {
    protected static final Logger logger = LoggerFactory.getLogger(AbstractDatabase.class);
    protected volatile boolean isConnected = false;

    @Override
    public synchronized boolean connect() throws DatabaseException {
        if (isConnected) {
            logger.warn("Attempted to connect, but already connected.");
            throw new DatabaseException("Already connected to the database.");
        }
        isConnected = establishConnection();
        if (isConnected) {
            logger.info("Successfully connected to the database.");
        }
        return isConnected;
    }

    @Override
    public synchronized boolean disconnect() throws DatabaseException {
        if (!isConnected) {
            logger.warn("Attempted to disconnect, but no active connection exists.");
            throw new DatabaseException("No active database connection to disconnect.");
        }
        isConnected = !closeConnection();
        if (!isConnected) {
            logger.info("Successfully disconnected from the database.");
        }
        return !isConnected;
    }

    protected abstract boolean establishConnection() throws DatabaseException;

    protected abstract boolean closeConnection() throws DatabaseException;
}
