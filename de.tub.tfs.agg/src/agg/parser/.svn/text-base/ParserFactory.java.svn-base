package agg.parser;

import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.RuleLayer;

// ---------------------------------------------------------------------------+
/**
 * This factory produces different objects. With the help of some option objects
 * the specific object is creates. E.g. if a parser is desired the option
 * distinguish which parser is created.
 * 
 * @author $Author: olga $ Parser Group
 * @version $Id: ParserFactory.java,v 1.20 2010/11/16 23:33:08 olga Exp $
 */
public class ParserFactory {

	private ParserFactory() {
	}

	/**
	 * Here is a new parser created. The correct parser is chosen by the option.
	 * So the option must not be <code>null</code>. Anyway only the critical
	 * pairs can be <code>null</code> if they are not needed for the
	 * configured parser. This can happen e.g. for parser without optimization.
	 * <b>If one of the parameters is <code>null</code> <code>null</code> is
	 * returned.</b>
	 * 
	 * @param grammar
	 *            The graph grammar with all the rules.
	 * @param hostGraph
	 *            The host graph to work on.
	 * @param stopGraph
	 *            The stop graph stops the parser.
	 * @param pairs
	 *            The critical pairs for optimization.
	 * @see agg.parser.ParserFactory#generateCriticalPairs
	 * @param option
	 *            The option to configure the parser.
	 * @param layer
	 *            The layer function.
	 * @return The new specific parser.
	 * @deprecated
	 */
	public static Parser createParser(GraGra grammar, Graph hostGraph,
			Graph stopGraph, PairContainer pairs, ParserOption option,
			LayerFunction layer) {
		Parser p = null;
		if (option != null && grammar != null && hostGraph != null
				&& stopGraph != null) {
			switch (option.getSelectedParser()) {
			case ParserOption.SIMPLEPARSER:
				if (option.layerEnabled()) {
					p = new LayeredSimpleParser(grammar, hostGraph, stopGraph,
							layer);
				} else {
					p = new SimpleParser(grammar, hostGraph, stopGraph);
				}
				break;
			case ParserOption.EXCLUDEPARSER:
				if (pairs != null && pairs instanceof ExcludePairContainer) {
					if (option.layerEnabled()) {
						p = new LayeredExcludeParser(grammar, hostGraph,
								stopGraph, (LayeredExcludePairContainer) pairs,
								layer);
					} else {
						p = new ExcludeParser(grammar, hostGraph, stopGraph,
								(ExcludePairContainer) pairs);
					}
				}
				break;
			case ParserOption.SIMPLEEXCLUDEPARSER:
				if (pairs != null && pairs instanceof ExcludePairContainer) {
					if (option.layerEnabled()) {
						p = new LayeredSimpleExcludeParser(grammar, hostGraph,
								stopGraph, (LayeredExcludePairContainer) pairs,
								layer);
					} else {
						p = new SimpleExcludeParser(grammar, hostGraph,
								stopGraph, (ExcludePairContainer) pairs);
					}
				}
				break;
			default:
				break;
			}
		}
		return p;
	}

