package br.com.phendia.vitor.graphalgorithms.graph;

import java.io.IOException;

import br.com.phendia.vitor.graphalgorithms.search.BreadthFirstSearch;

public class PowerGraph extends PseudoDigraph {
	
	private int[][] distancesToNodes;
	//For every index (which is a node), an array with the distances from every node to the index

	public PowerGraph(Graph graph, int power) {
		super(graph.getNumNodes());
		this.distancesToNodes = new int[graph.getNumNodes()][graph.getNumNodes()];
		for (int node = 0; node < graph.getNumNodes(); node++) {
			BreadthFirstSearch bfs = new BreadthFirstSearch(graph, node);
			distancesToNodes[node] = bfs.getDistances();
		}
		for (int node1 = 0; node1 < graph.getNumNodes(); node1++) {
			for (int node2 = 0; node2 < graph.getNumNodes(); node2++) {
				if (node1 == node2) {
					continue;
				}
				if (distancesToNodes[node1][node2] <= power) {
					addEdge(node1, node2);
				}
			}
		}
	}

	public static void main(String[] args) {
		
		try {
			Graph g = PseudoDigraph.readFromFile("resources/powerg1.gdf");
			System.out.println(g);
			System.out.println("2nd power graph");
			System.out.println(new PowerGraph(g, 2));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
