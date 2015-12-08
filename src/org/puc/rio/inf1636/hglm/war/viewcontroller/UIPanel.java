package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.puc.rio.inf1636.hglm.war.Util;
import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.WarLogic;
import org.puc.rio.inf1636.hglm.war.model.Card;
import org.puc.rio.inf1636.hglm.war.model.Player;

@SuppressWarnings("serial")
public class UIPanel extends JPanel implements MouseListener, Observer {

	private CardLayout layout;

	private JPanel startPanel;
	private JPanel loadGamePanel;
	private JPanel enterNamesPanel;
	private JPanel gamePanel;
	private JPanel optionsPanel;
	private JPanel namesPanel;
	private JPanel gameEndedPanel;

	private List<JLabel> playerLabels = new LinkedList<JLabel>();
	private JLabel statusLabel;

	private JButton actionButton;
	private JButton showObjectiveButton;
	private JButton showCardsButton;
	private JButton endTurnButton;
	private JButton toggleMapDisplayButton;

	private Dimension size;

	private final double MULTIPLIER_X = 1.0;
	private final double MULTIPLIER_Y = 0.35;

	public UIPanel() {
		this.layout = new CardLayout();
		this.setLayout(layout);

		this.size = Util.getGameSize();
		this.size.height = (int) (this.size.height * MULTIPLIER_Y);
		this.size.width = (int) (this.size.width * MULTIPLIER_X);
		this.setMaximumSize(this.size);
		this.addStartUIPanel();

		this.layout.show(this, "Starting UI");
	}

	private void addStartUIPanel() {
		this.startPanel = new JPanel();
		this.startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

		this.enterNamesPanel = new JPanel();
		this.enterNamesPanel.setLayout(new BoxLayout(enterNamesPanel, BoxLayout.X_AXIS));
		final JLabel l1 = new JLabel(
				"Welcome to War. Enter the name of each player:");
		l1.setMaximumSize(new Dimension(300, 50));
		l1.setAlignmentY(Component.TOP_ALIGNMENT);
		this.enterNamesPanel.add(l1);

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
			this.enterNamesPanel.add(playerName);
		}

		JButton submitButton = new JButton("Submit");
		submitButton.setMaximumSize(new Dimension(100, 50));
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				List<Player> players = new ArrayList<Player>();
				for (int i = 0; i < WarLogic.MAX_PLAYERS; i++) {
					JTextField playerNameTextField = playerNameTextFields
							.get(i);
					if (playerNameTextField.getText().length() > 0) {
						players.add(new Player(playerNameTextField.getText(),
								Player.playerColors[i]));
					}
				}
				if (players.size() >= WarLogic.MIN_PLAYERS) {
					WarGame.getInstance().startGame(players);
				} else if (players.size() == 1) { //case for multiplayer
					
					WarGame.getInstance().startMultiplayer(players.get(0));
					System.out.println("called for multiplayer");
					l1.setText("<html>Welcome to War. Waiting for other players to connect");
					l1.setOpaque(true);
					l1.setBackground(Color.RED);
				}
				else {
					players.clear();
					l1.setText("<html>Welcome to War. Enter the name of each player:<br />Please enter at least 3 players!</html>");
					l1.setOpaque(true);
					l1.setBackground(Color.RED);
				}

			}
		});
		submitButton.setAlignmentY(Component.TOP_ALIGNMENT);
		this.enterNamesPanel.add(submitButton);
		
		this.loadGamePanel = new JPanel();
		this.loadGamePanel.setLayout(new BoxLayout(loadGamePanel, BoxLayout.X_AXIS));
		final JTextField  saveFileName = new JTextField("Enter savefile name");
		JButton loadGameButton = new JButton("Load game");
		loadGameButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				WarGame.getInstance().loadGame(saveFileName.getText());
			}});

		this.loadGamePanel.add(saveFileName);
		this.loadGamePanel.add(loadGameButton);
		
		this.startPanel.add(enterNamesPanel);
		this.startPanel.add(loadGamePanel);
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
			this.gamePanel.addMouseListener(this);
		}
		this.updateNamesPanel(first);
		this.updateOptionsPanel(first);
		
	}

	public void showGameEndedPanel(Player winner) {
		this.gameEndedPanel = new JPanel();
		this.gameEndedPanel.setLayout(new BorderLayout());
		JTextArea endGameMessage = new JTextArea(
				String.format(
						"The game has ended, %s is victorious for completing his/her objective (%s)",
						winner.getName(), winner.getObjective()
								.getDescription()));
		this.gameEndedPanel.add(endGameMessage, BorderLayout.CENTER);
		this.add(gameEndedPanel, "Game Ended");
		layout.show(this, "Game Ended");
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
			playerLabel.setText(String.format("%s (%d territories)",
					p.getName(), WarGame.getInstance().getMap().getTerritoriesByOwner(p).size()));
			if (WarGame.getInstance().getCurrentPlayer().equals(p)) {
				playerLabel.setFont(playerLabel.getFont().deriveFont(
						playerLabel.getFont().getStyle() | Font.BOLD));
			} else {
				playerLabel.setFont(playerLabel.getFont().deriveFont(
						playerLabel.getFont().getStyle() & ~Font.BOLD));
			}
			i++;
		}
	}

	public void updateOptionsPanel(boolean first) {
		if (first) {
			this.optionsPanel = new JPanel();
			this.optionsPanel.setLayout(new BoxLayout(optionsPanel,
					BoxLayout.Y_AXIS));
			this.optionsPanel.setMaximumSize(new Dimension(
					this.size.width / 8 * 7, this.size.height));
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
					WarGame.getInstance().showCards(false);
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
			this.toggleMapDisplayButton
					.setAlignmentX(Component.CENTER_ALIGNMENT);
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
			this.showCardsButton.setText("Show Cards");
		} else {	
			this.showCardsButton.setEnabled(true);
			this.showCardsButton.setText(String.format("Show Cards (%d)", currentPlayer.getCards().size()));
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
			if (WarGame.getInstance().getSelectedTerritory() != null
					&& currentPlayer.getUnplacedArmies() > 0) {
				actionString = String.format("Place armies in %s", WarGame
						.getInstance().getSelectedTerritory().getName());
				this.actionButton.setEnabled(true);
			}
			/* must always place all armies */
			this.endTurnButton.setEnabled(false);
			break;
		default:
			break;
		}
		if(WarGame.getInstance().isOnline())
		{
			if(WarGame.getInstance().isMyTurn())
			{
				this.statusLabel.setText(String.format("(it's your (%s) turn) %s",
						currentPlayer.getName(), statusString));
			}
			else
			{
				this.statusLabel.setText(String.format("it`s not your turn (it is %s's turn)/n you are %s",
						currentPlayer.getName(),WarGame.getInstance().getMyPlayer().getName()));
			}
			
		}
		else
		{
		this.statusLabel.setText(String.format("(%s's turn) %s",
				currentPlayer.getName(), statusString));
		}
		this.statusLabel.setBackground(currentPlayer.getColor());
		this.statusLabel.setForeground(Player.getForegroundColor(currentPlayer
				.getColor()));
		this.actionButton.setText(actionString);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		WarGame.getInstance().focusPopupIfExists();
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

	@Override
	public void update(Observable obs, Object obj) {
		this.update(false);		
	}
}
