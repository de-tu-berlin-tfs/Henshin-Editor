package de.tub.tfs.henshin.analysis;

import org.eclipse.emf.henshin.model.NamedElement;


public class Pair<T1, T2>  {
	
	private T1 first;
	private T2 second;

	public Pair(T1 t1,T2 t2){
		this.first = t1;
		this.second = t2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<?,?> other = (Pair<?,?>) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}

	public String toString() {
		if (first instanceof NamedElement && second instanceof NamedElement)
			return "(" + ((NamedElement)first).getName() + "," + ((NamedElement)second).getName() + ")";
		return "(" + first.toString() + "," + second.toString() + ")";
	}

	public T1 getFirst() {
		return first;
	}

	public void setFirst(T1 first) {
		this.first = first;
	}

	public T2 getSecond() {
		return second;
	}

	public void setSecond(T2 second) {
		this.second = second;
	}
	
	
}
