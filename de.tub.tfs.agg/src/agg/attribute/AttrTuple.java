package agg.attribute;

import java.io.Serializable;

import agg.attribute.impl.DeclTuple;
import agg.attribute.view.AttrViewSetting;

/* 
 * This is an abstract interface and is extended by AttrType and AttrInstance.
 * Its purpose is to provide a common observable pattern to attribute objects
 * as well as a common mechanism for querying their state and representation.
 * A tuple consists of one or several entries. Each entry has typically three components:
 * a type, a name and a value. It's up to the implementation to distribute these components
 * between AttrType and AttrInstance.
 * @see agg.attribute.AttrType
 * @see agg.attribute.AttrInstance
 * 
 * A client can register and de-register as an observer of an attribute 
 * tuple. In order to do so, he must implement the AttrObserver interface.
 * It involves the implementation of an updating method and a test if
 * the client claims persistency on the client's side.
 * @see agg.attribute.AttrObserver
 
 * Querying can refer to the absolute (core) state or, provided a supplied instance of
 * AttrViewSetting, address the tuple's representation relative to a customized view.
 * @see agg.attribute.AttrViewSetting
 *@version $Id: AttrTuple.java,v 1.3 2008/04/07 09:36:55 olga Exp $
 *@author $Author: olga $
 */
public interface AttrTuple extends Serializable {
	/**
	 * Getting the attribute manager.
	 */
	public AttrManager getAttrManager();

	/**
	 * Testing if the tuple is consistent and complete.
	 */
	public boolean isValid();

	public DeclTuple getTupleType();
	/**
	 * Getting a tuple member by its absolute (view-independent) index.
	 */
	public AttrMember getMemberAt(int index);

	/**
	 * Getting a tuple member by its view-dependent index.
	 */
	public AttrMember getMemberAt(AttrViewSetting view, int index);

	/**
	 * Getting a tuple member by its declaration name.
	 */
	public AttrMember getMemberAt(String name);

	/**
	 * Translation between address- and number-oriented access.
	 * 
	 * @return The corresponding index if the member is within the tuple, -1
	 *         otherwise.
	 */
	public int getIndexForMember(AttrMember m);

	/**
	 * Getting the absolute (view-independent) total number of entries (lines);
	 * The retrieval index range is [0 .. (getNumberOfEntries() - 1)].
	 */
	public int getNumberOfEntries();

	/**
	 * Getting a view-independent representation of a type as String.
	 * 
	 * @param entryIndex
	 *            Index of entry.
	 */
	public String getTypeAsString(int entryIndex);

	/**
	 * Getting a view-independent representation of a name as String.
	 * 
	 * @param entryIndex
	 *            Index of entry.
	 */
	public String getNameAsString(int entryIndex);

	/**
	 * Getting a view-independent representation of a value as String.
	 * 
	 * @param entryIndex
	 *            Index of entry.
	 */
	public String getValueAsString(int entryIndex);

	/**
	 * Getting the view-dependent number of attribute entries (lines). The
	 * retrieval index range is [0 .. (getNumberOfEntries() - 1)].
	 * 
	 * @param viewSetting
	 *            The view context which mandates how attribute tuples have to
	 *            be represented.
	 */
	public int getNumberOfEntries(AttrViewSetting viewSetting);

	/**
	 * Getting a view-dependent representation of a type as String.
	 * 
	 * @param viewSetting
	 *            The view context which mandates how attribute tuples have to
	 *            be represented.
	 * @param entryIndex
	 *            Index of entry.
	 */
	public String getTypeAsString(AttrViewSetting viewSetting, int entryIndex);

	/**
	 * Getting a view-dependent representation of a type as String.
	 * 
	 * @param viewSetting
	 *            The view context which mandates how attribute tuples have to
	 *            be represented.
	 * @param entryIndex
	 *            Index of entry.
	 */
	public String getNameAsString(AttrViewSetting viewSetting, int entryIndex);

	/**
	 * Getting a view-dependent representation of a type as String.
	 * 
	 * @param viewSetting
	 *            The view context which mandates how attribute tuples have to
	 *            be represented.
	 * @param entryIndex
	 *            Index of entry.
	 */
	public String getValueAsString(AttrViewSetting viewSetting, int entryIndex);

	//
	// Observable Interface:
	//

	/**
	 * Adding a new attribute observer.
	 * 
	 * @param attrObs
	 *            The attribute observer to be registered.
	 */
	public void addObserver(AttrObserver attrObs);

	/**
	 * Removing an attribute observer from the list of observers.
	 * 
	 * @param attrObs
	 *            The attribute observer to be registered.
	 */
	public void removeObserver(AttrObserver attrObs);
}
/*
 * $Log: AttrTuple.java,v $
 * Revision 1.3  2008/04/07 09:36:55  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.2  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty
 * log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.3 2003/03/05 18:24:07 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:47 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:07:00 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
