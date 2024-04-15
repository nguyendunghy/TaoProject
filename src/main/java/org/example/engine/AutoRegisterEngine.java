package org.example.engine;

import lombok.extern.slf4j.Slf4j;
import org.example.telegram.TeleGramMessageSender;
import org.example.utils.PropertyUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.script.RunShellScript.register;
import static org.example.utils.Constants.MAX_REGISTER_PRICE_MAP;

@Slf4j
public class AutoRegisterEngine {

    private String subnetId;

    private List<String> registeredHotKeyList = new ArrayList<>();

    public AutoRegisterEngine(String subnetId) {
        this.subnetId = subnetId;
    }

    public static void main(String[] args) throws Exception {

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
                String hotkey = getHotKey();
                if(hotkey == null || hotkey.isEmpty()){
                    log.info("No hotkey to register!!!");
                    break;
                }

                Double maxRegisterPrice = MAX_REGISTER_PRICE_MAP.get(subnetId);
                String registerSubnetScriptPath = PropertyUtils.registerSubnetScriptPath();
                String output = register(registerSubnetScriptPath, subnetId, maxRegisterPrice.toString(), hotkey);
                log.info(output);
                if (output.contains("Registered") && !output.contains("Already Registered")) {
                    registeredHotKeyList.add(hotkey);
                    String telegramChannelId = PropertyUtils.getProperty("subnet.register.price.channel.chat.id");
                    TeleGramMessageSender.sendMessage(telegramChannelId, "Registered successfully subnet:" + subnetId + " hotkey: " + hotkey);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }


    private String getHotKey(){
        String hotkeyValue = PropertyUtils.getProperty("register.hotkey");
        List<String> hotKeyList = Arrays.asList(hotkeyValue.split(","));
        hotKeyList.removeAll(registeredHotKeyList);
        if(hotKeyList.isEmpty()){
            return "";
        }
        return hotKeyList.get(0);
    }


}
