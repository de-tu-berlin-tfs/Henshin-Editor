package agg.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;
import java.util.SortedSet;

import agg.util.csp.BinaryPredicate;


/*
 * This ordered set does not allow any duplications.
 */
public class OrderedSet<E> extends Vector<E> implements SortedSet<E> {
		
	
	Comparator comp;
	BinaryPredicate predicate;
	Iterator<E> iter;
	E obj;
	
	public OrderedSet() {
		super();
	}

	public OrderedSet(Comparator comparator) {
		this();
		this.comp = comparator;
	}
	
	public OrderedSet(BinaryPredicate bp) {
		this();
		this.predicate = bp;
	}
		
	public OrderedSet(Collection<E> col) {
		this();
		
		Iterator<E> iter = col.iterator();
		while (iter.hasNext()) {
			this.add(iter.next());
		}
	}
	
	public OrderedSet(Collection<E> col, Comparator comparator) {
		this(comparator);
		
		Iterator<E> iter = col.iterator();
		while (iter.hasNext()) {
			this.add(iter.next());
		}
	}
	
	public OrderedSet(SortedSet<E> ss) {
		super();
		
		this.comp = ss.comparator();
		Iterator<E> iter = ss.iterator();
		while (iter.hasNext()) {
			this.add(iter.next());
		}
	}
	
	public synchronized boolean add(E e) {
		boolean res = false;
		if (this.isEmpty()) {
			res = super.add(e);
		}
		else if (this.comp != null) {
			res = addByComparator(e);
		}
		else if (this.predicate != null) {			
			res = addByPredicate(e);
		}
		else if (!super.contains(e)) {
			res = super.add(e);
		}
		return res;
	}
	
	public boolean remove(Object o) {
		int i = (this.iter != null && this.obj != null)? this.indexOf(this.obj): -1;
		int i1 = this.indexOf(o);
		
		boolean res = super.remove(o);
		if (res) {
			if (i == -1 && this.iter != null) {
				this.start();
			}
			else if (i==0 && i1==0) {
				this.start();
			}
			else if (i>0) {
				if (i>=i1) {
					this.start();
					for (int j=0; j<=i-1; j++) {this.get();}
				}
				else if (i<i1) {
					this.start();
					for (int j=0; j<=i; j++) {this.get();}
				}
			}
		}
		return res;		 
	}
	
	private boolean addByPredicate(E e) {
		for (int i=this.size()-1; i>=0; i--) {
			if (this.predicate.execute(e, this.get(i))) {
				if (i == this.size()-1) {
					return super.add(e);
				}
				else {
					super.add(i+1, e);
					return true;
				}
			}
			else if (i > 0 && this.predicate.execute(e, this.get(i-1))) {
				super.add(i, e);
				return true;
			}
			else if (i == 0) {
				super.add(i, e);
				return true;
			}
		}
		return false;
	}
	
	private boolean addByComparator(E e) {
		for (int i=this.size()-1; i>=0; i--) {
			int c = this.comp.compare(e, this.get(i));
			if (c == 0) {
				return false;
			}
			if (c > 0) {
				if (i == this.size()-1) {
					return super.add(e);
				}
				else {
					super.add(i+1, e);
					return true;
				}
			}
			else if (i > 0 && this.comp.compare(e, this.get(i-1)) > 0) {
				super.add(i, e);
				return true;
			}
			else if (i == 0) {
				super.add(i, e);
				return true;
			}
		}
		return false;
	}
	
	public Comparator<? super E> comparator() {
		return this.comp;
	}
	
	public BinaryPredicate binaryPredicate() {
		return this.predicate;
	}
	
	public SortedSet<E> subSet(E fromElement, E toElement) {
		OrderedSet<E> set = new OrderedSet<E>();
		int start = this.indexOf(fromElement);
		int end = this.indexOf(toElement);
		for (int i=start; i<=end; i++) {
			set.add(this.get(i));
		}
		set.comp = this.comp;
		set.predicate = this.predicate;
		return set;
	}

	public SortedSet<E> headSet(E toElement) {
		OrderedSet<E> set = new OrderedSet<E>(this.comp);
		int end = this.indexOf(toElement);
		for (int i=0; i<=end; i++) {
			set.add(this.get(i));
		}
		set.comp = this.comp;
		set.predicate = this.predicate;
		return set;
	}

	public SortedSet<E> tailSet(E fromElement) {
		OrderedSet<E> set = new OrderedSet<E>();
		int start = this.indexOf(fromElement);
		for (int i=start; i<this.size(); i++) {
			set.add(this.get(i));
		}
		set.comp = this.comp;
		set.predicate = this.predicate;
		return set;
	}
	
	public E first() {
		return !this.isEmpty()? this.firstElement(): null;
	}
	
	public E last() {
		return !this.isEmpty()? this.lastElement(): null;
	}

	public void start() {
		this.iter = this.iterator();
		this.obj = null;
	}
	
	public E get() {
		this.obj = this.iter.hasNext()? this.iter.next(): null;
		return this.obj;
	}
	
	public boolean hasNext() {
		if (this.iter == null) 
			this.start();
		return this.iter.hasNext()? true: false;
	}
	
	public OrderedSet<E> union(OrderedSet<E> os) {
		OrderedSet<E> set = new OrderedSet<E>();
		for (int i=0; i<this.size(); i++) {
			set.add(this.get(i));
		}
		for (int i=0; i<os.size(); i++) {
			set.add(os.get(i));
		}
		set.comp = this.comp;
		set.predicate = this.predicate;
		return set;
	}

}
