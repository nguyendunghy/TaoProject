package org.example.engine;

import org.example.utils.Constants;
import org.example.utils.PropertyUtils;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args == null || args.length < 2) {
            System.out.println("Missing argument, the first argument is AUTO_REGISTER or GET_PRICE. The second argument is property file path");
        }
        Constants.CONFIG_FILE = args[1];
        String[] subnetIdArray;
        switch (args[0]) {
            case "GET_PRICE":
                subnetIdArray = PropertyUtils.getProperty("get.price.subnetId").split(",");
                GetPriceEngine.startRunningGetPrice(Arrays.asList(subnetIdArray));
                break;
            case "AUTO_REGISTER":
                subnetIdArray = PropertyUtils.getProperty("register.subnetId").split(",");
                AutoRegisterEngine.startRunningAutoRegisterEngine(Arrays.asList(subnetIdArray));
                break;
            default:
                break;
        }

    }
}
