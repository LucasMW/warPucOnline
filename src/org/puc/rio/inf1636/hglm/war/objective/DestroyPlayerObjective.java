package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;

public class DestroyPlayerObjective extends WarObjective {

	Player targetPlayer;

	public DestroyPlayerObjective(Player targetPlayer) {
		super(String.format("Destroy %s completely", targetPlayer.getName()));
		this.targetPlayer = targetPlayer;
	}

	@Override
	public boolean checkVictory(Map m, Player p) {
		if (this.targetPlayer.getNumberOfTerritories() == 0) {
			return true;
		}
		return false;
	}

	public Player getTargetPlayer() {
		return this.targetPlayer;
	}

}
