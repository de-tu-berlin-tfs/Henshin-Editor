package agg.xt_basis;

import java.util.List;
import java.util.Vector;

import agg.attribute.AttrInstance;
import agg.attribute.AttrEvent;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.ContextView;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.util.Change;
import agg.util.Pair;

/**
 * @version $Id: Arc.java,v 1.40 2010/11/06 18:34:59 olga Exp $
 * @author $Author: olga $
 */
public class Arc extends GraphObject implements XMLObject {

	protected boolean inheritance = false;
	protected boolean directed = true;
	
	protected GraphObject itsSource;
	protected GraphObject itsTarget;
	
	protected String keyStr = null;
	
	protected Arc(	final Type type, 
			final GraphObject src, 
			final GraphObject tar,
			final Graph context) {

		this.itsContext = context;
		this.itsType = type;	
		
		this.itsSource = src;
		this.itsTarget = tar;
		addToSrcTar(this.itsSource, this.itsTarget);
		
		this.itsContextUsage = hashCode(); 
				
		if (!this.itsType.isAttrTypeEmpty()) {
			this.itsAttr = AttrTupleManager.getDefaultManager().newInstance(
					this.itsType.getAttrType(), context.getAttrContext());
		} 
		if (this.itsAttr != null) 
			this.itsAttr.addObserver(this);	
		
		this.keyStr = this.itsSource.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsTarget.getType().convertToKey());
	}
	
	
	/**
	 * @param context
	 *            The graph/subgraph context in which to consider incoming and
	 *            outgoing arcs.
	 */
	public Arc(	final AttrInstance attr,
				final Type type, 
				final GraphObject src, 
				final GraphObject tar,
				final Graph context) {
		
		this.itsContext = context;
		this.itsType = type;	
				
		this.itsSource = src;
		this.itsTarget = tar;
		addToSrcTar(this.itsSource, this.itsTarget);
		
		this.itsContextUsage = hashCode(); 
		
		this.itsAttr = attr;		
		if (this.itsAttr != null) 
			this.itsAttr.addObserver(this);

		this.keyStr = this.itsSource.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsTarget.getType().convertToKey());
	}

	protected Arc(	final Arc orig, 
					final GraphObject src, 
					final GraphObject tar, 
					final Graph context) {
		
		this(orig.getType(), src, tar, context);
		
		if (orig.getAttribute() != null) {
			if (this.itsAttr == null)
				this.createAttributeInstance();
			((ValueTuple) this.itsAttr).copyEntries(orig.getAttribute());
		}
		
		// object name is not jet used in AGG GUI
		if (!"".equals(orig.getObjectName())) {				
			this.setObjectName(orig.getObjectName());
		}
	}

	/*
	 * Add <code>this</code> to the outgoing arcs of the <code>src</code> 
	 * and to the incoming arcs of the <code>tar</code> .
	 */
	protected void addToSrcTar(final GraphObject src, final GraphObject tar) {
		if ((src != null) && (tar != null)) {
			((Node)src).addOut(this);
			((Node)tar).addIn(this);
		}
	}
	
	public void dispose() {
//		long t = System.nanoTime();
			
		((Node)this.itsTarget).removeIn(this);
		((Node)this.itsSource).removeOut(this);

		if (this.itsAttr != null) {
			this.itsAttr.removeObserver(this);
			((ValueTuple) this.itsAttr).dispose();
			this.itsAttr = null;
		}
		
		this.itsType = null;
		this.itsContext = null;
		this.itsContextUsage = -1;
		
		this.itsTarget = null;
		this.itsSource = null;

//		System.out.println("Arc disposed  in: "+(System.nanoTime()-t)+"nano");
	}
	
	public void finalize() {}

	/**
	 * If the specified parameter is <code>true</code> set this edge to be an inheritance edge of a type graph.
	 * @param inherit
	 */
	protected void setInheritance(boolean inherit) {
		this.inheritance = inherit;
	}

	/**
	 * Returns true if this edge is an inheritance edge of a type graph.
	 * @return
	 */
	public boolean isInheritance() {
		return this.inheritance;
	}

	public final boolean isArc() {
		return true;
	}

	public final boolean isNode() {
		return false;
	}

	public boolean isAbstract() {
		return false;
	}

	public final GraphObject getSource() {
		return this.itsSource;
	}

	public final GraphObject getTarget() {
		return this.itsTarget;
	}

	public void setSource(Node n) {
		((Node)this.itsSource).removeOut(this);
		this.itsSource = n;
		n.addOut(this);
		
		this.keyStr = this.itsSource.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsTarget.getType().convertToKey());
	}

	public void setTarget(Node n) {
		((Node)this.itsTarget).removeIn(this);
		this.itsTarget = n;
		n.addIn(this);
		
		this.keyStr = this.itsSource.getType().convertToKey()
						.concat(this.itsType.convertToKey())
						.concat(this.itsTarget.getType().convertToKey());
	}

	public Type getSourceType() {
		return this.itsSource.getType();
	}

	public Type getTargetType() {
		return this.itsTarget.getType();
	}

	/**
	 * Converts my type to the type key string that can be used for search
	 * operations: 
	 * <code> ((Arc) this).getSource().getType().convertToKey()
	 * + ((Arc) this).getType().convertToKey() 
	 * + ((Arc) this).getTarget().getType().convertToKey()
	 * </code>
	 */
	public String convertToKey() {
		if (this.keyStr == null) {
			this.keyStr = this.itsSource.getType().convertToKey()
							.concat(this.itsType.convertToKey())
							.concat(this.itsTarget.getType().convertToKey());
		}
		return this.keyStr;
	}
	
	public List<String> convertToKeyParentExtended() {
		final List<String> list = new Vector<String>();
		
		Vector<Type> mySrcParents = this.getSource().getType().getAllParents();
		Vector<Type> myTarParents = this.getTarget().getType().getAllParents();
		
		for (int i = 0; i < mySrcParents.size(); ++i) {
			for (int j = 0; j < myTarParents.size(); ++j) {
				String keystr = mySrcParents.get(i).convertToKey()
						+ this.getType().convertToKey()
						+ myTarParents.get(j).convertToKey();
				list.add(keystr);
			}
		}
		return list;
	}
	
	/**
	 * The edge type map key is the string:
	 * getSource().getType().convertToKey()+getType().convertToKey()+getTarget().getType().convertToKey()
	 * and is used to fill the type to objects map of a graph.
	 * 
	 * @return String key
	 */
	public String getTypeMapKey() {
		return this.convertToKey();
	}

	public void setDirected(boolean b) {
		this.directed = b;
	}

	public boolean isDirected() {
		return this.directed;
	}

	public boolean isLoop() {
		return (this.itsSource == this.itsTarget);
	}
	
	public boolean compareTo(GraphObject o) {
		if (o == null || !o.isArc()) {
			return false;
		}
		
		Arc a = (Arc) o;
//		if (!this.getObjectName().equals(a.getObjectName())) {
//			return false;
//		}
		if (!this.itsType.isParentOf(a.getType())) {
			return false;
		}
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
		if (!((Node) getSource()).compareTo(a.getSource())
				|| !((Node) getTarget()).compareTo(a.getTarget()) ) {
			return false;
		}
		return true;
	}
	
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
	
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("Edge", this);
		if (!this.directed)
			h.addAttr("directed", "false");
		if (!this.visible)
			h.addAttr("visible", "false");

		if (!this.getObjectName().equals(""))
			h.addAttr("name", this.getObjectName());
		
		h.addObject("type", this.itsType, false);
		h.addObject("source", getSource(), false);
		h.addObject("target", getTarget(), false);

		// save multiplicity, if part of type graph
		if (this.itsContext != null && this.itsContext.isTypeGraph()) {
			// System.out.println("Arc.Xwrite... is elem of type graph");
			Type sourceType = getSource().getType();
			Type targetType = getTarget().getType();

			int minmax = this.itsType.getSourceMin(sourceType, targetType);
			if (minmax != Type.UNDEFINED)
				h.addAttr("sourcemin", Integer.toString(minmax));

			minmax = this.itsType.getTargetMin(sourceType, targetType);
			// System.out.println("targetmin " +minmax);
			if (minmax != Type.UNDEFINED)
				h.addAttr("targetmin", Integer.toString(minmax));

			minmax = this.itsType.getSourceMax(sourceType, targetType);
			if (minmax != Type.UNDEFINED)
				h.addAttr("sourcemax", Integer.toString(minmax));

			minmax = this.itsType.getTargetMax(sourceType, targetType);
			// System.out.println("targetmax " +minmax);
			if (minmax != Type.UNDEFINED)
				h.addAttr("targetmax", Integer.toString(minmax));
		}
		
		h.addObject("", this.itsAttr, true);
		
		h.close();
	}

	public void XreadObject(XMLHelper h) {
		if (h.isTag("Edge", this)) {
			String str = h.readAttr("directed");
			this.directed = str.equals("false")?  false: true;
			
			str = h.readAttr("visible");
			this.visible = str.equals("false")? false: true;

			str = h.readAttr("name");
			this.setObjectName(str);
			
			if (this.itsType.getAttrType() != null
					|| this.itsType.hasInheritedAttribute())
				this.createAttributeInstance();

			AttrInstance attri = this.itsAttr;
			if (attri != null) {
				// if(Debug.HASHCODE){
				// agg.attribute.AttrType attrType = itsType.getAttrType();
				// if(!((agg.attribute.impl.DeclTuple)
				// attrType).containsName("HASHCODE"))
				// attrType.addMember(agg.attribute.facade.impl.DefaultInformationFacade.self().getJavaHandler(),"String",
				// "HASHCODE" );
				//
				// agg.attribute.impl.ValueMember mem =
				// ((agg.attribute.impl.ValueTuple)
				// attri).getValueMemberAt("HASHCODE");
				// String hc = String.valueOf(hashCode());
				// mem.setExprAsObject(hc);
				// mem.checkValidity();
				// }

				h.enrichObject(attri);
			}
			h.close();

			// if this node uses variable
			// in its attribute so the variable will be marked
			if (this.itsContext != null 
					&& this.itsContext.getAttrContext() != null
					&& this.itsAttr != null) {
				ValueTuple value = (ValueTuple) this.itsAttr;
				for (int i = 0; i < value.getSize(); i++) {
					ValueMember val = value.getValueMemberAt(i);
					if (val.isSet() && val.getExpr().isVariable()) {
						ContextView viewContext = (ContextView) ((ValueTuple) val
								.getHoldingTuple()).getContext();
						VarTuple variable = (VarTuple) viewContext
								.getVariables();
						VarMember var = variable.getVarMemberAt(val
								.getExprAsText());
						if (getContext().isNacGraph())
							var.setMark(VarMember.NAC);
						else if (getContext().isPacGraph())
							var.setMark(VarMember.PAC);
						else if (viewContext.doesAllowComplexExpressions())
							var.setMark(VarMember.RHS);
						else
							var.setMark(VarMember.LHS);
//						System.out.println(this.itsContext.getName()+"    "+var.getName()+"   "+var.getMark());
					}
				}
			}
		}
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
				+ "--->" + tTrg + ") ";
		if (this.itsAttr != null) {
			result = result+this.itsAttr.toString();
		}
		return result;
	}

	/**
	 * Implements the AttrObserver. Propagates the change
	 * <code>agg.util.Change.OBJECT_MODIFIED<code>
	 * and object Pair (this, ev.getID()) 
	 * to its Graph if the attributes are changed.
	 */
	public void attributeChanged(AttrEvent ev) {
		if (this.itsContext != null) {
			Pair<Object, AttrEvent> p = new Pair<Object, AttrEvent>(this, ev);
			this.itsContext.propagateChange(new Change(Change.OBJECT_MODIFIED, p));
		}
	}

}
