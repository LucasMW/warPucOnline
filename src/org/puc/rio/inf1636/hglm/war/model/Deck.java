package org.puc.rio.inf1636.hglm.war.model;

import java.util.Collections;
import java.util.Stack;

public class Deck {
	
	private Stack<Card> cards = new Stack<Card>();
	
	public Deck() {
	}
	
	public void addCard(TerritoryCard c) {
		this.cards.push(c);
	}
	
	public void returnCard(TerritoryCard c) {
		this.cards.add(0, c);
	}
	
	public Card takeCard() {
		return this.cards.pop();
	}

	public void shuffle() {
		Collections.shuffle(this.cards);
	}

	public void addJoker() {
		this.cards.push(new Card(CardType.JOKER));
	}
	
	public void addJoker(int count) {
		for (int i = 0; i < count; i++) {
			addJoker();
		}
	}
	
}
