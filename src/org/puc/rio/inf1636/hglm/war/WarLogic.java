package org.puc.rio.inf1636.hglm.war;

import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;

public class WarLogic {

	public final static int MAX_PLAYERS = 6;
	public final static int MIN_PLAYERS = 3;
	public final static int MAX_DICE = 3;

	public static int calculateArmiesToGain(Map m, Player p) {
		int countFromTotalTerritories = p.getNumberOfTerritories() / 2;
		int countFromContinentsOwned = 0;
		
		/* that's the minimum specified in the manual */
		if (countFromTotalTerritories < 3) {
			countFromTotalTerritories = 3;
		}
		
		for (Continent c : m.getContinentsOwnedByPlayer(p)) {
			System.out.println(String.format("Player %s owns %s and gains %d territories from it", p.getName(), c.toString(), c.getTerritoriesToGain()));
			countFromContinentsOwned += c.getTerritoriesToGain();
		}
		return countFromTotalTerritories + countFromContinentsOwned;
	}
}
