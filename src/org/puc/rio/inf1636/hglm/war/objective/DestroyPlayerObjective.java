package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.WarGame;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;

public class DestroyPlayerObjective extends WarObjective {

	String targetPlayerName;

	public DestroyPlayerObjective(String targetPlayerName) {
		super(String.format("Destroy %s completely", targetPlayerName));
		this.targetPlayerName = targetPlayerName;
	}

	@Override
	public boolean checkVictory(Map m, Player p) {
		Player targetPlayer = WarGame.getInstance().getPlayerByName(targetPlayerName);
		if (m.getTerritoriesByOwner(targetPlayer).size() == 0) {
			return true;
		}
		return false;
	}

	public String getTargetPlayerName() {
		return this.targetPlayerName;
	}

}
