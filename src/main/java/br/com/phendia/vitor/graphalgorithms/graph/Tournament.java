package br.com.phendia.vitor.graphalgorithms.graph;

import java.util.Random;

import br.com.phendia.vitor.graphalgorithms.paths.HamiltonianPath;
import br.com.phendia.vitor.graphalgorithms.paths.Path;

public class Tournament extends PseudoDigraph {

	private Graph originalGraph;

	public Tournament(CompleteDigraph g) {
		super(g.getNumNodes());
		Random rand = new Random();
		for (int i = 0; i < g.getNumNodes(); i++) {
			for (int j : g.getAdjacentNodes(i)) {
				if (this.existsEdgeBetween(i, j)
						|| this.existsEdgeBetween(j, i)) {
					continue;
				} else {
					if (rand.nextBoolean()) {
						this.addEdge(i, j);
					} else {
						this.addEdge(j, i);
					}
				}
			}
		}
		this.originalGraph = g;
	}

	public Tournament(PseudoDigraph graph) {
		super(graph.getNumNodes());
		for (int node = 0; node < graph.getNumNodes(); node++) {
			for (Edge edge : graph.getOutEdges(node)) {
				this.addEdge(edge.getNodeOne(), edge.getNodeTwo(),
						edge.getWeight());
			}
		}
	}

	public Tournament(int numNodes) {
		super(numNodes);
	}

	public Graph getOriginalGraph() {
		return this.originalGraph;
	}

	public Tournament copySubgraph(int numNodes) {
		Tournament graph = new Tournament(numNodes);
		for (int node1 = 0; node1 < numNodes; node1++) {
			for (Edge edge : this.getOutEdges(node1)) {
				int node2 = edge.getNodeTwo();
				if (node2 < numNodes) {
					graph.addEdge(node1, node2, edge.getWeight());
				}
			}
		}
		return graph;
	}

	public static void main(String[] args) {
		try {
			CompleteDigraph g = new CompleteDigraph(5);
			Tournament tournament = new Tournament(g);
			System.out.println(tournament);
			Path hapath = HamiltonianPath.build(tournament);
			System.out.println(hapath);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
