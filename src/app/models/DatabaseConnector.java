
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Burak Atalay
 *
 */
public class DatabaseConnector {
	
	public static Connection connection;
	public static PreparedStatement statement = null;
	public static ResultSet resultSet = null;
	
	public static Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = null;
		
		connection = DriverManager.getConnection("jdbc:mysql://localhost/OzuSch?characterEncoding=UTF-8" + 
                "user=ozusch&password=1");

		if (connection != null) {
			return connection;
		} else {
			System.out.println("Failed to make connection!");
			return null;
		}
	}
	
	static PreparedStatement defineStatement(String SQLCommand) throws SQLException {
		return statement = connection.prepareStatement(SQLCommand);
	}

	static ResultSet defineSet() throws SQLException {
		return resultSet = statement.executeQuery();
	}
	
	static  void closeSetAndStatement() throws SQLException {
		resultSet.close();
		statement.close();
	}

}
