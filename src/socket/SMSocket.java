package socket;


import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

import org.json.JSONArray;
import org.json.JSONObject;

//import org.json.*;
//import org.json.simple.*;
//import org.json.simple.parser.JSONParser;
import java.util.Iterator;

import layers.GameCompanion;
//import appstate.AppState;
import appstate.GameState;
import core.Passport;

//import layers.GeneralGraphicsLayer;

public class SMSocket {
	/* Connect to URL */
//	private String URL;
	final Socket socket; // Current socket
	private GameState game;
//	private CharacterSelectState css;
	private long startTime = System.nanoTime(); 
	private long estimatedTime; 
	private long coordinateCounter;
	private long secondCounter;
	private long totalTime;
	private long timeSinceLastMessage;
	private boolean clockSockets;
//	DateTime dateTime;
	Passport _p;
	private String controllerName;
	
	GameCompanion gc;
	
	public SMSocket(Passport passport) throws URISyntaxException{
		
// 		Make Socket connection
		socket = IO.socket("http://localhost:8080");
//		socket = IO.socket("http://52.24.205.124/");
//		socket = IO.socket("https://msgameserver.herokuapp.com/");
		
		this.controllerName = controllerName;
		_p = passport;
		
		clockSockets = false;
		secondCounter = 0;
//		socket = IO.socket("http://localhost:8080/");


		
		
		// When the socket connects
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {  
				System.out.print("******************************* \n"
								+" SMSocket successfully connected \n "
								+"******************************* \n");
      	  	}
      	  
      	}).on("user:joined", new Emitter.Listener(){
      		
      		@Override
      		public void call(Object... args){
      			System.out.println(args[0] + " has joined!");
      			try{
              		JSONObject obj = (JSONObject)args[0];
              		String username = (String)obj.get("username");     

//              		game.addNetworkedPlayer(username);
              		
      			}catch(Exception e){
      				e.printStackTrace();
      			}
      		}
      		
      	}).on("private:join", new Emitter.Listener(){
      		
      		@Override
      		public void call(Object... args){
      			System.out.println(args[0] + " has joined!");
      			System.out.println("private:join socket received!");
      			
      			try{
      				// ORG.JSON
      				JSONObject responseObject = new JSONObject(args[0].toString());
      				String username 	= (String)responseObject.get("username");
      				String id 			= (String)responseObject.get("id");
      				int position 		= (int)responseObject.get("position");
      				
      				System.out.println("Player " + username + " is attempting to join game " + id + " at position " + position);
//      				css.getGameById(id);
//      				css.setPlayerProperties(position);
      				
      				//JSON SIMPLE
//      				JSONParser parser  = new JSONParser();
//      				JSONArray array = (JSONArray)args[0];
//      				JSONObject obj = (JSONObject)array.get(1);
//              		String username = (String)obj.get("username");
//              		System.out.println(username);
//              		game.addNetworkedPlayer(username);
              		
      			}catch(Exception e){
      				e.printStackTrace();
      			}
      			
      		}
      		
      	}).on("room:syncMessage", new Emitter.Listener() {

        	  @Override
        	  public void call(Object... args) {   
        		  
        		  try{
            		  JSONObject responseObject = new JSONObject(args[0].toString());
            		  String message = (String)responseObject.get("message");
            		  gc.updateMessage(message);
            		  
        		  }catch(Exception e){
        			  e.printStackTrace();
        		  }
        		  
        	  }

       }).on("private:message", new Emitter.Listener(){
      		
      		@Override
      		public void call(Object... args){
      			try{
      				// ORG.JSON
      				JSONObject responseObject = new JSONObject(args[0].toString());
      				String message = (String)responseObject.get("message");
      				System.out.println(message);
      				//JSON SIMPLE
//      				JSONParser parser  = new JSONParser();
//      				JSONArray array = (JSONArray)args[0];
//      				JSONObject obj = (JSONObject)array.get(1);
//              		String username = (String)obj.get("username");
//              		System.out.println(username);
//              		game.addNetworkedPlayer(username);
              		
      			}catch(Exception e){
      				e.printStackTrace();
      			}
      			
      		}
      		
      	}).on("event", new Emitter.Listener() {

      	  @Override
      	  public void call(Object... args) {   
      		  System.out.println("Message was received");
      		  System.out.println(args[0]);
      		  updateFrameMessages(args[0].toString());
      		  
//      		JSONObject obj = (JSONObject)args[0];
      		  
      	  }

      	}).on("sync:polling", new Emitter.Listener() {

        	  @Override
        	  public void call(Object... args) {   
        		  
        		  try{
        			  System.out.println(args[0]);
              		  JSONObject responseObject = new JSONObject(args[0].toString());
              		  String title 			= (String)responseObject.get("title");
              		  String description 	= (String)responseObject.get("description");
              		  JSONArray players 	= responseObject.getJSONArray("players");
          			  game.updateGame(players);	  
              		  
//              		  int playerSize = players.length();
//              		  for(int i = 0; i < playerSize; i++){
//              			  JSONObject player = players.getJSONObject(i);
//              		  }
//              		  int position = (int)responseObject.get("position");  
//              		  boolean ready = (boolean)responseObject.get("ready");
              		  
          		  }catch(Exception e){
          			  e.printStackTrace();
          		  }
        		  
        		  
        	  }

        }).on("sync:lobbies", new Emitter.Listener() {

        	  @Override
        	  public void call(Object... args) {   
        		  System.out.println("Request to sync lobby");
        		  
//        		  sendLobbySync(css.username,css.gameId,css.userReady,css.allReady);
      			//css.username
      			//css.gameId
//        		JSONObject obj = (JSONObject)args[0];
        		  
        	  }

        }).on("sync:lobbyPlayers", new Emitter.Listener() {

          	  @Override
          	  public void call(Object... args) {   
          		  System.out.println("Command to sync lobby");
          		  
          		  try{
              		  JSONObject responseObject = new JSONObject(args[0].toString());
              		  String username = (String)responseObject.get("username");
              		  int position = (int)responseObject.get("position");  
              		  boolean ready = (boolean)responseObject.get("ready");
              		  
          		  }catch(Exception e){
          			  e.printStackTrace();
          		  }
//          		JSONObject obj = (JSONObject)args[0];
          		  
          	  }

         }).on("user:replications", new Emitter.Listener(){
      		
      		/* Update NetPlayer Location */
      		@Override
      		public void call(Object... args){
      			coordinateCounter++;
      			
//      			System.out.println("Coordinates received:");
          		JSONObject obj = (JSONObject)args[0];
//      			estimatedTime = System.nanoTime() - startTime;
          		
      			
      			if(clockSockets){
      				timeSinceLastMessage = System.nanoTime() - startTime;
      				totalTime += timeSinceLastMessage;
      				
          			if(totalTime > 1000000000){
          				secondCounter++;


          				System.out.println("{MsgPerSec: " + coordinateCounter + ", Time: " + secondCounter + " }" );
          				coordinateCounter = 0;
          				totalTime = 0;
          			}else{
              			
          			}      			
          			
      			}
      			

          		try{
          			/* You absolutely need this conversion for the values to work */
          			String username = (String)obj.get("n");
              		double xpos = ((Number)obj.get("x")).doubleValue();
              		double ypos = ((Number)obj.get("y")).doubleValue();
              		long time 	= ((Number)obj.get("t")).longValue();

              		System.out.println("{ " + username + "," + ypos + "," + xpos + " }");
//              		System.out.println("{ " + counter + " }");
//              		game.findAndUpdateNetPlayer(username, xpos, ypos,time);
              		
          		}catch(Exception e){
          			e.printStackTrace();
          		}
          		
      			startTime = System.nanoTime();
          		clockSockets = true;
          		
      		}
      	})
      	/* Receives user coordinates and then sends it to findAndUpdateNetPlayer */
      	.on("user:replication", new Emitter.Listener(){
      		/* Update NetPlayer Location */
      		@Override
      		public void call(Object... args){
      			System.out.println("Socket Coordinates");
      			coordinateCounter++;
      			
//      			System.out.println("Coordinates received:");
      			try{
//	      			System.out.println(args[0]);
	  				JSONObject responseObject = new JSONObject(args[0].toString());
	  				Iterator<?> keys = responseObject.keys();
					
	  				while(keys.hasNext()){
						String key = (String)keys.next();
					    JSONObject subObj = (JSONObject)responseObject.get(key);
					    
					    	String username = (String)key;
					    	double xpos = ((Number)subObj.get("xpos")).doubleValue();
					    	double ypos = ((Number)subObj.get("ypos")).doubleValue();
					    	long time 	= ((Number)subObj.get("time")).longValue();

//					    System.out.println("{ " + (String)key + " ,x: " + ypos + ", y: " + xpos + " }");
	//				    System.out.println("{ " + counter + " }");
//					    game.findAndUpdateNetPlayer((String)key, xpos, ypos,time);
				}

      				System.out.println(responseObject);
      				
//      				responseObject.keys();
      				
//      				JSONObject responseObject = new JSONObject(args[0].toString());
//      				
//      				
//      				responseObject.keys();
      				
      				
//      				JSONParser parser = new JSONParser();
//      				JSONObject obj = (JSONObject)(parser.parse((String) args[0]));      				
////      	      		System.out.println(obj);
//      	      		
//      				for(Iterator<?> iterator = obj.keySet().iterator(); iterator.hasNext();) {
//      				    String key = (String) iterator.next();
////      				    System.out.println(key);
////      				    System.out.println(obj.get(key));
//      				    JSONObject subObj = (JSONObject)obj.get(key);
////          			String username = (String)obj.get("n");
//      				    double xpos = ((Number)subObj.get("xpos")).doubleValue();
//      				    double ypos = ((Number)subObj.get("ypos")).doubleValue();
//              		System.out.println("{ " + key + " ,x: " + ypos + ", y: " + xpos + " }");
//////              		System.out.println("{ " + counter + " }");
//              		game.findAndUpdateNetPlayer(key, xpos, ypos);
//      				    
//      				    
//      				}
      	      
      			}
      			catch(Exception e){
      				e.printStackTrace();
      			}
      	
//
//
//      	        // do something here with the value...
//      	    }
               
//      			if(clockSockets){
//      				timeSinceLastMessage = System.nanoTime() - startTime;
//      				totalTime += timeSinceLastMessage;
//      				
//          			if(totalTime > 1000000000){
//          				secondCounter++;
//          				debugger.sendToScreen("SckCrds", coordinateCounter);
//          				debugger.sendToScreen("Time", secondCounter);
//
//          				System.out.println("{MsgPerSec: " + coordinateCounter + ", Time: " + secondCounter + " }" );
//          				coordinateCounter = 0;
//          				totalTime = 0;
//          			}else{
//              			
//          			}      			
//          			
//      			}
      			

//          		try{
//          			/* You absolutely need this conversion for the values to work */
//          			String username = (String)obj.get("n");
//              		double xpos = ((Number)obj.get("x")).doubleValue();
//              		double ypos = ((Number)obj.get("y")).doubleValue();
//              		System.out.println("{ " + username + "," + ypos + "," + xpos + " }");
////              		System.out.println("{ " + counter + " }");
//              		game.findAndUpdateNetPlayer(username, xpos, ypos);
//              		
//          		}catch(Exception e){
//          			e.printStackTrace();
//          		}
          		
      			startTime = System.nanoTime();
          		clockSockets = true;
          		
      		}
      	}).on("action:fireball", new Emitter.Listener() {

        	  @Override
        	  public void call(Object... args) {   
        		  System.out.println("Fireball was shot!");
        		  try{
            		  JSONObject responseObject 	= new JSONObject(args[0].toString());
            		  String username 				= (String)responseObject.get("owner");
            		  String projID 				= (String)responseObject.get("projID");
            		  boolean facingRight 			= (boolean)responseObject.get("facingRight");
            		  double xpos 	= ((Number)responseObject.get("xpos")).doubleValue();
            		  double ypos 	= ((Number)responseObject.get("ypos")).doubleValue();
            		  long time 	= ((Number)responseObject.get("time")).longValue();

//					    game.findAndUpdateNetPlayerProjectile((String)username,projID, xpos, ypos,time,facingRight);
            		  
        		  }catch(Exception e){
        			  e.printStackTrace();
        		  }
//        		JSONObject obj = (JSONObject)args[0];
        		  
        	  }

       }).on("from_server", new Emitter.Listener(){
      		
      		@Override
      		public void call(Object... args){
      			estimatedTime = System.nanoTime() - startTime;
      			System.out.println("Recieving data " + args[0] + " : TLP -  " + estimatedTime);	
      			startTime = System.nanoTime();
//      			System.out.println("Time since last message: " + estimatedTime);
      		}
      	});
    	socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
        	  @Override
        	  public void call(Object... args) {
        		  
        		System.out.println("disconnected");
//        	    socket.emit("message", "user connected");
        	  }

        	});
    	if(socket.connected()){
    		socket.open();
    	}else{
    		socket.connect();
    	}
