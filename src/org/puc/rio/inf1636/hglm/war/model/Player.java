package org.puc.rio.inf1636.hglm.war.model;

import java.awt.Color;

public class Player {

	private String name;
	private Color color;
	private int numberOfTerritories = 0;
	public final static Color[] playerColors = { Color.RED, Color.GREEN,
			Color.BLUE, Color.YELLOW, Color.PINK, Color.BLACK };

	public Player(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	public String getName() {
		return this.name;
	}

	public Color getColor() {
		return this.color;
	}
	
	public int addTerritory() {
		return ++this.numberOfTerritories;
	}
	
	public int removeTerritory() {
		return --this.numberOfTerritories;
	}

	public int getNumberOfTerritories() {
		return this.numberOfTerritories;
	}
	
	public Color getForegroundColor() {
		if (this.color == Color.BLUE || this.color == Color.BLACK) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}
}
