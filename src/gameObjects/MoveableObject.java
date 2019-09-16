package gameObjects;

import java.util.Random;
import com.mycompany.a3.GameObject;

public abstract class MoveableObject extends GameObject implements IMoveable {
	private int speed, direction; // object attributes
	
	/* Constructor */
	public MoveableObject() {
		Random r = new Random();
		speed = r.nextInt(10)+1;    // random integer from 1-10
		direction = r.nextInt(360); // random integer from 0-359
	}
	
	/* Set speed to given value inS */
	public void setSpeed(int inS) {
		speed = inS;
	}
	
	/* Return speed value */
	public int getSpeed() {
		return speed;
	}
	
	/* Set direction to given value inD */
	public void setDirection(int inD) {
		direction = inD;
	}
	
	/* Return direction value */
	public int getDirection() {
		return direction;
	}
}
