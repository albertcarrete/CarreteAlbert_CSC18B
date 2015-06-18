package core;
/*
 * RootPanel.java
 * 
 * 
 * RootPanel > GraphicsPanel
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import socket.SMSocket;
import layers.GameCompanion;
import layers.GraphicsPanel;


public class RootPanel extends JPanel implements Runnable, KeyListener{
	
	GameFrame frame;
	JLayeredPane layeredPane;
	SMSocket socket;
	
	Passport _p;
	GraphicsPanel graphicsPanel;
	GameCompanion gc;
	
	private Thread thread;
	
	// screen size
	private int screenW;
	private int screenH;
	
	
	/* GameFrame -> RootPanel*/
	public RootPanel(GameFrame frame , Passport p, SMSocket socket){
		super();
		this.frame = frame;
		this.socket = socket;
		setWidth(700);
		setHeight(500);
		
		_p = p;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout(new BorderLayout());
//        setLayout(null);
        setPreferredSize(new Dimension(screenW, screenH));
		setFocusable(true);
		requestFocus();
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(screenW,400));
		
		gc = new GameCompanion(_p,this.socket);

		add(layeredPane, BorderLayout.CENTER);
		add(gc,BorderLayout.PAGE_END);
		
	}
	public void addNotify(){
		super.addNotify();
		if(thread == null){
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	public void run(){
		init();
	}

	private void init(){
		graphicsPanel = new GraphicsPanel(frame, this,_p,socket);
		layeredPane.add(graphicsPanel, new Integer(0));
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
	
	
	public void keyTyped(KeyEvent key){
		
	}
	public void keyPressed(KeyEvent key){
		graphicsPanel.keyPressed(key.getKeyCode());
//		gsm.keyPressed(key.getKeyCode());
	}
	public void keyReleased(KeyEvent key){
		graphicsPanel.keyReleased(key.getKeyCode());
	}
	
}
