package org.puc.rio.inf1636.hglm.war.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.objective.WarObjective;

public class Player {

	private String name;
	private Color color;
	private int numberOfTerritories = 0;
	private List<Card> cards = new ArrayList<Card>(); //contains this player owned cards
	private WarObjective objective;
	private static final Color BLUE = new Color(0, 0, 128);
	private static final Color GREEN = new Color(0, 128, 0);
	private static final Color RED = new Color(128, 0, 0);
	private static final Color LIGHT_BLUE = new Color(0, 128, 128);
	private static final Color PURPLE = new Color(128, 0, 128);
	private static final Color BROWN = new Color(128, 128, 0);
	public final static Color[] playerColors = { GREEN, RED, BLUE, LIGHT_BLUE,
			PURPLE, BROWN };
	private int unplacedArmies = 0; // represents number of armies to be
										// placed

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

	public static Color getForegroundColor(Color c) {
		if (c == BLUE || c == PURPLE || c == RED) {
			return Color.WHITE;
		} else {
			return Color.BLACK;
		}
	}

	public int getUnplacedArmies() {
		return this.unplacedArmies;
	}

	public void setUnplacedArmies(int number) {
		this.unplacedArmies = number;
	}
	
	public void giveArmies(int number) {
		this.unplacedArmies += number;
	}
	
	public void removeArmies(int number) {
		this.unplacedArmies -= number;
	}
	
	public void addCard(Card c){
		this.cards.add(c);
		c.owner = this;
	}
	
	public void removeCard(Card c){
		this.cards.remove(c);
		c.owner = null;
	}

	public List<Card> getCards() {
		return this.cards;
	}

}
