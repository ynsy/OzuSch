package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */
public class Department extends Model {

    @Id
    public int id;

    public String name;

    @OneToOne
    public int universityId;

}
