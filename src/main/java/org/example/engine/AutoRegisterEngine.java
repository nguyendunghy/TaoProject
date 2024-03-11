package org.example.engine;

import org.example.utils.Constants;
import org.example.script.RunShellScript;
import org.example.utils.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.utils.Constants.MAX_REGISTER_PRICE_MAP;
import static org.example.script.RunShellScript.register;

public class AutoRegisterEngine {
    private int counter = 0;

    private String subnetId;

    public AutoRegisterEngine(String subnetId) {
        this.subnetId = subnetId;
    }

    public static void main(String[] args) throws Exception {
        startRunningAutoRegisterEngine(Arrays.asList("26"));
    }

    public static void startRunningAutoRegisterEngine(List<String> listSubnetId) {
        List<Thread> listThread = new ArrayList<>();

        listSubnetId.forEach(subnetId ->
        {
            Thread thread = new Thread(() -> {
                AutoRegisterEngine engine = new AutoRegisterEngine(subnetId);
                engine.run();
            });
            listThread.add(thread);
        });

        for (Thread thread : listThread) {
            thread.start();
        }
    }

    public void run() {
        while (true) {
            try {
                String scriptOutput = RunShellScript.run(Constants.GET_REGISTER_PRICE_SCRIPT_PATH, subnetId);
                Double currentPrice = Double.parseDouble(StringUtils.extractPrice(scriptOutput));
                if (currentPrice.compareTo(MAX_REGISTER_PRICE_MAP.get(subnetId)) < 0) {
                    String hotkey = genPublicKey();
                    String output = register(Constants.REGISTER_SUBNET_SCRIPT_PATH, subnetId, hotkey);
                    System.out.println(output);
                } else {
                    System.out.println("Price register subnet " + subnetId + " is too expensive: " + currentPrice);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private String genPublicKey() {
        return "jackie_hotkey_22";
    }


//    private String genPublicKey() {
//        return "jackie_hotkey_" + (21 + counter++ % 2);
//    }

}
