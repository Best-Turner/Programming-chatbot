package org.example;

import org.example.config.AppConfig;
import org.example.exception.LessonNotFoundException;
import org.example.gui.ChatBotGUI;
import org.example.model.Lesson;
import org.example.service.impl.LessonServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Collections;

public class ChatBotApplication {
    public static void main(String[] args)  {
        var context = new AnnotationConfigApplicationContext(AppConfig.class);

        ChatBotGUI.createAndShowGUI();

       // LessonServiceImpl service = context.getBean(LessonServiceImpl.class);


        //Lesson lesson = new Lesson(5, "Title5", "Description5", Collections.singletonList("SomeContent"), "Spring");

        //service.addLesson(lesson);
        //
        //Lesson lessonById = service.getLessonById(5);
        //service.searchLessonByTopic("Spring").forEach(System.out::println);

        //System.out.println(lessonById);
        //service.deleteLessonById(5);

        //service.getAll().forEach(System.out::println);

    }
}
