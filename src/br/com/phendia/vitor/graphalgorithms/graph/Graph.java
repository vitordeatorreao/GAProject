package br.com.phendia.vitor.graphalgorithms.graph;

import java.util.LinkedList;

public interface Graph {
	
	public void addEdge(int node1, int node2);
	
	public void addEdge(int node1, int node2, float weight);
	
	public LinkedList<Edge> getAdjacentNodes(int referenceNode);
	
	public int getNumNodes();

}
