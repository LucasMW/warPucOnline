package org.puc.rio.inf1636.hglm.war.model;

import java.util.HashSet;
import java.util.Set;

public class Map {

	private Set<Territory> territories = new HashSet<Territory>();

	public Map() {

	}

	public void addTerritory(Territory t) {
		this.territories.add(t);
	}

	public Set<Territory> getTerritories() {
		return this.territories;
	}
}