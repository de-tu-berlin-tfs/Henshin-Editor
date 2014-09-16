package agg.attribute;

/* 
 * Represents a mapping between two attribute instances. Also
 * keeps record of variables that were assigned values because
 * of the mapping.
 
 *@version $Id: AttrMapping.java,v 1.5 2010/03/31 21:06:47 olga Exp $
 *@author $Author: olga $
 */

public interface AttrMapping extends java.io.Serializable {
	static final long serialVersionUID = 4086026124433522160L;

	// Constants for mapping modes.

	/**
	 * Constant for the "plain" mapping mode. In Graph Transformation this
	 * stands for a mapping as in the rule morphisms.
	 */
	static public final int PLAIN_MAP = 0;

	/**
	 * Constant for the "match" mapping mode. In Graph Transformation this
	 * stands for a mapping as in match constructions.
	 */
	static public final int MATCH_MAP = 1;

	static public final int GRAPH_MAP = 2;

	static public final int OBJECT_FLOW_MAP = 3;
	
	/**
	 * Use the next possible mapping;
	 * 
	 * @return "true" if more subsequent mappings exist, "false" otherwise.
	 */
	public boolean next();

	/**
	 * Discard mapping; Removes variable assignments made by this mapping from
	 * its context and dissolves the connection between the attribute instances.
	 */
	public void remove();
	
	public void removeNow();
}

/*
 * $Log: AttrMapping.java,v $
 * Revision 1.5  2010/03/31 21:06:47  olga
 * tuning
 *
 * Revision 1.4  2009/11/23 08:52:02  olga
 * new map kind: OBJECT_FLOW_MAP
 *
 * Revision 1.3  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/01/16 09:34:20 olga Extended
 * attribute setting
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:23:46 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.4 2000/04/05 12:06:53 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
