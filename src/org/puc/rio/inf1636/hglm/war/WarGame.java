package org.puc.rio.inf1636.hglm.war;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.viewcontroller.WarFrame;

public class WarGame {

	private static WarGame instance;
	private Map map = null;
	private List<Player> players = new ArrayList<Player>();
	private int currentPlayerIndex = 0;
	private double multiplierX;
	private double multiplierY;
	private WarFrame warFrame;

	public final static int MAX_PLAYERS = 6;
	public final static int MIN_PLAYERS = 3;
	public final static int MAX_DICE = 3;

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
		this.giveAwayTerritories();
		this.getWarFrame().getMapPanel().updateTroopLabels(true);
		this.getMap().calculateNeighbors();
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
		return this.players.get(this.currentPlayerIndex);
	}

	public int getCurrentPlayerIndex() {
		return this.currentPlayerIndex;
	}

	public void nextTurn() {
		this.currentPlayerIndex++;
		if (this.currentPlayerIndex >= players.size())
			this.currentPlayerIndex = 0;
		warFrame.battleEnded();

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
				pi = this.getPlayers().iterator(); //loop back
			}
			Player p = pi.next();
			this.getMap().getTerritories().get(i).setOwner(p);
			p.addTerritory();
		}
	}



}
