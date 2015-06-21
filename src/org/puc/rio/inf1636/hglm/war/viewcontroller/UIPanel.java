package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.puc.rio.inf1636.hglm.war.Util;
import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.WarLogic;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class UIPanel extends JPanel {

	private CardLayout layout;

	private JPanel startPanel;
	private JPanel gamePanel;
	private JPanel optionsPanel;
	private JPanel namesPanel;
	private List<JLabel> playerLabels = new LinkedList<JLabel>();
	private JLabel statusLabel;

	private JButton actionButton;
	private JButton endTurnButton;
	private JButton toggleMapDisplayButton;

	private Dimension size;

	private final double MULTIPLIER_X = 1.0;
	private final double MULTIPLIER_Y = 0.2;

	public UIPanel() {
		this.layout = new CardLayout();
		this.setLayout(layout);

		this.size = Util.getGameSize();
		this.size.height = (int) (this.size.height * MULTIPLIER_Y);
		this.size.width = (int) (this.size.width * MULTIPLIER_X);
		this.setMaximumSize(this.size);
		addStartUIPanel();

		layout.show(this, "Starting UI");
	}

	private void addStartUIPanel() {
		this.startPanel = new JPanel();
		this.startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.X_AXIS));
		JLabel l1 = new JLabel("Welcome to War. Enter the name of each player:");
		l1.setAlignmentY(Component.TOP_ALIGNMENT);
		this.startPanel.add(l1);

		final JLabel error = new JLabel(String.format(
				"You must enter at least %d players", WarLogic.MIN_PLAYERS));
		error.setBackground(Color.RED);
		error.setVisible(false);
		this.startPanel.add(error);

		final List<JTextField> playerNameTextFields = new LinkedList<JTextField>();
		for (int i = 0; i < WarLogic.MAX_PLAYERS; i++) { // 6 textFields for
															// each
															// possible player
			JTextField playerName = new JTextField();
			playerName.setMaximumSize(new Dimension(400, (int) (30)));
			playerName.setBackground(Player.playerColors[i]);
			playerNameTextFields.add(playerName);
			playerName.setCaretColor(Player
					.getForegroundColor(Player.playerColors[i]));
			playerName.setForeground(Player
					.getForegroundColor(Player.playerColors[i]));
			playerName.setAlignmentY(Component.TOP_ALIGNMENT);
			this.startPanel.add(playerName);
		}

		JButton submitButton = new JButton("Submit");
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				for (int i = 0; i < WarLogic.MAX_PLAYERS; i++) {
					JTextField playerNameTextField = playerNameTextFields
							.get(i);
					if (playerNameTextField.getText().length() > 0) {
						WarGame.getInstance().addPlayer(
								new Player(playerNameTextField.getText(),
										Player.playerColors[i]));
					}
				}
				if (WarGame.getInstance().getPlayers().size() >= WarLogic.MIN_PLAYERS) {
					WarGame.getInstance().startGame();
				} else {
					WarGame.getInstance().getPlayers().clear();
					error.setVisible(true);
				}

			}
		});
		submitButton.setAlignmentY(Component.TOP_ALIGNMENT);
		startPanel.add(submitButton);
		this.add(startPanel, "Starting UI");
	}

	public void update(boolean first) {
		this.updateGameUI(first);
		if (first) {
			layout.show(this, "Game UI");
		}
	}

	public void updateGameUI(boolean first) {
		if (first) {
			this.gamePanel = new JPanel();
			this.gamePanel
					.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));
			this.add(gamePanel, "Game UI");
		}
		this.updateNamesPanel(first);
		this.updateOptionsPanel(first);
	}

	private void updateNamesPanel(boolean first) {
		if (first) {
			this.namesPanel = new JPanel();
			this.namesPanel.setLayout(new BoxLayout(namesPanel,
					BoxLayout.Y_AXIS));
			this.namesPanel.setMaximumSize(new Dimension(this.size.width / 2,
					this.size.height));
			JLabel playersLabel = new JLabel("Players:");
			this.namesPanel.add(playersLabel);
			this.gamePanel.add(this.namesPanel);
		}
		int i = 0;
		for (Player p : WarGame.getInstance().getPlayers()) {
			boolean isCurrentPlayer = WarGame.getInstance()
					.getCurrentPlayerIndex() == i;
			JLabel playerLabel;
			if (first) {
				playerLabel = new JLabel();
				playerLabel.setOpaque(true);
				playerLabel.setBackground(p.getColor());
				playerLabel.setForeground(Player.getForegroundColor(p
						.getColor()));
				playerLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
				this.playerLabels.add(playerLabel);
				this.namesPanel.add(playerLabel);
			} else {
				playerLabel = this.playerLabels.get(i);
			}
			playerLabel.setText(String.format("%s (%d territories in total)%s",
					p.getName(), p.getNumberOfTerritories(),
					isCurrentPlayer ? "*" : ""));
			i++;
		}
	}

	public void updateOptionsPanel(boolean first) {
		if (first) {
			this.optionsPanel = new JPanel();
			this.optionsPanel.setLayout(new BoxLayout(optionsPanel,
					BoxLayout.Y_AXIS));
			this.optionsPanel.setMaximumSize(new Dimension(this.size.width / 2,
					this.size.height));
			this.statusLabel = new JLabel();
			this.statusLabel.setOpaque(true);
			this.statusLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

			this.actionButton = new JButton();
			this.actionButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.actionButton.setEnabled(false);
			ActionListener actionButtonListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					WarGame.getInstance().actionPerformed();
				}
			};
			actionButton.addActionListener(actionButtonListener);

			this.endTurnButton = new JButton("End Turn");
			this.endTurnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			ActionListener endTurnButtonListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					WarGame.getInstance().nextTurn();
				}
			};
			this.endTurnButton.addActionListener(endTurnButtonListener);

			this.toggleMapDisplayButton = new JButton("Toggle Map Display");
			this.toggleMapDisplayButton.setAlignmentX(Component.LEFT_ALIGNMENT);
			ActionListener toggleMapDisplayButtonListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					WarGame.getInstance().getWarFrame().getMapPanel()
							.toggleMapDisplay();
				}
			};
			toggleMapDisplayButton
					.addActionListener(toggleMapDisplayButtonListener);

			this.optionsPanel.add(this.statusLabel);
			this.optionsPanel.add(this.actionButton);
			this.optionsPanel.add(this.endTurnButton);
			this.optionsPanel.add(this.toggleMapDisplayButton);

			this.gamePanel.add(this.optionsPanel);
		}

		Player currentPlayer = WarGame.getInstance().getCurrentPlayer();
		String actionString = "No action";
		String statusString = "No status";
		this.actionButton.setEnabled(false);
		switch (WarGame.getInstance().getTurnState()) {
		case ATTACKING:
			statusString = "Select a country to attack from";
			break;
		case MOVING_ARMIES:
			statusString = "Select a country to move from";
			break;
		case PLACING_NEW_ARMIES:
			statusString = String
					.format("Select a country place armies in (You have %d armies left to place)",
							currentPlayer.getUnplacedArmies());
			if (WarGame.getInstance().getWarState().getSelectedTerritory() != null) {
				actionString = String.format("Place armies in %s", WarGame
						.getInstance().getWarState().getSelectedTerritory()
						.getName());
				this.actionButton.setEnabled(true);
			}
			break;
		case RECEIVING_LETTER:
			break;
		default:
			break;
		}
		this.statusLabel.setText(String.format("(%s's turn) %s",
				currentPlayer.getName(), statusString));
		this.statusLabel.setBackground(currentPlayer.getColor());
		this.statusLabel.setForeground(Player.getForegroundColor(currentPlayer
				.getColor()));
		this.actionButton.setText(actionString);
	}
}
