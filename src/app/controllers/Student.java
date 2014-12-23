package controllers;

import java.util.ArrayList;
import java.util.regex.*;
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
}
