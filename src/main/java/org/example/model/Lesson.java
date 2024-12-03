package org.example.model;

import java.util.List;

public class Lesson {

    private int id;
    private String title; // Название урока
    private String description; // Описание урока
    private List<String> content; // Содержимое урока (может быть текст, ссылки и т.д.)
    private String topic; // Тематика урока (например, "Основы Java", "ООП" и т.д.)

    public Lesson() {
    }

    public Lesson(int id, String title, String description, List<String> content, String topic) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.topic = topic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Lesson{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", description='" + description + '\'' +
               ", content=" + content +
               ", topic='" + topic + '\'' +
               '}';
    }
}
