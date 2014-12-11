package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class Section extends Model {

    @Id
    public String id;

    public String courseId;
    public String sectionLetter;
    public String instructorId;

}
