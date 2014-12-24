package models;

import play.db.ebean.Model;
import java.util.List;
import java.util.ArrayList;
import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class CourseInstructors extends Model {

    @Id
    public int id;

    public String name;
    public String surname;
    public boolean isPrimary;

    @ManyToOne
    public Courses course;
}
