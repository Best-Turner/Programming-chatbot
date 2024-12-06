package org.example;

import org.example.config.AppConfig;
import org.example.exception.LessonNotFoundException;
import org.example.gui.ChatBotGUI;
import org.example.model.Lesson;
import org.example.service.impl.ChatBotService;
import org.example.service.impl.LessonServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;

public class ChatBotApplication {
    public static void main(String[] args)  {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        ChatBotGUI.createAndShowGUI(context.getBean(ChatBotService.class));

    }
}
