package org.example.model;

import java.time.LocalDateTime;

public class Message {

    public static final int STATUS_CODE_ZERO = 0;
    public static final int STATUS_CODE_ONE = 1;
    public static final int STATUS_CODE_TWO = 2;

    private String id;
    private String text;
    private int statusCode;

    private LocalDateTime dateTime;


    public Message() {
    }

    public Message(String id, String test, int statusCode, LocalDateTime dateTime) {
        this.id = id;
        this.text = test;
        this.statusCode = statusCode;
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Message{" +
               "id='" + id + '\'' +
               ", test='" + text + '\'' +
               ", statusCode=" + statusCode +
               ", dateTime=" + dateTime +
               '}';
    }
}