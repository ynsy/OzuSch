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

	public ArrayList<Oclock> oClock = new ArrayList<Oclock>();

	public Day(String name) {
		this.name = name;
		// start from 8 am, end at 10:59 pm (15 hours)
		for (int index = 0; index < 15; index++) {
			this.oClock.add(new Oclock());
		}
	}
	
	
}