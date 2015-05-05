package br.com.phendia.vitor.graphalgorithms.graph;

import java.util.LinkedList;
import java.util.List;

public interface Graph {
	
	public void addEdge(int node1, int node2);
	
	public void addEdge(int node1, int node2, float weight);
	
	public LinkedList<Edge> getAdjacentNodes(int referenceNode);
	
	public boolean existsEdgeBetween(int node1, int node2);
	
	public boolean hasLoop();
	
	public boolean isSymmetric();
	
	public boolean isStronglyConnected();
	
	public boolean hasParallelEdges();
	
	public boolean hasCycle();
	
	public List<Integer> getTopologicOrder();
	
	public Graph getTransposed();
	
	public List<List<Integer>> getStronglyConnectedComponents();
	
	public int getNumNodes();

}
