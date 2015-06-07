package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;


public class WarFrame extends JFrame {

	public static double multX=1.0;
	public static double multY=1.0;
	private UIPanel ui;
	private MapPanel map;
	private static Dimension gameSize;
	public WarFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War - by Hugo Grochau and Lucas Menezes");
		Dimension screenSize =java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		gameSize =new Dimension((int)(screenSize.width*multX), (int)(screenSize.height*multY) );
		//gameSize=screenSize;
		 
		System.out.print(gameSize);
		this.setSize(gameSize);
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);
		addPanels();
		this.setVisible(true);
	}

	private void addPanels() {
		 map = new MapPanel();
		 ui = new UIPanel();
		this.getContentPane().add(map);
		this.getContentPane().add(ui);
	}
	public static Dimension getGameSize()
	{
		return gameSize;
	}
	public void selectedTerritory()
	{
		ui.updateSelectedLabel();
	}
	public void battleEnded()
	{
		ui.switchPlayer();
	}
	
}
