package agg.xt_basis;

import java.util.Vector;

import agg.attribute.AttrInstance;
import agg.attribute.AttrTuple;
import agg.attribute.AttrObserver;
import agg.attribute.AttrEvent;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;
import agg.util.XMLHelper;
import agg.util.XMLObject;


/**
 * GraphObject defines the common interface and implementations for Nodes and
 * Arcs.
 * 
 * @version $Id: GraphObject.java,v 1.51 2010/11/14 23:51:48 olga Exp $
 * @author $Author: olga $
 */
@SuppressWarnings("serial")
public abstract class GraphObject implements XMLObject , AttrObserver {
	
	protected String name = "";
	
	protected Graph itsContext=null;
	
	protected Type itsType=null;

	protected AttrInstance itsAttr=null;

	protected int itsContextUsage;

	protected boolean critical=false;

	protected boolean visible=true;

	
	public final void createAttributeInstance() {
		if (this.itsType.getAttrType() == null)
			this.itsType.createAttributeType();
		
		if (this.itsType.getAttrType() != null) {
			if (this.itsAttr == null) {
				this.itsAttr = AttrTupleManager.getDefaultManager().newInstance(
						this.itsType.getAttrType(), this.itsContext.getAttrContext());
				this.itsAttr.addObserver(this);
				this.itsContext.attributed = true;				
			} 
			else if (this.itsAttr.getType() != this.itsType.getAttrType()) {
				this.itsAttr.removeObserver(this);
				((ValueTuple) this.itsAttr).dispose();
				
				this.itsAttr = AttrTupleManager.getDefaultManager().newInstance(
						this.itsType.getAttrType(), this.itsContext.getAttrContext());
				this.itsAttr.addObserver(this);
			}
		}		
	}

	public void disposeAttributeInstance() {
		if (this.itsAttr != null) {			
			this.itsAttr.removeObserver(this);
			((ValueTuple) this.itsAttr).dispose();
			this.itsAttr = null;
		}
	}

	public final void setObjectName(final String n) {
		this.name = (n != null)? n: "";
	}
	
	public final String getObjectName() {
		return this.name;
	}
	
	public final int getContextUsage() {
		return this.itsContextUsage;
	}

	public final void setContextUsage(int aContextUsage) {
		this.itsContextUsage = aContextUsage;
	}

	public final Graph getContext() {
		return this.itsContext;
	}

	public final void setCritical(boolean b) {
		this.critical = b;
	}

	public final boolean isCritical() {
		return this.critical;
	}

	public final void setVisible(boolean b) {
		this.visible = b;
	}

	public final boolean isVisible() {
		return this.visible;
	}

	public final Type getType() {
		return this.itsType;
	}

	public final void setType(Type type) {
		this.itsType = type;
	}

	/**
	 * Converts my type to a type key string that can be used for search
	 * operations. For a node it is similar to
	 * <code> ((Node) this).getType().convertToKey() </code>, for an edge to
	 * <code> ((Arc) this).getSource().getType().convertToKey()
	 * + ((Arc) this).getType().convertToKey() 
	 * + ((Arc) this).getTarget().getType().convertToKey()
	 * </code>
	 */
	public abstract String convertToKey();

	public abstract String resetTypeKey();
	
	/** Return my attribute value. */
	public final AttrInstance getAttribute() {
		return this.itsAttr;
	}

	/** True when <code>this.itsAttr != null && this.itsAttr.getNumberOfEntries() > 0 </code> */
	public final boolean attrExists() {
		return this.itsAttr != null && this.itsAttr.getNumberOfEntries() > 0;
	}
	
	public final int getNumberOfAttributes() {
		return this.itsAttr==null? 0: this.itsAttr.getNumberOfEntries();
	}
		
	public final Vector<String> getVariableNamesOfAttribute() {
		return this.itsAttr==null? new Vector<String>(0): ((ValueTuple) this.itsAttr).getAllVariableNames();
	}

	public synchronized void copyAttributes(GraphObject orig) {
		if (orig.getAttribute() != null) {
			if (this.itsAttr == null)
				this.createAttributeInstance();	
			
			this.itsAttr.copyEntries(orig.getAttribute());
		}
	}

