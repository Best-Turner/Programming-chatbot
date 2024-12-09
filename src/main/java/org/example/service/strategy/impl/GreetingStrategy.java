package org.example.service.strategy.impl;

import org.example.service.strategy.ResponseStrategy;

import java.util.List;
import java.util.Random;

public class GreetingStrategy implements ResponseStrategy {

    private List<String> helloMessage;

    private int count = 0;

    public GreetingStrategy() {
        helloMessage = List.of(
                "Привет!",
                "Добро пожаловать! Чем я могу помочь тебе сегодня?",
                "Здравствуйте!",
                "Приветствую, мой друг!"

        );
    }

    @Override
    public String response(String input) {
        Random random = new Random();
        int index = random.nextInt(helloMessage.size());
        if (count >= 2) {
            return "Мы уже поздоровались, друг";
        }
        count++;
        return helloMessage.get(index);
    }
}
