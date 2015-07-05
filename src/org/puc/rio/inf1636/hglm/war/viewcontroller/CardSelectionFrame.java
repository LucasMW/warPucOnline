package org.puc.rio.inf1636.hglm.war.viewcontroller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.TerritoryCard;

@SuppressWarnings("serial")
public class CardSelectionFrame extends JFrame implements MouseListener{
	private HashMap<JLabel, TerritoryCard> cards = new HashMap<JLabel, TerritoryCard>();
	private List<TerritoryCard> selectedCards = new LinkedList<TerritoryCard>();

	private JPanel cardDisplayPanel;
	
	private JButton exchangeCardsButton;

	private Player player;

	public CardSelectionFrame(Player p, boolean forcedToExchange) {
		this.player = p;
		if (forcedToExchange) {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		} else {			
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		}
		this.setTitle(String.format("%s's cards%s", p.getName(), forcedToExchange ? " (Must exchange)" : ""));
		this.setSize(new Dimension(1100+2*2*5, 600));
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		
		/* Card display panel */
		this.cardDisplayPanel = new JPanel();
		this.cardDisplayPanel.setLayout(new BoxLayout(cardDisplayPanel, BoxLayout.X_AXIS));
		
		for (TerritoryCard tc: p.getCards()) {
			JLabel cardLabel = new JLabel();
			cardLabel.setSize(220, 363);
			cardLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
			cardLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
			cardLabel.addMouseListener(this);
			String imagePath = String.format("resources/cards/war_carta_%s.png", tc.getTerritory().getName().toLowerCase().replaceAll("\\s+",""));
			imagePath = Normalizer.normalize(imagePath, Normalizer.Form.NFD);
			imagePath = imagePath.replaceAll("[^\\x00-\\x7F]", "");
			System.out.println(imagePath);
			ImageIcon cardImage = new ImageIcon(imagePath);
			Image resizedImage = cardImage.getImage().getScaledInstance(cardLabel.getWidth(), cardLabel.getHeight(), Image.SCALE_SMOOTH);
			cardLabel.setIcon(new ImageIcon(resizedImage));

			this.cards.put(cardLabel, tc);
			this.cardDisplayPanel.add(cardLabel);
		}
		
		this.exchangeCardsButton = new JButton(String.format("Exchange cards for %d armies", WarGame.getInstance().getState().getCardExchangeArmyCount()));
		ActionListener exchangeCardsListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				WarGame.getInstance().exchangeCards(selectedCards);
				dispose();
			}
			
		};
		this.exchangeCardsButton.addActionListener(exchangeCardsListener);
		this.exchangeCardsButton.setEnabled(false);
			
		this.getContentPane().add(this.cardDisplayPanel, BorderLayout.CENTER);
		this.getContentPane().add(this.exchangeCardsButton, BorderLayout.PAGE_END);
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		JLabel clickedCard = (JLabel) me.getSource();
		int index = this.selectedCards.indexOf(this.cards.get(clickedCard));
		/* already selected */
		if (index >= 0) {
			this.selectedCards.remove(index);
			clickedCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		} else if (this.selectedCards.size() < 3) {
			this.selectedCards.add(this.cards.get(clickedCard));			
			clickedCard.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
		}
		
		if (this.player.checkIfHasValidCardExchange(this.selectedCards) && WarGame.getInstance().getState().isPlacing()) {
			this.exchangeCardsButton.setEnabled(true);
		} else {
			this.exchangeCardsButton.setEnabled(false);
		}
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
