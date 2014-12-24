package models.JSONParser;


import java.sql.SQLException;
import java.util.ArrayList;

import models.DatabaseConnector;

public class Course {
	
	private int id;
	private String subjectName;
	private String courseNo;
	private String displayName;
	private String sectionNo;
	
	private static ArrayList<Course> courses = new ArrayList<Course>();
	
	public Course(int id, String subjectName, String courseNo, String displayName, String sectionNo){
		this.id = id;
		this.subjectName = subjectName;
		this.courseNo = courseNo;
		this.displayName = displayName;
		this.sectionNo = sectionNo;
		
		this.courses.add(this);
	}
	
	public Course(){
		
	}

	public int getId() {
		return id;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public String getCourseNo() {
		return courseNo;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getSectionNo() {
		return sectionNo;
	}
	
	public ArrayList getCourses(){
		return courses;
	}
	
	public void printAllCourses(){
		for (Course course : courses) {
			System.out.println("id\tsubject_name\tdisplay_name\tsection_no");
			System.out.println(course.getId() + "\t" + course.getSubjectName() + "\t" + course.getCourseNo() + "\t" + course.getDisplayName() + "\t\t" + course.getSectionNo());
		}
	}
	
	public static void goToDb() throws SQLException {
		for(Course course : courses) {
			DatabaseConnector.statement = DatabaseConnector.connection.prepareStatement("INSERT INTO courses (id,subject_name,course_no,display_name,section_no) VALUES ("
			+ course.getId() + ",\"" + course.getSubjectName() + "\",\"" + course.getCourseNo() + "\",\"" + course.getDisplayName() + "\",\"" + course.getSectionNo() + "\");");
			DatabaseConnector.statement.executeUpdate();

		}
	}

}
