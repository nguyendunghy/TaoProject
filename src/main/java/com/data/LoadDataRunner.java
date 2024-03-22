package com.data;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.utils.PropertyUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.GZIPInputStream;

public class LoadDataRunner implements Runnable {

    //File name template: c4-train.00000-of-01024.json.gz,  c4-train.00001-of-01024.json.gz
    private static String SOURCE_FILE_TEMPLATE = "c4-train.${index}-of-01024.json.gz";
    private static String TARGET_FILE_TEMPLATE = "c4-train.${index}-of-01024.json";
    private static final Logger logger = LogManager.getLogger(LoadDataRunner.class);

    private int startFileIndex;

    private int endFileIndex;

    public LoadDataRunner() {}

    public LoadDataRunner(int startFileIndex, int endFileIndex) {
        this.startFileIndex = startFileIndex;
        this.endFileIndex = endFileIndex;
    }


    public void run() {
        for (int fileIndex = startFileIndex; fileIndex < endFileIndex; fileIndex++) {
            String sourceFileName = SOURCE_FILE_TEMPLATE.replace("${index}", buildIndex(fileIndex));
            String extractedFileName = TARGET_FILE_TEMPLATE.replace("${index}", buildIndex(fileIndex));

            String sourceFilePath = PropertyUtils.getProperty("c4.source.folder.folder") + sourceFileName;
            String targetFilePath = PropertyUtils.getProperty("c4.extract.folder=") + extractedFileName;
            extract(sourceFilePath, targetFilePath);
            RedisManager.load(targetFilePath);
            deleteFile(targetFilePath);
            logger.info("load data to redis success with sourceFileName = " + sourceFileName);
        }
    }

    public static void deleteFile(String targetFilePath) {
        //delete extracted file
        try {
            Path fileToDeletePath = Paths.get(targetFilePath);
            Files.delete(fileToDeletePath);
            logger.info("delete file success: " + targetFilePath);
        } catch (Exception e) {
            logger.error("delete file failed:", e);
        }
    }

    public static void extract(String gZipFile, String targetFile) {
        try (
                FileInputStream fis = new FileInputStream(gZipFile);
                GZIPInputStream gis = new GZIPInputStream(fis);
                FileOutputStream fos = new FileOutputStream(targetFile)) {
            byte[] buffer = new byte[2048];
            int len;
            while ((len = gis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
        } catch (Exception e) {
            logger.error("extract data failed:", e);
        }
    }


    private static String buildIndex(int a) {
        int len = String.valueOf(a).length();
        switch (len) {
            case 1:
                return "0000" + a;
            case 2:
                return "000" + a;
            case 3:
                return "00" + a;
            case 4:
                return "0" + a;
            default:
                return String.valueOf(a);
        }
    }

    public static void main(String[] args) {
        extract("/Users/nannan/Downloads/c4-train.00000-of-01024.json.gz",
                "/Users/nannan/Downloads/c4-train.00000-of-01024.json");

//        deleteFile("/Users/nannan/Downloads/c4-train.00000-of-01024.json");
    }
}
