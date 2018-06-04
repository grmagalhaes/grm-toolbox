package sofia.toolbox.db.testcase;

import sofia.toolbox.db.Statement;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class StatementTest extends TestCase {

	public StatementTest(String name) {
		super(name);
	}

	protected void setUp() {

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));

	}

	public void testGetStatement1() {

		System.out.println("method: " + this.getName());

		Statement statement = new Statement("statement.properties", true);

		assertEquals("select name, nickname, country_code, language_code, theme_id, last_connection, type, authentication_type, email, enabled, password_date from users where code = ?", statement.getStatement("context.getUserInformation1"));
	}
	
	public void testGetStatement2() {

		System.out.println("method: " + this.getName());

		Statement statement = new Statement("statement.properties");
		
		statement.setEncrypted(true);

		assertEquals("select name, nickname, country_code, language_code, theme_id, last_connection, type, authentication_type, email, enabled, password_date from users where code = ?", statement.getStatement("context.getUserInformation1"));
	}
	
	
	public void testGetStatement3() {

		System.out.println("method: " + this.getName());

		Statement statement = new Statement("statement.properties", false);

		assertEquals("select name, nickname, country_code, language_code, theme_id, last_connection, type, authentication_type, email, enabled, password_date from users where code = ?", statement.getStatement("context.getUserInformation2"));
	}
	
	public void testGetStatement4() {

		System.out.println("method: " + this.getName());

		Statement statement = new Statement("statement.properties");
		
		statement.setEncrypted(false);		

		assertEquals("select name, nickname, country_code, language_code, theme_id, last_connection, type, authentication_type, email, enabled, password_date from users where code = ?", statement.getStatement("context.getUserInformation2"));
	}	
	
}
