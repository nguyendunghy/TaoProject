package com.data;

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
}
