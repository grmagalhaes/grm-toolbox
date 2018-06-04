package sofia.toolbox.compress.testcase;

import java.io.File;

import sofia.toolbox.compress.Zip;
import sofia.toolbox.io.FileSystem;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class ZipTest extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	public ZipTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}

	public void testExtract() {

		System.out.println("method: " + this.getName());

		File file = new File(currentDir + "testfile2.jpg");
		if (file.exists()) file.delete();

		Zip zip = new Zip();
		zip.extract(currentDir + "testfile.zip", currentDir);
		if (!file.exists()) fail("File not extracted");
		
		file.delete();
	}

	public void testCreate() {

		System.out.println("method: " + this.getName());

		String[] files = new String[1];
		files[0] = "testfile.jpg";

		File file = new File("\\temp\\testfile.zip");
		file.delete();

		Zip zip = new Zip();

		zip.create("\\temp\\testfile.zip", files);
		if (!file.exists()) fail("Zip file not created");

		file.delete();
	}	 


}
