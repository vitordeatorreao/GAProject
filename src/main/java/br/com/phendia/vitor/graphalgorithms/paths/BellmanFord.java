package br.com.phendia.vitor.graphalgorithms.paths;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.phendia.vitor.graphalgorithms.graph.Edge;
import br.com.phendia.vitor.graphalgorithms.graph.Graph;
import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;

public class BellmanFord {

	public static final float MAX_VALUE = 1000000;

	private float costs[];
	private int predecessors[];
	private boolean hasNegativeCycles;

	public BellmanFord(Graph g) {
		this(g, 0);
	}

	public BellmanFord(Graph g, int source) {
		hasNegativeCycles = false;
		costs = new float[g.getNumNodes()];
		predecessors = new int[g.getNumNodes()];
		for (int v = 0; v < g.getNumNodes(); v++) {
			costs[v] = MAX_VALUE;
			predecessors[v] = -1;
		}
		costs[source] = 0;
		for (int i = 0; i < g.getNumNodes() - 1; i++) {
			// Iterate over all edges V-1 times
			for (int node = 0; node < g.getNumNodes(); node++) {
				for (Edge edge : g.getOutEdges(node)) {
					relax(edge);
				}
			}
		}
		// Iterate over all edges to check for negative cycles
		for (int node = 0; node < g.getNumNodes(); node++) {
			for (Edge edge : g.getOutEdges(node)) {
				int x 	= edge.getNodeOne();
				int y 	= edge.getNodeTwo();
				float w = edge.getWeight();
				if ((costs[x] + w) < costs[y]) {
					hasNegativeCycles = true;
					break;
				}
			}
		}
	}

	public List<Float> getCosts() {
		ArrayList<Float> costs = new ArrayList<Float>();
		for (Float f : this.costs) {
			costs.add(f);
		}
		return costs;
	}

	public List<Integer> getPredecessors() {
		ArrayList<Integer> predecessors = new ArrayList<Integer>();
		for (Integer i : this.predecessors) {
			predecessors.add(i);
		}
		return predecessors;
	}

	public boolean hasNegativeCycles() {
		return hasNegativeCycles;
	}

	private void relax(Edge edge) {
		int		x = edge.getNodeOne();
		int		y = edge.getNodeTwo();
		float 	w = edge.getWeight();
		if ((costs[x] + w) < costs[y]) {
			costs[y] = costs[x] + w;
			predecessors[y] = x;
		}
	}

	public static void main(String[] args) {

		Graph g;
		try {
			g = PseudoDigraph.readFromFileWithWeights("examples/NegativeCycleGraph.wgdf");
			BellmanFord bellmanFord = new BellmanFord(g, 0);
			System.out.println("Has Negative Cycles? "
					+ bellmanFord.hasNegativeCycles());
			System.out.println("Predecessors: "
					+ bellmanFord.getPredecessors());
			System.out.println("Costs: " + bellmanFord.getCosts());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
