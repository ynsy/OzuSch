
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
	
	public static String insertIntoCourses = "INSERT INTO courses (id) VALUES (1);";
	
	public static void main (String[] args) throws ClassNotFoundException, SQLException {
		makeConnection();
		executeInsertCommands(insertIntoCourses);
	}
	
	public static void executeInsertCommands(String rsrcID) throws SQLException {
		statement = defineStatement(insertIntoCourses);
		resultSet = statement.executeQuery();
		closeSetAndStatement();
	}
	
	public static Connection makeConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = null;
		DriverManager.registerDriver(new com.mysql.jdbc.Driver());
		
		connection = DriverManager.getConnection("jdbc:mysql://localhost/OzuSch?" + 
                "user=ozusch&password=1");

		if (connection != null) {
			return connection;
		} else {
			System.out.println("Failed to make connection!");
			return null;
		}
	}
	
	public static PreparedStatement defineStatement(String SQLCommand) throws SQLException {
		return statement = connection.prepareStatement(SQLCommand);
	}

	public static ResultSet defineSet() throws SQLException {
		return resultSet = statement.executeQuery();
	}
	
	public static  void closeSetAndStatement() throws SQLException {
		resultSet.close();
		statement.close();
	}

}
