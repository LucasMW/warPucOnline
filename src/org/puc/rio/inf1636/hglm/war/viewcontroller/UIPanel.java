package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class UIPanel extends JPanel {

	public UIPanel() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenSize.height = 200;
		this.setMinimumSize(screenSize);
		this.setMaximumSize(screenSize);
	}
}
