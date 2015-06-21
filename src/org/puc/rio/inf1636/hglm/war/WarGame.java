package org.puc.rio.inf1636.hglm.war;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;
import org.puc.rio.inf1636.hglm.war.model.WarState;
import org.puc.rio.inf1636.hglm.war.model.WarState.TurnState;
import org.puc.rio.inf1636.hglm.war.viewcontroller.WarFrame;

public class WarGame {

	private static WarGame instance;
	private Map map = null;
	private List<Player> players = new ArrayList<Player>();
	private WarFrame warFrame;
	private WarState warState = null;

	private WarGame() {
		this.warFrame = new WarFrame();
		this.map = new Map();
	}

	public static WarGame getInstance() {
		if (WarGame.instance == null) {
			WarGame.instance = new WarGame();
		}
		return WarGame.instance;
	}

	public void startGame() {
		Collections.shuffle(players); // randomize player order
		Util.loadTerritories(this.map);
		this.warState = new WarState(players.get(0));
		this.giveAwayTerritories();
		this.getMap().calculateNeighbors();
		players.get(0).giveArmies(
				WarLogic.calculateArmiesToGain(players.get(0)));
		this.getWarFrame().update(true);
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

	public Player getCurrentPlayer() {
		return this.warState.getCurrentPlayer();
	}

	public int getCurrentPlayerIndex() {
		return this.getPlayers().indexOf(warState.getCurrentPlayer());
	}

	public void nextTurn() {
		warState.nextTurn();
		warState.getCurrentPlayer().giveArmies(
				WarLogic.calculateArmiesToGain(warState.getCurrentPlayer()));
		warFrame.update(false);
	}

	public TurnState getTurnState() {
		return this.warState.getCurrentState();
	}

	public WarState getState() {
		return this.warState;
	}

	public WarFrame getWarFrame() {
		return this.warFrame;
	}

	private void giveAwayTerritories() {
		List<Integer> indexes = new ArrayList<Integer>();
		Iterator<Player> pi = this.getPlayers().iterator();
		for (int i = 0; i < this.getMap().getTerritories().size(); i++) {
			indexes.add(i);
		}
		Collections.shuffle(indexes);
		for (Integer i : indexes) {
			if (!pi.hasNext()) {
				pi = this.getPlayers().iterator(); // loop back
			}
			Player p = pi.next();
			this.getMap().getTerritories().get(i).setOwner(p);
			p.addTerritory();
		}
	}

	public void actionPerformed() {
		switch (this.getTurnState()) {
		case ATTACKING:
			this.warState.unselectTerritory();
			this.warFrame.update(false);
			break;
		case MOVING_ARMIES:
			break;
		case PLACING_NEW_ARMIES:
			if (this.warState.getSelectedTerritory() != null) {
				this.getWarFrame().spawnChooseNumberFrame(
						this.getCurrentPlayer().getUnplacedArmies(),
						"How many do you want to place?");
			}
			break;
		case RECEIVING_LETTER:
			break;
		default:
			break;

		}
	}

	public void selectTerritory(Territory t) {
		switch (this.getTurnState()) {
		case ATTACKING:
			if (this.getSelectedTerritory() == null) {
				this.warState.selectTerritory(t);
			} else if (t.getOwner().equals(this.getCurrentPlayer())) {
				this.warState.selectTerritory(t);
			} else if (this.getSelectedTerritory().canAttack(t)) {
				this.warState.targetTerritory(t);
				this.getWarFrame().spawnChooseNumberFrame(
						this.getSelectedTerritory().getMaxAttackArmyNumber(),
						"How many to attack with");
			}
			break;
		case MOVING_ARMIES:
			break;
		case PLACING_NEW_ARMIES:
			if (t.getOwner().equals(this.getCurrentPlayer())) {
				this.warState.selectTerritory(t);
			}
			break;
		case RECEIVING_LETTER:
			break;
		default:
			break;
		}
		this.warFrame.update(false);
	}

	public void selectNumber(int number) {
		switch (this.getTurnState()) {
		case ATTACKING:
			/* conquered */
			if (this.getSelectedTerritory().getOwner()
					.equals(this.getTargettedTerritory().getOwner())) {
				this.moveArmies(this.getSelectedTerritory(), this.getTargettedTerritory(), number - 1);
			} else {
				this.getWarFrame().spawnAttackFrame(
						this.getSelectedTerritory(),
						this.getTargettedTerritory(), number);
			}
			break;
		case MOVING_ARMIES:
			break;
		case PLACING_NEW_ARMIES:
			this.getCurrentPlayer().removeArmies(number);
			this.warState.getSelectedTerritory().addArmies(number);
			if (this.getCurrentPlayer().getUnplacedArmies() <= 0) {
				this.warState.startAttacking();
			}
			break;
		case RECEIVING_LETTER:
			break;
		default:
			break;

		}
		this.warFrame.update(false);
	}

	private Territory getTargettedTerritory() {
		return this.warState.getTargettedTerritory();

	}

	public Territory getSelectedTerritory() {
		return this.warState.getSelectedTerritory();
	}
	
	public boolean moveArmies(Territory from, Territory to, int amount) {
		if (!from.getOwner().equals(to.getOwner())) {
			return false;
		}
		if (from.getArmyCount() - 1 < amount) {
			return false;
		}
		from.removeArmies(amount);
		to.addArmies(amount);
		return true;
	}

	public void attackResult(int[] losses) {
		if (this.getState().isAttacking()) {
			this.getSelectedTerritory().removeArmies(losses[0]);
			this.getTargettedTerritory().removeArmies(losses[1]);

			/* attacker conquered */
			if (this.getTargettedTerritory().getArmyCount() == 0) {
				this.getTargettedTerritory().setOwner(
						this.getSelectedTerritory().getOwner());
				
				/* always move at least one */
				this.getSelectedTerritory().removeArmies(1);
				this.getTargettedTerritory().addArmies(1);
				
				int maxToMove = this.getSelectedTerritory().getMaxAttackArmyNumber() + 1;
				if (maxToMove > 3) {
					maxToMove = 3;
				}
				
				this.warFrame.spawnChooseNumberFrame(maxToMove,
						String.format("How many armies to move from %s to %s?",
								this.getSelectedTerritory().getName(), this
										.getTargettedTerritory().getName()));
			}
		}
		this.warFrame.update(false);
	}
}
