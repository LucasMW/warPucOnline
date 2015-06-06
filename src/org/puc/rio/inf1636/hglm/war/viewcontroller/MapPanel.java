package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class MapPanel extends JPanel {

	Image backgroundImage;
	public static double multX=1.0;
	public static double multY=0.6;
	static Dimension mapSize;
	public MapPanel() {
		try {
			backgroundImage = new ImageIcon("resources/maps/war_tabuleiro_com_nomes.png").getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		 mapSize = new Dimension((int) (WarFrame.getGameSize().width*multX),(int)(WarFrame.getGameSize().height*multY));
		this.setPreferredSize(mapSize);
		this.setSize(mapSize);
		this.setMaximumSize(mapSize);
		this.setMinimumSize(mapSize);
		this.addMouseListener(new MapPanelMouseListener());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
	public static Dimension getMapSize()
	{	System.out.printf("as %d %d\n",mapSize.width,mapSize.height);
		return mapSize;
	}
}


class MapPanelMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out.printf("clicked point <%d,%d>\n",me.getX(),me.getY());
		for (Territory t : WarGame.getInstance().getMap().getTerritories()) {
			if (t.getPolygon().contains(me.getX(), me.getY())) {
				System.out.println(t.getName());
				
			}
		}

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

}
