package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection {
	private ArrayList<GameObject> theCollection;
	
	/* Constructor */
	public GameObjectCollection() {
		theCollection = new ArrayList<GameObject>();
	}
	
	/* Add an object to the collection */
	public void add(GameObject go) {
		theCollection.add(go);
	}
	
	/* Add multiple objects to the collection */
	public void addAll(GameObject... gameObjects) {
		for (GameObject g : gameObjects)
			theCollection.add(g);
	}
	
	/* Remove an object from the collection */
	public void remove(GameObject go) {
		theCollection.remove(go);
	}
	
	/* Remove multiple objects */
	public void removeAll(GameObject... gameObjects) {
		for (GameObject g : gameObjects)
			theCollection.remove(g);
	}
	
	/* Clear all objects */
	public void clear() {
		theCollection.clear();
	}
	
	/* Check if collection contains an object */
	public boolean contains(GameObject go) {
		return theCollection.contains(go);
	}
	
	/* Return an iterator for the collection */
	public IIterator getIterator() {
		return new GameObjectIterator();
	}
	
	/* Iterator Class */
	private class GameObjectIterator implements IIterator {
		private int index;
		
		/* Constructor */
		public GameObjectIterator() {
			index = -1;
		}
		
		/* Check if collection has a next object */
		public boolean hasNext() {
			if (theCollection.size() <= 0) return false;
			if (index == theCollection.size() - 1) return false;
			return true;
		}
		
		/* Retrieve next object */
		public GameObject getNext() {
			return theCollection.get(++index);
		}
	}
}
