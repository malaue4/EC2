package graph;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by martin on 3/23/17.
 */
public class Graph {
	Set<Vertice> mVertices;

	Graph(){
		mVertices = new HashSet<>();
	}

	public void addVertice(String content) {
		Vertice vertice = new Vertice(content);
		mVertices.add(vertice);
	}

	public Vertice getVertice(String content) {
		for(Vertice vertice : mVertices){
			if(vertice.mContent.equals(content)){
				return vertice;
			}
		}
		return null;
	}

	public void addEdge(String source, String destination, double cost) {
		getVertice(source).mEdges.add(new Edge(getVertice(destination), cost));
	}
}
