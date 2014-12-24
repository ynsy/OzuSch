package models.JSONParser;

import java.util.ArrayList;

public class LectureInterval {
	
	private int id;
	private int courseId;
	private String startHour;
	private String finishHour;
	private String day;
	private String roomCode;
	
	private ArrayList<LectureInterval> lectureIntevals = new ArrayList<LectureInterval>();
	
	public LectureInterval() {
		
	}
	
	public LectureInterval(int id, int courseId, String startHour, String finishHour, String day, String roomCode) {
		this.id = id;
		this.courseId = courseId;
		this.startHour = startHour;
		this.finishHour = finishHour;
		this.day = day;
		this.roomCode = roomCode;
		
		this.lectureIntevals.add(this);
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
		return lectureIntevals;
	}
	
	public void printAllLectureIntervals(){
		for (LectureInterval lectureInterval : lectureIntevals) {
			System.out.println("id\tstart_hour\tend_hour\tday\troom_code\tcourse_id");
			System.out.println(lectureInterval.getId() + "\t" + lectureInterval.getStartHour() + "\t\t" + lectureInterval.getEndHour() + "\t\t" + lectureInterval.getDay() + "\t" + lectureInterval.getRoomCode() + "\t" + lectureInterval.getCourseId());
		}
	}
}
