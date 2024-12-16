package org.example.service.strategy.impl;

import org.example.exception.LessonNotFoundException;
import org.example.model.Lesson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class EditLessonStrategyTest extends CommonTestSetupResponseStrategy {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new EditLessonStrategy(service);
    }

    @Test
    void whenInputIsNotANumberThenReturnListOfLessons() {
        // Arrange
        initListLessons();
        when(service.getAll()).thenReturn(list);

        // Act
        String response = strategy.response("hello");

        // Assert
        assertTrue(response.contains("~СПИСОК УРОКОВ КОТОРЫЙ МОЖНО ИЗМЕНИТЬ~"));
        assertTrue(response.contains("ID (0): title 0, topic 0"));
        assertTrue(response.contains("ID (5): title 5, topic 5"));
        assertTrue(response.contains("ID (9): title 9, topic 9"));
        assertTrue(response.contains("~ВВЕДИТЕ ID УРОКА КОТОРЫЙ ХОТИТЕ ИЗМЕНИТЬ~"));
    }

    @Test
    void whenInputIsValidIdThenPromptForNewTitle() {
        // Act
        String response = strategy.response("1");

        // Assert
        assertEquals("~ВВЕДИТЕ НОВЫЙ ЗАГОЛОВОК~", response);
    }

    @Test
    void whenEditLessonThenReturnSuccessMessage() throws LessonNotFoundException {
        // Arrange
        when(service.getAll()).thenReturn(List.of(lesson));
        when(service.editById(anyInt(), any(Lesson.class))).thenReturn(lesson);

        // Act
        strategy.response("1");
        strategy.response("New Title");
        strategy.response("New Description");
        strategy.response("New Content");
        String response = strategy.response("New Topic");

        // Assert
        assertEquals("~ДАННЫЕ ЗАДАЧИ С ID = 1 ИЗМЕНЕНЫ~", response);
        verify(service).editById(eq(1), any(Lesson.class)); // Проверка, что метод editById был вызван
    }

    @Test
    void whenLessonNotFoundThenReturnErrorMessage() throws LessonNotFoundException {
        // Arrange
        when(service.getAll()).thenReturn(List.of(lesson));
        doThrow(new LessonNotFoundException("Урок не найден")).when(service).editById(anyInt(), any(Lesson.class));

        // Act
        strategy.response("1");
        strategy.response("New Title");
        strategy.response("New Description");
        strategy.response("New Content");
        String response = strategy.response("New Topic");

        // Assert
        assertEquals("Урок не найден", response);
    }

}