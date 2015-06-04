package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

	Image backgroundImage;

	public MapPanel() {
		try {
			backgroundImage = new ImageIcon("resources/maps/war_tabuleiro_com_nomes.png").getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

		Dimension d = new Dimension(backgroundImage.getWidth(null), backgroundImage.getHeight(null));
		this.setPreferredSize(d);
		this.setSize(d);
	    setMinimumSize(d);
	    setMaximumSize(d);
		this.setLayout(null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}
