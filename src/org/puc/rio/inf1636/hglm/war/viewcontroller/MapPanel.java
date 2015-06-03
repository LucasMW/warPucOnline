package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class MapPanel extends JPanel {

	JLabel label;
	
	
	public MapPanel()
	{
		Toolkit tk = Toolkit.getDefaultToolkit();
		
		System.out.print("map Painted inited\n");
		try
		{
		Image bgImage = tk.createImage("resources/maps/war_tabuleiro_com_nomes.png");
		this.getGraphics().drawImage(bgImage, 0, 0, null) ;
		}
		catch( Exception e)
		{
			System.out.print("image not found, sorry\n");
		}
				//new Image("resources/maps/war_tabuleiro_com_nomes.png");
		
		
		this.repaint();
		System.out.print("sfmlajfl\n");
		
	}
	
//	@Override
//	protected void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Toolkit tk = Toolkit.getDefaultToolkit();
//		
//		Image bgImage = tk.createImage("resources/maps/war_tabuleiro_com_nomes.png");
//				//new Image("resources/maps/war_tabuleiro_com_nomes.png");
//		
//		g.drawImage(bgImage, 0, 0, null);
//		System.out.print("sfmlajfl");
//		
//	}
}
