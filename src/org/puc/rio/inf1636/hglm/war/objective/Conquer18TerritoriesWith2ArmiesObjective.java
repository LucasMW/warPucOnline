package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class Conquer18TerritoriesWith2ArmiesObjective extends WarObjective {

	public Conquer18TerritoriesWith2ArmiesObjective(String description) {
		super("Conquistar 18 TERRITÓRIOS e ocupar cada um deles com pelo menos dois exércitos.");
	}

	@Override
	boolean checkVictory(Map m, Player p) {
		
		if(p.getNumberOfTerritories() >= 18)
		{
			int cont =0 ;
			for (Territory t : m.getTerritories()){
				if(t.getOwner().equals(p) && t.getArmyCount() >=2) {
					cont++;
				}
			}
			if(cont>=18) {
				return true;
			}
		}
		return false;
	}

}
