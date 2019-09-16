package moveableObjects;

import java.util.Random;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.ICollider;
import fixedObjects.SpaceStation;
import gameObjects.MoveableObject;
import objectGraphics.FlyingSaucerGraphic;

public class FlyingSaucer extends MoveableObject {
	private FlyingSaucerGraphic thisSaucer;
	private int size; // determines dimensions of object
	private boolean destroyedByMissile;
	
	/* Constructor */
	public FlyingSaucer() {
		Random r = new Random();
		setColor(ColorUtil.rgb(255,0,0));  // red
		this.size = 10*(r.nextInt(2) + 1); // randomly select 10 or 20
		this.destroyedByMissile = false;
		// graphical object
		this.thisSaucer = new FlyingSaucerGraphic(size);
		thisSaucer.translate(getLocation().getX(), getLocation().getY());
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
		thisSaucer.translate(dX, dY);
		
		// 
		int newX = getLocation().getX()+dX;
		int newY = getLocation().getY()+dY;
		int mapX = Game.getMapWidth()+60;
		int mapY = Game.getMapHeight()+60;
		
		// wrap around if moves past edge of map
		if (newX > mapX) {
			thisSaucer.translate(-(mapX+30), 0);
			newX -= mapX+30;
		}
		else if (newX < -30) {
			thisSaucer.translate(mapX+30, 0);
			newX += mapX+30;
		}
		if (newY > mapY) {
			thisSaucer.translate(0, -(mapY+30));
			newY -= mapY+30;
		}
		else if (newY < -30) {
			thisSaucer.translate(0, mapY+30);
			newY += mapY+30;
		}
		
		setLocation(newX,newY);
		
		/* Commented out for purposes of grading */
		/* Will comment back in for presentation */
		/*
		// remove from world if moves past edge of map
		if (newX > mapX || newX < -60 ||
			newY > mapY || newY < -60) this.destroy();*/
	}
	
	/* Override toString() for outputting object state information */
	public String toString() {
		return ("Flying Saucer:" + 
				" loc=" + getLocation().getX() + "," + getLocation().getY() +
				" color=[" + ColorUtil.red(getColor()) + "," +
				 			 ColorUtil.green(getColor()) + "," +
				 			 ColorUtil.blue(getColor()) + "]" +
				" speed=" + getSpeed() +
				" dir=" + getDirection() +
				" size=" + size);
	}

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(this.getColor());
		thisSaucer.draw(g, pCmpRelPrnt, pCmpRelScrn, isSelected());
	}

	public boolean collidesWith(ICollider otherObj) {
		// return if not an object that collides with this one
		if (otherObj instanceof FlyingSaucer || otherObj instanceof SpaceStation)
			return false;
		// find distance between centers
		int dx = getLocation().getX()-((GameObject)otherObj).getLocation().getX();
		int dy = getLocation().getY()-((GameObject)otherObj).getLocation().getY();
		// find this object's "radius"
		int incomingAngle =
			180-(Math.abs(180-Math.abs(270-((MoveableObject)otherObj).getDirection())));
		int thisR = Math.round((float)((size*size*6) /
			Math.sqrt(
					((size*6)*(size*6)*Math.sin(Math.toRadians(incomingAngle))
							          *Math.sin(Math.toRadians(incomingAngle)))
					+
					(size*size*Math.cos(Math.toRadians(incomingAngle))
							  *Math.cos(Math.toRadians(incomingAngle)))
					)));
		// collision detection, return true if collision occurs
		if (otherObj instanceof Asteroid) {
			// find "radius"
			int otherR = ((Asteroid)otherObj).getSize()*2;
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR))
				return true;
		} // end Asteroid
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
			/* Temporary until later optimized */
			// find "radius"
			int otherR = ((Ship)otherObj).getHeight()/2;
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR))
				return true;
		} // end Ship
		return false; // else no collision
	}

	public void handleCollision(ICollider otherObj) {
		this.destroy();
		((GameObject)otherObj).destroy();
	}

	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		if (!Game.isPaused()) return false;
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + getLocation().getX();
		int yLoc = pCmpRelPrnt.getY() + getLocation().getY();
		int selectRx = size*6;
		int selectRy = size;
		
		if ((px > xLoc-selectRx && px < xLoc+selectRx)
			&&
			(py > yLoc-selectRy && py < yLoc+selectRy))
			return true;
		return false;
	}
}
