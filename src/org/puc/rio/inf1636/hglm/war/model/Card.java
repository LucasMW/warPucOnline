package org.puc.rio.inf1636.hglm.war.model;

public class Card {
	int id; // identifies the cards
	CardType type;
	Territory territory;
	String objective;
	
	public enum CardType {
		TERRITORY_CARD,
		OBJECTIVE_CARD,
		JOKER_CARD
	}
	public Card(Territory t){
		
		this.territory=t;
		this.objective=null;
		this.type= CardType.TERRITORY_CARD;
	}
	public Card(String obj)
	{
		this.territory=null;
		this.objective=obj;
		this.type= CardType.OBJECTIVE_CARD;
		
	}
	public Card() {
		this.type= CardType.JOKER_CARD;
		this.territory=null;
		this.objective=null;
		
	}
	
	public CardType getType() {
		return this.type;
	}
	
	
	public Territory getTerritory() {
		return this.territory;
	}

	
	

}
