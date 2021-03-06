package models.JSONParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.StringTokenizer;

import models.DatabaseConnector;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	public static int COURSE_ID = 0;
	public static int COURSE_INSTRUCTOR_ID = 0;
	public static int LECTURE_INTERVAL_ID = 0;
	public static Course courseObject;

	public static JSONObject convertJSONObject(Object object) {
		return (JSONObject) object;
	}

	public static JSONArray convertJSONArray(Object object) {
		return (JSONArray) object;
	}

	public static void parseJSON() throws FileNotFoundException, IOException,
			ParseException, SQLException, ClassNotFoundException {

		CourseInstructor courseInstructor = null;
		LectureInterval lectureInterval = null;

		JSONParser parser = new JSONParser();
//		 Object obj = parser.parse(new
//		 FileReader("/Users/bahadirkirdan/Desktop/CS320/OzuSch/src/public/CoursesOffered.json"));
//		Object obj = parser.parse(new
//		 FileReader("/Users/burakatalay/Desktop/CS 320/project/CS320/src/public/CoursesOffered.json"));
		 Object obj = parser.parse(new
				 FileReader("//Users/play/Documents/CS320/src/public/CoursesOffered.json"));
//		Object obj = parser
//				.parse(new FileReader(
//						"C:/Users/Yunus/Documents/GitHub/CS320/src/public/CoursesOffered.json"));
		JSONArray jsonArray = convertJSONArray(obj);

		for (Object courseArray : jsonArray) {
			JSONObject courseJSON = convertJSONObject(courseArray);
			Object courseObj = courseJSON.get("Course");

			JSONObject course = convertJSONObject(courseObj);

			COURSE_ID++;
			String subjectName = (String) course.get("SubjectName");
			String courseNo = (String) course.get("CourseNo");
			String sectionNo = (String) course.get("SectionNo");

			String displayName = subjectName + "-" + courseNo;

			courseObject = new Course(COURSE_ID, subjectName, courseNo,
					displayName, sectionNo);

			// Get Instructors
			Object instructorsObj = course.get("Instructors");
			JSONArray instructorArray = convertJSONArray(instructorsObj);

			for (Object instructorJSONObj : instructorArray) {
				JSONObject instructor = convertJSONObject(instructorJSONObj);

				COURSE_INSTRUCTOR_ID++;
				String name = (String) instructor.get("Name");
				String surname = (String) instructor.get("Surname");
				boolean isPrimary = Boolean.valueOf((instructor
						.get("IsPrimary")).toString());

				courseInstructor = new CourseInstructor(COURSE_INSTRUCTOR_ID,
						COURSE_ID, name, surname, isPrimary);

			}

			// Get Schedule
			Object scheduleObj = course.get("Schedule");
			JSONArray scheduleArray = convertJSONArray(scheduleObj);

			for (Object scheduleJSONObj : scheduleArray) {
				JSONObject schedule = convertJSONObject(scheduleJSONObj);

				String startDate = (String) schedule.get("StartDate");
				String finishDate = (String) schedule.get("FinishDate");

				// Get MeetingTime
				Object meetingTimeObj = schedule.get("MeetingTime");
				JSONArray meetingTimeArray = convertJSONArray(meetingTimeObj);

				for (Object meetingTimeJSONObj : meetingTimeArray) {
					JSONObject meetingTime = convertJSONObject(meetingTimeJSONObj);

					String day = (String) meetingTime.get("Day");
					String startHourStr = (String) meetingTime.get("StartHour");
					String finishHourStr = (String) meetingTime
							.get("FinishHour");

					StringTokenizer tokenizer = new StringTokenizer(
							startHourStr, ":");

					int startHour = Integer.parseInt(tokenizer.nextToken()
							.trim());
					int startMinute = Integer.parseInt(tokenizer.nextToken()
							.trim());

					tokenizer = new StringTokenizer(finishHourStr, ":");

					int finishHour = Integer.parseInt(tokenizer.nextToken()
							.trim());
					int finishMinute = Integer.parseInt(tokenizer.nextToken()
							.trim());

					// Get Room
					Object roomObj = meetingTime.get("Room");
					JSONArray roomArray = convertJSONArray(roomObj);

					for (Object roomJSONObj : roomArray) {
						LECTURE_INTERVAL_ID++;

						JSONObject room = convertJSONObject(roomJSONObj);
						String roomCode = (String) room.get("RoomCode");

						lectureInterval = new LectureInterval(
								LECTURE_INTERVAL_ID, COURSE_ID, startHour,
								startMinute, finishHour, finishMinute, day,
								roomCode);

						// System.out.println(roomCode);
					}
				}
			}
			// System.out.println(subjectName);
			// courseObject.printAllCourses();
			// courseInstructor.printAllCourseInstructors();
			// lectureInterval.printAllLectureIntervals();
		}

		DatabaseConnector.makeConnection();
		Course.retrieveCourseListFromDB();
		for (Course c : Course.getDbCourseList()) {
			System.out.println("id: " + c.getId() + " displayname: "
					+ c.getDisplayName());
		}

	}

}
