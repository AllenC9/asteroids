package moveableObjects;

public interface ISteerable {
	abstract void changeDirection(int c); // increase or decrease direction by value 'c'
	abstract void changeSpeed(int c);	  // increase or decrease speed by value 'c'
}
