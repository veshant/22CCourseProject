
public class Course implements Comparable<Course>{

	// Private variables
	private String courseID;
	private String title;
	private String teacher;
	private String meetingTimes;
	private String location;
	private int courseSize;
	private int units;
	
	/**
     * Constructor for the Course class
     * @param course ID: the Course's CRN/ individual ID for the quarter 
     * @param title: title of course (allows for duplicates)
     * @param teacher: the Course's teacher
     * @param meetingTimes: the days and times for the course held each week
     * @param location: the location where the course is held
     * @param courseSize = amount of students that can be enrolled in the course
     * @param units: the Course's amount of units for the quarter
     */
	public Course(String CRN, String title, String teacher, int students, int units, String meetingTimes, String location) {
		// Initialize with values passed in to constructor
		this.courseID = CRN; // primary key
		this.title = title; // secondary key
		this.teacher = teacher;
		this.meetingTimes = meetingTimes;
		this.location = location;
		this.courseSize = students;
		this.units = units;
	}
	
	
	// ACCESSORS
	
	/**
     * Accesses the course ID (CRN) of the Course
     * @return the Course's ID
     */
	public String getCourseID() {
		return courseID;
	}
	
	/**
     * Accesses the title/ name of the Course
     * @return the Course's title
     */
	public String getTitle() {
		return title;
	}
	
	/**
     * Accesses the teacher of the Course
     * @return the Course's teacher
     */
	public String getTeacher() {
		return teacher;
	}
	
	/**
     * Accesses the weekly meeting times of the Course (including days of the week and times)
     * @return the Course's meeting times
     */
	public String meetingTimes() {
		return meetingTimes;
	}
	
	/**
     * Accesses the location of the Course
     * @return the COurse's location
     */
	public String location() {
		return location;
	}
	
	/**
     * Accesses the total possible class size of the Course
     * @return the Course's size
     */
	public int getCourseSize() {
		return courseSize;
	}
	
	/**
     * Accesses the amount of units for the Course
     * @return the Course's units
     */
	public int getUnits() {
		return units;
	}
	
	
	// MUTATORS 
	
	
	/**
     * Sets the course ID (CRN) of the Course
     * @param CRN: the COurse's ID number
     */
	public void setCourseID(String CRN) {
		this.courseID = CRN;
	}
	
	/**
     * Sets the title of the Course
     * @param title: the name of the Course
     */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
     * Sets the teacher of the Course
     * @param teacher: the name of the Course's teacher
     */
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	
	/**
     * Sets the weekly meeting times for the Course (including days of week and times)
     * @param meetingTimes: the Course's meeting times
     */
	public void setMeetingTimes(String meetingTimes) {
		this.meetingTimes = meetingTimes;
	}
	
	/**
     * Sets the location of where the Course is held
     * @param location: the Course's location
     */
	public void setLocation(String location) {
		this.location = location;
	}
	
	/**
     * Sets the total possible class size of the Course
     * @param courseSize: the Course's size
     */
	public void setCourseSize(int courseSize) {
		this.courseSize = courseSize;
	}
	
	/**
     * Sets the amount of units for the Course
     * @param units: the Course's units
     */
	public void setUnits(int units) {
		this.units = units;
	}
	
	/**
    * Creates a String of the Course information
    * the following format:
    * CRN: <courseID>
    * Course: <title>
    * Instructor: <teacher>
    * Class Size: <courseSize>
    * Times: <meetingTimes>
    * Location: <location>

    * Units: <units>
    */
	@Override public String toString() {
		// This is more effective way to build strings
		StringBuilder course = new StringBuilder("CRN: ").append(courseID).append("\nCourse: ").append(title)
				.append("\nInstructor: ").append(teacher).append("\nClass Size: ").append(courseSize)
				.append("\nUnits: ").append(units).append("\nTimes: ").append(meetingTimes)
				.append("\nLocation: ").append(location).append("\n");
		return course.toString();
		/*
		String course = "CRN: " + courseID + "\n"
        		+ "Course: " + title + "\n"
        		+ "Instructor: " + teacher + "\n"
        		+ "Class Size: " + courseSize + "\n"
        		+ "Units: " + units + "\n"
        		+ "Times: " + meetingTimes + "\n"
        		+ "Location: " + location + "\n";
        return course;
        */
	}
	
	/**
     * Determines whether two Course objects are equal by comparing courseID, titles, and teachers
     * @param otherCourse: the second Course object
     * @return whether the Courses are equal
     */
	@Override public boolean equals(Object otherCourse) {
		if (otherCourse == this)
        	return true;
        else if (!(otherCourse instanceof Course) )
        	return false;
        else {
        	Course C = (Course) otherCourse;
        	return this.courseID.equals(C.getCourseID()); // To be equal, must have same course ID
      }
	}

	/**
     * Compares two Course objects to determine ordering
     * Returns 0 if the two items are equal
     * Return -1 if this Course's ID comes before the other Course's ID
     * Returns 1 if the other Course's ID comes before this Course's ID
     * @param the other Course object to compare to this
     * @return 0 (same course), -1 (this course ordered first), or 1 (the other course ordered first) 
     */
	@Override
	public int compareTo(Course otherCourse) {
		if (this.courseID.equals(otherCourse.courseID)) { // Course IDs should be unique for the quarter
			return 0;
		} 
    	else {
    		if (Integer.parseInt(this.courseID) < Integer.parseInt(otherCourse.courseID))
    			return -1;
    		else
    			return 1;
		}
	}
	
	/**
     * Returns a consistent hash code for each Course by summing the Unicode values of each character in the key
     * Key = courseID + title
     * @return the hash code
     */
	@Override public int hashCode() {
    	String key = courseID;
        int sum = 0;
        for (int i = 0; i < key.length(); i++) {
              sum += (int) key.charAt(i);
        }
        return sum;
    }
	
	private Course() {
	}
	
	public static Course getByCourseID(String courseID) {
		Course course = new Course();
		course.setCourseID(courseID);
		return course;	
	}
	
	public static Course getByTitle(String title) {
		Course course = new Course();
		course.setTitle(title);
		return course;	
	}
	public static Course getByTitle(String courseID, String title) {
		Course course = new Course();
		course.setCourseID(courseID);
		course.setTitle(title);
		return course;	
	}
}
