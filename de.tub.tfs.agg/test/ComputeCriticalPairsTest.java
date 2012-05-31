

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.parser.ConflictsDependenciesBasisGraph;
import agg.parser.ConflictsDependenciesContainer;
import agg.parser.CriticalPair;
import agg.parser.CriticalPairData;
import agg.parser.CriticalPairEvent;
import agg.parser.CriticalPairOption;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePairContainer;
import agg.parser.PairContainer;
import agg.parser.ParserEvent;
import agg.parser.ParserEventListener;
import agg.parser.ParserFactory;
import agg.parser.ParserMessageEvent;
import agg.util.XMLHelper;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.util.Pair;


/**
 * This class computes conflicts and dependencies of rule pairs.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class ComputeCriticalPairsTest implements ParserEventListener {

	/** Creates a new instance of the AGG critical pair analysis */
	public ComputeCriticalPairsTest() {
		this.cpOption = new CriticalPairOption();
		this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.EXCLUDEONLY);
		this.cpOption.enableLayered(false);
	}

	public static void main(String[] args) {
		ComputeCriticalPairsTest ccp = new ComputeCriticalPairsTest();
		ccp.run(args);
	}
	
	public void run(String[] args) {
		if (args.length == 0) {
			helpText();
			return;
		}
		this.fname = "";
		this.outfname = "";
		this.pairsNumberToWrite = -1;

		// create an empty GraGra
		this.gragra = BaseFactory.theFactory().createGraGra();
		
		implementInputParameter(args);
		
		// load a .ggx respectively .cpx file
		if (!loadGGXfile() && !loadCPXfile()) {
			System.out.println("Input file name failed.");
			return ;
		}
		
		// check whether the grammar is ready to transform resp. compute CPA
		String s0 = "";
		Pair<Object, String> pair = this.gragra.isReadyToTransform(true);
		if (pair != null){
			Object test = pair.first;
			if (test != null) {
				s0 = pair.second + "\nComputing CPA stopped.";
				System.out.println("Loaded grammar isn't ready for usage!\n" + s0);
				return;
			}
		}
		
		setOutputFileName();

		// now compute CPA
		if (this.computeConflict) {
			computeRuleConflicts();
		} 
		if (this.computeDependency) {
			computeRuleDependencies();
		}	
		
		if (!this.saved) {
			this.save();
		}
		
		// an exmpl. of how to check 
		// the rules are in conflict at the host graph 
		computeRuleConflictsAtHostGraph(this.gragra.getGraph());

	}
	
	private void implementInputParameter(String[] args) {
		// check input parameters
		if (args.length == 1) {
			this.fname = args[0];
			this.computeConflict = true;
			this.computeDependency = true;
		} else {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("C") || args[i].equals("-C")) {
					// parallel rule conflicts
					this.cpOption
							.setCriticalPairAlgorithm(CriticalPairOption.EXCLUDEONLY);
					this.computeDependency = false;
				} else if (args[i].equals("D") || args[i].equals("-D")) {
					// sequential rule dependencies
					this.cpOption
							.setCriticalPairAlgorithm(CriticalPairOption.TRIGGER_DEPEND);
					this.computeConflict = false;
				} else if (args[i].equals("e") || args[i].equals("-e")) {
					// essential conflicts resp. dependencies
					this.cpOption.enableReduce(true);
				} else if (args[i].equals("nc") || args[i].equals("-nc")) {
					// not complete conflicts resp. dependencies
					this.cpOption.enableComplete(false);
				} else if (args[i].equals("ncc") || args[i].equals("-ncc")) {
					// no check of graph consistency
					this.cpOption.enableConsistent(false);
				} else if (args[i].indexOf("-w") == 0
						|| args[i].indexOf("w") == 0) {	
					// write rule pairs to output file
					String nn = args[i].substring(1);
					if (args[i].indexOf("-w") == 0)
						 nn = args[i].substring(2);
					try {
						this.pairsNumberToWrite = (new Integer(nn)).intValue();
					} catch (NumberFormatException ex) {
						this.pairsNumberToWrite = 10;
					}
				} else if (args[i].equals("o") || args[i].equals("-o")) {
					// output file
					if ((i + 1) < args.length) {
						i++;
						this.outfname = args[i];
						if ((i + 1) >= args.length) {
							break;
						}
					}
				} else
					this.fname = args[i];
			}
		}

	}
	
	private boolean loadGGXfile() {
		if (this.fname.indexOf(".ggx") != -1) {
			System.out.println("File to load:  " + this.fname);
			try {
				this.gragra.load(this.fname);
			} catch (Exception ex) {
				System.out.println("Loading file failed.");
				return false;
			}
//System.out.println(this.gragra.getGraTraOptions());
			
			this.cpOption.enableLayered(this.gragra.isLayered());
			this.cpOption.enablePriority(this.gragra.trafoByPriority());
			return true;
		} 
		return false;
	}
	
	private boolean loadCPXfile() {
		if (this.fname.indexOf(".cpx") != -1) {
			System.out.println("File to load:  " + this.fname);
			ConflictsDependenciesContainer cdc = new ConflictsDependenciesContainer();
			
			Object o = null;
			XMLHelper h = new XMLHelper();			
			if (h.read_from_xml(this.fname))
				o = h.getTopObject(cdc);
			if (o == null) {
				System.out.println("Loading  file failed.");
				return false;
			}
					
			cdc = (ConflictsDependenciesContainer) o;
			if (cdc.getExcludePairContainer() != null) {
				this.excludePairContainer = cdc.getExcludePairContainer();
				this.dependPairContainer = cdc.getDependencyPairContainer();
				this.cpOption.enableLayered(false);
				this.gragra = this.excludePairContainer.getGrammar();
			} else if (cdc.getDependencyPairContainer() != null) {
				this.excludePairContainer = cdc.getExcludePairContainer();
				this.dependPairContainer = cdc.getDependencyPairContainer();
				this.cpOption.enableLayered(false);
				this.gragra = this.dependPairContainer.getGrammar();
			} else if (cdc.getLayeredExcludePairContainer() != null) {
				this.excludePairContainer = cdc.getLayeredExcludePairContainer();
				this.dependPairContainer = cdc.getLayeredDependencyPairContainer();
				this.cpOption.enableLayered(true);
				this.gragra = this.excludePairContainer.getGrammar();
			} else if (cdc.getLayeredDependencyPairContainer() != null) {
				this.excludePairContainer = cdc.getLayeredExcludePairContainer();
				this.dependPairContainer = cdc.getLayeredDependencyPairContainer();
				this.cpOption.enableLayered(true);
				this.gragra = this.dependPairContainer.getGrammar();
			}
//			System.out.println(this.gragra.getGraTraOptions());
			return true;
		}
		return false;
	}
	
	private void computeRuleConflicts() {
		this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.EXCLUDEONLY);
		// create empty rule container, if needed
		if (this.excludePairContainer == null) {
			this.excludePairContainer = ParserFactory.createEmptyCriticalPairs(
					this.gragra, CriticalPairOption.EXCLUDEONLY, this.cpOption.layeredEnabled());
		}
		// set options of CPA
		// (see more for options 
		// in AGG GUI / Help / Menu Guide / Preferences > Options/ Critical Pairs
		((ExcludePairContainer) this.excludePairContainer)
				.enableComplete(this.cpOption.completeEnabled());
		
		((ExcludePairContainer) this.excludePairContainer).enableReduce(this.cpOption
				.reduceEnabled());
		
		((ExcludePairContainer) this.excludePairContainer)
				.enableConsistent(this.cpOption.consistentEnabled());
		
		((ExcludePairContainer) this.excludePairContainer)
				.enableStrongAttrCheck(this.cpOption.strongAttrCheckEnabled());
		
		((ExcludePairContainer) this.excludePairContainer)
				.enableEqualVariableNameOfAttrMapping(
						this.cpOption.equalVariableNameOfAttrMappingEnabled());
		
		((ExcludePairContainer) this.excludePairContainer)
				.enableIgnoreIdenticalRules(this.cpOption
						.ignoreIdenticalRulesEnabled());
		((ExcludePairContainer) this.excludePairContainer)
				.enableReduceSameMatch(this.cpOption.reduceSameMatchEnabled());
		((ExcludePairContainer) this.excludePairContainer)
				.enableDirectlyStrictConfluent(this.cpOption.directlyStrictConflEnabled());
		
		((ExcludePairContainer) this.excludePairContainer)
				.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());
		
		// do not forget to add PairEventListener 
		// to be able to react to CriticalPairEvent 
		// (see this.parserEventOccured(ParserEvent e) )
		this.excludePairContainer.addPairEventListener(this);
		
		computeRuleConflictsAtHostGraph(this.gragra.getGraph());
		
		computeRuleConflictsStepByStep();	
		
		// or to let this for the ParserFactory
