package utilities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class MotionPanel extends JPanel{
	
    private Point initialClick;
    private JFrame parent;
    private JLabel title;
    private JLabel close;
    
    public MotionPanel(final JFrame parent){
	    this.parent = parent;
	    System.out.println(parent.getWidth());
	    
	    JPanel panel = new JPanel(new MigLayout());
	    
	    setLayout(new BorderLayout());
	//	setPreferredSize(new Dimension(parent.getWidth(),200));
		setBounds(0,0,parent.getWidth(),100);
		setBorder(BorderFactory.createLineBorder(Color.red));
		
		title = new JLabel("Title of the poker room");
		close  = new JLabel("X");
	    panel.add(title,"width 10:200:200");
	    panel.add(close, "dock east");

	    add(panel, BorderLayout.CENTER);
	    addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	            initialClick = e.getPoint();
	            getComponentAt(initialClick);
	        }
	    });
	    
	    close.addMouseListener(new MouseAdapter(){
	    	public void mouseClicked(MouseEvent e){
	    		parent.dispose();
	    	}
	    });
	    addMouseMotionListener(new MouseMotionAdapter() {
	        @Override
	        public void mouseDragged(MouseEvent e) {
	
	            // get location of Window
	            int thisX = parent.getLocation().x;
	            int thisY = parent.getLocation().y;
	
	            // Determine how much the mouse moved since the initial click
	            int xMoved = (thisX + e.getX()) - (thisX + initialClick.x);
	            int yMoved = (thisY + e.getY()) - (thisY + initialClick.y);
	
	            // Move window to this position
	            int X = thisX + xMoved;
	            int Y = thisY + yMoved;
	            parent.setLocation(X, Y);
	        }
	    });
    }
}