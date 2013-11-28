package org.eclipse.emf.henshin.interpreter.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;

import javax.naming.OperationNotSupportedException;

import org.eclipse.emf.ecore.EObject;

public class HashList<E> extends LinkedHashSet<E> implements List<E>{

	private HashSet<String> containsMap = new HashSet<String>();
	
	/**
	 * 
	 */
	
	public HashList(Collection<? extends E> targetObjects) {
		super(targetObjects);
	}

	public HashList(int i) {
		super(i);
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
