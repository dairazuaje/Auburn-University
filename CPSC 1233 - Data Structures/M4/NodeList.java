import java.util.Iterator;
import java.util.NoSuchElementException;

public class NodeList<T> implements DoubleEndedList<T> {
	private int size;
	private Node front;
	private Node tail;
	
	public NodeList() {
		front = null;
		tail = null;
		size = 0;
	}
	
	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new NodeListIterator();
	}

	@Override
	public void addFirst(T element) {
		// TODO Auto-generated method stub
		if (element == null) {
			throw new IllegalArgumentException("Element was null");
		}
		
		Node n = new Node(element);
		
		if (isEmpty()) {
			front = n;
			tail = n;;
		} else {
			n.next = front;
			front.previous = n;
			front = n;
		}
		size++;
	}

	@Override
	public void addLast(T element) {
		// TODO Auto-generated method stub
		if (element == null) {
			throw new IllegalArgumentException("Element was null");
		}
		
		Node n = new Node(element);
		if (isEmpty()) {
			front = n;
			tail = n;
		} else {
			n.previous = tail;
			tail.next = n;
			tail = n;
		}
		size++;
	}

	@Override
	public T removeFirst() {
		// TODO Auto-generated method stub
		
		if (isEmpty()) {
			return null;
		}
		
		if (size == 1) {
			T removed = front.element;
			front = null;
			tail = null;
			size--;
			return removed;
		}
		
		T removed = front.element;
		front = front.next;
		front.previous.next = null;
		front.previous = null;
		size--;
		return removed;
	}

	@Override
	public T removeLast() {
		// TODO Auto-generated method stub
		if (isEmpty()) {
			return null;
		}
		
		if (size == 1) {
			T removed = front.element;
			front = null;
			tail = null;
			size--;
			return removed;
		}
		
		T removed = tail.element;
		tail = tail.previous;
		tail.next.previous = null;
		tail.next = null;
		size--;
		return removed;
	}
	
	private class Node {
		private T element;
		private Node next;
		private Node previous;
		
		public Node(T e) {
			element = e;
		}
	}
	
	private class NodeListIterator implements Iterator<T> {
		private int current = 0;
		private NodeList list;
		private T item;
		private Node currentNode;
		
		public NodeListIterator() {
			currentNode = front;
		}
		
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if (current != size) {
				return true;
			} else {
				return false;
			}
		}
		
		@Override
		public T next() {
			// TODO Auto-generated method stub
			if (isEmpty()) {
				throw new NoSuchElementException("No elements exist");
			}
			
			if (current < size) {
				item = currentNode.element;
				currentNode = currentNode.next;
			}
			
			current++;
			return item;
			
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
