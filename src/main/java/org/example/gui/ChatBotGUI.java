package org.example.gui;

import org.example.gui.buttons.RoundedButton;
import org.example.gui.textField.CustomTextField;
import org.example.service.impl.ChatBotService;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChatBotGUI extends JPanel implements ActionListener {

    private static final String NEW_LINE = "\n";
    private static final String BOT = "Чат-бот:\n";
    private static final String USER = "Вы:\n";
    private static ChatBotService service;
    JTextField textField;
    JTextPane textPane;


    public ChatBotGUI(ChatBotService service) {
        super(new GridBagLayout());
        this.service = service;
        setBackground(Color.LIGHT_GRAY);
        JButton button = new RoundedButton("Отправить");
        button.addActionListener(this);


        textField = new CustomTextField("Введите текст...");
        textField.addActionListener(this);

        textPane = new JTextPane();
        textPane.setContentType("text/plain"); // Устанавливаем контентный тип
        textPane.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textPane);
        scrollPane.setPreferredSize(new Dimension(350, 350)); // Устанавливаем размер JScrollPane

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = GridBagConstraints.REMAINDER; // занимает всю ширину
        c.fill = GridBagConstraints.BOTH; // заполняет по высоте и ширине
        add(scrollPane, c);

        c.gridy = 1; // строка 2
        c.gridx = 0; // столбец 1
        c.gridwidth = 1; // Занимает только одну ячейку
        c.fill = GridBagConstraints.HORIZONTAL; // Заполняет только по горизонтали
        add(textField, c);
        c.gridy = 1;
        c.gridx = 1;
        c.gridwidth = 1;
        add(button, c);
    }

    public static void createAndShowGUI(ChatBotService service) {
        //Create and set up the window.
        JFrame frame = new JFrame("Чат бот");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(300, 300);

        //Add contents to the window.
        frame.add(new ChatBotGUI(service));

        //Display the window.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textField.getText();
        if (text != null && !text.isEmpty()) {
            appendText(text, false); // Левое выравнивание
            textField.setText(null);
            textPane.setCaretPosition(textPane.getDocument().getLength());
            try {
                String response = service.getResponse(text);
                appendText(response, true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void appendText(String text, boolean alignLeft) {

        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet attrs = new SimpleAttributeSet();

        // Устанавливаем выравнивание
        StyleConstants.setAlignment(attrs, alignLeft ? StyleConstants.ALIGN_LEFT : StyleConstants.ALIGN_RIGHT);
        doc.setParagraphAttributes(doc.getLength(), 1, attrs, false);
        try {
            doc.insertString(doc.getLength(), (alignLeft ? BOT : USER).concat(text) + NEW_LINE, null);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
