package org.example.engine;

import org.example.utils.Constants;
import org.example.script.RunShellScript;
import org.example.utils.StringUtils;
import org.example.telegram.TeleGramMessageSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class GetPriceEngine {
    private static String SUBNET_REGISTER_PRICE_CHANNEL_CHAT_ID = "@BittensorSubnetRegisterPrice";

    private static Double REGISTER_PRICE_THRESHOLD = 10D;

    private static long MAX_TIME_NO_SEND_MESSAGE = 30;//30 min


    private String previousPrice = null;
    private String currentPrice = null;

    private String subnetId;

    private long previousSendTime = System.currentTimeMillis();

    public GetPriceEngine(String subnetId) {
        this.subnetId = subnetId;
    }

    public static void main(String[] args) {
        List<String> listSubnetId = Arrays.asList(
                "32",
                "26",
                "1"
        );
        startRunningGetPrice(listSubnetId);

    }

    public static void startRunningGetPrice(List<String> listSubnetId) {
        List<Thread> listThread = new ArrayList<>();

        listSubnetId.forEach(subnetId ->
        {
            Thread thread = new Thread(() -> {
                GetPriceEngine engine = new GetPriceEngine(subnetId);
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
                currentPrice = StringUtils.extractPrice(scriptOutput);
                String message = StringUtils.priceMessage(subnetId, currentPrice);
                System.out.println(message);
                if (shouldSendMessage(currentPrice)) {
                    TeleGramMessageSender.sendMessage(SUBNET_REGISTER_PRICE_CHANNEL_CHAT_ID, message);
                    previousSendTime = System.currentTimeMillis();
                }
                previousPrice = currentPrice;

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean shouldSendMessage(String price) {
        //send message if no message is sent in 30min
        if (System.currentTimeMillis() - previousSendTime > MAX_TIME_NO_SEND_MESSAGE * 60 * 1000) {
            return true;
        }

        //Don't send too much same message
        if (previousPrice != null) {
            if (previousPrice.compareTo(price) == 0) {
                return false;
            }
        }

        //Register Price must less than threshold
        Double doublePrice = Double.parseDouble(price);
        return doublePrice.compareTo(REGISTER_PRICE_THRESHOLD) < 0;
    }


}