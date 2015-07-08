package br.com.phendia.vitor.graphalgorithms.exercises;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.builder.HashCodeBuilder;

import br.com.phendia.graphalgorithms.trees.PrimMST;
import br.com.phendia.vitor.graphalgorithms.graph.Edge;
import br.com.phendia.vitor.graphalgorithms.graph.Graph;
import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;

public class DestroyedBridges {

	private Graph intactBridgesGraph, destroyedBridgesGraph, problemGraph,
			primAnswer;
	private PrimMST primAlg;
	private Set<DestroyedBridges.Bridge> resultSet;

	public DestroyedBridges(String intactBridgesFile,
			String destroyedBridgesFile) {
		try {
			intactBridgesGraph = PseudoDigraph
					.readFromFileWithWeights(intactBridgesFile);
			destroyedBridgesGraph = PseudoDigraph
					.readFromFileWithWeights(destroyedBridgesFile);
			problemGraph = new PseudoDigraph(intactBridgesGraph.getNumNodes());
			for (int node = 0; node < intactBridgesGraph.getNumNodes(); node++) {
				for (Edge edge : intactBridgesGraph.getOutEdges(node)) {
					problemGraph.addEdge(edge.getNodeOne(), edge.getNodeTwo(),
							0);
				}
			}
			for (int node = 0; node < destroyedBridgesGraph.getNumNodes(); node++) {
				for (Edge edge : destroyedBridgesGraph.getOutEdges(node)) {
					problemGraph.addEdge(edge.getNodeOne(), edge.getNodeTwo(),
							edge.getWeight());
				}
			}
			primAlg = new PrimMST(problemGraph);
			primAnswer = primAlg.getMinimumSpanningTree();
			resultSet = new HashSet<DestroyedBridges.Bridge>();
			for (int node = 0; node < primAnswer.getNumNodes(); node++) {
				for (Edge edge : primAnswer.getOutEdges(node)) {
					if (destroyedBridgesGraph.existsEdgeBetween(
							edge.getNodeOne(), edge.getNodeTwo())) {
						Bridge bridge = new Bridge(edge.getNodeOne(),
								edge.getNodeTwo(), edge.getWeight());
						resultSet.add(bridge);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Graph getProblemGraph() {
		return problemGraph;
	}

	public Graph getPrimAnswer() {
		return primAnswer;
	}

	public Set<DestroyedBridges.Bridge> getResultSet() {
		return resultSet;
	}

	public class Bridge {

		private int nodeOne, nodeTwo;
		private float cost;

		public Bridge(int node1, int node2, float cost) {
			this.nodeOne = node1;
			this.nodeTwo = node2;
			this.cost = cost;
		}

		public int getNodeOne() {
			return nodeOne;
		}

		public int getNodeTwo() {
			return nodeTwo;
		}

		public float getCost() {
			return cost;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Bridge))
				return false;
			if (obj == this)
				return true;

			Bridge bridge = (Bridge) obj;
			if (bridge.nodeOne == this.nodeOne
					&& bridge.nodeTwo == this.nodeTwo) {
				return true;
			} else if (bridge.nodeTwo == this.nodeOne
					&& this.nodeTwo == bridge.nodeOne) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public int hashCode() {
			// you pick a hard-coded, randomly chosen, non-zero, odd number
			// ideally different for each class
			int i = this.nodeOne + this.nodeTwo;
			return new HashCodeBuilder(17, 37).
					append(i).
					toHashCode();
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("{ilha" + this.nodeOne + ", ilha" + this.nodeTwo + "}");
			return sb.toString();
		}

	}

	public static void main(String[] args) {
		
		if (args.length < 2) {
			args = new String[2];
			args[0] = "examples/intactBridges.wgdf";
			args[1] = "examples/destroyedBridges.wgdf";
		}
		
		DestroyedBridges db = new DestroyedBridges(args[0], args[1]);
		System.out.print("It is best to rebuild the following bridges: ");
		System.out.println(db.getResultSet());

	}

}
