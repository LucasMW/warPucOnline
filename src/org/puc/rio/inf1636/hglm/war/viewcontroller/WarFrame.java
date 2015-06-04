package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class WarFrame extends JFrame {

	public WarFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War - by Hugo Grochau and Lucas Menezes");
		this.setSize(new Dimension(1024, 968));
		this.getContentPane().setLayout(
				new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		this.setResizable(false);
		addPanels();
		this.setVisible(true);
	}

	private void addPanels() {
		MapPanel map = new MapPanel();
		UIPanel ui = new UIPanel();
		this.getContentPane().add(map);
		this.getContentPane().add(ui);
	}
}
