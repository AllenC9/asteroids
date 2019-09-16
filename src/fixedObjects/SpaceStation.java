package fixedObjects;

import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.ICollider;
import gameObjects.FixedObject;
import moveableObjects.Ship;
import objectGraphics.SpaceStationGraphic;

public class SpaceStation extends FixedObject {
	private static SpaceStation myStation;
	private SpaceStationGraphic thisStation;
	private int blinkRate; // number of seconds light is on/off
	private int width, height;
	
	/* Constructor, private access for singleton design */
	private SpaceStation() {
		setColor(ColorUtil.MAGENTA);     // magenta
		this.width = 100;                // width always = 100
		this.height = 30;                // height always = 30
		this.blinkRate = new Random().nextInt(6); // random integer from 0-5
		setLocation(					 // fixed location for station
				Game.getMapWidth()-100,		
				Game.getMapHeight()-40);
		// graphical object
		this.thisStation = new SpaceStationGraphic(width,height);
		thisStation.translate(getLocation().getX(), getLocation().getY());
	}
	
	/* Return station, create if non-existing */
	public static SpaceStation getStation() {
		if (myStation == null)
			myStation = new SpaceStation();
		return myStation;
	}
	
	/* Return width value */
	public int getWidth() {
		return width;
	}
	
	/* Return height value */
	public int getHeight() {
		return height;
	}
	
	/* Return blinkRate value */
	public int getBlinkRate() {
		return blinkRate;
	}
	
	/* Override toString() for outputting object state information */
	public String toString() {
		return ("Station:" + 
				" loc=" + getLocation().getX() + "," + getLocation().getY() +
				" color=[" + ColorUtil.red(getColor()) + "," +
							 ColorUtil.green(getColor()) + "," +
							 ColorUtil.blue(getColor()) + "]" +
				" rate=" + blinkRate);
	}

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(this.getColor());
		thisStation.draw(g, pCmpRelPrnt, pCmpRelScrn, isSelected());
	}

	public boolean collidesWith(ICollider otherObject) {
		// return if not an object that collides with this one
		if (!(otherObject instanceof Ship)) return false;
		// find distance between centers
		int dx = getLocation().getX()-((GameObject)otherObject).getLocation().getX();
		int dy = getLocation().getY()-((GameObject)otherObject).getLocation().getY();
		int thisR = /* Temporary until later optimized */ width/2;
		// collision detection, return true if collision occurs
		if (otherObject instanceof Ship) {
			/* Temporary until later optimized */
			// find "radius"
			int otherR = ((Ship)otherObject).getHeight()/2;
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR))
				return true;
		} // end Ship
		return false; // else no collision
	}

	public void handleCollision(ICollider otherObject) {
		((Ship)otherObject).setMissileCount(12);
	}

	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		if (!Game.isPaused()) return false;
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + getLocation().getX();
		int yLoc = pCmpRelPrnt.getY() + getLocation().getY();
		int selectRx = width/2;
		int selectRy = height/2;
		
		if ((px > xLoc-selectRx && px < xLoc+selectRx)
			&&
			(py > yLoc-selectRy && py < yLoc+selectRy))
			return true;
		return false;
	}
}
