package appstate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONArray;
import org.json.JSONObject;

import socket.SMSocket;
import socket.SocketController;
import core.GameFrame;
import core.Passport;
import layers.GraphicsPanel;

public class GameState extends AppState{
	// Dependencies
	private AppStateManager asm;
	private GraphicsPanel graphicsPanel;
	private Passport _p;
	
	JPanel loginPanel;

	JTextField nameField;
	JTextField passwordField;
	JLabel nameLabel;
	JLabel passwordLabel;

	JButton login;
	JButton register;
	
	/*MULTIPLAYER*/
	private SMSocket socket;
	
	// images	
	private BufferedImage logoImage;
	private BufferedImage bgImage;
	private int logoHorizMargin;
	
	int w;
	int h;
	
	// Necessary Game Data
	String username;
	String gameID;
	int margin;
	Color tableOutline;
	String playerNames[];
	
	public GameState(AppStateManager asm, GraphicsPanel graphicsPanel, Passport p, SMSocket socket){
		
		System.out.println("GameState Constructor");
		this.asm = asm;
		this.graphicsPanel = graphicsPanel;
		_p = p;
		this.socket = socket;
		
		playerNames = new String[9];
		for(int i = 0; i < playerNames.length; i++ ){
			
			playerNames[i] = "Player " + i;
			
		}
		
		w = graphicsPanel.getWidth();
		h = graphicsPanel.getHeight();
		
		// Initialize Ncessary Game Data
		username = _p.getUsername();
		gameID = _p.getgameID();
		
		tableOutline = new Color(46,37,76);
		
		try{
			logoImage = ImageIO.read(getClass().getResourceAsStream("/images/logo.png"));
			bgImage = ImageIO.read(getClass().getResourceAsStream("/images/bg.gif"));
		}catch(Exception e){
			e.printStackTrace();
		}
		
//		int inputHeight 		= (int)(.10*h);
//		int inputWidth			= (int)(.80*w);
//		int loginPanelHeight 	= (int)(graphicsPanel.getHeight()*.50);
//		int loginPanelWidth 	= (int)(graphicsPanel.getWidth()*.50);
//		int horizMargin 		= (int)((graphicsPanel.getWidth() - loginPanelWidth)/2);
//		int vertMargin 			= (int)((graphicsPanel.getHeight() - loginPanelHeight)/2);
//		
		logoHorizMargin 		= (int)((graphicsPanel.getWidth() - 200)/2);
		margin = 50;

//		loginPanel = new JPanel();
//		loginPanel.setLayout(null);
//		loginPanel.setPreferredSize(new Dimension(loginPanelWidth,loginPanelHeight));
//		loginPanel.setBounds(horizMargin, vertMargin, loginPanelWidth, loginPanelHeight);
//		loginPanel.setBackground(Color.DARK_GRAY);
//		
//		nameLabel = new JLabel("Name");
//		nameLabel.setBounds(0,inputHeight*0,loginPanelWidth,inputHeight);
//		
//		nameField = new JTextField(20);
//		nameField.setBounds(0, inputHeight*1, inputWidth, inputHeight);
//
//		passwordLabel = new JLabel("Password");
//		passwordLabel.setBounds(0,inputHeight*2,inputWidth,inputHeight);
//		
//		passwordField = new JTextField(20);
//		passwordField.setBounds(0,inputHeight*3,inputWidth,inputHeight);
//		
//		login = new JButton("Login");
//		login.setBounds(0,inputHeight*4,(int)(inputWidth/2),inputHeight);
//		register = new JButton("Register");
//		register.setBounds((int)inputWidth/2,inputHeight*4,(int)(inputWidth/2),inputHeight);

		init();
		
//		register.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent event){
//				register();
//			}
//		});
		
		
	}
	
	public void init(){
		
		/*Attempt to connect to socket*/
		username 	= _p.getUsername();
		gameID 		= _p.getgameID();
		
		try{
//			this.socket = socket;
//			socket = new SMSocket(_p);
			socket.setGame(this);
	    	SocketController socketController = new SocketController(socket, this);
			socketController.linkUp();
			socket.userJoin(username,gameID);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	public void update(){
		
	}
	public void draw(Graphics2D drawingBoard){
		drawingBoard.drawImage(bgImage, 0, 0, graphicsPanel.getWidth(), graphicsPanel.getHeight(), null);
		drawingBoard.drawImage(logoImage, logoHorizMargin, 150, 200,70,null);
		
		
		double calc = 2 + Math.sqrt(Math.exp(25-(1-4)));
		
		drawingBoard.setColor(tableOutline);
		float r = 300;
		drawingBoard.drawOval(margin, margin, (int)w - (margin*2), (int)h - (margin*2));
		
		drawingBoard.setColor(Color.WHITE);
	
		int rotation = 0;
		for(int i = 0; i < 9; i++){
			drawingBoard.drawString(playerNames[i], xGetOnEllipse(rotation), yGetOnEllipse(rotation));
			rotation += 40;
		}
		
//		drawingBoard.drawString("Player1", xGetOnEllipse(40), yGetOnEllipse(40));
//		drawingBoard.drawString("Player2", xGetOnEllipse(80), yGetOnEllipse(80));
//		drawingBoard.drawString("Player3", xGetOnEllipse(120), yGetOnEllipse(120));
//		drawingBoard.drawString("Player4", xGetOnEllipse(160), yGetOnEllipse(160));
//		drawingBoard.drawString("Player5", xGetOnEllipse(200), yGetOnEllipse(200));
//		drawingBoard.drawString("Player6", xGetOnEllipse(240), yGetOnEllipse(240));
//		drawingBoard.drawString("Player7", xGetOnEllipse(280), yGetOnEllipse(280));
//		drawingBoard.drawString("Player8", xGetOnEllipse(320), yGetOnEllipse(320));
//		drawingBoard.drawString("Player9", xGetOnEllipse(360), yGetOnEllipse(360));

//		drawingBoard.setColor(Color.DARK_GRAY);
//		drawingBoard.drawRect(0, 0, 200, 200);
//    	drawingBoard.drawString("Gathering information...",180,120);

	}
	public void register(){
		asm.setState(AppStateManager.MAINMENUSTATE);
//		GameFrame frame = new GameFrame();
	}
	
	private int xGetOnEllipse(int rad){
		
		float width = w - (margin*2);
		float ePX = (float)((width/2) * Math.cos(Math.toRadians(rad)) + ((w/2)));
		
		return Math.round(ePX);
	}
	private int yGetOnEllipse(int rad){
//		int ePY = (int)(margin+(h/2)) + (int) ((h-(margin*2))/2 * Math.sin(rad * (Math.PI / 180F)));
		float height = h - (margin*2);
		float ePY = (float)((height/2) * Math.sin(Math.toRadians(rad))) + ((h/2));

		return Math.round(ePY);
	}
	
	public void updateGame(JSONArray players){
		
		try{
		JSONArray playersArray = players;
		
		int playerSize = players.length();
		for(int i = 0; i < playerSize; i++){
			JSONObject player = playersArray.getJSONObject(i);
			playerNames[i] = (String)player.get("username");
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public void keyPressed(int k){
		
	}
	public void keyReleased(int k){
		
	}
	
}
