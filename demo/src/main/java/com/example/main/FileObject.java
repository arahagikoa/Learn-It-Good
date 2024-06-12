package com.example.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

//@Author
//Aleksander Majkowski

public class FileObject extends JPanel {
    JLabel docTitle;
    JButton options;
    JPanel list;
    String TableName;
    public String file_text;
    public FilePanel frame;
    public String prompt;
    public ChatWindow window;

    public FileObject(FilePanel frame, String Name, ChatWindow window) {
        this.frame = frame;
        this.window = window;
        this.setBackground(Color.white);
        TableName = frame.mainPanel.getTitle();

        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(480, 50));
        panel.setBackground(Color.LIGHT_GRAY);

        FileObjectMenu menu = new FileObjectMenu(this, frame, window);
        docTitle = new JLabel(Name);
        docTitle.setText(Name);

        panel.add(docTitle, BorderLayout.WEST);
        panel.add(menu, BorderLayout.EAST);
        this.add(panel);
    }

    public void save_file_text(FileObject obj){
        obj.file_text = frame.text.toString();
    }

    public void call(String file_content) {
        create_prompt(file_content, window);
    }

    private String create_prompt(String file_text, ChatWindow window){
        StringBuilder prompt_Builder = new StringBuilder();
        prompt_Builder.append("You are a helpful assistant that is designed to answer documents. Document will be provided below and delimited by '####', question will be delimited by '$$$$'");
        prompt_Builder.append("#### \n" + file_text + "\n####");
        if (window.message != null) {
            prompt_Builder.append("$$$$"+ window.message + "$$$$");
        } else {
            prompt_Builder.append("$$$$"+ "default message" + "$$$$");
        }
        prompt = prompt_Builder.toString();
        System.out.println("create_prompt prompt:" + prompt);
        
        return prompt;
    }
}
