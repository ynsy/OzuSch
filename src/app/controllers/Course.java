package controllers;

import java.util.ArrayList;

public class Course {
	public String subjectName;
	public String courseNo;
	public String courseTitle;
	public ArrayList<CourseSection> sections = new ArrayList<CourseSection>();

	public Course(String subjectName, String courseNo) {
		this.courseNo = courseNo;
		this.subjectName = subjectName;
		this.courseTitle = subjectName + courseNo;
	}

	public void addSection(CourseSection section) {
		sections.add(section);
		// sortSection();
	}

	/* !!!! will be sorted !!!!
	 * public void sortSection(){ if (sections.size() > 1) {
	 * Collections.sort(sections); } }
	 */
}