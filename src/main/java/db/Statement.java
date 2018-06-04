package sofia.toolbox.db;

import sofia.toolbox.crypto.AES;
import sofia.toolbox.io.Property;

/**
 * This class contains methods to work with SQL clauses (cryptographed).
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Statement {

	private Property sqlConfig;
	private String message;
	private boolean encrypted = true;
	private AES aes = new AES("C2BAC097D7EECDF24B71358126C71AAF");


	public Statement(String sqlConfigFile, boolean encrypted) {
		this.sqlConfig = new Property(sqlConfigFile);
		this.encrypted = encrypted;
	}
	
	public Statement(String sqlConfigFile) {
		this(sqlConfigFile, true);
	}	

	public String getMessage() {
		return this.message;
	}		

	public void setEncrypted(boolean value) {
		this.encrypted = value;
	}

	public boolean isEncrypted() {
		return this.encrypted;
	}

	public String getStatement(String statementName) {
		if (this.encrypted)
			return aes.decrypt(this.sqlConfig.getProperty(statementName));
		else
			return this.sqlConfig.getProperty(statementName);			
	}

}
