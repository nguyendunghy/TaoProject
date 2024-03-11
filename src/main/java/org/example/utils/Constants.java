package org.example.utils;

import java.util.HashMap;

public class Constants {
    public static String GET_REGISTER_PRICE_SCRIPT_PATH = "/Users/nannan/IdeaProjects/TaoProject/src/main/resources/runGetRegisterPrice.sh";
    public static String REGISTER_SUBNET_SCRIPT_PATH = "/Users/nannan/IdeaProjects/TaoProject/src/main/resources/runAutoRegister.sh";

    public static HashMap<String,Double> MAX_REGISTER_PRICE_MAP =  new HashMap<String, Double>() {{
        put("32", 0.06D);
        put("26", 0.3D);
        put("6", 0.01D);
        put("1", 0.06D);

    }};
}
