package org.example.engine;

import lombok.extern.slf4j.Slf4j;
import org.example.script.RunShellScript;
import org.example.telegram.TeleGramMessageSender;
import org.example.utils.PropertyUtils;
import org.example.utils.StringUtils;

import java.util.ArrayList;
import java.util.List;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@Slf4j
public class GetPriceEngine {

    private static Double REGISTER_PRICE_THRESHOLD = 10D;


    private String previousPrice = null;
    private String currentPrice = null;

    private String subnetId;

    private long previousSendTime = System.currentTimeMillis();

    public GetPriceEngine(String subnetId) {
        this.subnetId = subnetId;
    }

    public static void main(String[] args) {


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
                String getRegisterPricePath = PropertyUtils.getPriceRegisterScriptPath();
                String hotkey = PropertyUtils.getProperty("get.price.hotkey");
                String scriptOutput = RunShellScript.getPriceLinuxCommand(getRegisterPricePath, subnetId, hotkey);
                currentPrice = StringUtils.extractPrice(scriptOutput);
                String message = StringUtils.priceMessage(subnetId, currentPrice);
                log.info(message);
                if (shouldSendMessage(currentPrice)) {
                    String channelId = PropertyUtils.getProperty("subnet.register.price.channel.chat.id");
                    TeleGramMessageSender.sendMessage(channelId, message);
                    previousSendTime = System.currentTimeMillis();
                }
                previousPrice = currentPrice;
                Thread.sleep(Long.parseLong(PropertyUtils.getProperty("get.price.thread.sleep")));

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private boolean shouldSendMessage(String price) {
        Long maxTimeNoSendMessage = Long.parseLong(PropertyUtils.getProperty("max.time.no.send.message"));
        if (System.currentTimeMillis() - previousSendTime > maxTimeNoSendMessage * 60 * 1000) {
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