	/**
	 * Here is a new parser created. The correct parser is chosen by the option.
	 * So the option must not be <code>null</code>. Anyway only the critical
	 * pairs can be <code>null</code> if they are not needed for the
	 * configured parser. This can happen e.g. for parser without optimization.
	 * <b>If one of the parameters is <code>null</code> <code>null</code> is
	 * returned.</b>
	 * 
	 * @param grammar
	 *            The graph grammar with all the rules.
	 * @param hostGraph
	 *            The host graph to work on.
	 * @param stopGraph
	 *            The stop graph stops the parser.
	 * @param pairs
	 *            The critical pairs for optimization.
	 * @see agg.parser.ParserFactory#generateCriticalPairs
	 * @param option
	 *            The option to configure the parser.
	 * @param layer
	 *            The layer function.
	 * @return The new specific parser.
	 */
	public static Parser createParser(GraGra grammar, Graph hostGraph,
			Graph stopGraph, PairContainer pairs, ParserOption option,
			RuleLayer layer) {
		if (option == null || grammar == null || hostGraph == null
				|| stopGraph == null)
			return null;

		Parser p = null;
		switch (option.getSelectedParser()) {
		case ParserOption.SIMPLEPARSER:
			if (option.layerEnabled()) {
				p = new LayeredSimpleParser(grammar, hostGraph, stopGraph,
						layer);
			} else {
				p = new SimpleParser(grammar, hostGraph, stopGraph);
			}
			break;
		case ParserOption.EXCLUDEPARSER:
			if (pairs != null && pairs instanceof ExcludePairContainer) {
				if (option.layerEnabled()) {
					p = new LayeredExcludeParser(grammar, hostGraph, stopGraph,
							(LayeredExcludePairContainer) pairs, layer);
				} else {
					p = new ExcludeParser(grammar, hostGraph, stopGraph,
							(ExcludePairContainer) pairs);
				}
			}
			break;
		case ParserOption.SIMPLEEXCLUDEPARSER:
			if (pairs != null && pairs instanceof ExcludePairContainer) {
				if (option.layerEnabled()) {
					p = new LayeredSimpleExcludeParser(grammar, hostGraph,
							stopGraph, (LayeredExcludePairContainer) pairs,
							layer);
				} else {
					p = new SimpleExcludeParser(grammar, hostGraph, stopGraph,
							(ExcludePairContainer) pairs);
				}
			}
			break;
		default:
			break;
		}
		return p;
	}

	/**
	 * Creates a empty container for critical pairs. This container has to be
	 * filled.
	 * 
	 * @param grammar
	 *            The graph grammar to generate the pairs for. This must not be
	 *            <code>null</code> or <code>null</code> is returned.
	 * @param layerFunc
	 *            The layer function. This can only be <code>null</code> if
	 *            the critical pairs do not need them. (<code>null</code> can
	 *            be returned.)
	 * @param option
	 *            The option to configure the critical pairs. This must not be
	 *            <code>null</code> or <code>null</code> is returned.
	 * @return A empty container.
	 * @deprecated
	 */
	public static PairContainer createEmptyCriticalPairs(GraGra grammar,
			LayerFunction layerFunc, CriticalPairOption option) {
		PairContainer pc = null;
		if (option != null && grammar != null) {
			switch (option.getCriticalPairAlgorithm()) {
			case CriticalPairOption.EXCLUDEONLY:
				if (option.layeredEnabled()) {
					pc = new LayeredExcludePairContainer(grammar);
					((LayeredExcludePairContainer) pc).setLayer(
							option.getLayer());
				} else
					pc = new ExcludePairContainer(grammar);
				((ExcludePairContainer) pc).enableComplete(
						option.completeEnabled());
				((ExcludePairContainer) pc).enableNACs(
						option.nacsEnabled());
				((ExcludePairContainer) pc).enablePACs(
						option.pacsEnabled());
				((ExcludePairContainer) pc).enableReduce(
						option.reduceEnabled());
				((ExcludePairContainer) pc).enableConsistent(
						option.consistentEnabled());
				((ExcludePairContainer) pc)
						.enableStrongAttrCheck(option.strongAttrCheckEnabled());
				((ExcludePairContainer) pc)
						.enableEqualVariableNameOfAttrMapping(
								option.equalVariableNameOfAttrMappingEnabled());
				((ExcludePairContainer) pc).enableIgnoreIdenticalRules(
						option.ignoreIdenticalRulesEnabled());
				((ExcludePairContainer) pc).enableReduceSameMatch(
						option.reduceSameMatchEnabled());
				((ExcludePairContainer) pc).enableDirectlyStrictConfluent(
						option.directlyStrictConflEnabled());
				((ExcludePairContainer) pc).enableDirectlyStrictConfluentUpToIso(
						option.directlyStrictConflUpToIsoEnabled());
				((ExcludePairContainer) pc).enableNamedObjectOnly(
						option.namedObjectEnabled());
				break;
			case CriticalPairOption.TRIGGER_DEPEND:
			case CriticalPairOption.TRIGGER_SWITCH_DEPEND:	
				// System.out.println("DependencyPairContainer will be used");
				if (option.layeredEnabled()) {
					pc = new LayeredDependencyPairContainer(grammar);
					((LayeredExcludePairContainer) pc).setLayer(
							option.getLayer());
				} else
					pc = new DependencyPairContainer(grammar);
				
				((DependencyPairContainer) pc).enableSwitchDependency(
						option.switchDependencyEnabled());
				((DependencyPairContainer) pc).enableComplete(
						option.completeEnabled());
				((DependencyPairContainer) pc).enableNACs(
						option.nacsEnabled());
				((DependencyPairContainer) pc).enableReduce(
						option.reduceEnabled());
				((DependencyPairContainer) pc).enableConsistent(
						option.consistentEnabled());
				((DependencyPairContainer) pc).enableIgnoreIdenticalRules(
						option.ignoreIdenticalRulesEnabled());
				((DependencyPairContainer) pc).enableReduceSameMatch(
						option.reduceSameMatchEnabled());
				((DependencyPairContainer) pc).enableDirectlyStrictConfluent(
						option.directlyStrictConflEnabled());
				((DependencyPairContainer) pc).enableNamedObjectOnly(
						option.namedObjectEnabled());
				break;
			default:
				break;
			}
		}
		return pc;
	}

