package br.com.phendia.vitor.graphalgorithms.view;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;
import org.graphstream.stream.file.FileSourceDOT;

import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;

public class GraphVisualizer {
	
	public static final String CSS = "node {"
			+ "shape: circle; size: 20px, 20px; "
			+ "fill-color: white; stroke-mode: plain; "
			+ "stroke-color: black; stroke-width: 2px;"
			+ "} edge {"
			+ "text-background-mode: plain; "
			+ "text-background-color: white;"
			+ "}";
	
	private Graph graph;

	public GraphVisualizer(PseudoDigraph graph) {
		this.graph = new DefaultGraph("g");
		ByteArrayInputStream bais = 
				new ByteArrayInputStream(graph.toDOT().getBytes());
		FileSourceDOT fs = new FileSourceDOT();
		fs.addSink(this.graph);
		try {
			fs.readAll(bais);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			fs.removeSink(this.graph);
		}
		for (Node n : this.graph.getNodeSet()) {
			n.addAttribute("ui.label", n.getId());
		}
		System.setProperty("org.graphstream.ui.renderer",
	            "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

		this.graph.addAttribute("ui.stylesheet", CSS);
		this.graph.addAttribute("ui.quality");
		this.graph.addAttribute("ui.antialias");
	}
	
	public void viewGraphOnScreen() {
		
		this.graph.display();
	}

	public static void main(String[] args) {
		
		PseudoDigraph graph;
		try {
			graph = PseudoDigraph.readFromFile("resources/powerg1.gdf");
			GraphVisualizer g = new GraphVisualizer(graph);
			g.viewGraphOnScreen();			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
