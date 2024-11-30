package org.example.gui.buttons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RoundedButton extends JButton {


    private Color hoverBackgroundColor = new Color(103, 188, 21); // Цвет фона при наведении
    private Color pressBackgroundColor = new Color(38, 128, 21); // Нормальный цвет фона
    private Color normalButtonColor = new Color(12, 166, 24);
    private Color textColor = Color.BLACK;

    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Убираем стандартное заполнение
        setFocusPainted(false); // Убираем рамку при фокусе
        setBorderPainted(false); // Убираем границу
        setOpaque(false); // Делаем кнопку прозрачной

        // Добавляем обработчики событий для изменения внешнего вида при наведении
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverBackgroundColor); // Меняем цвет фона при наведении
            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBackground(pressBackgroundColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(normalButtonColor); // Возвращаем цвет фона
            }
        });

        // Устанавливаем нормальный цвет фона
        setBackground(normalButtonColor);
        setForeground(textColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // 20 - радиус закругления
        super.paintComponent(g); // Рисуем текст кнопки
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 30); // Устанавливаем предпочтительный размер кнопки
    }
}
