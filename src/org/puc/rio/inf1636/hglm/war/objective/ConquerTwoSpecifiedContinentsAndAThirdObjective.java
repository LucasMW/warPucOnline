package org.puc.rio.inf1636.hglm.war.objective;

import org.puc.rio.inf1636.hglm.war.model.Continent;
import org.puc.rio.inf1636.hglm.war.model.Map;
import org.puc.rio.inf1636.hglm.war.model.Player;
import org.puc.rio.inf1636.hglm.war.model.Territory;

public class ConquerTwoSpecifiedContinentsAndAThirdObjective extends WarObjective {


Continent targetContinent1;
Continent targetContinent2;
	public ConquerTwoSpecifiedContinentsAndAThirdObjective(String description, Continent c1, Continent c2) {
		super("Conquistar na totalidade a "+ c1.toString() + ", " + c2.toString() + " e um terceiro");
		this.targetContinent1=c1;
		this.targetContinent2=c2;
	}

	@Override
	public
	boolean checkVictory(Map m, Player p) {
		for(Continent c : Continent.values()) { //for each continent
			int tCont =0;
			int pCont =0;
			for(Territory t : m.getTerritoriesByContinent(c)) {
				if(c.equals(targetContinent1) || c.equals(targetContinent2)){
					if(!t.getOwner().equals(p)) {
						return false; //Player fails in specified
					}
				}
				else { // unspecified continent
					tCont++;
					if(t.getOwner().equals(p)){
						pCont++;
					}
					if(tCont==pCont) { //Player conquered unspecified Territory
						return true;
					}
				}
			}
		}
		return false;
	}

}