	/**
	 * Implements the AttrObserver and propagates attribute changes 
	 * to the attribute observers.
	 */
	public void attributeChanged(AttrEvent ev) {
//		Pair<Object, Integer> p = new Pair<Object, Integer>(this, Integer.valueOf(
//				ev.getID()));
//		itsContext.propagateChange(new Change(Change.OBJECT_MODIFIED, p));
	}

	public String attributeToString() {
		String result = "\nAttributes of : " + getType().getName() + " {\n";
		for (int i = 0; i < this.itsAttr.getNumberOfEntries(); i++) {
			ValueMember mem = (ValueMember) this.itsAttr.getMemberAt(i);
			result += "name: " + mem.getName() + "   value: " + mem.getExpr()
					+ "\n";

		}
		result += " }\n";
		return result;
	}

	public int getNumberOfIncomingArcs() {
		return 0;
	}
	
	public int getNumberOfOutgoingArcs() {
		return 0;
	}
	
	public int getNumberOfInOutArcs() {
		return 0;
	}
	
	public boolean doesChangeAttr(GraphObject go) {
		if (this.attrExists() && go.attrExists()) {
			for (int i = 0; i < this.itsAttr.getNumberOfEntries(); i++) {
				ValueMember vm = (ValueMember) this.itsAttr.getMemberAt(i);
				ValueMember vm2 = ((ValueTuple) go.getAttribute()).getEntryAt(vm.getName());
				if (vm2 != null
						&& vm.getDeclaration().getTypeName().equals(vm2.getDeclaration().getTypeName())
						&& vm.isSet() && vm2.isSet()
						&& !vm.getExprAsText().equals(vm2.getExprAsText()))
					return true;
			}
		}
		return false;
	}
	
	public boolean isAttrMemValDifferent(GraphObject go) {
		if (this.attrExists() && go.attrExists()) {
			for (int i = 0; i < this.itsAttr.getNumberOfEntries(); i++) {
				ValueMember vm = (ValueMember) this.itsAttr.getMemberAt(i);
				ValueMember vm2 = ((ValueTuple) go.getAttribute()).getEntryAt(vm.getName());
				if (vm2 != null
						&& vm.getDeclaration().getTypeName().equals(vm2.getDeclaration().getTypeName())
						&& vm.isSet() && vm2.isSet()
						&& !vm.getExprAsText().equals(vm2.getExprAsText()))
					return true;
			}
		}
		return false;
	}
	
	public boolean isAttrMemConstantValDifferent(GraphObject go) {
		if (this.attrExists() && go.attrExists()) {
			for (int i = 0; i < this.itsAttr.getNumberOfEntries(); i++) {
				ValueMember vm = (ValueMember) this.itsAttr.getMemberAt(i);
				ValueMember vm2 = ((ValueTuple) go.getAttribute()).getEntryAt(vm.getName());
				if (vm.isSet() && vm.getExpr().isConstant()) {
					if (vm2 != null && vm2.isSet() && vm2.getExpr().isConstant()
							&& !vm.getExprAsText().equals(vm2.getExprAsText())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean isAttrMemConstantValDifferent(GraphObject go1, GraphObject go2) {
		if (this.attrExists() && go1.attrExists() && go2.attrExists()) {
			for (int i = 0; i < this.itsAttr.getNumberOfEntries(); i++) {
				ValueMember vm = (ValueMember) this.itsAttr.getMemberAt(i);
				ValueMember vm2 = ((ValueTuple) go1.getAttribute()).getEntryAt(vm.getName());
				if (vm.isSet() && vm.getExpr().isConstant()) {
					if (vm2 != null && vm2.isSet() && vm2.getExpr().isConstant()
							&& !vm.getExprAsText().equals(vm2.getExprAsText())) {
						return true;
					} 
					else {
						vm2 = ((ValueTuple) go2.getAttribute()).getEntryAt(vm.getName());
						if (vm2 != null && vm2.isSet() && vm2.getExpr().isConstant()
								&& !vm.getExprAsText().equals(vm2.getExprAsText())) {
							return true;
						} 
					}
				}
			}
		}
		return false;
	}
	
	public abstract boolean isArc();

	public abstract boolean isNode();

	public abstract boolean compareTo(GraphObject o);

	public abstract void XwriteObject(XMLHelper h);

	public abstract void XreadObject(XMLHelper h);


	/**
	 * Checks whether the attribute observer wants a persistent connection to
	 * the given attribute. 
	 */
	public boolean isPersistentFor(AttrTuple at) {
		return false;
	}

}
