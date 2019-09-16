package com.mycompany.a3;

public interface ICollection {
	abstract void add(GameObject go);                   // add object to collection
	abstract void addAll(GameObject...gameObjects);     // add multiple objects
	abstract void remove(GameObject go);                // remove object from collection
	abstract void removeAll(GameObject... gameObjects); // remove multiple objects
	abstract IIterator getIterator();                   // return collection iterator
}
