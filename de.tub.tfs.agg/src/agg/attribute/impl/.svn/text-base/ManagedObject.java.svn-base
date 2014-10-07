package agg.attribute.impl;

import agg.attribute.AttrManager;

/**
 * This intermediate class was designed without a special purpose. It was
 * thought, at one point all the attributes might want to access a central
 * institution. Till now no need has occured.
 * 
 * @author $Author: olga $
 * @version $Id: ManagedObject.java,v 1.3 2010/09/23 08:14:09 olga Exp $
 */
public class ManagedObject extends AttrObject {

	/** A reference to the manager in case something is needed. */
	protected AttrTupleManager manager;

	/** A common constructor, so everybody knows where he belongs. */
	public ManagedObject(AttrTupleManager m) {
		super();
		this.manager = m;
		// ValueTuple brauchen keinen TupleManager
		// allerdings laeuft noch was bei den condTuple falsch
		// System.out.println(this);
		// if(this instanceof ValueTuple){
		// this.manager = null;
		// }
	}

	/** Retrieving the manager of others might also be necessary one day. */
	public AttrTupleManager getManager() {
		return this.manager;
	}

	/** For TupleObject's implementation of the AttrTuple interface. */
	public AttrManager getAttrManager() {
		return this.manager;
	}
}
/*
 * $Log: ManagedObject.java,v $
 * Revision 1.3  2010/09/23 08:14:09  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico ***
 * empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/10/04 16:36:38 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:09:17 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
