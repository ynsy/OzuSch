package models;

import play.db.ebean.Model;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class Students extends Model{

    @Id
    public int id;

    public String name;
    public String surname;
    public String displayName;
    public String username;
    public String email;
    public String password; //It may be a private. Is must be looked.

    /*
    @OneToOne
    public Departments department;
	*/

    @OneToOne
    public Universities university;
    
    public static Finder<String,Students> findStudents = new Finder(String.class, Students.class);

    public static void create(Students student) {
    	  student.save();
    }
    
    public Boolean isUsernameValid(String username){
    	String studentUsername = findStudents.ref(username).username;
    	
    	if( !(studentUsername.isEmpty()) ){
    		return true;
    	}
    	
    	return false;
    }
    
    public Boolean isEmailValid(String email){
    	String studentEmail = findStudents.ref(username).email;
    	
    	if( !(studentEmail.isEmpty()) ){
    		return true;
    	}
    	
    	return false;
    }

}
