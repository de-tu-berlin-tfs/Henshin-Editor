package agg.xt_basis;

import java.util.List;
import java.util.Vector;

import agg.attribute.AttrInstance;




public class UndirectedArc extends Arc {

	protected String keyStr2 = null;
	
	public UndirectedArc(final AttrInstance attr,
							final Type type, 
							final GraphObject src, 
							final GraphObject tar,
							final Graph context) {
	
		super(attr, type, src, tar, context);	
		this.directed = false;
	}
	
	protected UndirectedArc(final Type type, 
							final GraphObject src, 
							final GraphObject tar,
							final Graph context) {

		super(type, src, tar, context);	
		this.directed = false;			
	}
	
	protected UndirectedArc(final Arc orig, 
							final GraphObject src, 
							final GraphObject tar, 
							final Graph context) {

		super(orig.getType(), src, tar, context);
		this.directed = false;
	}
	
	/*
	 * Add <code>this</code> to the outgoing arcs of the <code>src</code> 
	 * and to the outgoing arcs of the <code>tar</code> .
	 */
	protected void addToSrcTar(final GraphObject src, final GraphObject tar) {
		if ((src != null) && (tar != null)) {
			((Node)src).addOut(this);
			((Node)tar).addOut(this);
		}
	}
	
	public void dispose() {
		((Node)this.itsTarget).removeOut(this);
		((Node)this.itsSource).removeOut(this);
		
		super.dispose();
	}
	
	/*
	 * It is always undirected.
	 */
	public void setDirected(boolean b) {
		this.directed = false;
	}
	
	/*
	 * Returns always false.
	 */
	public boolean isDirected() {
		return false;
	}
	
	/**
	 * Converts my type to the inverse type key string that can be used for search
	 * operations: 
	 * <code> ((Arc) this).getTarget().getType().convertToKey()
	 * + ((Arc) this).getType().convertToKey() 
	 * + ((Arc) this).getSource().getType().convertToKey()
	 * </code>
	 */
	public String convertToInverseKey() {
		if (this.keyStr2 == null) {
			this.keyStr2 = this.itsTarget.getType().convertToKey()
							.concat(this.itsType.convertToKey())
							.concat(this.itsSource.getType().convertToKey());
		}
		return this.keyStr2;
	}
	
	public List<String> convertToInverseKeyParentExtended() {
		final List<String> list = new Vector<String>();
		
		Vector<Type> mySrcParents = this.getSource().getType().getAllParents();
		Vector<Type> myTarParents = this.getTarget().getType().getAllParents();
		
		for (int i = 0; i < mySrcParents.size(); ++i) {
			for (int j = 0; j < myTarParents.size(); ++j) {
				String keystr = myTarParents.get(i).convertToKey()
						+ this.getType().convertToKey()
						+ mySrcParents.get(j).convertToKey();
				list.add(keystr);
			}
		}
		return list;
	}
	
	public boolean compareTo(GraphObject o) {
//		System.out.println(this.getClass().getName()+".compareTo(GraphObject)   called ...");
		if (o == null || !o.isArc()) {
			return false;
		}
		Arc a = (Arc) o;
		// edge type
		if (!this.itsType.isParentOf(a.getType())) {
			return false;
		}
		// edge attribute
		if ((this.itsAttr == null && a.getAttribute() == null)
				|| ((this.attrExists() && a.attrExists()) 
						&& this.itsAttr.compareTo(a.getAttribute()))) {
			;
		} else {
			return false;
		}
		if (!this.compareSrcTarTo(a)) {
			return false;
		}	
		if (!this.compareMultiplicityTo(a)) {
			return false;
		}
		return true;
	}
	
	protected boolean compareSrcTarTo(Arc a) {
		if (( ((Node) getSource()).compareTo(a.getSource())
					&& ((Node) getTarget()).compareTo(a.getTarget()) )
				|| ( ((Node) getSource()).compareTo(a.getTarget())
					&& ((Node) getTarget()).compareTo(a.getSource()) )) {
			return true;
		}
		return false;
	}
	
	//????
	protected boolean compareMultiplicityTo(Arc a) {
		if (this.itsContext.isTypeGraph()) {
			Type srcType = getSource().getType();
			Type tarType = getTarget().getType();
			Type a_srcType = a.getSource().getType();
			Type a_tarType = a.getTarget().getType();
			int minmax = this.itsType.getSourceMin(srcType, tarType);
			int a_minmax = a.getType().getSourceMin(a_srcType, a_tarType);
			if (minmax != a_minmax)
				return  false;
			else {
				minmax = this.itsType.getTargetMin(srcType, tarType);
				a_minmax = a.getType().getTargetMin(a_srcType, a_tarType);
				if (minmax != a_minmax)
					return  false;
				else {
					minmax = this.itsType.getSourceMax(srcType, tarType);
					a_minmax = a.getType().getSourceMax(a_srcType,
							a_tarType);
					if (minmax != a_minmax)
						return  false;
					else {
						minmax = this.itsType.getTargetMax(srcType, tarType);
						a_minmax = a.getType().getTargetMax(a_srcType,
								a_tarType);
						if (minmax != a_minmax)
							return  false;
					}
				}
			}
		}
		return true;
	}
	
	public void setSource(Node n) {
		((Node)this.itsSource).removeOut(this);
		this.itsSource = n;
		n.addOut(this);
		
		this.keyStr = this.itsSource.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsTarget.getType().convertToKey());
		
		this.keyStr2 = this.itsTarget.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsSource.getType().convertToKey());
	}

	public void setTarget(Node n) {
		((Node)this.itsTarget).removeIn(this);
		this.itsTarget = n;
		n.addOut(this);
		
		this.keyStr = this.itsSource.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsTarget.getType().convertToKey());
		
		this.keyStr2 = this.itsTarget.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsSource.getType().convertToKey());
	}
	
	public String toString() {
		String result = "";
		String t = this.itsType.getStringRepr();
		if (t.equals(""))
			t = "[unnamed]";
		String tSrc = getSource().getType().getStringRepr();
		if (tSrc.equals(""))
				tSrc = "[unnamed]";
		String tTrg = getTarget().getType().getStringRepr();
		if (tTrg.equals(""))
			tTrg = "[unnamed]";
		result = " (" + "[" + hashCode() + "] " + "Arc: " + tSrc + "---" + t
				+ "---" + tTrg + ") ";
		if (this.itsAttr != null) {
			result = result+this.itsAttr.toString();
		}
		return result;
	}
	
}
