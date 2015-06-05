package org.puc.rio.inf1636.hglm.war.model;

import java.awt.Color;
import java.util.Random;

public class Player {

	private String name;
	private Color color;
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

}
