package agg.attribute.impl;

import java.util.Vector;

import agg.attribute.AttrMapping;

/**
 * Representation of a mapping between two attribute instances.
 * 
 * @author $Author: olga $
 * @version $Id: TupleMapping.java,v 1.8 2010/09/23 08:14:08 olga Exp $
 */
public class TupleMapping extends AttrObject implements AttrMapping,
		AttrMsgCode {

	static final long serialVersionUID = 5592548875776404533L;

	/** The source and target objects of this mapping. */
	protected ValueTuple source, target;

	/** The context this mapping is contained in. */
	protected ContextView context;

	/**
	 * References to value settings done due to this mapping. (Vector of Object)
	 */
	protected String assignedVariables[];

	/**
	 * All or nothing: tries to create a mapping right away, with matching if
	 * it's in a match context. If it fails, an 'AttrImplException' is thrown.
	 */
	public TupleMapping(ContextView mappingContext, ValueTuple src,
			ValueTuple tar) {
//		logPrintln(VerboseControl.logTrace, "TupleMapping:\n->new TupleMapping");
		boolean child2parentMapping = false;
		
		if (mappingContext.getAllowedMapping() == AttrMapping.PLAIN_MAP) {
//			logPrintln(VerboseControl.logMapping,
//					"creating TupleMapping for a plain mapping");
			if (!src.getType().compareTo(tar.getType())
					&& !((DeclTuple)src.getType()).weakcompareTo(tar.getType())
					) {
				throw new AttrImplException(ATTR_DONT_MATCH,
						"Types must be equal.");
			}
		} else if (mappingContext.getAllowedMapping() == AttrMapping.MATCH_MAP) {
//			logPrintln(VerboseControl.logMapping,
//					"creating TupleMapping for a match mapping");
			if (!src.getType().compareTo(tar.getType())
					&& !((DeclTuple)src.getType()).weakcompareTo(tar.getType())
					&& !tar.getTupleType().isSubclassOf(src.getTupleType())) {
				if (src.getTupleType().isSubclassOf(tar.getTupleType())) {
					child2parentMapping = true;
				} else {
				System.out.println("Target type must be subtype of source or equal.");
				throw new AttrImplException(
						"Target type must be subtype of source or equal.");
				}
			}
		}

		String variables[] = null;
		this.context = mappingContext;

		// logPrintln( VerboseControl.logContextOfInstances,
		// "mappingContext = " +
		// getAllowedMappingAsString( mappingContext ) +
		// " " + mappingContext );
		// logPrintln( VerboseControl.logContextOfInstances,
		// "src.context = " +
		// getAllowedMappingAsString( src.getContextView() ) +
		// " " + src.getContextView() );
		// logPrintln( VerboseControl.logContextOfInstances,
		// "tar.context = " +
		// getAllowedMappingAsString( tar.getContextView() ) +
		// " " + tar.getContextView() );

		if (this.context == null)
			this.context = src.getContextView();

		try {
			if (this.context.getAllowedMapping() == AttrMapping.MATCH_MAP) {
				if (child2parentMapping) {
					variables = src.matchChild2Parent(tar, this.context);
				} else {
					variables = src.matchTo(tar, this.context);
				}
			} else { // context.getAllowedMapping() == AttrMapping.PLAIN_MAP
				// anscheinend kopiert diese Stelle den Inhalt der linken
				// Regelseite in die rechte
				// Es werden auf der rechten Seite genau die Member aufgefuellt,
				// die leer sind.
				// Dieses Verhalten zeigt sich, wenn in beiden Regelseiten ein
				// Graphobjekt
				// existiert und nachtraeglich ein Morphismus definiert wird.
				// System.out.println("TuppleMapping: Constructor -
				// AllowedMapping() == AttrMapping.PLAIN_MAP");
				// test without adoptEntriesWhereEmpty
				// tar.adoptEntriesWhereEmpty( src );
			}
		} catch (AttrImplException ex1) {
			// System.out.println("TuppleMapping: Constructor - catch
			// exception\n"+ex1.getMessage());
			// System.out.println("src: "+src);
			// System.out.println("tar: "+tar);
			// throw ex1;
			throw new AttrImplException("TupleMapping: attribute exception\n"
					+ ex1.getMessage());
		}
		this.source = src;
		this.target = tar;
		this.assignedVariables = variables;
		this.context.addMapping(this);
//		logPrintln(VerboseControl.logTrace,
//				"TuppleMapping:\n<-new TuppleMapping");
	}

	/** This method accepts AttrMapping.PLAIN_MAP only. */
	public void adoptEntriesWhereEmpty(ValueTuple src, ValueTuple tar) {
		try {
			if (this.context.getAllowedMapping() == AttrMapping.PLAIN_MAP) {
				tar.adoptEntriesWhereEmpty(src);
				// System.out.println("TupleMapping.adoptEntriesWhereEmpty ...
				// done");
			}
		} catch (AttrImplException ex1) {
			// System.out.println("TuppleMapping: Constructor - catch
			// exception\n"+ex1.getMessage());
			// System.out.println("src: "+src);
			// System.out.println("tar: "+tar);
			// throw ex1;
			throw new AttrImplException("TupleMapping: attribute exception\n"
					+ ex1.getMessage());
		}
	}

	public Vector<String> getAssignedVariables() {
		int nn = (this.assignedVariables == null) ? -1 : this.assignedVariables.length;
		Vector<String> v = new Vector<String>(1);
		for (int i = 0; i < nn; i++) {
			v.add(this.assignedVariables[i]);
		}
		// System.out.println("TupleMapping.getAssignedVariables: "+v);
		return v;
	}

	/**
	 * Use the next possible mapping;
	 * 
	 * @return "true" if more subsequent mappings exist, "false" otherwise.
	 */
	public boolean next() {
		return false;
	}

	/**
	 * Implementation of agg.attribute.AttrMapping#remove(). Called by a client.
	 * Discards mapping; Actually only telling the context that this mapping
	 * shall be removed; The context chooses when exactly this occurs and then
	 * calls #removeNow .
	 */
	public void remove() {
		this.context.removeMapping(this);
	}

	/**
	 * Called from this mapping's context; Discard mapping; Removes assignments
	 * made by it from its context.
	 */
	public void removeNow() {
		if (this.assignedVariables != null) {
			for (int i = 0; i < this.assignedVariables.length; i++) {
				this.context.removeValue(this.assignedVariables[i]);
			}
		}
	}

	/** Getting the source attribute instance. */
	public ValueTuple getSource() {
		return this.source;
	}

	/** Getting the target attribute instance. */
	public ValueTuple getTarget() {
		return this.target;
	}

	/** For debugging output. */
	protected String getAllowedMappingAsString(ContextView contextview) {
		if (contextview == null)
			return "";
		return contextview.getAllowedMapping() == AttrMapping.MATCH_MAP ? " (match)"
				: " (plain)";
	}
}