//		System.out.println("Generating conflicts of rules ... ");
//		ParserFactory.generateCriticalPairs(this.excludePairContainer);		
	}
	
	private void computeRuleConflictsStepByStep() {
		// now we can generate rule conflicts step by step
		Enumeration<Rule> rulelist1 = this.gragra.getRules();		
		while (rulelist1.hasMoreElements()) {
			Rule r1 = rulelist1.nextElement();
			if (r1.isEnabled()) {
				Enumeration<Rule> rulelist2 = this.gragra.getRules();
				while (rulelist2.hasMoreElements()) {
					Rule r2 = rulelist2.nextElement();
					if (r2.isEnabled()) {
						try {
							// Sorry, result type of a critical rule pair is very complex!
							Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
							resultlist = this.excludePairContainer.getCriticalPair(r1, r2, CriticalPair.EXCLUDE, true);
							if (resultlist != null) {
								System.out.println("Rule pair with conflict: ( "+r1.getName()+" , "+r2.getName()+" )");	
								
								// now you can use the CriticalPairData class to view conflicts in more readable form
								CriticalPairData cpdata = new CriticalPairData(r1, r2, resultlist);
								// see CriticalPairData methods for more infos

								// this is the old form of the inspection of critical pairs
								for (int i=0; i<resultlist.size(); i++) {
									Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>
									pair = resultlist.get(i);
									whatIsWhatOfConflictResult(r1, r2, pair);						
								}
							} else {
								System.out.println("Rule pair without conflict: ( "+r1.getName()+" , "+r2.getName()+" )");
							}
						} catch (Exception ex) {
							System.out.println("ExcludePairContainer Exception : "+ex.getLocalizedMessage()); 
						}
					}
				}
			}
		}		
	}
	
	private void computeRuleConflictsAtHostGraph(final Graph g) {
		if (this.excludePairContainer != null) {
			((ExcludePairContainer) this.excludePairContainer).enableUseHostGraph(true, g);
			
			Enumeration<Rule> rulelist1 = this.gragra.getRules();		
			while (rulelist1.hasMoreElements()) {
				Rule r1 = rulelist1.nextElement();
				Enumeration<Rule> rulelist2 = this.gragra.getRules();
				while (rulelist2.hasMoreElements()) {
					Rule r2 = rulelist2.nextElement();
					
					Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>
					result = ((ExcludePairContainer) this.excludePairContainer).getCriticalPairAtGraph(r1, r2);
					
					if (result != null && !result.isEmpty()) {
						for (int i=0; i<result.size(); i++) {
							Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>
							pair = result.get(i);
							System.out.println((i+1)+". conflict match of "+r1.getName()+" , "+r2.getName()+"  at graph: "+g.getName());
				
							// get the match of the rule r1 at the graph g
							Hashtable<GraphObject, GraphObject> matchR1 = pair.first;
							Enumeration<GraphObject> keys1 = matchR1.keys();
							while (keys1.hasMoreElements()) {
								// get element of the LHS of the rule r1
								GraphObject goLHS1 = keys1.nextElement();
								// get element of the graph g
								GraphObject go = matchR1.get(goLHS1);
								// is this GraphObject critical?
								if (go.isCritical()) {
									System.out.println("critical graph object: "+go);
								}
							}
							
							// get the match of the rule r2 at the graph g
							Hashtable<GraphObject, GraphObject> matchR2 = pair.second;
							Enumeration<GraphObject> keys2 = matchR2.keys();
							while (keys2.hasMoreElements()) {
								// get element of the LHS of the rule r2
								GraphObject goLHS2 = keys2.nextElement();
								// get element of the graph g
								GraphObject go = matchR2.get(goLHS2);
							}
						}
					}
					
				}
			}
		}
	}
	
	private void computeRuleDependencies() {
//		this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.TRIGGER_DEPEND);
		// another possibility
		this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.TRIGGER_SWITCH_DEPEND);
		
		// create empty rule container, if needed
		if (this.dependPairContainer == null) {
			this.dependPairContainer = ParserFactory.createEmptyCriticalPairs(
					this.gragra, this.cpOption.getCriticalPairAlgorithm(), this.cpOption
							.layeredEnabled());
		}
		// set options of CPA
		((DependencyPairContainer) this.dependPairContainer)
				.enableSwitchDependency(this.cpOption.switchDependencyEnabled());
				
		((DependencyPairContainer) this.dependPairContainer)
				.enableComplete(this.cpOption.completeEnabled());

		((DependencyPairContainer) this.dependPairContainer)
				.enableReduce(this.cpOption.reduceEnabled());
		
		((DependencyPairContainer) this.dependPairContainer)
				.enableConsistent(this.cpOption.consistentEnabled());
		
		((DependencyPairContainer) this.dependPairContainer)		
				.enableStrongAttrCheck(this.cpOption.strongAttrCheckEnabled());
		
		((DependencyPairContainer) this.dependPairContainer)
				.enableEqualVariableNameOfAttrMapping(
						this.cpOption.equalVariableNameOfAttrMappingEnabled());
		
		((DependencyPairContainer) this.dependPairContainer)
				.enableIgnoreIdenticalRules(this.cpOption
						.ignoreIdenticalRulesEnabled());
		((DependencyPairContainer) this.dependPairContainer)
				.enableReduceSameMatch(this.cpOption.reduceSameMatchEnabled());
		((DependencyPairContainer) this.dependPairContainer)
				.enableDirectlyStrictConfluent(this.cpOption.directlyStrictConflEnabled());
		
		((DependencyPairContainer) this.dependPairContainer)
			.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());
		
		// do not forget to add PairEventListener
		this.dependPairContainer.addPairEventListener(this);
		
		computeRuleDependenciesStepByStep();
		
		// or to let this for the ParserFactory
