package org.example.telegram;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public class TeleGramMessageSender {
    private static String URL_STRING = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s";

    private static String API_TOKEN = "7036226782:AAHliZcXRdvCto5KhUyYqMDR2eiIQase7IA";

    private static String TELEGRAM_BASE_URL = "https://api.telegram.org/bot";
    private static String API_URL = TELEGRAM_BASE_URL + API_TOKEN;


    public static void sendMessage(String chatID, String message) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL + "/sendMessage")
                    .queryParam("chat_id", chatID)
                    .queryParam("text", message);
            ResponseEntity exchange = restTemplate.exchange(builder.toUriString().replaceAll("%20", " "), HttpMethod.POST, null, String.class);
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
}
