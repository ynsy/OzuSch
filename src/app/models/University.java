package models;

import play.db.ebean.Model;
import javax.persistence.*;

/**
 * Created by bahadirkirdan on 16/12/14.
 */
public class University extends Model {

    @Id
    public int id;

    public String name;

}
