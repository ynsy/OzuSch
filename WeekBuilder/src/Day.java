
public class Day {

	String name = "";
	//start from 8 am, end at 6:59 pm (11 hours)
	Oclock[] oClock = new Oclock[11];
	
	public Day(String name){
		this.name = name;
		for (int index = 0; index < oClock.length; index++){
			oClock[index] = new Oclock();
		}
	}

}
