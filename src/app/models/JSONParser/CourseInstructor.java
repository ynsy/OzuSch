package models.JSONParser;

import java.util.ArrayList;


public class CourseInstructor {
	
	private int id;
	private int courseId;
	private String name;
	private String surname;
	private Boolean isPrimary;
	
	private ArrayList<CourseInstructor> courseInstructors = new ArrayList<CourseInstructor>();
	
	
	public CourseInstructor(){
		
	}
	
	public CourseInstructor(int id, int courseId, String name, String surname, Boolean isPrimary){
		this.id = id;
		this.courseId = courseId;
		this.name = name;
		this.surname = surname;
		this.isPrimary = isPrimary;
		
		this.courseInstructors.add(this);
	}

	public int getId() {
		return id;
	}

	public int getCourseId() {
		return courseId;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public Boolean getIsPrimary() {
		return isPrimary;
	}
	
	public ArrayList getCourseInstructors(){
		return courseInstructors;
	}
	
	public void printAllCourseInstructors(){
		for (CourseInstructor courseInstructor : courseInstructors) {
			System.out.println("id\tname\t\tsurname\t\tis_primary\tcourse_id");
			System.out.println(courseInstructor.getId() + "\t" + courseInstructor.getName() + "\t\t" + courseInstructor.getSurname() +"\t\t"+ courseInstructor.getIsPrimary() + "\t\t" + courseInstructor.getCourseId());
		}
	}
}
