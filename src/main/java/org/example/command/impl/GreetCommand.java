package org.example.command.impl;

import org.example.command.Command;
import org.example.model.Message;

import java.util.List;
import java.util.Random;

public class GreetCommand implements Command {

    private List<String> helloMessage;

    public GreetCommand() {
        helloMessage = List.of(
                "Привет!",
                "Добро пожаловать! Чем я могу помочь тебе сегодня?",
                "Здравствуйте!",
                "Приветствую, мой друг!"

        );
    }

    @Override
    public String execute(Message input) {
        Random random = new Random();
        int index = random.nextInt(helloMessage.size());
        return helloMessage.get(index);
    }
}
