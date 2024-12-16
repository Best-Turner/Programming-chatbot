package org.example.service.impl;

import org.example.repository.KeywordStorage;
import org.example.service.LessonService;
import org.example.service.strategy.ResponseStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

class ChatBotServiceTest {
    private static final String MESSAGE_ERROR_INPUT = "Неверная команда";
    @Mock
    private KeywordStorage storageCommand;

    @Mock
    private LessonService lessonService;

    @Mock
    private ResponseStrategy strategy;


    @InjectMocks
    private ChatBotService chatBotService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        chatBotService.setStrategy(strategy);
    }


    @Test
    void whenCommandIncorrectThenReturnMessageWithErrorText() {
        String input = "someText";
        setStatusCode(-1);
        chatBotService.setStrategy(null);
        String sut = chatBotService.getResponse(input);
        assertEquals(MESSAGE_ERROR_INPUT, sut);
    }

    @Test
    void whenInputContainsGreetingWordsThenMustBeInstalledGreetingStrategy() {
        //Arrange
        String input = "привет";
        setStatusCode(0);
        when(strategy.response(input)).thenReturn("привет");

        //Act
        String response = chatBotService.getResponse(input);

        //Assert
        verify(strategy, times(1)).response(input);
        assertEquals("привет", response);
    }

    private void setStatusCode(int statusCode) {
        when(storageCommand.getStatusCode("someText")).thenReturn(statusCode);
    }

}