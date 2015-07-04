package org.puc.rio.inf1636.hglm.war.model;

import java.util.Stack;

public class Deck {
	
	private Stack<TerritoryCard> cards = new Stack<TerritoryCard>();
	
	public Deck() {
	}
	
	public void addCard(TerritoryCard c) {
		this.cards.push(c);
	}
	
	public void returnCard(TerritoryCard c) {
		this.cards.add(0, c);
	}
	
	public TerritoryCard takeCard() {
		return this.cards.pop();
	}
	
}
