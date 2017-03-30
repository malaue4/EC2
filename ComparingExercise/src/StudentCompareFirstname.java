import java.util.Comparator;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

/**
 * Created by martin on 3/16/17.
 */
public class StudentCompareFirstname implements Comparator<Student> {
	@Override
	public int compare(Student student1, Student student2) {
		return student1.getFirstName().compareTo(student2.getFirstName());
	}
}
