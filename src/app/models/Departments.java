package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class Departments extends Model {

    @Id
    public int id;

    public String name;

    @OneToOne(mappedBy = "university")
    public int universityId;

}
