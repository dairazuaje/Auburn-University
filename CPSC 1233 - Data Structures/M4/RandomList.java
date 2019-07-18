import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

public class RandomList<T> implements RandomizedList<T> {
	private T[] elements;
	private int size;
	private final int DEFAULT_LENGTH = 5;
	
	@SuppressWarnings("unchecked")
	public RandomList() {
		size = 0;
		elements = (T[]) new Object[DEFAULT_LENGTH];
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return size == 0;
	}

	@Override
	public Iterator<T> iterator() {
		// TODO Auto-generated method stub
		return new ListIterator(elements);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void add(T element) {
		// TODO Auto-generated method stub
		
		if (element == null) {
			throw new IllegalArgumentException("Element was null");
		}
		
		if (size == elements.length) {
			int newCapacity = elements.length * 2;
			T[] temp = (T[]) new Object[newCapacity];
			for (int i = 0; i < elements.length; i++) {
				temp[i] = elements[i];
			}
			elements = temp;
		}
		
		elements[size] = element;
		size++;
	}

	@Override
	public T remove() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		
		int random = new Random().nextInt(size);
		T element = elements[random];
		
		for (int i = size; i < elements.length; i++) {
			if (i >= elements.length) {
				elements[size] = elements[size++];
			} else {
				elements[size] = null;
			}
		}
		size--;
		return element;
		
	}

	@Override
	public T sample() {
		// TODO Auto-generated method stub
		if (size == 0) {
			return null;
		}
		int random = new Random().nextInt(size());
	   return elements[random];
	}
	
	@SuppressWarnings("unchecked")
	private class ListIterator implements Iterator<T> {
		private int current;
		private T[] list = (T[]) new Object[elements.length];
		private T element;
		public ListIterator(T[] arr) {
			list = arr;
			current = 0;
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
			T sample = null;
			if (hasNext()) {
				sample = list[current];
			}
			current++;
			return sample;
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
}
