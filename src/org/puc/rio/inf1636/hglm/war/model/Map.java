package org.puc.rio.inf1636.hglm.war.model;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.Util;

public class Map extends Object {

	private List<Territory> territories = new ArrayList<Territory>();
	
	public Map() {

	}

	public void addTerritory(Territory t) {
		this.territories.add(t);
	}

	public List<Territory> getTerritories() {
		return this.territories;
	}	
	
	public void calculateNeighbors() {
        ArrayList<Line2D.Double> tLines = new ArrayList<>();
        ArrayList<Line2D.Double> uLines = new ArrayList<>();
		
		 for (Territory t: territories) {
			 tLines = Util.getLineSegments(t.getPolygon());
			 for (Territory u: territories) {
				 if (!t.equals(u)) {
					 uLines = Util.getLineSegments(u.getPolygon());
					 for (Line2D.Double tl : tLines) {
						 for (Line2D.Double ul: uLines) {
							 if (tl.intersectsLine(ul)) {
								 t.addNeighbor(u);
								 continue;
							 }
						 }
					 }
				 }
			 }
		 }
	}
}