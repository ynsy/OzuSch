package models.JSONParser;

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
	
	public LectureInterval() {
		
	}
	
	public LectureInterval(int id, int courseId, int startHour, int startMinute, int finishHour, int finishMinute, String day, String roomCode) {
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
	
	public int getStartMinute(){
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

	public ArrayList<LectureInterval> getLectureInteval() {
		return this.lectureIntervals;
	}
	
	public void printAllLectureIntervals(){
		for (LectureInterval lectureInterval : lectureIntervals) {
			System.out.println("id\tstart_hour\tend_hour\tday\troom_code\tcourse_id");
			System.out.println(lectureInterval.getId() + "\t" + lectureInterval.getStartHour() + "\t\t" + lectureInterval.getFinishHour() + "\t\t" + lectureInterval.getDay() + "\t" + lectureInterval.getRoomCode() + "\t" + lectureInterval.getCourseId());
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
}
