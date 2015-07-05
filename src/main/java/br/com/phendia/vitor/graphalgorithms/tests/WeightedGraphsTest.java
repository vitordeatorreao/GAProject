package br.com.phendia.vitor.graphalgorithms.tests;

import java.io.IOException;

import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;
import br.com.phendia.vitor.graphalgorithms.view.GraphVisualizer;

public class WeightedGraphsTest {

	public static void main(String[] args) {
		
		try {
			PseudoDigraph graph = PseudoDigraph.readFromFileWithWeights("examples/WeightedGraph1.wgdf");
			System.out.println(graph);
			GraphVisualizer gv = new GraphVisualizer(graph);
			gv.viewGraphOnScreen();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
