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
import java.util.Random;
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
	public static Boolean noProblemForMultiple = true;
	public static ArrayList<CourseSection> multipleSectionsResult = new ArrayList<CourseSection>();
	public static ArrayList<Course> multipleSectionCourses = new ArrayList<Course>();
	public static ArrayList<Course> oneSectionCourses = new ArrayList<Course>();
	public static ArrayList<ArrayList<CourseSection>> result = new ArrayList<ArrayList<CourseSection>>();
	public static ArrayList<CourseSection> oneSectionsResult = new ArrayList<CourseSection>();
	public static ArrayList<String> studentInformation = new ArrayList<String>();
	public static ArrayList<Student> dbStudentsList = new ArrayList<Student>();
	public static Day[] calendarOneAdded;
	public static Boolean noProblemForOne = true;
	public static ArrayList<Course> usrCourseList = new ArrayList<Course>();
	public static ArrayList<models.JSONParser.Course> courseList = new ArrayList<models.JSONParser.Course>();
	private static String title = "OzUSch";
	private static String url = routes.Application.index().absoluteURL(
			request());
	public static ResultSet resultSet;
	public static PreparedStatement statement;
	public static ArrayList<models.JSONParser.Course> selectedCourse = new ArrayList<models.JSONParser.Course>();
	public static String insertIntoCourses = "INSERT INTO courses (id) VALUES (1);";

	public static Result index() throws ClassNotFoundException, SQLException,
	FileNotFoundException, IOException, ParseException {

		//		 models.JSONParser.Main.parseJSON();
		//		 models.JSONParser.Course.addCoursesToDatabase();
		//		 models.JSONParser.CourseInstructor.addInstructorsToDatabase();
		//		 models.JSONParser.LectureInterval.addLectureIntervalsToDatabase();

		// return ok(homePage.render("deneme","home"));

		// After first connection please comment below line.

		//	 Student.addUniversityToDatabase();

		//		Student.addUniversityToDatabase();

		String user = session("isLoggedIn");
		String username = session("username");
		if (user != null) {
			return ok(homePage.render(title, "home", url, true, username));
		} else {

			CreationCaptchaImage ci = new CreationCaptchaImage();
			ci.createCaptcha();

			session("captcha", ci.getCaptchaValue());
			return ok(homePage.render(title, "home", url, false, username));
		}

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

		String user = session("isLoggedIn");

		DatabaseConnector.makeConnection();
		models.JSONParser.Course.retrieveCourseListFromDB();
		courseList = models.JSONParser.Course.getDbCourseList();

		if (user != null) {
			return ok(offeredCourses.render(title, "offeredCourses", url,
					courseList, true));
		} else {
			return redirect("/login");
		}

	}

	static Form<Courses> courseForm = Form.form(Courses.class);

	public static Result selectedCourses() throws ClassNotFoundException,
	SQLException, FileNotFoundException, IOException, ParseException {

		String user = session("isLoggedIn");

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

		if (user != null) {
			return ok(selectedCourses.render(title, "selectedCourses", url,
					selectedCourse, true));
		} else {
			return redirect("/login");
		}

	}

	public static Result scheduler() throws ClassNotFoundException,
	SQLException {

		String user = session("isLoggedIn");

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


		startScheduler();

		System.out.println("RESUlT:" + result);


		if(user != null) {
			return ok(scheduler.render(title, "scheduler", url, result, true));
		} else {
			return redirect("/login");
		}

	}

	static Form<Students> taskForm = Form.form(Students.class);

	public static Result signUp() {
		CreationCaptchaImage ci = new CreationCaptchaImage();
		ci.createCaptcha();

		session("captcha", ci.getCaptchaValue());
		return ok(signUpPage.render(title, "signUp", url,
				"This is sign-up page. Please register to system.", taskForm,
				false));
	}

	public static Result logout() {
		session().clear();
		return redirect("/");
	}

	static Form<Students> loginForm = Form.form(Students.class);

	public static Result login() throws Exception {


		CreationCaptchaImage ci = new CreationCaptchaImage();
		ci.createCaptcha();

		session("captcha", ci.getCaptchaValue());
		Form<Students> filledForm = loginForm.bindFromRequest();
		String username = filledForm.data().get("username");
		String password = filledForm.data().get("password");

		return ok(loginPage.render(title, "login", url, "Please login", false));
	}

	public static Result loginStudent() throws Exception {

		Form<Students> filledForm = loginForm.bindFromRequest();
		String username = filledForm.data().get("username");
		String password = filledForm.data().get("password");
		String captcha = filledForm.data().get("captcha");
		String ses_captcha = session("captcha");
		if(ses_captcha.equals(captcha)){
			password = PasswordEncryption.mixPassword(password);
			Boolean isLoggedIn = Student.login(username, password);

			if (isLoggedIn) {
				session("isLoggedIn", "true");
				session("username", username);
				return redirect("/");
			} else {
				return redirect("/login");
			}
		}else{
			return redirect("/login");
		}

		// return ok(loginPage.render(title, "login",
		// url,"user: "+username+", pass: "+password+": "+isLoggedIn));

	}

	public static Result newStudent() throws Exception {
		Form<Students> filledForm = taskForm.bindFromRequest();
		String username = filledForm.data().get("username");
		String password = filledForm.data().get("password");
		String cpassword = filledForm.data().get("cpassword");
		String email = filledForm.data().get("email");
		String cemail = filledForm.data().get("cemail");
		String captcha = filledForm.data().get("captcha");
		String message = "";
		String ses_captcha = session("captcha");
		if (email.contains(cemail) && password.contains(cpassword)) {

			if (!Students.isEmailValid(email)
					&& !Students.isUsernameValid(username)) {

				if(captcha.equals(ses_captcha)){
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
						message = "You are now registered to the system.";
						controllers.Student.sendMail(email,
								Student.registerMailInfo);
					} else {
						message = "Your password is weak";
					}

				}else{

					message = "girilen: "+captcha+", session: "+ses_captcha;
				}


			} else {
				message = "Your username or email is used";
			}

		} else {
			message = "Please enter correct values.";
		}

		Boolean validEmail = Students.isEmailValid(email);


		return ok(signUpPage.render(title, "signUp", url, message, taskForm,
				false));
	}
	public static void selectedCoursesToCourses(){
		if (!selectedCourse.isEmpty()){
			for (models.JSONParser.Course selectedSection : selectedCourse) {
				if(usrCourseList.isEmpty()){
					Course course = new Course(selectedSection.getSubjectName(), selectedSection.getCourseNo());
					CourseSection section = new CourseSection(course.courseTitle, selectedSection.getSectionNo(), "testInstructor");
					for (models.JSONParser.LectureInterval lectureInterval : selectedSection.getLectures()) {
						int startHour = lectureInterval.getStartHour();
						int startMinute = lectureInterval.getStartMinute();
						int finishHour = lectureInterval.getFinishHour();
						int finishMinute = lectureInterval.getFinishMinute();

						String startTime = Integer.toString(startHour) + ":" + Integer.toString(startMinute);
						String finishTime = Integer.toString(finishHour) + ":" + Integer.toString(finishMinute);

						String day = lectureInterval.getDay();

						TimePeriod time = new TimePeriod(day, startTime, finishTime);
						section.addTime(time);
					}
					course.addSection(section);
					System.out.println("Girdi 1: " + course.courseTitle + "-" + course.sections.get(0).sectionTitle);
					usrCourseList.add(course);
					System.out.println("Birinci için: "+ usrCourseList.size());
				}else{
					boolean isExist = false;
					for (Course existCourse : usrCourseList) {
						System.out.println("Hello: " + usrCourseList.indexOf(existCourse));
						String name = selectedSection.getSubjectName();
						name += selectedSection.getCourseNo();
						if(existCourse.courseTitle.equals(name)){
							System.out.println("Aynı");
							CourseSection section = new CourseSection(existCourse.courseTitle, selectedSection.getSectionNo(), "testInstructor");
							for (models.JSONParser.LectureInterval lectureInterval : selectedSection.getLectures()) {
								int startHour = lectureInterval.getStartHour();
								int startMinute = lectureInterval.getStartMinute();
								int finishHour = lectureInterval.getFinishHour();
								int finishMinute = lectureInterval.getFinishMinute();

								String startTime = Integer.toString(startHour) + ":" + Integer.toString(startMinute);
								String finishTime = Integer.toString(finishHour) + ":" + Integer.toString(finishMinute);

								String day = lectureInterval.getDay();

								TimePeriod time = new TimePeriod(day, startTime, finishTime);
								section.addTime(time);
							}
							existCourse.addSection(section);
							System.out.println("Girdi 3: " + existCourse.courseTitle + "-" + section.sectionTitle);
							isExist = true;
							break;
						}
					}
					if(isExist == false){
						Course course = new Course(selectedSection.getSubjectName(), selectedSection.getCourseNo());
						CourseSection section = new CourseSection(course.courseTitle, selectedSection.getSectionNo(), "testInstructor");

						for (models.JSONParser.LectureInterval lectureInterval : selectedSection.getLectures()) {
							int startHour = lectureInterval.getStartHour();
							int startMinute = lectureInterval.getStartMinute();
							int finishHour = lectureInterval.getFinishHour();
							int finishMinute = lectureInterval.getFinishMinute();

							String startTime = Integer.toString(startHour) + ":" + Integer.toString(startMinute);
							String finishTime = Integer.toString(finishHour) + ":" + Integer.toString(finishMinute);

							String day = lectureInterval.getDay();

							TimePeriod time = new TimePeriod(day, startTime, finishTime);
							section.addTime(time);
						}
						course.addSection(section);
						System.out.println("Girdi 2: " + course.courseTitle + "-" + course.sections.get(0).sectionTitle);
						usrCourseList.add(course);
						System.out.println("İkinci için: "+ usrCourseList.size());
					}
				}
			}
		}
	}

	public static void startScheduler() {
		usrCourseList.clear();
		System.out.println(usrCourseList.isEmpty());
		System.out.println("SelectedCourse: " + selectedCourse.size());
		selectedCoursesToCourses();
		System.out.println("Size: " + usrCourseList.size());
		if (!usrCourseList.isEmpty()) {
			setOneSectionCourses();
			setScheduleForOneSections();
			setMultipleSectionCourses();
		}
		System.out.println(result.size());
	}




	private static void setOneSectionCourses() {

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

	private static void setTimeIntervals(int timeIndex, int startIndex, int period,
			int day, String type, Day[] calendar) {
		// set Week with relevant meetingTime
		System.out.println("TimeIndex: " + timeIndex + "\nStartIndex: " + startIndex + "\nPeriod: " + period);
		if (timeIndex == startIndex) {
			if (calendar[day].oClock.get(startIndex).isStartAvailable == true) {
				System.out.println("Degisti 1");
				calendar[day].oClock.get(startIndex).isStartAvailable = false;
			} else {
				setNoProblem(type);
			}
		} else if (timeIndex == period-1) {
			if (calendar[day].oClock.get(timeIndex).isEndAvailable == true) {
				System.out.println("Degisti Son");
				calendar[day].oClock.get(timeIndex).isEndAvailable = false;
			} else {
				setNoProblem(type);
			}
		} else {
			System.out.println("Bakıyor");
			if (calendar[day].oClock.get(timeIndex).isStartAvailable == true) {
				if (calendar[day].oClock.get(timeIndex).isEndAvailable == true) {
					System.out.println("Degisti Ara");
					calendar[day].oClock.get(timeIndex).isStartAvailable = false;
					calendar[day].oClock.get(timeIndex).isEndAvailable = false;
				} else {
					System.out.println("Hata 2");
					setNoProblem(type);
				}
			} else {
				System.out.println("Hata 1" + calendar[day].oClock.get(timeIndex).isStartAvailable);
				setNoProblem(type);
			}
		}
	}

	private static void lookMeetingTimes(ArrayList<TimePeriod> meetingTimes,
			String type, Day[] calendar) {
		// look meetingTimes for relevant course
		if (meetingTimes.size() > 0) {
			System.out.println("Buyuk");
			for (int meetingIndex = 0; meetingIndex < meetingTimes.size(); meetingIndex++) {
				System.out.println("meetingIndex: " + meetingIndex);
				if (getNoProblem(type) == true) {
					int day = meetingTimes.get(meetingIndex).dayIndex;
					// lessons allways start min at 8 so startHour-8
					int startIndex = (meetingTimes.get(meetingIndex).startHour) - 8;
					int period = startIndex + meetingTimes.get(meetingIndex).hours;
					for (int timeIndex = startIndex; timeIndex < period; timeIndex++) {
						if (getNoProblem(type) == true
								&& type.equals("OneSection")) {
							System.out.println("Sete Girdi!");
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

	private static void setScheduleForOneSections() {
		System.out.println("Girmeli!");
		if (!oneSectionCourses.isEmpty()) {
			System.out.println("Dolu!");
			Day[] calendar = new Week().getWeek();
			for (int index = 0; index < oneSectionCourses.size(); index++) {
				if (noProblemForOne == true) {
					System.out.println("Sorunsuz");
					lookMeetingTimes(oneSectionCourses.get(index).sections.get(0).meetingTimes,"OneSection", calendar);
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

	public static void setScheduleForMultipleSections(
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

	public static void setMultipleSectionCourses() {
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

	public static void setNoProblem(String type) {
		if (type.equals("OneSection")) {
			noProblemForOne = false;
		} else {
			noProblemForMultiple = false;
		}
	}

	public static boolean getNoProblem(String type) {
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