//		System.out.println("Generating dependencies of rules ... ");
//		ParserFactory.generateCriticalPairs(this.dependPairContainer);
	}
	
	private void computeRuleDependenciesStepByStep() {
		// now we can generate rule dependency step by step
		Enumeration<Rule> rulelist1 = this.gragra.getRules();
		while (rulelist1.hasMoreElements()) {
			Rule r1 = rulelist1.nextElement();
			if (r1.isEnabled()) {
			Enumeration<Rule> rulelist2 = this.gragra.getRules();
				while (rulelist2.hasMoreElements()) {
					Rule r2 = rulelist2.nextElement();
					if (r2.isEnabled()) {
						try {
							// Sorry, result type of a critical rule pair is very complex!
							Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>
							resultlist = this.dependPairContainer.getCriticalPair(r1, r2, CriticalPair.EXCLUDE, true);
							
							if (resultlist != null) {
								System.out.println("Rule pair with dependency: ( "+r1.getName()+" , "+r2.getName()+" )");
								
								// now you can use the CriticalPairData class to view conflicts in more readable form
								CriticalPairData cpdata = new CriticalPairData(r1, r2, resultlist);
								// see CriticalPairData methods for more infos

								// this is the old form of the inspection of critical pairs
								for (int i=0; i<resultlist.size(); i++) {
									Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>
									pair = resultlist.get(i);
									
									whatIsWhatOfDependencyResult(r1, r2, pair);						
								}
							} else {
								System.out.println("Rule pair without dependency: ( "+r1.getName()+" , "+r2.getName()+" )");
							}
						} catch (Exception ex) {
							System.out.println("DependencyPairContainer Exception : "+ex.getLocalizedMessage()); 
						}
					}
				}
			}
		}
	}
	
	/* Implements agg.parser.ParserEventListener */ 
	public void parserEventOccured(ParserEvent e) {
//		 System.out.println("Computing critical pairs - "+e.getMessage());
		if (e instanceof CriticalPairEvent) {
			if (((CriticalPairEvent) e).getKey() == CriticalPairEvent.CRITICAL
					|| ((CriticalPairEvent) e).getKey() == CriticalPairEvent.UNCRITICAL) {
				if (this.nP == this.pairsNumberToWrite) {
					savePair();
					this.nP = 0;
				} else
					this.nP++;
			}
		} else if (e instanceof ParserMessageEvent
				&& ((ParserMessageEvent) e).getKey() == ParserEvent.FINISHED) {
			this.computeConflict = false;
			System.out.println("Computing critical rule pairs  -  finished.");
			
			// now continue with dependencies, if needed
			if (this.computeDependency) {
				this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.TRIGGER_DEPEND);
								
				if (this.dependPairContainer == null)
					this.dependPairContainer = ParserFactory
							.createEmptyCriticalPairs(this.gragra, this.cpOption
									.getCriticalPairAlgorithm(), this.cpOption
									.layeredEnabled());
				
				((ExcludePairContainer) this.dependPairContainer)
						.enableComplete(this.cpOption.completeEnabled());
				 ((ExcludePairContainer) this.dependPairContainer)
				 		.enableReduce(this.cpOption.reduceEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableConsistent(this.cpOption.consistentEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableStrongAttrCheck(this.cpOption.strongAttrCheckEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableEqualVariableNameOfAttrMapping(
								this.cpOption.equalVariableNameOfAttrMappingEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableIgnoreIdenticalRules(this.cpOption.ignoreIdenticalRulesEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableReduceSameMatch(this.cpOption.reduceSameMatchEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableDirectlyStrictConfluent(this.cpOption.directlyStrictConflEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());
				
				System.out.println("Generating dependencies of rules ... ");
				this.dependPairContainer.addPairEventListener(this);
				this.computeDependency = false;
				ParserFactory.generateCriticalPairs(this.dependPairContainer);
				
			} else {
				save();
			}
		} else {
			System.out.println(e.getMessage());
		}
	}

	private void save() {
		System.out.println("Generating CPA graph. Please wait ...");
		Graph cpaGraph = generateCPAgraph();
		
		System.out.println("Save critical pairs and CPA graph ... ");
		// save computed critical pairs
		ConflictsDependenciesContainer cdPC = new ConflictsDependenciesContainer(
				this.excludePairContainer, this.dependPairContainer, cpaGraph);

		XMLHelper h = new XMLHelper();
		h.addTopObject(cdPC);
		h.save_to_xml(this.outfname);
		System.out
				.println("Computed critical pairs are saved in : " + this.outfname);
		System.out.println();
		this.saved = true;
	}

	private void savePair() {
		System.out.println("\nTry to save just computed pairs (+"
				+ this.pairsNumberToWrite + ").");
		ConflictsDependenciesContainer cdPC = new ConflictsDependenciesContainer(
				this.excludePairContainer, this.dependPairContainer);

		renameLastSaving();

		XMLHelper h = new XMLHelper();
		h.addTopObject(cdPC);
		h.save_to_xml(this.outfname);
		System.out
				.println("Computed critical pairs are saved in : " + this.outfname);
		System.out.println();
	}

	private void renameLastSaving() {
		File f = new File(this.outfname);
		if (f.exists()) {
			String lastSaving = "Last-" + this.outfname;
			File flast = new File(lastSaving);
			f.renameTo(flast);
			if (!flast.exists()) {
				System.out.println("Cannot rename  " + this.outfname + " to  "
						+ lastSaving + "  failed!");
			}
		}
	}

	private void setOutputFileName() {
		if (this.outfname.equals("")) {
			String s = "";
			if (this.fname.indexOf(".ggx") != -1)
				s = this.fname.substring(0, this.fname.indexOf(".ggx"));
			else if (this.fname.indexOf(".cpx") != -1)
				s = this.fname.substring(0, this.fname.indexOf(".cpx"));
			this.outfname = s + "_out.cpx";
		} else if (!this.outfname.endsWith(".cpx")) {
			this.outfname = this.outfname + ".cpx";
		}
	}

	private void helpText() {
		 System.out
		 		.println("Usage: java -Xmx1000m agg.parser.ComputeCriticalPairs [-C | -D] [-e | -nc] [-ncc] [-wN] [-o outfile] file");
		System.out.println("Where:");
		String str = "\t-C\t\t- compute parallel conflicts of rules"
				+ "\n\t-D\t\t- compute sequential dependencies of rules"
				+"\n\t-e\t\t- essential critical pairs only "
				+ "\n\t-nc\t\t- not complete critical pairs "
				+ "\n\t-ncc\t\t- critical pairs without respect \n\t\t\tto graph consistency constraints "
				+ "\n\t-wN\t\t- write each N computed rule pairs to output file "
				+ "\n\t\t\t(see also file with name: \"Last-\"+outfile, "
				+ "\n\t\t\tin case when ccp was brocken)"
				+ "\n\t-o outfile\t- output file "
				+ "\n\tdefaults:\t- complete, consistent critical pairs, "
				+ "\n\t\t\tthe name of output file is inputfilename_out.cpx "
				+ "\n\tfile\t	- *.ggx  or  *.cpx  file. ";
		System.out.println(str);
		System.out
				.println("If the algorithm parameter (C and D) missed, both, the conflicts and the dependencies, will be computed.");
		System.out
				.println("The input file  *.ggx  should contain a grammar with a rule set. This grammar can be layered. ");
		System.out
				.println("The output file  *_out.cpx  will contain computed critical pairs.");
		System.out
				.println("The input file  *.cpx  can contain partly computed critical pairs of one or of both algorithms.");
		System.out
				.println("The output file  *_out.cpx  will be completed with new computed critical pairs.");
		System.out
				.println("The output file  *_out.cpx  will also contain CPA graph generated from critical rule pairs.");
		System.out.println("");
	}

	private Graph generateCPAgraph() {
		ConflictsDependenciesBasisGraph conflictDependGraph = new ConflictsDependenciesBasisGraph(
				(ExcludePairContainer) this.excludePairContainer,
				(ExcludePairContainer) this.dependPairContainer);
		Graph cpaGraph = conflictDependGraph.getConflictsDependenciesGraph();
		return cpaGraph;
	}

	private void whatIsWhatOfConflictResult(
			Rule r1, 
			Rule r2, 
			Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> pair) {
		// first pair is a conflict pair
		Pair<OrdinaryMorphism, OrdinaryMorphism> p1 = pair.first;
		
		// first OrdinaryMorphism is the critical overlapping of rule r1
		OrdinaryMorphism om1 = p1.first;			
		// second OrdinaryMorphism is the critical overlapping of rule r2
		OrdinaryMorphism om2 = p1.second;

		// target graph of om1 == target graph of om2 == the overlapping graph
		Graph overlapGraph = om1.getTarget();

		// second pair is a help pair, which can be null, too
		Pair<OrdinaryMorphism, OrdinaryMorphism> p2 = pair.second;
		
		if (p2 == null) {
			// source graph of om2 should be always LHS of r2
			
			// source graph of om1 is LHS of r1
			if (om1.getSource() == r1.getLeft()) {
				if (om2.getSource() == r2.getLeft()) {
					// we have delete-use conflict
					// or change-use-attr conflict
					// show conflict graph object pairs
					Enumeration<GraphObject> domain1 = om1.getDomain();
					while (domain1.hasMoreElements()) {
						GraphObject go1 = domain1.nextElement();
						GraphObject go = om1.getImage(go1);
						if (go.isCritical()) {
							GraphObject go2 = om2.getInverseImage(go).nextElement();							
							System.out.println(overlapGraph.getName()
									+" :: critical object: "+go
									+"  of go1: "+go1+" and go2: "+go2);
						}
					}
				}
			}	
			
		} else {
			// source graph of om1 can be LHS of r1
			if (om1.getSource() == r1.getLeft()) {	
				
				// source of om2 is LHS2 extended by a PAC of r2
				Graph lhs2extPAC = om2.getSource();
				
				// first om of p2 is morph : LHS2 -> lhs2extPAC
				OrdinaryMorphism p2om1 = p2.first;
				// second om of p2 is morph : PAC_r2 -> lhs2extPAC
				OrdinaryMorphism p2om2 = p2.second;
				
				String pacName = overlapGraph.getHelpInfoAboutPAC();
				OrdinaryMorphism pac_r2 = r2.getPAC(pacName);
				if (pac_r2 != null) {
					// we have delete-need conflict
					// or change-need attr conflict
					// show conflict graph object pairs
					Enumeration<GraphObject> domain1 = om1.getDomain();
					while (domain1.hasMoreElements()) {
						GraphObject go1 = domain1.nextElement();
						GraphObject go = om1.getImage(go1);
						if (go.isCritical()) {
							GraphObject go2 = om2.getInverseImage(go).nextElement();
							Enumeration<GraphObject> test = p2om2.getInverseImage(go2);
							if (test.hasMoreElements()) {
								GraphObject go2PAC_r2 = test.nextElement();
								if (pac_r2.getTarget().isElement(go2PAC_r2)) {
									System.out.println(overlapGraph.getName()
										+" :: critical object: "+go
										+"  of go1: "+go1+" and go2PAC_r2: "+go2PAC_r2);
								} else {
									System.out.println("Something gone wrong with PAC: "+pacName);
								}
							}
						}
					}
				} else {
					String nacName = overlapGraph.getHelpInfoAboutNAC();
					OrdinaryMorphism nac_r2 = r2.getNAC(nacName);
					if (nac_r2 != null) {
						// or we have change-forbid attr conflict
						// show conflict graph object pairs
						Enumeration<GraphObject> domain1 = om1.getDomain();
						while (domain1.hasMoreElements()) {
							GraphObject go1 = domain1.nextElement();
							GraphObject go = om1.getImage(go1);
							if (go.isCritical()) {
								GraphObject go2 = om2.getInverseImage(go).nextElement();
								Enumeration<GraphObject> test = p2om2.getInverseImage(go2);
								if (test.hasMoreElements()) {
									GraphObject go2NAC_r2 = test.nextElement();
									if (nac_r2.getTarget().isElement(go2NAC_r2)) {
										System.out.println(overlapGraph.getName()
											+" :: critical object: "+go
											+"  of go1: "+go1+" and go2NAC_r2: "+go2NAC_r2);
									} else {
										System.out.println("Something gone wrong with NAC: "+nacName);
									}	
								}
							}
						}
					}
				}
			} else
			// source graph of om1 can be RHS of r1
			if (om1.getSource() == r1.getRight()) {		

				// source of om2 is LHS2 extended by a NAC of r2
				Graph lhs2extNAC = om2.getSource();
				
				// first om of p2 is morph : LHS2 -> lhs2extNAC
				OrdinaryMorphism p2om1 = p2.first;
				// second om of p2 is morph : NAC_r2 -> lhs2extNAC
				OrdinaryMorphism p2om2 = p2.second;
				
				String nacName = overlapGraph.getHelpInfoAboutNAC();
				OrdinaryMorphism nac_r2 = r2.getNAC(nacName);
				if (nac_r2 != null) {
					// we have produce-forbid conflict
					// show conflict graph object pairs
					Enumeration<GraphObject> domain1 = om1.getDomain();
					while (domain1.hasMoreElements()) {
						GraphObject go1 = domain1.nextElement();
						GraphObject go = om1.getImage(go1);
						if (go.isCritical()) {
							GraphObject go2 = om2.getInverseImage(go).nextElement();
							Enumeration<GraphObject> test = p2om2.getInverseImage(go2);
							if (test.hasMoreElements()) {
								GraphObject go2NAC_r2 = test.nextElement();
								if (nac_r2.getTarget().isElement(go2NAC_r2)) {
									System.out.println(overlapGraph.getName()
										+" :: critical object: "+go
										+"  of go1: "+go1+" and go2NAC_r2: "+go2NAC_r2);
								} else {
									System.out.println("Something gone wrong with NAC: "+nacName);
								}	
							}
						}
					}
				}
			}	
		}
	}

	private void whatIsWhatOfDependencyResult(
			Rule r1, 
			Rule r2, 
			Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> pair) {
		// first pair is a dependency pair
		Pair<OrdinaryMorphism, OrdinaryMorphism> p1 = pair.first;
		
		// first OrdinaryMorphism is the critical overlapping of rule r1
		OrdinaryMorphism om1 = p1.first;			
		// second OrdinaryMorphism is the critical overlapping of rule r2
		OrdinaryMorphism om2 = p1.second;

		// target graph of om1 == target graph of om2 == the overlapping graph
		Graph overlapGraph = om1.getTarget();

		// second pair is a help pair, which can be null, too
		Pair<OrdinaryMorphism, OrdinaryMorphism> p2 = pair.second;
		
		if (p2 == null) {
			// source graph of om2 should be always LHS of r2
			
			// source graph of om1 is RHS of r1
			if (om1.getSource() == r1.getRight()) {
				if (om2.getSource() == r2.getLeft()) {
					// we have produce-use dependency
					// or change-use-attr dependency
					// show dependency graph object pairs
					Enumeration<GraphObject> domain1 = om1.getDomain();
					while (domain1.hasMoreElements()) {
						GraphObject go1 = domain1.nextElement();
						GraphObject go = om1.getImage(go1);
						if (go.isCritical()) {
							GraphObject go2 = om2.getInverseImage(go).nextElement();							
							System.out.println(overlapGraph.getName()
									+" :: critical object: "+go
									+"  of go1: "+go1+" and go2: "+go2);
						}
					}
				}
			}	
			
		} else {
			// source graph of om1 can be RHS of r1
			if (om1.getSource() == r1.getRight()) {		

				// source of om2 is LHS2 extended by a NAC of r2
				Graph lhs2extNAC = om2.getSource();
				
				// first om of p2 is morph : LHS2 -> lhs2extNAC
				OrdinaryMorphism p2om1 = p2.first;
				// second om of p2 is morph : NAC_r2 -> lhs2extNAC
				OrdinaryMorphism p2om2 = p2.second;
				
				String nacName = overlapGraph.getHelpInfoAboutNAC();
				OrdinaryMorphism nac_r2 = r2.getNAC(nacName);
				if (nac_r2 != null) {
					// we have delete-forbid dependency
					// show dependency graph object pairs
					Enumeration<GraphObject> domain1 = om1.getDomain();
					while (domain1.hasMoreElements()) {
						GraphObject go1 = domain1.nextElement();
						GraphObject go = om1.getImage(go1);
						if (go.isCritical()) {
							GraphObject go2 = om2.getInverseImage(go).nextElement();
							Enumeration<GraphObject> test = p2om2.getInverseImage(go2);
							if (test.hasMoreElements()) {
								GraphObject go2NAC_r2 = test.nextElement();
								if (nac_r2.getTarget().isElement(go2NAC_r2)) {
									System.out.println(overlapGraph.getName()
										+" :: critical object: "+go
										+"  of go1: "+go1+" and go2NAC_r2: "+go2NAC_r2);
								} else {
									System.out.println("Something gone wrong with NAC: "+nacName);
								}	
							}
						}
					}
				}
			} else
			// source graph of om1 can be LHS of r1
			if (om1.getSource() == r1.getLeft()) {	
				
				// source of om2 is LHS2 extended by a PAC of r2
				Graph lhs2extPAC = om2.getSource();
				
				// first om of p2 is morph : LHS2 -> lhs2extPAC
				OrdinaryMorphism p2om1 = p2.first;
				// second om of p2 is morph : PAC_r2 -> lhs2extPAC
				OrdinaryMorphism p2om2 = p2.second;
				
				String pacName = overlapGraph.getHelpInfoAboutPAC();
				OrdinaryMorphism pac_r2 = r2.getPAC(pacName);
				if (pac_r2 != null) {
					// we have produce-need dependency
					// or change-use attr dependency
					// show conflict graph object pairs
					Enumeration<GraphObject> domain1 = om1.getDomain();
					while (domain1.hasMoreElements()) {
						GraphObject go1 = domain1.nextElement();
						GraphObject go = om1.getImage(go1);
						if (go.isCritical()) {
							GraphObject go2 = om2.getInverseImage(go).nextElement();
							Enumeration<GraphObject> test = p2om2.getInverseImage(go2);
							if (test.hasMoreElements()) {
								GraphObject go2PAC_r2 = test.nextElement();
								if (pac_r2.getTarget().isElement(go2PAC_r2)) {
									System.out.println(overlapGraph.getName()
										+" :: critical object: "+go
										+"  of go1: "+go1+" and go2PAC_r2: "+go2PAC_r2);
								} else {
									System.out.println("Something gone wrong with PAC: "+pacName);
								}
							}
						}
					}
				} else {
					String nacName = overlapGraph.getHelpInfoAboutPAC();
					OrdinaryMorphism nac_r2 = r2.getNAC(nacName);
					if (nac_r2 != null) {
						// or we have change-forbid attr dependency
						// show dependency graph object pairs
						Enumeration<GraphObject> domain1 = om1.getDomain();
						while (domain1.hasMoreElements()) {
							GraphObject go1 = domain1.nextElement();
							GraphObject go = om1.getImage(go1);
							if (go.isCritical()) {
								GraphObject go2 = om2.getInverseImage(go).nextElement();
								Enumeration<GraphObject> test = p2om2.getInverseImage(go2);
								if (test.hasMoreElements()) {
									GraphObject go2NAC_r2 = test.nextElement();
									if (nac_r2.getTarget().isElement(go2NAC_r2)) {
										System.out.println(overlapGraph.getName()
											+" :: critical object: "+go
											+"  of go1: "+go1+" and go2NAC_r2: "+go2NAC_r2);
									} else {
										System.out.println("Something gone wrong with NAC: "+nacName);
									}	
								}
							}
						}
					}
				}
			}
	
		}
	}
	
	public String anOptionStr;

	public CriticalPairOption cpOption;

	public ConflictsDependenciesContainer cdContainer;

	public PairContainer excludePairContainer;

	public PairContainer dependPairContainer;

	public String fname, outfname;

	public boolean computeConflict = true;

	public boolean computeDependency = true;

	public GraGra gragra;

	int pairsNumberToWrite, nP;
		
	boolean saved;

}
