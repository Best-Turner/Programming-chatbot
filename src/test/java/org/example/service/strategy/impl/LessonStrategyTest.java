package org.example.service.strategy.impl;

import org.example.exception.LessonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class LessonStrategyTest extends CommonTestSetupResponseStrategy {
    ;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new LessonStrategy(service);
    }

    @Test
    void whenGetLessonByIdThenReturnLesson() throws LessonNotFoundException {
        //Arrange
        when(service.getById(5)).thenReturn(lesson);
        String expected = String.format("\n\tID (%d): %s, %s%n %s", lesson.getId(), lesson.getTitle(), lesson.getTopic(), lesson.getContent());
        //Act
        String response = strategy.response(INPUT_WITH_NUMBER);
        //Assert
        assertEquals(expected, response);
    }

    @Test
    void whenGetAllLessonsThenReturnListLessons() {
        //Arrange
        initListLessons();
        when(service.getAll()).thenReturn(list);
        StringBuilder expected = new StringBuilder();
        list.forEach(el -> expected.append(String.format("\tID (%d): %s; %s%n", el.getId(), el.getTitle(), el.getTopic())));
        //Act
        String sut = strategy.response(INPUT_WITHOUT_NUMBER);
        //Assert
        assertTrue(sut.contains(expected));
    }


    @Test
    void whenListLessonsEmptyThenReturnInfoMessage() {
        //Arrange
        final String infoMessage = "~У ВАС НЕТ НИ ОДНОГО ДОСТУПНОГО УРОКА~";
        when(service.getAll()).thenReturn(Collections.emptyList());
        //Act
        String sut = strategy.response(INPUT_WITHOUT_NUMBER);
        //Assert
        assertEquals(infoMessage, sut);
    }

    @Test
    void whenGetLessonWithIncorrectIdThenShowWarningMessage() throws LessonNotFoundException {
        //Arrange
        final String warningMessage = "Урок не найден";
        when(service.getById(5)).thenThrow(new LessonNotFoundException(warningMessage));
        //Act
        String sut = strategy.response(INPUT_WITH_NUMBER);
        //Assert
        assertEquals(warningMessage, sut);
    }
}