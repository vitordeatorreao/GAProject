package br.com.phendia.vitor.graphalgorithms.graph;

public class CompleteDigraph extends PseudoDigraph {

	public CompleteDigraph(int numOfNodes) {
		super(numOfNodes);
		for (int i = 0; i < numOfNodes; i++) {
			for (int j = 0; j < numOfNodes; j++) {
				if (i != j) {
					this.addEdge(i, j);
				}
			}
		}
	}

	public static void main(String[] args) {
		
		CompleteDigraph g = new CompleteDigraph(5);
		System.out.println(g);

	}

}
