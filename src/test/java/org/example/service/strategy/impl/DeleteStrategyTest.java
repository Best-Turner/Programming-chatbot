package org.example.service.strategy.impl;

import org.example.exception.LessonNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class DeleteStrategyTest extends CommonTestSetupResponseStrategy {

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        strategy = new DeleteStrategy(service);
    }

    @Test
    void whenDeleteLessonWithCorrectIdThenReturnMessageWithThisLesson() throws LessonNotFoundException {
        //Arrange
        final String expected =
                String.format("Урок - (%d) %s, %s  /УДАЛЕН", lesson.getId(), lesson.getTitle(), lesson.getTopic());
        when(service.getById(5)).thenReturn(lesson);
        when(service.deleteById(5)).thenReturn(true);
        //Act
        String response = strategy.response(INPUT_WITH_NUMBER);
        //Assert
        assertEquals(expected, response);
    }

    @Test
    void whenDeleteLessonWithOutIdThenReturnListLessonForDeleted() throws LessonNotFoundException {
        //Arrange
        initListLessons();
        StringBuilder expected = new StringBuilder();
        expected.append("~ВОТ СПИСОК УРОКОВ КОТОРЫЕ ВЫ МОЖЕТЕ УДАЛИТЬ~");
        list.forEach(el ->
                expected.append(String.format("%nID (%d): %s, %s", el.getId(), el.getTitle(), el.getTopic())));
        expected.append("\n~ВВЕДИТЕ ID УРОКА КОТОРЫЙ ХОТИТЕ УДАЛИТЬ~");
        when(service.getAll()).thenReturn(list);

        //Act
        String response = strategy.response(INPUT_WITHOUT_NUMBER);

        //Assert
        assertEquals(expected.toString(), response);
    }

}