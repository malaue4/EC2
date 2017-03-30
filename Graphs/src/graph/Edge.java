package graph;

/**
 * Created by martin on 3/23/17.
 */
public class Edge {
	Vertice mDestination;
	Double mCost;

	Edge(Vertice destination, double cost){
		mDestination = destination;
		mCost = cost;
	}
}
