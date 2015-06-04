package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

public class WarFrame extends JFrame {

	public WarFrame() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("War - by Hugo Grochau and Lucas Menezes");
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.setSize(screenSize);
		addPanels();
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setResizable(false);
		this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		this.setVisible(true);
	}

	private void addPanels() {
		MapPanel map = new MapPanel();
		UIPanel ui = new UIPanel();
		this.getContentPane().add(map);
		this.getContentPane().add(ui);
	}
}
