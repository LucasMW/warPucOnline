package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class Conquer24TerritoriesObjective extends WarObjective {

	public Conquer24TerritoriesObjective(String description) {
		super("Conquistar 24 TERRITÓRIOS à sua escolha");
	}

	@Override
	public
	boolean checkVictory(Map m, Player p) {
		
		if(p.getNumberOfTerritories() >= 24) {
			return true;
		}
		return false;
	}

}
