package appstate;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import layers.GraphicsPanel;

public class MainMenuState extends AppState implements ListSelectionListener{
	
	private BufferedImage bgImage;
	private GraphicsPanel graphicsPanel;
	/* Menu Item */
	private JLabel menu1;
	private JLabel menu2;
	private JLabel menu3;
	
	private JPanel menu;
	Font titleFont;
	
	private JList list;
	private DefaultListModel listModel;
	
	private static final String selectString = "Select";
	
	private JTextField lobbyName;
	
	public MainMenuState(AppStateManager asm, GraphicsPanel graphicsPanel){
		System.out.println("MainMenuState Constructor");
		this.asm = asm;
		this.graphicsPanel = graphicsPanel;
		
		try{
			bgImage = ImageIO.read(getClass().getResourceAsStream("/images/bg.gif"));			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		titleFont = new Font("Arial", Font.PLAIN, 30);
	}
	public void update(){
		
	}
	public void init(){
		graphicsPanel.removeAll();
		
		graphicsPanel.setFont(titleFont);
		
		menu = new JPanel();
		menu.setLayout(new GridLayout(0,1));
		menu.add(new JButton("Lobbies"));
		menu.add(new JButton("Profile"));
		menu.add(new JButton("Settings"));

		menu.setBounds(0, 0, 100, graphicsPanel.getHeight());
		graphicsPanel.add(menu);
		
		listModel = new DefaultListModel();
		listModel.addElement("Lobby Room 1");
		listModel.addElement("Lobby Room 2");
		listModel.addElement("Lobby Room 3");		
		
		list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        list.setPreferredSize(new Dimension(500,500));
        listScrollPane.setPreferredSize(new Dimension(500,500));
        listScrollPane.setBounds(200, 100, 500, 500);
        
        JButton selectButton = new JButton(selectString);
        SelectListener selectListener = new SelectListener(selectButton);
		selectButton.setActionCommand(selectString);
		selectButton.addActionListener(selectListener);
		
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.LINE_AXIS));
		buttonPane.add(selectButton);
		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
//		buttonPane.add(Box.createHorizontalStrut(5));
		buttonPane.setBounds(150, 0, 100, 50);
        graphicsPanel.add(listScrollPane);
        graphicsPanel.add(buttonPane);
        
        
//		menu1 = new JLabel("Lobbies");
//		menu2 = new JLabel("Profile");
//		menu3 = new JLabel("Settings");
//		
//		menu1.setBounds(100,0,200,100);
//		menu2.setBounds(250,0,200,100);
//		menu3.setBounds(400,0,200,100);
//		
//		graphicsPanel.add(menu1);
//		graphicsPanel.add(menu2);
//		graphicsPanel.add(menu3);

		
	}
	
	class SelectListener implements ActionListener, DocumentListener{
		private boolean alreadyEnabled = false;
		private JButton button;
		
		public SelectListener(JButton button){
			this.button = button;
		}
		
		public void actionPerformed(ActionEvent e){
			String name = lobbyName.getText();
		}
		public void insertUpdate(DocumentEvent e){
		}
		public void removeUpdate(DocumentEvent e){

		}
		public void changedUpdate(DocumentEvent e){
			
		}
		
		
	}
	
	
	public void draw(Graphics2D drawingBoard){
		
		drawingBoard.setColor(Color.DARK_GRAY);
		drawingBoard.drawImage(bgImage, 100, 0, graphicsPanel.getWidth()-100, graphicsPanel.getHeight(), null);
	}
	
	class LobbyListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			
		}
	}
    protected boolean alreadyInList(String name) {
        return listModel.contains(name);
    }

    //Required by DocumentListener.
    public void insertUpdate(DocumentEvent e) {
    }

    //Required by DocumentListener.
    public void removeUpdate(DocumentEvent e) {
    }

    //Required by DocumentListener.
    public void changedUpdate(DocumentEvent e) {
  
    }
    public void valueChanged(ListSelectionEvent e) {

    }
	
	public void keyPressed(int k){
		
	}
	public void keyReleased(int k){
		
	}
}
