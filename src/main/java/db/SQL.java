package sofia.toolbox.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import sofia.toolbox.util.DateTime;

/**
 * This class contains methods to work with SQL and result sets via JDBC.
 * 
 * @author Gerson Rodrigues
 * @since 1.0
 * 
 */

public class SQL {

	private String message;
	private Connection connection;
	private HashMap<ResultSet, PreparedStatement> hmPreparedStatement;

	public SQL (Connection connection) {

		hmPreparedStatement = new HashMap<ResultSet, PreparedStatement>();
		this.connection = connection;

	}	

	public String getMessage() {
		return this.message;
	}		

	private Connection getConnection() {
		return this.connection;
	}

	public final int executeUpdate(String sql, Object... params) {

		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);

			int cont = 1;
			for (Object p : params) { 
				if (p == null) pstmt.setString(cont++, null);
				else
					if (p.getClass().getName().equals("java.lang.String")) pstmt.setString(cont++, (String)p);
					else
						if (p.getClass().getName().equals("java.lang.Integer")) pstmt.setInt(cont++, (Integer)p);
						else
							if (p.getClass().getName().equals("java.sql.Date")) pstmt.setDate(cont++, (java.sql.Date)p);
							else
								if (p.getClass().getName().equals("java.sql.Timestamp")) pstmt.setTimestamp(cont++, (Timestamp)p, Calendar.getInstance(TimeZone.getTimeZone("GMT" + DateTime.getTimeZone())));
								else
									if (p.getClass().getName().equals("java.lang.Double")) pstmt.setDouble(cont++, (Double)p);
									else
										if (p.getClass().getName().equals("java.lang.Boolean")) pstmt.setString(cont++, ((Boolean)p?"Y":"N"));
										else
											if (p.getClass().getName().equals("java.lang.Long")) pstmt.setLong(cont++, ((Long)p));

			}
			int total = pstmt.executeUpdate();

			pstmt.close();

