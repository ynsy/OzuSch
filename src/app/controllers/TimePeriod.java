package controllers;

public class TimePeriod {
	public String day;
	public String startTime;
	public String endTime;

	int startHour = 0;
	int endHour = 0;
	int hours = 0;
	int dayIndex = 0;

	public TimePeriod(String day, String startTime, String endTime) {
		this.day = day;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hours = setHours();
		this.dayIndex = setDayIndex();
	}

	private int setHours() {
		int indexOfSeperator = startTime.indexOf(':');
		String timeParts = startTime.substring(0, indexOfSeperator);
		startHour = Integer.parseInt(timeParts);

		indexOfSeperator = endTime.indexOf(':');
		timeParts = endTime.substring(0, indexOfSeperator);
		endHour = Integer.parseInt(timeParts);

		return (endHour-startHour+1);
	}
	private int setDayIndex() {
		if (day.equals("Monday")) {
			return 0;
		}else if (day.equals("Tuesday")) {
			return 1;
		}else if (day.equals("Wednesday")) {
			return 2;
		}else if (day.equals("Thursday")) {
			return 3;
		}else {
			return 4;
		}
	}



}
