package socket;

import appstate.GameState;


public class SocketController {
	private SMSocket socket;
	private GameState game;
	
	public SocketController(SMSocket socket, GameState game){
		this.socket = socket;
		this.game = game;
	}
	
	// TODO Combine these two functions together
	public void linkUp(){
		socket.setGame(game);
//		frame.setSocket(socket);
	}
}
