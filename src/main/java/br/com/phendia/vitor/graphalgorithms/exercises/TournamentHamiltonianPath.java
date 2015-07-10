package br.com.phendia.vitor.graphalgorithms.exercises;

import java.io.IOException;
import br.com.phendia.vitor.graphalgorithms.graph.Tournament;
import br.com.phendia.vitor.graphalgorithms.paths.HamiltonianPath;
import br.com.phendia.vitor.graphalgorithms.paths.Path;

public class TournamentHamiltonianPath {

	private Tournament problemGraph;
	private Path hamiltonianPath;

	public TournamentHamiltonianPath(String tournamentGraphFile) {
		try {
			problemGraph = new Tournament(
					Tournament.readFromFileWithWeights(tournamentGraphFile));
			hamiltonianPath = HamiltonianPath.build(problemGraph);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Path getHamiltonianPath() {
		return this.hamiltonianPath;
	}

	public static void main(String[] args) {
		if (args.length < 1) {
			args = new String[1];
			args[0] = "examples/tournament.wgdf";
			System.out.println("No graph provided as argument, using default: "
					+ args[0]);
		}
		TournamentHamiltonianPath thp = new TournamentHamiltonianPath(args[0]);
		System.out.print("This tournament's hamiltonian path is: ");
		System.out.println(thp.getHamiltonianPath());
	}

}
