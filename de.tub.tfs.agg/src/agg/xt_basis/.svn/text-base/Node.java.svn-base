package agg.xt_basis;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
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
 * @version $Id: Node.java,v 1.47 2010/11/06 18:34:59 olga Exp $
 * @author $Author: olga $
 */
@SuppressWarnings("serial")
public class Node extends GraphObject implements XMLObject {
	
//	// test: node XY-position as attribute
	public boolean xyAttr = false;
		
//	final protected Vector<Arc> itsOutgoingArcs = new Vector<Arc>();	
//	final protected Vector<Arc> itsIncomingArcs = new Vector<Arc>();
	
	final protected LinkedHashSet<Arc> itsOutgoingArcs = new LinkedHashSet<Arc>();	
	final protected LinkedHashSet<Arc> itsIncomingArcs = new LinkedHashSet<Arc>();

	
	
	protected Node(Type type, Graph context) {
		this.itsContext = context;
		this.itsType = type;	

		this.itsContextUsage = hashCode();
		
		// test: XY Position as attributes
		addXYPosAttrs(this.itsContext != null && this.itsContext.xyAttr);
				
		if (!this.itsType.isAttrTypeEmpty() && this.itsAttr == null) {
			this.itsAttr = AttrTupleManager.getDefaultManager().newInstance(
					this.itsType.getAttrType(), context.getAttrContext());
		} 
			
		if (this.itsAttr != null) 
			this.itsAttr.addObserver(this);
	}
	
	public Node(AttrInstance attr, Type type, Graph context) {
		this.itsContext = context;
		this.itsType = type;	
		
		this.itsContextUsage = hashCode(); 
		
		this.itsAttr = attr;
		
		// test: XY Position as attributes
		addXYPosAttrs(this.itsContext != null && this.itsContext.xyAttr);
		
		if (this.itsAttr != null) 			
			this.itsAttr.addObserver(this);
	}

