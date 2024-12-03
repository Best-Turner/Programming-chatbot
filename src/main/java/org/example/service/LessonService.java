package org.example.service;

import org.example.exception.LessonNotFoundException;
import org.example.model.Lesson;

import java.util.List;

public interface LessonService extends Service<Lesson, Integer> {

    List<Lesson> searchLessonByTopic(String topic) throws LessonNotFoundException;
}
