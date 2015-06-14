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

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.json.JSONArray;
import org.json.JSONObject;

import core.Passport;
import core.RootMenu;
import net.miginfocom.swing.MigLayout;

public class SignInState_LogIn extends JPanel{
	
	private final String USER_AGENT = "Mozilla/5.0";
	
	Passport _p;
	JLabel usernameLabel;
	JTextField usernameTextField;
	
	JLabel passwordLabel;
	JPasswordField passwordTextField;
	
	JTextArea errorsLabel;

	RootMenu root;
	SignInState parent;
	
	// Send Post 
	String connectionName = "localhost:8080";
	ArrayList<String> errorCodes;
	boolean errors;
	boolean success;
	
	
	public SignInState_LogIn(RootMenu root, SignInState sis, Passport p){
		super();
		
		this.root = root;
		this.parent = sis;
		_p = p;
		success = false;
		errorCodes = new ArrayList();
		errors = false;
		
		Color transparency = new Color(220,200,200,0);

		JPanel panel = new JPanel(new MigLayout());
		
		usernameLabel = new JLabel("Username");
		usernameTextField = new JTextField(20);
		
		passwordLabel = new JLabel("Password");
		passwordTextField = new JPasswordField(20);
		
		JButton signInButton = new JButton("Log In");
		JButton registerButton = new JButton("Register");
		
		errorsLabel = new JTextArea("");
		errorsLabel.setEditable(false);
		errorsLabel.setBackground(transparency);

		panel.add(usernameLabel);
		panel.add(usernameTextField,"wrap");
		panel.add(passwordLabel);
		panel.add(passwordTextField,"wrap");
		panel.add(signInButton);
		panel.add(registerButton,"wrap");
		panel.add(errorsLabel,"span");
		add(panel);
		
		signInButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				checkCredentials();
			}
		});
		registerButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				parent.showCard(SignInState.REGISTERPANEL);
			}
		});
		
	}
	private void checkCredentials(){
		
		errors = false;
		errorCodes = new ArrayList();
		
		if(!(usernameTextField.getText().matches("^[a-z0-9_-]{3,15}$"))){
			errorCodes.add("username invalid");
			Border border = BorderFactory.createLineBorder(Color.RED, 2);
			usernameTextField.setBorder(border);
			errors = true;
		}else{
			usernameTextField.setBorder(null);
		}
		char arr[] = passwordTextField.getPassword();
		String b = new String(arr);
		System.out.println(b);
		
		if(!(b.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"))){
			errorCodes.add("Password invalid");
			Border border = BorderFactory.createLineBorder(Color.RED, 2);
			passwordTextField.setBorder(border);
			errors = true;		
		}else{
			passwordTextField.setBorder(null);
		}
		
		if(errors){
			System.out.println("Errors");
			errorsLabel.setText("");
			for(String error : errorCodes){
				errorsLabel.setText(errorsLabel.getText() + error + "\n" );
			}
		}else{
			errorsLabel.setText("");
			errorsLabel.setText("Client validation successful! Sending information...");
			try{
				success = sendPost();				
			}catch(Exception e){
				e.printStackTrace();
			}
			if(success){
				parent.setVisible(false);
				root.grantAccess();
			}

		}
		
//		parent.grantAccess();
//		setVisible(false);
	}
	
	private boolean sendPost() throws Exception{
//		try{
		
			success = false;
			URL url = new URL("http://"+ connectionName + "/api/authenticate");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type","application/json");

			char arr[] = passwordTextField.getPassword();
			String pass = new String(arr);
			
			JSONObject json = new JSONObject();
		    json.put("username", usernameTextField.getText());	    
		    json.put("password", pass);
		    
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
				errorsLabel.setText("");
				errorsLabel.setText(message);
	  		}else{
	  			_p.setUsername(usernameTextField.getText());
	  		}
	  		
	  		
//	  		parent.setCurrentGameId(id);
	  		
	  		// Set Passport
//	  		_p.setGameId(id);
//	  		_p.setPosition(0);
	  		return success;
	
	}
}
