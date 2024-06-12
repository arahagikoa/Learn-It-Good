package com.example.main;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FilePanel extends JPanel {
    private JPanel fileContainer;
    private JButton addButton;
    MainPanel mainPanel;
    public Color col = Color.WHITE;
    public StringBuilder text;
    public ChatWindow chatWindow;
    public FileObject fileObj;
    public JScrollPane scrollPane;
    public FilePanel(MainPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        
        
        this.chatWindow = new ChatWindow(mainPanel, fileObj);
        fileContainer = new JPanel();
        fileContainer.setLayout(new BoxLayout(fileContainer, BoxLayout.Y_AXIS));
        fileContainer.setBackground(Color.black);
        scrollPane = new JScrollPane(fileContainer);
        scrollPane.setBackground(Color.black);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    
        SwingUtilities.invokeLater(() -> {
            DataBaseManager mng = DataBaseManager.getInstance();
            try (Connection conn = mng.getConnection()) {
                getAllFilesAndGenerate(conn, fileContainer, col);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    
        addButton = new JButton("Add File");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
    
                int result = fileChooser.showOpenDialog(FilePanel.this);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    addFile(selectedFile);
                }
            }
        });
    
        add(scrollPane, BorderLayout.CENTER);
        add(addButton, BorderLayout.SOUTH);
    }
    
    public void setChatWindow(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
        System.out.println("FilePanel: ChatWindow is set.");
    }

    private void addFile(File file) {
        JLabel fileLabel = new JLabel(file.getName().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", ""));
        
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
    
            text = new StringBuilder();
            String line;
    
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line).append("\n");
            }
    
            fileObj = new FileObject(this, file.getName(), chatWindow);
            fileObj.save_file_text(fileObj);

            System.out.println("Setting FileObject in ChatWindow...");
            chatWindow.setFileObject(fileObj);  
            System.out.println("FileObject set successfully.");
    
            bufferedReader.close();
            System.out.println("Text read from file:\n" + text);
    
            String fileName = file.getName().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
            String storageName = mainPanel.getTitle();
            DataBaseManager mng = DataBaseManager.getInstance();
            
            try (Connection conn = mng.getConnection()) {
                JDBCconn cmds = new JDBCconn();
                cmds.addNewDataFile(conn, storageName, fileName, text.toString());
                getAllFilesAndGenerate(conn, fileContainer, col);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    void getAllFilesAndGenerate(Connection conn, JPanel panel, Color color) {
        try {
            String SELECT_ALL_SQL = "SELECT file_data_name FROM " + mainPanel.getTitle();
            PreparedStatement prepStatement = conn.prepareStatement(SELECT_ALL_SQL);
            ResultSet resultSet = prepStatement.executeQuery();

            panel.removeAll();
            while (resultSet.next()) {
                String fileName = resultSet.getString("file_data_name");
                System.out.println("File Name: " + fileName);

                SwingUtilities.invokeLater(() -> {
                    FileObject field = new FileObject(this, fileName, chatWindow);
                    field.changebcgColor(color);
                    panel.add(field);
                    panel.revalidate();
                    panel.repaint();
                });
            }

            resultSet.close();
            prepStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setBackgroundColor(Color color) {
        this.setBackground(color);
        fileContainer.setBackground(color);
        scrollPane.setBackground(color);
        SwingUtilities.invokeLater(() -> {
            DataBaseManager mng = DataBaseManager.getInstance();
            try (Connection conn = mng.getConnection()) {
                getAllFilesAndGenerate(conn, fileContainer, color);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
    }
}
