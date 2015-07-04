package org.puc.rio.inf1636.hglm.war.model;

//This class implements only the territory card and the extras
public class Card {
	int id; // identifies the cards
	CardType type;
	Territory territory;
	Player ownwer; //the player who owns this card
	
	
	
	public enum CardType {
		TRIANGLE_CARD,
		SQUARE_CARD,
		CIRCLE_CARD,
		EXTRA_CARD
	}
	public Card(Territory t,int type){
		
		this.territory=t;
		this.ownwer=null;
		if(t!=null){
			switch(type) {
			case 3:
				this.type=CardType.TRIANGLE_CARD;
				break;
			case 4:
				this.type = CardType.SQUARE_CARD;
				break;
			case 5:
				this.type = CardType.CIRCLE_CARD;
				break;
			default:
				this.type= CardType.EXTRA_CARD;
				break;
				
			}
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
