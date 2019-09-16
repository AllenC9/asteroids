package moveableObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.ICollider;
import fixedObjects.SpaceStation;
import gameObjects.MoveableObject;
import objectGraphics.ShipGraphic;

public class Ship extends MoveableObject implements ISteerable {
	private ShipGraphic thisShip; // graphical ship object
	private int missileCount;   // number of missiles remaining
	private int base, height;
	
	
	/* constructor */
	public Ship() {
		this.setColor(ColorUtil.WHITE); // green
		this.setSpeed(0);		        // initial speed: 0
		this.setDirection(0);	        // initial direction: north
		this.setLocation(
			Game.getMapWidth() / 2,     // center of map X coordinate
			Game.getMapHeight() / 2);   // center of map Y coordinate
		this.missileCount = 12;	        // initial missiles: 12 (max)
		this.base = 18;
		this.height = 36;
		// graphical object
		this.thisShip = new ShipGraphic(base,height);
		thisShip.translate(getLocation().getX(), getLocation().getY());
	}
	
	/* Increase or decrease direction by value c */
	public void changeDirection(int c) {
		setDirection(getDirection() + c);
		thisShip.rotate(c);
	}
	
	/* Increase or decrease speed by value c */
	public void changeSpeed(int c) {
		setSpeed(getSpeed() + c);
	}
	
	/* Set missileCount to given value c */
	public void setMissileCount(int c) {
		missileCount = c;
	}
	
	/* Return missileCount value */
	public int getMissileCount() {
		return missileCount;
	}
	
	/* Fire missile and decrement missile count */
	public boolean fire() {
		if (missileCount <= 0) return false;
		missileCount--;
		return true;
	}
	
	/* Reset location, speed, and direction */
	public void reset() {
		this.setSpeed(0);
		thisShip.rotate(-getDirection());
		this.setDirection(0);
		int centerX = Game.getMapWidth()/2;
		int centerY = Game.getMapHeight()/2;
		thisShip.translate(
			centerX-getLocation().getX(),
			centerY-getLocation().getY());
		this.setLocation(centerX, centerY);	
	}
	
	/* Return base value */
	public int getBase() {
		return base;
	}
	
	/* Return height value */
	public int getHeight() {
		return height;
	}
	
	/* Change location based on speed and direction */
	public void move(int elapsedTime) {
		// compute deltaX, deltaY values
		int dX = Math.round((float)
					(Math.cos(Math.toRadians(90+getDirection()))
					*(getSpeed()*20.0)
					*(elapsedTime/1000.0))
				 );
		int dY = Math.round((float)
					(Math.sin(Math.toRadians(90+getDirection()))
					*(getSpeed()*20.0)
					*(elapsedTime/1000.0))
				 );
		thisShip.translate(-dX, -dY);
		
		// 
		int newX = getLocation().getX()-dX;
		int newY = getLocation().getY()-dY;
		int mapX = Game.getMapWidth()+20;
		int mapY = Game.getMapHeight()+20;
		
		// wrap around if moves past edge of map
		if (newX > mapX) {
			thisShip.translate(-(mapX+30), 0);
			newX -= mapX+30;
		}
		else if (newX < -30) {
			thisShip.translate(mapX+30, 0);
			newX += mapX+30;
		}
		if (newY > mapY) {
			thisShip.translate(0, -(mapY+30));
			newY -= mapY+30;
		}
		else if (newY < -30) {
			thisShip.translate(0, mapY+30);
			newY += mapY+30;
		}
		
		setLocation(newX,newY);
	}

	/* Override toString() for outputting object state information */
	public String toString() {
		return ("Ship:" + 
				" loc=" + getLocation().getX() + "," + getLocation().getY() +
				" color=[" + ColorUtil.red(getColor()) + "," +
				 			 ColorUtil.green(getColor()) + "," +
				 			 ColorUtil.blue(getColor()) + "]" +
				" speed=" + getSpeed() +
				" dir=" + getDirection() +
				" missiles=" + missileCount);
	}

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {		
		g.setColor(this.getColor());
		thisShip.draw(g, pCmpRelPrnt, pCmpRelScrn, isSelected());
	}

	public boolean collidesWith(ICollider otherObj) {
		// return if not an object that collides with this one
		if (otherObj instanceof Missile) return false;
		// find distance between centers
		int dx = getLocation().getX()-((GameObject)otherObj).getLocation().getX();
		int dy = getLocation().getY()-((GameObject)otherObj).getLocation().getY();
		int thisR /* Temporary until later optimized */ = height/2;
		// collision detection, return true if collision occurs
		if (otherObj instanceof SpaceStation ) {
			/* Temporary until later optimized */
			int otherR = ((SpaceStation)otherObj).getWidth()/2;
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR))
				return true;
		}
		else {
			/* Temporary until later optimized */
			/*
			int incomingDir = ((MoveableObject)otherObj).getDirection();
			int angle = 180-(Math.abs(180-Math.abs(270-incomingDir)));
			
			if (incomingDir > 90 && incomingDir < 270) {
				int relAngle = 
				if (incomingDir > 180) angle = 180-angle;
				Math.a
		
			}
			else {
				
			}*/
			
			if (otherObj instanceof Asteroid) {
				// find "radius"
				int otherR = ((Asteroid)otherObj).getSize()*2;
				// compare
				if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR))
					return true;
			}
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
			}
		}
		return false; // else no collision
	}

	public void handleCollision(ICollider otherObj) {
		if (otherObj instanceof SpaceStation) {
			missileCount = 12;
			return;
		}
		
		this.destroy();
		((GameObject)otherObj).destroy();
	}

	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		if (!Game.isPaused()) return false;
		int px = pPtrRelPrnt.getX();
		int py = pPtrRelPrnt.getY();
		int xLoc = pCmpRelPrnt.getX() + getLocation().getX();
		int yLoc = pCmpRelPrnt.getY() + getLocation().getY();
		int selectR = height/2;
		
		if ((px > xLoc-selectR && px < xLoc+selectR)
			&&
			(py > yLoc-selectR && py < yLoc+selectR))
			return true;
		return false;
	}
}
