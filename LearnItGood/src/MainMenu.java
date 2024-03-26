import java.awt.*;
import javax.swing.*;


public class MainMenu extends JFrame {
	
	
	private BazaWiedzyFrame baseBox;
	
	
	public MainMenu() throws HeadlessException {
		this.setTitle("Learn it good");
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200, 800);
        this.setLocationRelativeTo(null);
        this.setLayout(new FlowLayout());
        
        
        baseBox = new BazaWiedzyFrame();
        
        this.add(baseBox);
      
        setVisible(true);
        
	}
	public static void showMainMenu() {
        JFrame mainMenuFrame = new JFrame("Main Menu");
        mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainMenuFrame.setSize(800, 600);
        mainMenuFrame.setLocationRelativeTo(null);
        mainMenuFrame.setContentPane(new MainMenu());
        mainMenuFrame.setVisible(true);
    }
	public static void showBaseFrame(String name) {
		MainPanel main = new MainPanel(name);
		main.setVisible(true);
	}
	public static void main(String[] args) {
        new MainMenu();
    }
	
	
}
