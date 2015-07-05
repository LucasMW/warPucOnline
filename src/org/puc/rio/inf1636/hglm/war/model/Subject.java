package org.puc.rio.inf1636.hglm.war.model;

import java.util.ArrayList;
import java.util.List;

import org.puc.rio.inf1636.hglm.war.viewcontroller.Observer;


public abstract class Subject {
	private List<Observer> observers = new ArrayList<Observer>();
	
	public void attach(Observer observer) {
		observers.add(observer);
	}
	
	public void notifyAllObservers() {
		for (Observer o: observers) {
			o.update();
		}
	}
}
