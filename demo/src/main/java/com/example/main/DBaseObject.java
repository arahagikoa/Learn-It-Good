package com.example.main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class DBaseObject extends JPanel {
	JLabel docTitle;
	JButton options;
	JPanel list;
	public DBaseObject(BazaWiedzyFrame frame, String Name) {
		this.setBackground(Color.GRAY);
		JPanel panel = new JPanel(new BorderLayout());

		panel.setPreferredSize(new Dimension(480, 50)); 
		
		panel.setBackground(Color.white);
		DBaseObjectMenu menu = new DBaseObjectMenu(this, frame);	
		docTitle = new JLabel(Name);
		docTitle.setText(Name);
		
		panel.add(docTitle, BorderLayout.WEST);
		panel.add(menu, BorderLayout.EAST);
		// this.add(options)
		// panel.add(options);
		this.add(panel);
		
	}
 }
