package com.mycompany.a3;

public interface IGameWorld {
	/* specifications here for all GameWorld methods */
	
	abstract int getScore();      // return score of GameWorld	
	abstract int getLives();      // return player lives remaining
	abstract int getMissiles();   // return missiles remaining
	abstract boolean isSoundON(); // return sound value of GameWorld
	abstract int getClock();      // return clock time of GameWorld
	// return game objects collection of GameWorld
	abstract GameObjectCollection getGameObjects();
}
