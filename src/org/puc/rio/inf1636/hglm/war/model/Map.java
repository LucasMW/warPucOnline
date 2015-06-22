package org.puc.rio.inf1636.hglm.war.model;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.Util;

public class Map extends Object {

	private List<Territory> territories = new ArrayList<Territory>();

	public Map() {

	}

	public void addTerritory(Territory t) {
		this.territories.add(t);
	}

	public List<Territory> getTerritories() {
		return this.territories;
	}

	public void calculateNeighbors() {
		ArrayList<Line2D.Double> tLines = new ArrayList<>();
		ArrayList<Line2D.Double> uLines = new ArrayList<>();

		for (Territory t : territories) {
			tLines = Util.getLineSegments(t.getPolygon());
			for (Territory u : territories) {
				if (!t.equals(u)) {
					uLines = Util.getLineSegments(u.getPolygon());
					for (Line2D.Double tl : tLines) {
						for (Line2D.Double ul : uLines) {
							if (tl.intersectsLine(ul)) {
								t.addNeighbor(u);
								continue;
							}
						}
					}
				}
			}
		}
		// add bridged neighbors
		this.bridgeTerritoriesByNames("Argélia", "Espanha");
		this.bridgeTerritoriesByNames("Argélia", "Itália");
		this.bridgeTerritoriesByNames("Alasca", "Sibéria");
		this.bridgeTerritoriesByNames("Brasil", "Nigéria");
		this.bridgeTerritoriesByNames("Austrália", "Indonésia");
		this.bridgeTerritoriesByNames("Austrália", "Nova Zelândia");
		this.bridgeTerritoriesByNames("Bangladesh", "Indonésia");
		this.bridgeTerritoriesByNames("Egito", "România");
		this.bridgeTerritoriesByNames("Egito", "Jordânia");
		this.bridgeTerritoriesByNames("França", "Reino Unido");
		this.bridgeTerritoriesByNames("Groelandia", "Reino Unido");
		this.bridgeTerritoriesByNames("Índia", "Indonésia");
		this.bridgeTerritoriesByNames("Japão", "Cazaquistão");
		this.bridgeTerritoriesByNames("Japão", "Coréia do Norte");
		this.bridgeTerritoriesByNames("Japão", "Mongólia");
		this.bridgeTerritoriesByNames("Somália", "Arábia Saudita");
		this.bridgeTerritoriesByNames("Suécia", "França");
		this.bridgeTerritoriesByNames("Suécia", "Itália");

	}

	private void bridgeTerritoriesByNames(String nameX, String nameY) {
		Territory x, y;
		x = this.getTerritoryByName(nameX);
		y = this.getTerritoryByName(nameY);
		if (x == null || y == null) {
			System.out.println("not found" + nameX + " " + nameY);
			return;
		}
		// System.out.println("found " + nameX + " " + nameY);
		x.addNeighbor(y);
		y.addNeighbor(x);
	}

	public Territory getTerritoryByName(String name) {
		for (Territory t : this.territories) {
			if (t.getName().equals(name)) {
				// System.out.println(name + "Found");
				return t;
			}
		}
		System.out.println(name + " notFound");
		return null;

	}

}
