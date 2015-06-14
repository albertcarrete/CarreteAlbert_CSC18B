package core;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import menustate.AboutState;
import menustate.CreateState;
import menustate.LobbyState;
import menustate.PanelHandler;
import menustate.ProfileState;
import menustate.SettingsState;
import menustate.SignInState;

public class RootMenu extends JPanel implements Runnable,KeyListener{
	
	JFrame frame;
	private Thread thread;
	
	Passport _p;
	// screen size
	private int screenW;
	private int screenH;
	
	JPanel main;
	JPanel cards;
	
	SignInState signInState;
	PanelHandler panelHandler;
	
	final static String BUTTONPANEL = "Card with JButtons";
	final static String TEXTPANEL = "Card with JTextField";
	final static String CREATEPANEL = "Card with create panel";
	final static String SETTINGSPANEL = "Card with settings panel";
	final static String ABOUTPANEL = "Card with about panel";

	/* Javada JFrame -> RootMenu */
	public RootMenu(JFrame frame){
		super();
		this.frame = frame;
		
		_p = new Passport();
		
		setWidth(600);
		setHeight(300);
		
		setLayout(new BorderLayout());
        setPreferredSize(new Dimension(screenW, screenH));
		setFocusable(true);
		requestFocus();
		
		signInState = new SignInState(this,_p);
		panelHandler = new PanelHandler(_p);
		
		add(signInState);

	}
	
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	// Required by Runnable
	public void run(){
		
	}
	
	
	public int getHeight(){
		return screenH;
	}
	public int getWidth(){
		return screenW;
	}
	public void setHeight(int h){
		screenH = h;
	}
	public void setWidth(int w){
		screenW = w;
	}
	
	public void grantAccess(){
		add(panelHandler);
	}
	
	// Required by KeyListener
	public void keyTyped(KeyEvent key){
		
	}
	// Required by KeyListener
	public void keyPressed(KeyEvent key){
		
	}
	// Required by KeyListener
	public void keyReleased(KeyEvent key){
		
	}
}
