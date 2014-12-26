package models.JSONParser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import models.DatabaseConnector;

public class LectureInterval {

	private int id;
	private int courseId;
	private int startHour;
	private int startMinute;
	private int finishHour;
	private int finishMinute;
	private String day;
	private String roomCode;

	private static ArrayList<LectureInterval> lectureIntervals = new ArrayList<LectureInterval>();
	public static ArrayList<LectureInterval> dbLectureIntervalList = new ArrayList<LectureInterval>();
	public static ResultSet resultSet;
	public static java.sql.PreparedStatement statement;

	public LectureInterval() {

	}

	public LectureInterval(int id, int courseId, int startHour,
			int startMinute, int finishHour, int finishMinute, String day,
			String roomCode) {
		this.id = id;
		this.courseId = courseId;
		this.startHour = startHour;
		this.startMinute = startMinute;
		this.finishHour = finishHour;
		this.finishMinute = finishMinute;
		this.day = day;
		this.roomCode = roomCode;

		this.lectureIntervals.add(this);
	}

	public int getId() {
		return this.id;
	}

	public int getCourseId() {
		return this.courseId;
	}

	public int getStartHour() {
		return this.startHour;
	}

	public int getStartMinute() {
		return this.startMinute;
	}

	public int getFinishHour() {
		return this.finishHour;
	}

	public int getFinishMinute() {
		return this.finishMinute;
	}

	public String getDay() {
		return this.day;
	}

	public String getRoomCode() {
		return this.roomCode;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = startMinute;
	}

	public void setFinishHour(int finishHour) {
		this.finishHour = finishHour;
	}

	public void setFinishMinute(int finishMinute) {
		this.finishMinute = finishMinute;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public ArrayList<LectureInterval> getLectureInteval() {
		return this.lectureIntervals;
	}

	// SORUNLU
	public static ArrayList<LectureInterval> getLectureInterval(int course_id)
			throws SQLException {
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, start_hour, start_minute, end_hour, end_minute, day, room_code, course_id FROM lecture_intervals WHERE course_id = '"
						+ course_id + "';");
		resultSet = statement.executeQuery();
		ArrayList<LectureInterval> lectureIntervalList = new ArrayList<LectureInterval>();
		while (resultSet.next()) {
			LectureInterval l = new LectureInterval();
			l.setId(resultSet.getInt(1));
			l.setStartHour(resultSet.getInt(2));
			l.setStartMinute(resultSet.getInt(3));
			l.setFinishHour(resultSet.getInt(4));
			l.setFinishMinute(resultSet.getInt(5));
			l.setDay(resultSet.getString(6));
			l.setRoomCode(resultSet.getString(7));
			l.setCourseId(resultSet.getInt(8));
			lectureIntervalList.add(l);
		}
		resultSet.close();
		statement.close();
		return lectureIntervalList;
	}

	public void printAllLectureIntervals() {
		for (LectureInterval lectureInterval : lectureIntervals) {
			System.out
					.println("id\tstart_hour\tend_hour\tday\troom_code\tcourse_id");
			System.out.println(lectureInterval.getId() + "\t"
					+ lectureInterval.getStartHour() + "\t\t"
					+ lectureInterval.getFinishHour() + "\t\t"
					+ lectureInterval.getDay() + "\t"
					+ lectureInterval.getRoomCode() + "\t"
					+ lectureInterval.getCourseId());
		}
	}

	public static void addLectureIntervalsToDatabase() throws SQLException {
		for (LectureInterval lectureInterval : lectureIntervals) {
			DatabaseConnector.statement = DatabaseConnector.connection
					.prepareStatement("INSERT INTO lecture_intervals (id,start_hour,start_minute,end_hour,end_minute,day,room_code,course_id) VALUES ("
							+ lectureInterval.getId()
							+ ",\""
							+ lectureInterval.getStartHour()
							+ "\",\""
							+ lectureInterval.getStartMinute()
							+ "\",\""
							+ lectureInterval.getFinishHour()
							+ "\",\""
							+ lectureInterval.getFinishMinute()
							+ "\",\""
							+ lectureInterval.getDay()
							+ "\",\""
							+ lectureInterval.getRoomCode()
							+ "\","
							+ lectureInterval.getCourseId() + ");");
			DatabaseConnector.statement.executeUpdate();
			DatabaseConnector.statement.close();

		}

	}

	public static void retrieveLectureIntervalListFromDB() throws SQLException {
		statement = DatabaseConnector.connection
				.prepareStatement("SELECT id, start_hour, start_minute, end_hour, end_minute, day, room_code, course_id FROM lecture_intervals;");
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
			LectureInterval l = new LectureInterval();
			l.setId(resultSet.getInt(1));
			l.setStartHour(resultSet.getInt(2));
			l.setStartMinute(resultSet.getInt(3));
			l.setFinishHour(resultSet.getInt(4));
			l.setFinishMinute(resultSet.getInt(5));
			l.setDay(resultSet.getString(6));
			l.setRoomCode(resultSet.getString(7));
			l.setCourseId(resultSet.getInt(8));
			dbLectureIntervalList.add(l);
		}
		statement.close();
	}
}
