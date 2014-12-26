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
	public ArrayList<Course> passedCourseList;
	public ArrayList<Course> userCourses;
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

	public void register(String name, String surname, String displayName,
			String email, String password, int university) throws Exception {
		password = PasswordEncryption.mixPassword(password);
		Student std = new Student(name, surname, displayName, email, password);
		// studentsList.add(std);
		addStudentToDatabase(name, surname, displayName, email, password);
		sendMail(email, "Hi " + name + registerMailInfo);
	}

	public Boolean login(String displayName, String password) throws Exception {
		Student st = checkStudent(displayName);
		if (st != null) {
			if (st.displayName == displayName
					&& st.password == PasswordEncryption.mixPassword(password)) {
				return true;
			}
		}
		return false;
	}

	public Student checkStudent(String displayName) {
		for (int i = 0; i < dbStudentsList.size(); i++) {
			Student st = dbStudentsList.get(i);
			if (st.displayName == displayName) {
				return st;
			}
		}
		return null;
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

	public boolean checkPasswordSatisfaction(String password) {
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

	public static void retrieveStudentListFromDB() throws SQLException {
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, name, surname, display_name, email, password, unviersity_id FROM students");
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
