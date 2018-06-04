package sofia.toolbox.util.testcase;

import sofia.toolbox.util.HTMLEscape;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class HtmlEscapeTest extends TestCase {

	public HtmlEscapeTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}
			
		public void testEscapeForHtml() {
			
			System.out.println("method: " + this.getName());
		
			assertEquals("&amp;lt;html&amp;gt;", HTMLEscape.escape("<html>"));
			assertEquals("html", HTMLEscape.escape("html"));
			assertEquals("&amp;", HTMLEscape.escape("&"));
		}
		
				
		
	}
	
	 	
