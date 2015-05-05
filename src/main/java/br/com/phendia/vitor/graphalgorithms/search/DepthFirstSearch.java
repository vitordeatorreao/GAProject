package br.com.phendia.vitor.graphalgorithms.search;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import br.com.phendia.vitor.graphalgorithms.graph.Edge;
import br.com.phendia.vitor.graphalgorithms.graph.Graph;
import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;

public class DepthFirstSearch extends GraphSearch {
	
	private int[] color;
	private Integer[] predecessor;
	private int[] initialTime; // Moment the node (index) is visited
	private int[] finalTime; // Moment the node's (index) neighbors are visited
	private LinkedList<Integer> visitedNodes;

	private int time;
	
	private Boolean hasCycle;
	private int initialNode;

	public DepthFirstSearch(Graph graph) {
		super(graph);
	}
	
	//ORDEM-TOP
	public List<Integer> getTopologicOrder() {
		this.visitedNodes = new LinkedList<Integer>();
		this.initialNode = 0;
		this.setUpNewSearch();
		for (int node = 0; node < getGraph().getNumNodes(); node++) {
			if (this.color[node] == WHITE)
				this.dfsVisit(node);
		}
		return this.visitedNodes;
	}
	
	//DFS-START*
	public List<List<Integer>> getSCC(List<Integer> nodes) {
		List<List<Integer>> result = new LinkedList<List<Integer>>();
		this.setUpNewSearch();
		for (int node : nodes) {
			if (this.color[node] == WHITE) {
				this.visitedNodes = new LinkedList<Integer>();
				this.dfsVisit(node);
				result.add(visitedNodes);
			}
		}
		return result;
	}
	
	private void setUpNewSearch() {
		this.time = 0;
		this.color = new int[getGraph().getNumNodes()];
		this.predecessor = new Integer[getGraph().getNumNodes()];
		this.initialTime = new int[getGraph().getNumNodes()];
		this.finalTime = new int[getGraph().getNumNodes()];
		
		this.hasCycle = false;

		for (int node = 0; node < getGraph().getNumNodes(); node++) {
			this.initialTime[node] = -1;
			this.finalTime[node] = -1;
		}
		// Predecessor and color are already set to the correct default value
	}

	@Override
	public void search(int vertice) {
		this.initialNode = vertice;
		this.setUpNewSearch();
		
		this.dfsVisit(vertice);
	}
	
	private void dfsVisit(int u) {
		this.color[u] = GRAY;
		this.initialTime[u] = this.time++;
		for (Edge e : getGraph().getAdjacentNodes(u)) {
			int v = e.getNodeTwo();
			if (this.color[v] == GRAY && v != this.predecessor[u]) {
				this.hasCycle = true;
			}
			if (this.color[v] == WHITE) {
				this.predecessor[v] = u;
				dfsVisit(v);
			}
		}
		this.color[u] = BLACK;
		this.finalTime[u] = this.time++;
		if (this.visitedNodes != null) {
			this.visitedNodes.push(u);
		}
	}

	public Boolean hasCycle() {
		return hasCycle;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		sb.append("\t\"initial_node\" : " + this.initialNode + ",\n");
		sb.append("\t\"has_cycle\" : " + this.hasCycle + ",\n");
		if (this.visitedNodes != null) {
			sb.append("\t\"topological_order\" : [");
			for (int i = 0;;) {
				sb.append(this.visitedNodes.get(i));
				if (++i >= this.visitedNodes.size()) {
					sb.append("]\n");
					break;
				} else {
					sb.append(", ");
					continue;
				}
			}
		}
		sb.append("\t\"nodes\" : " + "[\n");
		for (int i = 0;;) {
			sb.append("\t\t" + i + " : {\n");
			switch (this.color[i]) {
			case GRAY:
				sb.append("\t\t\t\"color\" : \"gray\"\n");
				break;
			case BLACK:
				sb.append("\t\t\t\"color\" : \"black\"\n");
				break;
			case WHITE:
			default:
				sb.append("\t\t\t\"color\" : \"white\"\n");
				break;
			}
			sb.append("\t\t\t\"initial_time\" : " + this.initialTime[i] + "\n");
			sb.append("\t\t\t\"final_time\" : " + this.finalTime[i] + "\n");
			sb.append("\t\t\t\"predecessor\" : " + this.predecessor[i] + "\n");
			sb.append("\t\t}");
			if (++i >= getGraph().getNumNodes()) {
				sb.append("\n");
				break;
			} else {
				sb.append(",\n");
				continue;
			}
		}
		sb.append("\t]\n");
		sb.append("}");
		return sb.toString();
	}

	public static void main(String[] args) {
		
		try {
			Graph g = PseudoDigraph.readFromFile("resources/cfcs.gdf");
			DepthFirstSearch dfs = new DepthFirstSearch(g);
			dfs.getTopologicOrder();
			System.out.println(dfs);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

}
