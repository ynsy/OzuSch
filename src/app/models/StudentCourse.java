package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 16/12/14.
 */
public class StudentCourse extends Model {

    @Id
    public int id;

    @ManyToOne
    public int studentId;

    @ManyToOne
    public int courseId;
}
