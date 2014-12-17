package controllers;

import java.util.ArrayList;

public class CourseSection {

	String sectionTitle;
	String sectionNo;
	String instructor;
	ArrayList<TimePeriod> meetingTimes;

	public CourseSection(String courseTitle, String sectionNo, String instructor) {
		this.sectionNo = sectionNo;
		this.instructor = instructor;
		this.sectionTitle = (courseTitle + "-" + sectionNo);
	}

	public void addTime(TimePeriod time) {
		meetingTimes.add(time);
	}
}
