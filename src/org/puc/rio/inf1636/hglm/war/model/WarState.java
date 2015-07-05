package org.puc.rio.inf1636.hglm.war.model;

import java.util.ArrayList;
import java.util.List;

public class WarState {

	private Player currentPlayer;
	private List<Player> players = new ArrayList<Player>();
	private Player canStealCardsFrom = null;
	
	private TurnState currentTurnState;
	
	private Territory selectedTerritory;
	private Territory targetedTerritory;
	
	private int conquestsThisTurn = 0;
	private int cardExchangeArmyCount = 4;

	private Map map = null;
	
	private Deck deck;
	
	public enum TurnState {
		PLACING_NEW_ARMIES, ATTACKING, MOVING_ARMIES, RECEIVING_LETTER;
	}

	public WarState(List<Player> players, Map map, Deck deck) {
		this.players = players;
		this.map = map;
		this.deck = deck;
		this.currentPlayer = players.get(0);
		this.currentTurnState = TurnState.PLACING_NEW_ARMIES;
	}

	public Map getMap() {
		return this.map;
	}

	public void addPlayer(Player p) {
		players.add(p);
	}

	public List<Player> getPlayers() {
		return this.players;
	}

	public Deck getDeck() {
		return deck;
	}

	public TurnState getCurrentTurnState() {
		return this.currentTurnState;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void nextTurn() {
		int currentPlayerIndex = players.indexOf(this.currentPlayer);
		if (currentPlayerIndex == players.size() - 1) {
			this.currentPlayer = players.get(0);
		} else {
			this.currentPlayer = players.get(currentPlayerIndex + 1);
		}
		this.clearSelections();
		this.conquestsThisTurn = 0;
		this.canStealCardsFrom = null;
		this.currentTurnState = TurnState.PLACING_NEW_ARMIES;
	}

	public boolean startAttacking() {
		if (this.getCurrentTurnState() != TurnState.PLACING_NEW_ARMIES) {
			System.out.println("Should place all reinforcements first");
			return false;
		}
		this.clearSelections();
		this.currentTurnState = TurnState.ATTACKING;
		return true;
	}

	public boolean startMovingArmies() {
		if (!this.currentTurnState.equals(TurnState.ATTACKING)) {
			System.out.println("Must be attacking before moving");
			return false;
		}
		this.clearSelections();
		this.currentTurnState = TurnState.MOVING_ARMIES;
		return true;

	}

	public void selectTerritory(Territory t) {
		this.selectedTerritory = t;
	}

	public void unselectTerritory() {
		this.selectedTerritory = null;
	}

	public Territory getSelectedTerritory() {
		return this.selectedTerritory;
	}
	
	public void targetTerritory(Territory t) {
		this.targetedTerritory = t;
	}

	public void untargetTerritory() {
		this.targetedTerritory = null;
	}

	public Territory getTargetedTerritory() {
		return this.targetedTerritory;
	}

	public void clearSelections() {
		this.selectedTerritory = null;
		this.targetedTerritory = null;
	}
	
	public int getConquestsThisTurn() {
		return this.conquestsThisTurn;
	}
	
	public void addConquestThisTurn() {
		this.conquestsThisTurn++;
	}
	
	public int getCardExchangeArmyCount() {
		return this.cardExchangeArmyCount;
	}
	
	public void setCardExchangeArmyCount(int count) {
		this.cardExchangeArmyCount = count;
	}

	public void setCanStealCardsFrom(Player p) {
		this.canStealCardsFrom = p;
	}
	
	public Player getCanStealCardsFrom() {
		return this.canStealCardsFrom;
	}
}
