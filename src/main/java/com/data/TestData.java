package com.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.PropertyUtils;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class TestData {
    private static final Logger logger = LogManager.getLogger(TestData.class);

    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void test() {
        String filePath = PropertyUtils.getProjectLocation() + PropertyUtils.getProperty("c4.validator.data");
        logger.info("start test data: " + filePath);
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        int numDb = Integer.parseInt(PropertyUtils.getProperty("redis.db.number"));
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Jedis jedis = RedisManager.getPool().getResource()) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                DataEntity entity = objectMapper.readValue(line, DataEntity.class);
                //logger.info("entity.getText: " + entity.getText());
                String sha256hex = DigestUtils.sha256Hex(entity.getText());
                int db = Math.abs(sha256hex.hashCode()) % numDb;
                long timeCheckExistStart = System.currentTimeMillis();
                jedis.select(db);
                boolean exists = jedis.exists(sha256hex);
                long timeCheckExistEnd = System.currentTimeMillis();
                logger.info("Time process in redis: " + (timeCheckExistEnd - timeCheckExistStart));
                count++;
                logger.info("data line: " + count + " :db: " + db + " :sha256: " + sha256hex + " :exists: " + exists);

            }
            logger.info("end test data success:" + filePath);
        } catch (Exception e) {
            logger.error("test data error: ", e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Time loading: " + (end - start));
        }
    }
}
