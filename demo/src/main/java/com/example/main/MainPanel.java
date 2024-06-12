package com.example.main;
import java.awt.*;
import javax.swing.*;

public class MainPanel extends JFrame {
    private Color czatbackgroundColor;
    private Color filesbackgroundColor;
    private FilePanel files;
    private ChatWindow chat;
    private String titleName;
    private String jezyk;
    private FileObjectMenu menu;
    
    //public FileObject obj;
    
    public MainPanel(String name) {
    	jezyk = "Polski";
    	this.titleName = name;
        this.setTitle(name);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);   // centrowanie okna

        FilePanel filePanel = new FilePanel(this);
        ChatWindow chatWindow = new ChatWindow(this, filePanel.fileObj);
        filePanel.setChatWindow(chatWindow);
    
        filePanel.setChatWindow(chatWindow); // ustawia chatWindow w filePanel

        MenuBar bar = new MenuBar(this);
        files = filePanel;
        chat = chatWindow;
        
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(files);
        panel.add(chat);

        this.setLayout(new BorderLayout());
        this.add(bar, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
    }
    public String getTitleName() {
        return titleName;
    }
    public void setCzatBackgroundColor(Color color) {
        czatbackgroundColor = color;
        chat.setBackgroundColor(czatbackgroundColor); // Zmiana tutaj
    }

    public void setFilesBackgroundColor(Color color) { 
        filesbackgroundColor = color;
        files.setBackgroundColor(filesbackgroundColor); // Zmiana tutaj
    }
   
    public void changeJezyk(String nowy_jezyk) {
    	jezyk = nowy_jezyk;
    }
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //miało poprawić grafikę ale nie specjalnie to widać XD
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        MainPanel mainPanel = new MainPanel("Learn it good");
        mainPanel.setVisible(true);
    }
}
