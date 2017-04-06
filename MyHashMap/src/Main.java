/**
 * Created by martin on 3/30/17.
 */
public class Main {
	public static void main(String[] args){
		MyHashMap hashMap = new MyHashMap();

		String jensen = "Jensen";
		hashMap.add(jensen);

		int i = hashMap.indexOf(jensen);
		System.out.println(i);
		System.out.println(hashMap.get(jensen));
	}
}
