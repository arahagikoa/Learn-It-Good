package com.example.main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;

public class BazaWiedzyFrame extends JPanel {
	
	private static final String SELECT_ALL_SQL = "SELECT * FROM storages";
    JLabel title;
    JButton addBase;
    JPanel topPanel;
    JPanel centPanel;
    
    public BazaWiedzyFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 700));
        this.setBackground(Color.GRAY);
        
        
        
        // w tej klasie trzymane są wszystkie komendy które będziemy wykonywali na bazie danych
        JDBCconn cmds = new JDBCconn();
        
        // panel na instancje baz danych
        centPanel = new JPanel();
        centPanel.setLayout(new BoxLayout(centPanel, BoxLayout.Y_AXIS));
        centPanel.setBackground(Color.GRAY);
        centPanel.setSize(100, 200);
        
        
        
        addBase = new JButton("Dodaj");
        
        
        title = new JLabel("Istniejące bazy");
        Font titleFont = title.getFont();
        title.setFont(new Font(titleFont.getName(), Font.BOLD, 24));
        
        SwingUtilities.invokeLater(() -> {
            DataBaseManager mng = DataBaseManager.getInstance();
            try (Connection conn = mng.getConnection()) {
                getAllStoragesAndGenerate(conn, centPanel);
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        
        
        addBase.addActionListener(new ActionListener() {
        	DBaseObject field;
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		String name = JOptionPane.showInputDialog("Podaj nazwę dla nowej bazy");
        		
        		if (name != null && !name.isEmpty()) {
        		    // Replace spaces with underscores
        		    String tableName = name.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
        		    
        		    if (!tableName.isEmpty()) {
        		        DataBaseManager mng = DataBaseManager.getInstance();
        		        
        		        try (Connection conn = mng.getConnection();) {
        		            cmds.createTable(conn, tableName);
        		            cmds.addNewRow(conn, tableName);
        		            getAllStoragesAndGenerate(conn, centPanel);
        		        } catch (SQLException e1) {
        		            e1.printStackTrace();
        		        }
        		    } else {
        		        // Invalid table name
        		        JOptionPane.showMessageDialog(null, "Podana nazwa jest nieprawidłowa.");
        		    }
        		}
        	}
        });

        topPanel = new JPanel(); 
        topPanel.setLayout(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(title, BorderLayout.WEST); 
        topPanel.add(addBase, BorderLayout.EAST); 
        topPanel.setPreferredSize(new Dimension(490, topPanel.getPreferredSize().height));
        
        panel.add(centPanel, BorderLayout.CENTER);
        panel.add(topPanel, BorderLayout.NORTH);
        
        
        add(panel);
    }
    void getAllStoragesAndGenerate(Connection conn, JPanel panel) {
        try {
            PreparedStatement prepStatement = conn.prepareStatement(SELECT_ALL_SQL);
            ResultSet resultSet = prepStatement.executeQuery();
            
            panel.removeAll();
            while (resultSet.next()) {
                int storageId = resultSet.getInt("storage_id");
                String storageName = resultSet.getString("storage_name");
                System.out.println("Storage ID: " + storageId + "   Storage Name: " + storageName);
                
                SwingUtilities.invokeLater(() -> {
                    DBaseObject field = new DBaseObject(BazaWiedzyFrame.this, storageName);
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
}
