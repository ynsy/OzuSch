package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class Student extends Model{

    @Id
    public int id;

    public String name;
    public String surname;
    public String displayName;
    public String email;
    public String password; //It may be a private. Is must be looked.

    @OneToOne
    public int departmentId;

    @OneToOne
    public int schoolId;


}
