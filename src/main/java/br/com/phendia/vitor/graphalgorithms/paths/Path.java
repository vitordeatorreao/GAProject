package br.com.phendia.vitor.graphalgorithms.paths;

import java.util.ArrayList;

import br.com.phendia.vitor.graphalgorithms.graph.Graph;

public class Path extends ArrayList<Integer> {
	
	/**
	 * The Graph on which this Path was built.
	 */
	private Graph graph;
	
	public Path(Graph g) {
		this.graph = g;
	}
	
	public Graph getGraph() {
		return this.graph;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -8020542590197079271L;

	/**
	 * Returns true if this Path ends in the same node it started, or false
	 * otherwise
	 * 
	 * @return Whether this Path is a cycle or not
	 */
	public boolean isCycle() {
		if (this.get(0) == this.get(this.size() - 1)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Returns true if this Path goes through every node without repeating, or
	 * false otherwise
	 * 
	 * @return Whether this Path is a Hamiltonian Path or not
	 */
	public boolean isHamiltonian() {
		//TODO: implement a check to see if the path is hamiltonian
		return false;
	}

}
