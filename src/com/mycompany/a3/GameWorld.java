package com.mycompany.a3;

import java.util.Observable;
import java.util.Random;

import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.util.UITimer;
import fixedObjects.*;
import gameObjects.IMoveable;
import moveableObjects.*;

public class GameWorld extends Observable implements IGameWorld, Runnable {
	private int score, lives, clock;
	private boolean sound;
	private GameObjectCollection gameObjectCollection;
	private UITimer animationTimer;
	private BGSound bgMusicGame;
	private Sound missileFire, missileExplosion, gameOverSound;
	
	/* Constructor */
	public GameWorld() {
		score = 0; lives = 3; clock = 0; sound = true;
		gameObjectCollection = new GameObjectCollection();
		missileFire      = new Sound("Missile.wav");
		missileExplosion = new Sound("Explosion.wav");
		gameOverSound    = new Sound("GameOver.wav");
		bgMusicGame      = new BGSound("Starlight.wav");
		missileFire.changeVolume(4);
		missileExplosion.changeVolume(6);
		gameOverSound.changeVolume(2);
		bgMusicGame.changeVolume(10);
		bgMusicGame.play();
	}
	
	public void init() {
		createShip();
		createSpaceStation();
		animationTimer = UITimer.timer(20, true, this);
	}
	
	/* additional methods here to	*/
	/* manipulate world objects and	*/
	/* related game data			*/
	
	/* Notify observers of change */
	public void observableUpdate() {
		this.setChanged();
		this.notifyObservers(new GameWorldProxy(this));
	}
	
	/* Randomly spawn asteroid or flying saucer */
	public void spawnEnemy() {
		if (clock%3 == 0) { // every 3 seconds
			if (new Random().nextInt(10) == 0)
				createFlyingSaucer();
			else createAsteroid();
		}
	}
	
	/* Add a new asteroid */
	public void createAsteroid() {
		gameObjectCollection.add(new Asteroid());
		observableUpdate();
	}
	
	/* Add a flying saucer */
	public void createFlyingSaucer() {
		gameObjectCollection.add(new FlyingSaucer());
		observableUpdate();
	}
	
	/* Add a new blinking space station */
	public String createSpaceStation() {
		// check if space station already exists in world
		if (gameObjectCollection.contains(SpaceStation.getStation()))
			return "There is already a Space Station!";
		gameObjectCollection.add(SpaceStation.getStation());
		observableUpdate();
		return null;
	}
	
	/* Find current existing ship.   */
	/* Return Ship object if exists. */
	public Ship findShip() {
		IIterator iterator = gameObjectCollection.getIterator();
		while (iterator.hasNext()) {
			GameObject currentObject = iterator.getNext();
			if (currentObject instanceof Ship)
				return (Ship)currentObject;
		}
		return null;
	}
	
	/* Add a new ship */
	public String createShip() {
		// return error if ship already exists in world
		if (findShip() != null) return "There is already a ship!";
		// else add new ship to collection
		gameObjectCollection.add(new Ship());
		observableUpdate();
		return null;
	}
	
	/* Increase speed of the ship by a small amount */
	public String increaseShipSpeed() {
		Ship myShip = findShip();
		// return error if ship does not exist in world
		if (myShip == null) return "A ship does not exist in this world.";
		// return error if ship already at max speed
		if (myShip.getSpeed() >= 15) return "Ship already at maximum speed.";
		// else increase ship speed
		myShip.changeSpeed(1); return null;	
	}
	
	/* Decrease speed of the ship by a small amount */
	public String decreaseShipSpeed() {
		Ship myShip = findShip();
		// return error if ship does not exist in world
		if (myShip == null) return "A ship does not exist in this world.";
		// return error if ship already at 0 speed
		if (myShip.getSpeed() <= 0) return "Ship is currently stationary.";
		// else decrease ship speed
		myShip.changeSpeed(-1); return null;
	}
	
	/* Turn left by a small amount */
	public String turnShipLeft() {
		Ship myShip = findShip();
		// return error if ship does not exist in world
		if (myShip == null) return "A ship does not exist in this world.";
		// else change ship direction counter-clockwise
		if (myShip.getDirection() <= 0)
			myShip.setDirection(359);
		else
			myShip.changeDirection(-3);
		return null;
	}
	
	/* Turn right by a small amount */
	public String turnShipRight() {
		Ship myShip = findShip();
		// return error if ship does not exist in world
		if (myShip == null) return "A ship does not exist in this world.";
		// else change ship direction clockwise
		if (myShip.getDirection() == 359)
			myShip.setDirection(0);
		else
			myShip.changeDirection(3);
		return null;
	}
	
	/* Fire a missile out the front of the ship */
	public String fireMissile() {
		Ship myShip = findShip();
		// return error if ship does not exist in world
		if (myShip == null) return "A ship does not exist in this world.";
		// return error if ship has 0 missiles
		if (!myShip.fire()) return "Out of missiles";
		// else reduce missile count and add a missile to collection
		if (sound) missileFire.play();
		gameObjectCollection.add(
				new Missile(myShip.getLocation(),
						    myShip.getDirection()));
		observableUpdate();
		return null;
	}
	
	/* Refuel selected missiles */
	public void refuelSelectedMissiles() {
		IIterator iterator = gameObjectCollection.getIterator();
		while (iterator.hasNext()) {
			ISelectable currentObject = (ISelectable)iterator.getNext();
			if (currentObject.isSelected()) {
				if (currentObject instanceof Missile)
					((Missile)currentObject).refuel();
			}
		}
	}
	
