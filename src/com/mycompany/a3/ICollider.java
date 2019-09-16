package com.mycompany.a3;

public interface ICollider {
	abstract boolean collidesWith(ICollider otherObject);
	abstract void handleCollision(ICollider otherObject);
}
