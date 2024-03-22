package com.data;

import org.apache.commons.codec.digest.DigestUtils;

public class DataEntity {
    private String text;
    private String timestamp;

    private String url;

    public DataEntity() {
    }

    public DataEntity(String text, String timestamp, String url) {
        this.text = text;
        this.timestamp = timestamp;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static void main(String[] args) {
        String raw = "Beginners BBQ Class Taking Place in Missoula!\\nDo you want to get better at making delicious BBQ? You will have the opportunity, put this on your calendar now. Thursday, September 22nd join World Class BBQ Champion, Tony Balay from Lonestar Smoke Rangers. He will be teaching a beginner level class for everyone who wants to get better with their culinary skills.\\nHe will teach you everything you need to know to compete in a KCBS BBQ competition, including techniques, recipes, timelines, meat selection and trimming, plus smoker and fire information.\\nThe cost to be in the class is $35 per person, and for spectators it is free. Included in the cost will be either a t-shirt or apron and you will be tasting samples of each meat that is prepared.";
        String sha256hex = DigestUtils.sha256Hex(raw);
        System.out.println(sha256hex);
        String txt = "df8fe77b749d3d27be290dc9a07ee522ab5d9fde1c523c0d3a18e1768efce3c3";
        System.out.println(sha256hex.equals(txt));
        System.out.println(Math.abs(txt.hashCode()) % 256);
    }
}
