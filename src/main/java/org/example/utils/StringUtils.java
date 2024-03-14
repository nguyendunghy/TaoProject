package org.example.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StringUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss dd-MM-yyyy");

    public static String extractPrice(String input) throws Exception {

        String[] strArray = input.split("\n");
        String price;

        if (input.contains("Insufficient balance")) {
            try {
                price = strArray[strArray.length - 1].split("τ")[2].split(" ")[0].trim();
                return price;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

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

        try {
            price = strArray[strArray.length - 1].split(" ")[0].split("τ")[1].trim();
            return price;
        } catch (Exception ex) {
            System.out.println("input: " + input);
            System.out.println(ex.getMessage());
            throw ex;
        }

    }

    public static String priceMessage(String subnetID, String price) {
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(FORMATTER);
        String text = String.format("Price of subnet %s at %s is : %s", subnetID, formattedDateTime, price);
        return text;
    }
}
