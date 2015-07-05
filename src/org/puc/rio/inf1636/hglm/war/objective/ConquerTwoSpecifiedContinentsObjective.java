package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class ConquerTwoSpecifiedContinentsObjective extends WarObjective {


Continent targetContinent1;
Continent targetContinent2;
	public ConquerTwoSpecifiedContinentsObjective(String description, Continent c1, Continent c2) {
		super("Conquistar na totalidade a "+ c1.toString() + " e " + c2.toString());
		this.targetContinent1=c1;
		this.targetContinent2=c2;
	}

	@Override
	public
	boolean checkVictory(Map m, Player p) {
		for(Territory t : m.getTerritoriesByContinent(this.targetContinent1)) {
			if(!t.getOwner().equals(p)) {
				return false; //Player fails in specified
			}
		}
		for(Territory t : m.getTerritoriesByContinent(this.targetContinent2)) {
			if(!t.getOwner().equals(p)) {
				return false; //Player fails in specified
			}
		}
		return true;
	}

}
