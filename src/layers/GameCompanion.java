package layers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import socket.SMSocket;
import core.Passport;
import net.miginfocom.swing.MigLayout;



/* Holds GUI information for a game instance */
public class GameCompanion extends JPanel implements ChangeListener{
	Passport _p;
	final SMSocket socket;
	
	private JButton send;
	private JTextArea messageTextArea;
	private JTextField messageTextField;
	
	
	private JButton fold;
	private JButton check;
	private JButton raise;
	
	private JSlider raiseAmount;
	private JTextField raiseInput;
	
	public GameCompanion(Passport p, SMSocket socket){
		
		super();
		
		_p=p;
		this.socket=socket;
		socket.linkGameCompanion(this);
		setLayout(new GridLayout(0,2));
	    
		JPanel panel = new JPanel(new MigLayout());
		JPanel panel2 = new JPanel(new MigLayout());

		messageTextField = new JTextField(15);
		messageTextArea = new JTextArea(5,20);
		messageTextArea.setLineWrap(true);
		JScrollPane scrollPane = new JScrollPane(messageTextArea); 
		
		messageTextArea.setEditable(false);
		panel.add(scrollPane,"dock north");
		panel.add(messageTextField);
		send = new JButton("Send");
		panel.add(send);
		
		
		send.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				
				
				
				if(!messageTextField.getText().equals("")){
					
					if(messageTextField.getText().startsWith("/")){
						
						String checkInput = messageTextField.getText().substring(1);
						if(checkInput.equals("join")){
							findSeat();
						}
						
					}else{
						sendMessageThroughSocket(messageTextField.getText());
						messageTextField.setText("");	
					}
					
				}
				
				
//				String data = messageTextArea.getText().trim();
//				if(data.equals("")){
//					messageTextArea.setText(messageTextField.getText());
//				}else{
//					messageTextArea.setText(messageTextArea.getText() + "\n" + messageTextField.getText());
//				}
//				messageTextField.setText("");

			}
		});
		
		fold = new JButton("Fold");
		check = new JButton("Check");
		raise = new JButton("Raise");
		
		JPanel buttonPanel = new JPanel();
			
		int MIN = 0;
		int MAX = 30;
		int INIT = 15; 
		raiseAmount = new JSlider(JSlider.HORIZONTAL,MIN,MAX,INIT);
		raiseAmount.addChangeListener(this);
		raiseAmount.setMajorTickSpacing(10);
		raiseAmount.setMinorTickSpacing(1);
		raiseAmount.setPaintTicks(true);
		raiseAmount.setPaintLabels(true);
		
		raiseInput = new JTextField(20);
		buttonPanel.add(fold);
		buttonPanel.add(check);
		buttonPanel.add(raise);
		
		panel2.add(buttonPanel,"dock north");
		panel2.add(raiseAmount);
		panel2.add(raiseInput);
		
		add(panel);
		add(panel2);
	}
	
	 public void stateChanged(ChangeEvent e) {
	        JSlider source = (JSlider)e.getSource();
	        if (!source.getValueIsAdjusting()) {
	        	System.out.println(source.getValue());
	        	raiseInput.setText(Integer.toString(source.getValue()));
	        }
	 }
	 public void ActionPerformed(ActionEvent e){
		 
	 }
	 public void sendMessageThroughSocket(String message){
			this.socket.sendMessageToRoom(_p.getgameID(),_p.getUsername(), messageTextField.getText());			
	 }
	 public void updateMessage(String message){
			String data = messageTextArea.getText().trim();
			if(data.equals("")){
				messageTextArea.setText(message);
			}else{
				messageTextArea.setText(messageTextArea.getText() + "\n" + message);
			}
			messageTextField.setText("");
	 }
	 public void findSeat(){
		 socket.findSeat(_p.getgameID(),_p.getUsername());
	 }
}
