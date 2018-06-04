package sofia.toolbox.crypto.testcase;

import sofia.toolbox.crypto.AES;
import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class AESTest extends TestCase {

	public AESTest(String name) {
		super(name);
	}

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}	

	public void testEncrypt(){

		System.out.println("method: " + this.getName());

		AES aes = new AES();
		
		
		aes.setKey("12345678901234567890123456789777");
		assertEquals("7C93043AED613C32EC0397596F2242A2", aes.encrypt("gerson"));

		aes.setKey("12345678901234567890123456789777");
		assertEquals("7C93043AED613C32EC0397596F2242A2", aes.encrypt("gerson"));		
		
		// Used in query
		//aes.setKey("C2BAC097D7EECDF24B71358126C71AAF");
		//System.out.println("QUERY: " + aes.encrypt("select count(*) total from users where code like ? and internal_name like ? and enabled like ? and authentication_type like ? and type like ? and country_code like ? and language_code like ? and created_by like ? and changed_by like ?"));
	
		// Used in Config
		//aes.setKey("B2BAC097D7EECDF24B71358126C7139F");
		//System.out.println("System: " + aes.encrypt("SOS - Service Order System"));
		//System.out.println("Site: " + aes.encrypt("http://www.docprinter.com.br"));
		//System.out.println("Application: " + aes.encrypt("sos.jpg"));
		//System.out.println("version: " + aes.encrypt("1.0"));
		
		
		/* sigem4 decrypt */
		//aes.setKey("2F15714947D9B8590C07ADD6AF7F679A");
		//System.out.println("senha: " + aes.decrypt("53C7A467AF335FC43C9F915EC7AC4B77"));
		
	};

	public void testDecrypt()  {

		System.out.println("method: " + this.getName());

		AES aes = new AES();

		aes.setKey(aes.generateKey());
		String encodedText = aes.encrypt("gerson");

		assertEquals("gerson", aes.decrypt(encodedText));
		
		aes.setKey("12345678901234567890123456789777");
		assertEquals("gerson", aes.decrypt("7C93043AED613C32EC0397596F2242A2"));
		
		aes.setKey("904850918590231485908");
		System.out.println(aes.decrypt("354F363E373138393A3C303C383A413E3F374F3A30303C3637373C3B373E3A313E374D333E3E333F4E37353A413D44"));

	};	

}
