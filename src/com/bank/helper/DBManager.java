package com.bank.helper;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bank.exception.DatabaseException;

public class DBManager
{

	private final String driver;
	private final String driverType;
	private final String databaseType;
	private final String host;
	private final String databaseName;
	private final String username;
	private final String password;
	private final String url;
	private Connection connection;

	@SuppressWarnings("unused")
	private DBManager()
	{
		this.driver = null;;
		this.driverType = null;
		this.databaseType = null;
		this.host = null;
		this.databaseName = null;
		this.username = null;
		this.password = null;
		this.url = null;
		this.connection = null;
	}

	public DBManager(String host, String databaseName, String username, String password)
	{
		this.driver = "oracle.jdbc.driver.OracleDriver";
		this.driverType = "thin";
		this.databaseType = "oracle";
		this.host = host;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;
		this.url = "jdbc:" + this.databaseType + ":" + this.driverType + "://" + this.host + ":" + this.databaseName;
	}

	public DBManager(String driver, String driverType, String databaseType, String host, String databaseName, String username, String password)
	{
		this.driver = driver;
		this.driverType = driverType;
		this.databaseType = databaseType;
		this.host = host;
		this.databaseName = databaseName;
		this.username = username;
		this.password = password;

		this.url = "jdbc:" + this.databaseType + ":" + this.driverType + "://" + this.host + ":" + this.databaseName;
	}

	public String getDriver()
	{
		return driver;
	}

	public String getDriverType()
	{
		return driverType;
	}

	public String getDatabaseType()
	{
		return databaseType;
	}

	public String getHost()
	{
		return host;
	}

	public String getDatabaseName()
	{
		return databaseName;
	}

	public String getUsername()
	{
		return username;
	}

	public String getPassword()
	{
		return password;
	}

	public String getUrl()
	{
		return url;
	}

	public Connection getConnection()
	{
		return connection;
	}

	public Connection connect()
	{
		try
		{
			Class.forName(driver);

			try
			{
				connection = DriverManager.getConnection(url, this.username, this.password);
			} catch ( SQLException e )
			{
				e.printStackTrace(); 
			}
		} catch ( ClassNotFoundException e )
		{
			e.printStackTrace();
		}

		return connection;
	}

