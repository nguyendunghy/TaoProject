package org.example.engine;

import org.example.telegram.TeleGramMessageSender;
import org.example.utils.Constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.script.RunShellScript.register;
import static org.example.utils.Constants.MAX_REGISTER_PRICE_MAP;
import static org.example.utils.Constants.SUBNET_REGISTER_PRICE_CHANNEL_CHAT_ID;

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
                String hotkey = genPublicKey();
                String output = register(Constants.REGISTER_SUBNET_SCRIPT_PATH, subnetId, MAX_REGISTER_PRICE_MAP.get(subnetId).toString(),hotkey);
                System.out.println(output);
                if(output.contains("Registered") && !output.contains("Already Registered")){
                    TeleGramMessageSender.sendMessage(SUBNET_REGISTER_PRICE_CHANNEL_CHAT_ID, "Registered successfully subnet:" + subnetId);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private String genPublicKey() {
        return "jackie_hotkey_23";
    }


//    private String genPublicKey() {
//        return "jackie_hotkey_" + (21 + counter++ % 2);
//    }

}
