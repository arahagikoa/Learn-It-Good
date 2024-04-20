package main;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class BazaWiedzyFrame extends JPanel {
    
    JLabel title;
    JButton addBase;
    JPanel topPanel;
    JPanel centPanel;
    
    public BazaWiedzyFrame() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(500, 700));
        this.setBackground(Color.GRAY);
        
        
        // panel na instancje baz danych
        centPanel = new JPanel();
        centPanel.setLayout(new BoxLayout(centPanel, BoxLayout.Y_AXIS));
        centPanel.setBackground(Color.GRAY);
        centPanel.setSize(100, 200);
        
        
        
        addBase = new JButton("Dodaj");
        
        
        title = new JLabel("Istniejące bazy");
        Font titleFont = title.getFont();
        title.setFont(new Font(titleFont.getName(), Font.BOLD, 24));
        
        
        
        addBase.addActionListener(new ActionListener() {
        	BaseField field;
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		String name = JOptionPane.showInputDialog("Podaj nazwę dla nowej bazy");
        		if (name != null && !name.isEmpty()) {
        			field = new BaseField(BazaWiedzyFrame.this, name);
                    centPanel.add(field);
            		centPanel.revalidate(); 
                    centPanel.repaint();
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
}
