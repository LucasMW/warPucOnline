package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class DestroyPlayerObjective extends WarObjective {

	Player targetPlayer;
	public DestroyPlayerObjective(String description,Player targetPlayer ) {
		super("Destruir totalmente " + targetPlayer.getName());
		this.targetPlayer= targetPlayer;
	}

	@Override
	boolean checkVictory(Map m, Player p) {
		
		if(this.targetPlayer.getNumberOfTerritories() == 0) {
			return true;
		}
		return false;
	}

}
