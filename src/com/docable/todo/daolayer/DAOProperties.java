//$Id$
package com.docable.todo.daolayer;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.docable.todo.exceptions.CustomException;

public class DAOProperties {

	private static final String PROPERTIES_FILE = "dao.properties";
	private static final Properties PROPERTIES = new Properties();

	static {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

		if (propertiesFile == null) {
			throw new CustomException("Properties file '" + PROPERTIES_FILE + "' is missing in classpath.");
		}

		try {
			PROPERTIES.load(propertiesFile);
		} catch (IOException e) {
			throw new CustomException("Cannot load properties file '" + PROPERTIES_FILE + "'.", e);
		}
	}

	private String dbName;

	public DAOProperties(String specificKey) throws CustomException {
		this.dbName = specificKey;
	}

	public String getProperty(String key, boolean mandatory) throws CustomException {
		String fullKey = dbName + "." + key;
		String property = PROPERTIES.getProperty(fullKey);

		if (property == null || property.trim().length() == 0) {
			if (mandatory) {
				throw new CustomException("Required property '" + fullKey + "'" + " is missing in properties file '"
						+ PROPERTIES_FILE + "'.");
			} else {
				property = null;
			}
		}

		return property;
	}

}
