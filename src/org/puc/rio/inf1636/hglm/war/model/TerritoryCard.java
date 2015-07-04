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

	public enum CardType {
		TRIANGLE_CARD(3), SQUARE_CARD(4), CIRCLE_CARD(0);
		private final int value;

		CardType(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
		}

		public static CardType getByValue(int value) {
			switch (value) {
			case 0:
				return CardType.CIRCLE_CARD;
			case 3:
				return CardType.TRIANGLE_CARD;
			case 4:
				return CardType.SQUARE_CARD;
			default:
				return null;
			}
		}

		@Override
		public String toString() {
			switch (this.value) {
			case 3:
				return "\u25B2";
			case 4:
				return "\u25FC";
			case 0:
				return "\u25CF";
			}
			return null;
		}
	}

}
