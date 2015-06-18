package menustate;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.Border;

import core.Passport;
import core.RootMenu;
import net.miginfocom.swing.MigLayout;

public class SignInState extends JPanel{
	
	RootMenu parent;
	Passport _p;
	
	JPanel cards;

	JLabel usernameLabel;
	JTextField usernameTextField;
	
	JLabel passwordLabel;
	JPasswordField passwordTextField;
	
	JTextArea errorsLabel;
	
	final static String LOGINPANEL = "LoginPanel";
	final static String REGISTERPANEL = "RegisterPanel";
	
	
	public SignInState(RootMenu parent, Passport p){
		
		super();
		
		this.parent = parent;
		_p = p;
		
		JPanel card1 = new JPanel();
		card1.setLayout(new BorderLayout());
		SignInState_LogIn sis_Login 		= new SignInState_LogIn(parent,this,_p);
		
		JPanel card2 = new JPanel();
		card2.setLayout(new BorderLayout());
		SignInState_Register sis_Register 	= new SignInState_Register(parent,this);
		
		card1.add(sis_Login);
		card2.add(sis_Register);
		
		cards = new JPanel(new CardLayout());
		cards.add(card1,LOGINPANEL);
		cards.add(card2,REGISTERPANEL);
		add(cards,BorderLayout.CENTER);
		
		
	}
	
	
	public void showCard(String name){
		CardLayout cl = (CardLayout)(cards.getLayout());
		cl.show(cards, name);
	}

}