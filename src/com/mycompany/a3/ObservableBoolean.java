package com.mycompany.a3;

import java.util.Observable;

public class ObservableBoolean extends Observable {
	private boolean bool;
	
	/* Constructor */
	public ObservableBoolean() {}
	
	/* Constructor */
	public ObservableBoolean(boolean value) {
		bool = value;
	}
	
	/* Notify observers of change */
	public void observableUpdate() {
		setChanged(); notifyObservers();
	}
	
	/* Set value */
	public void setValue(boolean value) {
		bool = value; observableUpdate();
	}
	
	/* Get value */
	public boolean getValue() {
		return bool;
	}
}
