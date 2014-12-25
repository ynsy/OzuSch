package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.parser.ParseException;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.homePage;
import views.html.offeredCourses;
import models.DatabaseConnector;

public class Application extends Controller {
	public Boolean noProblemForMultiple = true;
	public ArrayList<CourseSection> multipleSectionsResult;
	public ArrayList<Course> multipleSectionCourses;
	public ArrayList<Course> oneSectionCourses;
	public ArrayList<ArrayList<CourseSection>> result;
	public ArrayList<CourseSection> oneSectionsResult;
	public Day[] calendarOneAdded;
	public Boolean noProblemForOne;
	public ArrayList<Course> usrCourseList;
	private static String title = "OzUSch";
	private static String url = routes.Application.index().absoluteURL(request());
	
	
	public static String insertIntoCourses = "INSERT INTO courses (id) VALUES (1);";

			
	public static Result index() throws ClassNotFoundException, SQLException, FileNotFoundException, IOException, ParseException {
		DatabaseConnector.makeConnection();
		models.JSONParser.Main.parseJSON();
		models.JSONParser.Course.addCoursesToDatabase();
		models.JSONParser.CourseInstructor.addInstructorsToDatabase();
		models.JSONParser.LectureInterval.addLectureIntervalsToDatabase();
		
		//return ok(homePage.render("deneme","home"));
	
		return ok(homePage.render(title,"home", url));
		
	}
	public static Result offeredCourses(){
		
		return ok(offeredCourses.render(title,"offeredCourses",url));
	}
	public void startScheduler(ArrayList<Course> usrCourseList){
		Scheduler sch = new Scheduler(usrCourseList);
		this.usrCourseList = usrCourseList;
		if (!this.usrCourseList.isEmpty()){
            setOneSectionCourses();
            setScheduleForOneSections();
            setMultipleSectionCourses();
            
        }
	}
	private void setOneSectionCourses(){
        // Seperate courses which have only one section from the others
        int lastIndex = usrCourseList.size();
        for (int index = 0; index < lastIndex; index++) {
            Course course = usrCourseList.get(index);
            if (course.sections.size() == 1) {
                oneSectionCourses.add(course);
            }else {
                multipleSectionCourses.add(course);
            }
        }
    }
	
	private void setTimeIntervals(int timeIndex, int startIndex, int period, int day, String type, Day[] calendar) {
        //set Week with relevant meetingTime
        if (timeIndex == startIndex) {
            if (calendar[day].oClock.get(startIndex).isStartAvailable == true) {
            	calendar[day].oClock.get(startIndex).isStartAvailable = false;
            }else{
                setNoProblem(type);
            }
        }else if (timeIndex == period) {
            if (calendar[day].oClock.get(period).isEndAvailable == true) {
            	calendar[day].oClock.get(period).isEndAvailable = false;
            }else {
                setNoProblem(type);
            }
        }else {
            if (calendar[day].oClock.get(timeIndex).isStartAvailable == true) {
                if (calendar[day].oClock.get(timeIndex).isEndAvailable == true) {
                	calendar[day].oClock.get(timeIndex).isStartAvailable = false;
                			calendar[day].oClock.get(timeIndex).isEndAvailable = false;
                }else {
                    setNoProblem(type);
                }
            }else {
                setNoProblem(type);
            }
        }
    }
    
    private void lookMeetingTimes(ArrayList<TimePeriod> meetingTimes, String type, Day[] calendar){
        //look meetingTimes for relevant course
        if (meetingTimes.size() > 0) {
            for (int meetingIndex = 0; meetingIndex < meetingTimes.size(); meetingIndex++) {
                if (getNoProblem(type) == true) {
                    int day = meetingTimes.get(meetingIndex).dayIndex;
                    //lessons allways start min at 8 so startHour-8
                    int startIndex = (meetingTimes.get(meetingIndex).startHour)-8;
                    int period = startIndex + meetingTimes.get(meetingIndex).hours;
                    for (int timeIndex = startIndex; startIndex < period; startIndex++) {
                        if (getNoProblem(type) == true && type.equals("OneSection")) {
                            setTimeIntervals(timeIndex, startIndex, period, day, "OneSection", calendar);
                        }else if (getNoProblem(type) == true && type.equals("MultipleSections")){
                            setTimeIntervals(timeIndex, startIndex, period, day, "MultipleSections", calendar);
                        }else {
                        
                        }
                    }
                }else {
                    break;
                }
            }
        }else {
            setNoProblem(type);
        }
    }
    
