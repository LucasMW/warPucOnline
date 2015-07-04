package org.puc.rio.inf1636.hglm.war.model;

//This class implements only the territory card and the extras
public class Card {
	int id; // identifies the cards
	CardType type;
	Territory territory;
	Player ownwer; //the player who owns this card
	
	
	
	public enum CardType {
		TRIANGLE_CARD,
		CIRCLE_CARD,
		SQUARE_CARD,
		EXTRA_CARD
	}
	public Card(Territory t){
		
		this.territory=t;
		if(t!=null){
			this.type= CardType.TRIANGLE_CARD;
		}
		else{
			this.type = CardType.EXTRA_CARD;
		}
	}
	
	
	public CardType getType() {
		return this.type;
	}
	public void setOnwer(Player p){
		this.ownwer=p;
	}
	
	public Territory getTerritory() {
		return this.territory;
	}

	
	

}
