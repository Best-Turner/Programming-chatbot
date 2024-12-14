package org.example.command.impl;

import org.example.command.Command;
import org.example.model.Lesson;
import org.example.model.Message;
import org.example.service.LessonService;

public class DeleteLessonByIdCommand implements Command {

    private final LessonService service;

    public DeleteLessonByIdCommand(LessonService service) {
        this.service = service;
    }

    @Override
    public String execute(Message message) throws Exception {
        StringBuilder response = new StringBuilder();
        int id = Integer.parseInt(message.getText());
        Lesson byId = service.getById(id);
        response.append(byId.getId())
                .append(" ").append(byId.getTitle())
                .append(" ").append(byId.getTopic())
                .append(" - УДАЛЕН");
        service.deleteById(id);
        return response.toString();
    }
}
