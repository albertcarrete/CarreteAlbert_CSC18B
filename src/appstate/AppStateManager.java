package appstate;

import java.awt.Graphics2D;
import java.util.ArrayList;

import socket.SMSocket;
import core.Passport;
import layers.GraphicsPanel;

public class AppStateManager {
	
	private ArrayList<AppState> appStates;
	private int currentState;
	private GraphicsPanel graphicsPanel;
	Passport _p;
	SMSocket socket;
	public static final int LOGINSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int GAMESTATE = 2;
	public static final int SETTINGSSTATE = 3;
	public static final int TESTSTATE = 4;
	
	public AppStateManager(GraphicsPanel graphicsPanel, Passport p, SMSocket socket){
		
		appStates = new ArrayList<AppState>();
		currentState = GAMESTATE;
		this.graphicsPanel = graphicsPanel;
		this.socket = socket;
		_p = p;
		appStates.add(new LoginState(this,this.graphicsPanel));
		appStates.add(new MainMenuState(this,this.graphicsPanel));
		appStates.add(new GameState(this,this.graphicsPanel,_p,socket));

	}
	public void setState(int state){
		currentState = state;
		appStates.get(currentState).init();		
	}
	public void update(){
		appStates.get(currentState).update();
	}
	public void draw(Graphics2D g){
		appStates.get(currentState).draw(g);
	}
	public void keyPressed(int k){
		appStates.get(currentState).keyPressed(k);
	}
	public void keyReleased(int k){
		appStates.get(currentState).keyReleased(k);
	}
	
}