/*
 * $Log: TupleMapping.java,v $
 * Revision 1.8  2010/09/23 08:14:08  olga
 * tuning
 *
 * Revision 1.7  2007/12/10 08:42:58  olga
 * CPA of grammar with node type inheritance for attributed graphs - bug fixed
 *
 * Revision 1.6  2007/09/24 09:42:33  olga
 * AGG transformation engine tuning
 *
 * Revision 1.5  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.4 2006/12/13 13:32:58 enrico
 * reimplemented code
 * 
 * Revision 1.3 2006/11/01 11:17:29 olga Optimized agg sources of CSP algorithm,
 * match usability, graph isomorphic copy, node/edge type multiplicity check for
 * injective rule and match
 * 
 * Revision 1.2 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.7 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.6 2005/01/03 13:14:43 olga Errors handling
 * 
 * Revision 1.5 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.4 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/12/18 09:15:25 olga Testausgaben raus
 * 
 * Revision 1.2 2002/11/11 09:41:16 olga Nur Testausgaben
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.14 2001/03/08 10:38:40 olga Testausgaben raus.
 * 
 * Revision 1.13 2001/02/15 16:00:03 olga Einige Aenderungen wegen XML
 * 
 * Revision 1.12 2000/12/21 09:48:48 olga In dieser Version wurden XML und GUI
 * Reimplementierung zusammen gefuehrt.
 * 
 * Revision 1.11.6.2 2000/12/04 13:25:52 olga Erste Stufe der GUI
 * Reimplementierung abgeschlossen: - AGGAppl.java optimiert - Print eingebaut
 * (GraGraPrint.java) - GraGraTreeView.java, GraGraEditor.java optimiert - Event
 * eingebaut - GraTra umgestellt
 * 
 * Revision 1.11.6.1 2000/11/09 13:28:28 olga Umstellung von Graphtransformation
 * in TransformInterpret, TransformDebug auf die Methoden aus
 * agg.xt_basis.GraTra. TransformInterpret noch fehlerhaft.
 * 
 * Revision 1.11 2000/06/05 14:07:48 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.10 2000/05/24 10:02:30 olga Nur Testausgaben eingebaut bei der
 * Suche nach dem Fehler in DISAGG : Match Konstante auf Konstante
 * 
 * Revision 1.9 2000/04/05 12:09:23 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
