package org.example.service.impl;

import org.example.service.LessonService;
import org.example.service.QuestionService;
import org.springframework.stereotype.Service;

@Service
public class ChatBotService {
    private final LessonService lessonService;
    private final QuestionService questionService;


    public ChatBotService(LessonService lessonService, QuestionService questionService) {
        this.lessonService = lessonService;
        this.questionService = questionService;
    }


    public void handleUserInput(String input) {


    }

    private void sendResponse() {

    }


    private enum Command {
        START, HELP, LESSONS()
    }
}
