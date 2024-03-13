package org.example.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.utils.Constants.MAX_REGISTER_PRICE_MAP;

public class PropertyUtils {

    private static Properties properties = new Properties();

    static {
        loadProperties();
        MAX_REGISTER_PRICE_MAP = buildMaxRegisterPriceMap();
    }

    private static void loadProperties() {
        File file = new File(Constants.CONFIG_FILE);
        try (InputStream input = Files.newInputStream(file.toPath())) {
            properties.load(input);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    private static HashMap<String, Double> buildMaxRegisterPriceMap() {
        try {
            Set<String> filteredKeys = properties.stringPropertyNames().stream()
                    .filter(key -> key.startsWith("max.register.price"))
                    .collect(Collectors.toSet());

            HashMap<String, Double> maxRegisterPriceMap = new HashMap<>();
            for (String key : filteredKeys) {
                maxRegisterPriceMap.put(key, Double.valueOf(properties.getProperty(key)));
            }
            return maxRegisterPriceMap;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
