package br.com.phendia.vitor.graphalgorithms.utils;

import java.util.Iterator;

public class LinkedList implements Iterable<Object>{
	private class Node {
		public Object data;
		public Node next;

		public Node(Object dataValue) {
			this.data = dataValue;
			this.next = null;
		}
	}

	private Node head;
	private int size;

	public LinkedList() {
		this.head = null;
		this.size = 0;
	}
	
	private Node getNode(int index) {
		if (index < 0) {
			throw new ArrayIndexOutOfBoundsException(
					"index must be grater than or equal to zero");
		}
		Node elem = this.head;
		for (int i = 1; i <= index; i++) {
			try {
				elem = elem.next;
			} catch (NullPointerException e) {
				throw new ArrayIndexOutOfBoundsException("Array (size "
						+ this.size + ") does not have an element at position "
						+ index);
			}
		}
		return elem;
	}

	public void add(Object data) {
		Node newElem = new Node(data);
		this.size++;
		if (this.head == null) {
			this.head = newElem;
			return;
		}
		Node elem = this.head;
		while (elem.next != null) {
			elem = elem.next;
		}
		elem.next = newElem;
	}
	
	public void set(int index, Object data) {
		Node elem = this.getNode(index);
		elem.data = data;
	}

	public Object get(int index) {
		Node elem = this.getNode(index);
		return elem.data;
	}
	
	public void remove(int index) {
		Node elem = this.getNode(index);
		Node prevElem = this.getNode(index-1);
		prevElem.next = elem.next;
		this.size--;
	}
	
	public int getSize() {
		return this.size;
	}
	
	private class LinkedListIterator implements Iterator<Object> {
		
		private int index;
		private LinkedList list;
		
		public LinkedListIterator(LinkedList list) {
			this.index = 0;
			this.list = list;
		}

		@Override
		public boolean hasNext() {
			if (this.index < this.list.getSize()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Object next() {
			return this.list.get(this.index++);
		} 
		
	}
	
	@Override
	public Iterator<Object> iterator() {
		return (Iterator<Object>) new LinkedListIterator(this);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		int i;
		for (i = 0; i < this.size-1; i++) {
			sb.append(this.get(i) + ", ");
		}
		sb.append(this.get(i) + "]");
		return sb.toString();
	}
	
	public static void main(String[] args) {
		LinkedList list = new LinkedList();
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		System.out.println(list.toString() + " => " + list.getSize());
		list.remove(3);
		System.out.println(list);
		System.out.println(list.get(0));
		list.add(7);
		System.out.println(list);
		list.set(0, 40);
		System.out.println(list);
		
		for (Object obj : list) {
			int i = (int) obj;
			System.out.println(i);
		}
	}
}
