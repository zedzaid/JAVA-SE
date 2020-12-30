package com.example.demo.utils;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DbConnectionUtils {

	public static Connection getMySqlConnection() {
		Connection connection = null;
		String fileName = "DbConnection.properties";
		try {
			InputStream inputStream = DbConnectionUtils.class.getClassLoader().getResourceAsStream(fileName);
			Properties properties = new Properties();
			properties.load(inputStream);
			connection = DriverManager.getConnection(
					properties.getProperty("datasource.url"),
					properties.getProperty("datasource.username"),
					properties.getProperty("datasource.password")
					);
		}
		catch(IOException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
}
