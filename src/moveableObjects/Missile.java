package moveableObjects;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.Game;
import com.mycompany.a3.GameObject;
import com.mycompany.a3.ICollider;
import gameObjects.MoveableObject;
import objectGraphics.MissileGraphic;

public class Missile extends MoveableObject implements Runnable {
	private MissileGraphic thisMissile;
	private UITimer timer; // fuel level timer
	private int fuelLevel; // number of game ticks remaining before disappearing
	private int width, height;
	
	/* Constructor */
	public Missile(Point location, int direction) {
		this.setColor(ColorUtil.GREEN); // green
		this.setLocation(               // initial location = location of ship
				location.getX(),
				location.getY());
		this.setDirection(direction);   // direction = direction of ship
		this.setSpeed(25);			    // speed of all missiles will be 22
		this.width = 4;                 // width of all missiles will be 4
		this.height = 20;               // height of all missiles will be 20
		this.fuelLevel = 5; 	        // initial fuel level = 5
		this.timer = UITimer.timer(1000, true, this);
		// graphical object
		this.thisMissile = new MissileGraphic(width, height);
		thisMissile.rotate(direction);
		thisMissile.translate(location.getX(), location.getY());
	}
	
	/* Return fuelLevel value */
	public int getFuelLevel() {
		return fuelLevel;
	}
	
	/* Set fuelLevel to max */
	public void refuel() {
		fuelLevel = 5;
	}
	
	/* Return width */
	public int getWidth() {
		return width;
	}
	
	/* Return height */
	public int getHeight() {
		return height;
	}
	
	/* Decrement fuelLevel */
	public void run() {
		if (Game.isPaused()) return;
		if (--fuelLevel == 0) {
			timer.cancel();
			this.destroy();
		}
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
		thisMissile.translate(-dX, -dY);
		
		// 
		int newX = getLocation().getX()-dX;
		int newY = getLocation().getY()-dY;
		int mapX = Game.getMapWidth()+20;
		int mapY = Game.getMapHeight()+20;
		
		// wrap around if moves past edge of map
		if (newX > mapX) {
			thisMissile.translate(-(mapX+30), 0);
			newX -= mapX+30;
		}
		else if (newX < -30) {
			thisMissile.translate(mapX+30, 0);
			newX += mapX+30;
		}
		if (newY > mapY) {
			thisMissile.translate(0, -(mapY+30));
			newY -= mapY+30;
		}
		else if (newY < -30) {
			thisMissile.translate(0, mapY+30);
			newY += mapY+30;
		}
		
		setLocation(newX,newY);
	}
	
	/* Override toString() for outputting object state information */
	public String toString() {
		return ("Missile:" + 
				" loc=" + getLocation().getX() + "," + getLocation().getY() +
				" color=[" + ColorUtil.red(getColor()) + "," +
				 			 ColorUtil.green(getColor()) + "," +
				 			 ColorUtil.blue(getColor()) + "]" +
				" speed=" + getSpeed() +
				" dir=" + getDirection() +
				" fuel=" + fuelLevel);
	}

	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn) {
		g.setColor(this.getColor());
		thisMissile.draw(g, pCmpRelPrnt, pCmpRelScrn, isSelected());
	}

	public boolean collidesWith(ICollider otherObj) {
		// return if not an object that collides with this one
		if (!(otherObj instanceof Asteroid || otherObj instanceof FlyingSaucer))
			return false;
		// find distance between centers
		int dx = getLocation().getX()-((GameObject)otherObj).getLocation().getX();
		int dy = getLocation().getY()-((GameObject)otherObj).getLocation().getY();
		int thisR = height/2; // this object's "radius"
		// collision detection, return true if collision occurs
		if (otherObj instanceof Asteroid) {
			// find "radius"
			int otherR = ((Asteroid)otherObj).getSize()*2;
			// compare
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR)) {
				((Asteroid)otherObj).setDestroyedByMissile();
				return true;
			}
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
			if ((dx*dx + dy*dy) <= (thisR + otherR)*(thisR + otherR)) {
				((FlyingSaucer)otherObj).setDestroyedByMissile();
				return true;
			}
		} // end FlyingSaucer
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
		int selectR = height/2;
		
		if ((px > xLoc-selectR && px < xLoc+selectR)
			&&
			(py > yLoc-selectR && py < yLoc+selectR))
			return true;
		return false;
	}
}
