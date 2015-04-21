package appstate;

import java.awt.Graphics2D;
import java.util.ArrayList;

import layers.GraphicsPanel;

public class AppStateManager {
	
	private ArrayList<AppState> appStates;
	private int currentState;
	private GraphicsPanel graphicsPanel;
	
	public static final int LOGINSTATE = 0;
	public static final int MAINMENUSTATE = 1;
	public static final int LOBBYSTATE = 2;
	public static final int SETTINGSSTATE = 3;
	public static final int GAMESTATE = 4;
	
	public AppStateManager(GraphicsPanel graphicsPanel){
		
		appStates = new ArrayList<AppState>();
		currentState = LOGINSTATE;
		this.graphicsPanel = graphicsPanel;
		appStates.add(new LoginState(this,this.graphicsPanel));
		appStates.add(new MainMenuState(this,this.graphicsPanel));

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
