package br.com.phendia.vitor.graphalgorithms.graph;

public class Edge {
	
	private int nodeOne;
	private int nodeTwo;
	private float weight;
	
	public Edge(int node1, int node2, float weight) {
		this.nodeOne = node1;
		this.nodeTwo = node2;
		this.weight = weight;
	}
	
	public Edge(int node1, int node2) {
		this(node1, node2, 1f);
	}
	
	public int getNodeOne() {
		return this.nodeOne;
	}
	
	public int getNodeTwo() {
		return this.nodeTwo;
	}
	
	public float getWeight() {
		return this.weight;
	}
	
	public void setWeight(float newWeight) {
		this.weight = newWeight;
	}
	
	public boolean equals(Object obj) {
		if (! (obj instanceof Edge))
			return false;
		if (obj == this)
			return true;
		
		Edge edge = (Edge) obj;
		if (edge.getNodeOne() == this.getNodeOne() && 
				edge.getNodeTwo() == this.getNodeTwo() &&
				edge.getWeight() == this.getWeight()) {
			return true;
		} else {
			return false;
		}
	}

}
