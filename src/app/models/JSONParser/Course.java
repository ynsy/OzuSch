package models.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.DatabaseConnector;

public class Course {

	private int id;
	private String subjectName;
	private String courseNo;
	private String displayName;
	private String sectionNo;
	private ArrayList<CourseInstructor> instructor = null;
	private ArrayList<LectureInterval> lectures = null;
	
	public static ArrayList<Course> dbCourseList = new ArrayList<Course>();

	public static ArrayList<Course> getDbCourseList() {
		return dbCourseList;
	}

	public static ResultSet resultSet;
	public static java.sql.PreparedStatement statement;

	private static ArrayList<Course> courses = new ArrayList<Course>();

	public Course(int id, String subjectName, String courseNo,
			String displayName, String sectionNo) {
		this.id = id;
		this.subjectName = subjectName;
		this.courseNo = courseNo;
		this.displayName = displayName;
		this.sectionNo = sectionNo;

		this.courses.add(this);
	}

	public Course() {

	}

	public void setId(int id) {
		this.id = id;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public void setCourseNo(String courseNo) {
		this.courseNo = courseNo;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setSectionNo(String sectionNo) {
		this.sectionNo = sectionNo;
	}
	
	public void setInstructor(ArrayList<CourseInstructor> instructor){
		this.instructor = instructor;
	}

	public void setLectures(ArrayList<LectureInterval> lectures) {
		this.lectures = lectures;
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

	public ArrayList getCourses() {
		return courses;
	}
	public ArrayList<CourseInstructor> getInstructor(){
		return instructor;
	}
	public ArrayList<LectureInterval> getLectures(){
		return lectures;
	}
	public void printAllCourses() {
		for (Course course : courses) {
			System.out.println("id\tsubject_name\tdisplay_name\tsection_no");
			System.out.println(course.getId() + "\t" + course.getSubjectName()
					+ "\t" + course.getCourseNo() + "\t"
					+ course.getDisplayName() + "\t\t" + course.getSectionNo());
		}
	}

	public static void addCoursesToDatabase() throws SQLException {
		for (Course course : courses) {
			DatabaseConnector.statement = DatabaseConnector.connection
					.prepareStatement("INSERT INTO courses (id,subject_name,course_no,display_name,section_no) VALUES ("
							+ course.getId()
							+ ",\""
							+ course.getSubjectName()
							+ "\",\""
							+ course.getCourseNo()
							+ "\",\""
							+ course.getDisplayName()
							+ "\",\""
							+ course.getSectionNo() + "\");");
			DatabaseConnector.statement.executeUpdate();
			DatabaseConnector.statement.close();

		}
	}

	public static void retrieveCourseListFromDB() throws SQLException {
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, subject_name, course_no, display_name, section_no FROM courses");
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Course c = new Course();
			c.setId(resultSet.getInt(1));
			c.setSubjectName(resultSet.getString(2));
			c.setCourseNo(resultSet.getString(3));
			c.setDisplayName((String) resultSet.getString(4));
			c.setSectionNo((String) resultSet.getString(5));
			c.setInstructor(CourseInstructor.getCourseInstructor(resultSet.getInt(1)));
			c.setLectures(LectureInterval.getLectureInterval(resultSet.getInt(1)));
			// System.out.println(c.getDisplayName());
			dbCourseList.add(c);

			// System.out.println(resultSet.getString(2));
		}
		statement.close();
		resultSet.close();
	}


}
