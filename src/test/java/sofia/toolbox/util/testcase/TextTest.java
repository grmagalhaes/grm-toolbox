package sofia.toolbox.util.testcase;


import java.util.Locale;

import sofia.toolbox.util.Text;
import junit.framework.TestCase;

public class TextTest extends TestCase {

	public TextTest(String name) {
		super(name);
	}	

	protected void setUp() { 

		System.out.println("class: " + this.getClass().getName());

	}

	protected void tearDown() {

		System.out.println(Text.repeat("=", 80));		

	}
	
	 public void testCapitalize() {

		System.out.println("method: " + this.getName());

		assertEquals("Gerson rodrigues", Text.capitalize("gerson rodrigues"));
		assertEquals("Gerson Rodrigues", Text.capitalize("gerson Rodrigues"));		

	}	

	 public void testExtractString() {

		System.out.println("method: " + this.getName());

		assertEquals("rso", Text.extractString("Gerson", "rs", 1));
		assertEquals("erso", Text.extractString("Gerson", "e", 3));
		assertEquals("e", Text.extractString("Gerson", "e", 0));

	}
	 
	 public void testDoubleToString() {

		System.out.println("method: " + this.getName());

		assertEquals("121,12", Text.doubleToString(121.12, new Locale("PT", "BR")));
		assertEquals("1.121,12", Text.doubleToString(1121.12, new Locale("PT", "BR")));		
		assertEquals("1,121.12", Text.doubleToString(1121.12, new Locale("EN", "US")));
		assertEquals("2", Text.doubleToString( 2, Locale.ENGLISH));
		assertEquals("2", Text.doubleToString( 2, new Locale("pt", "BR")));
		assertEquals("20", Text.doubleToString( 20, Locale.ENGLISH));
		assertEquals("20", Text.doubleToString( 20, new Locale("pt", "BR")));		
		assertEquals("1,077.65", Text.doubleToString(1077.65, Locale.ENGLISH));		

	}	 

	public void testIsNumeric() {
	
		System.out.println("method: " + this.getName());
	
		assertTrue(Text.isNumeric("23"));
		assertFalse(Text.isNumeric("-23"));
		assertFalse(Text.isNumeric("aa"));
		assertFalse(Text.isNumeric("  0"));
		
	}

	public void testRPad() {
		
		System.out.println("method: " + this.getName());
	
		assertEquals("gersonXXXX", Text.rPad("gerson", 'X', 10));
		assertEquals("gersonbbbb", Text.rPad("gerson", 'b', 10));
		assertEquals("gersonbbbb", Text.rPad(" gerson ", 'b', 10));
		assertEquals("gerson    ", Text.rPad("gerson", 10));
		
	}

	public void testLPad() {
		
		System.out.println("method: " + this.getName());
	
		assertEquals("XXXXgerson", Text.lPad("gerson", 'X', 10));
		assertEquals("bbbbgerson", Text.lPad("gerson", 'b', 10));
		assertEquals("bbbbgerson", Text.lPad(" gerson ", 'b', 10));
		assertEquals("    gerson", Text.lPad("gerson", 10));
		
	}	
	
	public void testRemoveInvalidChars() {
		
		System.out.println("method: " + this.getName());
	
		assertEquals("_", Text.removeInvalidChars("/"));
		assertEquals("_", Text.removeInvalidChars("\\"));
		assertEquals("_", Text.removeInvalidChars("|"));
		assertEquals("_", Text.removeInvalidChars("*"));
		assertEquals("_", Text.removeInvalidChars("?"));
		assertEquals("_", Text.removeInvalidChars(">"));
		assertEquals("_", Text.removeInvalidChars("<"));
		assertEquals("_", Text.removeInvalidChars(":"));
		assertEquals("_", Text.removeInvalidChars("\""));
		assertEquals("____", Text.removeInvalidChars("|||*"));
		
		assertEquals("_abcdefghijklmnopqrstuvwxtzABCDEFGHIJKLMNOPQRSTUVWXYZ$#@!%__", 
				     Text.removeInvalidChars(":abcdefghijklmnopqrstuvwxtzABCDEFGHIJKLMNOPQRSTUVWXYZ$#@!%_:"));
	}

