package br.com.phendia.vitor.graphalgorithms.graph;

/**
 * Interface for a very generic graph.
 * @author V&iacute;tor de Albuquerque Torre&atilde;o
 *
 */
public interface Graph {
	
	/**
	 * Adds an edge connecting the nodes in the parameters. 
	 * Since there is no restriction to what kind of graph 
	 * this interface represents, the edge might connect any number of nodes.
	 * @param nodes The nodes the edge connects
	 */
	public void addEdge(float weight, Node... nodes);

	/**
	 * Adds a single node to the Graph.
	 * @param node The node to be added to the Graph
	 */
	public void addNode(Node node);
	
	/**
	 * Retrieves a list of Nodes that succeed the given Node.
	 * @param node The node from which to find successors
	 */
	public Iterable<Arc> getSuccessors(Node node);
	
	/**
	 * Verifies if there exists an edge connecting the two given nodes.
	 * @param nodeOne
	 * @param nodeTwo
	 */
	public boolean existsEdgeBetween(Node nodeOne, Node nodeTwo);

}
