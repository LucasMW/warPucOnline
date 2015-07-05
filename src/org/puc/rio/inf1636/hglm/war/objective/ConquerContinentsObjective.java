package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class ConquerContinentsObjective extends WarObjective {

	Continent targetContinent1;
	Continent targetContinent2;
	boolean hasToConquerAThirdContinent;

	public ConquerContinentsObjective(Continent c1,
			Continent c2, boolean hasToConquerAThirdContinent) {
		super(String.format("Conquer %s, %s %s", c1.toString(), c2.toString(),
				hasToConquerAThirdContinent ? "and any other continent" : ""));
		this.targetContinent1 = c1;
		this.targetContinent2 = c2;
		this.hasToConquerAThirdContinent = hasToConquerAThirdContinent;
	}

	@Override
	public boolean checkVictory(Map m, Player p) {
		boolean conqueredTargetContinents = true;
		boolean conqueredAnotherContinent = false;
		for (Continent c : Continent.values()) { // for each continent
			int totalTerritoriesCount = 0;
			int territoriesOwnedByPlayerCount = 0;
			for (Territory t : m.getTerritoriesByContinent(c)) {
				totalTerritoriesCount++;
				if (t.getOwner().equals(p)) {
					territoriesOwnedByPlayerCount++;
				}
			}
			if (territoriesOwnedByPlayerCount < totalTerritoriesCount) {
				/* Player didn't conquer targeted continent */
				if (c.equals(this.targetContinent1)
						|| c.equals(this.targetContinent2)) {
					conqueredTargetContinents = false;
				}
			} else {
				/* Player conquered whole third continent */
				if (!c.equals(this.targetContinent1) && !c.equals(this.targetContinent2)) {
					conqueredAnotherContinent = true;
				}
			}
		}
		return conqueredTargetContinents && (conqueredAnotherContinent || !this.hasToConquerAThirdContinent);
	}

}
