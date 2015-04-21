package menustate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AboutState extends JPanel{
	private BufferedImage drawing;

	/* Dimensions of Root Panel */
	private int width;
	private int height;
	private BufferedImage image;

	
	public AboutState(){
		super();
		setLayout(null);
		width = 500;
		height = 300;
		drawing = GraphicsEnvironment.getLocalGraphicsEnvironment()
        		.getDefaultScreenDevice().getDefaultConfiguration()
                .createCompatibleImage(width, height);		
		
		try{
			image = ImageIO.read(getClass().getResourceAsStream("/images/aboutscreen.jpg"));		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		JLabel test = new JLabel("Version 1.01");
		test.setForeground(Color.WHITE);
		test.setBounds(200,100,100,100);
		add(test);
		
	}
	/* paint component */
	@Override public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		Graphics2D drawingBoard = drawing.createGraphics();
//		asm.draw(drawingBoard);
		drawingBoard.drawImage(image, 0, 0, width, height, null);
		drawingBoard.dispose();
		
		Graphics tempGraphics = g.create();
		tempGraphics.drawImage(drawing, 0, 0, width, height, null);
		tempGraphics.dispose();
	
	}
	
}
