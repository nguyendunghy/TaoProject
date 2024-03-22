package com.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.PropertyUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class RedisManager {
    private static final Logger logger = LogManager.getLogger(RedisManager.class);

    private static JedisPool pool = new JedisPool("127.0.0.1", 6379);
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static String set(int db, String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            jedis.select(db);
            return jedis.set(key, value);
        }
    }

    public static String get(int db, String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.select(db);
            return jedis.get(key);
        }
    }

    public static boolean exist(int db, String key) {
        try (Jedis jedis = pool.getResource()) {
            jedis.select(db);
            return jedis.exists(key);
        }
    }

    public static void load(String filePath) {
        logger.info("start load data "+ filePath);
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        int numDb = Integer.parseInt(PropertyUtils.getProperty("redis.db.number"));
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Jedis jedis = pool.getResource()) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                DataEntity entity = objectMapper.readValue(line, DataEntity.class);
                //logger.info("entity.getText: " + entity.getText());
                String sha256hex = DigestUtils.sha256Hex(entity.getText());
                int db = Math.abs(sha256hex.hashCode()) % numDb;
                jedis.select(db);
                jedis.set(sha256hex, "");
                count++;
                logger.info("load data from file: " + file + " :line: " + count + " :db: " + db + " :sha256: " + sha256hex);

            }
            logger.info("load data to redis success:" + filePath);
        } catch (Exception e) {
            logger.error("load data error: ", e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Time loading: " + (end - start));
        }
    }

}
