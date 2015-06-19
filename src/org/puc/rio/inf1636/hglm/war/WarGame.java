package org.puc.rio.inf1636.hglm.war;

import java.awt.Dimension;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;
import org.puc.rio.inf1636.hglm.war.viewcontroller.WarFrame;

import com.google.gson.Gson;

public class WarGame {

	private static WarGame instance;
	private Map map = null;
	private List<Player> players = new ArrayList<Player>();
	private int currentPlayerIndex = 0;
	private Territory currentTerritory;
	private double multiplierX;
	private double multiplierY;

	public static final double MULTIPLIER_X = 1.0;
	public static final double MULTIPLIER_Y = 1.0;

	private WarFrame warFrame;

	public final static int MAX_PLAYERS = 6;
	public final static int MIN_PLAYERS = 3;
	public final static int MAX_DICE = 3;

	private WarGame() {
		this.warFrame = new WarFrame();
		this.map = new Map();
		loadTerritories();
	}

	public static WarGame getInstance() {
		if (WarGame.instance == null) {
			WarGame.instance = new WarGame();
		}
		return WarGame.instance;
	}

	public void startGame() {
		Collections.shuffle(players); // randomize player order
		this.loadTerritories();
		this.giveAwayTerritories();
		this.getWarFrame().getMapPanel().renderTroopLabels(true);
	}

	public Map getMap() {
		return this.map;
	}

	public void selectTerritory(Territory t) {
		currentTerritory = t;
		warFrame.selectedTerritory();
	}

	public Territory getCurrentTerritory() {
		return currentTerritory;
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

	public static Dimension getGameSize() {
		Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		return new Dimension((int) (screenSize.width * MULTIPLIER_X),
				(int) (screenSize.height * MULTIPLIER_Y));
	}

	private void loadTerritories() {
		String jsonContent;
		try {
			jsonContent = readFile("resources/territoryBounds.json",
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		java.util.Map<String, List<List<Double>>> territories = new Gson()
				.fromJson(jsonContent, java.util.Map.class);
		Iterator it = territories.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry<String, List<List<Double>>> pair = (java.util.Map.Entry<String, List<List<Double>>>) it
					.next();
			List<List<Double>> values = pair.getValue();
			List<Point2D.Double> points = new LinkedList<Point2D.Double>();
			for (List<Double> point : values) {
				points.add(new Point2D.Double(
						(double) point.get(0)
								* this.getWarFrame().getMapPanel().coordinatesMultiplierX,
						(double) point.get(1)
								* this.getWarFrame().getMapPanel().coordinatesMultiplierY));
			}
			this.map.addTerritory(new Territory(pair.getKey(), points));
			it.remove();
		}
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
				this.getMap().getTerritories().get(i).setOwner(pi.next());
		}
	}

	private static String readFile(String path, Charset encoding)
			throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
