package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import org.puc.rio.inf1636.hglm.war.model.Card;
import org.puc.rio.inf1636.hglm.war.model.Player;

@SuppressWarnings("serial")
public class UIPanel extends JPanel {

	private CardLayout layout;

	private JPanel startPanel;
	private JPanel gamePanel;
	private JPanel optionsPanel;
	private JPanel namesPanel;
	private List<JLabel> playerLabels = new LinkedList<JLabel>();
	private JLabel statusLabel;

	private JButton actionButton;
	private JButton showObjectiveButton;
	private JButton showCardsButton;

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
		final JLabel l1 = new JLabel(
				"Welcome to War. Enter the name of each player:");
		l1.setMaximumSize(new Dimension(300, 50));
		l1.setAlignmentY(Component.TOP_ALIGNMENT);
		this.startPanel.add(l1);

		final List<JTextField> playerNameTextFields = new LinkedList<JTextField>();
		/* 6 textFields for each possible player */
		for (int i = 0; i < WarLogic.MAX_PLAYERS; i++) {
			JTextField playerName = new JTextField();
			playerName.setMaximumSize(new Dimension(400, 50));
			playerName.setBackground(Player.playerColors[i]);
			playerName.setFont(new Font("Arial", Font.PLAIN, 34));
			playerNameTextFields.add(playerName);
			playerName.setCaretColor(Player
					.getForegroundColor(Player.playerColors[i]));
			playerName.setForeground(Player
					.getForegroundColor(Player.playerColors[i]));
			playerName.setAlignmentY(Component.TOP_ALIGNMENT);
			this.startPanel.add(playerName);
		}

		JButton submitButton = new JButton("Submit");
		submitButton.setMaximumSize(new Dimension(100, 50));
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
					l1.setText("<html>Welcome to War. Enter the name of each player:<br />Please enter at least 3 players!</html>");
					l1.setOpaque(true);
					l1.setBackground(Color.RED);
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
			this.namesPanel.setMaximumSize(new Dimension(this.size.width / 8,
					this.size.height));
			JLabel playersLabel = new JLabel("Players:");
			this.namesPanel.add(playersLabel);
			this.gamePanel.add(this.namesPanel);
		}
		int i = 0;
		for (Player p : WarGame.getInstance().getPlayers()) {
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
			StringBuilder sb = new StringBuilder();
			for (Card c : p.getCards()) {
				sb.append(c.getType().toString());
				sb.append(" ");
			}
			playerLabel.setText(String.format("%s (%d territories in total)", p.getName(), p.getNumberOfTerritories()));
			if (WarGame.getInstance().getCurrentPlayer().equals(p)) {
				playerLabel.setFont(playerLabel.getFont().deriveFont(playerLabel.getFont().getStyle() | Font.BOLD));
			} else {
				playerLabel.setFont(playerLabel.getFont().deriveFont(playerLabel.getFont().getStyle()  & ~Font.BOLD));
			}
			i++;
		}
	}

	public void updateOptionsPanel(boolean first) {
		if (first) {
			this.optionsPanel = new JPanel();
			this.optionsPanel.setLayout(new BoxLayout(optionsPanel,
					BoxLayout.Y_AXIS));
			this.optionsPanel.setMaximumSize(new Dimension(this.size.width / 8 * 7,
					this.size.height));
			this.statusLabel = new JLabel();
			this.statusLabel.setOpaque(true);
			this.statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.actionButton = new JButton();
			this.actionButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.actionButton.setMaximumSize(new Dimension(200, 25));
			this.actionButton.setEnabled(false);
			ActionListener actionButtonListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					WarGame.getInstance().actionPerformed();
				}
			};
			actionButton.addActionListener(actionButtonListener);
			
			this.showCardsButton = new JButton("Show Cards");
			this.showCardsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.showCardsButton.setMaximumSize(new Dimension(200, 25));
			this.showCardsButton.setEnabled(false);
			ActionListener showCardsListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					WarGame.getInstance().showCards();
				}
			};
			this.showCardsButton.addActionListener(showCardsListener);
			
			this.showObjectiveButton = new JButton("Show Objective");
			this.showObjectiveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.showObjectiveButton.setMaximumSize(new Dimension(200, 25));
			this.showObjectiveButton.setEnabled(true);
			ActionListener showObjectiveListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					WarGame.getInstance().showObjective();
				}
			};
			this.showObjectiveButton.addActionListener(showObjectiveListener);
			
			this.endTurnButton = new JButton("End Turn");
			this.endTurnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.endTurnButton.setMaximumSize(new Dimension(200, 25));
			this.endTurnButton.setEnabled(false);
			ActionListener endTurnButtonListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ae) {
					WarGame.getInstance().nextTurn();
				}
			};
			this.endTurnButton.addActionListener(endTurnButtonListener);

			this.toggleMapDisplayButton = new JButton("Toggle Map Display");
			this.toggleMapDisplayButton.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.toggleMapDisplayButton.setMaximumSize(new Dimension(200, 25));
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
			this.optionsPanel.add(this.showCardsButton);
			this.optionsPanel.add(this.showObjectiveButton);
			this.optionsPanel.add(this.endTurnButton);
			this.optionsPanel.add(this.toggleMapDisplayButton);

			this.gamePanel.add(this.optionsPanel);
		}

		Player currentPlayer = WarGame.getInstance().getCurrentPlayer();
		String actionString = "No action";
		String statusString = "No status";
		this.actionButton.setEnabled(false);
		this.endTurnButton.setEnabled(true);
		if (currentPlayer.getCards().isEmpty()) {
			this.showCardsButton.setEnabled(false);
		} else {
			this.showCardsButton.setEnabled(true);
		}
		switch (WarGame.getInstance().getTurnState()) {
		case ATTACKING:
			if (WarGame.getInstance().getSelectedTerritory() == null) {
				statusString = "Select a country to attack from";
			} else {
				statusString = "Select a country to attack";
			}
			actionString = "Stop attacking";
			this.actionButton.setEnabled(true);
			break;
		case MOVING_ARMIES:
			actionString = "Clear selection";
			this.actionButton.setEnabled(true);
			if (WarGame.getInstance().getSelectedTerritory() == null) {
				statusString = "Select a country to move from";
			} else if (WarGame.getInstance().getTargetedTerritory() == null) {
				statusString = "Select a country to move to";
			}
			break;
		case PLACING_NEW_ARMIES:
			statusString = String
					.format("Select a country place armies in (you have %d armies left to place)",
							currentPlayer.getUnplacedArmies());
			if (WarGame.getInstance().getState().getSelectedTerritory() != null
					&& currentPlayer.getUnplacedArmies() > 0) {
				actionString = String.format("Place armies in %s", WarGame
						.getInstance().getState().getSelectedTerritory()
						.getName());
				this.actionButton.setEnabled(true);
			}
			/* must always place all armies */
			this.endTurnButton.setEnabled(false);
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
