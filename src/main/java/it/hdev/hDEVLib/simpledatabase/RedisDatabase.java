package it.hdev.hDEVLib.simpledatabase;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.io.Serializable;

public class RedisDatabase extends AbstractDatabase {
    private static final Logger logger = LoggerFactory.getLogger(RedisDatabase.class);
    private final JedisPool jedisPool;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public RedisDatabase(String host, int port) {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(10);
        poolConfig.setMaxIdle(5);
        poolConfig.setMinIdle(1);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        this.jedisPool = new JedisPool(poolConfig, host, port);
        logger.info("JedisPool initialized for Redis at {}:{}", host, port);
    }

    @Override
    protected boolean establishConnection() throws DatabaseException {
        try (var jedis = jedisPool.getResource()) {
            if ("PONG".equals(jedis.ping())) {
                logger.info("Connected to Redis Database.");
                return true;
            } else {
                logger.error("Failed to ping Redis server.");
                return false;
            }
        } catch (JedisException e) {
            logger.error("Failed to connect to Redis Database.", e);
            throw new DatabaseException("Failed to connect to Redis Database.", e);
        }
    }

    @Override
    protected boolean closeConnection() throws DatabaseException {
        try {
            jedisPool.close();
            logger.info("JedisPool closed.");
            return true;
        } catch (Exception e) {
            logger.error("Failed to close JedisPool.", e);
            throw new DatabaseException("Failed to disconnect from Redis Database.", e);
        }
    }

    @Override
    public void saveData(String table, String key, Serializable serializable) throws DatabaseException {
        String redisKey = table + ":" + key;
        try (var jedis = jedisPool.getResource()) {
            String jsonData = objectMapper.writeValueAsString(serializable);
            jedis.set(redisKey, jsonData);
            logger.info("Data saved to Redis with key: {}", redisKey);
        } catch (Exception e) {
            logger.error("Failed to save data to Redis.", e);
            throw new DatabaseException("Failed to save data to Redis.", e);
        }
    }

    @Override
    public Object getData(String table, String key) throws DatabaseException {
        String redisKey = table + ":" + key;
        try (var jedis = jedisPool.getResource()) {
            String jsonData = jedis.get(redisKey);
            return jsonData == null ? null : objectMapper.readValue(jsonData, Object.class);
        } catch (Exception e) {
            logger.error("Failed to retrieve data from Redis.", e);
            throw new DatabaseException("Failed to retrieve data from Redis.", e);
        }
    }
}
