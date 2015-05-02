package br.com.phendia.vitor.graphalgorithms.search;

import java.util.List;

import br.com.phendia.vitor.graphalgorithms.graph.Graph;
import br.com.phendia.vitor.graphalgorithms.utils.Range;

public abstract class GraphSearch {
	
	public static final int WHITE = 0;
	public static final int GRAY = 1;
	public static final int BLACK = 2;
	
	private Graph graph;
	private List<Integer> nodes;
	
	public GraphSearch(Graph graph) {
		this.graph = graph;
		this.nodes = new Range( this.graph.getNumNodes() );
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public List<Integer> getNodes() {
		return this.nodes;
	}
	
	public abstract void search(int vertice);

}
