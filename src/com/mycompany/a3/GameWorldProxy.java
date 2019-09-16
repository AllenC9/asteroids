package com.mycompany.a3;

import java.util.Observable;

public class GameWorldProxy extends Observable implements IGameWorld {
	/* code here to accept and hold a GameWorld, provide implementations */
	/* of all the public methods in a GameWorld, forward allowed         */
	/* calls to the actual GameWorld, and reject calls to methods        */
	/* which the outside should not be able to access in the GameWorld   */
	
	private GameWorld gameWorld;
	
	/* Constructor */
	public GameWorldProxy(GameWorld gw) {
		gameWorld = gw;
	}

	/* Retrieve GameWorld score */
	public int getScore() {
		return gameWorld.getScore();
	}

	/* Retrieve GameWorld lives */
	public int getLives() {
		return gameWorld.getLives();
	}

	/* Retrieve GameWorld active ship missile count */
	public int getMissiles() {
		return gameWorld.getMissiles();
	}

	/* Retrieve GameWorld sound value */
	public boolean isSoundON() {
		return gameWorld.isSoundON();
	}

	/* Retrieve GameWorld clock time */
	public int getClock() {
		return gameWorld.getClock();
	}
	
	/* Retrieve GameWorld collection of game objects */
	public GameObjectCollection getGameObjects() {
		return gameWorld.getGameObjects();
	}
}
