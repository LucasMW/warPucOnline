package org.puc.rio.inf1636.hglm.war.model;

import java.util.ArrayList;
import java.util.List;

public class Map {

	private List<Territory> territories = new ArrayList<Territory>();
	
	public Map() {

	}

	public void addTerritory(Territory t) {
		this.territories.add(t);
	}

	public List<Territory> getTerritories() {
		return this.territories;
	}
}