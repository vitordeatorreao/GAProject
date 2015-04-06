package br.com.phendia.vitor.graphalgorithms.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
	
	private HashMap<Node, List<Arc>> adjacencyList;
	
	public PseudoDigraph() {
		this.adjacencyList = new HashMap<Node, List<Arc>>();
	}
	
	public List<Node> getNodes() {
		List<Node> nodes = new ArrayList<Node>();
		for(Node n: this.adjacencyList.keySet()) {
			nodes.add(n);
		}
		return nodes;
	}

	@Override
	public void addEdge(float weight, Node... nodes) {
		if (nodes.length != 2) {
			throw new IllegalArgumentException("Edge must have two Nodes");
		}
		if( !this.adjacencyList.containsKey(nodes[0]) ) {
			this.addNode(nodes[0]);
		}
		if( !this.adjacencyList.containsKey(nodes[1]) ) {
			this.addNode(nodes[1]);
		}
		Arc edge = new Arc(nodes[0], nodes[1], weight);
		this.adjacencyList.get(nodes[0]).add(edge);
	}
	
	public void addEdge(float weight, long... nodes) {
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
		addEdge(weight, nodeArgs);
	}
	
	public void addEdge(Node... nodes) {
		addEdge(0f, nodes);
	}
	
	public void addEdge(long... nodes) {
		addEdge(0f, nodes);
	}

	@Override
	public void addNode(Node node) {
		if ( this.adjacencyList.containsKey(node.getId()) ) {
			throw new IllegalArgumentException("Node is already in Graph");
		}
		this.adjacencyList.put(node, new ArrayList<Arc>());
	}
	
	public void addNode(long node) {
		Node n = Node.getNodeFromId(node);
		if (n == null) {
			n = new Node(node);
		}
		addNode(n);
	}

	/**
	 * Adds a arbitrary number of nodes. With IDs from 0 to number minus one.
	 * @param numNodes The number of nodes to be added to the Graph
	 */
	public void addNode(int numNodes) {
		for (int i = 0; i < numNodes; i++) {
			long l = i;
			addNode(l);
		}
	}

	@Override
	public Iterable<Arc> getSuccessors(Node node) {
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
		List<Node> nodeOneSuccessors = new ArrayList<Node>();
		for(Arc edge : this.adjacencyList.get(nodeOne)) {
			nodeOneSuccessors.add(edge.getNodeTwo());
		}
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
			for(Arc edge : getSuccessors(n)) {
				Node successor = edge.getNodeTwo();
				sb.append(successor.getId()+", ");
			}
			sb.replace(sb.length()-2, sb.length(), "\n");
		}
		sb.append("}\n");
		return sb.toString();
	}

	/**
	 * Loads a Graph in memory according to the design  
	 * provided in the given file.
	 * @param filename The name of the file
	 * @throws IOException In case the file does not exist or cannot be read
	 */
	public static PseudoDigraph readFromFile(String filename) throws IOException {
		PseudoDigraph graph = new PseudoDigraph();
		File file = new File(filename);
		if (!file.exists())
			throw new IOException("File does not exist");
		if (!file.canRead())
			throw new IOException("File cannot be read");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		long lineNumber = 1;
		if (line == null) {
			br.close();
			throw new IllegalArgumentException("Syntax Error at line " 
					+ lineNumber + ": Expected at least one line.");
		}
		int numNodes = 0;
		try {
			numNodes = Integer.parseInt(line);
		} catch (NumberFormatException e) {
			br.close();
			throw new IllegalArgumentException("Syntax Error at line " 
					+ lineNumber + ": Expected an integer");
		}
		graph.addNode(numNodes);
		for (int i = 0; i < numNodes; i++) {
			line = br.readLine();
			lineNumber++;
			if (line == null) {
				continue;
				/*br.close();
				throw new IllegalArgumentException("Syntax Error at line " 
						+ lineNumber + ": Expected at least "+(numNodes+1) 
						+ "lines in the file");*/
				//In case there are nodes with degree 0, there will be null lines
			}
			Node node = Node.getNodeFromId(lineNumber-2);
			String[] nodes = line.split(" ");
			for (int j = 0; j < nodes.length; j++) {
				if (nodes[j].equals(""))
					continue;
				try {
					long nid = Long.parseLong(nodes[j]);
					Node node2 = Node.getNodeFromId(nid);
					graph.addEdge(node, node2);
				} catch (NumberFormatException e) {
					br.close();
					throw new IllegalArgumentException("Syntax Error at line " 
							+ lineNumber + ": Expected only integers");
				}
			}
		}
		br.close();
		return graph;
	}
	
	public static void main(String[] args) {
		/*PseudoDigraph pd = new PseudoDigraph();
		pd.addEdge(1, 2);
		pd.addEdge(2, 3);
		pd.addEdge(1, 3);
		System.out.println(pd);
		
		PseudoDigraph pd2 = new PseudoDigraph();
		pd2.addNode(1);
		pd2.addNode(2);
		pd2.addNode(3);
		pd2.addEdge(3, 1);
		pd2.addEdge(2, 1);
		pd2.addEdge(1, 3);
		pd2.addEdge(3, 2);
		System.out.println(pd2);
		
		System.out.println(pd2.existsEdgeBetween(Node.getNodeFromId(1), 
				Node.getNodeFromId(2)));*/
		
		String filename = "resources/badExample.gdf";
			try {
				System.out.println(PseudoDigraph.readFromFile(filename));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
