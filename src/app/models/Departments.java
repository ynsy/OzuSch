package models;

import play.db.ebean.Model;
import javax.persistence.*;
import java.util.*;

/**
 * Created by bahadirkirdan on 12/12/14.
 */

@Entity
public class Departments extends Model {

    @Id
    public int id;

    public String name;


    @ManyToOne
    public Universities university;

    @OneToMany
    public List<Instructors> instructors = new ArrayList<Instructors>();

}
