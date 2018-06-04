package sofia.toolbox.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import sofia.toolbox.db.SQL;

/**
 * This class contains methods to work with data sets via JDBC.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class Database {

	private DataSource datasource;
	private String message;

	InitialContext envContext;

	public Database (String jndiName, Properties ht) {
		try {
			Context initialContext = new InitialContext(ht);
			this.datasource = (DataSource) initialContext.lookup(jndiName);	

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
		}
	}

	public String getMessage() {
		return this.message;
	}		

	public DataSource getDataSource() {
		return this.datasource;
	}
	
	public boolean isValid() { 
		try {
			Connection connection = this.getDataSource().getConnection();
			connection.close();

			return true;

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return false;
		}

	}		

	public String getDriverName() {
		try {
			Connection connection = this.getDataSource().getConnection();
			DatabaseMetaData meta = connection.getMetaData();

			String product = meta.getDatabaseProductName();

			connection.close();			

			int pos = product.indexOf(" ");
			if (pos <= 0) pos = product.length();
			return product.substring(0, pos).toLowerCase();

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return "";
		}

	}

	public String getProductName() 
	{
		try {
			Connection connection = this.getDataSource().getConnection();
			DatabaseMetaData meta = connection.getMetaData();
			
			String productName = meta.getDatabaseProductName();
			
			connection.close();

			return productName;

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();	
			return "";
		}

	}	

	public String getProductVersion() 
	{
		try {
			Connection connection = this.getDataSource().getConnection();
			DatabaseMetaData meta = connection.getMetaData();
			
			String productVersion = meta.getDatabaseProductVersion();
			
			connection.close();
			
			return productVersion;

		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return "";
		}

	}	

	public SQL open() {
		try {
			return new SQL(this.getDataSource().getConnection());
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}

	}
	
	public boolean close(SQL sql) {
		try {
			return sql.close();
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}

	}	
}
