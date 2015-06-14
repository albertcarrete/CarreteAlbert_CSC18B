package core;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;




/*
 * GameFrame.java
 * Builds a new JFrame to display a specific poker game
 * instance. 
 * 
 * GameFrame > RootPanel > GraphicsPanel
 */
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import socket.SMSocket;

public class GameFrame extends JFrame {
	
	private String gameID;
	private String username;
	private SMSocket socket;
	Passport _p;

	public GameFrame(String gameID, String username){
		
		super("Game");
		_p = new Passport(username,gameID);
		
		try{
			if(socket==null){
				socket = new SMSocket(_p);							
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		this.setTitle("Game - " + gameID + " - " + username);
		RootPanel panel = new RootPanel(this,_p,socket);

		setContentPane(panel);
//		setUndecorated(true); // removes OS title bar
		setResizable(false);
		pack();
		setVisible(true);
		toFront();
		
		addWindowListener(new WindowAdapter() {
			  public void windowClosing(WindowEvent e) {
			    int confirmed = JOptionPane.showConfirmDialog(null, 
			        "Are you sure you want to exit the program?", "Exit Program Message Box",
			        JOptionPane.YES_NO_OPTION);

			    if (confirmed == JOptionPane.YES_OPTION) {
			    	socket.disconnect(_p.getUsername(),_p.getgameID());
			    	dispose();
			    }
			  }
			});
		
	}
	
	public String getGameID(){
		return gameID;
	}
	public String getUsername(){
		return username;
	}
}
