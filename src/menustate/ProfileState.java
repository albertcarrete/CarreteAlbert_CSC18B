package menustate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class ProfileState extends JPanel{
		
	JButton findButton;
	JTextArea playerInfo;
	JTextField playerToSearch;
	
	public ProfileState(){
		super();
		System.out.println("Attempting to build profileState");
		//Lobbies
//		setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
		setLayout(new BorderLayout());
//		setBorder(BorderFactory.createLineBorder(Color.red));		
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		findButton = new JButton("Find Player");
		playerToSearch = new JTextField(20);
		
		buttonPanel.add(playerToSearch);
		buttonPanel.add(findButton);
		
		playerInfo = new JTextArea();
		playerInfo.setEditable(false);
//		
//		JList list;
//		DefaultListModel listModel;
//		
//		listModel = new DefaultListModel();
//		listModel.addElement("Lobby Room 1");
//		listModel.addElement("Lobby Room 2");
//		listModel.addElement("Lobby Room 3");		
//		
//		list = new JList(listModel);
//        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
//        list.setSelectedIndex(0);
//        list.setVisibleRowCount(5);
//        JScrollPane listScrollPane = new JScrollPane(list);		
		add(buttonPanel,BorderLayout.PAGE_START);
        add(playerInfo,BorderLayout.CENTER);
        
        setVisible(true);
        
        
        findButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				if(findButton.getText() != ""){
					playerInfo.setText("Player information for " + playerToSearch.getText() + " will be displayed here");					
				}
			}
		});	
        
        
        
	}
	
	

}
