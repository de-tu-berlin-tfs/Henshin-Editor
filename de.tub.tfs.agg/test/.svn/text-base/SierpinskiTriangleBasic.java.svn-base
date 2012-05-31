

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Rule;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTra;
import agg.xt_basis.DefaultGraTraImpl;
import agg.xt_basis.LayeredGraTraImpl;
import agg.xt_basis.PriorityGraTraImpl;
import agg.xt_basis.RuleSequencesGraTraImpl;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.xt_basis.Match;
import agg.xt_basis.Version;
import agg.util.XMLHelper;

public class SierpinskiTriangleBasic implements GraTraEventListener {

	long startTime = System.currentTimeMillis();
	
	static XMLHelper h;
	
	static GraGra gragra;

	static int NN;
	
	GraTra gratra;

	int msgGraTra;

	static boolean layered = false;
	
	static boolean ruleSequence = false;
	
	static boolean priority = false;
	
	boolean didTransformation = true;

	static String fileName = "";

	static String outputFileName;

	boolean writeLogFile = false;
	
	File f;

	FileOutputStream os;

	String protocolFileName = "";

	
	public SierpinskiTriangleBasic(String filename) {
		this(filename, "1");
	}
	
	public SierpinskiTriangleBasic(String filename, String nn) {
		NN = (Integer.valueOf(nn)).intValue();	
				
		fileName = filename;
		System.out.println("File name: " + fileName+"   iterations: "+NN);
		description();
		
		gragra = load(fileName);

		if (gragra != null) {
			prepareTransformProtocol();
			prepareTransform();
									
			transform(NN);
			
			String s = "\nUsed time: "+(System.currentTimeMillis()-this.startTime)+"ms";		
			writeTransformProtocol(s);
			closeTransformProtocol();
			
			if (this.didTransformation) {
				if (outputFileName != null) {
					// save gragra					
					save(gragra, outputFileName);
					System.out.println("Output file: " + outputFileName);
				}
				if (this.writeLogFile)
					System.out.println("Protocol file: " + this.protocolFileName);
			} else
				System.out.println("Grammar:  " + filename //gragra.getName()
						+ "   could not perform any transformations!");
		} else
			System.out.println("Grammar:  " + filename + "   FAILED!");
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			SierpinskiTriangleBasic.helpText();
		} else {
			handleInput(args);
			new SierpinskiTriangleBasic(fileName, String.valueOf(NN));			
		}
	}

	static void handleInput(String[] args){
		NN = -1;
		for (int i=0; i<args.length; i++) {
			String nn = args[i];
			if(NN == -1) {
				try {
					NN = (Integer.valueOf(nn)).intValue();
					continue;
				} catch (NumberFormatException ex) {}
			}
			
			if (nn.indexOf("-o") == 0) {
				if ((i + 1) < args.length) {
					i++;
					outputFileName = args[i];					
				}
				else
					outputFileName = "";
				continue;
			}
			
			if(fileName.equals("")) {
				if (args[i].endsWith(".ggx")) 
					fileName = args[i];
				else 
					fileName = args[i] + ".ggx";
				continue;
			}
		}
		
		if(NN == -1)
			NN = 1;
	}
	
	/**
	 * A subclass should replace this method
	 */
	void description() {
		String s = " -) Input grammar defines rule matching and graph transformation.";
		System.out.println(s);
	}
	
	static void helpText() {
		 System.out
		 		.println("Usage: java -Xmx1000m test.SierpinskiTriangleBasic [N] ggxfile  [-o outfile]");
		System.out.println("Where:");
		String str = "\tN\t\t- iteration number of graph transformation, 1 by default"
				+ "\n\tggxfile\t\t- input file (.ggx) with an AGG grammar"
				+ "\n\t-o outfile\t- output file (.ggx) to put the grammar after transformation";
		System.out.println(str);
		System.out.println("");
	}

	static void warning() {
		System.out.println("Input grammar isn't found!");
	}

	static GraGra load(String fName) {
		if (fName.endsWith(".ggx")) {			
			GraGra gra = BaseFactory.theFactory().createGraGra();
			h = new XMLHelper();
			if (h.read_from_xml(fName)) {
				h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			} 
			return null;
		} 
		return null;
	}

	static void save(GraGra gra, String out) {
		if (outputFileName == null)
			return;
		
		if (out.equals(""))
			outputFileName = gra.getName() + "_out";
		
		if (!outputFileName.endsWith(".ggx")) 
			outputFileName = outputFileName + ".ggx";
		
		gra.save(outputFileName);
	}
	
	/**
	 * A subclass should replace this method
	 */
	void prepareTransform() {		
		if (gragra.getGraTraOptions().contains("ruleSequence")) {
			this.gratra = new RuleSequencesGraTraImpl();
			ruleSequence = true;
			System.out.println("Graph transformation by rule sequences ...");
		} else if (gragra.getGraTraOptions().contains("priority")) {
			this.gratra = new PriorityGraTraImpl();
			priority = true;
			System.out.println("Graph transformation by rule priority ...");
		} else if (gragra.getGraTraOptions().contains("layered")) {
			this.gratra = new LayeredGraTraImpl();
			layered = true;
			System.out.println("Layered graph transformation ...");
		} else {
			this.gratra = new DefaultGraTraImpl();
			System.out.println("Graph transformation  non-deterministically ...");
		}

		gragra.destroyAllMatches();
		
		
		MorphCompletionStrategy strategy = CompletionStrategySelector.getDefault();
		strategy.setRandomisedDomain(false);
		
		if (!gragra.hasRuleWithApplCond()) {
			strategy = new Completion_InjCSP(false);
		} 
//		strategy.showProperties();
		
		gragra.setGraTraOptions(strategy);
		
		this.gratra.setGraGra(gragra);
		this.gratra.setHostGraph(gragra.getGraph());
		
		if (gragra.getGraTraOptions().isEmpty()) {
			gragra.setGraTraOptions(strategy);
			this.gratra.setCompletionStrategy(strategy);
		} else {
			gragra.getMorphismCompletionStrategy().setRandomisedDomain(false);
			if (gragra.getGraTraOptions().contains("showGraphAfterStep"))		
				gragra.getGraTraOptions().remove("showGraphAfterStep");
		}
//		gratra.setGraTraOptions(gragra.getGraTraOptions());

		addGraTraEventListener();		
	}

	/**
	 * A subclass should replace this method
	 */
	void addGraTraEventListener() {
		this.gratra.addGraTraListener(this);		
	}
		
	
	void transform(int iterations) {
		if (ruleSequence) {
			((RuleSequencesGraTraImpl) this.gratra).transform();
		}
		else if (priority) {
			((PriorityGraTraImpl) this.gratra).transform();
		}
		else if (layered) {
			((LayeredGraTraImpl) this.gratra).transform();
		}
		else {			
			((DefaultGraTraImpl) this.gratra).setMaxOfCounter(iterations);
			((DefaultGraTraImpl) this.gratra).transform();
		}
	}

	/** Implements GraTraEventListener.graTraEventOccurred 
	 * 
	 * A subclass should replace this method
	*/	
	public void graTraEventOccurred(GraTraEvent event) {
		Match match = event.getMatch();
		
//		String ruleName = "Rule";
//		if (match != null)
//			ruleName = match.getRule().getName();

		this.msgGraTra = event.getMessage();
		if (this.msgGraTra == GraTraEvent.TRANSFORM_FINISHED) {
			this.gratra.stop();
			this.didTransformation = this.gratra.transformationDone();
		
		} else if ((this.msgGraTra == GraTraEvent.INPUT_PARAMETER_NOT_SET)) {
			System.out.println("GraTraEvent message : PARAMETER NOT SET!");
		}		
		else if((this.msgGraTra == GraTraEvent.STEP_COMPLETED)) { 
//			if (match != null && match.getCoMorphism() != null)
				match.getCoMorphism().dispose();
//			System.out.println("Rule : "+ruleName+" ==> STEP DONE" ); 
		}

		/* 
		 * else if (msgGraTra == GraTraEvent.NO_COMPLETION) {
		 * //System.out.println("Rule : "+ ruleName+" ==> NO_COMPLETION"); }
		 * else if (msgGraTra == GraTraEvent.CANNOT_TRANSFORM){
		 * //System.out.println("Rule : "+ ruleName+" ==> CANNOT_TRANSFORM"); }
		 */
	}

	protected String getRuleNames(List<Rule> rules) {
		String names = "[ ";
		for (int j = 0; j < rules.size(); j++) {
			Rule r = rules.get(j);
			names = names + r.getName() + " ";
		}
		names = names + "]";
		return names;
	}
	
	void openTransformProtocol(String dirName, String name, String log) {
		String fName = name + "_GraTra"+log;
		String dName = dirName;
		if ((dName != null) && !dName.equals("")) {
			this.f = new File(dirName);
			if (this.f.exists()) {
				if (this.f.isFile()) {
					if (this.f.getParent() != null)
						dName = this.f.getParent() + File.separator;
					else
						dName = "." + File.separator;
				} else if (this.f.isDirectory()) {
					dName = this.f.getPath() + File.separator;
				} else
					dName = "." + File.separator;
			} else
				dName = "." + File.separator;
			this.f = new File(dirName + fName);
		} else
			this.f = new File(fName);

		try {
			this.os = new FileOutputStream(this.f);
			this.protocolFileName = this.f.getName();
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}

		writeTransformProtocol((new Date()).toString());
	}

	void prepareTransformProtocol() {
		String dirName = gragra.getDirName();
		String fName = gragra.getName();
		if ((fName == null) || fName.equals(""))
				fName = gragra.getName();
		if (this.writeLogFile) {
			String log = ".log";
			openTransformProtocol(dirName, fName, log);
			String version = "Version:  AGG " + Version.getID() + "\n";
			writeTransformProtocol(version);
			String s = "Graph Grammar : " + gragra.getName();
			writeTransformProtocol(s);
			s = getRuleNames(gragra.getListOfRules());			
			writeTransformProtocol(s);
			writeTransformProtocol("\n");					
		}
	}
	
	
	void writeTransformProtocol(String s) {
		if (this.os == null)
			return;
		try {
			if (!s.equals("\n"))
				this.os.write(s.getBytes());
			this.os.write('\n');
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	void closeTransformProtocol() {
		if (this.os == null)
			return;
		try {
			this.os.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

}
