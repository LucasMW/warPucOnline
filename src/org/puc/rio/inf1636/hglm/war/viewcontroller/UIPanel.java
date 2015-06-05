package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Player;

public class UIPanel extends JPanel {

	private Image backgroundImage;
	private CardLayout layout;
	private JPanel startPanel;
	private JPanel gamePanel;
	private JLabel playerTurnLabel;

	public UIPanel() {
		this.layout = new CardLayout();
		this.setLayout(layout);
		try {
			backgroundImage = new ImageIcon("resources/war_tabuleiro_fundo.png")
					.getImage();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}
		Dimension uiSize = Toolkit.getDefaultToolkit().getScreenSize();
		uiSize.height = 200;
		this.setMinimumSize(uiSize);
		addStartUIPanel();
		addGameUIPanel();
		layout.show(this, "Starting UI");
	}

	private void addStartUIPanel() {
		startPanel = new JPanel();
		startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));
		
		JLabel l1 = new JLabel(
				"Welcome to War. Enter the name of each player:");
		l1.setAlignmentX(Component.CENTER_ALIGNMENT);
		startPanel.add(l1);
		
		final JLabel error = new JLabel(String.format(
				"You must enter at least %d players", WarGame.MIN_PLAYERS));
		error.setAlignmentX(Component.CENTER_ALIGNMENT);
		error.setBackground(Color.RED);
		error.setVisible(false);
		startPanel.add(error);
		
		final List<JTextField> playerNameTextFields = new LinkedList<JTextField>();
		for (int i = 0; i < WarGame.MAX_PLAYERS; i++) {
			JTextField playerName = new JTextField();
			playerName.setMaximumSize(new Dimension(400, 50));
			playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
			playerName.setBackground(Player.playerColors[i]);
			playerNameTextFields.add(playerName);
			startPanel.add(playerName);
		}

		JButton submitButton = new JButton("Submit");
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		submitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				for (int i = 0; i < WarGame.MAX_PLAYERS; i++) {
					JTextField playerNameTextField = playerNameTextFields.get(i);
					if (playerNameTextField.getText().length() > 0) {
						WarGame.getInstance().addPlayer(new Player(playerNameTextField.getText(), Player.playerColors[i]));
					}
				}
				if (WarGame.getInstance().getPlayers().size() >= WarGame.MIN_PLAYERS) {
					WarGame.getInstance().startGame();
					switchPlayer();
					switchCard("Game UI");
				} else {
					WarGame.getInstance().getPlayers().clear();
					error.setVisible(true);
				}

			}
		});

		startPanel.add(submitButton);
		this.add(startPanel, "Starting UI");
	}

	private void addGameUIPanel() {
		gamePanel = new JPanel();
		playerTurnLabel = new JLabel("");
		gamePanel.add(playerTurnLabel);
		this.add(gamePanel, "Game UI");
	}
	
	private void switchCard(String name) {
		layout.show(this, name);
	}
	
	private void switchPlayer() {
		playerTurnLabel.setText(String.format("%s's turn", WarGame.getInstance().getCurrentPlayer().getName()));
		gamePanel.setBackground(WarGame.getInstance().getCurrentPlayer().getColor());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}
