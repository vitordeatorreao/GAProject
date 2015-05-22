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
		 * Opera��o para inicializar o grafo com N v�rtices/n�s, numerados de 0
		 * a N-1
		 */
		Graph grafo = new PseudoDigraph(4);
		/*
		 * Fun��o para acrescentar no grafo uma aresta (x,y), onde x e y s�o
		 * v�rtices v�lidos
		 */
		grafo.addEdge(0, 1);
		grafo.addEdge(1, 0);
		grafo.addEdge(1, 2);
		grafo.addEdge(2, 0);
		grafo.addEdge(2, 3);
		grafo.addEdge(3, 0);
		System.out.println("Grafo 1 = " + grafo);
		
		/*
		 * Uma maneira de acessar (apenas) os vizinhos de um n� v (entrada)
		 */
		System.out.println("Adjacent nodes of 2: " + grafo.getAdjacentNodes(2));
		// [0, 3]
		/*
		 * Testar se existe ou n�o uma aresta (x,y), para x e y dados como
		 * entradas
		 */
		System.out.println("Existe aresta entre 1 e 2? "
				+ grafo.existsEdgeBetween(1, 2));
		// True
		/*
		 * Opera��o ler um grafo de um arquivo texto conforme o formato
		 */
		if (args.length > 0) {
			grafo = PseudoDigraph.readFromFile(args[0]);
		} else {
			grafo = PseudoDigraph.readFromFile("resources/powerg1.gdf");
		}
		System.out.println("Grafo carregado do arquivo = " + grafo);
		/*
		 * Testar se G tem loop (arco/aresta para o pr�prio n�)		
		 */
		System.out.println("Este grafo tem loop? " + grafo.hasLoop());
		/*
		 * Testar se G tem arcos paralelos	
		 */
		System.out.println("Este grafo tem arcos paralelos? " + grafo.hasParallelEdges());
		/*
		 * Testar se G � sim�trico
		 */
		System.out.println("Este grafo � sim�trico? " + grafo.isSymmetric());
		/*
		 * Calcular o transposto de G
		 */
		Graph transposed = grafo.getTransposed();
		System.out.println("O transposto desse grafo � = "  + transposed);
		/*
		 * Calcular o quadrado de G
		 */
		Graph square = new PowerGraph(grafo, 2);
		System.out.println("O quadrado desse grafo � = "  + square);
		/*
		 * Testar o grafo � ac�clico
		 */
		boolean hasCycle = grafo.hasCycle();
		System.out.println("Este grafo � c�clico? " + hasCycle);
		if (!hasCycle) {
			/*
			 * Calcular a ordem topol�gica
			 */
			List<Integer> topologicalOrder = (new DepthFirstSearch(grafo)).getTopologicOrder();
			System.out.println("A ordem topol�gica desse grafo � = " + topologicalOrder);
		}
		/*
		 * Informar se ele � ou n�o fortemente conectado
		 */
		System.out.println("Este Grafo � fortemente conectado? " + grafo.isStronglyConnected());
	}
}
