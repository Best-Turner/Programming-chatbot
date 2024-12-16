package org.example.service.strategy.impl;

import org.example.model.Lesson;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

public abstract class CommonTestSetupResponseStrategy {

    protected static final String INPUT_WITHOUT_NUMBER = "урок";
    protected static final String INPUT_WITH_NUMBER = "урок 5";
    protected ResponseStrategy strategy;
    protected Lesson lesson;
    protected List<Lesson> list;
    @Mock
    protected LessonService service;


    public CommonTestSetupResponseStrategy() {
        lesson = new Lesson(5, "title", "description", "content", "topic");
    }

    protected final void initListLessons() {
        list = new ArrayList<>();
        int count = 0;
        while (count < 10) {
            list.add(new Lesson(count, "title " + count, "description " + count, "content " + count, "topic " + count));
            count++;
        }
    }
}
