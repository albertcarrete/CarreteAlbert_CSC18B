package menustate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import core.GameFrame;

public class LobbyState extends JPanel{
	JPanel main;
	
	public LobbyState(JPanel main){
		super();
		
		System.out.println("Attempting to build lobbyState");
		//Lobbies
//		setPreferredSize(new Dimension(main.getWidth(),main.getHeight()));
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createLineBorder(Color.red));

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
		JButton joinButton = new JButton("Join");
		buttonPanel.add(joinButton);
		
		JList list;
		DefaultListModel listModel;
		
		listModel = new DefaultListModel();
		listModel.addElement("Lobby Room 1");
		listModel.addElement("Lobby Room 2");
		listModel.addElement("Lobby Room 3");		
		
		list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);		
		add(buttonPanel,BorderLayout.PAGE_START);
        add(listScrollPane,BorderLayout.CENTER);
        
        setVisible(true);
        
		joinButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				GameFrame gameFrame = new GameFrame();
			}
		});
        
	}
	
}
