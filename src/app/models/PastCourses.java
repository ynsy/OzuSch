package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class PastCourses extends Model {

    @Id
    public String id;


    @OneToOne
    public Students student;

    @OneToOne
    public Courses course;
}
