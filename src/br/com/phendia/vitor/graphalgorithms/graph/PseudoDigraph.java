package br.com.phendia.vitor.graphalgorithms.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class that implements the Graph interface and respects 
 * the specifics of the Project.<br>
 * According to the Project's specification, it should work with any
 * Pseudo-digraph. And it must use adjacency list to be implemented. 
 * @author V&iacute;tor de Albuquerque Torre&atilde;o
 *
 */
public class PseudoDigraph implements Graph {
	
	private HashMap<Node, List<Node>> adjacencyList;
	
	public PseudoDigraph() {
		this.adjacencyList = new HashMap<Node, List<Node>>();
	}
	
	public List<Node> getNodes() {
		List<Node> nodes = new ArrayList<Node>();
		for(Node n: this.adjacencyList.keySet()) {
			nodes.add(n);
		}
		return nodes;
	}

	@Override
	public void addEdge(Node... nodes) {
		if (nodes.length != 2) {
			throw new IllegalArgumentException("Edge must have two Nodes");
		}
		if( !this.adjacencyList.containsKey(nodes[0]) ) {
			this.addNode(nodes[0]);
		}
		if( !this.adjacencyList.containsKey(nodes[1]) ) {
			this.addNode(nodes[1]);
		}
		this.adjacencyList.get(nodes[0]).add(nodes[1]);
	}
	
	public void addEdge(long... nodes) {
		if (nodes.length != 2) {
			throw new IllegalArgumentException("Edge must have two Nodes");
		}
		Node[] nodeArgs = new Node[nodes.length];
		for(int i = 0; i < nodes.length; i++) {
			Node node = Node.getNodeFromId(nodes[i]);
			if (node == null) {
				node = new Node(nodes[i]);
			}
			nodeArgs[i] = node;
		}
		addEdge(nodeArgs);
	}

	@Override
	public void addNode(Node node) {
		if ( this.adjacencyList.containsKey(node.getId()) ) {
			throw new IllegalArgumentException("Node is already in Graph");
		}
		this.adjacencyList.put(node, new ArrayList<Node>());
	}
	
	public void addNode(long node) {
		Node n = Node.getNodeFromId(node);
		if (n == null) {
			n = new Node(node);
		}
		addNode(n);
	}

	@Override
	public Iterable<Node> getSuccessors(Node node) {
		if (!this.adjacencyList.containsKey(node)) {
			throw new IllegalArgumentException("This graph doesn't contain the given Node");
		}
		return this.adjacencyList.get(node);
	}

	@Override
	public boolean existsEdgeBetween(Node nodeOne, Node nodeTwo) {
		if (!this.adjacencyList.containsKey(nodeOne) || 
				!this.adjacencyList.containsKey(nodeTwo)) {
			return false;
		}
		List<Node> nodeOneSuccessors = this.adjacencyList.get(nodeOne);
		if (nodeOneSuccessors.contains(nodeTwo)) {
			return true;
		} else {
			return false;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for(Node n : getNodes()) {
			sb.append("\t["+n.getId()+"] => ");
			for(Node successor : getSuccessors(n)) {
				sb.append(successor.getId()+", ");
			}
			sb.replace(sb.length()-2, sb.length()-1, "\n");
		}
		sb.append("}\n");
		return sb.toString();
	}

	/**
	 * Loads a Graph in memory according to the design  
	 * provided in the given file.
	 * @param filename The name of the file
	 */
	public void readFromFile(String filename) {}
	
	public static void main(String[] args) {
		PseudoDigraph pd = new PseudoDigraph();
		pd.addEdge(1, 2);
		pd.addEdge(2, 3);
		pd.addEdge(1, 3);
		System.out.println(pd);
		
		Node.getNodeFromId(8);
	}

}
