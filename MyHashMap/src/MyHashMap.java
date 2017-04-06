import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by martin on 3/30/17.
 */
public class MyHashMap{
	String[] values = new String[1000];

	public boolean add(String s) {
		int hashValue = getHashValue(s);
		if(values[hashValue]==null) {
			values[hashValue]=s;
			return true;
		} else {
			return false;
		}
	}

	private int getHashValue(String s) {
		int hashValue = 0;
		for(char c : s.substring(0, 3).toCharArray()){
			hashValue += (int)c;
		}
		return hashValue;
	}

	public String get(String string) {
		int hashValue = getHashValue(string);
		if(values[hashValue]==null)
			return values[hashValue];
		else
			return null;
	}

	public int indexOf(String string) {
		int hashValue = getHashValue(string);
		String value = values[hashValue];
		if(value != null && value.equals(string))
			return hashValue;
		else
			return -1;
	}
}
