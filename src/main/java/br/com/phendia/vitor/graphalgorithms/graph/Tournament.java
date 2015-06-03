package br.com.phendia.vitor.graphalgorithms.graph;

import java.util.Random;

public class Tournament extends PseudoDigraph { 
	
	public Tournament(Graph g) {
		super(g.getNumNodes());
		Random rand = new Random();
		for (int i = 0; i < g.getNumNodes(); i++) {
			for (int j : g.getAdjacentNodes(i)) {
				if (this.existsEdgeBetween(i, j) || this.existsEdgeBetween(j, i)) {
					continue;
				} else {
					if (rand.nextBoolean()) {
						this.addEdge(i, j);
					} else {
						this.addEdge(j, i);
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		try {
			CompleteDigraph g = new CompleteDigraph(5);
			Tournament tournament = new Tournament(g);
			System.out.println(tournament);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
