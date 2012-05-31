

import java.util.Iterator;
import java.util.Vector;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.GraGra;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Node;
import agg.xt_basis.Rule;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.GraTra;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.xt_basis.Match;
import agg.xt_basis.BadMappingException;
import agg.util.XMLHelper;

public class PartialMatchBasicTest implements GraTraEventListener {

	private static XMLHelper h = new XMLHelper();

	private static GraGra gragra;

	private static GraTra gratra;

	private int msgGraTra;

//	private static boolean didTransformation = false;

	private static String fileName;

	public PartialMatchBasicTest() {
	}

	public PartialMatchBasicTest(String filename) {
		fileName = filename;
		System.out.println("File name:  " + fileName);
		/* load gragra */
		gragra = load(fileName);

		if (gragra != null) {
			matchingTest();
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
				new PartialMatchBasicTest(args[0]);
			}
		}
	}

	public static GraGra load(String fName) {
		// System.out.println(fileName.endsWith(".ggx"));
		if (fName.endsWith(".ggx")) {
			if (h.read_from_xml(fName)) {

				// create a gragra
				GraGra gra = BaseFactory.theFactory().createGraGra();
				h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			}
			return null;
		} 
		return null;
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
//			didTransformation = 
			gratra.transformationDone();
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

	public void matchingTest() {
		long t = System.currentTimeMillis();
		// test partial match completion
		Rule r = gragra.getRule(0);
		makeMatch(r);

		int times = 3;
		for (int i = 0; i < times; i++) {
			System.out.println(i + ". test of partial match completion");
			setPartialMatch(r, i);
			completeMatch(r);

			r.getMatch().clear();
			r.getMatch().resetVariableDomainOfCompletionStrategy(true);
			r.getMatch().getCompletionStrategy().reinitializeSolver(true);
			// gragra.destroyMatch(r.getMatch());
			// r.setMatch(null);
		}

		System.out.println("Now  all match completions");
		completeMatch(r);

		r.getMatch().clear();
		r.getMatch().resetVariableDomainOfCompletionStrategy(true);
		r.getMatch().getCompletionStrategy().reinitializeSolver(true);
		// gragra.destroyMatch(r.getMatch());
		// r.setMatch(null);
		System.out.println("*** Used time: " + (System.currentTimeMillis() - t)
				+ "ms");
	}

	private void makeMatch(Rule r) {
		gragra.createMatch(r);
		MorphCompletionStrategy strategy = CompletionStrategySelector
				.getDefault();
		r.getMatch().setCompletionStrategy(strategy, true);
	}

	private void setPartialMatch(Rule r, int indx) {
		if (r.getMatch() == null)
			makeMatch(r);

		Graph lhs = r.getLeft();
		int nn = -1;
		GraphObject orig = null;
		GraphObject img = null;
		Iterator<Node> en = lhs.getNodesSet().iterator();
		while (en.hasNext()) {
			GraphObject go = en.next();
			Vector<GraphObject> dom = r.getMatch().getTarget().getElementsOfTypeAsVector(
					go.getType());
			int domSize = dom.size();
			if ((domSize > 0) && (nn == -1 || domSize < nn)) {
				nn = domSize;
				orig = go;
				img = dom.get(indx);
			}
		}
		if (orig != null && img != null) {
			try {
				r.getMatch().addMapping(orig, img);
				r.getMatch().setPartialMorphismCompletion(true);
				System.out.println(orig.getType().getName() + " --> "
						+ img.getType().getName());
			} catch (BadMappingException ex) {
			}
		}
	}

	private void completeMatch(Rule r) {
		if (r.getMatch() == null)
			makeMatch(r);

		Match m = r.getMatch();
		System.out.println("Rule : " + r.getName() + "   match  is empty: "
				+ m.isEmpty() + "  isPartial: " + m.isPartial());
		int nn = 0;
		while (m.nextCompletion()) {
			if (m.isValid()) {
				nn++;
				System.out.println(nn + ".  completion of (part.) match");
			} else {
				m.clear();
			}
		}
		System.out.println("NO MORE COMPLETION!");
	}

}
