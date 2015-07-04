package org.puc.rio.inf1636.hglm.war.model;


public enum Continent {

	NORTH_AMERICA(0), SOUTH_AMERICA(1), AFRICA(2), EUROPE(3), ASIA(4), OCEANIA(
			5);
	private final int id;

	Continent(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static Continent getById(int id) {
		for (Continent c: Continent.values()) {
			if (c.id == id) {
				return c;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return super.toString().replace("_", " ");
	}
}
