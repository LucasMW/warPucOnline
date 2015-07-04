package org.puc.rio.inf1636.hglm.war.model;

public class CardObjective implements WarObjective {

	private String d = "This is the first objective rules";
	
	@Override
	public boolean check() {
		return false;	
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return this.d;
	}
	
	

}
