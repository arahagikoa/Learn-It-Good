package main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class FileObjectMenu extends JMenuBar {
	private FileObject basefield;
	
	public FileObjectMenu(FileObject Panel, FilePanel frame) {
		
		this.basefield = Panel;
        JMenu menu = new JMenu("\\/");
        this.add(menu);
        JDBCconn cmds = new JDBCconn();
        JMenuItem usun = new JMenuItem("Usuń");
        JMenuItem zmien = new JMenuItem("Zmień nazwę");
        
		usun.addActionListener(new ActionListener() {
        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		Container parent = basefield.getParent();
                if (parent instanceof JPanel) {
                	DataBaseManager mng = DataBaseManager.getInstance();
    		        String tableName = basefield.TableName;
    		        String fileName = basefield.docTitle.getText();
    		        try (Connection conn = mng.getConnection();) {
    		            cmds.deleteDataFile(conn, tableName, fileName);
    		            
    		            
    		            
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
        		String name = JOptionPane.showInputDialog("Podaj nową nazwę dla tego pliku");
        		String oldStorageName = basefield.docTitle.getText();
        		if (name != null || name != "") {
        			DataBaseManager mng = DataBaseManager.getInstance();
        			try (Connection conn = mng.getConnection();) {
        				String tableName = basefield.TableName;
        		        String fileName = basefield.docTitle.getText();
    		            String newFileName = name.replaceAll("\\s+", "_").replaceAll("[^a-zA-Z0-9_]", "");
            			
    		            cmds.changeDataFileName(conn, tableName, newFileName, fileName);
    		            
    		            
    		            basefield.docTitle.setText(newFileName);
    	                ((JPanel) basefield.getParent()).revalidate();
    	                ((JPanel) basefield.getParent()).repaint();
    		            
    		        } catch (SQLException e1) {
    		            e1.printStackTrace();
    		        }
        			
        		}
        		
        		
        	}
        	
        	
        });
        
        menu.add(usun);
        menu.add(zmien);
	}
}
