package agg.attribute;

import agg.util.XMLObject;

/**
 * Interface of tuples of attribute values.
 * 
 * @version $Id: AttrInstance.java,v 1.4 2010/08/05 14:12:04 olga Exp $
 * @author $Author: olga $
 */
public interface AttrInstance extends AttrTuple, XMLObject {

	/** Retrieving the type of an instance. */
	public AttrType getType();

	/** Retrieving the context of an instance. */
	public AttrContext getContext();

	/** Test, if a value is set or not. */
	public boolean isValueSetAt(String name);

	/**
	 * Retrieving the value of an entry. If the result is 'null', the reason can
	 * be: 1. The value is set as 'null'; 2. The value is not set at all. For
	 * testing, if the value was set as 'null' or not set at all, use
	 * 'isValueSetAt()' of this class.
	 */
	public Object getValueAt(String name);

	/**
	 * Setting the value of an entry directly.
	 * 
	 * @param value
	 *            Any object instance.
	 * @param name
	 *            specifies the entry to change.
	 */
	public void setValueAt(Object value, String name);

	/**
	 * Evaluating an expression and setting its value as an entry.
	 * 
	 * @param expr
	 *            textual expression representation;
	 * @param name
	 *            specifies the entry to change.
	 */
	public void setExprValueAt(String expr, String name);

	/**
	 * Setting an expression as an entry without immediate evaluation. Syntax
	 * and type checking are performed.
	 * 
	 * @param expr
	 *            textual expression representation;
	 * @param name
	 *            specifies the entry to change;
	 */
	public void setExprAt(String expr, String name);

	/**
	 * Copying the contents of an attribute instance into another; The reference
	 * to the attribute type is shared.
	 */
	public void copy(AttrInstance source);

	/** Copying the contents of an attribute instance into another. */
	public void copyEntries(AttrInstance source);

	/**
	 * Getting the number of variables declared by this instance which have no
	 * value assigned to them yet. Each variable name is counted only once, even
	 * if it is used more than once in this tuple.
	 * 
	 * @return The number of free variables.
	 */
	public int getNumberOfFreeVariables(AttrContext context);

	/**
	 * Applying a rule; the substitutions occur "in-place" (in the recipient);
	 * In Graph Transformation, this method is applied to attributes of host
	 * graph objects, "rightSide" being an attribute of the right side of the
	 * rule and "context" being the "match"-context built up by subsequently
	 * matching the attributes of corresponding graphical objects.
	 */
	public void apply(AttrInstance rightSide, AttrContext context);

	/*
	 * Applying a rule; the substitutions occur "in-place" (in the recipient);
	 * In Graph Transformation, this method is applied to attributes of host
	 * graph objects, "rightSide" being an attribute of the right side of the
	 * rule and "context" being the "match"-context built up by subsequently
	 * matching the attributes of corresponding graphical objects. Needs the
	 * graphobject from the graph G.
	 */
//	public void apply(AttrInstance rightSide, AttrContext context,
//			AttrInstance g);

	/*
	 * This method works like public void apply( AttrInstance rightSide,
	 * AttrContext context, AttrInstance g ) but also allows using variables
	 * without value in value of attribute member as expression
	 */
//	public void apply(AttrInstance rightSide, AttrContext context,
//			AttrInstance g, boolean allowVariableWithoutValue);

	/**
	 * This method works like public void apply( AttrInstance rightSide,
	 * AttrContext context ) but also allows using variables without value in
	 * value of attribute member as expression
	 */
	public void apply(AttrInstance rightSide, AttrContext context,
			boolean allowVariableWithoutValue);

	public boolean compareTo(AttrInstance another);

	/**
	 * Unset the value of the own attribute members (not of attribute members of
	 * its parents). The value of its attribute member is null after this.
	 */
	public void unsetValue();

	// /** Glueing - for later realization. */
	// public AttrInstance glue( AttrInstance withInstance );
}

/*
 * $Log: AttrInstance.java,v $
 * Revision 1.4  2010/08/05 14:12:04  olga
 * tuning
 *
 * Revision 1.3  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2007/06/13 08:33:08 olga Update:
 * V161
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.6 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.5 2003/03/05 18:24:07 komm sorted/optimized import statements
 * 
 * Revision 1.4 2003/02/03 17:46:17 olga new method : compareTo(AttrInstance a)
 * 
 * Revision 1.3 2003/01/15 11:32:52 olga Zusaetzliche apply Methoden mit mehr
 * Parametern
 * 
 * Revision 1.2 2002/09/23 12:23:46 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.7 2000/12/07 14:23:33 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.6 2000/04/05 12:06:48 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