	public void testReplaceString() {
		
		System.out.println("method: " + this.getName());
	
		assertEquals("gexxon", Text.replaceString("gerson", "rs", "xx"));
		assertEquals("caxxo", Text.replaceString("carro", "r", "x"));
		assertEquals("caxxxxo", Text.replaceString("carro", "r", "xx"));
		assertEquals("cao", Text.replaceString("carro", "r", ""));
		
	}
	
	public void testRepeat() {
		
		System.out.println("method: " + this.getName());
	
		assertEquals("xxxxx", Text.repeat("x", 5));
		assertEquals("xxxxxxxxxx", Text.repeat("xx", 5));		
		assertEquals("", Text.repeat("", 5));		
		assertEquals("", Text.repeat("x", -1));		
		
	}	
	
	public void testReplaceRegexString() {
		
		System.out.println("method: " + this.getName());
	
		assertEquals("xxrson", Text.replaceRegexString("gerson", "[a-g]", "x"));
		assertEquals("gexson", Text.replaceRegexString("gerson", "[a-r&&[r-z]]", "x"));  //intersection
		assertEquals("xxxxxx", Text.replaceRegexString("gerson", "[a-r[r-z]]", "x"));  //union
		assertEquals("12|12|2009", Text.replaceRegexString("12/12/2009", "[//]", "|"));
		assertEquals("DD/DD/DDDD", Text.replaceRegexString("12/12/2009", "[0-9]", "D"));
	}
	
	 public void testByteToHex() {

			System.out.println("method: " + this.getName());

			byte[] b = new byte[2];
			b[0] = 10;
			b[1] = 20;
			
			assertEquals("0A14", Text.byteToHex(b, 2));
			assertEquals("0A14", Text.byteToHex(b));
		}

		public void testFormat() {
		
			System.out.println("method: " + this.getName());
		
		}

		public void testHextoByte() {
		
			System.out.println("method: " + this.getName());
		
			
			assertEquals(10, Text.hexToByte("0A14")[0]);
			assertEquals(20, Text.hexToByte("0A14")[1]);

			
		}

		public void testNullToString() {
		
			System.out.println("method: " + this.getName());
		
			String test1 = null;
			String test2 = "gerson";
			
			assertEquals("", Text.nullToString(test1));
			assertEquals("gerson", Text.nullToString(test2));
			
		}

		public void testParseInt() {
		
			System.out.println("method: " + this.getName());
		
			String test1 = "23";
			String test2 = "XX";
			
			assertEquals(23, Text.stringToInteger(test1, 99));
			assertEquals(99, Text.stringToInteger(test2, 99));
			
		}

		public void testConvert() {
		
			System.out.println("method: " + this.getName());
		
			assertEquals("aeiou aeiou aeiou ao aeiou c AEIOU AEIOU AEIOU AO AEIOU C", Text.convert("àèìòù áéíóú âêîôû ãõ äëïöü ç ÀÈÌÒÙ ÁÉÍÓÚ ÂÊÎÔÛ ÃÕ ÄËÏÖÜ Ç"));
			assertEquals("abcdefghjiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", Text.convert("abcdefghjiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));		
			assertEquals("Carrocao", Text.convert("Carroção"));
			assertEquals("ORGAO", Text.convert("ÓRGÃO"));		
		}
		
		public void testTranslate() {
			
			System.out.println("method: " + this.getName());
		
			assertEquals("jose!!", Text.translate("gerson", "gerson", "jose!!"));
			assertEquals("abcdefghjiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", Text.translate("abcdefghjiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghjiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ", "abcdefghjiklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"));		
			assertEquals("aba", Text.translate("abc", "cde", "abc"));
			assertEquals("abcdefgh", Text.translate("flamengo", "aeflmnog", "ceabdfhg"));		
			assertEquals("alamengo", Text.translate("flamengo", "flamengo", "a"));	
			assertEquals("flxmengo", Text.translate("flamengo", "aa", "xy"));	
		}
		
		
	}
	
	 	
