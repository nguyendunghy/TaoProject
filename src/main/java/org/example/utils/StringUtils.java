package org.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    public static String extractPrice(String input){
        String[] strArray = input.split("\n");
        String price;


        try {
            price = strArray[7].split("τ")[1].split(" ")[0].trim();
            return price;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
             price = strArray[3].split("τ")[1].trim();
             return price;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            price = strArray[7].split("τ")[1].trim();
            return price;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        price = strArray[strArray.length - 1].split(" ")[0].split("τ")[1].trim();
        return price;
    }

    public static String priceMessage(String subnetID, String price){
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(FORMATTER);
        String text = String.format("Price of subnet %s at %s is : %s",subnetID, formattedDateTime, price);
        return text;
    }
}
