package main;
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
    
    
    public FilePanel(MainPanel mainPanel) {
    	this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        
        fileContainer = new JPanel();
        fileContainer.setLayout(new BoxLayout(fileContainer, BoxLayout.Y_AXIS)); // Ustawienie BoxLayout do osi y
        JScrollPane scrollPane = new JScrollPane(fileContainer);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        SwingUtilities.invokeLater(() -> {
            DataBaseManager mng = DataBaseManager.getInstance();
            try (Connection conn = mng.getConnection()) {
                getAllFilesAndGenerate(conn, fileContainer);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        addButton = new JButton("Dodaj plik");
        //obsługa przycisku do dodawnaia plików
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
    //obsługa plików
    private void addFile(File file) {
        
   
        JLabel fileLabel = new JLabel(file.getName().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", ""));
        
        
        try {
            
            FileReader fileReader = new FileReader(file);

            
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            
            StringBuilder text = new StringBuilder();
            String line;

            
            while ((line = bufferedReader.readLine()) != null) {
                text.append(line).append("\n"); 
            }

            // Close the BufferedReader.
            bufferedReader.close();

            // Display the text if needed.
            System.out.println("Text read from file:\n" + text);

            // Insert the text into the MySQL database
            String fileName = file.getName().replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
            String storageName = mainPanel.getTitle();
            DataBaseManager mng = DataBaseManager.getInstance();
            
            try (Connection conn = mng.getConnection()) {
                JDBCconn cmds = new JDBCconn();
                cmds.addNewDataFile(conn, storageName, fileName, text.toString());
                getAllFilesAndGenerate(conn, fileContainer);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        } catch (Exception e) {
            // Handle any errors that may occur.
            e.printStackTrace();
        }
        
    }
    void getAllFilesAndGenerate(Connection conn, JPanel panel) {
        try {
        	String SELECT_ALL_SQL = "SELECT file_data_name FROM " + mainPanel.getTitle();
            PreparedStatement prepStatement = conn.prepareStatement(SELECT_ALL_SQL);
            ResultSet resultSet = prepStatement.executeQuery();
            
            panel.removeAll();
            while (resultSet.next()) {
                
                String FileName = resultSet.getString("file_data_name");
                System.out.println( "File Name: " + FileName);
                
                SwingUtilities.invokeLater(() -> {
                    FileObject field = new FileObject(this, FileName);
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
    }

    
}
