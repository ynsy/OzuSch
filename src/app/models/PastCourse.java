package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class PastCourse extends Model {

    @Id
    public String id;

    @OneToOne
    public String studentId;

    @OneToOne
    public String courseId;
}
