import java.util.Comparator;

/**
 * Created by martin on 3/16/17.
 */
public class StudentCompareFirstnameLength implements Comparator<Student> {
	@Override
	public int compare(Student student1, Student student2) {
		return student1.getFirstName().length()-student2.getFirstName().length();
	}
}
