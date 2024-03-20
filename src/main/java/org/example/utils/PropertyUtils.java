package org.example.utils;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.utils.Constants.MAX_REGISTER_PRICE_MAP;
import static org.example.utils.Constants.PROJECT_LOCATION;

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

    public static String getPriceRegisterScriptPath() throws Exception {
        String path = getProjectLocation() + getProperty("script.get.register.price.path");
        return path;
    }

    public static String registerSubnetScriptPath() throws Exception {
        String path = getProjectLocation() + getProperty("script.register.path");
        return path;
    }

    public static String checkBalanceScriptPath() throws Exception {
        String path = getProjectLocation() + getProperty("script.check.balance.path");
        return path;
    }

    public static String getProjectLocation() {
        try {
            if(PROJECT_LOCATION != null && !PROJECT_LOCATION.trim().isEmpty()){
                return PROJECT_LOCATION;
            }
            String ip = SystemUtils.getIpAddress();
            String key = ip + ".project.location";
            PROJECT_LOCATION = getProperty(key);
            return PROJECT_LOCATION;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HashMap<String, Double> buildMaxRegisterPriceMap() {
        try {
            Set<String> filteredKeys = properties.stringPropertyNames().stream()
                    .filter(key -> key.startsWith("max.register.price"))
                    .collect(Collectors.toSet());

            HashMap<String, Double> maxRegisterPriceMap = new HashMap<>();
            for (String key : filteredKeys) {
                maxRegisterPriceMap.put(key.replaceAll("max.register.price.",""), Double.valueOf(properties.getProperty(key)));
            }
            return maxRegisterPriceMap;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
