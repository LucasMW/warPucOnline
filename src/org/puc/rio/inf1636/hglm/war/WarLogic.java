package org.puc.rio.inf1636.hglm.war;

import org.puc.rio.inf1636.hglm.war.model.Player;

public class WarLogic {

	public final static int MAX_PLAYERS = 6;
	public final static int MIN_PLAYERS = 3;
	public final static int MAX_DICE = 3;

	public static int calculateArmiesToGain(Player p) {
		int x = p.getNumberOfTerritories() / 2;
		/* that's the minimum specified in the manual */
		if (x < 3) {
			x = 3;
		}
		return x;
	}
}
