package agg.attribute.impl;

/**
 * @version $Id: NoSuchVariableException.java,v 1.1 2005/08/25 11:56:57 enrico
 *          Exp $
 * @author $Author: olga $
 */
public class NoSuchVariableException extends RuntimeException {
	static final long serialVersionUID = 2348562827848599911L;

	public NoSuchVariableException(String name) {
		super(name);
	}
}
/*
 * $Log: NoSuchVariableException.java,v $
 * Revision 1.2  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57
 * enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:21 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:09:20 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
