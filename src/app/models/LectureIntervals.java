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

    public Date startHour;
    public Date endHour;
    public String day;
    public String roomCode;

    @OneToOne(mappedBy = "course")
    public int courseId;
}
