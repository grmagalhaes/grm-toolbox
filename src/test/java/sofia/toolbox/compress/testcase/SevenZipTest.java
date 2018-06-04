package sofia.toolbox.compress.testcase;

import java.io.File;

import sofia.toolbox.compress.SevenZip;
import sofia.toolbox.io.FileSystem;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class SevenZipTest extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	public SevenZipTest(String name) {
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

		File file1 = new File(currentDir + "testfile1.jpg");
		if (file1.exists()) file1.delete();
		
		File file2 = new File(currentDir + "testfile2.jpg");
		if (file2.exists()) file2.delete();
		
		File file3 = new File(currentDir + "testfile3.jpg");
		if (file3.exists()) file3.delete();

		SevenZip szip = new SevenZip();
		szip.extract(currentDir + "testfile.7z", currentDir);
		if (!file1.exists()) fail("File not extracted");
		if (!file2.exists()) fail("File not extracted");
		if (!file3.exists()) fail("File not extracted");
		
		file1.delete();
		file2.delete();
		file3.delete();
	}

	public void testCreate() {

		System.out.println("method: " + this.getName());

		String[] files = new String[1];
		files[0] = currentDir + "testfile.jpg";

		File file = new File(currentDir + "testfile2.7z");
		file.delete();

		SevenZip szip = new SevenZip();

		szip.create(currentDir + "testfile2.7z", files);
		if (!file.exists()) fail("Zip file not created");

		file.delete();
	}	 


}
