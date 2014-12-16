package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class Instructor extends Model {

    @Id
    public int id;

    public String name;
    public String surname;
    public String email;
}
