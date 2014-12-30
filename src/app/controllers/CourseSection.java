package controllers;

import java.util.ArrayList;

public class CourseSection {

	public String getSectionTitle() {
		return sectionTitle;
	}

	public String getSectionNo() {
		return sectionNo;
	}

	public String getInstructor() {
		return instructor;
	}

	public ArrayList<TimePeriod> getMeetingTimes() {
		return meetingTimes;
	}

	String sectionTitle;
	String sectionNo;
	String instructor;
	ArrayList<TimePeriod> meetingTimes = new ArrayList<TimePeriod>();

	public CourseSection(String courseTitle, String sectionNo, String instructor) {
		this.sectionNo = sectionNo;
		this.instructor = instructor;
		this.sectionTitle = (courseTitle + "-" + sectionNo);
	}

	public void addTime(TimePeriod time) {
		meetingTimes.add(time);
	}
}
