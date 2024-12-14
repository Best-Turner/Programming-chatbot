package org.example.command.impl;

import org.example.command.Command;
import org.example.model.Message;
import org.example.service.LessonService;

public class LessonByIdCommand implements Command {

    private final LessonService service;

    public LessonByIdCommand(LessonService service) {
        this.service = service;
    }

    @Override
    public String execute(Message message) throws Exception {
        int id = Integer.parseInt(message.getText());
        return service.getById(id).toString();
    }
}
