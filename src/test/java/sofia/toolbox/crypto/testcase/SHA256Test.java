package sofia.toolbox.crypto.testcase;

import java.io.File;

import sofia.toolbox.crypto.SHA256;
import sofia.toolbox.io.FileSystem;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class SHA256Test extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;

	public SHA256Test(String name) {
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

		assertEquals("BB866952A8675F537773E9854CC9C5B3BE33B22BEE2E11DBB71FD07F3317C335", new SHA256().getHashCode("Sofia"));
		assertEquals("BB866952A8675F537773E9854CC9C5B3BE33B22BEE2E11DBB71FD07F3317C335", new SHA256("Sofia").toString());
		assertEquals("BB866952A8675F537773E9854CC9C5B3BE33B22BEE2E11DBB71FD07F3317C335", new SHA256("Sofia").getHashCode());

	};
	
	public void testGetFileHashCode() {

		System.out.println("method: " + this.getName());
		assertEquals("5CF181DAE8620B20CF6D923BFC3F62712601F8DCFD2FC7C1E55E7DED9E70583A", new SHA256().getFileHashCode(currentDir + "testfile.jpg"));

	};
	

}
