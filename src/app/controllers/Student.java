package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import models.CourseInstructors;
import models.DatabaseConnector;
import models.Departments;
import models.Universities;
import models.JSONParser.Course;
import models.JSONParser.CourseInstructor;
import models.JSONParser.LectureInterval;
import models.security.PasswordEncryption;

public class Student {
	public static int id = 0;
	public String name;
	public String surname;
	public String displayName;
	public String email;
	public String password;
	public static ArrayList<Student> dbStudentsList = new ArrayList<Student>();
	public Course courseTitle;
	public ArrayList<Course> passedCourseList = new ArrayList<Course>();
	public static ArrayList<Course> userCourses = new ArrayList<Course>();
	public ArrayList<Course> eliminatedCourses = new ArrayList<Course>();
	public static Pattern pattern;
	public static Matcher matcher;
	public static String registerMailInfo = "\n Welcome to OzuSch \n We are glad to see you here. You can do your schedule with OzuSch. \n Best regards.";
	public static ResultSet resultSet;
	public static java.sql.PreparedStatement statement;

	public Student(String name, String surname, String displayName,
			String email, String password) {
		this.name = name;
		this.surname = surname;
		this.displayName = displayName;
		this.email = email;
		this.password = password;
	}

	// user selected courses retrieving information from DB
	public static Course selectedCourses(int id) throws SQLException,
			ClassNotFoundException {
		DatabaseConnector.makeConnection();
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT c.id, c.subject_name, c.course_no, c.display_name, c.section_no, ci.id, ci.name, ci.surname, li.id, li.start_hour, li.start_minute, li.end_hour, li.end_minute, li.day, li.room_code  FROM courses as c left outer join course_instructors as ci on c.id = ci.course_id left outer join lecture_intervals as li on c.id = li.course_id where c.id = "
						+ id + ";");
		resultSet = statement.executeQuery();

		Course course = null;
		ArrayList<CourseInstructor> courseInstructor = new ArrayList<CourseInstructor>();
		ArrayList<LectureInterval> lectureIntervals = new ArrayList<LectureInterval>();

		while (resultSet.next()) {
			course = new Course(resultSet.getInt(1), resultSet.getString(2),
					resultSet.getString(3), resultSet.getString(4),
					resultSet.getString(5));

			CourseInstructor ins = new CourseInstructor(resultSet.getInt(6),
					resultSet.getInt(1), resultSet.getString(7),
					resultSet.getString(8), resultSet.getBoolean(9));

			courseInstructor.add(ins);
			course.setInstructor(courseInstructor);

			LectureInterval lInterval = new LectureInterval(
					resultSet.getInt(9), resultSet.getInt(1),
					resultSet.getInt(10), resultSet.getInt(11),
					resultSet.getInt(12), resultSet.getInt(13),
					resultSet.getString(14), resultSet.getString(15));

			lectureIntervals.add(lInterval);
			course.setLectures(lectureIntervals);

		}

		statement.close();
		resultSet.close();

		return course;
	}

	public ArrayList<Course> passedCourses(int id) throws SQLException {
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, subject_name, course_no, display_name, section_no FROM courses where id = "
						+ id + ";");
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Course c = new Course(resultSet.getInt(1), resultSet.getString(2),
					resultSet.getString(3), resultSet.getString(4),
					resultSet.getString(5));
			userCourses.add(c);
		}
		statement.close();
		resultSet.close();

		return passedCourseList;
	}

	public ArrayList<Course> eliminateCourses() {
		eliminatedCourses = models.JSONParser.Course.getDbCourseList();
		eliminatedCourses.removeAll(passedCourseList);

		return eliminatedCourses;
	}

	public void register(String name, String surname, String displayName,
			String email, String password, int university) throws Exception {
		password = PasswordEncryption.mixPassword(password);
		Student std = new Student(name, surname, displayName, email, password);
		// studentsList.add(std);
		addStudentToDatabase(name, surname, displayName, email, password);
		sendMail(email, "Hi " + name + registerMailInfo);
	}

	public static Boolean login(String displayName, String password)
			throws Exception {
		retrieveStudentListFromDB();
		Student st = checkstdnt(displayName);
		
		if (st != null) {
			if (st.displayName.equals(displayName)
					&& st.password.equals(password)) {
				return true;
			}
		}
		return false;
	}

	// public static Student checkStudent(String displayName) throws
	// ClassNotFoundException, SQLException {
	// Student std = checkstdnt(displayName);
	// for (int i = 0; i < dbStudentsList.size(); i++) {
	// Student st = dbStudentsList.get(i);
	// if (st.displayName == displayName) {
	// return st;
	// }
	// }
	// return null;
	// }

	public static Student checkstdnt(String display_name) throws SQLException,
			ClassNotFoundException {
		DatabaseConnector.makeConnection();
		Student std = null;
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, name, surname, display_name, email, password FROM students where display_name = \""
						+ display_name + "\";");
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			std = new Student(resultSet.getString(2), resultSet.getString(3),
					resultSet.getString(4), resultSet.getString(5),
					resultSet.getString(6));

		}
		statement.close();
		resultSet.close();
		return std;
	}

	public void addPassedCourses(Course courseTitle) {
		passedCourseList.add(courseTitle);
	}

	public ArrayList<Course> getPassedCourseList() {
		return passedCourseList;
	}

	public ArrayList<Course> eliminateCourse() {
		userCourses.removeAll((ArrayList<Course>) getPassedCourseList());
		return userCourses;
	}

	public static boolean checkPasswordSatisfaction(String password) {
		if (password.length() > 6) {
			pattern = Pattern.compile("[a-zA-Z0-9]*");
			matcher = pattern.matcher(password);
			return matcher.lookingAt();
		} else {
			System.out.println("Password must be longer than 6 character!");
			return false;
		}
	}

	public static void sendMail(String recipient, String notification) {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class",
				"javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(
								"ozuscheduler@gmail.com", "*ozusch2014cs320*");
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("ozuscheduler@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(recipient));
			message.setSubject("OzuSch Notification");
			message.setText(notification);

			Transport.send(message);

			System.out.println("Mail Sending Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	public static void addUniversityToDatabase() throws SQLException {
		Universities university = new Universities();

		university.id = 1;
		university.name = "Ozyegin University";

		university.create(university);

		// DatabaseConnector.statement = DatabaseConnector.connection
		// .prepareStatement("INSERT INTO universities (id,name) VALUES (1,\"Ozyegin\"");
		// DatabaseConnector.statement.executeUpdate();
		// DatabaseConnector.statement.close();
	}

	public static void addStudentToDatabase(String name, String surname,
			String displayName, String email, String password)
			throws SQLException {

		DatabaseConnector.statement = DatabaseConnector.connection
				.prepareStatement("INSERT INTO students (id,name,surname,display_name,email, password, university_id) VALUES ("
						+ id
						+ ",\""
						+ name
						+ "\",\""
						+ surname
						+ "\",\""
						+ displayName
						+ "\",\""
						+ email
						+ "\",\""
						+ password
						+ "\"," + 1 + ");");
		DatabaseConnector.statement.executeUpdate();
		DatabaseConnector.statement.close();
	}

	public static void retrieveStudentListFromDB() throws SQLException,
			ClassNotFoundException {
		DatabaseConnector.makeConnection();
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, name, surname, display_name, email, password FROM students");
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			Student std = new Student(resultSet.getString(2),
					resultSet.getString(3), resultSet.getString(4),
					resultSet.getString(5), resultSet.getString(6));
			dbStudentsList.add(std);
		}
		statement.close();
		resultSet.close();
	}

}
