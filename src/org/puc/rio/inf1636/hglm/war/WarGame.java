package org.puc.rio.inf1636.hglm.war;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Territory;
import org.puc.rio.inf1636.hglm.war.viewcontroller.WarFrame;

import com.google.gson.Gson;

public class WarGame {

	private static WarGame instance = null;
	private org.puc.rio.inf1636.hglm.war.model.Map map = null;

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

	private void loadTerritories() {
		String jsonContent;
		try {
			jsonContent = readFile("resources/territoryBounds.json",
					StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		java.util.Map<String, Object> territories = new Gson().fromJson(jsonContent, java.util.Map.class);
		Iterator it = territories.entrySet().iterator();
		while (it.hasNext()) {
			java.util.Map.Entry pair = (java.util.Map.Entry) it.next();
			ArrayList values = (ArrayList) pair.getValue();
			List<Point2D.Double> points = new LinkedList<Point2D.Double>();
			for (Object point: values) {
				ArrayList pointArray = (ArrayList) point;
				points.add(new Point2D.Double((double) pointArray.get(0), (double) pointArray.get(1)));
			}
			this.map.addTerritory(new Territory((String) pair.getKey(), points));
			it.remove();
		}
	}

	public Map getMap() {
		return this.map;
	}

	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
