package org.puc.rio.inf1636.hglm.war.model;

public class TerritoryCard extends Card {
	Territory territory;

	public TerritoryCard(Territory t, CardType type) {
		super(type);
		this.territory = t;
	}

	public TerritoryCard(Territory t, int type) {
		super(type);
		this.territory = t;
	}

	public Territory getTerritory() {
		return this.territory;
	}

}
