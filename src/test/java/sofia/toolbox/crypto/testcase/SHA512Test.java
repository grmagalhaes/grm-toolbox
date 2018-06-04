package sofia.toolbox.crypto.testcase;

import java.io.File;

import sofia.toolbox.crypto.SHA512;
import sofia.toolbox.io.FileSystem;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class SHA512Test extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	public SHA512Test(String name) {
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

		assertEquals("F16D024E2AC11FF8CEA5EC630D6BF63A0C96695A27295D916C4B2F74B25746145920F7E82BDEE4F4131F8F7F3CC7927AD27CFECEB3629F1EE7E7D010A90E2079", new SHA512().getHashCode("Sofia"));
		assertEquals("F16D024E2AC11FF8CEA5EC630D6BF63A0C96695A27295D916C4B2F74B25746145920F7E82BDEE4F4131F8F7F3CC7927AD27CFECEB3629F1EE7E7D010A90E2079", new SHA512("Sofia").toString());
		assertEquals("F16D024E2AC11FF8CEA5EC630D6BF63A0C96695A27295D916C4B2F74B25746145920F7E82BDEE4F4131F8F7F3CC7927AD27CFECEB3629F1EE7E7D010A90E2079", new SHA512("Sofia").getHashCode());
		
	};
	
	public void testGetFileHashCode() {

		System.out.println("method: " + this.getName());
		assertEquals("0C47EB8AE7494B78C10D64062FBFEF20483DE0ACCD63DA60D538BD61464927964EF09DD18C90A31918CFEEE3D4735AF5D7BDAE8F1EBAB9B84FAA4E47EDCD10B7", new SHA512().getFileHashCode(currentDir + "testfile.jpg"));

	};
	

}
