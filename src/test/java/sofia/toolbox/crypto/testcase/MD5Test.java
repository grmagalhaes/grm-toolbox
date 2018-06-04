package sofia.toolbox.crypto.testcase;

import java.io.File;

import sofia.toolbox.crypto.MD5;
import sofia.toolbox.io.FileSystem;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class MD5Test extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	public MD5Test(String name) {
		super(name);
	}

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}	

	public void testGetHashCode() {

		System.out.println("method: " + this.getName());

		assertEquals("8A9D9A205EB4F6E692C982AA03BA01F5", new MD5().getHashCode("gw toolbox"));
		assertEquals("8A9D9A205EB4F6E692C982AA03BA01F5", new MD5("gw toolbox").toString());
		assertEquals("8A9D9A205EB4F6E692C982AA03BA01F5", new MD5("gw toolbox").getHashCode());

	};

	public void testGetFileHashCode() {
		
		System.out.println("method: " + this.getName());		

		assertEquals("F3254B9E7ECD97E0F8422E7A5A2B483D", new MD5().getFileHashCode(currentDir + "testfile.jpg"));

	};
	

}
