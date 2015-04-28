package br.com.phendia.vitor.graphalgorithms.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

public class Graph {
	private LinkedList<Edge>[] adjacency;
	
	@SuppressWarnings("unchecked")
	public Graph(int numOfNodes) {
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
	}
	public void addEdge(int node1, int node2, float weight) {
		this.adjacency[node1].add(new Edge(node1, node2, weight));
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{\n");
		for(int i = 0; i < this.adjacency.length; i++) {
			sb.append("\t["+i+"] => ");
			for(Edge edge : this.adjacency[i]) {
				int successor = edge.getNodeTwo();
				sb.append(successor+", ");
			}
			sb.replace(sb.length()-2, sb.length(), "\n");
		}
		sb.append("}\n");
		return sb.toString();
	}
	
	public static Graph readFromFile(String filename) throws IOException {
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
		Graph graph = new Graph(numNodes);
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
			int node = (lineNumber-2);
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
	
	public static void main(String[] args) {
		Graph graph;
		try {
			graph = Graph.readFromFile("resources/badExample.gdf");
			System.out.println(graph);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
