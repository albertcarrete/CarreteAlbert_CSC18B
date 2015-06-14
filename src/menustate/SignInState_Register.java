package menustate;

import java.awt.Color;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.json.JSONObject;

import net.miginfocom.swing.MigLayout;
import core.RootMenu;

public class SignInState_Register extends JPanel{
	
	JPanel root;
	SignInState parent;
	
	JLabel nameLabel;
	JTextField nameTextField;
	
	JLabel usernameLabel;
	JTextField usernameTextField;
	
	JLabel passwordLabel;
	JPasswordField passwordTextField;
	
	JTextArea errorsLabel;
	
	JLabel genderLabel;
	String[] genderOptions = { "Male","Female" };
	JComboBox genderDropdown;
	
	ArrayList<String> errorCodes;
	boolean errors;

	
//	String connectionName = "52.24.205.124";
	 String connectionName = "localhost:8080";
	private boolean success;
	
	public SignInState_Register(JPanel root, SignInState p){
		
		super();
		
		this.root = root;
		this.parent = p;
		
		success = false;
		
		errorCodes = new ArrayList();
		errors = false;

		Color transparency = new Color(220,200,200,0);

		JPanel panel = new JPanel(new MigLayout());

		nameLabel = new JLabel("Name");
		nameTextField = new JTextField(20);

		usernameLabel = new JLabel("Username");
		usernameTextField = new JTextField(20);
		
		passwordLabel = new JLabel("Password");
		passwordTextField = new JPasswordField(20);
		
		genderLabel = new JLabel("Gender");
		genderDropdown = new JComboBox(genderOptions);
		
		
		JButton backButton = new JButton("Back");
		JButton registerButton = new JButton("Create User");
		
		errorsLabel = new JTextArea("");
		errorsLabel.setEditable(false);
		errorsLabel.setBackground(transparency);
		
		nameTextField.setText("");
		usernameTextField.setText("");
		passwordTextField.setText("");


		
		panel.add(nameLabel);
		panel.add(nameTextField,"wrap");
		panel.add(usernameLabel);
		panel.add(usernameTextField,"wrap");
		panel.add(passwordLabel);
		panel.add(passwordTextField,"wrap");
		panel.add(genderLabel);
		panel.add(genderDropdown,"wrap");
		panel.add(backButton);
		panel.add(registerButton,"wrap");
		panel.add(errorsLabel,"span");
		add(panel);
		
		backButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				parent.showCard(SignInState.LOGINPANEL);
//				checkCredentials();
			}
		});
		registerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				try{
					success = sendPost();				
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				if(success){
					parent.showCard(SignInState.LOGINPANEL);
				}else{
					System.out.println("Error has occured with game creation");
				}
				
				parent.showCard(SignInState.LOGINPANEL);
			}
		});
		
		
	}
	private boolean sendPost() throws Exception{
//		try{
		
			Boolean success = false;
			URL url = new URL("http://"+ connectionName + "/api/users");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json");

			char arr[] = passwordTextField.getPassword();
			String pass = new String(arr);
			
			JSONObject json = new JSONObject();
		    json.put("username", usernameTextField.getText());	    
		    json.put("password", pass);
		    json.put("gender", genderOptions[genderDropdown.getSelectedIndex()]);
		    
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
	  		
//	  		parent.setCurrentGameId(id);
	  		
	  		// Set Passport
//	  		_p.setGameId(id);
//	  		_p.setPosition(0);
	  		return success;
	
	}
}
