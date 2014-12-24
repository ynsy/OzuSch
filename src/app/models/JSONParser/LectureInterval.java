package models.JSONParser;

import java.sql.SQLException;
import java.util.ArrayList;

import models.DatabaseConnector;

public class LectureInterval {
	
	private int id;
	private int courseId;
	private String startHour;
	private String finishHour;
	private String day;
	private String roomCode;
	
	private static ArrayList<LectureInterval> lectureIntervals = new ArrayList<LectureInterval>();
	
	public LectureInterval() {
		
	}
	
	public LectureInterval(int id, int courseId, String startHour, String finishHour, String day, String roomCode) {
		this.id = id;
		this.courseId = courseId;
		this.startHour = startHour;
		this.finishHour = finishHour;
		this.day = day;
		this.roomCode = roomCode;
		
		this.lectureIntervals.add(this);
	}

	public int getId() {
		return id;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getStartHour() {
		return startHour;
	}

	public String getEndHour() {
		return finishHour;
	}

	public String getDay() {
		return day;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public ArrayList<LectureInterval> getLectureInteval() {
		return lectureIntervals;
	}
	
	public void printAllLectureIntervals(){
		for (LectureInterval lectureInterval : lectureIntervals) {
			System.out.println("id\tstart_hour\tend_hour\tday\troom_code\tcourse_id");
			System.out.println(lectureInterval.getId() + "\t" + lectureInterval.getStartHour() + "\t\t" + lectureInterval.getEndHour() + "\t\t" + lectureInterval.getDay() + "\t" + lectureInterval.getRoomCode() + "\t" + lectureInterval.getCourseId());
		}
	}
	
	public static void addLectureIntervalsToDatabase() throws SQLException {
		for (LectureInterval lectureInterval : lectureIntervals) {
			DatabaseConnector.statement = DatabaseConnector.connection
					.prepareStatement("INSERT INTO lecture_intervals (id,start_hour,end_hour,day,room_code,course_id) VALUES ("
							+ lectureInterval.getId()
							+ ",\""
							+ lectureInterval.getStartHour()
							+ "\",\""
							+ lectureInterval.getEndHour()
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
}
