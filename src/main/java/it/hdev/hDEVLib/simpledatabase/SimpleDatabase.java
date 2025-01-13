// src/main/java/it/hdev/hDEVLib/simpledatabase/SimpleDatabase.java
package it.hdev.hDEVLib.simpledatabase;

import java.io.Serializable;

public interface SimpleDatabase {
    void saveData(String table, String key, Serializable serializable) throws DatabaseException;

    Object getData(String table, String key) throws DatabaseException;

    boolean connect() throws DatabaseException;

    boolean disconnect() throws DatabaseException;
}
