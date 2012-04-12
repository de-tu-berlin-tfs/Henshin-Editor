

import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTra;
import agg.xt_basis.DefaultGraTraImpl;
import agg.xt_basis.LayeredGraTraImpl;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.PriorityGraTraImpl;
import agg.xt_basis.RuleSequencesGraTraImpl;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.util.XMLHelper;

public class ConferenceScheduleTest implements GraTraEventListener {

	private static XMLHelper h;

	private static GraGra gragra;

//	private static GraGra impGraGra;
	
//	private static Graph impGraph;
	
//	private static Graph impTypeGraph;
	
	private static GraTra gratra;

	private int msgGraTra;

	private static boolean layered = false, ruleSequence = false,
			priority = false;;

	private static boolean didTransformation = false;

	private static String fileName;

//	private static String impFileName;

	private static String outputFileName;

//	private static String error;

	private static boolean writeLogFile = false;


	public ConferenceScheduleTest(String filename) {
		fileName = filename;
		System.out.println("File name:  " + fileName);
		/* load gragra */
		
		gragra = load(fileName);						
		
		if (gragra != null) {
			//gragra.getLevelOfTypeGraphCheck();

		
			// do transform						
			transform(gragra, this);
			
			
			if (didTransformation) {
				// save gragra
//				String out = "_out.ggx";
//				save(gragra, out);
//				System.out.println("Output file:  " + outputFileName);				
			} else
				System.out.println("Grammar:  " + gragra.getName()
						+ "   could not perform any transformations!");						
			
		} else
			System.out.println("Grammar:  " + filename + "   FAILED!");
	}


	public static void main(String[] args) {
		String vers = System.getProperty("java.version");
		if (vers.compareTo("1.4.2") < 0) {
			System.out.println("WARNING : Swing must be run with the "
					+ "1.4.2 version of the JVM.");
		}

		if (args.length == 1) {
			if ((args[0]).compareToIgnoreCase("-logfile") != 0) {
				new ConferenceScheduleTest(args[0]);
				writeLogFile = false;
			}
		} else {
			warning();
			return;
		}
	}

	static void warning() {
		System.out.println("Execution failed. No grammar given.");
	}

	public static GraGra load(String fName) {
		// System.out.println(fileName.endsWith(".ggx"));
		if (fName.endsWith(".ggx")) {
			h = new XMLHelper();
			if (h.read_from_xml(fName)) {
	
				// create a gragra
				GraGra gra =  new GraGra(false);
				h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			} 
			return null;
		}
		return null;
	}

