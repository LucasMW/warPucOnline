package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.puc.rio.inf1636.hglm.war.Util;
import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class MapPanel extends JPanel {

	Image backgroundImage;
	private final double MULTIPLIER_X = 1.0;
	private final double MULTIPLIER_Y = 0.8;
	public double coordinatesMultiplierX;
	public double coordinatesMultiplierY;
	private Dimension mapSize;
	private List<JLabel> troopsLabels = new LinkedList<JLabel>();

	public MapPanel() {
		try {
			backgroundImage = new ImageIcon(
					"resources/maps/war_tabuleiro_completo.png").getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		Dimension gameSize = Util.getGameSize();
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

	public void updateTroopLabels(boolean first) {
		int i = 0;
		for (Territory t : WarGame.getInstance().getMap().getTerritories()) {
			JLabel centerLabel;
			if (first) {
				centerLabel = new JLabel("", SwingConstants.CENTER);
				centerLabel.setBounds((int) (t.getCenter().x),
						(int) (t.getCenter().y), 130, 20);
				centerLabel.setOpaque(true);
				this.troopsLabels.add(centerLabel);
				this.add(centerLabel);
			} else {
				centerLabel = this.troopsLabels.get(i);
			}
			centerLabel.setBackground(t.getOwner().getColor());
			centerLabel.setForeground(Player.getForegroundColor(t.getOwner().getColor()));
			centerLabel.setText(String.format("(%d) %s", t.getTroopCount(), t.getName()));
			centerLabel.setBorder(BorderFactory.createLineBorder(this.calculateBorderColor(t), 3));
			if (WarGame.getInstance().getMap().getCurrentTerritory() != null) {
				if (WarGame.getInstance().getMap().getCurrentTerritory().equals(t)) {
					this.setComponentZOrder(centerLabel, 0);
					centerLabel.setBackground(t.getOwner().getColor().darker());
				} else {
					this.setComponentZOrder(centerLabel, 2);
				}
				if (WarGame.getInstance().getMap().getCurrentTerritory().getNeighbors().contains(t)) {
					centerLabel.setBackground(t.getOwner().getColor().brighter());
				}
			}
			this.repaint();
			centerLabel.repaint();
			i++;
		}
	}

	public void selectTerritory(Territory t) {
		WarGame.getInstance().getMap().setCurrentTerritory(t);
		this.updateTroopLabels(false);
		WarGame.getInstance().getWarFrame().getUIPanel().updateSelectedLabel();
	}
	
	private Color calculateBorderColor(Territory t) {
		Color color = Color.BLACK;
		Territory currentTerritory = WarGame.getInstance().getMap().getCurrentTerritory();
		if (currentTerritory == null) {
			color = Color.BLACK;
		} else if (currentTerritory.equals(t)){
			color = Color.WHITE;
		} else if (currentTerritory.getNeighbors().contains(t)) {
			color = Color.RED;
		}
		return color;
	}

}

class MapPanelMouseListener implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent me) {
		System.out
				.printf("clicked point <%f,%f>\n",
						me.getX()
								/ WarGame.getInstance().getWarFrame()
										.getMapPanel().coordinatesMultiplierX,
						me.getY()
								/ WarGame.getInstance().getWarFrame()
										.getMapPanel().coordinatesMultiplierY);
		for (Territory t : WarGame.getInstance().getMap().getTerritories()) {
			if (t.getPolygon().contains(me.getX(), me.getY())) {
				WarGame.getInstance().getWarFrame().getMapPanel()
						.selectTerritory(t);
				return; // Cannot select twice
			}
		}
		WarGame.getInstance().getMap().setCurrentTerritory(null); // none selected
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

}
