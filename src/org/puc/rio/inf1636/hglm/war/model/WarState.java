package org.puc.rio.inf1636.hglm.war.model;

import org.puc.rio.inf1636.hglm.war.WarGame;

public class WarState {
	/* this will be accessed too many times */
	private final static WarGame game = WarGame.getInstance();
	private Player currentPlayer;
	private TurnState currentState;

	public enum TurnState {
		PLACING_NEW_ARMIES, ATTACKING, MOVING_ARMIES, RECEIVING_LETTER;
	}

	public WarState(Player firstPlayer) {
		this.currentPlayer = firstPlayer;
		this.currentState = TurnState.PLACING_NEW_ARMIES;
	}

	public TurnState getCurrentState() {
		return this.currentState;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void nextTurn() {
		int currentPlayerIndex = game.getPlayers().indexOf(this.currentPlayer);
		if (currentPlayerIndex == game.getPlayers().size() - 1) {
			this.currentPlayer = game.getPlayers().get(0);
		} else {
			this.currentPlayer = game.getPlayers().get(currentPlayerIndex + 1);
		}
		this.currentState = TurnState.PLACING_NEW_ARMIES;
	}

	public boolean startAttacking() {
		if (this.getCurrentState() != TurnState.PLACING_NEW_ARMIES) {
			System.out.println("Should place all reinforcements first");
			return false;
		}
		this.currentState = TurnState.ATTACKING;
		return true;
	}

	public boolean startMovingArmies() {
		if (!this.currentState.equals(TurnState.ATTACKING)) {
			System.out.println("Must be attacking before moving");
			return false;
		} else {
			this.currentState = TurnState.MOVING_ARMIES;
			return true;
		}
	}

}
