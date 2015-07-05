package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class ConquerTerritoriesObjective extends WarObjective {

	int numberOfTerritories;
	int numberOfArmies;

	public ConquerTerritoriesObjective(int numberOfTerritories,
			int numberOfArmies) {
		super(
				String.format(
						"Conquer %d territories and occupy them with at least %d armies.",
						numberOfTerritories, numberOfArmies));
	}

	@Override
	public boolean checkVictory(Map m, Player p) {
		int numberOfTerritoriesOwnedWithCondition = 0;
		for (Territory t : m.getTerritories()) {
			if (t.getOwner().equals(p)
					&& t.getArmyCount() >= this.numberOfArmies) {
				numberOfTerritoriesOwnedWithCondition++;
			}
		}

		return numberOfTerritoriesOwnedWithCondition >= this.numberOfTerritories;
	}

}
