package br.com.phendia.vitor.graphalgorithms.search;

import br.com.phendia.vitor.graphalgorithms.graph.Graph;

public abstract class GraphSearch {
	
	public static final int WHITE = 0;
	public static final int GRAY = 1;
	public static final int BLACK = 2;
	
	private Graph graph;
	
	public GraphSearch(Graph graph) {
		this.graph = graph;
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public abstract void search(int vertice);

}
