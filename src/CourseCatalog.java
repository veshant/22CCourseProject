
/**
 * CourseCatalog.java
 * @author Alexandra Hutchins
 * @author Alvin Nguyen
 * @author Julia Lagun
 * @author Shu Hui Yu
 * @author Ruby Liao
 * @author Veshant Chettiar
 * CIS 22C, Course Project
 * ** DESCRIPTION HERE **
 */

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CourseCatalog {

	// Comparing by title
	Comparator<Course> secondaryCompare = new Comparator<Course>() {
		@Override
		public int compare(Course c1, Course c2) {
			return c1.getTitle().compareTo(c2.getTitle());
		}
	};

	private Hash<Course> ht;
	private BST<Course> bstCRN;
	private BST<Course> bstTitle;

	private final String[] mainMenuItems = { "1. Add new course", "2. Delete course", "3. Search course", "4. Display courses",
			"5. Write data to a file", "Q. Quit" };

	private final String[] searchMenuItems = { "1. Find course by CRN code",
			"2. Find by title", "B. Back" };
	private final String[] listMenuItems = { "1. List unsorted data",
			"2. List data sorted by the CRN code", "3. List data sorted by the Title",
			"B. Back" };

	public static void main(String[] arg) {
		CourseCatalog cc = new CourseCatalog();

		System.out.println("Welcome to Stanford's course catalog!");
		Scanner sc = new Scanner(System.in);

		while (true){
			System.out.print("\nPlease input the name of data text file: ");
			String fileName = sc.nextLine();
			try {
				cc.readFile(fileName);
				break;
			} catch (IOException e) {
				System.out.println("Invalid name of file. Please try again.");
			}
		}
		cc.bstTitle.preOrderPrint();
		cc.mainMenu(sc);

		// Auto print contents to ClassDataAuto.txt after program runs
		System.out.println("Data saved to file ClassDataAuto.txt");
		cc.printTxt("ClassDataAuto");
		
		System.out.println("\nBye!");
	}

	/*** MENU METHODS ***/

	/**
	 * Runs main menu
	 * 
	 * @param sc
	 */
	private void mainMenu(Scanner sc) {
		String input = "";
		while (true) {
			System.out.println("\nMenu:");
			printMenu(mainMenuItems);
			System.out.print("\nEnter your choice: ");
			input = sc.nextLine().toUpperCase();
			switch (input) {
			case "1":
				addMenu(sc);
				break;
			case "2":
				deleteMenu(sc);
				break;
			case "3":
				searchMenu(sc);
				break;
			case "4":
				listMenu(sc);
				break;
			case "5":
				writeMenu(sc);
				break;
			case "Q":
				return;
			default:
				System.out.println("Wrong input. Please try again.");
			}
		}
	}

	/**
	 * Ask user to enter data for new course. Don't allow to enter duplicates by crn
	 * @param sc
	 */
	private void addMenu(Scanner sc) {
		// request data to be added and add
		while (true) {
			System.out.println("\nEnter information about new course:");
			System.out.print("CRN: ");
			String crn = sc.nextLine();
			Course searchKeyCrn = Course.getByCourseID(crn);
			Course foundCourse = ht.search(searchKeyCrn);
			if (foundCourse != null) {
				System.out.println("\nCourse with such code already exist. Please try another code.");
			} else {	
				System.out.print("Course title and department: ");
				String title = sc.nextLine();
				System.out.print("Instructor: ");
				String instructor = sc.nextLine();
				System.out.print("Class size: ");
				int size = sc.nextInt();
				sc.nextLine();
				System.out.print("Units: ");
				int units = sc.nextInt();
				sc.nextLine();
				System.out.print("Times: ");
				String times = sc.nextLine();
				System.out.print("Location: ");
				String loc = sc.nextLine();

				Course c = new Course(crn, title, instructor, size, units, times, loc);
				ht.insert(c);
				bstCRN.insert(c);
				bstTitle.insert(c);
				System.out.printf("\n%s %s was added!\n", crn, title);
				break;
			}
		}		
	}

	/**
	 * Delete menu asks user about crn code for removig
	 * and delete this course if it exist or inform not found if not
	 * @param sc
	 */
	private void deleteMenu(Scanner sc) {
		System.out.print("\nEnter the CRN for removing course: ");
		String crn = sc.nextLine();
		Course searchKeyCrn = Course.getByCourseID(crn);
		
		try {
			bstCRN.remove(searchKeyCrn);
			String title = ht.search(searchKeyCrn).getTitle();
			Course searchKeyTitle = Course.getByTitle(crn, title);
			ht.remove(searchKeyCrn);
			// find all matches courses by title
			ArrayList<Course> courses = bstTitle.search(searchKeyTitle);
			// remove all matches courses from bst
			for(int i = 0; i < courses.size(); i++) {
				bstTitle.remove(searchKeyTitle);
			}
			// add back to bst removed courses except one with removing crn 
			for(Course course: courses) {
				if(!searchKeyTitle.equals(course)) {
					bstTitle.insert(course);
				}
			}
			System.out.printf("Course with CRN %s was successfully removed\n", crn);			
		} catch (NoSuchElementException e) {
			System.out.printf("There are no course with code %s\n", searchKeyCrn);
		}
	}

	/**
	 * Search menu makes search courses by crn or title,
	 * shows result of search or inform that not found
	 * @param sc
	 */
	private void searchMenu(Scanner sc) {
		String input = "";
		while (true) {
			System.out.println();
			printMenu(searchMenuItems);
			System.out.print("\nEnter your choice: ");
			input = sc.nextLine().toUpperCase();
			switch (input) {
			case "1":
				System.out.print("\nEnter course CRN: ");
				String crnCode = sc.nextLine();
				Course searchKeyCrn = Course.getByCourseID(crnCode);
				Course foundCourse = ht.search(searchKeyCrn);
				
				if(foundCourse != null) {
					System.out.println("\nCourse found:\n");
					System.out.println(foundCourse);
 				} else {
 					System.out.printf("Course with CRN = %s not found\n", crnCode);
 				}
				return;
			case "2":
				System.out.print("\nEnter course title: ");
				String title = sc.nextLine();
				Course searchKeyTitle = Course.getByTitle(title);
				ArrayList<Course> foundCourses = bstTitle.search(searchKeyTitle);
				if(!foundCourses.isEmpty()) {
					System.out.println("\nFound courses:\n");
					for(int i = 0; i < foundCourses.size(); i++) {
						System.out.println(foundCourses.get(i));
					}
				} else {
					System.out.printf("Course %s not found\n", title);
				}
				return;
			case "B":
				return;
			default:
				System.out.println("Wrong input. Please try again.");
			}
		}
	}

	/**
	 * Display courses in specific order
	 * @param sc
	 */
	private void listMenu(Scanner sc) {
		String input = "";
		while (true) {
			System.out.println();
			printMenu(listMenuItems);
			System.out.print("\nEnter your choice: ");
			input = sc.nextLine().toUpperCase();
			switch (input) {
			case "1":
				System.out.println("\n*** Courses in unsorted order ***");
				System.out.println(ht);
				return;
			case "2":
				System.out.println("\n*** Courses in sorted order by the CRN ***\n");
				bstCRN.inOrderPrint();
				return;
			case "3":
				System.out.println("\n*** Courses in sorted order by Title ***\n");
				bstTitle.inOrderPrint();
				return;
			case "B":
				return;
			default:
				System.out.println("Wrong input. Please try again.");
			}
		}
	}

	/**
	 * Asks user file name for writing data
	*/
	private void writeMenu(Scanner sc) {
		System.out.println();
		System.out.print("Please enter file name: ");
		String fileName = sc.nextLine();
		System.out.println("*** Writing data to the file ***");
		printTxt(fileName);
	}

	/**
	 * Prints menu options to the screen
	 * 
	 * @param menuItems array of menu items to be printed
	 */
	private void printMenu(String[] menuItems) {
		for (int i = 0; i < menuItems.length; i++) {
			System.out.println(menuItems[i]);
		}
	}

	/**
	 * Returns string with desired information, ignoring information labels in
	 * ClassData.txt
	 * 
	 * @param in Scanner that contains contents of .txt file
	 * @precondition Scanner has lines to read
	 * @returns String with desired information
	 */
	private String info(Scanner in) {
		String str = in.nextLine();
		str = str.substring(str.indexOf(": ") + 2);
		return str;
	}

	/**
	 * Populates both BSTs and hash table with data from .txt file
	 * 
	 * @precondition .txt file to read data from must be in proper format
	 * @postcondition BSTs and hash tables created and populated
	 */
	private void readFile(String fileName) throws IOException {
		File file = new File(fileName + ".txt");
		Scanner in = new Scanner(file);
		String str, courseID, title, instructor, location, schedule;
		int size, units;

		in.nextLine(); // Skip title line
		str = in.nextLine();
		System.out.println(str);

		int courseCount = Integer.parseInt(str.substring(0, str.indexOf(" "))); // Get course count from .txt
		in.nextLine(); // Skip empty line

		ht = new Hash<>(courseCount * 2);
		bstCRN = new BST<>();
		bstTitle = new BST<>(secondaryCompare);

		while (in.hasNext()) {

			courseID = info(in); // Get CRN
			title = info(in); // Get course title
			instructor = info(in); // Get instructor
			size = Integer.parseInt(info(in)); // Get class size
			units = Integer.parseInt(info(in)); // Get class units
			schedule = info(in); // Get class meeting times
			location = info(in); // Get class location

			Course c = new Course(courseID, title, instructor, size, units, schedule, location);

			ht.insert(c);
			bstCRN.insert(c);
			bstTitle.insert(c);

			if (in.hasNext()) { // Skip empty line separating course info
				in.nextLine();
			}
		}
		in.close();
	}

	/**
	 * Prints file contents to ClassDataUpdated.txt at the end of program
	 * 
	 * @postcondition Text file generated in the style of ClassData.txt, unsorted
	 */
	private void printTxt(String fileName) {

		try {
			PrintWriter print = new PrintWriter(fileName + ".txt");
			print.println( // Print header
					"Stanford ExploreCourses 2019-2020 (" + fileName + ".txt) \n" + ht.getNumElements()
							+ " courses listed\n");
			print.println(ht);
			print.close();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	/**
	 * Prints contents of data structures to console (for debug)
	 * 
	 * @postcondition Contents of data structures printed to console
	 */
	private void print() {
		System.out.println("*** Printing hash table ***\n");
		System.out.println(ht);
		System.out.println("*** Printing BST by CRN ***\n");
		System.out.println(bstCRN);
		System.out.println("*** Printing BST by course title ***\n");
		System.out.println(bstTitle);
	}

}
