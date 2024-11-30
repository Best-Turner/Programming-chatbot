package org.example.model;

import java.time.LocalDateTime;

public class ChatMessage {

    private String sender;
    private String content;

    private LocalDateTime dateTime;


    public ChatMessage() {
    }

    public ChatMessage(String sender, String content, LocalDateTime dateTime) {
        this.sender = sender;
        this.content = content;
        this.dateTime = dateTime;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


    @Override
    public String toString() {
        return "ChatMessage{" +
               "sender='" + sender + '\'' +
               ", content='" + content + '\'' +
               ", dateTime=" + dateTime +
               '}';
    }
}