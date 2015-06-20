package org.puc.rio.inf1636.hglm.war.model;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Territory extends Object {

	private String name;
	private GeneralPath polygon;
	private Point2D.Double center = new Point2D.Double(0., 0.);
	private Player owner = null;
	private int troopCount = 1;
	private Set<Territory> neighbors = new HashSet<Territory>();

	public Territory(String name, List<Point2D.Double> points) {
		this.name = name;
		this.createPolygon(points);
	}
	
	public void setOwner(Player p) {
		this.owner = p;
	}
	
	public Player getOwner() {
		return this.owner;
	}

	public GeneralPath getPolygon() {
		return this.polygon;
	}
	
	public Point2D.Double getCenter() {
		return this.center;
	}

	public int getTroopCount() {
		return this.troopCount;
	}
	
	public int addTroops(int troops) {
		this.troopCount += troops;
		return this.troopCount;
	}
	
	public int setTroops(int troops) {
		this.troopCount = troops;
		return this.troopCount;
	}

	public String getName() {
		return this.name;
	}
	
	public void addNeighbor(Territory t) {
		this.neighbors.add(t);
	}
	
	public Set<Territory> getNeighbors() {
		return this.neighbors;
	}
		
	@Override
	public boolean equals(Object another) {
		return this.name == ((Territory) another).getName();
	}
	
	private void createPolygon(List<Point2D.Double> points) {
		GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		boolean first = true;
		for (Point2D.Double point : points) {
			if (first) {
				gp.moveTo(point.x, point.y);
				first = false;
			} else {
				gp.lineTo(point.x, point.y);
			}
			this.center.x += point.x;
			this.center.y += point.y;
		}
		this.center.x /= points.size();
		this.center.y /= points.size();
		gp.closePath();
		this.polygon = gp;
	}
}