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
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class UIPanel extends JPanel {

	private CardLayout layout;

	private JPanel startPanel;
	private JPanel gamePanel;
	private JPanel optionsPanel;
	private JPanel namesPanel;
	private List<JLabel> playerLabels = new LinkedList<JLabel>();
	private JLabel playerTurnLabel;

	private JButton attackButton;
	private JButton endTurnButton;

	private DiceFrame diceFrame;

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
				"You must enter at least %d players", WarGame.MIN_PLAYERS));
		error.setBackground(Color.RED);
		error.setVisible(false);
		this.startPanel.add(error);

		final List<JTextField> playerNameTextFields = new LinkedList<JTextField>();
		for (int i = 0; i < WarGame.MAX_PLAYERS; i++) { // 6 textFields for each
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
				for (int i = 0; i < WarGame.MAX_PLAYERS; i++) {
					JTextField playerNameTextField = playerNameTextFields
							.get(i);
					if (playerNameTextField.getText().length() > 0) {
						WarGame.getInstance().addPlayer(
								new Player(playerNameTextField.getText(),
										Player.playerColors[i]));
					}
				}
				if (WarGame.getInstance().getPlayers().size() >= WarGame.MIN_PLAYERS) {
					WarGame.getInstance().startGame();
					startGame();
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

	private void addGameUIPanel() {
		Player currentPlayer = WarGame.getInstance().getCurrentPlayer();
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.X_AXIS));

		this.optionsPanel = new JPanel();
		this.optionsPanel.setLayout(new BoxLayout(optionsPanel,
				BoxLayout.Y_AXIS));
		this.optionsPanel.setMaximumSize(new Dimension(this.size.width / 2,
				this.size.height));
		this.playerTurnLabel = new JLabel(String.format("%s's turn",
				currentPlayer.getName()));
		this.playerTurnLabel.setOpaque(true);
		this.playerTurnLabel.setBackground(currentPlayer.getColor());
		this.playerTurnLabel.setForeground(Player
				.getForegroundColor(currentPlayer.getColor()));
		this.playerTurnLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.attackButton = new JButton("Attack!");
		this.attackButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.endTurnButton = new JButton("End Turn");
		this.endTurnButton.setAlignmentX(Component.LEFT_ALIGNMENT);
		this.attackButton.setEnabled(false);
		ActionListener attackButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				diceFrame = new DiceFrame();
				diceFrame.setVisible(true);

			}
		};
		attackButton.addActionListener(attackButtonListener);
		ActionListener endTurnButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("end turn");
				WarGame.getInstance().nextTurn();
			}
		};
		this.endTurnButton.addActionListener(endTurnButtonListener);
		this.optionsPanel.add(this.playerTurnLabel);
		this.optionsPanel.add(this.attackButton);
		this.optionsPanel.add(this.endTurnButton);

		this.namesPanel = new JPanel();
		this.namesPanel.setLayout(new BoxLayout(namesPanel, BoxLayout.Y_AXIS));
		this.namesPanel.setMaximumSize(new Dimension(this.size.width / 2,
				this.size.height));

		JLabel playersLabel = new JLabel("Players:");
		this.namesPanel.add(playersLabel);
		updatePlayerLabels(true);

		this.gamePanel.add(this.namesPanel);
		this.gamePanel.add(this.optionsPanel);

		this.add(gamePanel, "Game UI");
	}

	private void startGame() {
		this.addGameUIPanel();
		layout.show(this, "Game UI");
	}

	public void switchPlayer() {
		Player currentPlayer = WarGame.getInstance().getCurrentPlayer();

		this.playerTurnLabel.setText(String.format("%s's turn",
				currentPlayer.getName()));
		this.playerTurnLabel.setBackground(currentPlayer.getColor());
		this.playerTurnLabel.setForeground(Player
				.getForegroundColor(currentPlayer.getColor()));
		updatePlayerLabels(false);
	}

	public void updatePlayerLabels(boolean first) {
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
			playerLabel.setText(String.format("%s (%d territorios em total)%s",
					p.getName(), p.getNumberOfTerritories(),
					isCurrentPlayer ? "*" : ""));
			i++;
		}
	}

	public void updateSelectedLabel() {
		Territory t = WarGame.getInstance().getMap().getCurrentTerritory();
		if (t == null) {
			attackButton.setText("Attack!");
			attackButton.setEnabled(false);
		} else {
			attackButton.setText(String.format("Attack %s!", t.getName()));
			attackButton.setEnabled(true);
		}

	}
}
