package org.example.command.impl;

import org.example.command.Command;
import org.example.model.Message;

import java.util.List;
import java.util.Random;

public class GoodbyeCommand implements Command {
    private List<String> goodbyeMessage;

    public GoodbyeCommand() {
        goodbyeMessage = List.of(
                "Пока!",
                "Счастливо",
                "До встречи!",
                "Всего доброго"

        );
    }

    @Override
    public String execute(Message input) {
        Random random = new Random();
        int index = random.nextInt(goodbyeMessage.size());
        return goodbyeMessage.get(index);
    }
}
