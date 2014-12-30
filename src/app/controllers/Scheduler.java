package controllers;

import java.util.ArrayList;

public class Scheduler {

	public static ArrayList<models.JSONParser.Course> selectedCoourseList = Application.selectedCourse;
	public static ArrayList<Scheduler> schedules = new ArrayList<Scheduler>();

	public String courseName;
	public String courseLocation;
	public String time;
	// public ArrayList<String> Monday = new ArrayList<String>();
	// public ArrayList<String> Tuesday = new ArrayList<String>();
	// public ArrayList<String> Wednesday = new ArrayList<String>();
	// public ArrayList<String> Thursday = new ArrayList<String>();
	// public ArrayList<String> Friday = new ArrayList<String>();

	public String[] Monday = new String[15];
	public String[] Tuesday = new String[15];
	public String[] Wednesday = new String[15];
	public String[] Thursday = new String[15];
	public String[] Friday = new String[15];

	public Scheduler(String courseName, String courseLocation, String time,
			String[] Monday, String[] Tuesday, String[] Wednesday,
			String[] Thursday, String[] Friday) {
		this.courseName = courseName;
		this.courseLocation = courseLocation;
		this.time = time;
		this.Monday = Monday;
		this.Tuesday = Tuesday;
		this.Wednesday = Wednesday;
		this.Thursday = Thursday;
		this.Friday = Friday;

	}

	public static void main(String[] args) {
		for (int i = 0; i < selectedCoourseList.size(); i++) {
			for (int j = i; i < selectedCoourseList.size(); j++) {
				if (selectedCoourseList.get(i).getLectures().get(0).getDay() != selectedCoourseList
						.get(j).getLectures().get(0).getDay()) {
					if (selectedCoourseList.get(i).getLectures().get(0)
							.getDay().equals("Monday")) {
						for (int k = selectedCoourseList.get(i).getLectures()
								.get(0).getStartHour(); k < selectedCoourseList
								.get(i).getLectures().get(0).getFinishHour(); k++) {
							schedules.get(i).Monday[k] = selectedCoourseList
									.get(i).getDisplayName();
						}
					} else if (selectedCoourseList.get(i).getLectures().get(0)
							.getDay().equals("Tuesday")) {
						for (int k = selectedCoourseList.get(i).getLectures()
								.get(0).getStartHour(); k < selectedCoourseList
								.get(i).getLectures().get(0).getFinishHour(); k++) {
							schedules.get(i).Tuesday[k] = selectedCoourseList
									.get(i).getDisplayName();
						}
					} else if (selectedCoourseList.get(i).getLectures().get(0)
							.getDay().equals("Wednesday")) {
						for (int k = selectedCoourseList.get(i).getLectures()
								.get(0).getStartHour(); k < selectedCoourseList
								.get(i).getLectures().get(0).getFinishHour(); k++) {
							schedules.get(i).Wednesday[k] = selectedCoourseList
									.get(i).getDisplayName();
						}
					} else if (selectedCoourseList.get(i).getLectures().get(0)
							.getDay().equals("Thursday")) {
						for (int k = selectedCoourseList.get(i).getLectures()
								.get(0).getStartHour(); k < selectedCoourseList
								.get(i).getLectures().get(0).getFinishHour(); k++) {
							schedules.get(i).Thursday[k] = selectedCoourseList
									.get(i).getDisplayName();
						}
					} else if (selectedCoourseList.get(i).getLectures().get(0)
							.getDay().equals("Friday")) {
						for (int k = selectedCoourseList.get(i).getLectures()
								.get(0).getStartHour(); k < selectedCoourseList
								.get(i).getLectures().get(0).getFinishHour(); k++) {
							schedules.get(i).Friday[k] = selectedCoourseList
									.get(i).getDisplayName();
						}
					}
				}
			}
		}
	}

	public static void takeIntervals(int courseIndex) {
		for (int i = 0; i < selectedCoourseList.get(courseIndex).getLectures()
				.size(); i++) {

		}
	}

}
