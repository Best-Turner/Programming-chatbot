package org.example;

import org.example.config.AppConfig;
import org.example.model.ChatMessage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

    }
}
