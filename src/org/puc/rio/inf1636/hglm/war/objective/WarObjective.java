package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;

public abstract class WarObjective {
	private String description;
	
	public WarObjective(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}

	abstract boolean checkVictory(Map m, Player p);
}