	/**
	 * Creates a empty container for critical pairs. This container has to be
	 * filled.
	 * 
	 * @param grammar
	 *            The graph grammar to generate the pairs for. This must not be
	 *            <code>null</code> or <code>null</code> is returned.
	 * @param option
	 *            The option to configure the critical pairs. This must not be
	 *            <code>null</code> or <code>null</code> is returned.
	 * @return A empty container.
	 */
	public static PairContainer createEmptyCriticalPairs(GraGra grammar,
			CriticalPairOption option) {
		PairContainer pc = null;
		if (option != null && grammar != null) {
			switch (option.getCriticalPairAlgorithm()) {
			case CriticalPairOption.EXCLUDEONLY:
				if (option.layeredEnabled()) {
					pc = new LayeredExcludePairContainer(grammar);
					((LayeredExcludePairContainer) pc).setLayer(
							option.getLayer());
				} else
					pc = new ExcludePairContainer(grammar);
				((ExcludePairContainer) pc).enableComplete(
						option.completeEnabled());
				((ExcludePairContainer) pc).enableNACs(
						option.nacsEnabled());
				((ExcludePairContainer) pc).enablePACs(
						option.pacsEnabled());
				((ExcludePairContainer) pc).enableReduce(
						option.reduceEnabled());
				((ExcludePairContainer) pc).enableConsistent(
						option.consistentEnabled());
				((ExcludePairContainer) pc)
						.enableStrongAttrCheck(option.strongAttrCheckEnabled());
				((ExcludePairContainer) pc)
						.enableEqualVariableNameOfAttrMapping(
								option.equalVariableNameOfAttrMappingEnabled());
				((ExcludePairContainer) pc).enableIgnoreIdenticalRules(
						option.ignoreIdenticalRulesEnabled());
				((ExcludePairContainer) pc).enableReduceSameMatch(
						option.reduceSameMatchEnabled());
				((ExcludePairContainer) pc).enableDirectlyStrictConfluent(
						option.directlyStrictConflEnabled());
				((ExcludePairContainer) pc).enableDirectlyStrictConfluentUpToIso(
						option.directlyStrictConflUpToIsoEnabled());
				((ExcludePairContainer) pc).enableNamedObjectOnly(
						option.namedObjectEnabled());
				
				break;
			case CriticalPairOption.TRIGGER_DEPEND:
			case CriticalPairOption.TRIGGER_SWITCH_DEPEND:
				// System.out.println("DependencyPairContainer will be used");

				if (option.layeredEnabled()) {
					pc = new LayeredDependencyPairContainer(grammar);
					((LayeredExcludePairContainer) pc).setLayer(
							option.getLayer());
				} else
					pc = new DependencyPairContainer(grammar);
				
				((DependencyPairContainer) pc).enableSwitchDependency(
						option.switchDependencyEnabled());
				((DependencyPairContainer) pc).enableComplete(
						option.completeEnabled());
				((DependencyPairContainer) pc).enableNACs(
						option.nacsEnabled());
				((DependencyPairContainer) pc).enableReduce(
						option.reduceEnabled());
				((DependencyPairContainer) pc).enableConsistent(						
						option.consistentEnabled());
				((DependencyPairContainer) pc).enableStrongAttrCheck(
						option.strongAttrCheckEnabled());
				((DependencyPairContainer) pc).enableIgnoreIdenticalRules(
						option.ignoreIdenticalRulesEnabled());
				((DependencyPairContainer) pc).enableReduceSameMatch(
						option.reduceSameMatchEnabled());
				((DependencyPairContainer) pc).enableDirectlyStrictConfluent(
						option.directlyStrictConflEnabled());
				((DependencyPairContainer) pc).enableNamedObjectOnly(
						option.namedObjectEnabled());
				break;
			default:
				break;
			}
		}
		return pc;
	}

