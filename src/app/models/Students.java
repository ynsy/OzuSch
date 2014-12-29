package models;

import play.db.ebean.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.mysql.jdbc.Connection;

import controllers.Student;
import models.JSONParser.LectureInterval;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class Students extends Model{

    @Id
    public int id;

    public String name;

    public String surname;
    public String display_name;
    public String email;
    public String password; //It may be a private. Is must be looked.
    public static ResultSet resultSet;
	public static java.sql.PreparedStatement statement;
    /*
    @OneToOne
    public Departments department;
	*/

    @OneToOne
    public Universities university;
    
    public static void create(Students student) throws SQLException {
    	Ebean.save(student);
    }
    
    public static Boolean isUsernameValid(String username) throws SQLException, ClassNotFoundException{
    	DatabaseConnector.makeConnection();
		statement = DatabaseConnector.connection.prepareStatement("SELECT * FROM students WHERE display_name='"+username+"'");
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			return true;
		}
		statement.close();
		return false;
    }
    
    public static Boolean isEmailValid(String email) throws SQLException, ClassNotFoundException{
    		DatabaseConnector.makeConnection();
    		statement = DatabaseConnector.connection.prepareStatement("SELECT * FROM students WHERE email='"+email+"'");
    		resultSet = statement.executeQuery();

    		while (resultSet.next()) {
    			return true;
    		}
    		statement.close();
    		return false;
    	
    }

}
