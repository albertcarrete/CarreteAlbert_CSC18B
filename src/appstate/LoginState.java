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

import core.GameFrame;
import layers.GraphicsPanel;

public class LoginState extends AppState{
	// Dependencies
	private AppStateManager asm;
	private GraphicsPanel graphicsPanel;

	JPanel loginPanel;

	JTextField nameField;
	JTextField passwordField;
	JLabel nameLabel;
	JLabel passwordLabel;

	JButton login;
	JButton register;
	
	// images	
	private BufferedImage logoImage;
	private BufferedImage bgImage;
	private int logoHorizMargin;
	
	public LoginState(AppStateManager asm, GraphicsPanel graphicsPanel){
		System.out.println("LoginState Constructor");
		this.asm = asm;
		this.graphicsPanel = graphicsPanel;
		
		int w = 500;
		int h = 250;
		
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
//		
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
//		loginPanel.add(nameLabel);
//		loginPanel.add(nameField);
//		loginPanel.add(passwordLabel);
//		loginPanel.add(passwordField);
//		loginPanel.add(login);
//		loginPanel.add(register);
//		graphicsPanel.add(loginPanel);
//		graphicsPanel.add(nameField);
	}
	
	public void update(){
		
	}
	public void draw(Graphics2D drawingBoard){
		drawingBoard.drawImage(bgImage, 0, 0, graphicsPanel.getWidth(), graphicsPanel.getHeight(), null);
		drawingBoard.drawImage(logoImage, logoHorizMargin, 150, 200,70,null);
		
//		drawingBoard.setColor(Color.DARK_GRAY);
//		drawingBoard.drawRect(0, 0, 200, 200);
//    	drawingBoard.drawString("Gathering information...",180,120);

	}
	public void register(){
		asm.setState(AppStateManager.MAINMENUSTATE);
		GameFrame frame = new GameFrame();
	}
	
	public void keyPressed(int k){
		
	}
	public void keyReleased(int k){
		
	}
	
}
