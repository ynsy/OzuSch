package models.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.DatabaseConnector;

public class CourseInstructor {

	private int id;
	private int courseId;
	private String name;
	private String surname;
	private Boolean isPrimary;
	public static ArrayList<CourseInstructor> dbCourseInstructorList = new ArrayList<CourseInstructor>();
	public static ResultSet resultSet;
	public static java.sql.PreparedStatement statement;

	private static ArrayList<CourseInstructor> courseInstructors = new ArrayList<CourseInstructor>();

	public CourseInstructor() {

	}

	public CourseInstructor(int id, int courseId, String name, String surname,
			Boolean isPrimary) {
		this.id = id;
		this.courseId = courseId;
		this.name = name;
		this.surname = surname;
		this.isPrimary = isPrimary;

		this.courseInstructors.add(this);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setIsPrimary(Boolean isPrimary) {
		this.isPrimary = isPrimary;
	}

	public int getId() {
		return id;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}

	public ArrayList getCourseInstructors() {
		return courseInstructors;
	}

	public void printAllCourseInstructors() {
		for (CourseInstructor courseInstructor : courseInstructors) {
			System.out.println("id\tname\t\tsurname\t\tis_primary\tcourse_id");
			System.out.println(courseInstructor.getId() + "\t"
					+ courseInstructor.getName() + "\t\t"
					+ courseInstructor.getSurname() + "\t\t"
					+ courseInstructor.getIsPrimary() + "\t\t"
					+ courseInstructor.getCourseId());
		}
	}

	public static void addInstructorsToDatabase() throws SQLException {
		for (CourseInstructor courseInstructor : courseInstructors) {
			DatabaseConnector.statement = DatabaseConnector.connection
					.prepareStatement("INSERT INTO course_instructors (id,name,surname,is_primary,course_id) VALUES ("
							+ courseInstructor.getId()
							+ ",\""
							+ courseInstructor.getName()
							+ "\",\""
							+ courseInstructor.getSurname()
							+ "\","
							+ courseInstructor.getIsPrimary()
							+ ","
							+ courseInstructor.getCourseId() + ");");
			DatabaseConnector.statement.executeUpdate();
			DatabaseConnector.statement.close();

		}
	}

	public static void retrieveCourseInstructorListFromDB() throws SQLException {
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, name, surname, is_primary, course_id FROM courses;");
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			CourseInstructor c = new CourseInstructor();
			c.setId(resultSet.getInt(1));
			c.setName(resultSet.getString(2));
			c.setSurname(resultSet.getString(3));
			c.setIsPrimary((Boolean) resultSet.getBoolean(4));
			c.setCourseId(resultSet.getInt(5));

			dbCourseInstructorList.add(c);
		}
		statement.close();
	}
}
