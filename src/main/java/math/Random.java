package sofia.toolbox.math;

/**
 * This class contains methods to work with randomic numbers.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Random {
	
	java.util.Random random;	
	
	public Random () {
		random = new java.util.Random();
	}
	
	public int nextInt(int value) {
		return random.nextInt(value);
	}
	
}
