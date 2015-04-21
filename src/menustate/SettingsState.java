package menustate;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SettingsState extends JPanel{

	JLabel profileLabel;
	JTextField profileText;
	
	JLabel autoSeatLabel;
	JCheckBox autoSeatSelector;
	
	JLabel showPotLabel;
	JCheckBox showPotSelector;
	
	public SettingsState(){
		super();
		
		setLayout(null);
		
		profileLabel = new JLabel("Profile:");
		profileLabel.setBounds(10,10,100,25); // x , y, width, height
		add(profileLabel);
		
		profileText = new JTextField(20);
		profileText.setBounds(110,10,300,25);
		add(profileText);
		
		autoSeatLabel = new JLabel("Autoseat");
		autoSeatLabel.setBounds(10,50,100,25);
		add(autoSeatLabel);
		
		autoSeatSelector = new JCheckBox();
		autoSeatSelector.setBounds(110,50,300,25);
		add(autoSeatSelector);
		
		showPotLabel = new JLabel("Show Pot Total");
		showPotLabel.setBounds(10,90,100,25);
		add(showPotLabel);
		
		showPotSelector = new JCheckBox();
		showPotSelector.setBounds(110,90,300,25);
		add(showPotSelector);
	}
}
