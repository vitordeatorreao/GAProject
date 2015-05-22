package br.com.phendia.vitor.graphalgorithms.exercises;

import java.io.IOException;
import java.util.List;

import br.com.phendia.vitor.graphalgorithms.graph.Graph;
import br.com.phendia.vitor.graphalgorithms.graph.PowerGraph;
import br.com.phendia.vitor.graphalgorithms.graph.PseudoDigraph;
import br.com.phendia.vitor.graphalgorithms.search.DepthFirstSearch;

public class Exercise {

	public static void main(String[] args) throws IOException {

		/*
		 * Operação para inicializar o grafo com N vértices/nós, numerados de 0
		 * a N-1
		 */
		Graph grafo = new PseudoDigraph(4);
		/*
		 * Função para acrescentar no grafo uma aresta (x,y), onde x e y são
		 * vértices válidos
		 */
		grafo.addEdge(0, 1);
		grafo.addEdge(1, 0);
		grafo.addEdge(1, 2);
		grafo.addEdge(2, 0);
		grafo.addEdge(2, 3);
		grafo.addEdge(3, 0);
		System.out.println("Grafo 1 = " + grafo);
		
		/*
		 * Uma maneira de acessar (apenas) os vizinhos de um nó v (entrada)
		 */
		System.out.println("Adjacent nodes of 2: " + grafo.getAdjacentNodes(2));
		// [0, 3]
		/*
		 * Testar se existe ou não uma aresta (x,y), para x e y dados como
		 * entradas
		 */
		System.out.println("Existe aresta entre 1 e 2? "
				+ grafo.existsEdgeBetween(1, 2));
		// True
		/*
		 * Operação ler um grafo de um arquivo texto conforme o formato
		 */
		if (args.length > 0) {
			grafo = PseudoDigraph.readFromFile(args[0]);
		} else {
			grafo = PseudoDigraph.readFromFile("resources/powerg1.gdf");
		}
		System.out.println("Grafo carregado do arquivo = " + grafo);
		/*
		 * Testar se G tem loop (arco/aresta para o próprio nó)		
		 */
		System.out.println("Este grafo tem loop? " + grafo.hasLoop());
		/*
		 * Testar se G tem arcos paralelos	
		 */
		System.out.println("Este grafo tem arcos paralelos? " + grafo.hasParallelEdges());
		/*
		 * Testar se G é simétrico
		 */
		System.out.println("Este grafo é simétrico? " + grafo.isSymmetric());
		/*
		 * Calcular o transposto de G
		 */
		Graph transposed = grafo.getTransposed();
		System.out.println("O transposto desse grafo é = "  + transposed);
		/*
		 * Calcular o quadrado de G
		 */
		Graph square = new PowerGraph(grafo, 2);
		System.out.println("O quadrado desse grafo é = "  + square);
		/*
		 * Testar o grafo é acíclico
		 */
		boolean hasCycle = grafo.hasCycle();
		System.out.println("Este grafo é cíclico? " + hasCycle);
		if (!hasCycle) {
			/*
			 * Calcular a ordem topológica
			 */
			List<Integer> topologicalOrder = (new DepthFirstSearch(grafo)).getTopologicOrder();
			System.out.println("A ordem topológica desse grafo é = " + topologicalOrder);
		}
		/*
		 * Informar se ele é ou não fortemente conectado
		 */
		System.out.println("Este Grafo é fortemente conectado? " + grafo.isStronglyConnected());
	}
}