    private void setScheduleForOneSections(){
        if (!oneSectionCourses.isEmpty()) {
            Day[] calendar = new Week().getWeek();
            for (int index = 0; index < oneSectionCourses.size(); index++) {
                if (noProblemForOne == true) {
                    lookMeetingTimes(oneSectionCourses.get(index).sections.get(0).meetingTimes, "OneSection", calendar);
                    oneSectionsResult.add(oneSectionCourses.get(index).sections.get(0));
                }else {
                    break;
                }
            }
            //there is conflict in oneSectionCourses
            if (noProblemForOne == false) {
                
            }else{
                calendarOneAdded = calendar;
            }
        }
    }
	
	public void setScheduleForMultipleSections(
			ArrayList<Course> multipleSectionCourses, int courseIndex,
			ArrayList<CourseSection> multipleSectionsResult, Day[] newCalendar) {
		if (!multipleSectionCourses.isEmpty()) {
			if (noProblemForMultiple == true) {
				for (int sectionIndex = 0; sectionIndex < multipleSectionCourses
						.get(courseIndex).sections.size(); sectionIndex++) {
					if (courseIndex == 0) {
						if (multipleSectionCourses.get(courseIndex).sections
								.get(sectionIndex).sectionTitle == "FIN202-B") {
							System.out.println("fin202-b");
						}
					}
					Week week = new Week();

					Day[] calendar = week.getCalendar(newCalendar);
					lookMeetingTimes(
							multipleSectionCourses.get(courseIndex).sections
									.get(sectionIndex).meetingTimes,
							"MultipleSections", calendar);

					if (getNoProblem("MultipleSections")) {
						if (multipleSectionCourses.get(courseIndex).sections
								.get(sectionIndex).sectionTitle == "FIN202-B") {
							System.out.println("2");
						}

						ArrayList<CourseSection> nextMultipleSectionsResult = multipleSectionsResult;
						nextMultipleSectionsResult.add(multipleSectionCourses
								.get(courseIndex).sections.get(sectionIndex));
						if (courseIndex + 1 != multipleSectionCourses.size()) {
							setScheduleForMultipleSections(
									multipleSectionCourses, courseIndex + 1,
									nextMultipleSectionsResult, calendar);
						} else {
							result.add(nextMultipleSectionsResult);
							result.add(oneSectionsResult);
						}
					} else {
						if (multipleSectionCourses.get(courseIndex).sections
								.get(sectionIndex).sectionTitle == "FIN202-B") {
							System.out.println("fin202-b");
						}
						noProblemForMultiple = true;
					}
				}
			}
		}
	}

	public void setMultipleSectionCourses() {
        if (noProblemForOne) {
            if (multipleSectionCourses.size() > 0) {
                if (multipleSectionCourses.size() != 1) {
				// multipleSectionCourses.sort()
                }
                Day[] newCalendar;
                if (!oneSectionCourses.isEmpty()) {
                    newCalendar = calendarOneAdded;
                } else {
                    newCalendar = new Week().getWeek();
                }

                setScheduleForMultipleSections(multipleSectionCourses, 0,
					multipleSectionsResult, newCalendar);
            } else {
                result.add(oneSectionsResult);
            }
        }
	}

	public void setNoProblem(String type) {
		if (type.equals("OneSection")) {
			noProblemForOne = false;
		} else {
			noProblemForMultiple = false;
		}
	}

	public boolean getNoProblem(String type) {
		if (type.equals("OneSection")) {
			return noProblemForOne;
		} else {
			return noProblemForMultiple;
		}
	}

	public boolean isOkay() {
		if (!usrCourseList.isEmpty()) {
			if (result.size() > 0) {
				int numOfOneSectCourses = oneSectionCourses.size();
				int numOfMultipleSectCourses = multipleSectionCourses.size();
				if (result.get(0).size() == (numOfOneSectCourses + numOfMultipleSectCourses)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}