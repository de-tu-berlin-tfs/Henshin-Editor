//################################################################################

package agg.xt_basis;

/**
 * This Exception is thrown by methods of the class
 * <code>OrdinaryMorphism</code>, especially by <code>addMapping()</code>,
 * to indicate a violation of some morphism property. A more detailed
 * description of the violation cause will be given in the exception's detail
 * message.
 * 
 * @see agg.xt_basis.OrdinaryMorphism
 * @see agg.xt_basis.OrdinaryMorphism#addMapping
 */
public class BadMappingException extends RuntimeException {
	/** Construct myself as an exception without any detail message. */
	public BadMappingException() {
	}

	/** Construct myself with the specified detail message. */
	public BadMappingException(String n) {
		super(n);
	}

}// ##############################################################################

// $Id: BadMappingException.java,v 1.2 2007/09/10 13:05:34 olga Exp $

// $Log: BadMappingException.java,v $
// Revision 1.2  2007/09/10 13:05:34  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.1 2005/08/25 11:56:54 enrico
// *** empty log message ***
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.1.1.1 2002/07/11 12:17:27 olga
// Imported sources
//
// Revision 1.9 2000/05/18 12:27:15 shultzke
// deprecated und test-Klassen geloescht
//
// Revision 1.8 1999/09/10 18:27:56 stefan
// -------------------------------------------
// I just have been hunting some syntax-bugs.
// -------------------------------------------
//
// Revision 1.7 1999/09/07 09:58:02 stefan
// -------------------------------------------
// For the sake of re-usability, the classes
// of the deprecated package "xt_basis" have
// been provided with static-final-long serial
// version attributes, where necessary. The
// values of these serial version attributes
// stem from the current AGG version to be
// found in the archive agg-active.jar which
// had been established before by Thorsten S.
// -------------------------------------------
// Revision 1.6 1999/09/06 11:55:55 stefan
// ---------------------------------------------
// Package xt_basis deprecated with all classes.
// New Package "agt" is in preparation. Reason:
// see Gabi's lecture at the AGTIVE-99 workshop.
// ---------------------------------------------
// Revision 1.5 1999/06/28 16:00:02 shultzke
// Revision 1.4 1998/05/04 16:38:04 mich
// ----------------------------------------------------
// + commentary adapted to the change in morphism class
// hierarchy (OrdinaryMorphism).
// ----------------------------------------------------
