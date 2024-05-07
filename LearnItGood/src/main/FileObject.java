package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FileObject extends JPanel {
	JLabel docTitle;
	JButton options;
	JPanel list;
	String TableName;
	
	public FileObject(FilePanel frame, String Name) {
		this.setBackground(Color.white);
		TableName = frame.mainPanel.getTitle();
		
		JPanel panel = new JPanel(new BorderLayout());

		panel.setPreferredSize(new Dimension(480, 50)); 
		
		panel.setBackground(Color.LIGHT_GRAY);
		FileObjectMenu menu = new FileObjectMenu(this, frame);	
		docTitle = new JLabel(Name);
		docTitle.setText(Name);
		
		panel.add(docTitle, BorderLayout.WEST);
		panel.add(menu, BorderLayout.EAST);
		// this.add(options)
		// panel.add(options);
		this.add(panel);
		
	}
	
}
