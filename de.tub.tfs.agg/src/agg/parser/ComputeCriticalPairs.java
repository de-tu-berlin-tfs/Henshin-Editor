package agg.parser;

import java.io.File;

import agg.util.XMLHelper;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.util.Pair;

/**
 * This class computes conflicts and dependencies of rule pairs.
 * 
 * @author $Author: olga $
 * @version $ID
 */
public class ComputeCriticalPairs implements ParserEventListener {

	/** Creates a new instance of the AGG critical pair analysis */
	public ComputeCriticalPairs() {
		this.cpOption = new CriticalPairOption();
		this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.EXCLUDEONLY);
		this.cpOption.enableLayered(false);
	}

	/* Implements agg.parser.ParserEventListener */
	public void parserEventOccured(ParserEvent e) {
		// System.out.println("Computing critical pairs - "+e.getMessage());
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
			System.out.println("Computing critical pairs  -  finished.");
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
						.enableNACs(this.cpOption.nacsEnabled());
				((ExcludePairContainer) this.dependPairContainer)
				.enablePACs(this.cpOption.pacsEnabled());
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
						.enableIgnoreIdenticalRules(this.cpOption
						.ignoreIdenticalRulesEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableReduceSameMatch(this.cpOption.reduceSameMatchEnabled());
				((ExcludePairContainer) this.dependPairContainer)
						.enableDirectlyStrictConfluent(this.cpOption.directlyStrictConflEnabled());
				((ExcludePairContainer) this.dependPairContainer)
				.enableDirectlyStrictConfluentUpToIso(this.cpOption.directlyStrictConflUpToIsoEnabled());
				((ExcludePairContainer) this.dependPairContainer)
				.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());

				
				System.out.println("Generating dependencies of rules ... ");
				this.dependPairContainer.addPairEventListener(this);
				this.computeDependency = false;
				ParserFactory.generateCriticalPairs(this.dependPairContainer);				
			} else
				save();
		} else
			System.out.println(e.getMessage());
	}

	private void save() {
		System.out.println("Generate CPA graph ...");
		Graph cpaGraph = generateCPAgraph();
		System.out.println("Save critical pairs and CPA graph ... ");

		/* save computed critical pairs */
		ConflictsDependenciesContainer cdPC = new ConflictsDependenciesContainer(
				this.excludePairContainer, this.dependPairContainer, cpaGraph);

		XMLHelper h = new XMLHelper();
		h.addTopObject(cdPC);
		h.save_to_xml(this.outfname);
		System.out
				.println("Computed critical pairs are saved in : " + this.outfname);
		System.out.println();
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
		} else if (!this.outfname.endsWith(".cpx"))
			this.outfname = this.outfname + ".cpx";
	}

	private void helpText() {
		 System.out
		 		.println("Usage: java -Xmx1000m agg.parser.ComputeCriticalPairs [-C | -D] [-e | -nc] [-cc] [-ns] [-wN] [-o outfile] file");
//		System.out
//				.println("Usage: java -Xmx1000m agg.parser.ComputeCriticalPairs [-C | -D] [-nc] [-cc] [-ns] [-wN] [-o outfile] file");

		System.out.println("Where:");
		String str = "\t-C\t\t- compute parallel conflicts of rules"
				+ "\n\t-D\t\t- compute sequential dependencies of rules"
				+"\n\t-e\t\t- essential critical pairs only "
				+ "\n\t-nc\t\t- not complete critical pairs "
				+ "\n\t-cc\t\t- critical pairs with checking \n\t\t\t graph consistency constraints "
				+ "\n\t-ns\t\t- without strong attribute check"
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

		// objects for critical pairs
		XMLHelper h = new XMLHelper();

		if (args.length == 1) {
			this.fname = args[0];
			this.computeConflict = true;
			this.computeDependency = true;
		} else {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("C") || args[i].equals("-C")) {
					this.cpOption
							.setCriticalPairAlgorithm(CriticalPairOption.EXCLUDEONLY);
					this.computeDependency = false;
				} else if (args[i].equals("D") || args[i].equals("-D")) {
					this.cpOption
							.setCriticalPairAlgorithm(CriticalPairOption.TRIGGER_DEPEND);
					this.computeConflict = false;
				} else if (args[i].equals("e") || args[i].equals("-e")) {
					this.cpOption.enableReduce(true);
				} else if (args[i].equals("nc") || args[i].equals("-nc")) {
					this.cpOption.enableComplete(false);
				} else if (args[i].equals("cc") || args[i].equals("-cc")) {
					this.cpOption.enableConsistent(true);
				} else if (args[i].equals("ns") || args[i].equals("-ns")) {
					this.cpOption.enableStrongAttrCheck(false);
				}
				else if (args[i].indexOf("-w") == 0
						|| args[i].indexOf("w") == 0) {					
					String nn = args[i].substring(1);
					if (args[i].indexOf("-w") == 0)
						 nn = args[i].substring(2);
					try {
						this.pairsNumberToWrite = (new Integer(nn)).intValue();
					} catch (NumberFormatException ex) {
						this.pairsNumberToWrite = 10;
					}
				} else if (args[i].equals("o") || args[i].equals("-o")) {
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

		if (this.fname.indexOf(".ggx") != -1) {
			System.out.println("File to load:  " + this.fname);
			try {
				this.gragra.load(this.fname);
			} catch (Exception ex) {
				System.out.println("Loading file failed.");
				return;
			}
//System.out.println(this.gragra.getGraTraOptions());
			this.cpOption.enableLayered(this.gragra.isLayered());
			this.cpOption.enablePriority(this.gragra.trafoByPriority());
		} else if (this.fname.indexOf(".cpx") != -1) {
			System.out.println("File to load:  " + this.fname);
			ConflictsDependenciesContainer cdc = new ConflictsDependenciesContainer();
			
			Object o = null;
			if (h.read_from_xml(this.fname))
				o = h.getTopObject(cdc);
			if (o == null) {
				System.out.println("Loading file failed.");
				return;
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
		} else {
			System.out.println("Input file name failed.");
			return;
		}

		// check whether the grammar is ready to transform resp. compute CPs
		String s0 = "";
		Pair<Object, String> pair = this.gragra.isReadyToTransform(true);
		if (pair != null){
			Object test = pair.first;
			if (test != null) {
				s0 = pair.second + "\nComputing CPs stopped.";
				System.out.println("Loaded grammar is not ready for usage!\n" + s0);
				return;
			}
		}
		
		setOutputFileName();

		if (this.computeConflict) {
			this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.EXCLUDEONLY);
			if (this.excludePairContainer == null)
				this.excludePairContainer = ParserFactory.createEmptyCriticalPairs(
						this.gragra, CriticalPairOption.EXCLUDEONLY, this.cpOption.layeredEnabled());
			
			((ExcludePairContainer) this.excludePairContainer)
					.enableComplete(this.cpOption.completeEnabled());
			((ExcludePairContainer) this.excludePairContainer).enableNACs(this.cpOption
					.nacsEnabled());
			((ExcludePairContainer) this.excludePairContainer).enablePACs(this.cpOption
					.pacsEnabled());
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
					.enableDirectlyStrictConfluentUpToIso(this.cpOption.directlyStrictConflUpToIsoEnabled());
			((ExcludePairContainer) this.excludePairContainer)
					.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());
			System.out.println("Generating conflicts of rules ... ");
			this.excludePairContainer.addPairEventListener(this);
			ParserFactory.generateCriticalPairs(this.excludePairContainer);
		} else if (this.computeDependency) {
			this.cpOption.setCriticalPairAlgorithm(CriticalPairOption.TRIGGER_DEPEND);
			if (this.dependPairContainer == null)
				this.dependPairContainer = ParserFactory.createEmptyCriticalPairs(
						this.gragra, this.cpOption.getCriticalPairAlgorithm(), this.cpOption
								.layeredEnabled());
			
			((ExcludePairContainer) this.dependPairContainer)
					.enableComplete(this.cpOption.completeEnabled());
			((ExcludePairContainer) this.dependPairContainer).enableNACs(this.cpOption
					.nacsEnabled());
			((ExcludePairContainer) this.dependPairContainer).enablePACs(this.cpOption
					.pacsEnabled());
			 ((ExcludePairContainer) this.dependPairContainer).enableReduce(this.cpOption
					 .reduceEnabled());
			((ExcludePairContainer) this.dependPairContainer)
					.enableConsistent(this.cpOption.consistentEnabled());
			((ExcludePairContainer) this.dependPairContainer)
					.enableStrongAttrCheck(this.cpOption.strongAttrCheckEnabled());
			((ExcludePairContainer) this.dependPairContainer)
					.enableEqualVariableNameOfAttrMapping(
							this.cpOption.equalVariableNameOfAttrMappingEnabled());
			((ExcludePairContainer) this.dependPairContainer)
					.enableIgnoreIdenticalRules(this.cpOption
							.ignoreIdenticalRulesEnabled());
			((ExcludePairContainer) this.dependPairContainer)
					.enableReduceSameMatch(this.cpOption.reduceSameMatchEnabled());
			((ExcludePairContainer) this.dependPairContainer)
					.enableDirectlyStrictConfluent(this.cpOption.directlyStrictConflEnabled());
			((ExcludePairContainer) this.dependPairContainer)
					.enableNamedObjectOnly(this.cpOption.namedObjectEnabled());
			System.out.println("Generating dependencies of rules ... ");
			this.dependPairContainer.addPairEventListener(this);
			ParserFactory.generateCriticalPairs(this.dependPairContainer);
		}
	}

	public static void main(String[] args) {
		ComputeCriticalPairs ccp = new ComputeCriticalPairs();
		ccp.run(args);
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

}
