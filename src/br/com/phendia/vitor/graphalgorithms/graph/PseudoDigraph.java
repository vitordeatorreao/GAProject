package br.com.phendia.vitor.graphalgorithms.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import br.com.phendia.vitor.graphalgorithms.search.BreadthFirstSearch;
import br.com.phendia.vitor.graphalgorithms.search.DepthFirstSearch;

public class PseudoDigraph implements Graph {

	private LinkedList<Edge>[] adjacency;
	private BreadthFirstSearch properties;

	@SuppressWarnings("unchecked")
	public PseudoDigraph(int numOfNodes) {
		this.adjacency = (LinkedList<Edge>[]) new LinkedList[numOfNodes];
		for (int i = 0; i < numOfNodes; i++) {
			this.adjacency[i] = new LinkedList<Edge>();
		}
	}

	public LinkedList<Edge> getAdjacentNodes(int referenceNode) {
		return this.adjacency[referenceNode];
	}

	public boolean existsEdgeBetween(int node1, int node2) {
		LinkedList<Edge> list = this.adjacency[node1];
		for (int i = 0; i < list.size(); i++) {
			Edge edge = list.get(i);
			if (edge.getNodeTwo() == node2) {
				return true;
			}
		}
		return false;
	}

	public void addEdge(int node1, int node2) {
		this.adjacency[node1].add(new Edge(node1, node2));
		resetProperties();
	}

	public void addEdge(int node1, int node2, float weight) {
		this.adjacency[node1].add(new Edge(node1, node2, weight));
		resetProperties();
	}

	/**
	 * This method must be called any time there is a change in the graph's
	 * structure. This will cause another run of the Search Algorithm.
	 */
	private void resetProperties() {
		this.properties = null;
	}

	@Override
	public int getNumNodes() {
		return this.adjacency.length;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("\t\"num_nodes\" : " + this.adjacency.length + ",\n");
		sb.append("\t\"nodes\" : [\n");
		for (int i = 0;;) {
			sb.append("\t\t[");
			for (Edge edge : this.adjacency[i]) {
				int successor = edge.getNodeTwo();
				sb.append(successor + ", ");
			}
			if (++i >= this.adjacency.length) {
				sb.replace(sb.length() - 2, sb.length(), "]\n");
				break;
			} else {
				sb.replace(sb.length() - 2, sb.length(), "],\n");
				continue;
			}
		}
		sb.append("\t],\n");
		sb.append("\t\"strongly_connected_components\" : [\n");
		List<List<Integer>> scc = this.getStronglyConnectedComponents();
		for (int i = 0;;) {
			sb.append("\t\t[");
			for (int node : scc.get(i)) {
				sb.append(node + ", ");
			}
			if (++i >= scc.size()) {
				sb.replace(sb.length() - 2, sb.length(), "]\n");
				break;
			} else {
				sb.replace(sb.length() - 2, sb.length(), "],\n");
				continue;
			}
		}
		sb.append("\t]\n");
		sb.append("}\n");
		return sb.toString();
	}

	public static PseudoDigraph readFromFile(String filename)
			throws IOException {
		File file = new File(filename);
		if (!file.exists())
			throw new IOException("File does not exist");
		if (!file.canRead())
			throw new IOException("File cannot be read");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		int lineNumber = 1;
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
		PseudoDigraph graph = new PseudoDigraph(numNodes);
		for (int i = 0; i < numNodes; i++) {
			line = br.readLine();
			lineNumber++;
			if (line == null) {
				continue;
				/*
				 * br.close(); throw new
				 * IllegalArgumentException("Syntax Error at line " + lineNumber
				 * + ": Expected at least "+(numNodes+1) + "lines in the file");
				 */
				// In case there are nodes with degree 0, there will be null
				// lines
			}
			int node = (lineNumber - 2);
			String[] nodes = line.split(" ");
			for (int j = 0; j < nodes.length; j++) {
				if (nodes[j].equals(""))
					continue;
				try {
					int node2 = Integer.parseInt(nodes[j]);
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

	@Override
	public boolean hasLoop() {
		if (this.properties == null) {
			this.properties = new BreadthFirstSearch(this);
		}
		return this.properties.hasLoop();
	}

	@Override
	public boolean isSymmetric() {
		if (this.properties == null) {
			this.properties = new BreadthFirstSearch(this);
		}
		return this.properties.isSymmetric();
	}

	@Override
	public boolean hasParallelEdges() {
		if (this.properties == null) {
			this.properties = new BreadthFirstSearch(this);
		}
		return this.properties.hasParallelEdges();
	}

	@Override
	public Graph getTransposed() {
		if (this.properties == null) {
			this.properties = new BreadthFirstSearch(this);
		}
		return this.properties.getTransposedGraph();
	}

	@Override
	public List<List<Integer>> getStronglyConnectedComponents() {
		List<Integer> topologicOrder = (new DepthFirstSearch(this)).getTopologicOrder();
		return (new DepthFirstSearch(this.getTransposed())).getSCC(topologicOrder);
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof PseudoDigraph))
			return false;
		if (obj == this) {
			return true;
		}
		PseudoDigraph graph = (PseudoDigraph) obj;
		if (graph.getNumNodes() != this.getNumNodes()) {
			return false;
		}
		for (int i = 0; i < this.getNumNodes(); i++) {
			if (graph.adjacency[i].size() != this.adjacency[i].size()) {
				return false;
			}
			for (int j = 0; j < this.adjacency[i].size(); j++) {
				if (!this.adjacency[i].get(j).equals(graph.adjacency[i].get(j))) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		PseudoDigraph graph;
		try {
			graph = PseudoDigraph.readFromFile("resources/cfcs.gdf");
			System.out.println(graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
