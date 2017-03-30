package graph;

import java.util.TreeSet;

/**
 * Created by martin on 3/23/17.
 */
public class Vertice {
	String mContent;
	TreeSet<Edge> mEdges;

	Vertice(String content){
		mContent = content;
		mEdges = new TreeSet<>();
	}
}
