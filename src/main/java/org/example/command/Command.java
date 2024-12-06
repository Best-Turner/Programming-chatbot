package org.example.command;

import org.example.model.Message;

public interface Command {

    String execute(Message message) throws Exception;
}
