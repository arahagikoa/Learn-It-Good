package main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.*;


public class DBaseObjectMenu extends JMenuBar {
	private DBaseObject basefield;
	
	public DBaseObjectMenu(DBaseObject Panel, BazaWiedzyFrame frame) {
		this.basefield = Panel;
        JMenu menu = new JMenu("\\/");
        this.add(menu);
        JDBCconn cmds = new JDBCconn();
        JMenuItem uzyj = new JMenuItem("Użyj");
        JMenuItem usun = new JMenuItem("Usuń");
        JMenuItem zmien = new JMenuItem("Zmień nazwę");
        
		uzyj.addActionListener(new ActionListener() {
		        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		String name = Panel.docTitle.getText();
        		SwingUtilities.getWindowAncestor(DBaseObjectMenu.this).dispose();

                // Show the main menu frame
                MainMenu.showBaseFrame(name);
        	}
        	
        	
        });
		usun.addActionListener(new ActionListener() {
        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		Container parent = basefield.getParent();
                if (parent instanceof JPanel) {
                	DataBaseManager mng = DataBaseManager.getInstance();
    		        String storageName = basefield.docTitle.getText();
    		        try (Connection conn = mng.getConnection();) {
    		            cmds.deleteTable(conn, storageName);
    		            cmds.deleteRow(conn, storageName);
    		            
    		            
    		            ((JPanel) parent).remove(basefield);
    	                ((JPanel) parent).revalidate();
    	                ((JPanel) parent).repaint();
    		            
    		            
    		        } catch (SQLException e1) {
    		            e1.printStackTrace();
    		        }
                }
        	}
        	
        	
        });
		zmien.addActionListener(new ActionListener() {
        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		String name = JOptionPane.showInputDialog("Podaj nową nazwę dla tej bazy");
        		String oldStorageName = basefield.docTitle.getText();
        		if (name != null || name != "") {
        			DataBaseManager mng = DataBaseManager.getInstance();
        			try (Connection conn = mng.getConnection();) {
    		            
    		            String tableName = name.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
            			
    		            cmds.changeTableName(conn, oldStorageName, tableName);
    		            cmds.changeStorageName(conn, tableName, oldStorageName);
    		            
    		            basefield.docTitle.setText(tableName);
    	                ((JPanel) basefield.getParent()).revalidate();
    	                ((JPanel) basefield.getParent()).repaint();
    		            
    		        } catch (SQLException e1) {
    		            e1.printStackTrace();
    		        }
        			
        		}
        		
        		
        	}
        	
        	
        });
        
        
        menu.add(uzyj);
        menu.add(usun);
        menu.add(zmien);
        
        
        
	}

}
