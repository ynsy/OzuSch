package controllers;

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

import models.Departments;
import models.Universities;
import models.security.PasswordEncryption;

public class Student {
	public String name;
	public String surname;
	public String displayName;
	public String email;
	public String password;
	public Departments department;
	public Universities university;
	public ArrayList<Student> studentsList;
	public Course courseTitle;
	public ArrayList<Course> passedCourseList;
	public ArrayList<Course> userCourses;
	public static Pattern pattern;
	public static Matcher matcher;
	public static String registerMailInfo = "\n Welcome to OzuSch \n We are glad to see you here. You can do your schedule with OzuSch. \n Best regards.";

	public Student(String name, String surname, String displayName,
			String email, String password, Departments department,
			Universities university) {
		this.name = name;
		this.surname = surname;
		this.displayName = displayName;
		this.email = email;
		this.password = password;
		this.department = department;
		this.university = university;
	}

	public void register(String name, String surname, String displayName,
			String email, String password, Departments department,
			Universities university) throws Exception {
		password = PasswordEncryption.mixPassword(password);
		Student std = new Student(name, surname, displayName, email, password,
				department, university);
		studentsList.add(std);

		sendMail(email, "Hi " + name + registerMailInfo);
	}

	public Boolean signUp(String displayName, String password) throws Exception {
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
		for (int i = 0; i < studentsList.size(); i++) {
			Student st = studentsList.get(i);
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

			System.out.println("Mail Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
