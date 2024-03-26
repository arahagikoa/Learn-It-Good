import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class ChatWindow extends JPanel {

    private DefaultListModel<String> chatModel = new DefaultListModel<>();
    private JList<String> chatHistory = new JList<>(chatModel);
    private JTextField inputField = new JTextField(35);

    public ChatWindow() {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(chatHistory);
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(inputField, BorderLayout.SOUTH);
        add(panel);
        panel.setPreferredSize(new Dimension(500, 700));
        scrollPane.setMinimumSize(new Dimension(500, 700));
        //pack(); to działa tylko na JFrame
        //Obsługua wprowadzania tekstu 
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = inputField.getText();
                chatModel.addElement("You: " + message);
                chatModel.addElement("AI:" + " Miło że piszesz. Niedługo będę w stanie odpowiadać na twoje zapytania :) ");
                inputField.setText("");
            }
        });

        // Brak możliwość pisania w czacie
        chatHistory.setEnabled(false);
    }
    public void setBackgroundColor(Color color) {
        this.setBackground(color);
    }

    //public static void main(String[] args) {
      //  new ChatWindow();
    //}
}
