package menustate;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import org.json.JSONObject;

import core.GameFrame;
import core.Passport;
import net.miginfocom.swing.MigLayout;
import utilities.SpringUtilities;

public class CreateState extends JPanel{
	
	PanelHandler parent;
	Passport _p;
	
	JLabel nameLabel;
	JTextField nameTextField;
	
	JLabel descriptionLabel;
	JTextField descriptionTextField;
	
	JLabel typeLabel;
	JComboBox typeDropdown;
	
	JLabel betLabel;
	JComboBox betDropdown;
	
	String[] typeOptions = { "Texas Holdem" };
	String[] minBetOptions = { "5","10","25","50","100" };

	// Send Post 
	String connectionName = "localhost:8080";
	ArrayList<String> errorCodes;
	boolean errors;
	boolean success;
	
	public CreateState(PanelHandler parent, Passport p){
		this.parent = parent;
		_p = p;
		
		JPanel panel = new JPanel(new MigLayout());
		
		nameLabel = new JLabel("Name");
		nameTextField = new JTextField(20);
		
		descriptionLabel = new JLabel("Description");
		descriptionTextField = new JTextField(20);

		typeLabel = new JLabel("Type");
		typeDropdown = new JComboBox<Object>(typeOptions);
		
		betLabel = new JLabel("Minimum Bet");
		betDropdown = new JComboBox<Object>(minBetOptions);
		
		JButton createButton = new JButton("Create");
		
		
		
		
		panel.add(nameLabel);
		panel.add(nameTextField,"wrap");
		panel.add(descriptionLabel);
		panel.add(descriptionTextField,"wrap");
		panel.add(typeLabel);
		panel.add(typeDropdown,"wrap");
		panel.add(betLabel);
		panel.add(betDropdown,"wrap");
		panel.add(createButton,"span");
		add(panel);
		
		createButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try{
					sendPost();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		});
		
	}
	private boolean sendPost() throws Exception{
//		try{
		
			success = false;
			URL url = new URL("http://"+ connectionName + "/api/games");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json");

			// Data to send
			JSONObject json = new JSONObject();
		    json.put("title", nameTextField.getText());	    
		    json.put("description", descriptionTextField.getText());
		    json.put("type",typeOptions[typeDropdown.getSelectedIndex()]);
		    json.put("minimum", minBetOptions[betDropdown.getSelectedIndex()]);

		    System.out.println(json.toString());
			String input = json.toString();
			
			OutputStream os = conn.getOutputStream();
			os.write(input.getBytes());
			os.flush();
			
			if(conn.getResponseCode() != HttpURLConnection.HTTP_OK){
				throw new RuntimeException("Failed: HTTP error code : " + conn.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String output;
			System.out.println("Output from Server ... \n");
			StringBuffer response = new StringBuffer();

			while((output = br.readLine()) != null){
				System.out.println(output);
				response.append(output);
			}
			conn.disconnect();
			
			JSONObject responseObject = new JSONObject(response.toString());
	  		success = (boolean)responseObject.get("success");
	  		
	  		if(!success){
		  		String message = (String)responseObject.get("message");
//				errorsLabel.setText("");
//				errorsLabel.setText(message);
	  		}else{
	  			String gameID = (String)responseObject.get("id");
	  			GameFrame gameFrame = new GameFrame(gameID,_p.getUsername());
	  			parent.setCard(PanelHandler.LOBBYPANEL);
	  			
	  		}
	  		
	  		
//	  		parent.setCurrentGameId(id);
	  		
	  		// Set Passport
//	  		_p.setGameId(id);
//	  		_p.setPosition(0);
	  		return success;
	
	}
}
