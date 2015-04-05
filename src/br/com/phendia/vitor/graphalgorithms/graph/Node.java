package br.com.phendia.vitor.graphalgorithms.graph;

import java.util.HashMap;

/**
 * This class implements a Node. It may be used in a Graph, a Tree, a 
 * LinkedList. It must be uniquely identifiable and may contain any 
 * kind of information.
 * @author V&iacute;tor de Albuquerque Torre&atilde;o
 *
 */
public class Node {
	
	/**
	 * Servers as both counter of Nodes and automatic ID generator.
	 */
	private static long autoId = 0;
	
	/**
	 * Keeps record of each Node and its ID.
	 */
	private static HashMap<Long, Node> nodes = new HashMap<Long, Node>();
	
	/**
	 * It is requested that every node has a unique integer to identify it.
	 */
	private long id;
	
	/**
	 * A given Node may store any kind of data.
	 */
	private Object data;
	
	/**
	 * Basic constructor. 
	 * The Node is automatically given an ID and holds no data.
	 */
	public Node() {
		this.id = getNextId();
		this.data = null;
		nodes.put(this.id, this);
	}
	
	/**
	 * A Node constructor that allows the user to specify the Node's ID.
	 * @param id The specified ID for the Node
	 */
	public Node(long id) {
		if(nodes.containsKey(id)) {
			throw new IllegalArgumentException("There already exists a "
					+ "Node with the specified ID");
		}
		this.id = id;
		nodes.put(this.id, this);
	}
	
	/**
	 * A Node constructor that allows the user to not only specify 
	 * the Node's ID, but also to assign any Object to the Node.
	 * @param id The specified ID for the Node
	 * @param data The object assigned to this Node
	 */
	public Node(long id, Object data) {
		this(id);
		this.data = data;
	}
	
	/**
	 * Retrieves the Node's ID
	 * @return This instance's ID
	 */
	public long getId() {
		return this.id;
	}
	
	/**
	 * Allows the user to change the data been held by this Node.
	 * @param data The new object that shall be assigned to this Node
	 */
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * Retrieves the current object been held by the Node instance.
	 * @return The current object in memory associated with this node.
	 */
	public Object getData() {
		return this.data;
	}
	
	/**
	 * Method used by the class to automatically assign an ID to any Node.
	 * @return The ID that should be given to the next Node.
	 */
	private static long getNextId() {
		while(nodes.containsKey(autoId)) {
			autoId++;
		}
		return autoId++;
		//Returns the current value in autoId and then increments it by one
	}
	
	/**
	 * Retrieves the Node with the specified ID.
	 * @param id The ID with which to lookup the Node
	 * @return The Node with the given Id
	 */
	public static Node getNodeFromId(long id) {
		return nodes.get(id);
	}

}
