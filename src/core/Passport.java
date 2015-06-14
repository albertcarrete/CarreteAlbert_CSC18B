package core;

public class Passport {

	// Player Credentials
	private String username;
	private String gameID;
	
	public Passport(){
		username = "";
		gameID = "";
	}
	public Passport(String u, String g){
		username = u;
		gameID = g;
	}
	
	public void setUsername(String s){
		this.username = s;
	}
	public String getUsername(){
		return this.username;
	}
	public void setgameID(String s){
		this.gameID = s;
	}
	public String getgameID(){
		return this.gameID;
	}

	
}
