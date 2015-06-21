package org.puc.rio.inf1636.hglm.war.model;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.puc.rio.inf1636.hglm.war.WarLogic;

public class Territory extends Object {

	private String name;
	private GeneralPath polygon;
	private Point2D.Double center = new Point2D.Double(0., 0.);
	private Player owner = null;
	private int armyCount = 1;
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

	public int getArmyCount() {
		return this.armyCount;
	}

	public int addArmies(int armies) {
		this.armyCount += armies;
		return this.armyCount;
	}
	
	public int removeArmies(int armies) {
		this.armyCount -= armies;
		return this.armyCount;
	}

	public void setArmies(int armies) {
		this.armyCount = armies;
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

	public boolean isNeighbor(Territory t) {
		return this.neighbors.contains(t);
	}

	public boolean canAttack(Territory t) {
		if (this.getArmyCount() <= 1) {
			return false;
		}
		if (t.getOwner().equals(this.getOwner())) {
			return false;
		}
		if (!this.isNeighbor(t)) {
			return false;
		}
		return true;
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

	public int getMaxAttackArmyNumber() {
		if (this.getArmyCount() == 1) {
			return 0;
		} else if (this.getArmyCount() > WarLogic.MAX_DICE) {
			return WarLogic.MAX_DICE;
		} else {
			return this.getArmyCount() - 1;
		}
	}
}