package menustate;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import core.RootMenu;
import net.miginfocom.swing.MigLayout;

public class SignInState extends JPanel{
	
	RootMenu parent;
	
	JLabel usernameLabel;
	JTextField usernameTextField;
	
	JLabel passwordLabel;
	JPasswordField passwordTextField;
	
	JTextArea errorsLabel;
	
	public SignInState(RootMenu parent){
		super();
		
		this.parent = parent;
		
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
//				checkCredentials();
			}
		});
		
	}
	
	private void checkCredentials(){
		
		boolean errors = false;
		ArrayList<String> errorCodes = new ArrayList();
		
		if(!(usernameTextField.getText().matches("^[a-z0-9_-]{3,15}$"))){
			errorCodes.add("username invalid");
			errors = true;
		}
		char arr[] = passwordTextField.getPassword();
		String b = new String(arr);
		System.out.println(b);
		if(!(b.matches("((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})"))){
			errorCodes.add("Password invalid");
			errors = true;		
		}
		
		if(errors){
			System.out.println("Errors");
			errorsLabel.setText("");
			for(String error : errorCodes){
				errorsLabel.setText(errorsLabel.getText() + error + "\n" );
			}
		}else{
			errorsLabel.setText("");
			errorsLabel.setText("Validation successful! Sending information...");
			parent.grantAccess();
			setVisible(false);
		}
		
//		parent.grantAccess();
//		setVisible(false);
	}
}