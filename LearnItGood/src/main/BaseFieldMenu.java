package main;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class BaseFieldMenu extends JMenuBar {
	private BaseField basefield;
	
	public BaseFieldMenu(BaseField Panel) {
		this.basefield = Panel;
        JMenu menu = new JMenu("\\/");
        this.add(menu);
        
        JMenuItem uzyj = new JMenuItem("Użyj");
        JMenuItem usun = new JMenuItem("Usuń");
        JMenuItem zmien = new JMenuItem("Zmień nazwę");
        
		uzyj.addActionListener(new ActionListener() {
		        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		String name = Panel.docTitle.getText();
        		SwingUtilities.getWindowAncestor(BaseFieldMenu.this).dispose();

                // Show the main menu frame
                MainMenu.showBaseFrame(name);
        	}
        	
        	
        });
		usun.addActionListener(new ActionListener() {
        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		Container parent = basefield.getParent();
                if (parent instanceof JPanel) {
                    ((JPanel) parent).remove(basefield);
                    parent.revalidate();
                    parent.repaint();
                }
        	}
        	
        	
        });
		zmien.addActionListener(new ActionListener() {
        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		String newName = JOptionPane.showInputDialog("Podaj nową nazwę dla tej bazy");
        		if (newName != null || newName != "") {
        			Panel.docTitle.setText(newName);
        		}
        		
        		
        	}
        	
        	
        });
        
        
        menu.add(uzyj);
        menu.add(usun);
        menu.add(zmien);
        
        
        
	}

}