	private void addXYPosAttrs(boolean xyPosAttrs) {		
		// test: XY Position as attributes
		if (xyPosAttrs) {
			xyAttr = true;
			if (this.itsAttr == null) {
				if (this.itsType.getAttrType() == null)
					((NodeTypeImpl)this.itsType).setAttributeType(
							AttrTupleManager.getDefaultManager().newType());
				this.itsAttr = AttrTupleManager.getDefaultManager().newInstance(
						this.itsType.getAttrType(), this.itsContext.getAttrContext());
			}
			agg.attribute.AttrType attrType = itsType.getAttrType();
			if(!((agg.attribute.impl.DeclTuple)attrType).containsName("thisX"))
				attrType.addMember(
						agg.attribute.facade.impl.DefaultInformationFacade.self().getJavaHandler(),
						"int", "thisX" );	
			if(!((agg.attribute.impl.DeclTuple)attrType).containsName("thisY"))
				attrType.addMember(
					agg.attribute.facade.impl.DefaultInformationFacade.self().getJavaHandler(),
						"int", "thisY" );
				
		}
	}
	
	
	protected Node(Node orig, Graph context) {
		this(orig.getType(), context);
		
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

	public void dispose() {			
		this.itsOutgoingArcs.clear(); 
		this.itsIncomingArcs.clear();
				
		if (this.itsAttr != null) {
			this.itsAttr.removeObserver(this);
			((ValueTuple) this.itsAttr).dispose();
			this.itsAttr = null;
		}
		
		this.itsType = null;
		this.itsContext = null;
		this.itsContextUsage = -1; 	
	}
	
	
	public void finalize() {
	}
	
	protected synchronized void addOut(GraphObject obj) {
		this.itsOutgoingArcs.add((Arc) obj);	
	}

	protected synchronized void addIn(GraphObject obj) {
		this.itsIncomingArcs.add((Arc) obj);
	}
	
	protected synchronized void removeOut(GraphObject obj) {
		this.itsOutgoingArcs.remove(obj);
	}

	protected synchronized void removeIn(GraphObject obj) { 
		this.itsIncomingArcs.remove(obj);
	}
		
	public final int getNumberOfArcs() {
		return this.itsOutgoingArcs.size() + this.itsIncomingArcs.size();
	}
	
	/**
	 * Iterate through all the arcs that I am the target of. 
	 *
	 * @see agg.xt_basis.Arc
	 */
	public final Iterator<Arc> getIncomingArcs() {
		return ((LinkedHashSet<Arc>) this.itsIncomingArcs).iterator();
	}

	/**
	 * @deprecated	replaced by <code>HashSet<Arc> getIncomingArcsSet()</code>.<br>
	 * 				The order of arcs may differ from the arc creation order.
	 */
	public final List<Arc> getIncomingArcsVec() {
		return (new ArrayList<Arc>(this.itsIncomingArcs));
	}

	/**
	 * @return  a set of ordered incoming arcs. The order of arcs is the arc creation order.
	 */
	public final Iterator<Arc> getIncomingArcsIterator() {
		return this.itsIncomingArcs.iterator();
	}
	
	/**
	 * @return  a set of incoming arcs. The order of arcs may differ from the arc creation order.
	 */
	public final HashSet<Arc> getIncomingArcsSet() {
		return this.itsIncomingArcs;
	}
	
	public final int getNumberOfIncomingArcs() {
		return this.itsIncomingArcs.size();
	}

	public final int getNumberOfIncomingArcs(Type t) {
		int n = 0;
		Iterator<Arc> iter = this.itsIncomingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t))
				n++;
		}
		return n;
	}

	public final int getNumberOfIncomingArcsOfTypeFromSourceType(Type t, Type srcType) {
		int n = 0;
		Iterator<Arc> iter = this.itsIncomingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t)) {
				if (srcType.isParentOf(go.getSource().getType()) ) {
					n++;					
				}
			}
		}
		return n;
	}
	
	/**
	 * 
	 * @param t 	type of incoming arcs
	 * @param src	(parent) type of the source node of incoming arcs
	 * @return 		number of incoming arcs
	 */
	public final int getNumberOfIncomingArcs(Type t, Type src) {
		int n = 0;
		Iterator<Arc> iter = this.itsIncomingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t)) {
				if (src.isParentOf(go.getSourceType()) ) {
					n++;
				}
				else if (!this.itsContext.isCompleteGraph() &&
							go.getSourceType().isParentOf(src)) {
					n++;
				}
			}
		}
		return n;
	}

	public final List<Arc> getIncomingArcs(Type t, Type src) {
		final List<Arc> result = new Vector<Arc>(2);
		Iterator<Arc> iter = this.itsIncomingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t)) {
				if (src.isParentOf(go.getSourceType()) ) {
					result.add(go);
				}
				else if (!this.itsContext.isCompleteGraph() &&
							go.getSourceType().isParentOf(src)) {
					result.add(go);
				}
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param t 	(parent) type of the source node of an incoming arc
	 * @return 		true when an incoming arc exists
	 */
	public boolean hasIncomingArcFrom(Type t) {
		Iterator<Arc> e = this.itsIncomingArcs.iterator();
		while (e.hasNext()) {
			Arc a = e.next();
			if (t.isParentOf(a.getSourceType())
					|| a.getSourceType().isParentOf(t)	
				) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Iterate through all the arcs that I am the source of. 
	 * 
	 * @see agg.xt_basis.Arc
	 */
	public final Iterator<Arc> getOutgoingArcs() {
		return ((LinkedHashSet<Arc>) this.itsOutgoingArcs).iterator();
	}

	public final int getNumberOfOutgoingArcs() {
		return this.itsOutgoingArcs.size();
	}

	/**
	 * @deprecated  	replaced by <code>HashSet<Arc> getOutgoingArcsSet()</code>.
	 * 					The order of arcs may differ from the arc creation order.
	 */
	public final List<Arc> getOutgoingArcsVec() {
		return (new ArrayList<Arc>(this.itsOutgoingArcs));
	}

	/**
	 * @return  a set of outgoing arcs. 
	 * The order of arcs may differ from the arc creation order.
	 */
	public final HashSet<Arc> getOutgoingArcsSet() {
		return this.itsOutgoingArcs;
	}
	
	/**
	 * @return  a set of ordered outgoing arcs. 
	 * The order of arcs may differ from the arc creation order.
	 */
	public final Iterator<Arc> getOutgoingArcsIterator() {
		return this.itsOutgoingArcs.iterator();
	}
	
	public final int getNumberOfOutgoingArcs(Type t) {
		int n = 0;
		Iterator<Arc> iter = this.itsOutgoingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t))
				n++;
		}
		return n;
	}

	public final int getNumberOfOutgoingArcsOfTypeToTargetType(Type t, Type tarType) {
		int n = 0;		
		Iterator<Arc> iter = this.itsOutgoingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t)) {
				if (tarType.isParentOf(go.getTarget().getType()) ) {
					n++;					
				}
			}
		}
		return n;
	}
	
	/**
	 * 
	 * @param t 	type of outgoing arcs
	 * @param tar	(parent) type of the target node of outgoing arcs
	 * @return 		number of outgoing arcs
	 */
	public final int getNumberOfOutgoingArcs(Type t, Type tar) {
		int n = 0;	
		Iterator<Arc> iter = this.itsOutgoingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t)) {
				if (tar.isParentOf(go.getTargetType())) {
					n++;
				}
				else if (!this.itsContext.isCompleteGraph() &&
							go.getTargetType().isParentOf(tar)) {
					n++;
				}
			}
		}
		return n;
	}

	/*
	 * Checks whether this node is the source of an edge of the given type 
	 * and the specified target node.
	 */
	public boolean hasArc(final Type arct, final Node tar) {
		return (this.getOutgoingArc(arct, tar) != null);
	}
	
	public final List<Arc> getOutgoingArcs(Type t, Type tar) {
		final List<Arc> result = new Vector<Arc>(2);	
		Iterator<Arc> iter = this.itsOutgoingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getType().compareTo(t)) {
				if (tar.isParentOf(go.getTargetType())) {
					result.add(go);
				}
				else if (!this.itsContext.isCompleteGraph() &&
							go.getTargetType().isParentOf(tar)) {
					result.add(go);
				}
			}
		}
		return result;
	}
	
	public final Arc getOutgoingArc(Type t, Node tar) {	
		Iterator<Arc> iter = this.itsOutgoingArcs.iterator();
		while (iter.hasNext()) {
			Arc go = iter.next();
			if (go.getTarget() == tar
					&& go.getType().compareTo(t))
				return go;
		}
		return null;
	}
	
	/**
	 * 
	 * @param t 	(parent) type of the target node of an outgoing arc
	 * @return 		true when an outgoing arc exists
	 */
	public boolean hasOutgoingArcTo(Type t) {
		Iterator<Arc> e = this.itsOutgoingArcs.iterator();
		while (e.hasNext()) {
			Arc a = e.next();
			if (t.isParentOf(a.getTargetType())
					|| a.getTargetType().isParentOf(t)	
				)
				return true;
		}
		return false;
	}

	public final int getNumberOfInOutArcs() {
		int nb = this.itsIncomingArcs.size() + this.itsOutgoingArcs.size();
		return nb;
	}
	/**
	 * Converts my type to a type key string that is used for search
	 * operations.
	 */
	public String convertToKey() {
		return this.getType().convertToKey();
	}
	
	public String resetTypeKey() {
		return this.getType().resetKey();
	}
	
	public boolean compareTo(GraphObject o) {
		if (!o.isNode()) {
			return false;
		}

		Node n = (Node) o;
//		if (!this.getObjectName().equals(n.getObjectName())) {
//			return false;
//		}
		if (!this.itsType.isParentOf(n.getType())) {
			return false;
		}
		if ((this.itsAttr == null && n.getAttribute() == null)
				|| ((this.attrExists() && n.attrExists()) 
						&& this.itsAttr.compareTo(n.getAttribute()))) {
			;
		} else {
			return false;
		}
		if (!this.compareMultiplicityTo(n)) {
			return false;
		}
		return true;
	}
	
	protected boolean compareMultiplicityTo(Node n) {
		if (this.itsContext.isTypeGraph()) {
			int minmax = this.itsType.getSourceMin();
			int n_minmax = n.getType().getSourceMin();
			if (minmax != n_minmax)
				return false;
			else {
				minmax = this.itsType.getSourceMax();
				n_minmax = n.getType().getSourceMax();
				if (minmax != n_minmax)
					return false;
			}
		}
		return true;
	}
	
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("Node", this);

		if (!this.visible)
			h.addAttr("visible", "false");

		if (!this.getObjectName().equals(""))
			h.addAttr("name", this.getObjectName());
		
		// if(!itsContextUsage.equals("")) h.addAttr("context",
		// itsContextUsage);

		h.addObject("type", this.itsType, false);

		// System.out.println("Node.XwriteObject ...
		// "+itsAttr.toString());
		// h.addObject("", itsAttr, true);

		// save multiplicity, if part of type graph
		if (this.itsContext != null && this.itsContext.isTypeGraph()) {
			// System.out.println("Node.Xwrite... is elem of type graph");
			int minmax = this.itsType.getSourceMin();
			if (minmax != Type.UNDEFINED)
				h.addAttr("sourcemin", Integer.toString(minmax));

			minmax = this.itsType.getSourceMax();
			if (minmax != Type.UNDEFINED)
				h.addAttr("sourcemax", Integer.toString(minmax));
		} //else 
		{
			// System.out.println("Node.XwriteObject ...
			// "+itsAttr.toString());
			h.addObject("", this.itsAttr, true);
		}
		h.close();
	}

	public void XreadObject(XMLHelper h) {
		if (h.isTag("Node", this)) {
			// System.out.println("Node.XreadObject: ");
			String str = h.readAttr("visible");
			str = h.readAttr("visible");
			this.visible = str.equals("false")? false: true;

			str = h.readAttr("name");
			this.setObjectName(str);
									
			if (this.itsType.getAttrType() != null
					|| this.itsType.hasInheritedAttribute()
					|| (this.itsContext != null && this.itsContext.xyAttr)
					)
				this.createAttributeInstance();

			AttrInstance attri = this.itsAttr;
			if (attri != null) {
				if (this.itsContext != null && this.itsContext.xyAttr) {
					xyAttr = true;
					agg.attribute.AttrType attrType = itsType.getAttrType();
					if(!((agg.attribute.impl.DeclTuple)attrType).containsName("thisX"))
						attrType.addMember(
							agg.attribute.facade.impl.DefaultInformationFacade.self().getJavaHandler(),
							"int", "thisX" );	
					if(!((agg.attribute.impl.DeclTuple)attrType).containsName("thisY"))
						attrType.addMember(
							agg.attribute.facade.impl.DefaultInformationFacade.self().getJavaHandler(),
							"int", "thisY" );
				}
				
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
			if (this.itsContext != null && this.itsContext.getAttrContext() != null
					&& this.itsAttr != null) {
				ValueTuple value = (ValueTuple) this.itsAttr;
				for (int i = 0; i < value.getSize(); i++) {
					ValueMember val = value.getValueMemberAt(i);
					if (val.isSet()) {
						if (val.getExpr().isVariable()) {
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
	}
	
	public final boolean isArc() {
		return false;
	}

	public final boolean isNode() {
		return true;
	}

	
	/** 
	 * @return true if don't exist any outgoing or incoming edges, otherwise false
	 */
	public final boolean isIsolated() {
		if (this.itsOutgoingArcs.isEmpty()
				&& this.itsIncomingArcs.isEmpty()) {
			return true;
		} 
		return false;
	}
	
	public String toString() {
		String result = "";
		String t = this.itsType.getStringRepr();
		if (this.itsAttr != null)
			result = " (" + "[" + hashCode() + "] " + "Node: " + t + ")  "
					+ this.itsAttr.toString();
		else
			result = " (" + "[" + hashCode() + "] " + "Node: " + t + ") ";
		return result;
	}

	/**
	 * Implements the AttrObserver. Propagates the change
	 * <code>agg.util.Change.OBJECT_MODIFIED<code>
	 * and object Pair (this, ev) 
	 * to its Graph if the attributes are changed.
	 */
	public void attributeChanged(AttrEvent ev) {
		if (this.itsContext != null) {
			
			Pair<Object, AttrEvent> p = new Pair<Object, AttrEvent>(this, ev);

			if (this.itsContext.isTypeGraph()) {
				if (ev.getID() == AttrEvent.MEMBER_VALUE_MODIFIED)
					propagateAttrValueToChildNode();
			}

			this.itsContext.propagateChange(new Change(Change.OBJECT_MODIFIED, p));
		}
	}

	private void propagateAttrValueToChildNode() {
		Enumeration<Type> children = this.getType().getChildren().elements();	
		while (children.hasMoreElements()) {
			Type cht = children.nextElement();
			List<Node> chnodes = this.itsContext.getNodesByParentType(cht);
			if (chnodes != null) {
				Node childNode = chnodes.get(0);
				if (childNode != this) {
					setValueToChildMember(childNode);
				}
			}
		}
	}
	
	private void setValueToChildMember(Node childNode) { 
		for (int i=0; i<((ValueTuple) this.itsAttr).getNumberOfEntries(); i++) {	
			ValueMember vm = ((ValueTuple) this.itsAttr).getValueMemberAt(i);
			if (vm.isSet()
					&& childNode.getAttribute() != null
					&& ((ValueTuple) childNode.getAttribute()).getValueAt(vm.getName()) == null) {
					((ValueTuple) childNode.getAttribute()).setExprValueAt(vm.getExprAsText(), vm.getName());
			}
		}
		
	}
	
	public void propagateAttrValueFromParentNode() {
		if (!this.itsContext.isTypeGraph())
			return;
		
		Enumeration<Type> parents = this.getType().getParents().elements();
		while (parents.hasMoreElements()) {
			Type part = parents.nextElement();
			List<Node> parnodes = this.itsContext.getNodesByParentType(part);
			if (parnodes != null) {
				Node parNode = parnodes.get(0);
				if (parNode != this) 
					setValueFromParentMember(parNode);
			}
		}
	}
	
	private void setValueFromParentMember(Node parentNode) {
		if (parentNode.getAttribute() != null && this.itsAttr == null) {
			this.createAttributeInstance();
			
			for (int i=0; i<((ValueTuple) this.itsAttr).getNumberOfEntries(); i++) {	
				ValueMember vm = ((ValueTuple) this.itsAttr).getValueMemberAt(i);
				ValueMember parvm = ((ValueTuple) parentNode.getAttribute()).getValueMemberAt(vm.getName());
				if (parvm != null && !vm.isSet() && parvm.isSet()) {
					((ValueTuple) this.itsAttr).setExprValueAt(parvm.getExprAsText(), parvm.getName());
				}
			}
		}
	}

	
}