	public static void transform(GraGra grammar, GraTraEventListener l) {
		if (grammar == null)
			return;
		/*
		 * test: there is a one way to set transformation options Vector gto =
		 * new Vector(); gto.add("layered"); gto.add("CSP");
		 * gto.add("injective"); gto.add("dangling");
		 * gragra.setGraTraOptions(gto);
		 */

		// create trafo
		// System.out.println(gragra.getGraTraOptions().toString());
		if (grammar.getGraTraOptions().contains("priority")) {
			gratra = new PriorityGraTraImpl();
			priority = true;
			System.out.println("Transformation by rule priority ...");
		} else if (grammar.getGraTraOptions().contains("layered")) {
			gratra = new LayeredGraTraImpl();
			layered = true;
			System.out.println("Layered transformation ...");
		} else if (grammar.getGraTraOptions().contains("ruleSequence")) {
			gratra = new RuleSequencesGraTraImpl();
			ruleSequence = true;
			System.out.println("Transformation by rule sequences ...");
		} else {
			gratra = new DefaultGraTraImpl();
			System.out.println("Transformation  non-deterministically ...");
		}

		gratra.addGraTraListener(l);
		gratra.setGraGra(grammar);
		gratra.setHostGraph(grammar.getGraph());
		gratra.enableWriteLogFile(writeLogFile);

		MorphCompletionStrategy strategy = CompletionStrategySelector
				.getDefault();
		// strategy = new Completion_NAC(new Completion_InjCSP());

		if (grammar.getGraTraOptions().isEmpty()) {
			grammar.setGraTraOptions(strategy);
			gratra.setCompletionStrategy(strategy);
		} else {
			if (grammar.getGraTraOptions().contains("showGraphAfterStep"))
				grammar.getGraTraOptions().remove("showGraphAfterStep");
			gratra.setGraTraOptions(grammar.getGraTraOptions());
			System.out.println("Options:  " + grammar.getGraTraOptions());
			System.out.println();
		}

		grammar.destroyAllMatches();

		if (layered) {			
			long startTime = System.currentTimeMillis();
			int nruns = 500;
			
			for (int i=0; i<nruns; i++) {
				((LayeredGraTraImpl) gratra).transform();
			}
			long endTime = System.currentTimeMillis();
			long time = endTime - startTime;
			System.out.println("500 runs, time: "+time+" average run: "+(time/nruns)+"ms");
		}
		
		else if (priority) {
			((PriorityGraTraImpl) gratra).transform();
		}
		
		else if (ruleSequence) {
			((RuleSequencesGraTraImpl) gratra).transform();
		}
		
		else {
			((DefaultGraTraImpl) gratra).transform();
			
			/*  test
			gragra.setStartGraph(gragra.getGraph().copy());
			int nruns = 10;			
			for (int i=0; i<nruns; i++) {
				System.out.println(i+".  transformation run ");
				((DefaultGraTraImpl) gratra).transform();
		
				gragra.resetGraph();
				gratra.setHostGraph(grammar.getGraph());
			}
			*/
		}
	}

	public static void save(GraGra gra, String outFileName) {
		// System.out.println("Output into: "+outFileName);
		if (outFileName.equals(""))
			outputFileName = gra.getName() + "_out.ggx";
		else if (outFileName.equals("_out.ggx"))
			outputFileName = fileName.substring(0, fileName.length() - 4)
					+ "_out.ggx";
		else if (outFileName.indexOf(".ggx") == -1)
			outputFileName = outFileName.concat(".ggx");
		else if (outFileName.equals(fileName))
			outputFileName = fileName.substring(0, fileName.length() - 4)
					+ "_out.ggx";
		else {
			outputFileName = outFileName;
		}
		// System.out.println("save :: Output into: "+outputFileName);
		if (outputFileName.endsWith(".ggx")) {
			XMLHelper xmlh = new XMLHelper();
			xmlh.addTopObject(gra);
			xmlh.save_to_xml(outputFileName);
		}

	}

	/** Implements GraTraEventListener.graTraEventOccurred */
	public void graTraEventOccurred(GraTraEvent event) {
		// System.out.println("AGGBasicAppl.graTraEventOccurred
		// "+event.getMessage());
		
//		Match match = event.getMatch();
		
//		String ruleName = "Rule";
//		if (match != null)
//			ruleName = match.getRule().getName();

		this.msgGraTra = event.getMessage();
		if (this.msgGraTra == GraTraEvent.TRANSFORM_FINISHED) {
			gratra.stop();
			didTransformation = gratra.transformationDone();
			// System.out.println("GraTraEvent message : TRANSFORM_FINISHED");
		} else if ((this.msgGraTra == GraTraEvent.INPUT_PARAMETER_NOT_SET)) {
			System.out.println("GraTraEvent message : PARAMETER NOT SET!");
		}
		/*
		 * else if((msgGraTra == GraTraEvent.STEP_COMPLETED)) { //
		 * System.out.println("Rule : "+ruleName+" ==> STEP DONE" ); }
		 * 
		 * else if (msgGraTra == GraTraEvent.NO_COMPLETION) {
		 * //System.out.println("Rule : "+ ruleName+" ==> NO_COMPLETION"); }
		 * else if (msgGraTra == GraTraEvent.CANNOT_TRANSFORM){
		 * //System.out.println("Rule : "+ ruleName+" ==> CANNOT_TRANSFORM"); }
		 */
	}

}
