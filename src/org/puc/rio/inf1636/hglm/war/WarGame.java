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
		Dimension mapSize = this.getWarFrame().getMapPanel().getMapSize();
		this.multiplierX = (mapSize.width / 1024.0);
		this.multiplierY = (mapSize.height / 768.0);
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

	public void nextTurn() {
		this.currentPlayerIndex++;
		System.out.println(this.currentPlayerIndex);
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
			System.out.println(pair.getKey());
			List<Point2D.Double> points = new LinkedList<Point2D.Double>();
			for (List<Double> point : values) {
				points.add(new Point2D.Double((double) point.get(0)
						* multiplierX, (double) point.get(1) * multiplierY));
				System.out.printf("px<%f %f> ", point.get(0), point.get(1));
				System.out.printf("VS px<%f %f>\n", point.get(0) * multiplierX,
						point.get(1) * multiplierY);
			}
			this.map.addTerritory(new Territory(pair.getKey(), points));
			it.remove();
		}
	}

	private static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}

}
