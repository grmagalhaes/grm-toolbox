package sofia.toolbox.crypto;



import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.io.RandomAccessFile;
import java.util.Iterator;

import sofia.toolbox.io.FileSystem;


public class FTPClientPetrobras
{
	private ArrayList<String> _params;
	private String internalHash = "904850918590231485908";
	private String externalHash = "Programa para conectar na area FTP do Cliente!";
	
	private String configFile;


	public FTPClientPetrobras(String configFile)
	{
		this._params = new ArrayList<String>();
		this.configFile = configFile;
	}
	
	private int multiplyObject(int a, int b) {
		return a * b;
	}
	
	private int addObject(int a, int b) {
		return a + b;
	}	

	private String addObject(String a, String b) {
		return a + b;
	}	
	
	private boolean conditionalCompareObjectLess(int a, int b, boolean dummy) {
		return a < b;
	}
	
	private int subtractObject(int a, int b) {
		return a - b;
	}
	
	private int intDivideObject(int a, int b) {
		return a/b;
	}
	
	private byte xorObject(int a, int b) {
		return (byte) (a ^ b);
	}
	
	private int compareString(String a, String b, boolean ignoreCase) {
		if (ignoreCase) {
			if (a.equalsIgnoreCase(b)) return 0;
			else return 1;
		}
		
		if (a.equals(b)) return 0;
		else return 1;
	}
	
	private int toInteger(int a) {
		return a;
	}
		
	private String toString(String a) {
		return a;
	}
	
	private String toString(int a) {
		return String.valueOf(a);
	}
	
	private String toString(char a) {
		return String.valueOf(a);
	}
	
	private byte asc(char c) {
		return (byte)c;
	}
	
	private byte asc(String s) {
		return (byte)s.charAt(0);
	}	
	
	private char chr(int a) {
		return (char)a;
	}
	
	private String concat(String a, String b) {
	  return a + b;
	}
	
	private String lowerCase(String a) {
		return a.toLowerCase();
	}
	
	private int length(String a) {
		return a.length();
	}	
	
	private int inString(String texto, String parte)	 {
		return texto.indexOf(parte) + 1;
	}
	
	private String middle(String texto, int inicio, int len) {
		if ((len + inicio - 1) > texto.length()) return texto.substring(inicio - 1);
		return texto.substring(inicio - 1, inicio + len - 1);
	}
	
	private char toChar(int a) {
    	return (char)a;
    }
	