			return total;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return (-1);
		}
	}

	public final int executeUpdate(StringBuffer sql, Object... params) {
		int total = executeUpdate(sql.toString(), params);
		return total;
	}

	public final ResultSet executeQuery(String sql, Object... params) {
		try {

			PreparedStatement pstmt;
			pstmt = this.getConnection().prepareStatement(sql);
			int cont = 1;
			for (Object p : params) {
				if (p == null) pstmt.setString(cont++, null);
				else 
					if (p.getClass().getName().equals("java.lang.String")) pstmt.setString(cont++, (String)p);
					else
						if (p.getClass().getName().equals("java.lang.Integer")) pstmt.setInt(cont++, (Integer)p);
						else
							if (p.getClass().getName().equals("java.sql.Date")) pstmt.setDate(cont++, (java.sql.Date)p);
							else
								if (p.getClass().getName().equals("java.sql.Timestamp")) pstmt.setTimestamp(cont++, (Timestamp)p, Calendar.getInstance(TimeZone.getTimeZone("GMT" + DateTime.getTimeZone())));
								else
									if (p.getClass().getName().equals("java.lang.Double")) pstmt.setDouble(cont++, (Double)p);
									else
										if (p.getClass().getName().equals("java.lang.Boolean")) pstmt.setString(cont++, ((Boolean)p?"Y":"N"));
										else
											if (p.getClass().getName().equals("java.lang.Long")) pstmt.setLong(cont++, ((Long)p));

			}

			ResultSet rs = pstmt.executeQuery();

			hmPreparedStatement.put(rs, pstmt);

			return rs;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return null;
		}
	}

	public final ResultSet executeQuery(StringBuffer sql, Object... params)  {
		return executeQuery(sql.toString(), params);
	}

	public final boolean execute(String sql, Object... params) {

		PreparedStatement pstmt;
		try {
			pstmt = this.getConnection().prepareStatement(sql);

			int cont = 1;
			for (Object p : params) {
				if (p == null) pstmt.setString(cont++, null);
				else
					if (p.getClass().getName().equals("java.lang.String")) pstmt.setString(cont++, (String)p);
					else
						if (p.getClass().getName().equals("java.lang.Integer")) pstmt.setInt(cont++, (Integer)p);
						else
							if (p.getClass().getName().equals("java.sql.Date")) pstmt.setDate(cont++, (java.sql.Date)p);
							else
								if (p.getClass().getName().equals("java.sql.Timestamp")) pstmt.setTimestamp(cont++, (Timestamp)p, Calendar.getInstance(TimeZone.getTimeZone("GMT" + DateTime.getTimeZone())));
								else
									if (p.getClass().getName().equals("java.lang.Double")) pstmt.setDouble(cont++, (Double)p);
									else
										if (p.getClass().getName().equals("java.lang.Boolean")) pstmt.setString(cont++, ((Boolean)p?"Y":"N"));
										else
											if (p.getClass().getName().equals("java.lang.Long")) pstmt.setLong(cont++, ((Long)p));

			}
			boolean result = pstmt.execute();

			pstmt.close();

			return result;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return (false);
		}
	}

	public final boolean execute(StringBuffer sql, Object... params) {
		return execute(sql.toString(), params);
	}

	public final boolean close(ResultSet rs)  {
		try {

			PreparedStatement pstmt = hmPreparedStatement.get(rs);
			rs.close();
			pstmt.close();
			hmPreparedStatement.remove(rs);
			return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}
	}

	public final boolean close()  {
		try {
			this.getConnection().close();
			return true;
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();
			return false;
		}
	}	

	public final ArrayList<String> getColumnNames(ResultSet rs) {

		ArrayList<String> al = new ArrayList<String>();
		ResultSetMetaData rsmd;

		try {
			rsmd = rs.getMetaData();
			for (int i = 1; i <= rsmd.getColumnCount(); i++) 
				al.add(rsmd.getColumnLabel(i));			
		} catch (Exception e) {
			this.message = e.getLocalizedMessage();			
			return null;
		}

		return al;
	}

	public String getString(ResultSet rs, String name) {
		String result;
		try {
			result = rs.getString(name);
			if (rs.wasNull()) result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}

	public Integer getInt(ResultSet rs, String name) {
		Integer result;
		try {
			result = rs.getInt(name);
			if (rs.wasNull()) result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}	

	public Timestamp getTimestamp(ResultSet rs, String name) {
		Timestamp result;
		try {
			result = rs.getTimestamp(name);
			if (rs.wasNull()) result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}	

	public Date getDate(ResultSet rs, String name) {
		Date result;
		try {
			result = rs.getDate(name);
			if (rs.wasNull()) result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}	

	public Long getLong(ResultSet rs, String name) {
		Long result;
		try {
			result = rs.getLong(name);
			if (rs.wasNull()) result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}		

	public Boolean getBoolean(ResultSet rs, String name) {
		String result;
		try {
			result = rs.getString(name);
			if (rs.wasNull()) result = null;
			return result.equals("Y");
		} catch (Exception e) {
			return null;
		}
	}	

	public Double getDouble(ResultSet rs, String name) {
		Double result;
		try {
			result = rs.getDouble(name);
			if (rs.wasNull()) result = null;
			return result;
		} catch (Exception e) {
			return null;
		}
	}	

	public String pagination(String sql, long pageNumber, long pageSize) {
		StringBuffer result = new StringBuffer();

		try {
			if (this.connection.getMetaData().getDatabaseProductName().toLowerCase().contains("oracle")) {
				long startItem = (pageNumber - 1)*pageSize + 1;
				long endItem = startItem + pageSize - 1;

				result.append("select * ");
				result.append("from ( select /*+ FIRST_ROWS(100) */ ");
				result.append("topn.*, ROWNUM rnum ");
				result.append(" from ( ");
				result.append(sql);
				result.append(" ) topn ");
				result.append(" where ROWNUM <= " + endItem + " ) ");
				result.append(" where rnum  >= " + startItem);
			}
			else
				if (this.connection.getMetaData().getDatabaseProductName().toLowerCase().contains("mysql")) {

					long startItem = (pageNumber - 1)*pageSize;

					result.append(sql);
					result.append(" limit " + startItem + "," + pageSize);
				}
				else
					if (this.connection.getMetaData().getDatabaseProductName().toLowerCase().contains("firebird")) {

						long startItem = (pageNumber - 1)*pageSize;

						result.append ("select first " + pageSize + " skip " + startItem + " * from (");
						result.append(sql);
						result.append(")");
					}
					else
						result.append(sql);
			return result.toString();
		}
		catch (Exception e) {
			return sql;
		}

	}

}
