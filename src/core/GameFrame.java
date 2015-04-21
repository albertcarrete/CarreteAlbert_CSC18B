package core;
/*
 * GameFrame.java
 * Builds a new JFrame to display a specific poker game
 * instance. 
 * 
 * GameFrame > RootPanel > GraphicsPanel
 */
import javax.swing.JFrame;

public class GameFrame extends JFrame {
		
	public GameFrame(){
		super("Game");
		RootPanel panel = new RootPanel(this);
		
		setContentPane(panel);
//		setUndecorated(true); // removes OS title bar
		setResizable(false);
		pack();
		setVisible(true);
		toFront();
	}
}
