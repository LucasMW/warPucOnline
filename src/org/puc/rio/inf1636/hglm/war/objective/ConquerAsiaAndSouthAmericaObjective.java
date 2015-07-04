package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class ConquerAsiaAndSouthAmericaObjective extends WarObjective {

	public ConquerAsiaAndSouthAmericaObjective(String description) {
		super(description);
	}

	@Override
	boolean checkVictory(Map m, Player p) {
		for (Territory t : m.getTerritories()) {
			/* if someone else owns a territory in asia or south america */
			if (!t.getOwner().equals(p)
					&& (t.getContinent() == Continent.ASIA || t.getContinent() == Continent.SOUTH_AMERICA)) {
				return false;
			}
		}
		return true;
	}

}
