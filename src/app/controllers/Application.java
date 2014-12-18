package controllers;

import java.util.ArrayList;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

public class Application extends Controller {
	public static Boolean noProblemForMultiple = true;
	public static ArrayList<CourseSection> multipleSectionsResult;
	public static ArrayList<Course> multipleSectionCourses;
	public static ArrayList<Course> oneSectionCourses;
	public static ArrayList<ArrayList<CourseSection>> result;
	public static ArrayList<CourseSection> oneSectionsResult;
	public static Day[] calendarOneAdded;
	public static Boolean noProblemForOne;
	public static ArrayList<Course> usrCourseList;

	public static Result index() {
		return ok(index.render("OzUSch"));
	}

	public static void setScheduleForMultipleSections(
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

	private static void lookMeetingTimes(ArrayList<TimePeriod> meetingTimes,
			String string, Day[] calendar) {
		// TODO Auto-generated method stub

	}

	public void setMultipleSectionCourses() {
		if (multipleSectionCourses.size() > 0) {
			if (multipleSectionCourses.size() != 1) {
				// multipleSectionCourses.sort()
			}
			Day[] newCalendar;
			if (!oneSectionCourses.isEmpty()) {
				newCalendar = calendarOneAdded;
			} else {
				newCalendar = Week.getWeek();
			}

			setScheduleForMultipleSections(multipleSectionCourses, 0,
					multipleSectionsResult, newCalendar);
		} else {
			result.add(oneSectionsResult);
		}
	}

	public void setNoProblem(String type) {
		if (type.equals("OneSection")) {
			noProblemForOne = false;
		} else {
			noProblemForMultiple = false;
		}
	}

	public static boolean getNoProblem(String type) {
		if (type.equals("OneSection")) {
			return noProblemForOne;
		} else {
			return noProblemForMultiple;
		}
	}

	public static boolean isOkay() {
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