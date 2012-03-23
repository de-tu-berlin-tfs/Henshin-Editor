package agg.attribute;

import java.io.Serializable;

/**
 * Mediator interface for distribution purposes. Provides services for creating
 * and maintaining of interface/local-relations between attribute tuples and
 * contexts.
 * 
 * @version $Id: AttrDistributionBroker.java,v 1.1 2005/08/25 11:56:55 enrico
 *          Exp $
 * @author $Author: olga $
 */
public interface AttrDistributionBroker extends Serializable {
	static final long serialVersionUID = 8823872469661905068L;

	/** Makes a type tuple into an interface of another type tuple. */
	public void connect(AttrType interfaceType, AttrType localType);

	/** Ends a type tuple's role as an interface of another type tuple. */
	public void disconnect(AttrType interfaceType, AttrType localType);

	/** Makes an instance tuple into an interface of another interface tuple. */
	public void connect(AttrInstance interfaceInstance,
			AttrInstance localInstance);

	/** Ends an instance tuple's role as an interface of another interface tuple. */
	public void disconnect(AttrInstance interfaceInstance,
			AttrInstance localInstance);

	/** Makes a context into an interface of another context. */
	public void connect(AttrContext interfaceContext, AttrContext localContext);

	/** Ends a context's role as an interface of another context. */
	public void disconnect(AttrContext interfaceContext,
			AttrContext localContext);
}

/*
 * $Log: AttrDistributionBroker.java,v $
 * Revision 1.2  2007/09/10 13:05:31  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:55 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:23:45 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:55 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:06:43 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
