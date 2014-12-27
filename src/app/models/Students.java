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
    public String email;
    public String password; //It may be a private. Is must be looked.

    /*
    @OneToOne
    public Departments department;
	*/

    @OneToOne
    public Universities university;  

    public static void create(Students student) {
    	  student.save();
    }

}
