import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class MenuBar extends JMenuBar {
    public Color menubackgroundColor;
    public Color czatbackgroundColor;
    public Color filesbackgroundColor;
    private MainPanel mainPanel;

    public MenuBar(MainPanel Panel) {
        this.mainPanel = Panel;
        JMenu menu = new JMenu("MENU");
        this.add(menu);

        JMenuItem powrot = new JMenuItem("Powrót do menu głównego");
        JMenuItem jezyk = new JMenuItem("Zmień język");

        JMenu motywMenu = new JMenu("Motyw");
        JMenuItem czat = new JMenuItem("Czat");
        JMenuItem pliki = new JMenuItem("Pliki");
        JMenuItem menu_bar = new JMenuItem("Pasek Menu");
        motywMenu.add(czat);
        motywMenu.add(pliki);
        motywMenu.add(menu_bar);

        JMenuItem autor = new JMenuItem("Autorzy");

        menu.add(powrot);
        menu.add(jezyk);
        menu.add(motywMenu);
        menu.add(autor);
        
        
        
        powrot.addActionListener(new ActionListener() {
        	
        	@Override 
        	public void actionPerformed(ActionEvent e) {
        		SwingUtilities.getWindowAncestor(MenuBar.this).dispose();

                // Show the main menu frame
                MainMenu.showMainMenu();
        	}
        	
        	
        });
        
        
        autor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MenuBar.this, "Aleksander Majkowski 325 649 \n Kamil Krzemiński 325 644");
            }
        });

        ActionListener motywActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menubackgroundColor = JColorChooser.showDialog(MenuBar.this, "Wybierz kolor", Color.white);
                if (menubackgroundColor != null) {
                    MenuBar.this.setBackground(menubackgroundColor);
                }
            }
        };

        ActionListener filesActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filesbackgroundColor = JColorChooser.showDialog(MenuBar.this, "Wybierz kolor", Color.white);
                if (filesbackgroundColor != null) {
                    mainPanel.setFilesBackgroundColor(filesbackgroundColor); 
                }
            }
        };

        ActionListener czatActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                czatbackgroundColor = JColorChooser.showDialog(MenuBar.this, "Wybierz kolor", Color.white);
                if (czatbackgroundColor != null) {
                    mainPanel.setCzatBackgroundColor(czatbackgroundColor); 
                }
            }
        };

        menu_bar.addActionListener(motywActionListener);
        czat.addActionListener(czatActionListener);
        pliki.addActionListener(filesActionListener);
    }

    //main do testów
    /*public static void main(String[] args) {
        JFrame frame = new JFrame();
        MenuBar bar = new MenuBar();
        frame.setJMenuBar(bar);
        frame.setLayout(new BorderLayout());
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int menuWidth = frame.getWidth() / 5;
        bar.setPreferredSize(new Dimension(menuWidth, bar.getPreferredSize().height));

        frame.setVisible(true);
    }*/
}
