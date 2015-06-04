package org.puc.rio.inf1636.hglm.war.model;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.List;

public class Territory {

	private String name;
	private GeneralPath polygon;

	public Territory(String name, List<Point2D.Double> points) {

		this.name = name;

		GeneralPath gp = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		boolean first = true;
		for (Point2D.Double point : points) {
			if (first) {
				gp.moveTo(point.x, point.y);
				first = false;
			} else {
				gp.lineTo(point.x, point.y);
			}
		}
		gp.closePath();
		this.polygon = gp;
	}

	public GeneralPath getPolygon() {
		return this.polygon;
	}

	public String getName() {
		return this.name;
	}
}