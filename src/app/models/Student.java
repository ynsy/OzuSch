package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class Student extends Model{

    @Id
    public String id;

    public String name;
    public String surname;
    public String displayName;
    public String email;

}
