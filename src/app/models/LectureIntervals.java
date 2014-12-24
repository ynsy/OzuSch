package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.Date;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class LectureIntervals extends Model {

    @Id
    public int id;

    public int startHour;
    public int startMinute;
    public int endHour;
    public int endMinute;
    public String day;
    public String roomCode;


    @ManyToOne
    public Courses course;

}
