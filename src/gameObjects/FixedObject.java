package gameObjects;

import com.mycompany.a3.GameObject;

public abstract class FixedObject extends GameObject {
	private static int idCounter = 0; // counter
	private final int id; // identification number of the object
	
	/* Constructor */
	public FixedObject() {
		id = ++idCounter; // assign id to next value in counter
	}
	
	/* Return id */
	public int getID() {
		return id;
	}
}
