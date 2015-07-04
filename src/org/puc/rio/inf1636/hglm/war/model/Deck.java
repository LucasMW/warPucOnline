package org.puc.rio.inf1636.hglm.war.model;

import java.util.Stack;

public class Deck {
	
	private Stack<Card> cards = new Stack<Card>();
	
	public Deck() {
	}
	
	public void addCard(Card c) {
		this.cards.push(c);
	}
	
	public void returnCard(Card c) {
		this.cards.add(0, c);
	}
	
	public Card takeCard() {
		return this.cards.pop();
	}
	
}
