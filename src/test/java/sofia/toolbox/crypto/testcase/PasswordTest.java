package sofia.toolbox.crypto.testcase;

import sofia.toolbox.crypto.Password;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class PasswordTest extends TestCase {

	public PasswordTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}

	 public void testGenerate() {

		System.out.println("method: " + this.getName());
		
		String oldPassword = new Password().generate();
		String newPassword = new Password().generate();

		assertEquals(8, oldPassword.length());
		assertEquals(8, newPassword.length());

		assertTrue(!oldPassword.equals(newPassword));
		
	}
	 
	 public void testValidate() {

		System.out.println("method: " + this.getName());
		
		Password password = new Password();
		assertEquals(false, password.validate("John", null, null));
		assertEquals(false, password.validate(null, "sjhsjdh", "aBcdEfhi"));
		assertEquals(false, password.validate("John", "sjhsjdh", "art"));
		assertEquals(false, password.validate("John", "sjhsjdh", "John1234"));
		assertEquals(true, password.validate("John", "sjhsjdh", "abcdeF67"));
		assertEquals(false, password.validate("John", "sjhsjdh", "abcdefghtjffhd7"));
		assertEquals(false, password.validate("John", "sjhsjdh", "ABCFGEDGHSGDD5467"));
		assertEquals(false, password.validate("John", "sjhsjdh", "12345686589382"));
		assertEquals(false, password.validate("John", "abcdeF67", "abcdeF67"));	
		assertEquals(false, password.validate("John", "ABCDEF67", "abcdeF67"));		
		assertEquals(true, password.validate("John", "ABCFEF67", "Abcdes67"));	
	}	 

	 
}
