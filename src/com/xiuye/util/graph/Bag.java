package com.xiuye.util.graph;

import java.util.Iterator;

public class Bag<T> implements Iterable<T>{

	private Node first;
	private class Node{
		T item;
		Node next;
	}
	
	public void add(T t) {
		Node oldFirst = first;
		first = new Node();
		first.item = t;
		first.next = oldFirst;
	}
	
	public Iterator<T> iterator(){
		return new ListIterator();
	}
	
	private class ListIterator implements Iterator<T>{

		private Node current = first;
		
		
		@Override
		public boolean hasNext() {
			return current != null;
		}

		@Override
		public T next() {
			T t = current.item;
			current = current.next;
			return t;
		}
		
	}
	
	
	
}
