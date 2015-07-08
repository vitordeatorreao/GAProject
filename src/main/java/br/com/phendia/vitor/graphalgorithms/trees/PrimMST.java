package br.com.phendia.vitor.graphalgorithms.trees;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import br.com.phendia.vitor.graphalgorithms.graph.EdgeDoesNotExistException;
import br.com.phendia.vitor.graphalgorithms.graph.Graph;
import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;
import br.com.phendia.vitor.graphalgorithms.view.GraphVisualizer;

public class PrimMST implements Comparator<Integer> {

	private float[] keys;
	private int[] pi;
	private Graph graph;

	public PrimMST(Graph g) {
		this(g, 0);
	}
	
	public PrimMST(Graph g, int r) {
		this.graph = g;
		this.keys = new float[g.getNumNodes()];
		this.pi = new int[g.getNumNodes()];
		PriorityQueue<Integer> priorityQueue =
				new PriorityQueue<Integer>(g.getNumNodes(), this);
		for (int node = 0; node < g.getNumNodes(); node++) {
			keys[node] = Float.MAX_VALUE;
			pi[node] = -1;
			priorityQueue.add(node);
		}
		keys[r] = 0;
		while (!priorityQueue.isEmpty()) {
			int u = priorityQueue.remove();
			for (int v : g.getAdjacentNodes(u)) {
				try {
					if (priorityQueue.contains(v)
							&& g.getEdgeWeight(u, v) < keys[v]) {
						pi[v] = u;
						keys[v] = g.getEdgeWeight(u, v);
						//Update the Queue
						priorityQueue.remove(v);
						priorityQueue.add(v);
					}
				} catch (EdgeDoesNotExistException e) {
					System.out.println("ops!");
					continue;
				}
			}
		}
	}

	@Override
	public int compare(Integer o1, Integer o2) {
		if (keys[o1] < keys[o2]) {
			return -1;
		} else if (keys[o1] > keys[o2]) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public List<Integer> getTree() {
		ArrayList<Integer> result = new ArrayList<Integer>();
		for (int i = 0; i < this.pi.length; i++) {
			result.add(this.pi[i]);
		}
		return result;
	}
	
	public Graph getMinimumSpanningTree() {
		PseudoDigraph graph = new PseudoDigraph(this.graph.getNumNodes());
		for (int i = 0; i < pi.length; i++) {
			if (pi[i] != -1) {
				try {
					float w = this.graph.getEdgeWeight(pi[i], i);
					graph.addEdge(pi[i], i, w);
					graph.addEdge(i, pi[i], w);
				} catch (EdgeDoesNotExistException e) {
					continue;
				}
			}
		}
		return graph;
	}
	
	public static void main(String[] args) {
		PseudoDigraph g;
		try {
			g = PseudoDigraph.readFromFileWithWeights("examples/WeightedGraph3.wgdf");
			int r = 0;
			PrimMST primMST = new PrimMST(g, r);
			(new GraphVisualizer((PseudoDigraph) primMST.getMinimumSpanningTree())).viewGraphOnScreen();
			System.out.println(primMST.getTree());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
