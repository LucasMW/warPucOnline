package org.puc.rio.inf1636.hglm.war.model;

import java.util.ArrayList;
import java.util.List;

public class Map extends Object {

	private List<Territory> territories = new ArrayList<Territory>();
	private Territory currentTerritory;
	
	public Map() {

	}

	public void addTerritory(Territory t) {
		this.territories.add(t);
	}

	public List<Territory> getTerritories() {
		return this.territories;
	}
	
	public void setCurrentTerritory(Territory t) {
		this.currentTerritory = t;
	}
	
	public Territory getCurrentTerritory() {
		return this.currentTerritory;
	}
}