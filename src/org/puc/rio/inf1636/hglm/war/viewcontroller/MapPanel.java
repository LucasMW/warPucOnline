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
import javax.swing.border.Border;

import org.puc.rio.inf1636.hglm.war.Util;
import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class MapPanel extends JPanel {

	private Image backgroundImage;
	private final double MULTIPLIER_X = 1.0;
	private final double MULTIPLIER_Y = 0.8;
	public double coordinatesMultiplierX;
	public double coordinatesMultiplierY;
	private Dimension mapSize;
	private List<JLabel> troopsLabels = new LinkedList<JLabel>();
	private boolean labelsHidden = false;

	public MapPanel() {
		this.setBackgroundImage("resources/maps/war_tabuleiro_completo.png");
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
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}

	public Dimension getMapSize() {
		return mapSize;
	}

	public void update(boolean first) {
		updateTroopLabels(first);
		if (first) {
			this.addMouseListener(new MapPanelMouseListener());
		}
	}

	public void updateTroopLabels(boolean first) {
		int i = 0;
		Territory selectedTerritory = WarGame.getInstance().getState()
				.getSelectedTerritory();
		for (Territory t : WarGame.getInstance().getMap().getTerritories()) {
			/* defaults */
			JLabel centerLabel;
			Border border = BorderFactory.createLineBorder(Color.BLACK, 3);
			int width = 130;
			int zOrder = 2;
			Color backgroundColor = t.getOwner().getColor();
			String text = String.format("(%d) %s", t.getTroopCount(),
					t.getName());

			if (first) {
				centerLabel = new JLabel("", SwingConstants.CENTER);
				centerLabel.setName(t.getName());
				centerLabel.setOpaque(true);
				centerLabel.addMouseListener(new MouseListener() {
					@Override
					public void mouseClicked(MouseEvent me) {
						JLabel label = (JLabel) me.getComponent();
						Territory t = WarGame.getInstance().getMap()
								.getTerritoryByName(label.getName());
						if (t != null) {
							WarGame.getInstance().selectTerritory(t);
						} else {
							System.out.println(String.format(
									"Couldn't find territory with name %s",
									label.getName()));
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
					}

					@Override
					public void mouseExited(MouseEvent e) {
					}

					@Override
					public void mousePressed(MouseEvent e) {
					}

					@Override
					public void mouseReleased(MouseEvent e) {
					}
				});
				this.troopsLabels.add(centerLabel);
				this.add(centerLabel);
			} else {
				centerLabel = this.troopsLabels.get(i);
			}
			if (selectedTerritory != null && selectedTerritory.equals(t)) {
				zOrder = 0;
				border = BorderFactory.createLineBorder(Color.RED, 3);
			} else if (t.getOwner().equals(
					WarGame.getInstance().getCurrentPlayer())) {
				zOrder = 1;
				backgroundColor = backgroundColor.darker();
				border = BorderFactory.createLineBorder(Color.WHITE, 3);
			} else {
				zOrder = 2;
				border = BorderFactory.createLineBorder(Color.BLACK, 3);
			}

			if (WarGame.getInstance().getState().isPlacing()) {
			} else if (WarGame.getInstance().getState().isAttacking()) {
				if (selectedTerritory != null) {
					/* neighbours */
					if (selectedTerritory.getNeighbors().contains(t)
							&& !selectedTerritory.getOwner().equals(
									t.getOwner())) {
						backgroundColor = backgroundColor.brighter();
						border = BorderFactory
								.createLineBorder(Color.YELLOW, 3);
					}
				}
			}

			if (this.labelsHidden) {
				text = String.format("%d", t.getTroopCount());
				width = 30;
			}

			if (!first) {
				this.setComponentZOrder(centerLabel, zOrder);
			}

			centerLabel.setBounds((int) (t.getCenter().x),
					(int) (t.getCenter().y), width, 20);
			centerLabel.setForeground(Player.getForegroundColor(t.getOwner()
					.getColor()));
			centerLabel.setText(text);
			centerLabel.setBorder(border);
			centerLabel.setBackground(backgroundColor);
			this.repaint();
			centerLabel.repaint();
			i++;
		}
	}

	public void setBackgroundImage(String path) {
		try {
			this.backgroundImage = new ImageIcon(path).getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public void toggleMapDisplay() {
		this.labelsHidden = !this.labelsHidden;
		if (labelsHidden) {
			this.setBackgroundImage("resources/maps/war_tabuleiro_com_nomes.png");
		} else {
			this.setBackgroundImage("resources/maps/war_tabuleiro_completo.png");

		}
		this.updateTroopLabels(false);
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
				WarGame.getInstance().selectTerritory(t);
				return; // Cannot select twice
			}
		}
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
