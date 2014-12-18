package controllers;

public class Week {
	Day[] week = new Day[5];

	public Week() {
		setWeek();
	}

	public Day[] getWeek() {
		return week;
	}

	private void setWeek() {
		for (int index = 0; index < 5; index++) {
			if (index == 0) {
				week[index] = new Day("Monday");
			} else if (index == 1) {
				week[index] = new Day("Tuesday");
			} else if (index == 2) {
				week[index] = new Day("Wednesday");
			} else if (index == 3) {
				week[index] = new Day("Thursday");
			} else {
				week[index] = new Day("Friday");
			}
		}
	}

	public Day[] getCalendar(Day[] calendar) {
		// Week();
		Day[] cal = getWeek();
		for (int day = 0; day < cal.length; day++) {
			for (int timeIndex = 0; timeIndex < calendar[day].oClock.size(); timeIndex++) {
				cal[day].oClock.get(timeIndex).isStartAvailable = calendar[day].oClock
						.get(timeIndex).isStartAvailable;
				cal[day].oClock.get(timeIndex).isEndAvailable = calendar[day].oClock
						.get(timeIndex).isEndAvailable;
			}
		}
		return cal;
	}

}
