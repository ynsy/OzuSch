package models;

import play.db.ebean.*;
import java.util.List;
import java.util.ArrayList;
import javax.persistence.*;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.config.GlobalProperties;

/**
 * Created by bahadirkirdan on 16/12/14.
 */

@Entity
public class Universities extends Model {

    @Id
    public int id;

    public String name;

    @OneToMany
    public List<Departments> departments = new ArrayList<Departments>();

    public static Model.Finder<Long,Universities> find = new Model.Finder<Long,Universities>(Long.class, Universities.class);


}
