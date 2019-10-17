//$Id$
package com.docable.todo.daolayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.docable.todo.exceptions.CustomException;

public abstract class DAOFactory {

	// Constants

	private static final String PROPERTY_URL = "url";
	private static final String PROPERTY_DRIVER = "driver";
	private static final String PROPERTY_USERNAME = "username";
	private static final String PROPERTY_PASSWORD = "password";

	// Actions

	public static DAOFactory getInstance(String name) throws CustomException {
		if (name == null) {
			throw new CustomException("Database name is null.");
		}

		DAOProperties properties = new DAOProperties(name);
		String url = properties.getProperty(PROPERTY_URL, true);
		String driverClassName = properties.getProperty(PROPERTY_DRIVER, false);
		String password = properties.getProperty(PROPERTY_PASSWORD, false);
		String username = properties.getProperty(PROPERTY_USERNAME, password != null);
		DAOFactory instance;

		// If driver is specified, then load it to let it register itself with
		// DriverManager.
		if (driverClassName != null) {
			try {
				Class.forName(driverClassName);
			} catch (ClassNotFoundException e) {
				throw new CustomException("Driver class '" + driverClassName + "' is missing in classpath.", e);
			}
			instance = new DriverManagerDAOFactory(url, username, password);
		}

		// Else assume URL as DataSource URL and lookup it in the JNDI.
		else {
			DataSource dataSource;
			try {
				dataSource = (DataSource) new InitialContext().lookup(url);
			} catch (NamingException e) {
				throw new CustomException("DataSource '" + url + "' is missing in JNDI.", e);
			}
			if (username != null) {
				instance = new DataSourceWithLoginDAOFactory(dataSource, username, password);
			} else {
				instance = new DataSourceDAOFactory(dataSource);
			}
		}

		return instance;
	}

	/**
	 * Returns a connection to the database. Package private so that it can be
	 * used inside the DAO package only.
	 * 
	 * @return A connection to the database.
	 * @throws SQLException
	 *             If acquiring the connection fails.
	 */
	abstract Connection getConnection() throws SQLException;

	// DAO implementation getters
	// -----------------------------------------------------------------

	/**
	 * Returns the User DAO associated with the current DAOFactory.
	 * 
	 * @return The User DAO associated with the current DAOFactory.
	 */
	public TodoDAO getToDoDAO() {
		return new TodoDAOJDBC(this);
	}

	// You can add more DAO implementation getters here.

}

// Default DAOFactory implementations

/**
 * The DriverManager based DAOFactory.
 */
class DriverManagerDAOFactory extends DAOFactory {
	private String url;
	private String username;
	private String password;

	DriverManagerDAOFactory(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@Override
	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}
}

/**
 * The DataSource based DAOFactory.
 */
class DataSourceDAOFactory extends DAOFactory {
	private DataSource dataSource;

	DataSourceDAOFactory(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
}

/**
 * The DataSource-with-Login based DAOFactory.
 */
class DataSourceWithLoginDAOFactory extends DAOFactory {
	private DataSource dataSource;
	private String username;
	private String password;

	DataSourceWithLoginDAOFactory(DataSource dataSource, String username, String password) {
		this.dataSource = dataSource;
		this.username = username;
		this.password = password;
	}

	@Override
	Connection getConnection() throws SQLException {
		return dataSource.getConnection(username, password);
	}
}
