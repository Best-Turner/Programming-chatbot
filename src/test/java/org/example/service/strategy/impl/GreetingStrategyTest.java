package org.example.service.strategy.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GreetingStrategyTest extends CommonTestSetupResponseStrategy {
    private static final String INPUT = "привет";

    private List<String> greetingWords =
            List.of("Привет!",
                    "Добро пожаловать! Чем я могу помочь тебе сегодня?",
                    "Здравствуйте!",
                    "Приветствую, мой друг!");

    @BeforeEach
    void setUp() {
        strategy = new GreetingStrategy();
    }


    @Test
    void whenInputGreetingWordThenReturnAnyGreetingWords() {
        //Act
        String sut = strategy.response(INPUT);
        //Assert
        assertTrue(greetingWords.contains(sut));
    }

    @Test
    void whenInputGreetingMoreThan3TimesThenReturnInfoMessage() {
        //Arrange
        final String infoMessage = "Мы уже поздоровались, друг";
        String result = null;
        //Act
        for (int i = 0; i < 3; i++) {
            result = strategy.response(INPUT);
        }
        //Assert
        assertEquals(infoMessage, result);
    }
}