package br.com.phendia.vitor.graphalgorithms.exercises;

import java.io.IOException;
import br.com.phendia.vitor.graphalgorithms.graph.Edge;
import br.com.phendia.vitor.graphalgorithms.graph.Graph;
import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;
import br.com.phendia.vitor.graphalgorithms.paths.BellmanFord;

public class DifferenceConstraintsSystems {

	private Graph diffConstrainsGraph, problemGraph;
	private BellmanFord bellmanFord;

	public DifferenceConstraintsSystems(String diffConstrainsGraphFile) {
		try {
			this.diffConstrainsGraph = PseudoDigraph
					.readFromFileWithWeights(diffConstrainsGraphFile);
			problemGraph = new PseudoDigraph(
					this.diffConstrainsGraph.getNumNodes() + 1);
			for (int node = 0; node < this.diffConstrainsGraph.getNumNodes(); node++) {
				for (Edge edge : this.diffConstrainsGraph.getOutEdges(node)) {
					problemGraph.addEdge(edge.getNodeOne(), edge.getNodeTwo(),
							edge.getWeight());
				}
			}
			// Add a node v_0, that in our case will be the v_n-1
			for (int node = 0; node < this.diffConstrainsGraph.getNumNodes(); node++) {
				problemGraph
						.addEdge(problemGraph.getNumNodes() - 1, node, 0.0f);
			}
			bellmanFord = new BellmanFord(problemGraph,
					problemGraph.getNumNodes() - 1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getAnswer() {
		String answer;
		if (bellmanFord.hasNegativeCycles()) {
			answer = "There are no solutions to this system of difference constraints";
		} else {
			answer = "{\n";
			for (int node = 0; node < diffConstrainsGraph.getNumNodes(); node++) {
				answer += "\tx" + node + " = " + bellmanFord.getCosts().get(node) + "\n"; 
			}
			answer += "}";
		}
		return answer;
	}
	
	public String getDifferenceConstraintsSystem() {
		String result = "{\n";
		for (int node = 0; node < this.diffConstrainsGraph.getNumNodes(); node++) {
			for (Edge edge : this.diffConstrainsGraph.getOutEdges(node)) {
				int x1, x2;
				float constraint;
				x1 = edge.getNodeTwo();
				x2 = edge.getNodeOne();
				constraint = edge.getWeight();
				result += "\tx" + x1 + " - x" + x2 + " <= " + constraint + "\n";
			}
		}
		return result += "}"; 
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			args = new String[1];
			//args[0] = "examples/DiffConstrGraph1.wgdf";
			args[0] = "examples/NegativeCycleGraph.wgdf";
		}
		DifferenceConstraintsSystems dcs = new DifferenceConstraintsSystems(
				args[0]);
		System.out.print("System of Difference Constraints: ");
		System.out.println(dcs.getDifferenceConstraintsSystem());
		System.out.print("Solution: ");
		System.out.println(dcs.getAnswer());
	}

}
