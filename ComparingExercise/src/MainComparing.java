import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by martin on 3/16/17.
 */
public class MainComparing {

	public static void main(String[] args){
		ArrayList<Student> students = new ArrayList<Student>();
		students.add(new Student("Bob", "Johnson"));
		students.add(new Student("Phil", "Fisher"));
		students.add(new Student("Annie", "Sarcastic"));
		students.add(new Student("Pat", "Boivin"));

		System.out.println("students = " + students);

		//Collections.sort(students);

		//students.sort(new StudentCompareFirstnameLength());

		Sorters.bubbleSort(students);

		System.out.println("students = " + students);
	}
}
