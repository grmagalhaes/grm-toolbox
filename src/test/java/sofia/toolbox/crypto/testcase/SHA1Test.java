package sofia.toolbox.crypto.testcase;

import java.io.File;

import sofia.toolbox.crypto.SHA1;
import sofia.toolbox.io.FileSystem;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class SHA1Test extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;

	public SHA1Test(String name) {
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

		assertEquals("DC81691D3F747A7E7D4CBE0A008C6345A5438415", new SHA1().getHashCode("Sofia"));
		assertEquals("DC81691D3F747A7E7D4CBE0A008C6345A5438415", new SHA1("Sofia").toString());
		assertEquals("DC81691D3F747A7E7D4CBE0A008C6345A5438415", new SHA1("Sofia").getHashCode());

	};
	
	public void testGetFileHashCode() {

		System.out.println("method: " + this.getName());
		assertEquals("5BD19DE8B4E9AA461DAE0481BC3E56D741E54A52", new SHA1().getFileHashCode(currentDir + "testfile.jpg"));

	};
	

}
