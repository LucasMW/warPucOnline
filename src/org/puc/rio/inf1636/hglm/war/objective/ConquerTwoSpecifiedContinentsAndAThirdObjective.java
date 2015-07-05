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
		int chosenContinents=0;
		boolean flagUnspecified=false;
		for(Continent c : Continent.values()) { //for each continent
			int tCont =0;
			int pCont =0;
			for(Territory t : m.getTerritoriesByContinent(c)) {
					tCont++;
					if(t.getOwner().equals(p)){
						pCont++;
					}
				}
			if(tCont==pCont) { //Player conquered unspecified Territory
				if(c.equals(this.targetContinent1) || c.equals(this.targetContinent2)) {
					chosenContinents++;
				}
				else {
					flagUnspecified=true;
				}
			}
			
		}
		return flagUnspecified && chosenContinents >=2;
	}

}