//    	socket.connect();
//		socket.open();
		
	}
	public void socketCapture(){
		socket.on("event", new Emitter.Listener() {

	      	  @Override
	      	  public void call(Object... args) {
	      		  System.out.println(args[0]);
//	      		JSONObject obj = (JSONObject)args[0];
	      		  
	      	  }
		});
	}
	public void userJoin(String username, String gameID){
		
	      try{
	    	  
		    JSONObject obj = new JSONObject();
		    System.out.println("sending " + username + " has joined room " + gameID);
	    	obj.put("username", username);
	      	obj.put("gameID", gameID);
			socket.emit("user:join",obj);
			
	      }catch(Exception e){
	      	e.printStackTrace();
	      } 
	      
	}

	/* *
	 * LOBBY/CHARACTER SELECT LOGIC
	 * 
	 * SendLobbySync --> private:syncCharacterSelect
	 * 
	 * */
	public void sendLobbySync(String username, String id, boolean userReady, boolean allReady){
		
		String gameID = id;
		try{
			JSONObject obj = new JSONObject();

			obj.put("username",username);
			obj.put("ready", userReady);
			obj.put("lobbyId",gameID);
			obj.put("position","outdated function was here");
			obj.put("allReady",allReady);
			socket.emit("private:syncCharacterSelect", obj);

		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	
	public void sendMessage(String message){
		System.out.println("Attempting to send socket message: " + message);
		socket.emit("message",message);
	}
	public void sendPlayerCoordinates(String username,double x, double y){
	      JSONObject obj = new JSONObject();
	      try{
	    	obj.put("username", username);
	      	obj.put("xpos", x);
	      	obj.put("ypos", y);
//			Timestamp timeStamp = new Timestamp(dateTime.getMillis());
			obj.put("timestamp", System.currentTimeMillis());
//	      	obj.put("timestamp", date.now);
	      }catch(Exception e){
	      	e.printStackTrace();
	      }
	      
		socket.emit("coordinates", obj);
	}
	public void sendProjectileCoordinates(String projecticleID, String projecticleOwner,double x, double y,boolean facingRight){
	      JSONObject obj = new JSONObject();
	      try{
	    	obj.put("owner",projecticleOwner);
	    	obj.put("projID", projecticleID);
	      	obj.put("xpos", x);
	      	obj.put("ypos", y);
	      	obj.put("facingRight",facingRight);
//			Timestamp timeStamp = new Timestamp(dateTime.getMillis());
			obj.put("timestamp", System.currentTimeMillis());
//	      	obj.put("timestamp", date.now);
	      }catch(Exception e){
	      	e.printStackTrace();
	      }
	      
		socket.emit("projecticleCoordinates", obj);
	}
	public void requestPrivateRoom(JSONObject obj){
		socket.emit("private:joinRoom", obj);
	}
	public void messagePrivateRoom(String id, String message){
		 JSONObject obj = new JSONObject();
	      try{
	    	obj.put("id", id);
	      	obj.put("message", message);
	      }catch(Exception e){
	      	e.printStackTrace();
	      }
	      socket.emit("private:message", obj);
	}
	public void setGame(GameState game){
		System.out.println("Set frame in socket");
		this.game = game;
	}
	public void updateFrameMessages(String message){
//		frame.updateMessages(message);
	}
	public void sendMessageToRoom(String gameID, String username, String message){
		
		 JSONObject obj = new JSONObject();
	      try{
	    	obj.put("username", username);
	    	obj.put("gameID", gameID);
	      	obj.put("message", message);
	      }catch(Exception e){
	      	e.printStackTrace();
	      }
	      socket.emit("room:message",obj);
	      
	}
	public void findSeat(String gameID, String username){
		 JSONObject obj = new JSONObject();
	      try{
	    	obj.put("username", username);
	    	obj.put("gameID", gameID);
	      }catch(Exception e){
	      	e.printStackTrace();
	      }
	      socket.emit("room:findSeat",obj);
	      
	}
	public void linkGameCompanion(GameCompanion gc){
		this.gc = gc;
	}
	public void disconnect(String username, String gameID){
		 JSONObject obj = new JSONObject();
	      try{
	    	obj.put("username", username);
	      	obj.put("gameID", gameID);
	      }catch(Exception e){
	      	e.printStackTrace();
	      }
		socket.emit("user:left",obj);
		socket.off();
	}
}
