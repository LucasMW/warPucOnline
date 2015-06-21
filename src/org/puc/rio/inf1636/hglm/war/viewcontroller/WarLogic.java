package org.puc.rio.inf1636.hglm.war.viewcontroller;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Player;

public class WarLogic {
	/* this will be accessed too many times */
	private final static WarGame game = WarGame.getInstance();
	private Player currentPlayer;
	private TurnState currentState;

	public enum TurnState {
		PLACING_NEW_ARMIES, ATTACKING, MOVING_ARMIES, RECEIVING_LETTER;
	}

	public WarLogic() {
	}

	public TurnState getCurrentState() {
		return currentState;
	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void nextTurn() {
		int currentPlayerIndex = game.getPlayers().indexOf(this.currentPlayer);
		if (currentPlayerIndex == game.getPlayers().size() - 1) {
			this.currentPlayer = game.getPlayers().get(0);
		} else {
			game.getPlayers().get(currentPlayerIndex + 1);
		}
	}

	public boolean giveReinforcements() {
		if (this.getCurrentState() != TurnState.PLACING_NEW_ARMIES) {
			/* this should never be called */
			System.out.printf("Must be placing new armies to receive reinforcements");
			return false; 
		}
		Player currentPlayer = game.getCurrentPlayer();
		/* integer division as specified in manual */
		currentPlayer.giveArmies(currentPlayer.getNumberOfTerritories() / 2); 
		return true;
	}

	public boolean attack() {
		if (game.getCurrentPlayer().getUnplacedArmies() != 0) {
			System.out.println("Should place all reinforcements first");
			return false;
		}
		this.currentState = TurnState.ATTACKING;
		return true;
	}

	public boolean moveArmy() {
		if (!this.currentState.equals(TurnState.ATTACKING)) {
			System.out.println("Must be attacking before moving");
			return false;
		} else {
			this.currentState = TurnState.MOVING_ARMIES;
			return true;
		}
	}
}
