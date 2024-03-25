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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
                long timeCheckExistStart = System.nanoTime();
                jedis.select(db);
                boolean exists = jedis.exists(sha256hex);
                long timeCheckExistEnd = System.nanoTime();
                logger.info("Time process in redis: " + (timeCheckExistEnd - timeCheckExistStart));
                count++;
                logger.info("data line: " + count + " :db: " + db + " :sha256: " + sha256hex + " :exists: " + exists);

            }
            logger.info("end test data success:" + filePath);
        } catch (Exception e) {
            logger.error("test data error: ", e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Time testing: " + (end - start));
        }
    }

    public static void countWords(){
        String filePath =  PropertyUtils.getProperty("c4.file.path");
        logger.info("start test data: " + filePath);
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            long wordCount = 0;
            long lineCount = 0;
            long minWord = Long.MAX_VALUE;
            long maxWord = Long.MIN_VALUE;
            while ((line = reader.readLine()) != null) {
                DataEntity entity = objectMapper.readValue(line, DataEntity.class);
                //logger.info("entity.getText: " + entity.getText());
                long timeCheckExistStart = System.nanoTime();
                String text = entity.getText();
                String[] words = text.split(" ");
                minWord = minWord < words.length ? minWord:words.length;
                maxWord = maxWord > words.length ? maxWord: words.length;
                wordCount += words.length;
                lineCount ++;
                long timeCheckExistEnd = System.nanoTime();
                logger.info("Time process in count function: " + (timeCheckExistEnd - timeCheckExistStart));
            }
            logger.info("wordCount: " + wordCount);
            logger.info("lineCount: " + lineCount);
            logger.info("Average word: " + (wordCount/lineCount));
            logger.info("minWord: " + minWord);
            logger.info("maxWord: " + maxWord);
            logger.info("end count data success:" + filePath);
        } catch (Exception e) {
            logger.error("test data error: ", e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Time count: " + (end - start));
        }
    }

    public static void countSentence(){
        String filePath =  PropertyUtils.getProperty("c4.file.path");
        logger.info("start test data: " + filePath);
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            long sentenceCount = 0;
            long wordCount = 0;
            long recordCount = 0;
            long minSentence = Long.MAX_VALUE;
            long maxSentence = Long.MIN_VALUE;

            long maxWordPerSentence = Long.MIN_VALUE;
            long minWordPerSentence = Long.MAX_VALUE;
            while ((line = reader.readLine()) != null) {
                DataEntity entity = objectMapper.readValue(line, DataEntity.class);
                long timeCheckExistStart = System.nanoTime();
                String text = entity.getText();
                String[] sentences = text.split("(\\.|\\?|\\!)");
                for(String sentence: sentences){
                    String[] words = sentence.split(" ");
                    maxWordPerSentence = maxWordPerSentence > words.length ? maxWordPerSentence: words.length;
                    minWordPerSentence = minWordPerSentence < words.length ? minWordPerSentence: words.length;
                    wordCount += words.length;
                }

                minSentence = minSentence < sentences.length ? minSentence:sentences.length;
                maxSentence = maxSentence > sentences.length ? maxSentence: sentences.length;
                sentenceCount += sentences.length;
                recordCount ++;
                long timeCheckExistEnd = System.nanoTime();
                //logger.info("Time process in count function: " + (timeCheckExistEnd - timeCheckExistStart));
            }

            logger.info("sentenceCount: " + sentenceCount);

            logger.info("lineCount: " + recordCount);
            logger.info("Average sentence/record: " + (sentenceCount/recordCount));
            logger.info("minSentence: " + minSentence);
            logger.info("maxSentence: " + maxSentence);

            logger.info("wordCount: " + wordCount);
            logger.info("Average word/sentence: " + wordCount/sentenceCount);

            logger.info("end count data success:" + filePath);
        } catch (Exception e) {
            logger.error("test data error: ", e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Time count: " + (end - start));
        }
    }


    public static void testAll() {
        String filePath = PropertyUtils.getProjectLocation() + PropertyUtils.getProperty("c4.validator.data");
        logger.info("start test data: " + filePath);
        long start = System.currentTimeMillis();
        File file = new File(filePath);
        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             Jedis jedis = RedisManager.getPool().getResource()) {
            String line;
            int count = 0;
            List<String> listSha256 = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                DataEntity entity = objectMapper.readValue(line, DataEntity.class);
                //logger.info("entity.getText: " + entity.getText());
                String sha256hex = DigestUtils.sha256Hex(entity.getText());
                listSha256.add(sha256hex);
            }
            List<Boolean> listExists = checkDataExists(jedis, listSha256);
            logger.info("listExists: " + listExists);
            logger.info("end test data success:" + filePath);
        } catch (Exception e) {
            logger.error("test data error: ", e);
        } finally {
            long end = System.currentTimeMillis();
            logger.info("Time testing: " + (end - start));
        }
    }

    private static List<Boolean> checkDataExists(Jedis jedis, List<String> listSha256) {
        int numDb = Integer.parseInt(PropertyUtils.getProperty("redis.db.number"));
        List<Boolean> results = new ArrayList<>();
        long start = System.nanoTime();

        for (String sha256hex : listSha256) {
            int db = Math.abs(sha256hex.hashCode()) % numDb;
            logger.info("hash value is = " + sha256hex + " and db= " + db);
            long timeCheckExistStart = System.nanoTime();
            jedis.select(db);
            boolean exists = jedis.exists(sha256hex);
            long timeCheckExistEnd = System.nanoTime();
            logger.info("time processing hash: " + sha256hex + ":" + (timeCheckExistEnd - timeCheckExistStart));

            results.add(exists);
        }

        long end = System.nanoTime();
        logger.info("Time checkDataExists nano: " + (end - start));
        return results;
    }

    public static void main(String[] args) {
        String text = "abc.def?mnp!ksg.exy";
        String[] sentences = text.split("(\\.|\\?|\\!)");
        System.out.println(Arrays.asList(sentences));
    }

}
