import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by martin on 3/16/17.
 */
public class Sorters<T>{
	public static <T extends Comparable<? super T>> void bubbleSort(List<T> list){
		int swaps = 1;
		while(swaps!=0){
			swaps=0;
			for(int i = 0; i<list.size()-1; i++){
				if(list.get(i).compareTo(list.get(i+1)) > 0){
					Collections.swap(list, i, i+1);
					swaps++;
				}
			}
		}
	}
}
