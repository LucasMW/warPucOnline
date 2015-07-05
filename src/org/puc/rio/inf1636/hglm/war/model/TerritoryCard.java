package org.puc.rio.inf1636.hglm.war.model;

//This class implements only the territory card and the extras
public class TerritoryCard {
	int id; // identifies the cards
	CardType type;
	Territory territory;
	Player owner; // the player who owns this card

	public TerritoryCard(Territory t, int type) {
		this.territory = t;
		this.owner = null;
		if (t != null) {
			this.type = CardType.getByValue(type);
		} else {
			this.type = null;
		}

	}

	public CardType getType() {
		return this.type;
	}

	public void setOwner(Player p) {
		this.owner = p;
	}

	public Territory getTerritory() {
		return this.territory;
	}

}
