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
import layers.GameCompanion;
import layers.GraphicsPanel;


public class RootPanel extends JPanel implements Runnable, KeyListener{
	
	JFrame frame;
	JLayeredPane layeredPane;
	GraphicsPanel graphicsPanel;
	
	GameCompanion gc;
	
	private Thread thread;
	
	// screen size
	private int screenW;
	private int screenH;
	
	public RootPanel(JFrame frame){
		super();
		this.frame = frame;
		
		setWidth(700);
		setHeight(500);

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setLayout(new BorderLayout());
//        setLayout(null);
        setPreferredSize(new Dimension(screenW, screenH));
		setFocusable(true);
		requestFocus();
		
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(screenW,400));
		
		gc = new GameCompanion();

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
		graphicsPanel = new GraphicsPanel(frame, this);
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
