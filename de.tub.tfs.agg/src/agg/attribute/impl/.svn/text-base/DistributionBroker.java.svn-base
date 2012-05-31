package agg.attribute.impl;

import java.io.Serializable;

import agg.attribute.AttrContext;
import agg.attribute.AttrDistributionBroker;
import agg.attribute.AttrInstance;
import agg.attribute.AttrType;

/**
 * Mediator class for distribution purposes. Provides services for creating and
 * maintaining of interface/local-relations between attribute tuples and
 * contexts.
 */
public class DistributionBroker extends AttrObject implements
		AttrDistributionBroker, Serializable {

	static final long serialVersionUID = 7357614594619925137L;

	public DistributionBroker() {
		super();
	}

	/** Makes a type tuple into an interface of another type tuple. */
	public void connect(AttrType interfaceType, AttrType localType) {
	}

	/** Ends a type tuple's role as an interface of another type tuple. */
	public void disconnect(AttrType interfaceType, AttrType localType) {
	}

	/** Makes an instance tuple into an interface of another interface tuple. */
	public void connect(AttrInstance interfaceInstance,
			AttrInstance localInstance) {
	}

	/** Ends an instance tuple's role as an interface of another interface tuple. */
	public void disconnect(AttrInstance interfaceInstance,
			AttrInstance localInstance) {
	}

	/** Makes a context into an interface of another context. */
	public void connect(AttrContext interfaceContext, AttrContext localContext) {
	}

	/** Ends a context's role as an interface of another context. */
	public void disconnect(AttrContext interfaceContext,
			AttrContext localContext) {
	}
}

/*
 * $Log: DistributionBroker.java,v $
 * Revision 1.2  2007/09/10 13:05:19  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.3 2000/04/05 12:09:14 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
