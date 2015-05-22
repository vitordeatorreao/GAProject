package br.com.phendia.vitor.graphalgorithms.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

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

	public LinkedList<Edge> getOutEdges(int referenceNode) {
		return this.adjacency[referenceNode];
	}
	
	@Override
	public List<Integer> getAdjacentNodes(int referenceNode) {
		List<Integer> adjacentNodes = new LinkedList<Integer>();
		for (Edge e : getOutEdges(referenceNode)) {
			Integer adjacentNode = e.getNodeTwo();
			adjacentNodes.add(adjacentNode);
		}
		return adjacentNodes;
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

	public String toDOT() {
		StringBuilder sb = new StringBuilder();
		sb.append("digraph grafo {\n");
		for (int node = 0; node < this.getNumNodes(); node++) {
			for (Edge e : this.getOutEdges(node)) {
				int node1 = e.getNodeOne(), node2 = e.getNodeTwo();
				sb.append("\t" + node1 + " -> " + node2 + " [label=\""
						+ e.getWeight() + "\"];\n");
			}
		}
		sb.append("}");
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public JSONObject toJSON() {
		JSONObject jobj = new JSONObject();
		jobj.put("num_nodes", this.adjacency.length);
		JSONArray jarray = new JSONArray();
		for (int node = 0; node < this.adjacency.length; node++) {
			JSONArray adjacencies = new JSONArray();
			for (Edge edge : this.adjacency[node]) {
				adjacencies.add(edge.getNodeTwo());
			}
			jarray.add(adjacencies);
		}
		jobj.put("nodes", jarray);
		return jobj;
	}

	public String toJSONString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("\t\"num_nodes\" : " + this.adjacency.length + ",\n");
		sb.append("\t\"nodes\" : [\n");
		for (int i = 0;;) {
			sb.append("\t\t[");
			for (int j = 0;j < this.adjacency[i].size();) {
				Edge edge = this.adjacency[i].get(j);
				int successor = edge.getNodeTwo();
				if (++j >= this.adjacency[i].size()) {
					sb.append(successor);
					break;
				} else {
					sb.append(successor+", ");
					continue;
				}
			}
			if (++i >= this.adjacency.length) {
				sb.append("]\n");
				break;
			} else {
				sb.append("],\n");
				continue;
			}
		}
		sb.append("\t],\n");
		// sb.append("\t\"strongly_connected_components\" : [\n");
		// List<List<Integer>> scc = this.getStronglyConnectedComponents();
		// for (int i = 0;;) {
		// sb.append("\t\t[");
		// for (int node : scc.get(i)) {
		// sb.append(node + ", ");
		// }
		// if (++i >= scc.size()) {
		// sb.replace(sb.length() - 2, sb.length(), "]\n");
		// break;
		// } else {
		// sb.replace(sb.length() - 2, sb.length(), "],\n");
		// continue;
		// }
		// }
		// sb.append("\t]\n");
		sb.append("}\n");
		return sb.toString();
	}

	public String toString() {
		return this.toJSONString();
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
	public boolean isStronglyConnected() {
		return getStronglyConnectedComponents().size() == 1;
	}

	@Override
	public boolean hasParallelEdges() {
		if (this.properties == null) {
			this.properties = new BreadthFirstSearch(this);
		}
		return this.properties.hasParallelEdges();
	}

	@Override
	public boolean hasCycle() {
		DepthFirstSearch dfs = new DepthFirstSearch(this);
		dfs.getTopologicOrder();
		return dfs.hasCycle();
	}

	@Override
	public List<Integer> getTopologicOrder() {
		return (new DepthFirstSearch(this)).getTopologicOrder();
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
		List<Integer> topologicOrder = (new DepthFirstSearch(this))
				.getTopologicOrder();
		return (new DepthFirstSearch(this.getTransposed()))
				.getSCC(topologicOrder);
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
			// System.out.println(graph);
			StringWriter out = new StringWriter();
			JSONValue.writeJSONString(graph.toJSON(), out);
			String jsonText = out.toString();
			System.out.println(jsonText);
			System.out.println(graph.toDOT());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
