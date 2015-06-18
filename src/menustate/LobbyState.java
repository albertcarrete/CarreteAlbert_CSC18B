package menustate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import net.miginfocom.swing.MigLayout;
import models.LobbyModel;
import core.GameFrame;
import core.Passport;

public class LobbyState extends JPanel{
	JPanel main;
	Passport _p;
	
	JButton joinButton;
	JButton refreshButton;
	
	JTable table;
	LobbyModel lobbyModel;
	JTextArea output;
	String connectionName = "localhost:8080";
	String selectedLobbyID;
	String[][] results;
	private final String USER_AGENT = "Mozilla/5.0";

	public LobbyState(JPanel main, Passport p){
		super();
		JPanel panel = new JPanel(new MigLayout());
		_p = p;
		selectedLobbyID = "";
		System.out.println("Attempting to build lobbyState");
		//Lobbies
//		setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
		setLayout(new BorderLayout());
//		setBorder(BorderFactory.createLineBorder(Color.red));
		
		 String[][] tableData = {{"NA","NA","NA","NA"}};
		 
		 lobbyModel = new LobbyModel(tableData);
		 table = new JTable(lobbyModel);
		 
	        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//	        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
        JScrollPane tablePane = new JScrollPane(table);
		//Build output area.
        output = new JTextArea(10,50);
	        output.setEditable(false);
	        JScrollPane outputPane = new JScrollPane(output,
	                         ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	                         ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        
	    panel.add(tablePane);
		 
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		joinButton = new JButton("Join");
		buttonPanel.add(joinButton);
		refreshButton = new JButton("Refresh");
		buttonPanel.add(refreshButton);
		
	
		add(buttonPanel,BorderLayout.PAGE_START);
        add(panel,BorderLayout.CENTER);
        
        setVisible(true);
        

		
		joinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				selectedLobbyID = (String)lobbyModel.getValueAt(table.getSelectedRow(), 3);
				GameFrame gameFrame = new GameFrame(selectedLobbyID, _p.getUsername());
			}
		});
		refreshButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try{
					sendGet();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
        
	}
	
	public void init(){
		try{
			sendGet();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
private void sendGet() throws Exception{
		
		// URL to query
		String url = "http://"+ connectionName +"/api/games";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		// (optional) default is GET
		con.setRequestMethod("GET");
		// add request header
		con.setRequestProperty("User-Agent",USER_AGENT);
//		con.setRequestProperty("Content-Type","application/json");

		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
		
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		
		while((inputLine = in.readLine()) != null){
			response.append(inputLine);
		}
		in.close();
		
		
		System.out.println(response);

		
		JSONObject lobbies = new JSONObject(response.toString());
		Iterator<?> lobbyKeys = lobbies.keys();
		int x = 0;
		results = new String[lobbies.length()][4];

		while(lobbyKeys.hasNext()){
			
			String key = (String)lobbyKeys.next();
			System.out.println(key);
			

			JSONObject responseObject = lobbies.getJSONObject(key);
			
			System.out.println(responseObject);
									
			String title 		= responseObject.getString("title");
			String numPlayers 	= responseObject.getString("numPlayers");
			String type 		= responseObject.getString("type");
			String id			= key;
			
			results[x][0] = title;
			results[x][1] = numPlayers;
			results[x][2] = type;
			results[x][3] = id;
			
			x++;
		}
		
        lobbyModel = new LobbyModel(results);
        table.setModel(lobbyModel);
        table.repaint();	
		
		
		

//		System.out.println(response);
//		JSONArray prelimObject = new JSONArray(response.toString());
//		JSONObject testObject = prelimObject.getJSONObject(0);
//		
//		if(!testObject.has("success")){
//			System.out.println(response.toString());
//			String _id = "";
//				// Convert response from server to JSONArray (USING json.org.simple)
//				
//				boolean empty = true;
//				
//				JSONArray json = new JSONArray(response.toString());
//				int length = json.length();
//				results = new String[length][4];
//				
//				for(int i = 0; i < json.length(); i++){
//					
//					JSONObject responseObject = json.getJSONObject(i);
//					String title 		= responseObject.getString("title");
//					String numPlayers 	= responseObject.getString("numPlayers");
//					String type 		= responseObject.getString("type");
//					String id			= responseObject.getString("_id");
//					
//					results[i][0] = title;
//					results[i][1] = numPlayers;
//					results[i][2] = type;
//					results[i][3] = id;
//					
//				}
//				
//		        lobbyModel = new LobbyModel(results);
//		        table.setModel(lobbyModel);
//		        table.repaint();			
//		}else{
//			System.out.println("No lobbies available to view");
//		}
		

	}	
	
}
