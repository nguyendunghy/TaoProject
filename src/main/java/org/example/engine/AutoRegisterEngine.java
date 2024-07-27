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

    private List<String> registeredHotKeyList = new ArrayList<>();

    private List<Long> timeRegisterKeyList = new ArrayList<>();

    public AutoRegisterEngine(String subnetId) {
        this.subnetId = subnetId;
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
                String coldKey = getColdKey();
                String hotkey = getHotKey();
                String password = getPassword();
                if(hotkey == null || hotkey.isEmpty()){
                    System.out.println("No hotkey to register!!!");
                    break;
                }

                if(!checkReachingMaxRegisterKeyPerRound()){
                    System.out.println("Reaching max number of register key!");
                    Thread.sleep(1000);
                    continue;
                }

                System.out.println("start register coldKey: " + coldKey + ", hotKey: " + hotkey);

                Double maxRegisterPrice = MAX_REGISTER_PRICE_MAP.get(subnetId);
                String registerSubnetScriptPath = PropertyUtils.registerSubnetScriptPath();
                String output = register(registerSubnetScriptPath, subnetId, maxRegisterPrice.toString(), hotkey,coldKey,password);
                System.out.println(output);
                if (output.contains("Registered") && !output.contains("Already Registered")) {
                    registeredHotKeyList.add(hotkey);
                    timeRegisterKeyList.add(System.currentTimeMillis());
                    String price = extractPrice(output);
                    String telegramChannelId = PropertyUtils.getProperty("subnet.register.price.channel.chat.id");
                    TeleGramMessageSender.sendMessage(telegramChannelId, "Registered successfully subnet:" + subnetId
                            + " coldKey: " + coldKey +  ", hotkey: " + hotkey + " with price: " + price);
                } else if(output.contains("Already Registered")){
                    registeredHotKeyList.add(hotkey);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                System.out.println("Finish register !");
            }
        }
    }
    private String getColdKey(){
        return PropertyUtils.getProperty("register.coldkey");
    }

    private String getPassword(){
        return PropertyUtils.getProperty("register.coldkey.password");
    }

    private int getMaxKeysRegisterPerRound(){
        return Integer.parseInt(PropertyUtils.getProperty("max.key.register.per.round"));
    }

    public  boolean checkReachingMaxRegisterKeyPerRound(){
        Long current = System.currentTimeMillis();
        int count = 0;
        for(int i=timeRegisterKeyList.size()-1; i>=0 ;i--){
            Long timeRegister = timeRegisterKeyList.get(i);
            if(current - timeRegister < 60 * 60 * 1000){
                count++;
            }
        }

        return getMaxKeysRegisterPerRound() > count;
    }

    private String getHotKey(){
        String hotkeyValue = PropertyUtils.getProperty("register.hotkey");
        List<String> temp_list = Arrays.asList(hotkeyValue.split(","));
        List<String> hotKeyList = new ArrayList<>();
        for (String key: temp_list){
            if(!registeredHotKeyList.contains(key)){
                hotKeyList.add(key);
            }
        }
        if(hotKeyList.isEmpty()){
            return "";
        }
        return hotKeyList.get(0);
    }

    public static String extractPrice(String output){
        try{
            String[] lines = output.split("\\n");
            for(String line : lines){
                if(line.endsWith("y") && line.contains("Recycle")
                        && line.contains("τ") && line.contains("to register on subnet:")){
                    String price = line.split("τ")[1].split("to")[0].trim();
                    return price;
                }
            }

        } catch (Exception ex){
            ex.printStackTrace();
        }
        return "N/A";
    }

    public static void main(String[] args) throws Exception {
        String output = "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Checking Account on \u001B[1msubnet:10\u001B[0m...\n" +
                "\u001B[?25h\n" +
                "\u001B[1A\u001B[2KRecycle τ0.282747870 to register on subnet:10? \u001B[1;35m[y/n]\u001B[0m: y\n" +
                "\u001B[?25l\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\uD83D\uDCE1 Checking Balance\u001B[33m...\u001B[0m\n" +
                "\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2KBalance:\n" +
                "  \u001B[34mτ4.\u001B[0m\u001B[1;34m000000000\u001B[0m ➡ \u001B[32mτ3.\u001B[0m\u001B[1;32m717252130\u001B[0m\n" +
                "\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠧\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠇\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠏\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠋\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠙\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠹\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠸\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠼\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠴\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K✅ \u001B[32mRegistered\u001B[0m\n" +
                "\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[2K\u001B[32m⠦\u001B[0m \uD83D\uDCE1 Recycling TAO for Registration...\n" +
                "\u001B[?25h\n" +
                "\u001B[1A\u001B[2K\n" +
                "\n" +
                "09:07:58.834 [Thread-0] DEBUG org.springframework.web.client.RestTemplate - HTTP POST https://api.telegram.org/bot7036226782:AAHliZcXRdvCto5KhUyYqMDR2eiIQase7IA/sendMessage?chat_id=@BittensorSubnetRegisterPrice&text=Registered%20successfully%20subnet:10%20coldKey:%20tiger,%20hotkey:%20hk1\n" +
                "09:07:58.837 [Thread-0] DEBUG org.springframework.web.client.RestTemplate - Accept=[text/plain, application/json, application/*+json, */*]\n" +
                "09:08:00.064 [Thread-0] DEBUG org.springframework.web.client.RestTemplate - Response 200 OK\n" +
                "09:08:00.069 [Thread-0] DEBUG org.springframework.web.client.RestTemplate - Reading to [java.lang.String] as \"application/json\"\n" +
                "Finish register !\n" +
                "No hotkey to register!!!\n" +
                "Finish register !\n";
        System.out.println(extractPrice(output));
    }



}
