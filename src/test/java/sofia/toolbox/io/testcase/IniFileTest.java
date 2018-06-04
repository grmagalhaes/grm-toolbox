package sofia.toolbox.io.testcase;

import java.io.File;

import sofia.toolbox.io.FileSystem;
import sofia.toolbox.io.IniFile;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class IniFileTest extends TestCase {
	
	String currentDir = new FileSystem().getCurrentDirectory() + File.separator + "src" + File.separator + "test" + File.separator + "resources" + File.separator;	

	public IniFileTest(String name) {
		super(name);
	}

	protected void setUp() {

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));

	}
	
	public void testSetValue() {

		System.out.println("method: " + this.getName());
		
		File file = new File(currentDir + "test.ini");
		if (file.exists()) file.delete();

		IniFile iniFile = new IniFile(file.getPath());
		iniFile.setValue("test", "123456");
		iniFile.setValue("app_server", "Tomcat 5.0");
		iniFile.setValue("author", "Gerson Rodrigues");
		iniFile.save();
		
		if (!file.exists()) fail("IniFile not created");

	}	

	public void testGetValue() {

		this.testSetValue();
		
		File file = new File(currentDir + "test.ini");

		IniFile iniFile = new IniFile(file.getPath());

		assertEquals("Tomcat 5.0", iniFile.getValue("app_server"));
		assertEquals("Gerson Rodrigues", iniFile.getValue("author"));
		assertEquals("Undefined", iniFile.getValue("nao_existe"));
		
		if (file.exists()) file.delete();		
	}

}
