package main;
import java.awt.*;
import javax.swing.*;

public class MainPanel extends JFrame {
    private Color czatbackgroundColor;
    private Color filesbackgroundColor;
    private FilePanel files;
    private ChatWindow chat;

    public MainPanel(String name) {
        this.setTitle("Learn it good " + name);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);   // centrowanie okna

        MenuBar bar = new MenuBar(this);
        files = new FilePanel(); // Zmiana tutaj
        chat = new ChatWindow(); // Zmiana tutaj
        
        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(files);
        panel.add(chat);

        this.setLayout(new BorderLayout());     
        this.add(bar, BorderLayout.NORTH);
        this.add(panel, BorderLayout.CENTER);
    }

    public void setCzatBackgroundColor(Color color) {
        czatbackgroundColor = color;
        chat.setBackgroundColor(czatbackgroundColor); // Zmiana tutaj
    }

    public void setFilesBackgroundColor(Color color) { 
        filesbackgroundColor = color;
        files.setBackgroundColor(filesbackgroundColor); // Zmiana tutaj
    }
   

    public static void main(String[] args) {
        MainPanel mainPanel = new MainPanel("Learn it good");
        mainPanel.setVisible(true);
    }
}
