package br.com.phendia.vitor.graphalgorithms.graph;

public class HamiltonianPath {

	public static Path build(Tournament graph) {
		Path hamiltonianPath = new Path(graph);
		if (graph.getNumNodes() == 2) {
			if (graph.existsEdgeBetween(0, 1)) {
				hamiltonianPath.add(0);
				hamiltonianPath.add(1);
			} else {
				hamiltonianPath.add(1);
				hamiltonianPath.add(0);
			}
		} else {
			Tournament subTournament = graph.copySubgraph(graph.getNumNodes() - 1);
			hamiltonianPath = build(subTournament);
			int v = graph.getNumNodes() - 1;
			//V is the last node from graph which was taken off to form subTournament
			int mu1 = hamiltonianPath.get(0);
			int muk = hamiltonianPath.get(hamiltonianPath.size()-1);
			if (graph.existsEdgeBetween(v, mu1)) {
				hamiltonianPath.add(0, v);
			} else if (graph.existsEdgeBetween(muk, v)) {
				hamiltonianPath.add(v);
			} else {
				int i = 0;
				for (; i < hamiltonianPath.size(); i++) {
					if (graph.existsEdgeBetween(v, hamiltonianPath.get(i))) {
						break;
					}
				}
				hamiltonianPath.add(i, v);
			}
		}
		return hamiltonianPath;
	}
}
