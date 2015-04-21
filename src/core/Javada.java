/* Copyright (C) 2015, Square Matter and/or its affiliates. All rights reserved. 
 * JAVADA Poker App
 * Build 0.0.1
 * */
package core;
/*
 * Javada.java
 */
import java.awt.*;
import javax.swing.JFrame;


public class Javada {
	
	static void renderSplashFrame(Graphics2D g, int frame){
		
        final String[] comps = {"foo", "bar", "baz"};
        g.setComposite(AlphaComposite.Clear);
        g.fillRect(150,160,200,40);
        g.setPaintMode();
        g.setColor(Color.BLACK);
        g.drawString("Loading "+comps[(frame/5)%3]+"...", 220, 190);	
        
	}
	public static void main(String[] args) {
		
		// Retrieves specs for current screen
		Dimension currScreenSize 	= Toolkit.getDefaultToolkit().getScreenSize();
		
		JFrame window = new JFrame("JAVADA");
		window.setLocation((int)((currScreenSize.getWidth()-600)/2), 300);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		final SplashScreen splash = SplashScreen.getSplashScreen();
		if(splash == null){
            System.out.println("SplashScreen.getSplashScreen() returned null");
            return;
		}
		Graphics2D g = splash.createGraphics();
		if(g == null){
			System.out.println("g is null");
			return;
		}
		window.setContentPane(new RootMenu(window));
		window.setResizable(false);
		window.pack();
//        for(int i=0; i<100; i++) {
//            renderSplashFrame(g, i);
//            splash.update();
            try {
                Thread.sleep(900);
            }
            catch(InterruptedException e) {
            }
//        }
        splash.close();

		window.setVisible(true);
		window.toFront();
	}

}
