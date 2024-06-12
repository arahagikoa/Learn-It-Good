package com.example.main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ChatWindow extends JPanel {

    private DefaultListModel<String> chatModel = new DefaultListModel<>();
    private JList<String> chatHistory = new JList<>(chatModel);
    private JTextField inputField = new JTextField(35);
    private RequestsMNG rmng = new RequestsMNG();
    private JLabel fileLabel;
    
    public String message;
    public FileObject obj;
    public String fileName;
    public String tableName, FileContent;
    
    
    public ChatWindow(MainPanel mainPanel, FileObject object) {
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(chatHistory);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setFileObject(object);
        panel.add(scrollPane, BorderLayout.CENTER);
        fileLabel = new JLabel("Currently used file: " + fileName);
        
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton save = new JButton("Save");

        bottomPanel.add(save, BorderLayout.WEST);
        bottomPanel.add(inputField, BorderLayout.CENTER);
        bottomPanel.add(fileLabel, BorderLayout.SOUTH);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        add(panel);
        panel.setPreferredSize(new Dimension(500, 700));
        scrollPane.setMinimumSize(new Dimension(500, 700));

        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDBCconn cmds = new JDBCconn();
                
                DataBaseManager mng = DataBaseManager.getInstance();
                String tableName = mainPanel.getTitleName();
                message = inputField.getText();
                chatModel.addElement("You: " + message);
                try (Connection conn = mng.getConnection()) {
                	
                	System.out.println("file name(chat): " + fileName + "\n");
                    System.out.println("table name(chat): " + tableName + "\n");
                    System.out.println("table name(obj from chat window): " + obj.TableName);
                	FileContent = cmds.getFileData(conn, tableName, fileName);
                    String content = cmds.getFilesContent(conn, tableName);
                    String response_message1 = rmng.sendMessage(content, tableName); // nieużywane ale request niech idzie
                    System.out.println("Message from server: " + response_message1);
                    // Add debug print statement
                    
                    System.out.println("ChatWindow: Checking if FileObject is set...");
                    if (fileName != null) {
                    	Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                System.out.println("\n" +"File Content from chat: " + FileContent + "\n");
                                System.out.println("ChatWindow: FileObject is set. Calling FileObject...");
                                obj.call(FileContent);
                                String response_message = ChatModelUtil.generateAnswer(obj.prompt);
                                System.out.println("\n" + "AI response:" + response_message + "\n");
                                
                                chatModel.addElement("AI: " + response_message);
                            }
                        });

                        // Starting the new thread
                        thread.start();
                    } else {
                        System.out.println("ChatWindow: FileObject is not set.");
                        chatModel.addElement("AI: Error - File object is not set.");
                    }

                    inputField.setText("");
                } catch (SQLException | IOException e1) {
                    e1.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Error during request processing: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        chatHistory.setEnabled(false);

        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Chat History");
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Text files (*.txt)", "txt");
                fileChooser.setFileFilter(filter);

                int userSelection = fileChooser.showSaveDialog(ChatWindow.this);
                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    String fileToSave = fileChooser.getSelectedFile().toString();
                    if (!fileToSave.toLowerCase().endsWith(".txt")) {
                        fileToSave += ".txt";
                    }
                    saveChatHistoryToFile(fileToSave);
                }
            }
        });
    }
    //public void setFileContent(Connection conn, JDBCconn cmds, String fileName, String tableName) {
    //	cmds.getFileData(conn, tableName, fileName);
    	
    //#}
    public void setFileObject(FileObject object) {
        this.obj = object;
        System.out.println("ChatWindow: FileObject is set.");
    }
    public void settableData(String filename, String tablename) {
    	this.fileName = filename;
    	this.tableName = tablename;
    	fileLabel.setText("Currently used file: " + filename);
    	
    }
    public void setJLabel(String fileName) {
    	
    }
    private void saveChatHistoryToFile(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            for (int i = 0; i < chatModel.size(); i++) {
                writer.write(chatModel.getElementAt(i) + "\n");
            }
            writer.flush();
            JOptionPane.showMessageDialog(this, "Chat history saved successfully to " + fileName);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving chat history: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setBackgroundColor(Color color) {
        this.setBackground(color);
    }

    /*public void updateChatWithPrompt(String prompt) {
        chatModel.addElement("Prompt: " + prompt);
    }*/
}
