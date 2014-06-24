package org.eclipse.emf.henshin.interpreter.util;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

public class DomainList<E> extends ArrayDeque<E> implements List<E>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8979803241065301001L;

	public DomainList() {
		super();
		// TODO Auto-generated constructor stub
	}

	public DomainList(Collection<? extends E> c) {
		super(c);
	}

	public DomainList(int numElements) {
		super(numElements);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public E get(int index) {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public E set(int index, E element) {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public void add(int index, E element) {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public E remove(int index) {
		if (index == 0){
			return super.pollFirst();
		}
		if (index == this.size() -1){
			return super.pollLast();
		}
			
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public int indexOf(Object o) {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public int lastIndexOf(Object o) {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public ListIterator<E> listIterator() {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		throw new RuntimeException("Not Implemented!");
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		throw new RuntimeException("Not Implemented!");
	}

}
