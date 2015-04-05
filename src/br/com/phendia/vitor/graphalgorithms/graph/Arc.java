package br.com.phendia.vitor.graphalgorithms.graph;

/**
 * This class implements the concept of an Edge with at most two nodes. 
 * It connects nodes. Since the scope of this project 
 * is a PseudoDigraph, the Edge can be seen as a LinkedList, 
 * where &lt;A,B&gt; means Nodes A and B are connect via this edge.<br>
 * It is also specified in the project's description that an Edge must be
 * between two Nodes. These Nodes may be the same, however, since it must
 * allow for loops.<br>
 * Literature tends to call the edges with such restrictions as Arcs.
 * @author V&iacute;tor de Albuquerque Torre&atilde;o
 *
 */
public class Arc {
	
	/**
	 * The Node of origin.
	 */
	private Node nodeOne;
	
	/**
	 * The destination node.
	 */
	private Node nodeTwo;
	
	/**
	 * The weight associated with this Edge(Arc).
	 */
	private float weight;
	
	/**
	 * An Arc's most simple constructor. 
	 * It just requires origin and destination nodes. 
	 * It will assign automatically the value 0.0 to the weight.
	 * @param nodeOne The origin Node
	 * @param nodeTwo The destination Node
	 */
	public Arc(Node nodeOne, Node nodeTwo) {
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
		this.weight = 0f;
	}
	
	/**
	 * An Arc constructor that allows the user to input a weight.
	 * @param nodeOne The origin Node
	 * @param nodeTwo The destination Node
	 * @param weight The weight associated with this Arc (Edge)
	 */
	public Arc(Node nodeOne, Node nodeTwo, float weight) {
		this.nodeOne = nodeOne;
		this.nodeTwo = nodeTwo;
		this.weight = weight;
	}

	/**
	 * Retrieves the Node of origin for external use.
	 * @return The origin Node
	 */
	public Node getNodeOne() {
		return nodeOne;
	}

	/**
	 * Retrieves the node of destination for external use.
	 * @return The destination Node
	 */
	public Node getNodeTwo() {
		return nodeTwo;
	}
	
	/**
	 * Retrieves the weight associated with this Edge.
	 * @return The weight of this Edge
	 */
	public float getWeight() {
		return weight;
	}
	
	/**
	 * Allows for external classes to assign a new value to 
	 * serve as weight of this Edge.
	 * @param f A float-point number. The new weight of this Edge.
	 */
	public void setWeight(float f) {
		this.weight = f;
	}
}