	public static PairContainer createEmptyCriticalPairs(GraGra grammar,
			int algorithm, boolean layered) {
		PairContainer pc = null;
		if (grammar != null) {
			switch (algorithm) {
			case CriticalPairOption.EXCLUDEONLY:
				if (layered)
					pc = new LayeredExcludePairContainer(grammar);
				else if (grammar.trafoByPriority())
					pc = new PriorityExcludePairContainer(grammar);
				else
					pc = new ExcludePairContainer(grammar);
				break;
			case CriticalPairOption.TRIGGER_DEPEND:
			case CriticalPairOption.TRIGGER_SWITCH_DEPEND:
				if (layered)
					pc = new LayeredDependencyPairContainer(grammar);
				else if (grammar.trafoByPriority())
					pc = new PriorityDependencyPairContainer(grammar);
				else
					pc = new DependencyPairContainer(grammar);
				
				if (algorithm == CriticalPairOption.TRIGGER_SWITCH_DEPEND)
					((DependencyPairContainer) pc).enableSwitchDependency(
							true);
				break;
			default:
				break;
			}
		}
		return pc;
	}

	/**
	 * Generates critical pairs. These pairs are generated parallel. Use these
	 * generated pairs for the parser.
	 * 
	 * @param pc
	 *            The pair container to be filled.
	 */
	public static Thread generateCriticalPairs(PairContainer pc) {
		Thread t = null;
		if (pc != null && pc instanceof Runnable) {
			t = new Thread((Runnable) pc);
			t.setPriority(4);
			t.start();
		}
		return t;
	}

	/**
	 * Creates and generates critical pairs. These pairs are generated parallel.
	 * Use these generated pairs for the parser.
	 * 
	 * @param grammar
	 *            The graph grammar to generate the pairs for. This must not be
	 *            <CODE>null</CODE> or <CODE>null</CODE> is returned.
	 * @param layer
	 *            The layer function. This can only be <CODE>null</CODE> if
	 *            the critical pairs do not need them.
	 * @param option
	 *            The option to configure the critical pairs. This must not be
	 *            <CODE>null</CODE>.
	 * @return The genrated pairs in a container.
	 * @deprecated
	 */
	public static PairContainer generateCriticalPairs(GraGra grammar,
			LayerFunction layer, CriticalPairOption option) {
		PairContainer pc = createEmptyCriticalPairs(grammar, layer, option);
		generateCriticalPairs(pc);
		return pc;
	}

