package sofia.toolbox.util;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains utility methods to work with strings.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Text {

	private static final String INVALID_CHAR = "/\\:*?\"<>|";	
	private static final String SOURCE_CONVERT_CHAR = "àèìòù áéíóú âêîôû ãõ äëïöü ç ÀÈÌÒÙ ÁÉÍÓÚ ÂÊÎÔÛ ÃÕ ÄËÏÖÜ Ç";
	private static final String TARGET_CONVERT_CHAR = "aeiou aeiou aeiou ao aeiou c AEIOU AEIOU AEIOU AO AEIOU C";
	private static final int BASELENGTH = 128;	
	static private final int LOOKUPLENGTH = 16;
	static final private byte[] hexNumberTable = new byte[BASELENGTH];
	static final private char[] lookUpHexAlphabet = new char[LOOKUPLENGTH];

	static {
		for (int i = 0; i < BASELENGTH; i++) {
			hexNumberTable[i] = -1;
		}
		for (int i = '9'; i >= '0'; i--) {
			hexNumberTable[i] = (byte) (i - '0');
		}
		for (int i = 'F'; i >= 'A'; i--) {
			hexNumberTable[i] = (byte) (i - 'A' + 10);
		}
		for (int i = 'f'; i >= 'a'; i--) {
			hexNumberTable[i] = (byte) (i - 'a' + 10);
		}

		for (int i = 0; i < 10; i++) {
			lookUpHexAlphabet[i] = (char) ('0' + i);
		}
		for (int i = 10; i <= 15; i++) {
			lookUpHexAlphabet[i] = (char) ('A' + i - 10);
		}
	}	


	public static String removeInvalidChars(String stringToRemove){
		int size = stringToRemove.length();
		StringBuffer result = new StringBuffer(size);

		for(int i = 0; i < size; i++){
			int pos = INVALID_CHAR.indexOf(stringToRemove.substring(i,i+1));
			if (pos == -1)
				result.append(stringToRemove.substring(i,i+1));
			else
				result.append("_");
		}

		return (result.toString());
	}

	public static String rPad(String value, char character, int size){
		StringBuffer result = new StringBuffer(size);
		StringBuffer sFillChar = new StringBuffer(size);

		for (int i=0; i<size; i++){
			sFillChar.append(character);
		}

		if (value.trim().length() > size){
			result.append(value);
		} else {
			result.append(value.trim()).append(sFillChar.substring(0,size - value.trim().length()));
		}

		return (result.toString());
	}

	public static String rPad(String value, int size){

		return rPad(value, ' ', size);
	}


	public static String lPad(String value, char character, int size){
		StringBuffer result = new StringBuffer(size);
		StringBuffer sFillChar = new StringBuffer(size);

		for (int i=0; i<size; i++){
			sFillChar.append(character);
		}

		if (value.trim().length() > size){
			result.append(value);
		} else {
			result.append(sFillChar.substring(0,size - value.trim().length())).append(value.trim());
		}

		return (result.toString());
	}

	public static String lPad(String value, int size){

		return lPad(value, ' ', size);
	}	

	public static String replaceRegexString(String cStr, String cRegEx, String cReplace)
	{

		Pattern p = Pattern.compile(cRegEx);
		Matcher m = p.matcher(cStr);
		return m.replaceAll(cReplace);

	}

	public static String replaceString(String cStr, String oldString, String newString)
	{
		String p1;
		String p2;
		int pos = cStr.indexOf(oldString);

		if (pos == -1) return cStr;

		p1 = cStr.substring(0, pos);
		p2 = cStr.substring(pos + oldString.length());

		StringBuffer result = new StringBuffer();
		result.append(p1).append(newString).append(p2);
		return replaceString(result.toString(),oldString, newString);
	}

	public static String extractString(String cstr, String search, int length) {
		int npos = cstr.indexOf(search);
		if (npos > -1) {
			String ret = cstr.substring(npos, npos + search.length() + length);
			return ret;
		}
		else
			return "";
	}

	public static final boolean isNumeric(final String s) {
		final char[] numbers = s.toCharArray();
		for (int x = 0; x < numbers.length; x++) {      
			final char c = numbers[x];
			if ((c >= '0') && (c <= '9'))  continue;
			return false; // invalid
		}
		return true; // valid
	}

	public static String repeat(String value, int count){
		if (count <= 0) return "";

		StringBuffer buffer = new StringBuffer(value.length() * count);
		for(int i=0; i < count; i++) {
			buffer.append(value);
		}
		return buffer.toString();
	}

	/**
	 * Converte um array de bytes na sua representação em Hexadecimal (como String)
	 * 
	 * @param binaryData  array de bytes a ser convertido para o seu equivalente em hexadecimal
	 * @param lengthData tamanho do array de bytes a ser convertido. Não pode ser maior que o número de bytes alocados em digestBits 
	 * @return String Retorna binaryData convertido para seu valor em hexadecimal
	 */	

	static public String byteToHex(byte[] binaryData, final int lengthData) {
		if (binaryData == null)
			return null;

		int lengthEncode = lengthData * 2;
		char[] encodedData = new char[lengthEncode];
		int temp;
		for (int i = 0; i < lengthData; i++) {
			temp = binaryData[i];
			if (temp < 0)
				temp += 256;
			encodedData[i * 2] = lookUpHexAlphabet[temp >> 4];
			encodedData[i * 2 + 1] = lookUpHexAlphabet[temp & 0xf];
		}
		return new String(encodedData);
	}

	public static String byteToHex(final byte[] bynaryData) {

		return byteToHex(bynaryData, bynaryData.length);
	}    


	/**
	 * Decode hex string to a byte array
	 *
	 * @param value encoded string
	 * @return return array of byte to encode
	 */
	static public byte[] hexToByte(String value) {
		if (value == null)
			return null;
		int lengthData = value.length();
		if (lengthData % 2 != 0)
			return null;

		char[] binaryData = value.toCharArray();
		int lengthDecode = lengthData / 2;
		byte[] decodedData = new byte[lengthDecode];
		byte temp1, temp2;
		char tempChar;
		for (int i = 0; i < lengthDecode; i++) {
			tempChar = binaryData[i * 2];
			temp1 = (tempChar < BASELENGTH) ? hexNumberTable[tempChar] : -1;
			if (temp1 == -1)
				return null;
			tempChar = binaryData[i * 2 + 1];
			temp2 = (tempChar < BASELENGTH) ? hexNumberTable[tempChar] : -1;
			if (temp2 == -1)
				return null;
			decodedData[i] = (byte) ((temp1 << 4) | temp2);
		}
		return decodedData;
	}


	public static String doubleToString(double value, Locale locale) {

		NumberFormat nf = NumberFormat.getInstance(locale);

		return nf.format(value);

	}

	/**
	 * 
	 * Convert a value into a locale format
	 * 
	 * @param value Value to be converted
	 * @param locale Locale used in the conversion
	 * @return Value converted into a locale
	 */

	public static String integerToString(int value, Locale locale) {

		NumberFormat nf = NumberFormat.getInstance(locale);

		return nf.format(value);

	}	

	public static String floatToString(float value, Locale locale) {

		NumberFormat nf = NumberFormat.getInstance(locale);

		return nf.format(value);

	}	

	public static int stringToInteger(String value, int defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	public static String nullToString(String value) {
		return (value == null ? "" : value);
	}
	
	public static Integer nullToInteger(Integer value) {
		return (value == null ? 0 : value);
	}
	
	public static Long nullToLong(Long value) {
		return (value == null ? 0 : value);
	}
	
	public static Boolean nullToBoolean(Boolean value) {
		return (value == null ? false : value);
	}	
	
	public static String convert(String value) {
		int size = value.length();
		StringBuffer result = new StringBuffer(size);

		for(int i = 0; i < size; i++){
			int nPosicao = SOURCE_CONVERT_CHAR.indexOf(value.substring(i,i+1));
			if (nPosicao == -1)
				result.append(value.substring(i,i+1));
			else
				result.append(TARGET_CONVERT_CHAR.substring(nPosicao,nPosicao+1));
		}

		return (result.toString());
	}


	public static String capitalize(String value) {	
		if (value != null) 
			return value.substring(0, 1).toUpperCase() + value.substring(1, value.length());
		else
			return null;
	}

	public static String translate(String text, String oldPattern, String newPattern) {
		StringBuffer result = new StringBuffer();
		String charAt;
		int position, difference, newPatternLength;

		if (text.length() == 0) return null;

		difference = oldPattern.length() - newPattern.length();

		if (difference > 0) {

			newPatternLength = newPattern.length();

			for (int i=0; i < difference; i++)
				newPattern = newPattern.concat(oldPattern.substring(newPatternLength + i, newPatternLength + i + 1));
		}

		for (int i=0; i< text.length(); i++) {
			charAt = text.substring(i, i + 1);
			position = oldPattern.indexOf(charAt);
			if (position != -1)
				result.append(newPattern.substring(position, position + 1));
			else
				result.append(charAt);	
		}

		return result.toString();

	}

	public static String escapeforURL(String url){
		String result = null;
		try {
			result = URLEncoder.encode(url, "UTF-8");
			return result;
		}
		catch (Exception e){
			return null;
		}
	}	

	public static String escapeForJSON(String text){
		final StringBuilder result = new StringBuilder();
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE){
			if( character == '\"' ){
				result.append("\\\"");
			}
			else if(character == '\\'){
				result.append("\\\\");
			}
			else if(character == '/'){
				result.append("\\/");
			}
			else if(character == '\b'){
				result.append("\\b");
			}
			else if(character == '\f'){
				result.append("\\f");
			}
			else if(character == '\n'){
				result.append("\\n");
			}
			else if(character == '\r'){
				result.append("\\r");
			}
			else if(character == '\t'){
				result.append("\\t");
			}
			else {
				result.append(character);
			}
			character = iterator.next();
		}
		return result.toString();    
	}	

	public static String insertJSONItem(String variable, Object value) {
		if (value == null)
			return "\"" + variable + "\":null";
		if (value.getClass().getName().equals("java.lang.String"))
			return "\"" + variable + "\":\"" + value + "\"";
		return "\"" + variable + "\":" + value + "";
	}

	public static String formatMailReceiver(String username, String mail) {
		return username + " <" + mail + ">";
	}

}


