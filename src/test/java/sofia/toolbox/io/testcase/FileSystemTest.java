package sofia.toolbox.io.testcase;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

import sofia.toolbox.io.FileSystem;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class FileSystemTest extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	protected void setUp() {

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));

	}
	
	public void testIsValidFilename() {
		
		System.out.println("method: " + this.getName());		
		
		assertFalse(FileSystem.isValidFilename("/"));
		assertFalse(FileSystem.isValidFilename("\\"));
		assertFalse(FileSystem.isValidFilename("|"));
		assertFalse(FileSystem.isValidFilename("*"));
		assertFalse(FileSystem.isValidFilename("?"));
		assertFalse(FileSystem.isValidFilename(">"));
		assertFalse(FileSystem.isValidFilename("<"));
		assertFalse(FileSystem.isValidFilename(":"));
		assertFalse(FileSystem.isValidFilename("\""));
		assertFalse(FileSystem.isValidFilename("|||*"));
		
		assertTrue(FileSystem.isValidFilename("abcdefghijklmnopqrstuvwxtzABCDEFGHIJKLMNOPQRSTUVWXYZ$#@!%_"));
		
		
	}
	
	public void testWriteText() throws IOException {
		
		System.out.println("method: " + this.getName());
		
		File file = new File(currentDir + "teste.txt");
		if (file.exists()) file.delete();
		
		FileSystem fileSystem = new FileSystem();
		RandomAccessFile raf = fileSystem.openFile(file.getPath(), "rw");
		FileChannel fc = fileSystem.openChannel(raf);
        
        StringBuffer text = new StringBuffer();
        text.append("test1\n");
        text.append("test2");
        
        fileSystem.writeText(fc, text.toString());
        
        fileSystem.closeChannel(fc);
        raf.close();
        
 		if (!file.exists()) {
			fail("File not found");
		}
	}

	public void testReadText() throws IOException {
		
		testWriteText();
		
		File file = new File(currentDir + "teste.txt");

		FileSystem fileSystem = new FileSystem();

        RandomAccessFile raf = fileSystem.openFile(file.getPath(), "r");
        FileChannel fc = fileSystem.openChannel(raf);
        String result = fileSystem.readText(fc);
        fileSystem.closeChannel(fc);
        raf.close();
        assertTrue("Error to read the file", result.contains("test1\ntest2"));
        
		if (file.exists()) {
			file.delete();
		}        
 
	}


}
