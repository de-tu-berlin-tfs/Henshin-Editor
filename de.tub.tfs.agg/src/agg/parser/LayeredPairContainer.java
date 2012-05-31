package agg.parser;

/**
 * This class combines two aspects. The first is the well know pair container
 * algorithm. The second is the algorithm for layer.
 * 
 * @author $Author: olga $
 * @version $Id: LayeredPairContainer.java,v 1.3 2007/09/10 13:05:41 olga Exp $
 */
public interface LayeredPairContainer extends PairContainer// , Layer
{
}
/*
 * $Log: LayeredPairContainer.java,v $
 * Revision 1.3  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/12/13 13:33:00 enrico
 * reimplemented code
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:44:54 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.2 2001/01/28 13:14:55 shultzke API fertig
 * 
 * Revision 1.1.2.1 2000/12/10 14:55:48 shultzke um Layer erweitert
 * 
 */
