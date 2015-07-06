package org.puc.rio.inf1636.hglm.war.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class WarState extends Observable {

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
		PLACING_NEW_ARMIES, ATTACKING, MOVING_ARMIES;
	}

	public WarState(List<Player> players, Map map, Deck deck) {
		this.players = players;
		this.map = map;
		this.deck = deck;
		this.currentPlayer = players.get(0);
		this.currentTurnState = TurnState.PLACING_NEW_ARMIES;
	}

	public Map getMap() {
		this.setChanged();
		return this.map;
	}

	public List<Player> getPlayers() {
		this.setChanged();
		return this.players;
	}

	public Deck getDeck() {
		this.setChanged();
		return deck;
	}

	public TurnState getCurrentTurnState() {
		return this.currentTurnState;
	}

	public Player getCurrentPlayer() {
		this.setChanged();
		return this.currentPlayer;
	}

	public Territory getSelectedTerritory() {
		this.setChanged();
		return this.selectedTerritory;
	}

	public Territory getTargetedTerritory() {
		this.setChanged();
		return this.targetedTerritory;
	}

	public int getConquestsThisTurn() {
		return this.conquestsThisTurn;
	}

	public int getCardExchangeArmyCount() {
		return this.cardExchangeArmyCount;
	}

	public Player getCanStealCardsFrom() {
		return this.canStealCardsFrom;
	}
	
	public int getCurrentPlayerIndex() {
		return this.getPlayers().indexOf(this.getCurrentPlayer());
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
		this.setChanged();
	}

	public void startAttacking() {
		this.clearSelections();
		this.currentTurnState = TurnState.ATTACKING;
		this.setChanged();
	}

	public void startMovingArmies() {
		this.clearSelections();
		this.currentTurnState = TurnState.MOVING_ARMIES;
		this.setChanged();
	}

	public void selectTerritory(Territory t) {
		this.selectedTerritory = t;
		this.setChanged();
	}

	public void unselectTerritory() {
		this.selectedTerritory = null;
		this.setChanged();
	}

	public void targetTerritory(Territory t) {
		this.targetedTerritory = t;
		this.setChanged();
	}

	public void untargetTerritory() {
		this.targetedTerritory = null;
		this.setChanged();
	}

	public void clearSelections() {
		this.selectedTerritory = null;
		this.targetedTerritory = null;
		this.setChanged();
	}

	public void addConquestThisTurn() {
		this.conquestsThisTurn++;
		this.setChanged();
	}

	public void setCardExchangeArmyCount(int count) {
		this.cardExchangeArmyCount = count;
		this.setChanged();
	}

	public void setCanStealCardsFrom(Player p) {
		this.canStealCardsFrom = p;
		this.setChanged();
	}

}
