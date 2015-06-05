package org.puc.rio.inf1636.hglm.war;

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

	private static WarGame instance = null;
	private Map map = null;
	private List<Player> players = new ArrayList<Player>();
	private int currentPlayerIndex = 0;
	public final static int MAX_PLAYERS = 6;
	public final static int MIN_PLAYERS = 3;


	public WarGame() {
		WarFrame gameFrame = new WarFrame();
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
				points.add(new Point2D.Double((double) point.get(0),
						(double) point.get(1)));
			}
			this.map.addTerritory(new Territory(pair.getKey(), points));
			it.remove();
		}
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
