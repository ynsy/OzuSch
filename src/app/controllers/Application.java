package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import models.Courses;
import models.DatabaseConnector;
import models.Students;
import models.Universities;
import models.JSONParser.LectureInterval;
import models.security.PasswordEncryption;

import org.json.simple.parser.ParseException;

import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.homePage;
import views.html.loginPage;
import views.html.offeredCourses;
import views.html.selectedCourses;
import views.html.signUpPage;
import views.html.scheduler;

public class Application extends Controller {
	public Boolean noProblemForMultiple = true;
	public ArrayList<CourseSection> multipleSectionsResult;
	public ArrayList<Course> multipleSectionCourses;
	public ArrayList<Course> oneSectionCourses;
	public ArrayList<ArrayList<CourseSection>> result;
	public ArrayList<CourseSection> oneSectionsResult;
	public static ArrayList<String> studentInformation = new ArrayList<String>();
	public static ArrayList<Student> dbStudentsList = new ArrayList<Student>();
	public Day[] calendarOneAdded;
	public Boolean noProblemForOne;
	public static ArrayList<Course> usrCourseList = new ArrayList<Course>();
	public static ArrayList<models.JSONParser.Course> courseList;
	private static String title = "OzUSch";
	private static String url = routes.Application.index().absoluteURL(
			request());
	public static ResultSet resultSet;
	public static PreparedStatement statement;
	public static ArrayList<models.JSONParser.Course> selectedCourse = new ArrayList<models.JSONParser.Course>();
	public static String insertIntoCourses = "INSERT INTO courses (id) VALUES (1);";

	public static Result index() throws ClassNotFoundException, SQLException,
			FileNotFoundException, IOException, ParseException {

		// models.JSONParser.Main.parseJSON();
		// models.JSONParser.Course.addCoursesToDatabase();
		// models.JSONParser.CourseInstructor.addInstructorsToDatabase();
		// models.JSONParser.LectureInterval.addLectureIntervalsToDatabase();

		// return ok(homePage.render("deneme","home"));

		// After first connection please comment below line.

		// Student.addUniversityToDatabase();

		// retrieveStudentInformationFromDB();
		// selectedCourses();
		return ok(homePage.render(title, "home", url));

	}

