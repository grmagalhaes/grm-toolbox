package sofia.toolbox.io.testcase;

import sofia.toolbox.io.Property;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class PropertyTest extends TestCase {

	public PropertyTest(String name) {
		super(name);
	}

	protected void setUp() {

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));

	}

	public void testGetProperty() {

		System.out.println("method: " + this.getName());

		Property properties = new Property("testfile.properties");

		assertEquals("Tomcat 5.0", properties.getProperty("app_server"));
		assertEquals("Gerson Rodrigues", properties.getProperty("author"));
		assertEquals("Undefined", properties.getProperty("nao_existe"));
	}
}
