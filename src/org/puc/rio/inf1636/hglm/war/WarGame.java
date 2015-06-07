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
import org.puc.rio.inf1636.hglm.war.viewcontroller.MapPanel;
import org.puc.rio.inf1636.hglm.war.viewcontroller.WarFrame;

import com.google.gson.Gson;


public class WarGame {

	private static WarGame instance = null;
	private Map map = null;
	private List<Player> players = new ArrayList<Player>();
	private int currentPlayerIndex = 0;
	public final static int MAX_PLAYERS = 6;
	public final static int MIN_PLAYERS = 3;
	private Territory currentTerritory = null;
	public WarFrame gameFrame =null;
	
	private static double multiplyerX;
	private static double multiplyerY;

	public WarGame() {
		gameFrame= new WarFrame();
		
		
		this.map = new Map();
		multiplyerX = (MapPanel.getMapSize().width/1024.0);
		multiplyerY = (MapPanel.getMapSize().height/768.0);
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
	
	public void selectTerritory(Territory t)
	{
		currentTerritory = t;
		gameFrame.selectedTerritory();
	}
	public Territory getCurrentTerritory()
	{
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
	public void nextTurn()
	{	
		if(this.currentPlayerIndex++ >= players.size())
			this.currentPlayerIndex=0;
		gameFrame.battleEnded();
		
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
				points.add(new Point2D.Double((double) point.get(0) *multiplyerX ,
						(double) point.get(1)*multiplyerY));
				System.out.printf("px<%f %f> ",point.get(0),point.get(1));
				System.out.printf("VS px<%f %f>\n",point.get(0) *multiplyerX ,point.get(1) * multiplyerY);
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
