package com.mycompany.a3;

import java.util.Random;
import com.codename1.ui.geom.Point;

public abstract class GameObject implements IDrawable, ICollider, ISelectable {
	private Point location;         // location of object
	private int color;		        // color of object
	private boolean selected;       // flag if selected
	private boolean destroyed;      // flag if destroyed
	
	/* Constructor */
	public GameObject() {
		Random r = new Random();
		this.destroyed = false;
		// location randomly generated outside map
		location = new Point(
			r.nextInt(10) > 4 ? // location x value
					Game.getMapWidth()+r.nextInt(21)+10 :
					0-r.nextInt(21)-10,   
			r.nextInt(10) > 4 ? // location y value
					Game.getMapHeight()+r.nextInt(21)+10 :
					0-r.nextInt(21)-10); 
	}
	
	/* Set location to given (x,y) value */
	public void setLocation(int x, int y) {
		location.setX(x);
		location.setY(y);
	}
	
	/* Return location value */
	public Point getLocation() {
		return location;
	}
	
	/* Set color to given RGB value */
	public void setColor(int colorValue) {
		color = colorValue;
	}
	
	/* Return color value */
	public int getColor() {
		return color;
	}
	
	/* Set flag for object to be destroyed */
	public void destroy() {
		destroyed = true;
	}
	
	/* Return destroyed flag */
	public boolean isDestroyed() {
		return destroyed;
	}
	
	/* Set selected value */
	public void setSelected(boolean yesNo) {
		selected = yesNo;		
	}

	/* Return selected value */
	public boolean isSelected() {
		return selected;
	}
}
