package layers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import socket.SMSocket;
import appstate.AppStateManager;
import core.Passport;
import core.RootPanel;

public class GraphicsPanel extends JPanel implements Runnable{
	
	public static final int WIDTH = 700;
	public static final int HEIGHT = 500;
	
	/* Core */
	Thread thread;
	RootPanel root;
	Passport _p;
	SMSocket socket;
	
	/* Dimensions of Root Panel */
	private int width;
	private int height;
	
	private BufferedImage drawing;
	
	private AppStateManager asm;
	
	private boolean running;
	
	private int FPS = 60;
	private long targetTime = 1000/FPS;
	private int frameCounter;
	
	private JPanel menu;
	
	public GraphicsPanel(JFrame parent,RootPanel root, Passport p, SMSocket socket){
		super();
		
		this.root = root;
		this.socket = socket;
		_p = p;
		
		System.out.println("GraphicsPanel reporting " + _p.getgameID() + _p.getUsername());
		setWidth(root.getWidth());
		setHeight(root.getHeight()-100);
		
		/*Panel Settings*/
		setLayout(new BorderLayout());
		setPreferredSize(new Dimension(width,height));
		setFocusable(false);
//		setBorder(BorderFactory.createLineBorder(Color.BLUE));
		setBounds(0,0,width,height);
		asm = new AppStateManager(this,_p,socket);
		
		/* Drawing ===================================================
		 * The main board for the graphics information. The width and
		 * the height set for this will adjust to fit the outer panel.
		 * Scaling will occur. 
		 * */
		drawing = GraphicsEnvironment.getLocalGraphicsEnvironment()
        		.getDefaultScreenDevice().getDefaultConfiguration()
                .createCompatibleImage(width, height);		
	}
	/* Kick off a new thread */
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			thread.start();
		}
	}
	
	private void init(){
		running = true;
	}
	
	public void update(){
		asm.update();
	}
	
	/* paint component */
	@Override public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D drawingBoard = drawing.createGraphics();
		asm.draw(drawingBoard);
		drawingBoard.dispose();
		
		Graphics tempGraphics = g.create();
		tempGraphics.drawImage(drawing, 0, 0, width, height, null);
		tempGraphics.dispose();
	
	}
	

	
	public void run(){
		init();
		
		long start, elapsed, wait;
		long startF,endF, elapsedF;
		long totalTime = 0;
		
		while(running){
			start = System.nanoTime();

			startF = System.nanoTime();

			update();
			
			elapsed = System.nanoTime() - start;
			endF = System.nanoTime();
			elapsedF = endF - startF;

			totalTime += elapsedF;
//			System.out.println(totalTime);

			if(totalTime > 1000000000){
				System.out.println(frameCounter);
				totalTime = 0;
				frameCounter = 0;
			}
			wait = targetTime - elapsed / 1000000;
			
			if(wait < 0){
				wait = 5;
			}
			
			try{
				Thread.sleep(wait);
			}catch(Exception e){
				e.printStackTrace();
			}
			frameCounter++;
			repaint();
		}
		
	}

	public int getHeight(){
		return height;
	}
	public void setHeight(int h){
		height = h;
	}
	public int getWidth(){
		return width;
	}
	public void setWidth(int w){
		width = w;
	}
	
	
	// Input
	public void keyTyped(KeyEvent key){
		
	}
	public void keyPressed(int k){		
		asm.keyPressed(k);
	}
	public void keyReleased(int k){
		asm.keyReleased(k);
	}
}