	public static void retrieveStudentInformationFromDB() throws SQLException,
			ClassNotFoundException {
		DatabaseConnector.makeConnection();
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT display_name, email FROM students");
		resultSet = statement.executeQuery();

		ResultSetMetaData rsmd = resultSet.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (resultSet.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				String columnValue = resultSet.getString(i);
				// System.out.print(columnValue);
				studentInformation.add(columnValue);
			}

		}
		System.out.println(studentInformation);
		statement.close();
		resultSet.close();
	}

	public static Result offeredCourses() throws ClassNotFoundException,
			SQLException, FileNotFoundException, IOException, ParseException {

		DatabaseConnector.makeConnection();
		models.JSONParser.Course.retrieveCourseListFromDB();
		courseList = models.JSONParser.Course.getDbCourseList();

		return ok(offeredCourses.render(title, "offeredCourses", url,
				courseList));
	}

	static Form<Courses> courseForm = Form.form(Courses.class);

	public static Result selectedCourses() throws ClassNotFoundException,
			SQLException, FileNotFoundException, IOException, ParseException {

		DatabaseConnector.makeConnection();
		models.JSONParser.Course.retrieveCourseListFromDB();
		Form<Courses> filledForm = courseForm.bindFromRequest();

		courseList = models.JSONParser.Course.getDbCourseList();

		String value = "";

		final Set<Map.Entry<String, String[]>> entries = request()
				.queryString().entrySet();

		for (Map.Entry<String, String[]> entry : entries) {
			final String key = entry.getKey();
			value = Arrays.toString(entry.getValue());
		}
		value = value.substring(1);
		value = value.substring(0, value.length() - 1);

		StringTokenizer tokenizer = new StringTokenizer(value, ",");

		while (tokenizer.hasMoreTokens()) {
			int token = Integer.parseInt(tokenizer.nextToken().trim());
			System.out.println("TOKEN:" + token);
			selectedCourse.add(controllers.Student.selectedCourses(token));
		}

		return ok(selectedCourses.render(title, "selectedCourses", url,
				selectedCourse));
	}

	public static Result scheduler() throws ClassNotFoundException,
			SQLException {

		DatabaseConnector.makeConnection();
		String value = "";
		ArrayList<models.JSONParser.Course> selectedCourse = new ArrayList<models.JSONParser.Course>();
		ArrayList<models.JSONParser.Course> finalCourses = new ArrayList<models.JSONParser.Course>();

		final Set<Map.Entry<String, String[]>> entries = request()
				.queryString().entrySet();

		for (Map.Entry<String, String[]> entry : entries) {
			final String key = entry.getKey();
			value = Arrays.toString(entry.getValue());
		}
		value = value.substring(1);
		value = value.substring(0, value.length() - 1);

		StringTokenizer tokenizer = new StringTokenizer(value, ",");

		while (tokenizer.hasMoreTokens()) {
			int token = Integer.parseInt(tokenizer.nextToken().trim());
			selectedCourse.add(controllers.Student.selectedCourses(token));
		}

		// for (models.JSONParser.Course course : selectedCourse) {
		// ArrayList<LectureInterval> lectureIntervals = new
		// ArrayList<LectureInterval>();
		// lectureIntervals = course.getLectures();
		//
		// int startHour = 0;
		// int startMinute = 0;
		// int finishHour = 0;
		// int finishMinute = 0;
		// String day = "";
		//
		// if(finalCourses.isEmpty()){
		// finalCourses.add(course);
		// }else{
		// for (LectureInterval lectureInterval : lectureIntervals) {
		// startHour = lectureInterval.getStartHour();
		// startMinute = lectureInterval.getStartMinute();
		// finishHour = lectureInterval.getFinishHour();
		// finishMinute = lectureInterval.getFinishMinute();
		// day = lectureInterval.getDay();
		// }
		// for (models.JSONParser.Course finalCourse : finalCourses) {
		// ArrayList<LectureInterval> finalLectureInvervals = new
		// ArrayList<LectureInterval>();
		// finalLectureInvervals = finalCourse.getLectures();
		//
		// for (LectureInterval finalLectureInverval : finalLectureInvervals) {
		// int finalStartHour = finalLectureInverval.getStartHour();
		// int finalStartMinute = finalLectureInverval.getStartMinute();
		// int finalFinishHour = finalLectureInverval.getFinishHour();
		// int finalFinishMinute = finalLectureInverval.getFinishMinute();
		// String finalDay = finalLectureInverval.getDay();
		//
		// if(day.equals(finalDay)){
		// if()
		// }else{
		// finalCourses.
		// }
		//
		// }
		// }
		// }
		// }

		return ok(scheduler.render(title, "selectedCourses", url, finalCourses));
	}

	static Form<Students> taskForm = Form.form(Students.class);

	public static Result signUp() {

		return ok(signUpPage.render(title, "signUp", url,
				"This is sign-up page. Please register to system.", taskForm));
	}

	public static Result login() {
		return ok(loginPage.render(title, "login", url,
				"This is login page. Please login to system."));
	}

	public static Result newStudent() throws Exception {
		Form<Students> filledForm = taskForm.bindFromRequest();
		String username = filledForm.data().get("username");
		String password = filledForm.data().get("password");
		String cpassword = filledForm.data().get("cpassword");
		String email = filledForm.data().get("email");
		String cemail = filledForm.data().get("cemail");
		String message = "";
		if (email.contains(cemail) && password.contains(cpassword)) {

			if (!Students.isEmailValid(email)
					&& !Students.isUsernameValid(username)) {

				// Universities university = new Universities();
				// university.id = 1;
				// university.name = "Ozyegin University";
				//
				// university.create(university);
				//
				//
				Students student = new Students();
				student.display_name = username;
				student.password = password;
				student.email = email;
				student.university = new Universities();
				student.university.id = 1;
				student.university.name = "Ozyegin";

				if (Student.checkPasswordSatisfaction(password)) {
					password = PasswordEncryption.mixPassword(password);
					Student.addStudentToDatabase("", "", username, email,
							password);
					message = "Successful";
					controllers.Student.sendMail(email,
							Student.registerMailInfo);
				} else {
					message = "Your password is weak";
				}

			} else {
				message = "Your username or email is used";
			}

		} else {
			message = "Please enter correct values.";
		}

		Boolean validEmail = Students.isEmailValid(email);

		return ok(signUpPage.render(title, "signUp", url, "mesaj: " + message,
				taskForm));
	}

	public void startScheduler(ArrayList<Course> usrCourseList) {
		Scheduler sch = new Scheduler(usrCourseList);
		this.usrCourseList = usrCourseList;
		if (!this.usrCourseList.isEmpty()) {
			setOneSectionCourses();
			setScheduleForOneSections();
			setMultipleSectionCourses();
		}
	}

	private void setOneSectionCourses() {
		// Seperate courses which have only one section from the others
		int lastIndex = usrCourseList.size();
		for (int index = 0; index < lastIndex; index++) {
			Course course = usrCourseList.get(index);
			if (course.sections.size() == 1) {
				oneSectionCourses.add(course);
			} else {
				multipleSectionCourses.add(course);
			}
		}
	}

	private void setTimeIntervals(int timeIndex, int startIndex, int period,
			int day, String type, Day[] calendar) {
		// set Week with relevant meetingTime
		if (timeIndex == startIndex) {
			if (calendar[day].oClock.get(startIndex).isStartAvailable == true) {
				calendar[day].oClock.get(startIndex).isStartAvailable = false;
			} else {
				setNoProblem(type);
			}
		} else if (timeIndex == period) {
			if (calendar[day].oClock.get(period).isEndAvailable == true) {
				calendar[day].oClock.get(period).isEndAvailable = false;
			} else {
				setNoProblem(type);
			}
		} else {
			if (calendar[day].oClock.get(timeIndex).isStartAvailable == true) {
				if (calendar[day].oClock.get(timeIndex).isEndAvailable == true) {
					calendar[day].oClock.get(timeIndex).isStartAvailable = false;
					calendar[day].oClock.get(timeIndex).isEndAvailable = false;
				} else {
					setNoProblem(type);
				}
			} else {
				setNoProblem(type);
			}
		}
	}

	private void lookMeetingTimes(ArrayList<TimePeriod> meetingTimes,
			String type, Day[] calendar) {
		// look meetingTimes for relevant course
		if (meetingTimes.size() > 0) {
			for (int meetingIndex = 0; meetingIndex < meetingTimes.size(); meetingIndex++) {
				if (getNoProblem(type) == true) {
					int day = meetingTimes.get(meetingIndex).dayIndex;
					// lessons allways start min at 8 so startHour-8
					int startIndex = (meetingTimes.get(meetingIndex).startHour) - 8;
					int period = startIndex
							+ meetingTimes.get(meetingIndex).hours;
					for (int timeIndex = startIndex; startIndex < period; startIndex++) {
						if (getNoProblem(type) == true
								&& type.equals("OneSection")) {
							setTimeIntervals(timeIndex, startIndex, period,
									day, "OneSection", calendar);
						} else if (getNoProblem(type) == true
								&& type.equals("MultipleSections")) {
							setTimeIntervals(timeIndex, startIndex, period,
									day, "MultipleSections", calendar);
						} else {

						}
					}
				} else {
					break;
				}
			}
		} else {
			setNoProblem(type);
		}
	}

	private void setScheduleForOneSections() {
		if (!oneSectionCourses.isEmpty()) {
			Day[] calendar = new Week().getWeek();
			for (int index = 0; index < oneSectionCourses.size(); index++) {
				if (noProblemForOne == true) {
					lookMeetingTimes(
							oneSectionCourses.get(index).sections.get(0).meetingTimes,
							"OneSection", calendar);
					oneSectionsResult.add(oneSectionCourses.get(index).sections
							.get(0));
				} else {
					break;
				}
			}
			// there is conflict in oneSectionCourses
			if (noProblemForOne == false) {

			} else {
				calendarOneAdded = calendar;
			}
		}
	}

	public void setScheduleForMultipleSections(
			ArrayList<Course> multipleSectionCourses, int courseIndex,
			ArrayList<CourseSection> multipleSectionsResult, Day[] newCalendar) {
		if (!multipleSectionCourses.isEmpty()) {
			if (noProblemForMultiple == true) {
				for (int sectionIndex = 0; sectionIndex < multipleSectionCourses
						.get(courseIndex).sections.size(); sectionIndex++) {
					if (courseIndex == 0) {
						if (multipleSectionCourses.get(courseIndex).sections
								.get(sectionIndex).sectionTitle == "FIN202-B") {
							System.out.println("fin202-b");
						}
					}
					Week week = new Week();

					Day[] calendar = week.getCalendar(newCalendar);
					lookMeetingTimes(
							multipleSectionCourses.get(courseIndex).sections
									.get(sectionIndex).meetingTimes,
							"MultipleSections", calendar);

					if (getNoProblem("MultipleSections")) {
						if (multipleSectionCourses.get(courseIndex).sections
								.get(sectionIndex).sectionTitle == "FIN202-B") {
							System.out.println("2");
						}

						ArrayList<CourseSection> nextMultipleSectionsResult = multipleSectionsResult;
						nextMultipleSectionsResult.add(multipleSectionCourses
								.get(courseIndex).sections.get(sectionIndex));
						if (courseIndex + 1 != multipleSectionCourses.size()) {
							setScheduleForMultipleSections(
									multipleSectionCourses, courseIndex + 1,
									nextMultipleSectionsResult, calendar);
						} else {
							result.add(nextMultipleSectionsResult);
							result.add(oneSectionsResult);
						}
					} else {
						if (multipleSectionCourses.get(courseIndex).sections
								.get(sectionIndex).sectionTitle == "FIN202-B") {
							System.out.println("fin202-b");
						}
						noProblemForMultiple = true;
					}
				}
			}
		}
	}

	public void setMultipleSectionCourses() {
		if (noProblemForOne) {
			if (multipleSectionCourses.size() > 0) {
				if (multipleSectionCourses.size() != 1) {
					// multipleSectionCourses.sort()
				}
				Day[] newCalendar;
				if (!oneSectionCourses.isEmpty()) {
					newCalendar = calendarOneAdded;
				} else {
					newCalendar = new Week().getWeek();
				}

				setScheduleForMultipleSections(multipleSectionCourses, 0,
						multipleSectionsResult, newCalendar);
			} else {
				result.add(oneSectionsResult);
			}
		}
	}

	public void setNoProblem(String type) {
		if (type.equals("OneSection")) {
			noProblemForOne = false;
		} else {
			noProblemForMultiple = false;
		}
	}

	public boolean getNoProblem(String type) {
		if (type.equals("OneSection")) {
			return noProblemForOne;
		} else {
			return noProblemForMultiple;
		}
	}

	public boolean isOkay() {
		if (!usrCourseList.isEmpty()) {
			if (result.size() > 0) {
				int numOfOneSectCourses = oneSectionCourses.size();
				int numOfMultipleSectCourses = multipleSectionCourses.size();
				if (result.get(0).size() == (numOfOneSectCourses + numOfMultipleSectCourses)) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}
}