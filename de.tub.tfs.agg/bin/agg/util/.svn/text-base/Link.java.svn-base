/**
 * 
 */
package agg.util;

import agg.xt_basis.GraphObject;


/**
 * @author olga
 *
 */
public class Link {

	private Link up;

	private GraphObject o;

	public Link() {
		this.up = null;
		this.o = null;
	}
	
	public void dispose() {
		if (this.up != null) {
			this.up.dispose();
		}
		this.up = null;
//		System.out.println("Step.Link.dispose()   DONE  "+this);
		this.o = null;
	}

	public void finalize() {
//		System.out.println("Step.Link.finalize()   called  "+this);
	}
	
	public Link find() {
		Link l = this;
		while (l.up != null && l != l.up) {
			l = l.up;
		}
		Link m = this;
		while (m.up != null && m != m.up) {
			Link n = m.up;
			m.up = l;
			m = n;
		}
		return l;
	}

	public Link link(Link other) {
		Link po = other.find();
		Link pt = find();
		po.up = pt;
		return pt;
	}

	public GraphObject get() {
		return find().o;
	}

	public void set(GraphObject go) {
		find().o = go;
	}

}
