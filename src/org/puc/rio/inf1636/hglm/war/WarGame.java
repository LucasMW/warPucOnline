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

	public WarState getWarState() {
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
		if (this.getWarState().isAttacking()) {
			this.getWarFrame().attack();
		}
	}

	public void selectTerritory(Territory t) {
		this.warState.selectTerritory(t);
		this.warFrame.update(false);
	}
}
