package org.example.gui;

import org.example.service.impl.ChatBotService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import java.awt.event.ActionEvent;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ChatBotGUITest {

    private ChatBotService chatBotService;
    private ChatBotGUI chatBotGUI;

    @BeforeEach
    void setUp() {
        chatBotService = mock(ChatBotService.class);
        chatBotGUI = new ChatBotGUI(chatBotService);
    }

    @Test
    void testSendMessageAndReceiveResponse() {
        // Arrange
        String userMessage = "Привет";
        String botResponse = "Здравствуйте!";
        when(chatBotService.getResponse(userMessage)).thenReturn(botResponse);

        // Act
        chatBotGUI.textField.setText(userMessage);
        chatBotGUI.actionPerformed(new ActionEvent(chatBotGUI.textField, ActionEvent.ACTION_PERFORMED, null));

        // Assert
        StyledDocument doc = chatBotGUI.textPane.getStyledDocument();
        String text = "";
        try {
            text = doc.getText(0, doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }

        assertTrue(text.contains("Вы:\n" + userMessage));
        assertTrue(text.contains("Чат-бот:\n" + botResponse));
    }


    @Test
    void testEmptyInputDoesNotSendMessage() {
        // Arrange
        String initialText = chatBotGUI.textPane.getText();

        // Act
        chatBotGUI.textField.setText(""); // Устанавливаем пустое значение
        chatBotGUI.actionPerformed(new ActionEvent(chatBotGUI.textField, ActionEvent.ACTION_PERFORMED, null));

        // Assert
        assertTrue(chatBotGUI.textPane.getText().equals(initialText)); // Проверяем, что текст не изменился
    }

}