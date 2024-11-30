package org.example;

import org.example.config.AppConfig;
import org.example.gui.ChatBotGUI;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ChatBotApplication {
    public static void main(String[] args) {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        ChatBotGUI.createAndShowGUI();
    }
}