	private String hex(int a) {
	    char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
	            'A', 'B', 'C', 'D', 'E', 'F' };
	    StringBuffer buf = new StringBuffer();
	    buf.append(hexDigit[(a >> 4) & 0x0f]);
	    buf.append(hexDigit[a & 0x0f]);
	    return buf.toString();
	}
	
	private int toUInt32(String a, int radix) {
			return Integer.valueOf(a, radix);
	}	
	
	private String data_Asc_Hex(String Data)
	{
		String str = "";
		int obj = 0;
		while (true)
		{
			boolean length = this.conditionalCompareObjectLess(obj, Data.length(), false);
			if (!length)
			{
				break;
			}
			String str1 = this.hex(this.asc(Data.charAt(this.toInteger(obj))));
			length = str1.length() == 1;
			if (length)
			{
				str1 = this.concat("0", str1);
			}
			str = this.concat(str, str1);
			obj = this.addObject(obj, 1);
		}
		return str;
	}

	private String data_Hex_Asc(String Data)
	{
		String obj = "";
		while (true)
		{
			boolean length = Data.length() > 0;
			if (!length)
			{
				break;
			}
			char chr = this.toChar(this.toUInt32(Data.substring(0, 2), 16));
			String str = Character.toString(chr);
			obj = this.addObject(obj, str);
			Data = Data.substring(2, 2 + (Data.length() - 2));
		}
		String str1 = this.toString(obj);
		return str1;
	}

	private String generatePassword(String hash)
	{
		int counter;
		String txt = "";
		int left = 0;

		for (counter = 0; counter < hash.length(); counter++) {
			left = left + this.asc(hash.charAt(counter));
		}


		GregorianCalendar now = new GregorianCalendar();

		txt = this.getEncrypt(hash, this.toString(left));
		txt = this.getEncrypt(txt, this.toString(hash.length()));
		txt = this.getEncrypt(txt, this.internalHash);
		txt = this.getEncrypt(txt, this.toString(now.get(GregorianCalendar.DAY_OF_MONTH)));
		txt = this.getEncrypt(txt, this.toString(now.get(GregorianCalendar.MONTH)+1));
		txt = this.getEncrypt2Hex(txt, this.toString(now.get(GregorianCalendar.YEAR)));

		return (this.toString(this.chr(0x61 + now.get(GregorianCalendar.MONTH)+1)) + txt);
		
	} 


	private String getEncrypt(String txt, String hash)
	{
		String str2 = "";
		int left = 0;
		int obj3 = 0;
		while (this.conditionalCompareObjectLess(left, hash.length(), false))
		{

			obj3 = this.addObject(obj3, this.asc(hash.charAt(this.toInteger(left))));
			left = this.addObject(left, 1);
		}
		obj3 = this.subtractObject(obj3, this.multiplyObject(hash.length(), this.intDivideObject(obj3, hash.length())));
		for (left = 0; this.conditionalCompareObjectLess(left, txt.length(), false); left = this.addObject(left, 1))
		{
			int obj4= this.subtractObject(left, this.multiplyObject(hash.length(), this.intDivideObject(left, hash.length())));
			str2 = this.concat(str2, this.toString(this.chr(this.toInteger(this.xorObject(this.asc(txt.charAt(this.toInteger(left))) ^ this.asc(this.toString(this.asc(hash.charAt(this.toInteger(obj4))) ^ hash.length())), this.xorObject(obj3, this.asc(hash.charAt(this.toInteger(this.subtractObject(this.subtractObject(hash.length(), obj4), 1))))))))));
		}
		return str2;
	}

	private String getEncrypt2Hex(String txt, String hash)
	{
		String str = this.data_Asc_Hex(this.getEncrypt(txt, hash));
		return str;
	}

	private String getPair(String param)
	{
		String par = this.getPair(param, this.configFile);
		return par;
	}

	private String getPair(String param, String arquivo)
	{
		String parDireto = this.getRightPair(param, arquivo);
		return parDireto;
	}

	private String getPair2(String param, String par2)
	{
		String encrypt = this.getEncrypt(this.data_Hex_Asc(this.getPair(this.data_Asc_Hex(this.getEncrypt(param, par2)))), par2);
		return encrypt;
	}

	private String getRightPair(String param, String arquivo)
	{
		String str = "";
		String str1;
		Iterator<String> enumerator;
		boolean flag;
		try
		{
			str = "";
			param = this.lowerCase(param);
			this._params.clear();

			FileSystem fileSystem = new FileSystem();
			RandomAccessFile streamReader = fileSystem.openFile(arquivo, "r");
			do
			{
				str1 = streamReader.readLine();
				flag = str1 != null;
				if (flag)
				{
					this._params.add(this.lowerCase(str1));
				}
				flag = str1 != null;
			}
			while (flag);
			streamReader.close();
			try
			{
				enumerator = this._params.iterator();
				while (true)
				{
					flag = enumerator.hasNext();
					if (!flag)
					{
						break;
					}
					str1 = this.toString(enumerator.next());
					flag = this.compareString(this.internalHash, "", false) != 0;
					if (flag)
					{
						str1 = this.getEncrypt(this.data_Hex_Asc(str1), this.internalHash);
					}
					flag = this.inString(str1, this.concat(param, "=").toUpperCase()) > 0;
					if (flag)
					{
						String str2 = this.middle(str1, this.inString(str1, "=") + 1, this.length(str1));
						str = str2;
					}
				}
			}
			finally {};
		}
		catch (Exception exception)
		{
			System.out.println("erro em getParDireito");
		}
		return str;
	}

	public String getLang() {
		return this.getPair2("lang", this.externalHash); 
	}
	
	public String getServer() {
		return this.getPair2("server", this.externalHash); 
	}
	
	public String getUser() {
		return this.getPair2("user", this.externalHash); 
	}
	
	public String getLocalUser() {
		return this.getPair2("localuser", this.externalHash); 
	}	
	
	public String getUserPassword() {
		return this.getPair2("senhauser", this.externalHash); 
	}
	
	public String getArea() {
		return this.getPair2("area", this.externalHash); 
	}
	
	public String getSavePassword() {
		return this.getPair2("flagSalvarSenha", this.externalHash); 
	}
	
	public String getMode() {
		return this.getPair2("modo", this.externalHash); 
	}
	
	public String getActive() {
		return this.getPair2("ativo", this.externalHash); 
	}
	
	public String getLocalDir() {
		return this.getPair2("localdir", this.externalHash); 
	}		
	
	public String getRemoteDir() {
		return this.getPair2("remotedir", this.externalHash); 
	}
	
	public String getFTPDir() {
		return this.getPair2("ftpdir", this.externalHash); 
	}
	
	public String getHash() {
		return this.getPair2("hash", this.externalHash); 
	}		
	
	public String getRemotePassword() {
		return this.generatePassword(this.getPair2("hash", this.externalHash));
	}		
	
}
