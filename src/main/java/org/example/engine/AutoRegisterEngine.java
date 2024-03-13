package org.example.engine;

import org.example.telegram.TeleGramMessageSender;
import org.example.utils.PropertyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.script.RunShellScript.register;
import static org.example.utils.Constants.MAX_REGISTER_PRICE_MAP;

public class AutoRegisterEngine {

    private String subnetId;

    public AutoRegisterEngine(String subnetId) {
        this.subnetId = subnetId;
    }

    public static void main(String[] args) throws Exception {
        String[] subnetIdArray = PropertyUtils.getProperty("register.subnetId").split(",");
        startRunningAutoRegisterEngine(Arrays.asList(subnetIdArray));
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
                String hotkey = PropertyUtils.getProperty("register.hotkey");
                Double maxRegisterPrice = MAX_REGISTER_PRICE_MAP.get(subnetId);
                String registerSubnetScriptPath = PropertyUtils.getProperty("script.register.path");
                String output = register(registerSubnetScriptPath, subnetId, maxRegisterPrice.toString(), hotkey);
                System.out.println(output);
                if (output.contains("Registered") && !output.contains("Already Registered")) {
                    String telegramChannelId = PropertyUtils.getProperty("subnet.register.price.channel.chat.id");
                    TeleGramMessageSender.sendMessage(telegramChannelId, "Registered successfully subnet:" + subnetId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private String genPublicKey() {
        return "jackie_hotkey_24";
    }


}
