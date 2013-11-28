package agg.attribute.gui;

import agg.attribute.AttrTuple;
import agg.attribute.view.AttrViewSetting;

/**
 * @version $Id: AttrTupleEditor.java,v 1.2 2007/09/10 13:05:50 olga Exp $
 * @author $Author: olga $
 */
public interface AttrTupleEditor extends AttrEditor {

	public void setTuple(AttrTuple anAttrTuple);

	public AttrTuple getTuple();

	public void setViewSetting(AttrViewSetting anAttrViewSetting);

	public AttrViewSetting getViewSetting();

}
/*
 * $Log: AttrTupleEditor.java,v $
 * Revision 1.2  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:57:00 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:56 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:07:38 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
