package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class MapPanel extends JPanel {

	Image backgroundImage;
	private final double MULTIPLIER_X = 1.0;
	private final double MULTIPLIER_Y = 0.6;
	public double coordinatesMultiplierX;
	public double coordinatesMultiplierY;
	private Dimension mapSize;
	private List<JLabel> troopsLabels = new LinkedList<JLabel>();

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
		this.coordinatesMultiplierX = (mapSize.width / 1024.0);
		this.coordinatesMultiplierY = (mapSize.height / 768.0);
		this.setLayout(null);
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

	public void renderTroopLabels(boolean first) {
		int i = 0;
		for (Territory t : WarGame.getInstance().getMap().getTerritories()) {
			JLabel centerLabel;
			if (first) {
				centerLabel = new JLabel();
				centerLabel.setBounds((int) (t.getCenter().x),
						(int) (t.getCenter().y), 10, 10);
				centerLabel.setOpaque(true);
				this.troopsLabels.add(centerLabel);
				this.add(centerLabel);
			} else {
				centerLabel = this.troopsLabels.get(i);
			}
			centerLabel.setBackground(t.getOwner().getColor());
			centerLabel.setText(((Integer) t.getTroopCount()).toString());
			centerLabel.repaint();

			i++;
		}
	}
}

class MapPanelMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out.printf("clicked point <%d,%d>\n", me.getX(), me.getY());
		for (Territory t : WarGame.getInstance().getMap().getTerritories()) {
			if (t.getPolygon().contains(me.getX(), me.getY())) {
				System.out.println(t.getName());
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
