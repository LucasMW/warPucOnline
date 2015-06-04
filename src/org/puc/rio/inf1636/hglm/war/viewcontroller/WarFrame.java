package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Rectangle;

import javax.swing.JFrame;

public class WarFrame extends JFrame {

	public WarFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War - by Hugo Grochau and Lucas Menezes");
		addPanels();
		this.setBounds(new Rectangle(1000, 500));
		this.setVisible(true);
	}

	private void addPanels() {
		MapPanel map = new MapPanel();
		this.getContentPane().add(map);
	}
}
