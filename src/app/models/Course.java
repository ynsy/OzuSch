package models;

import play.db.ebean.Model;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class Course extends Model {

    @Id
    public int id;

    public String subjectName; //CS
    public int courseNo;       //201
    public String displayName; //It is just a display name information like "CS-201"
    public String section;  // "A" or "B"

}