	public Connection close()
	{
		try
		{
			connection.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return connection;
	}

	private static Boolean checkConnection(Connection connection) throws DatabaseException.NoConnectionEstablished
	{
		if ( connection == null )
		{
			throw new DatabaseException.NoConnectionEstablished("There is no database connection - please call the \"connect\" method before making any transactions on the database.");
		}

		return true;
	}

	private static String getFieldNames(String[] fieldsArr)
	{
		String fieldStr = Stream.of(fieldsArr).collect(Collectors.joining(","));

		return fieldStr;
	}

	private static String getValuePlaceholders(Object[] valuesArr)
	{
		String placeholder = Stream.of(valuesArr).map((value) -> "?").collect(Collectors.joining(","));
		
		return placeholder;
	}

	private static String getMappedFieldValues(String[] fieldsArr)
	{
		String mappedValues = Stream.of(fieldsArr).collect(Collectors.joining("=?,"));

		return mappedValues;
	}

	private static PreparedStatement setPreparedStatementValues(PreparedStatement pstmt, Object[] valuesArr) throws SQLException
	{
		for ( int index = 0; index < valuesArr.length; index++ )
		{
			Object value = valuesArr[index];
			int paramIndex = index + 1;

			if ( value instanceof String )
			{
				pstmt.setString( paramIndex, String.valueOf(value) );
			} else if ( value instanceof Integer )
			{
				pstmt.setInt( paramIndex, Integer.valueOf(String.valueOf(value)) );
			} else if ( value instanceof Double )
			{
				pstmt.setDouble( paramIndex, Double.valueOf(String.valueOf(value)) );
			} else if ( value instanceof Float )
			{
				pstmt.setFloat( paramIndex, Float.valueOf(String.valueOf(value)) );
			} else if ( value instanceof Boolean )
			{
				pstmt.setBoolean( paramIndex, Boolean.valueOf(String.valueOf(value)) );
			} else if ( value instanceof LocalDate )
			{
				pstmt.setDate( paramIndex, java.sql.Date.valueOf((LocalDate) value) );
			}  else if ( value instanceof BigDecimal )
			{
				pstmt.setBigDecimal( paramIndex, (BigDecimal) value );
			} else
			{
				pstmt.setObject(paramIndex, value);
			}
		}

		return pstmt;
	}
	
	public int insert(String table, String[] fieldsArr, Object[] valuesArr)
		throws DatabaseException.NoConnectionEstablished, DatabaseException.InvalidParameters
	{
		checkConnection(connection);

		if ( fieldsArr.length != valuesArr.length )
		{
			throw new DatabaseException.InvalidParameters("Invalid parameters given - \"fieldsArr\" and \"valuesArr\" must be the same length.");
		}

		try
		{
			String fields = getFieldNames(fieldsArr);
			String values = getValuePlaceholders(valuesArr);

			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ");");

			setPreparedStatementValues(pstmt, valuesArr);

			int numRows = pstmt.executeUpdate();

			connection.commit();

			return numRows;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public int insert(String table, String[] fieldsArr, Object[] valuesArr, String whereClause)
		throws DatabaseException.NoConnectionEstablished, DatabaseException.InvalidParameters
	{
		checkConnection(connection);

		if ( fieldsArr.length != valuesArr.length )
		{
			throw new DatabaseException.InvalidParameters("Invalid parameters given - \"fieldsArr\" and \"valuesArr\" must be the same length.");
		}

		try
		{
			String fields = getFieldNames(fieldsArr);
			String values = getValuePlaceholders(valuesArr);

			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ")" + "WHERE " + whereClause + ";");

			setPreparedStatementValues(pstmt, valuesArr);

			int numRows = pstmt.executeUpdate();

			connection.commit();

			return numRows;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}



	public int insert(String table, String[] fieldsArr, Object[] valuesArr, String whereClause, Object[] whereClauseValues)
		throws DatabaseException.NoConnectionEstablished, DatabaseException.InvalidParameters
	{
		checkConnection(connection);

		if ( fieldsArr.length != valuesArr.length )
		{
			throw new DatabaseException.InvalidParameters("Invalid parameters given - \"fieldsArr\" and \"valuesArr\" must be the same length.");
		}

		try
		{
			String fields = getFieldNames(fieldsArr);
			String values = getValuePlaceholders(valuesArr);

			PreparedStatement pstmt = connection.prepareStatement("INSERT INTO " + table + " (" + fields + ") VALUES (" + values + ")" + "WHERE " + whereClause + ";");

			setPreparedStatementValues(pstmt, DataFormatter.merge(valuesArr, whereClauseValues));

			int numRows = pstmt.executeUpdate();

			connection.commit();

			return numRows;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public int update(String table, String[] fieldsArr, Object[] valuesArr)
		throws DatabaseException.NoConnectionEstablished, DatabaseException.InvalidParameters
	{
		checkConnection(connection);

		if ( fieldsArr.length != valuesArr.length )
		{
			throw new DatabaseException.InvalidParameters("Invalid parameters given - \"fieldsArr\" and \"valuesArr\" must be the same length.");
		}

		try
		{
			String mappedFieldValues = getMappedFieldValues(fieldsArr);

			PreparedStatement pstmt = connection.prepareStatement("UPDATE " + table + " SET " + mappedFieldValues + ";");

			setPreparedStatementValues(pstmt, valuesArr);

			int numRows = pstmt.executeUpdate();

			connection.commit();

			return numRows;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public int update(String table, String[] fieldsArr, Object[] valuesArr, String whereClause)
		throws DatabaseException.NoConnectionEstablished, DatabaseException.InvalidParameters
	{
		checkConnection(connection);

		if ( fieldsArr.length != valuesArr.length )
		{
			throw new DatabaseException.InvalidParameters("Invalid parameters given - \"fieldsArr\" and \"valuesArr\" must be the same length.");
		}

		try
		{
			String mappedFieldValues = getMappedFieldValues(fieldsArr);

			PreparedStatement pstmt = connection.prepareStatement("UPDATE " + table + " SET " + mappedFieldValues + "WHERE " + whereClause + ";");

			setPreparedStatementValues(pstmt, valuesArr);

			int numRows = pstmt.executeUpdate();

			connection.commit();

			return numRows;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public int update(String table, String[] fieldsArr, Object[] valuesArr, String whereClause, Object[] whereClauseValues)
		throws DatabaseException.NoConnectionEstablished, DatabaseException.InvalidParameters
	{
		checkConnection(connection);

		if ( fieldsArr.length != valuesArr.length )
		{
			throw new DatabaseException.InvalidParameters("Invalid parameters given - \"fieldsArr\" and \"valuesArr\" must be the same length.");
		}

		try
		{
			String mappedFieldValues = getMappedFieldValues(fieldsArr);

			PreparedStatement pstmt = connection.prepareStatement("UPDATE " + table + " SET " + mappedFieldValues + "WHERE " + whereClause + ";");

			setPreparedStatementValues(pstmt, DataFormatter.merge(valuesArr, whereClauseValues));

			int numRows = pstmt.executeUpdate();

			connection.commit();

			return numRows;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public ResultSet select(String table, String[] fieldsArr) throws DatabaseException.NoConnectionEstablished
	{
		checkConnection(connection);

		try
		{
			String fields = fieldsArr.length > 0 ? getFieldNames(fieldsArr) : "*";

			PreparedStatement pstmt = connection.prepareStatement("SELECT " + fields + " FROM " + table + ";");

			ResultSet results = pstmt.executeQuery();

			connection.commit();

			return results;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public ResultSet select(String table, String[] fieldsArr, String whereClause) throws DatabaseException.NoConnectionEstablished
	{
		checkConnection(connection);

		try
		{
			String fields = fieldsArr.length > 0 ? getFieldNames(fieldsArr) : "*";

			PreparedStatement pstmt = connection.prepareStatement("SELECT " + fields + " FROM " + table + " WHERE " + whereClause + ";");

			ResultSet results = pstmt.executeQuery();

			connection.commit();

			return results;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public ResultSet select(String table, String[] fieldsArr, String whereClause, Object[] whereClauseValuesArr) throws DatabaseException.NoConnectionEstablished
	{
		checkConnection(connection);

		try
		{
			String fields = fieldsArr.length > 0 ? getFieldNames(fieldsArr) : "*";

			PreparedStatement pstmt = connection.prepareStatement("SELECT " + fields + " FROM " + table + " WHERE " + whereClause + ";");

			setPreparedStatementValues(pstmt, whereClauseValuesArr);

			ResultSet results = pstmt.executeQuery();

			connection.commit();

			return results;
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return null;
	}

	public int delete(String table, String whereClause) throws DatabaseException.NoConnectionEstablished
	{
		checkConnection(connection);

		try
		{
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM " + table + " WHERE " + whereClause + ";");

			int numRows = pstmt.executeUpdate();

			connection.commit();

			if ( numRows > 0 )
			{
				return numRows;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	public int delete(String table, String whereClause, Object[] whereClauseValuesArr) throws DatabaseException.NoConnectionEstablished
	{
		checkConnection(connection);

		try
		{
			PreparedStatement pstmt = connection.prepareStatement("DELETE FROM " + table + " WHERE " + whereClause + ";");

			setPreparedStatementValues(pstmt, whereClauseValuesArr);

			int numRows = pstmt.executeUpdate();

			connection.commit();

			if ( numRows > 0 )
			{
				return numRows;
			}
		} catch (SQLException e)
		{
			e.printStackTrace();
		}

		return 0;
	}

	@Override
	public String toString()
	{
		return "DBManager [driver=" + driver + ", driverType=" + driverType + ", databaseType=" + databaseType
				+ ", host=" + host + ", databaseName=" + databaseName + ", username=" + username + ", password="
				+ password + ", url=" + url + ", connection=" + connection + "]";
	}

}
