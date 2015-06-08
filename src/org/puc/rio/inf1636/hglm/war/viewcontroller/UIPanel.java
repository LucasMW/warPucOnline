package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
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
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class UIPanel extends JPanel {

	private Image backgroundImage;
	private CardLayout layout;
	
	private JPanel startPanel;
	private JPanel gamePanel;
	private JPanel playerOrderPanel;
	private JLabel playerOrderLabel;
	private JLabel playerTurnLabel;
	
	
	private JButton attackButton;
	private JButton endTurnButton;
	
	private DiceFrame diceFrame;
	
	public static double multX = 1.0;
	public static double multY = 1.0 / 2.0;

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
		Dimension uiSize = WarGame.getGameSize();
		uiSize.height = (int) (uiSize.height * multY);
		uiSize.width = (int) (uiSize.width * multX);
		this.setMaximumSize(uiSize);
		addStartUIPanel();
		addGameUIPanel();
		
		layout.show(this, "Starting UI");
	}

	private String generateOrderString()
	{
		List<Player> players=WarGame.getInstance().getPlayers();
		
		String s="";
		int currentIndex = WarGame.getInstance().getCurrentPlayerIndex();
		
		for(int i=0;i<players.size();i++)
		{
			Player p = players.get(i);
			String pStr;
			pStr=String.format("%s", p.getName());
			if(i == currentIndex)
			{
				pStr=String.format("<strong>%s</strong>", pStr);
			}
			if(i>0)
			{
				s = String.format("%s => %s",s,pStr);
			}
			else 
			{
				s=String.format("%s", pStr);
			}
		}
		s = String.format("<html>%s</html>", s);
		return s;
	}
	private void addStartUIPanel() {
		this.startPanel = new JPanel();
		this.startPanel.setLayout(new BoxLayout(startPanel, BoxLayout.Y_AXIS));

		JLabel l1 = new JLabel("Welcome to War. Enter the name of each player:");
		l1.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.startPanel.add(l1);

		final JLabel error = new JLabel(String.format(
				"You must enter at least %d players", WarGame.MIN_PLAYERS));
		error.setAlignmentX(Component.CENTER_ALIGNMENT);
		error.setBackground(Color.RED);
		error.setVisible(false);
		this.startPanel.add(error);

		final List<JTextField> playerNameTextFields = new LinkedList<JTextField>();
		for (int i = 0; i < WarGame.MAX_PLAYERS; i++) { // 6 textFields for each possible player
			JTextField playerName = new JTextField();
			playerName.setMaximumSize(new Dimension(400, (int) (50 * multY)));
			playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
			playerName.setBackground(Player.playerColors[i]);
			playerNameTextFields.add(playerName);
			if (Player.playerColors[i] == Color.blue
					|| Player.playerColors[i] == Color.black) {
				playerName.setCaretColor(Color.white);
				playerName.setForeground(Color.white);
			}
			this.startPanel.add(playerName);
		}

		JButton submitButton = new JButton("Submit");
		submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
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
					switchPlayer();
					//addPlayerOrderPanel();
					
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
		this.gamePanel = new JPanel();
		this.gamePanel.setLayout(new BoxLayout(gamePanel, BoxLayout.Y_AXIS));
		this.playerTurnLabel = new JLabel();
		this.playerTurnLabel.setAlignmentX(CENTER_ALIGNMENT);
		this.attackButton = new JButton("Attack!");
		this.attackButton.setAlignmentX(CENTER_ALIGNMENT);
		this.endTurnButton = new JButton("End Turn");
		this.endTurnButton.setAlignmentX(CENTER_ALIGNMENT);

		this.attackButton.setEnabled(false);
		ActionListener attackButtonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				System.out.println("attack!");
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
		
		this.gamePanel.add(this.playerTurnLabel);
		this.gamePanel.add(this.attackButton);
		this.gamePanel.add(this.endTurnButton);
		playerOrderLabel = new JLabel("order here");
		playerOrderLabel.setAlignmentX(CENTER_ALIGNMENT);
		gamePanel.add(playerOrderLabel);
		
		this.add(gamePanel, "Game UI");
	}

	private void addPlayerOrderPanel()
	{
		this.playerOrderPanel = new JPanel();
		this.playerOrderPanel.setAlignmentX(LEFT_ALIGNMENT);
		
		System.out.println("ORDER IS");
		List<Player> players=WarGame.getInstance().getPlayers();
		Player current;
		current = WarGame.getInstance().getCurrentPlayer();
		String s="";
		
		
		for(int i=0;i<players.size();i++)
		{
			JLabel pLabel;
			Player p = players.get(i);
			String pStr;
			pStr=String.format("%s", p.getName());
			if(i==0)
			{	
				pLabel =new JLabel(p.getName());
				pLabel.setBackground(p.getColor());
				
			}
			else
			{
				pLabel =new JLabel(String.format(" => %s", p.getName()));
				pLabel.setBackground(p.getColor());
				
			}
			if(p==current)
			{
				pLabel.setForeground(p.getColor());
				pStr=String.format("<strong>%s</strong>", pStr);
			}
			this.playerOrderPanel.add(pLabel);
			if(i>0)
			{
				s = String.format("%s => %s",s,pStr);
			}
			else 
			{
				s=String.format("%s", pStr);
			}
		}
		System.out.println(s);
		this.gamePanel.add(this.playerOrderPanel);
	}
	private void switchCard(String name) {
		layout.show(this, name);
		
	}

	public void switchPlayer() {
		// player should be switched
		System.out.println("SWITCHED?");
		Player p;
		p = WarGame.getInstance().getCurrentPlayer();
		playerTurnLabel.setText(String.format("%s's turn", p.getName()));
		if (p.getColor() == Color.blue || p.getColor() == Color.black) {
			// playerTurnLabel.setCaretColor(Color.white);
			playerTurnLabel.setForeground(Color.white);
		} else {
			playerTurnLabel.setForeground(null);
		}
		gamePanel.setBackground(WarGame.getInstance().getCurrentPlayer()
				.getColor());
		System.out.println(this.generateOrderString());
		this.playerOrderLabel.setText(this.generateOrderString());
	}

	public void updateSelectedLabel() {
		Territory t = WarGame.getInstance().getCurrentTerritory();
		if (t == null) {
			attackButton.setText("Attack!");
			attackButton.setEnabled(false);
		} else {
			attackButton.setText(String.format("Attack %s!",
					t.getName()));
			attackButton.setEnabled(true);
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
	}
}
