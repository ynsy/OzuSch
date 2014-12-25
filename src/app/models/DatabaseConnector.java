package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import models.JSONParser.*;

/**
 * @author Burak Atalay
 *
 */
public class DatabaseConnector {

	public static Connection connection;
	public static PreparedStatement statement = null;
	public static Course course;


	public static Connection makeConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());

		connection = DriverManager
				.getConnection("jdbc:mysql://localhost/OzuSch?"
						+ "user=root&password=root");

		if (connection != null) {
			return connection;
		} else {
			System.out.println("Failed to make connection!");
			return null;
		}
	}
}
