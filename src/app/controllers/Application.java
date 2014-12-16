package controllers;

import models.Day;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	Day[] week = new Day[5];

    public static Result index() {
        return ok(index.render("HEYE"));
    }
    
    public void Week(){
		setWeek();
	}
	
	public Day[] getWeek() {
		return week;
	}
	
	private void setWeek(){
		for(int index = 0; index < 5; index++){
			if (index == 0) {
				week[index] = Day("Monday");
			}else if (index == 1) {
				week[index] = Day("Tuesday");
			}else if (index == 2) {
				week[index] = Day("Wednesday");
			}else if (index == 3) {
				week[index] = Day("Thursday");
			}else {
				week[index] = Day("Friday");
			}
		}
	}
	
	public Day[] getCalendar(Day[] calendar) {
		Week();
		Day[] cal = getWeek();
        for (int day = 0; day < cal.length; day++) {
            for (int timeIndex = 0; timeIndex < calendar[day].oClock.count; timeIndex++) {
                cal[day].oClock[timeIndex].isStartAvailable = calendar[day].oClock[timeIndex].isStartAvailable;
                cal[day].oClock[timeIndex].isEndAvailable = calendar[day].oClock[timeIndex].isEndAvailable;
            }
        }
        return cal;
    }
	
	public Day Day(String name){
		Day day = new Day();
        day.name = name;
        //start from 8 am, end at 10:59 pm (15 hours)
        for (int index = 0; index < 15; index++ ){
            day.oClock.add(Oclock());
        }
        return day;
    }
	
	


}
