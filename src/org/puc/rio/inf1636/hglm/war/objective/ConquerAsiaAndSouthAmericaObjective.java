package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;


public class DestroyAsiaAndSouthAmericaObjective extends WarObjective {
	

	public DestroyAsiaAndSouthAmericaObjective(String description) {
		super(description);
	}

	@Override
	boolean checkVictory(Map m, Player p) {
		for (Territory t : m.getTerritories()) {
			if (!t.getOwner().equals(p)) {
				return false;
			}
			return true;
		}
		return false;
	}	
	

}
