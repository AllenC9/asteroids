package moveableObjects;

import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.ICollider;
import fixedObjects.SpaceStation;
import gameObjects.MoveableObject;
import objectGraphics.AsteroidGraphic;

public class Asteroid extends MoveableObject implements Runnable {
	private AsteroidGraphic thisAsteroid;
	private int size; // determines dimensions of object
	private boolean indestructible;
	private boolean destroyedByMissile;
	
	/* Constructor */
	public Asteroid() {
		this.setColor(ColorUtil.GRAY); 		  	    // gray
		this.size = new Random().nextInt(25) + 6;   // random integer from 6-30
		this.indestructible = true;
		this.destroyedByMissile = false;
		UITimer.timer(1000, false, this);
		// graphical object
		this.thisAsteroid = new AsteroidGraphic(size*2);
		thisAsteroid.translate(getLocation().getX(), getLocation().getY());
	}
	
	/* Overloaded constructor to include a given maxSize and location */
	public Asteroid(Point location, int maxSize, boolean finalObject) {
		this.setColor(ColorUtil.GRAY); // gray
		this.setLocation(location.getX(), location.getY());
		this.size = (finalObject) ? maxSize : new Random().nextInt(maxSize - 5) + 6;
		this.indestructible = true;
		this.destroyedByMissile = false;
		UITimer.timer(2000, false, this);
		// graphical object
		this.thisAsteroid = new AsteroidGraphic(size*2);
		thisAsteroid.translate(getLocation().getX(), getLocation().getY());
	}
	
	/* Return size value */
	public int getSize() {
		return size;
	}
	
	/* Set destroyed by Missile */
	public void setDestroyedByMissile() {
		destroyedByMissile = true;
	}
	
	/* Return destroyedByMissile value */
	public boolean destroyedByMissile() {
		return destroyedByMissile;
	}
	
	/* Make object destructible */
	public void run() {
		indestructible = false;
	}
	
	/* Change location based on speed and direction */
	public void move(int elapsedTime) {
		// compute deltaX, deltaY values
		int dX = Math.round((float)
					(Math.cos(Math.toRadians(90-getDirection()))
					*(getSpeed()*20.0)
					*(elapsedTime/1000.0))
				 );
		int dY = Math.round((float)
					(Math.sin(Math.toRadians(90-getDirection()))
					*(getSpeed()*20.0)
					*(elapsedTime/1000.0))
				 );
		thisAsteroid.translate(dX, dY);
		
		// 
		int newX = getLocation().getX()+dX;
		int newY = getLocation().getY()+dY;
		int mapX = Game.getMapWidth()+30;
		int mapY = Game.getMapHeight()+30;
		
		// wrap around if moves past edge of map
		if (newX > mapX) {
			thisAsteroid.translate(-(mapX+30), 0);
			newX -= mapX+30;
		}
		else if (newX < -30) {
			thisAsteroid.translate(mapX+30, 0);
			newX += mapX+30;
		}
		if (newY > mapY) {
			thisAsteroid.translate(0, -(mapY+30));
			newY -= mapY+30;
		}
		else if (newY < -30) {
			thisAsteroid.translate(0, mapY+30);
			newY += mapY+30;
		}
		
		setLocation(newX,newY);
	}
	
	/* Override toString() for outputting object state information */
	public String toString() {
		return ("Asteroid:" + 
				" loc=" + getLocation().getX() + "," + getLocation().getY() +
				" color=[" + ColorUtil.red(getColor()) + "," +
				 			 ColorUtil.green(getColor()) + "," +
				 			 ColorUtil.blue(getColor()) + "]" +
				" speed=" + getSpeed() +
				" dir=" + getDirection() +
				" size=" + size);
	}

	/* Draw this object's graphic */
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(this.getColor());
		thisAsteroid.draw(g, pCmpRelPrnt, pCmpRelScrn, isSelected());
	}

	/* Collision detection */
	public boolean collidesWith(ICollider otherObj) {
		// return if not an object that collides with this one
		if (otherObj instanceof SpaceStation) return false;
		// find distance between centers
		int dx = getLocation().getX()-((GameObject)otherObj).getLocation().getX();
		int dy = getLocation().getY()-((GameObject)otherObj).getLocation().getY();
		int thisR = size*2; // this object's "radius"
		// collision detection, return true if collision occurs
		if (otherObj instanceof Asteroid) {
			// if either this Asteroid or other Asteroid is indestructible, pass through
			if (indestructible || ((Asteroid)otherObj).indestructible)
				return false;
			// find radius
			int otherR = ((Asteroid)otherObj).size*2;
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR))
				return true;
		} // end Asteroid
		else if (otherObj instanceof FlyingSaucer) {
			// find "radius"
			int incomingAngle = 180-(Math.abs(180-Math.abs(270-getDirection())));
			int fsWidth  = ((FlyingSaucer)otherObj).getSize()*6;
			int fsHeight = ((FlyingSaucer)otherObj).getSize();
			int otherR = Math.round((float)((fsWidth*fsHeight) /
				Math.sqrt(
						(fsWidth*fsWidth*
								Math.sin(Math.toRadians(incomingAngle))*
								Math.sin(Math.toRadians(incomingAngle)))
						+
						(fsHeight*fsHeight*
								Math.cos(Math.toRadians(incomingAngle))*
								Math.cos(Math.toRadians(incomingAngle)))
						)));
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR))
				return true;
		} // end FlyingSaucer
		else if (otherObj instanceof Missile) {
			// find "radius"
			int otherR = ((Missile)otherObj).getHeight()/2;
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR)) {
				destroyedByMissile = true;
				return true;
			}
		} // end Missile
		else if (otherObj instanceof Ship) {
			// find "radius"
		} // end Ship
		return false; // else no collision
	}

	/* Collision handling */
	public void handleCollision(ICollider otherObj) {
		this.destroy();
		((GameObject)otherObj).destroy();
	}

	/* Selected detection */
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		if (!Game.isPaused()) return false;
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + getLocation().getX();
		int yLoc = pCmpRelPrnt.getY() + getLocation().getY();
		int selectR = size*2;
		
		if ((px > xLoc-selectR && px < xLoc+selectR)
			&&
			(py > yLoc-selectR && py < yLoc+selectR))
			return true;
		return false;
	}
}
