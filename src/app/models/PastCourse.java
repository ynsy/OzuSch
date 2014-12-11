package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class PastCourse extends Model {

    @Id
    public String id;

    @OneToOne(cascade = CascadeType.ALL)
    public String studentId;

    @OneToOne(cascade = CascadeType.ALL)
    public String courseId;
}
