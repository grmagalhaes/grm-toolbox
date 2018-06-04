package sofia.toolbox.math.testcase;

import sofia.toolbox.math.Random;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class RandomTest extends TestCase {

	public RandomTest(String name) {
		super(name);
	}

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}	

	public void testNextInt() {

		final int SAMPLE_NUMBER = 100;

		System.out.println("method: " + this.getName());

		Random random = new Random();
		int beforeValue = -1;
		int afterValue = -1;

		for (int i = 0; i <= SAMPLE_NUMBER - 1; i++) {
			afterValue = random.nextInt(1000000);
			assertFalse("Values must be different", beforeValue == afterValue);
			beforeValue = afterValue;
		}
	}

}