	/* Jump through hyper-space */
	public String hyperspace() {
		Ship myShip = findShip();
		// return error if ship does not exist in world
		if (myShip == null) return "A ship does not exist in this world.";
		// else reset ship location, speed, and direction
		myShip.reset();
		observableUpdate();
		return null;
	}
	
	/* Load a new supply of (maximum) missiles into the ship */
	public String reloadMissiles() {
		Ship myShip = findShip();
		// return error if ship does not exist in world
		if (myShip == null) return "A ship does not exist in this world.";
		// return error if ship already has max number of missiles
		if (myShip.getMissileCount() >= 12) return "Already at maximum number of missiles!";
		// else reset missile count to max
		myShip.setMissileCount(12);
		observableUpdate();
		return null;
	}
	
	/* An asteroid hit by a missile is split into smaller pieces */
	/* with the sum of the sizes = size of destroyed Asteroid.   */
	/* Recursively divides Asteroid into "numberOfSplits" pieces */
	private void splitAsteroid(Asteroid oldAsteroid, int sizeLimit, int numberOfSplits) {
		if (numberOfSplits == 0)
			return;
		else {
			Asteroid newAsteroid = new Asteroid(
					oldAsteroid.getLocation(),
					sizeLimit - (numberOfSplits-1)*6,
					(numberOfSplits > 1) ? false : true);
			gameObjectCollection.add(newAsteroid);
			splitAsteroid(newAsteroid, sizeLimit-newAsteroid.getSize(), --numberOfSplits);
		}
	}
	
	/* Increment the in-game clock */
	public void gameClockTick() {
		clock++;
		spawnEnemy();
		observableUpdate();
	}

	/* Update the Game World */
	public void run() {				
		GameObjectCollection tempCollection = new GameObjectCollection();
		IIterator iterator = gameObjectCollection.getIterator();
		
		// iterate through objects and move each movable object
		while (iterator.hasNext()) {
			GameObject currentObject = iterator.getNext();
			tempCollection.add(currentObject);
			if (currentObject instanceof IMoveable)
				((IMoveable) currentObject).move(20);
		}
		
		// iterate through objects and check for collisions
		iterator = tempCollection.getIterator();
		while (iterator.hasNext()) {
			ICollider curObj = iterator.getNext();
			// move on to next object if current is already destroyed
			if (((GameObject)curObj).isDestroyed()) continue;
			IIterator iter2 = tempCollection.getIterator();
			while (iter2.hasNext()) {
				ICollider otherObj = iter2.getNext();
				// move on to next object if current is already destroyed
				if (((GameObject)otherObj).isDestroyed()) continue;
				if (curObj != otherObj) // if not same object
					if (curObj.collidesWith(otherObj))
						curObj.handleCollision(otherObj);
			}
		}
		
		// iterate through objects and remove destroyed objects
		iterator = tempCollection.getIterator();
		while (iterator.hasNext()) {
			GameObject obj = iterator.getNext();
			if (obj.isDestroyed()) {
				if (obj instanceof Asteroid) {
					int aSize = ((Asteroid) obj).getSize();
					if (aSize <= 18 && ((Asteroid)obj).destroyedByMissile()) {
						if (sound) missileExplosion.play();
						score += 10;
					}
					else if (aSize <= 24 && ((Asteroid)obj).destroyedByMissile()) {
						if (sound) missileExplosion.play();
						splitAsteroid((Asteroid)obj, aSize, 2);
						score += 15;
					}
					else if (aSize >= 25 && ((Asteroid)obj).destroyedByMissile()) {
						if (sound) missileExplosion.play();
						splitAsteroid((Asteroid)obj, aSize, 3);
						score += 25;
					}
				}
				else if (obj instanceof FlyingSaucer && ((FlyingSaucer) obj).destroyedByMissile()) {
					if (sound) missileExplosion.play();
					score += 100;
				}
				else if (obj instanceof Ship) {
					lives--;
				}
				gameObjectCollection.remove(obj);
				if (lives > 0) createShip();
			}
		}
		
		observableUpdate();
		if (lives == 0) {
			if (sound) gameOverSound.play();
			gameOver();
		}
	}
	
	/* Game over */
	public void gameOver() {
		animationTimer.cancel();
		boolean continueGame = Dialog.show(
				"GAME OVER - No lives remaining",
				"Final Score = " + score,
				"Play Again",
				"Main Menu");
		if (continueGame) reset();
		else {
			bgMusicGame.pause();
			MainForm.openMainMenu();
		}
	}
	
	public void reset() {
		score = 0; lives = 3; clock = 0;
		gameObjectCollection.clear();
		init();
	}
	
	/* Halt the GameWorld */
	public void pause() {
		animationTimer.cancel();
	}
	
	/* Resume GameWorld */
	public void resume() {
		IIterator iterator = gameObjectCollection.getIterator();
		while (iterator.hasNext())
			((ISelectable)iterator.getNext()).setSelected(false);
		animationTimer = UITimer.timer(20, true, this);
	}
	
	/* Toggle sound state */
	public void toggleSound() {
		sound = !sound;
		if (sound) bgMusicGame.play();
		else bgMusicGame.pause();
		observableUpdate();
	}
	
	/* Return player score */
	public int getScore() {
		return score;
	}
	
	/* Return player lives */
	public int getLives() {
		return lives;
	}
	
	/* Find Ship object and return missile count */
	public int getMissiles() {
		Ship myShip = findShip();
		return (myShip == null) ? 0 : myShip.getMissileCount();
	}
	
	/* Return boolean value sound */
	public boolean isSoundON() {
		return sound;
	}
	
	/* Return clock (time) value */
	public int getClock() {
		return clock;
	}

	/* Return collection of game objects */
	public GameObjectCollection getGameObjects() {
		return gameObjectCollection;
	}
}
