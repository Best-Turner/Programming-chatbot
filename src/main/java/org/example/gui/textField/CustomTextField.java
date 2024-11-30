package org.example.gui.textField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class CustomTextField extends JTextField {

    private String placeholder;

    public CustomTextField(String placeholder) {
        this.placeholder = placeholder;
        setOpaque(false); // Делаем текстовое поле прозрачным
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Убираем стандартную границу

        // Добавляем обработчик событий для плейсхолдера
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.BLACK); // Устанавливаем цвет текста при фокусе
                    repaint();
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.LIGHT_GRAY); // Устанавливаем цвет плейсхолдера
                    repaint();
                }
            }
        });

        // Устанавливаем начальный цвет текста
        setForeground(Color.GRAY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (getText().isEmpty()) {
            g.setColor(getForeground());
            g.drawString(placeholder, 10, 20); // Рисуем плейсхолдер
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        g.setColor(Color.LIGHT_GRAY); // Цвет границы
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20); // Рисуем закругленную границу
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 30); // Устанавливаем предпочтительный размер
    }
}
