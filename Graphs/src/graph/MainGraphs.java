package graph;

/**
 * Created by martin on 3/23/17.
 */
public class MainGraphs {
	public void main(String[] args){
		Graph graph = new Graph();
		graph.addVertice("Rønne");
		graph.addVertice("Gudhjem");
		graph.addVertice("Allinge");
		graph.addVertice("Troldebo");
		graph.addVertice("Rokkestenen");
		graph.addVertice("Sandby");
		graph.addVertice("Kalle");

		graph.addEdge("Rønne", "Gudhjem", 14);

	}
}
