package org.puc.rio.inf1636.hglm.war.model;

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
		case 3:
			return CardType.TRIANGLE_CARD;
		case 4:
			return CardType.SQUARE_CARD;
		case 0:
			return CardType.CIRCLE_CARD;
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