package agg.attribute.impl;

import java.util.Enumeration;
import java.util.Vector;

import agg.attribute.AttrContext;
import agg.attribute.AttrInstance;
import agg.attribute.AttrMember;
import agg.attribute.AttrTypeMember;
import agg.attribute.AttrEvent;
//import agg.attribute.view.AttrViewSetting;
import agg.attribute.view.impl.OpenViewSetting;
import agg.util.XMLHelper;

/**
 * Implementation of the interface agg.attribute.AttrInstance; Encapsulates a
 * tuple of attributes, so that a graphical object needs to talk to this one
 * object only;
 * 
 * @version $Id: ValueTuple.java,v 1.41 2010/11/17 14:05:53 olga Exp $
 * @author $Author: olga $
 */
public class ValueTuple extends TupleObject implements AttrInstance,
		AttrMsgCode {

	// Protected instance variables.

	/**
	 * 
	 */
	private static final long serialVersionUID = -8398830346220181522L;

	/** Type reference. */
	protected DeclTuple type;

	/**
	 * Context in which this instance is placed.
	 */
	protected ContextView context = null;

	/** GUI editor for this instance. */
	// transient protected AttrEditor editor = null;
	transient protected String errorMsg;

	// Public Constructors.

	/**
	 * @param manager
	 *            AttrTupleManager managing this tuple;
	 * @param type
	 *            Type, of which this tuple shall be an instance.
	 */
	public ValueTuple(AttrTupleManager manager, DeclTuple type) {
		this(manager, type, null, null);
	}

	/**
	 * @param manager
	 *            AttrTupleManager managing this tuple
	 * @param type
	 *            Type, of which this tuple shall be an instance
	 * @param context
	 *            The context in which this instance is placed
	 */
	public ValueTuple(AttrTupleManager manager, DeclTuple type,
			ContextView context) {
		this(manager, type, context, null);
	}

	/**
	 * @param manager
	 *            AttrTupleManager managing this tuple
	 * @param type
	 *            Type, of which this tuple shall be an instance
	 * @param context
	 *            The context in which this instance is placed
	 */
	public ValueTuple(AttrTupleManager manager, DeclTuple type,
			ContextView context, ValueTuple parent) {
		super(manager, null);
		resetContextView(context);
		setType(type);
		
		if (getContextView() == null)
			warn("Context = null", new RuntimeException());

		this.errorMsg = "";
	}
	
	/** Propagates the event to the observers, pretending to be the source. */
	protected void propagateEvent(TupleEvent e) {
		OpenViewSetting myOpenView = (OpenViewSetting) this.manager
				.getDefaultOpenView();
		if (e.id == AttrEvent.MEMBER_ADDED) {
			refreshParents();
			myOpenView.reorderTuple(this);
		}
		if (e.id == AttrEvent.MEMBER_DELETED) {
			refreshParents();
			myOpenView.reorderTuple(this);
		}
		fireAttrChanged(e.cloneWithNewSource(this));
	}

	public void refreshParents() {
		if (this.type != null) {
			Vector<AttrMember> memberCopy = new Vector<AttrMember>(this.members);
			this.members.clear();
			for (int i = 0; i < this.type.getSize(); i++) {
				DeclMember currentDecl = this.type.getDeclMemberAt(i);
				if (currentDecl != null) {
					boolean oldEntry = false;
					for (int j = 0; j < memberCopy.size(); j++) {
						if (((ValueMember) memberCopy.get(j)).getDecl() == currentDecl) {
							ValueMember vm = (ValueMember) memberCopy.get(j);
							this.members.add(vm);
							oldEntry = true;
							break;
						}
					}
					if (!oldEntry) {
						this.members.add(newMember(currentDecl));
					}
				}
			}
		}
	}
	
	public void resetContextView(ContextView view) {
		this.context = view;
	}

	
	protected ContextView getContextView() {
		return this.context;
	}

	protected void assignParent(TupleObject newParent) {
		super.assignParent(newParent);
		if (this.parent != null)
			copyEntries((ValueTuple) this.parent);
	}

	protected void setType(DeclTuple type) {
		this.type = type;
		if (this.type != null) {
			this.type.addObserver(this);
			adaptToType();
			if (this.parent != null)
				copyEntries((AttrInstance) this.parent);
		}
	}

	/**
	 * Causes the value container (Vector) size to match the type container
	 * size.
	 */
	protected void adaptToType() {
		for (int i = 0; i < getTupleType().getSize(); i++) {
			if (getTupleType().getDeclMemberAt(i) != null)
				addMember(newMember(getTupleType().getDeclMemberAt(i)));
		}
	}

	protected ValueMember newMember(DeclMember decl) {
		return new ValueMember(this, decl);
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	public AttrContext getContext() {
		return getContextView();
	}


	/** For debugging: displaying itself in the logging window. */
	public void log() {
		String line = "";
		for (int i = 0; i < this.getNumberOfEntries(); i++) {
			line = line + getLogEntry(i);
		}
		logPrintln(line);
	}

	/** Subprocedure for 'log()', creates a text showing the entry at 'index' */
	protected String getLogEntry(int index) {
		return ("\n    " + index + ". (" + this.getTypeAsString(index) + ") "
				+ this.getNameAsString(index) + " = " + this
				.getValueAsString(index));
	}

	//
	// Implementation of abstract methods from TupleObject.
	//

	/** Retrieving the type of this tuple instance */
	public DeclTuple getTupleType() {
		return this.type;
	}

	//
	// Update methods.

	public void updateMemberAdded(TupleEvent e) {
		if (e.getSource() instanceof DeclTuple) {
			addMember(newMember(getTupleType().getDeclMemberAt(e.getIndex())));
		}
		propagateEvent(e);
	}

	public void updateMemberDeleted(TupleEvent e) {
		if (e.getSource() instanceof DeclTuple) {
			deleteMemberAt(e.getIndex());
			propagateEvent(e);
		}
	}

	public void updateMemberRetyped(TupleEvent e) {
		if (e.getSource() instanceof DeclTuple) {
			ValueMember m = getValueMemberAt(e.getIndex());
			if (m != null)
				m.typeChanged();
			// getValueMemberAt( e.getIndex() ).typeChanged();
		}
		propagateEvent(e);
	}

	/** Interface synchronization. */
	public void updateMemberValueChanged(TupleEvent e) {
		ValueMember val = (ValueMember) e.getSource().getMemberAt(e.getIndex());
		getValueMemberAt(e.getIndex()).copy(val);
		propagateEvent(e);
	}

	//
	// public void updateMemberValueCorrectness( TupleEvent e ){
	// getValueMemberAt( e.getIndex() ).check();
	// propagateEvent( e );
	// }
	//

	//
	// Implementation of the AttrInstance interface.
	//

	/**
	 * Convenience method for internal operations; works much like the generic
	 * getMemberAt( int index ), but returns the appropriate member type.
	 * 
	 * @see agg.attribute.impl.TupleObject#getMemberAt( int )
	 */
	public ValueMember getValueMemberAt(int index) {
		return (ValueMember) getMemberAt(index);
	}

	/**
	 * Convenience method for internal operations; works much like the generic
	 * getMemberAt( String name ), but returns the appropriate member type.
	 * 
	 * @see agg.attribute.impl.TupleObject#getMemberAt( String )
	 */
	public ValueMember getValueMemberAt(String name) {
		return (ValueMember) getMemberAt(name);
	}

	/** Getting a simple representation of a value as String. */
	public String getValueAsString(int entryIndex) {
		if (getMemberAt(entryIndex) != null)
			return getMemberAt(entryIndex).toString();
		
		return "";
	}

	/**
	 * Copying the contents of an attribute instance into another; The reference
	 * to the attribute type is shared.
	 */
	public void copy(AttrInstance source) {
		// System.out.println("ValueTuple.copy BEGIN");
		ValueTuple from = (ValueTuple) source;
		if (!this.type.compareTo(from.getTupleType())) {
			System.out
					.println("ValueTuple.copy(AttrInstance)::  Tried to copy from an AttrInstance with a different type!");
			throw new AttrImplException(
					"Tried to copy from an AttrInstance with a different type!");
		}
		this.manager = from.manager;
		// assignParent( from.getParent());

		// // multiple inheritance - olga
		// for(int i=0; i<from.getParents().size(); i++){
		// addParent( from.getParents().get(i) );
		// }//

		int length = from.getSize();
		for (int i = 0; i < length; i++) {
			ValueMember val = getValueMemberAt(i);
			// System.out.println(from.getValueMemberAt( i )+" copy to "+val);
			ValueMember valfrom = from.getValueMemberAt(i);
			if (val != null) {
				val.copy(valfrom);
				// System.out.println(getValueMemberAt( i ));

				if (valfrom instanceof VarMember)
					((VarMember) val).setMark(((VarMember) valfrom).getMark());
				else if (valfrom instanceof CondMember)
					((CondMember) val)
							.setMark(((CondMember) valfrom).getMark());
			}
		}
		// System.out.println("ValueTuple.copy END");
	}

	/*
	private boolean contains(ValueMember m) {
		int length = getSize();
		for (int i = 0; i < length; i++) {
			ValueMember val = getValueMemberAt(i);
			if (val.getHoldingTuple() == m.getHoldingTuple())
				if (val.compareTo(m))
					return true;
		}
		return false;
	}
*/
	
	/** Copying the contents of an attribute instance into another; */
	public void copyEntries(AttrInstance source) {
		// System.out.println("ValueTuple.copyEntries: ...");
		ValueTuple from = (ValueTuple) source;
		DeclTuple srcType = from.getTupleType();
		DeclTuple dstType = this.type;
		if (!srcType.compareTo(dstType) && !srcType.isSubclassOf(dstType)
				&& !dstType.isSubclassOf(srcType)) {
			// System.out.println("ValueTuple.copyEntries: The source of copy
			// has wrong type.");
			// throw new RuntimeException( "The copy source has a wrong
			// type.\n" );
			return;
		}

		int length = Math.min(srcType.getSize(), dstType.getSize());
		for (int i = 0; i < length; i++) {
			ValueMember val = getValueMemberAt(i);
			if (val != null) {				
				ValueMember valfrom = from.getValueMemberAt(val.getName());
				if (valfrom != null) {
					val.copy(valfrom);
					val.setTransient(valfrom.isTransient);
				}
			}
		}
	}

	public void copyEntriesToSimilarMembers(AttrInstance source) {
		ValueTuple from = (ValueTuple) source;
		DeclTuple srcType = from.getTupleType();
		DeclTuple dstType = this.type;
		if (!srcType.compareTo(dstType) && !srcType.isSubclassOf(dstType)
				&& !dstType.isSubclassOf(srcType)) {
			// /throw new RuntimeException( "The copy source has a wrong
			// type.\n" );
			return;
		}

		for (int i = 0; i < dstType.getSize(); i++) {
			ValueMember val = getValueMemberAt(i);
			if (val != null) {
				ValueMember valfrom = from.getValueMemberAt(val.getName());
				if (valfrom != null
						&& val.getDeclaration().getTypeName().equals(
								valfrom.getDeclaration().getTypeName())) {
					val.copy(valfrom);
					val.setTransient(valfrom.isTransient);
				}
				else
					continue;
			}
		}
	}

	/** Copying the contents of an attribute instance into another; */
	public void adoptEntriesWhereEmpty(AttrInstance source) {
		ValueTuple from = (ValueTuple) source;
		DeclTuple srcType = from.getTupleType();
		DeclTuple dstType = this.type;
		if (!srcType.compareTo(dstType) 
				&& !srcType.isSubclassOf(dstType)
				&& !dstType.isSubclassOf(srcType)) {
			throw new RuntimeException("The specified source has a wrong type.\n");
		}

		int length = Math.min(srcType.getSize(), dstType.getSize());
		for (int i = 0; i < length; i++) {
			ValueMember dst = getValueMemberAt(i);
			ValueMember src = from.getValueMemberAt(i);
			if (src == null)
				continue;

			if (dst.isEmpty() && !src.isEmpty()) {
				dst.copy(src);
			}

		}
	}

	/**
	 * Unset the value of the own attribute members (not of attribute members of
	 * its parents). The value of its attribute member is null after this.
	 */
	public void unsetValue() {
		for (int i = 0; i < getSize(); i++) {
			ValueMember val = getValueMemberAt(i);
			if (val.getDecl().getHoldingTuple() == this.getTupleType())
				val.setExpr(null);
		}
	}

	public void unsetValueOfUsedVariable(final AttrContext cntxt) {
		for (int i = 0; i < getSize(); i++) {
			ValueMember val = getValueMemberAt(i);
			if (val.isSet() && val.getExpr().isVariable()) {
				VarMember var = cntxt.getVariables().getVarMemberAt(val.getExprAsText());
				if (var != null) {
					var.setExpr(null);
				}
			}
		}
	}
	
	/**
	 * Unset the value which is an expression of the own attribute members (not of attribute members of
	 * its parents). The value of its attribute member is null after this.
	 */
	public void unsetValueAsExpr() {
		for (int i = 0; i < getSize(); i++) {
			ValueMember val = getValueMemberAt(i);
			if (val.getDecl().getHoldingTuple() == this.getTupleType()
					&& val.isSet() && val.getExpr().isComplex())
				val.setExpr(null);
		}
	}
	
	/*
	 * Applying a rule; the substitutions occur "in-place" (in the recipient);
	 * In Graph Transformation, this method is applied to attributes of host
	 * graph objects, "rightSide" being an attribute of the right side of the
	 * rule and "context" being the "match"-context built up by subsequently
	 * matching the attributes of corresponding graphical objects. Needs the
	 * graphobject from the graph G.
	 */
//	public void apply(AttrInstance rightSide, 
//						AttrContext attrcontext,
//						AttrInstance g) {
//		apply(rightSide, attrcontext);
//	}

	/*
	 * This method works like apply( AttrInstance, AttrContext, AttrInstance)
	 * additionally, allows using variables
	 * without value in value of attribute member.
	 */
//	public void apply(AttrInstance rightSide, AttrContext attrcontext,
//			AttrInstance g, boolean allowVariableWithoutValue) {
//		apply(rightSide, attrcontext, allowVariableWithoutValue, false);
//	}
	
	/*
	 * This method works like apply( AttrInstance, AttrContext, AttrInstance)
	 * additionally, allows using variables
	 * without value in value of attribute member.
	 * If similarVariableName is TRUE, then the name of the variable from rightSide
	 * must be equal to the name of the current variable.
	 * The similarVariableName option is only used when allowVariableWithoutValue is TRUE.
	 */
//	public void apply(AttrInstance rightSide, AttrContext attrcontext,
//			AttrInstance g, boolean allowVariableWithoutValue, boolean similarVariableName) {
//		
//		apply(rightSide, attrcontext, allowVariableWithoutValue, similarVariableName);
//	}

	/**
	 * Applying a rule; the substitutions occur "in-place" (in the recipient);
	 * In Graph Transformation, this method is applied to attributes of host
	 * graph objects, "rightSide" being an attribute of the right side of the
	 * rule and "context" being the "match"-context built up by subsequently
	 * matching the attributes of corresponding graphical objects.
	 */
	public void apply(AttrInstance rightSide, AttrContext attrcontext) {
		ValueMember left, right;
		AttrContext matchContext = attrcontext;
		ValueTuple rightTuple = (ValueTuple) rightSide;

		if (matchContext == null)
			matchContext = rightTuple.getContext();
		for (int i = 0; i < Math.min(getSize(), ((ValueTuple) rightSide)
				.getSize()); i++) {
			left = getValueMemberAt(i);
			right = rightTuple.getValueMemberAt(i);
			if (left != null)
				left.apply(right, matchContext);
		}
	}

	/**
	 * This method works like apply( AttrInstance,context )
	 * additionally, allow using variables without value in the
	 * value of attribute members
	 */
	public void apply(AttrInstance rightSide, AttrContext attrcontext,
			boolean allowVariableWithoutValue) {
		apply(rightSide, attrcontext, allowVariableWithoutValue, false);
	}

	/**
	 * This method works like apply( AttrInstance,context )
	 * additionally, allow using variables without value in the
	 * value of attribute members.
	 * If equalVariableName is TRUE, then the name of the variable from rightSide
	 * must be equal to the name of the current variable.
	 * The equalVariableName option is only used when allowVariableWithoutValue is TRUE.
	 */
	public void apply(AttrInstance rightSide, AttrContext attrcontext,
			boolean allowVariableWithoutValue, boolean equalVariableName) {
		
		if (!allowVariableWithoutValue) {
			apply(rightSide, attrcontext);
			return;
		}

		AttrContext matchContext = attrcontext;
		ValueTuple rightTuple = (ValueTuple) rightSide;
		if (matchContext == null)
			matchContext = rightTuple.getContext();
		for (int i = 0; i < getSize(); i++) {
			ValueMember left = getValueMemberAt(i);
			ValueMember right = rightTuple.getValueMemberAt(i);
			if (left != null)
				left.apply(right, matchContext, allowVariableWithoutValue, equalVariableName);
		}
	}
	
	public int getNumberOfFreeVariables(AttrContext ctx) {
		ContextView contview = (ContextView) ctx;
		ValueMember val;
		int nFree = 0;
		String varName;
		Vector<String> names = new Vector<String>(10, 10);

		for (int i = 0; i < getSize(); i++) {
			val = getValueMemberAt(i);
			if ((val != null) && (val.getExpr() != null) && val.getExpr().isVariable() 
					&& !val.isValid()) {
				varName = val.getExpr().toString();
				if (!names.contains(varName)) {
					names.addElement(varName);
					if (contview == null || contview.getExpr(varName) != null) {
						nFree++;
					}
				}
			}
		}
		return nFree;
	}

	public void XwriteObject(XMLHelper h) {
		if (isEmpty())
			return;
		int num = getSize();
		for (int i = 0; i < num; i++) {
			ValueMember val = getValueMemberAt(i);
			if (val != null && val.isSet()) {
				h.addObject("", val, true);
			}
		}
	}

	public void XreadObject(XMLHelper h) {
		Enumeration<?> en = h.getEnumeration("", null, true, "Attribute");
		while (en.hasMoreElements()) {
			h.peekElement(en.nextElement());
			AttrTypeMember typeMem = (AttrTypeMember) h.getObject("type", null,
					false);
			if (typeMem != null) {
				String name = typeMem.getName();
				ValueMember mem = getValueMemberAt(name);
				if (mem != null) {
					h.enrichObject(mem);
				}
			}
			h.close();
		}
	}

	// End of AttrInstance interface implementation.

	public boolean canMatchTo(ValueTuple target, ContextView attrcontext) {
		ValueMember src, tar;
		for (int i = 0; i < getSize(); i++) {
			src = getValueMemberAt(i);
			if (src != null) {
				String name = src.getName();
				tar = target.getValueMemberAt(name);
				if (src.getExpr() == null && tar.getExpr() == null) {
					continue;
				}
				if ((!src.canMatchTo(tar, attrcontext))) {
					this.errorMsg = src.getErrorMsg();
					return false;
				}
			}
		}
		return true;
	}

	public boolean canMatchChild2Parent(ValueTuple target, ContextView attrcontext) {
		ValueMember src, tar;
		for (int i = 0; i < getSize(); i++) {
			src = getValueMemberAt(i);
			if (src != null) {
				String name = src.getName();
				tar = target.getValueMemberAt(name);
				if (tar != null) {
					if (src.getExpr() == null && tar.getExpr() == null) {
						continue;
					}
					if ((!src.canMatchTo(tar, attrcontext))) {
						this.errorMsg = src.getErrorMsg();
						return false;
					}
				}
			}
		}
		return true;
	}
	
	/** Performs a match in a per-element-style. */
	public String[] matchTo(ValueTuple target, ContextView attrcontext) {
//		logPrintln(VerboseControl.logTrace, "ValueTuple:\n->matchTo");
		ValueMember src, tar;
		Vector<String> matches = new Vector<String>();
		String match = null;
		String assignedVariables[];

		if (!canMatchTo(target, attrcontext)) {
			this.errorMsg = this.errorMsg + "\nAttribute don't match.";
			throw new AttrImplException(ATTR_DONT_MATCH);
		}
//		logPrintln(VerboseControl.logMapping, "try to match to " + getSize()
//				+ " members");
		for (int i = 0; i < getSize(); i++) {
			src = getValueMemberAt(i);
			if (src != null) {
				String name = src.getName();
				tar = target.getValueMemberAt(name);
				match = src.matchTo(tar, attrcontext);
				if (match != null) {
					matches.addElement(match);
				}
			}
		}
//		logPrintln(VerboseControl.logMapping, "matches= " + matches);
		assignedVariables = new String[matches.size()];
		matches.copyInto(assignedVariables);
//		logPrintln(VerboseControl.logTrace, "ValueTuple:\n<-matchTo");
		return assignedVariables;
	}

	public String[] matchChild2Parent(ValueTuple target, ContextView attrcontext) {
//		logPrintln(VerboseControl.logTrace, "ValueTuple:\n->matchTo");
		ValueMember src, tar;
		Vector<String> matches = new Vector<String>();
		String match = null;
		String assignedVariables[];

		if (!canMatchChild2Parent(target, attrcontext)) {
			this.errorMsg = this.errorMsg + "\nAttribute don't match.";
			throw new AttrImplException(ATTR_DONT_MATCH);
		}
//		logPrintln(VerboseControl.logMapping, "try to match to " + getSize()
//				+ " members");
		for (int i = 0; i < getSize(); i++) {
			src = getValueMemberAt(i);
			if (src != null) {
				String name = src.getName();
				tar = target.getValueMemberAt(name);
				if (tar != null) {
					match = src.matchTo(tar, attrcontext);
					if (match != null) {
						matches.addElement(match);
					}
				}
			}
		}
//		logPrintln(VerboseControl.logMapping, "matches= " + matches);
		assignedVariables = new String[matches.size()];
		matches.copyInto(assignedVariables);
//		logPrintln(VerboseControl.logTrace, "ValueTuple:\n<-matchTo");
		return assignedVariables;
	}

	
	public void logPrintln(boolean logTopic, String msg) {
		super.logPrintln(logTopic, msg);
		if (logTopic) {
			for (int i = 0; i < getSize(); i++) {
				System.out.println(getValueMemberAt(i));
			}
		}
	}

	public String toString() {
		String result = super.toString() + " ";
		for (int i = 0; i < getSize(); i++) {
			result += getValueMemberAt(i) + "  ";
		}
		return result;
	}

	//
	//
	// >>>>>>>>> DEPRECATED
	//
	//

	/**
	 * Setting the value of the specified entry;
	 * 
	 * @param valueText
	 *            String representation of the new value;
	 * @param index
	 *            specifies the entry to change; Used by AttrEditor instances.
	 */
	public void setValueAt(String valueText, int index) {
		if (getValueMemberAt(index) != null)
			getValueMemberAt(index).setExprAsText(valueText);
	}

	/**
	 * Setting the value of the specified entry;
	 * 
	 * @param value
	 *            the new value;
	 * @param index
	 *            specifies the entry to change; Used by AttrEditor instances.
	 */
	public void setValueAt(ValueMember value, int index) {
		if (getValueMemberAt(index) != null)
			getValueMemberAt(index).copy(value);
	}

	/** Test, if a value is set or not. */
	public boolean isValueSetAt(String name) {
		if (getValueMemberAt(name) != null)
			return !getValueMemberAt(name).isEmpty();
		
		return false;
	}

	/** Test, if a value is set or not. */
	public boolean isValueSetAt(int index) {
		if (getValueMemberAt(index) != null)
			return !getValueMemberAt(index).isEmpty();
		
		return false;
	}

	/** Retrieving the value of an entry. */
	public Object getValueAt(String name) {
		if (getValueMemberAt(name) != null)
			return getValueMemberAt(name).getExprAsObject();
		
		return null;
	}

	/**
	 * Setting the value of an entry directly.
	 * 
	 * @param value
	 *            Any object instance.
	 * @param name
	 *            specifies the entry to change.
	 */
	public void setValueAt(Object value, String name) {
		if (getValueMemberAt(name) != null)
			getValueMemberAt(name).setExprAsObject(value);
	}

	public boolean isEmpty() {
		for (int i = 0; i < getSize(); i++) {
			if (!getValueMemberAt(i).isEmpty())
				return false;
		}
		return true;
	}

	/**
	 * Evaluating an expression and setting its value as an entry.
	 * 
	 * @param expr
	 *            textual expression representation;
	 * @param name
	 *            specifies the entry to change.
	 */
	public void setExprValueAt(String expr, String name) {
		if (getValueMemberAt(name) != null)
			getValueMemberAt(name).setExprAsEvaluatedText(expr);
	}

	/**
	 * Setting an expression as an entry without immediate evaluation. Syntax
	 * and type checking are performed.
	 * 
	 * @param expr
	 *            textual expression representation;
	 * @param name
	 *            specifies the entry to change;
	 */
	public void setExprAt(String expr, String name) {
		if (getValueMemberAt(name) != null)
			getValueMemberAt(name).setExprAsText(expr);
	}

	/** Getting a single value, referenced by 'index'. */
	public ValueMember getEntryAt(int index) {
		return getValueMemberAt(index);
	}

	/** Getting a single value, referenced by 'name'. */
	public ValueMember getEntryAt(String name) {
		return getValueMemberAt(name);
	}

	public ValueMember getEntryWithValueAsText(final String valueText) {
		for (int i = 0; i < getSize(); i++) {
			final ValueMember member = getValueMemberAt(i);
			if (member.isSet() 
					&& member.getExprAsText().equals(valueText)) {
				return member;
			}
		}
		return null;
	}
	
	public Vector<String> getAllVariableNamesOfExpressions() {
		Vector<String> resultVector = new Vector<String>();
		for (int i = 0; i < getSize(); i++) {
			ValueMember member = getValueMemberAt(i);
			if (member.isSet() && member.getExpr().isComplex()) {
				Vector<String> names = member.getAllVariableNamesOfExpression();
				for (int j = 0; j < names.size(); j++) {
					String name = names.elementAt(j);
					if (!resultVector.contains(name))
						resultVector.addElement(name);
				}
			}
		}
		// System.out.println("ValueTuple: Expression vars: "+resultVector);
		return resultVector;
	}

	public Vector<String> getAllVariableNames() {
		Vector<String> result = new Vector<String>();
		for (int i = 0; i < this.getNumberOfEntries(); i++) {
			ValueMember member = getValueMemberAt(i);
			Vector<String> names = member.getAllVariableNamesOfExpression();
			for (int j = 0; j < names.size(); j++) {
				String name = names.elementAt(j);
				if (!result.contains(name))
					result.addElement(name);
			}
		}
		// System.out.println("ValueTuple: vars: "+resultVector);
		return result;
	}

	public boolean compareTo(AttrInstance another) {
		ValueTuple vt = (ValueTuple) another;
		// compare tuple type
		if (!this.type.compareTo(vt.getTupleType()))
			return false;
		// compare member value
		int length = getSize();
		for (int i = 0; i < length; i++) {
			ValueMember v = getValueMemberAt(i);
			ValueMember v1 = vt.getValueMemberAt(i);
			if ((v.getExpr() == null) && (v1.getExpr() == null))
				;
			else if ((v.getExpr() == null) && (v1.getExpr() != null))
				return false;
			else if ((v.getExpr() != null) && (v1.getExpr() == null))
				return false;
			else if (!v.getExpr().equals(v1.getExpr()))
				return false;
		}
		return true;
	}
	
	public void reflectTransientFrom(AttrInstance another) {
		ValueTuple vt = (ValueTuple) another;
		int length = getSize();
		for (int i = 0; i < length; i++) {
			ValueMember v = getValueMemberAt(i);
			ValueMember v1 = vt.getValueMemberAt(v.getName());
			if (v1 != null && v1.isTransient)
				v.setTransient(true);
		}
	}
	
	public void showValue() {
		System.out.println("Attribute tuple value: ");
		for (int i = 0; i < getSize(); i++) {
			ValueMember v = getValueMemberAt(i);
			if (v != null) {
				System.out.println(v.getDeclaration().getTypeName() + " : "
						+ v.getName() + " : " + v.getExpr());
			}
		}
		System.out.println("================================");
	}

}
/*
 * $Log: ValueTuple.java,v $
 * Revision 1.41  2010/11/17 14:05:53  olga
 * tuning
 *
 * Revision 1.40  2010/11/04 10:56:25  olga
 * tuning
 *
 * Revision 1.39  2010/09/23 08:14:09  olga
 * tuning
 *
 * Revision 1.38  2010/08/05 14:12:32  olga
 * tuning
 *
 * Revision 1.37  2010/05/05 16:15:43  olga
 * tuning and tests
 *
 * Revision 1.36  2010/05/03 21:58:48  olga
 * tuning
 *
 * Revision 1.35  2010/03/31 21:08:13  olga
 * tuning
 *
 * Revision 1.34  2010/03/21 21:22:54  olga
 * tuning
 *
 * Revision 1.33  2010/03/17 21:37:37  olga
 * tuning
 *
 * Revision 1.32  2010/03/08 15:37:42  olga
 * code optimizing
 *
 * Revision 1.31  2009/10/12 08:27:49  olga
 * ApplOfRS-improved and bug fixed
 *
 * Revision 1.30  2009/03/19 09:31:07  olga
 * CPE: attr check improved
 *
 * Revision 1.29  2009/03/12 10:57:44  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.28  2008/07/14 07:35:46  olga
 * Applicability of RS - new option added, more tuning
 * Node animation - new animation parameter added,
 * Undo edit manager - possibility to disable it when graph transformation
 * because it costs much more time and memory
 *
 * Revision 1.27  2008/05/22 15:21:07  olga
 * ARS - implementing further details
 *
 * Revision 1.26  2008/02/27 20:40:23  olga
 * Code tuning
 *
 * Revision 1.25  2007/12/10 08:42:58  olga
 * CPA of grammar with node type inheritance for attributed graphs - bug fixed
 *
 * Revision 1.24  2007/11/05 09:18:17  olga
 * code tuning
 *
 * Revision 1.23  2007/11/01 09:58:14  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.22  2007/09/24 09:42:34  olga
 * AGG transformation engine tuning
 * Revision 1.21 2007/09/10 13:05:19 olga In this
 * update: - package xerces2.5.0 is not used anymore; - class
 * com.objectspace.jgl.Pair is replaced by the agg own generic class
 * agg.util.Pair; - bugs fixed in: usage of PACs in rules; match completion;
 * usage of static method calls in attr. conditions - graph editing: added some
 * new features Revision 1.20 2007/06/13 08:33:09 olga Update: V161
 * 
 * Revision 1.19 2007/05/07 07:59:30 olga CSP: extentions of CSP variables
 * concept
 * 
 * Revision 1.18 2007/02/05 12:33:48 olga CPA: chengeAttribute
 * conflict/dependency : attributes with constants bug fixed, but the critical
 * pairs computation has still a gap.
 * 
 * Revision 1.17 2007/01/29 09:44:33 olga Bugs fiixed, that occur during the
 * extension a non-attributed grammar by attributes.
 * 
 * Revision 1.16 2006/12/13 13:32:58 enrico reimplemented code
 * 
 * Revision 1.15 2006/11/15 09:00:32 olga Transform with input parameter : bug
 * fixed
 * 
 * Revision 1.14 2006/11/01 11:17:29 olga Optimized agg sources of CSP
 * algorithm, match usability, graph isomorphic copy, node/edge type
 * multiplicity check for injective rule and match
 * 
 * Revision 1.13 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.12 2006/05/29 07:59:42 olga GUI, undo delete - tuning.
 * 
 * Revision 1.11 2006/04/12 09:01:57 olga Layered graph constraints tuning
 * 
 * Revision 1.10 2006/04/10 09:19:30 olga Import Type Graph, Import Graph -
 * tuning. Attr. member type check: if class does not exist. Graph constraints
 * for a layer of layered grammar.
 * 
 * Revision 1.9 2006/04/03 08:57:50 olga New: Import Type Graph and some bugs
 * fixed
 * 
 * Revision 1.8 2006/03/01 09:55:47 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.7 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.6 2005/11/30 12:11:11 olga copyEntries : null fixed
 * 
 * Revision 1.5 2005/11/09 13:14:54 enrico fixed visibility reset on creation
 * and deletion of attributes
 * 
 * Revision 1.4 2005/11/07 09:38:07 olga Null pointer during retype attr. member
 * fixed.
 * 
 * Revision 1.3 2005/11/03 14:26:18 olga Null fixed
 * 
 * Revision 1.2 2005/11/03 13:03:36 enrico fixed NullPointerException on
 * attribute retyping
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.3 2005/07/11 14:23:08 olga Error fixed during matching with
 * variable
 * 
 * Revision 1.2.2.4 2005/07/18 11:39:51 enrico more specific use of
 * refreshParents
 * 
 * Revision 1.2.2.3 2005/07/04 11:41:37 enrico basic support for inheritance
 * 
 * Revision 1.2.2.2 2005/06/20 20:55:03 enrico ported changes from latest
 * inheritance version
 * 
 * Revision 1.2.2.1 2005/06/20 13:50:55 enrico refreshParents eingefuegt
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.18 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.17 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.16 2004/07/15 11:13:10 olga CPs letzter Schliff
 * 
 * Revision 1.15 2004/06/09 11:32:54 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.14 2004/05/13 17:54:09 olga Fehlerbehandlung
 * 
 * Revision 1.13 2004/05/06 17:23:26 olga graph matching OK
 * 
 * Revision 1.12 2004/04/28 12:46:38 olga test CSP
 * 
 * Revision 1.11 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.10 2004/01/07 09:37:15 olga test
 * 
 * Revision 1.9 2003/12/18 16:25:49 olga Tests.
 * 
 * Revision 1.8 2003/03/05 18:24:21 komm sorted/optimized import statements
 * 
 * Revision 1.7 2003/02/20 17:38:20 olga Tests
 * 
 * Revision 1.6 2003/02/03 17:46:30 olga new method : compareTo(AttrInstance a)
 * 
 * Revision 1.5 2003/01/15 16:29:06 olga apply angepasst
 * 
 * Revision 1.4 2003/01/15 11:35:44 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.3 2002/11/25 14:56:27 olga Der Fehler unter Windows 2000 im
 * AttributEditor ist endlich behoben. Es laeuft aber mit Java1.3.0 laeuft
 * endgueltig nicht. Also nicht Java1.3.0 benutzen!
 * 
 * Revision 1.2 2002/11/11 09:41:16 olga Nur Testausgaben
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.18 2001/11/15 16:37:59 olga Nur Tests wegen DIS_AGG
 * 
 * Revision 1.17 2001/05/14 12:10:43 olga Aenderungen wegen GenGEd: Match
 * Variable auf Variable - als Ergebnis nach Termersaetzung erscheint die
 * Variable in Klemmern: (x). Nur im Fall mit nur eine Variable werden die
 * Klammern weggelassen. Andere Aenderungen: Tests.
 * 
 * Revision 1.16 2000/12/07 14:23:35 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.15 2000/06/05 14:07:57 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.14 2000/05/24 10:02:34 olga Nur Testausgaben eingebaut bei der
 * Suche nach dem Fehler in DISAGG : Match Konstante auf Konstante
 * 
 * Revision 1.13 2000/05/17 11:57:03 olga Testversion an Gabi mit diversen
 * Aenderungen. Fehler sind moeglich!!
 * 
 * Revision 1.12 2000/05/17 11:33:35 shultzke diverse Aenderungen. Version von
 * Olga wird erwartet
 * 
 * Revision 1.11 2000/04/05 12:09:28 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
