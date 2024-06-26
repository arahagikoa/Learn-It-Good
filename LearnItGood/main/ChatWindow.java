package main;
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
    public ChatWindow(MainPanel mainPanel) {
    	
  
    	
        JPanel panel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(chatHistory);
        
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JPanel bottomPanel = new JPanel(new FlowLayout());
        JButton save = new JButton("Save");
        
        bottomPanel.add(save);
        bottomPanel.add(inputField);
        
        panel.add(bottomPanel, BorderLayout.SOUTH);
        
        //panel.add(inputField, BorderLayout.SOUTH);
        add(panel);
        
        panel.setPreferredSize(new Dimension(500, 700));
        
        scrollPane.setMinimumSize(new Dimension(500, 700));
        //pack(); to działa tylko na JFrame
        //Obsługua wprowadzania tekstu 
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	JDBCconn cmds = new JDBCconn();
            	DataBaseManager mng = DataBaseManager.getInstance();
            	String tableName = mainPanel.getTitleName();
		        String message = inputField.getText();
		        chatModel.addElement("You: " + message);
		        try (Connection conn = mng.getConnection();) {
		        	
		        	
		            String content = cmds.getFilesContent(conn, tableName);
		            
		            String response_message = rmng.sendMessage(content);
		            
		            chatModel.addElement("AI:" + response_message);
		            
		            inputField.setText("");
		           
		            
		            
		            
		            
		        } catch (SQLException | IOException e1) {
		            e1.printStackTrace();
		            JOptionPane.showMessageDialog(panel, "Error during request processing: " + e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		            
		        }
            	
            	
               
            }
        });

        // Brak możliwość pisania w czacie
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

    //public static void main(String[] args) {
      //  new ChatWindow();
    //}
}
