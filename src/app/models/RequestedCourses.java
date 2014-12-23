package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 16/12/14.
 */

@Entity
public class RequestedCourses extends Model {

    @Id
    public int id;

    @ManyToOne
    public Students student;

    @ManyToOne
    public Courses course;
}