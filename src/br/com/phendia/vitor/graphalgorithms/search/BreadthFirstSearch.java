package br.com.phendia.vitor.graphalgorithms.search;

import java.util.LinkedList;

import br.com.phendia.vitor.graphalgorithms.graph.Edge;
import br.com.phendia.vitor.graphalgorithms.graph.Graph;

public class BreadthFirstSearch extends GraphSearch {

	// A Java LinkedList can act as a queue
	private LinkedList<Integer> queue;

	private int[] color;
	private int[] distance;
	private Integer[] antecessor;
	private int initialNode;

	public BreadthFirstSearch(Graph graph) {
		super(graph);
		initialNode = 0;
	}

	@Override
	public void search(int vertice) {
		this.initialNode = vertice;
		this.queue = new LinkedList<Integer>();
		this.color = new int[getGraph().getNumNodes()];
		this.distance = new int[getGraph().getNumNodes()];
		this.antecessor = new Integer[getGraph().getNumNodes()];
		
		/* Color is already set to WHITE since starting value for all elements
		 * of the array is 0. Likewise, Predecessor is already set to null */
		for(int node : getNodes()) {
			this.distance[node] = Integer.MAX_VALUE;
		}
		
		this.color[vertice] = GRAY;
		this.distance[vertice] = 0;
		this.queue.add(vertice);
		
		while(!this.queue.isEmpty()) {
			int u = this.queue.poll();
			for (Edge e : this.getGraph().getAdjacentNodes(u)) {
				int v = e.getNodeTwo();
				if (this.color[v] == WHITE) {
					this.color[v] = GRAY;
					this.distance[v] = this.distance[u] + 1;
					this.antecessor[v] = u;
					this.queue.add(v);
				}
			}
			this.color[u] = BLACK;
		}
	}

	public int getInitialNode() {
		return initialNode;
	}

	public static void main(String[] args) {
	}

}