	/**
	 * Creates a new layer function with a invalid layer.
	 * 
	 * @param grammar
	 *            The graph grammar to generate the layer function for. This
	 *            must not be <code>null</code> or <code>null</code> is
	 *            returned.
	 * @param option
	 *            The option to configure the layer function. This must not be
	 *            <code>null</code> or <code>null</code> is returned.
	 * @return The new layer function.
	 * @deprecated
	 */
	public static LayerFunction createLayerFunction(GraGra grammar,
			LayerOption option) {
		// System.out.println("ParserFactory.createLayerFunction ");
		LayerFunction lf = null;
		if (option != null && grammar != null) {
			switch (option.getLayer()) {
			case LayerOption.RCD_LAYER:
				lf = new LayerFunction(grammar);
				// System.out.println("Rule, Creation, Deletion, Rule must
				// delete");
				break;
			case LayerOption.RCDN_LAYER:
				lf = new ExtendedLayerFunction(grammar);
				// System.out.println("Rule, Creation, Deletion, Rule must
				// delete, NAC check");
				break;
			case LayerOption.WEAK_RCD_LAYER:
				lf = new WeakLayerFunction(grammar);
				// System.out.println("Rule, Creation, Deletion");
				break;
			case LayerOption.WEAK_RCDN_LAYER:
				lf = new WeakExtendedLayerFunction(grammar);
				// System.out.println("Rule, Creation, Deletion, NAC check");
				break;
			default:
				break;
			}
		}
		return lf;
	}
}

