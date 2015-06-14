package menustate;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import core.Passport;

public class PanelHandler extends JPanel{
	
	private Thread thread;
	
	// screen size
	private int screenW;
	private int screenH;
	
	JPanel main;
	JPanel cards;
	
	final static String LOBBYPANEL = "LobbyPanel";
	final static String TEXTPANEL = "Card with JTextField";
	final static String CREATEPANEL = "Card with create panel";
	final static String SETTINGSPANEL = "Card with settings panel";
	final static String ABOUTPANEL = "Card with about panel";

	
    LobbyState lobbyState;


	public PanelHandler(Passport _p){
		super();
		
		setWidth(600);
		setHeight(300);
		
		setLayout(new BorderLayout());
        setPreferredSize(new Dimension(screenW, screenH));
		setFocusable(true);
		requestFocus();
		
		main = new JPanel();
		JPanel sidebar = new JPanel();
		
		sidebar.setLayout(new GridLayout(0,1));
		JButton lobbiesButton = new JButton("Lobbies");
		JButton profileButton = new JButton("Profile");
		JButton createButton = new JButton("Create");
		JButton settingsButton = new JButton("Settings");
		JButton aboutButton = new JButton("About");
		JButton exitButton = new JButton("Exit");

		sidebar.add(lobbiesButton);
		sidebar.add(profileButton);
		sidebar.add(createButton);
		sidebar.add(settingsButton);
		sidebar.add(aboutButton);
		sidebar.add(exitButton);
		
		JPanel card1 = new JPanel();
	    card1.setLayout(new BorderLayout());
	    lobbyState = new LobbyState(main,_p);
	    card1.add(lobbyState);
	    
	    ProfileState profileState = new ProfileState();
	    JPanel card2 = new JPanel();
	    card2.setLayout(new BorderLayout());
	    card2.add(profileState);
	    
	    CreateState createState = new CreateState(this,_p);
	    JPanel card3 = new JPanel();
	    card3.setLayout(new BorderLayout());
	    card3.add(createState);
	    
	    SettingsState settingsState = new SettingsState();
	    JPanel card4 = new JPanel();
	    card4.setLayout(new BorderLayout());
	    card4.add(settingsState);
	    
	    AboutState aboutState = new AboutState();
	    JPanel card5 = new JPanel();
	    card5.setLayout(new BorderLayout());
	    card5.add(aboutState);
	    
	    
		cards = new JPanel(new CardLayout());
		cards.add(card1,LOBBYPANEL);
		cards.add(card2,TEXTPANEL);
		cards.add(card3,CREATEPANEL);
		cards.add(card4,SETTINGSPANEL);
		cards.add(card5,ABOUTPANEL);

		
		
		main.setBackground(Color.BLACK);
//		sidebar.setBackground(Color.WHITE);
		add(cards, BorderLayout.CENTER);
		sidebar.setPreferredSize(new Dimension(100, screenH));
		add(sidebar,BorderLayout.LINE_START);
		
		
		lobbiesButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards,LOBBYPANEL);
		        lobbyState.init();
			}
		});
		profileButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards,TEXTPANEL);
			}
		});
		createButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards,CREATEPANEL);
			}
		});
		settingsButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards,SETTINGSPANEL);
			}
		});
		aboutButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				CardLayout cl = (CardLayout)(cards.getLayout());
		        cl.show(cards,ABOUTPANEL);
			}
		});
		exitButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
		
		
	}
	
	public void setCard(String panel){
		CardLayout cl = (CardLayout)(cards.getLayout());
        cl.show(cards,panel);
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
}
