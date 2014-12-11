package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class Interval extends Model {

    @Id
    public String id;

    public String sectionId;
    public Date startTime;
    public Date endTime;
    public String day;
}