/*
 * End of ParserFactory.java
 * -----------------------------------------------------------------------------
 * $Log: ParserFactory.java,v $
 * Revision 1.20  2010/11/16 23:33:08  olga
 * tuning
 *
 * Revision 1.19  2010/11/07 20:48:10  olga
 * tuning
 *
 * Revision 1.18  2010/11/04 11:01:32  olga
 * tuning
 *
 * Revision 1.17  2010/01/31 16:47:46  olga
 * tuning
 *
 * Revision 1.16  2009/06/30 09:50:20  olga
 * agg.xt_basis.GraphObject: added: setObjectName(String), getObjectName()
 * agg.xt_basis.Node, Arc: changed: save, load the object name
 * agg.editor.impl.EdGraphObject: changed: String getTypeString() - contains object name if set
 *
 * workaround of Applicability of Rule Sequences and Object Flow
 *
 * Revision 1.15  2009/03/12 10:57:45  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.14  2008/05/14 07:43:28  olga
 * Applicability of Rule Sequences - bugs fixed
 *
 * Revision 1.13  2008/05/07 08:37:55  olga
 * Applicability of Rule Sequences with NACs
 *
 * Revision 1.12  2008/04/07 09:36:51  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.11  2008/02/18 09:37:10  olga
 * - an extention of rule dependency check is implemented;
 * - some bugs fixed;
 * - editing of graphs improved
 *
 * Revision 1.10  2007/10/10 07:44:27  olga
 * CPA: bug fixed
 * GUI, AtomConstraint: bug fixed
 *
 * Revision 1.9  2007/09/10 13:05:41  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.8 2007/06/13 08:32:59 olga Update:
 * V161
 * 
 * Revision 1.7 2007/03/28 10:00:58 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.6 2006/12/13 13:33:00 enrico reimplemented code
 * 
 * Revision 1.5 2006/03/08 09:14:59 olga CPs mistake fixed
 * 
 * Revision 1.4 2006/03/01 09:55:46 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.3 2005/09/26 16:41:20 olga CPA graph, CPs - visualization
 * 
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.4 2004/09/13 10:21:14 olga Einige Erweiterungen und
 * Fehlerbeseitigung bei CPs und Graph Grammar Transformation
 * 
 * Revision 1.3 2004/06/21 08:35:34 olga immer noch CPs
 * 
 * Revision 1.2 2004/04/15 10:49:48 olga Kommentare
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.10 2001/08/16 14:14:08 olga LayerFunction erweitert:
 * ExtendedLayerFunction erbt LayerFunction (checkLayer ueberschrieben)
 * WeakLayerFunction erbt LayerFunction ( checkLayer ueberschrieben)
 * WeakExtendedLayerFunction erbt WeakLayerFunction ( checkLayer ueberschrieben)
 * 
 * Revision 1.9 2001/08/02 15:22:16 olga Error-Meldungen eingebaut in
 * LayerFunction und die Anzeige dieser Meldungen in GUI.
 * 
 * Revision 1.8 2001/07/04 14:06:10 bonefish Added a new
 * gengerateCriticalPairs() version that should be used to avoid race conditions
 * when using pair event listeners.
 * 
 * Revision 1.7 2001/06/18 13:37:47 olga Bei Critical Pair ein neuer Menuitem:
 * Debug, wo man einzelne Regelpaare testen kann. System.gc() eingefuegt.
 * 
 * Revision 1.6 2001/06/13 16:49:36 olga Parser Classen Optimierung.
 * 
 * Revision 1.5 2001/05/14 12:03:02 olga Zusaetzliche Parser Events Aufrufe
 * eingebaut, um bessere Kommunikation mit GUI Status Anzeige zu ermoeglichen.
 * 
 * Revision 1.4 2001/03/08 10:42:53 olga Die Parser Version aus parser branch
 * wurde in Head uebernommen.
 * 
 * Revision 1.2.2.8 2001/02/22 13:11:51 shultzke Docu Tag veraendert
 * 
 * Revision 1.2.2.7 2001/01/28 13:14:57 shultzke API fertig
 * 
 * Revision 1.2.2.6 2001/01/10 15:09:51 shultzke load and save fast fertig
 * 
 * Revision 1.2.2.5 2001/01/03 09:45:01 shultzke TODO's bis auf laden und
 * speichern erledigt. Wann meldet sich endlich Michael?
 * 
 * Revision 1.2.2.4 2000/12/26 10:00:06 shultzke Layered Parser hinzugefuegt
 * 
 * Revision 1.2.2.3 2000/12/21 13:46:03 shultzke optionen weiter veraendert
 * 
 * Revision 1.2.2.2 2000/12/10 14:55:48 shultzke um Layer erweitert
 * 
 * Revision 1.2.2.1 2000/07/12 07:58:43 shultzke merged
 * 
 * Revision 1.3 2000/07/10 15:09:41 shultzke additional representtion
 * hinzugefuegt
 * 
 * Revision 1.2 2000/07/09 17:12:59 shultzke grob die GUI eingebunden
 * 
 * Revision 1.1 2000/06/13 08:57:38 shultzke Initial version, very alpha
 * 
 * Revision 1.12 1999/09/29 10:19:35 elchkopf Mit generateBEO2
 * 
 * Revision 1.11 1999/09/28 11:13:24 elchkopf Es wurde die Methode
 * "createDumbParser" hinzugefuegt, um einen simplen Parser, der keine BEO
 * braucht zu kreieren.
 * 
 * Revision 1.10 1999/09/15 07:22:47 shultzke kleinen Fehler in der Factory
 * beseitigt
 * 
 * Revision 1.9 1999/09/14 10:52:37 shultzke Kommentare hinzugefuegt
 * 
 * Revision 1.8 1999/08/16 10:35:28 shultzke Beo wird als objekt erzeugt
 * 
 * Revision 1.7 1999/07/04 18:48:10 shultzke Docu erneuert. Events
 * implementiert.
 * 
 * Revision 1.6 1999/06/30 21:24:13 shultzke added rcs key and tried to check in
 * remote
 * 
 * Revision 1.5 1999/06/30 07:46:37 shultzke added event classes and changed
 * some method arguments
 * 
 * Revision 1.4 1999/06/10 09:56:12 shultzke added 'package ...' and Parser.java
 * 
 * Revision 1.3 1999/06/09 07:37:11 gragra Shared Source Working Environment
 * updated
 * 
 * Revision 1.2 1999/06/08 11:55:04 shultzke added all classes except the parser
 * 
 * Revision 1.1 1999/06/08 11:49:48 shultzke initial check-in
 */
