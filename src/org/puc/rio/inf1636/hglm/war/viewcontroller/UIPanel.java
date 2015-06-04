package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JPanel;

public class UIPanel extends JPanel {

	public UIPanel() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height = 200;
		this.setMinimumSize(screenSize);
		this.setBackground(Color.BLUE);
		JButton b = new JButton("FooBar");
		b.setBackground(Color.BLACK);
		this.add(b);
	}
}
