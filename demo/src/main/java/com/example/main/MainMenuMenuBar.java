package com.example.main;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;


public class MainMenuMenuBar extends JMenuBar {
	public MainMenuMenuBar(MainMenu Panel) {
		
		JMenu menuBar = new JMenu("Menu");
		JMenuItem wyjdz = new JMenuItem("Wyjdz");
		
		menuBar.add(wyjdz);
		wyjdz.addActionListener(new ActionListener() {
		        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		// wychodzimy z programu
        		SwingUtilities.getWindowAncestor(MainMenuMenuBar.this).dispose();

               
        	}
        	
		        	
        });
		
	}
	}
 
