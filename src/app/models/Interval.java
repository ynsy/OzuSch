package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class Interval extends Model {

    @Id
    public int id;

    public Date startHour;
    public Date endHour;
    public String day;
    public String roomCode;

    @OneToOne
    public int courseId;
}
