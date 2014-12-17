package controllers;

import java.util.ArrayList;

public class Day {

	String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	ArrayList<Oclock> oClock;
	Oclock oclock;

	public Day(String name) {
		this.name = name;
		// start from 8 am, end at 10:59 pm (15 hours)
		for (int index = 0; index < 15; index++) {
			this.oClock.add(oclock);
		}
	}
	
	
}