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
	private final double MULTIPLIER_X = 1.0;
	private final double MULTIPLIER_Y = 0.6;
	private Dimension mapSize;

	public MapPanel() {
		try {
			backgroundImage = new ImageIcon(
					"resources/maps/war_tabuleiro_com_nomes.png").getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		Dimension gameSize = WarGame.getGameSize();
		this.mapSize = new Dimension((int) (gameSize.width * MULTIPLIER_X),
				(int) (gameSize.height * MULTIPLIER_Y));
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

	public Dimension getMapSize() {
		return mapSize;
	}
}

class MapPanelMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent me) {
		boolean flag = false;
		System.out.printf("clicked point <%d,%d>\n", me.getX(), me.getY());
		for (Territory t : WarGame.getInstance().getMap().getTerritories()) {
			if (t.getPolygon().contains(me.getX(), me.getY())) {
				System.out.println(t.getName());
				flag = true;
				WarGame.getInstance().selectTerritory(t);

				return; // Cannot select twice
			}
		}
		WarGame.getInstance().selectTerritory(null); // none selected

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
