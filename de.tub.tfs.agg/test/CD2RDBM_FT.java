

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.CompletionStrategySelector;
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
import agg.xt_basis.Step;
import agg.xt_basis.Version;
import agg.util.XMLHelper;

public class CD2RDBM_FT implements GraTraEventListener {

	long startTime = System.currentTimeMillis();
	
	long trafoStartTime = 0;
	long trafoEndTime = 0;

	
	static final XMLHelper h = new XMLHelper();

	final Step step = new Step();
	
	GraGra gragra;

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

	
	public CD2RDBM_FT(String filename) {
		this(filename, "1");
	}
	
	public CD2RDBM_FT(String filename, String nn) {
		NN = (Integer.valueOf(nn)).intValue();	
				
		fileName = filename;
		System.out.println("File name: " + fileName+"   iterations: "+NN);
		description();
		
		this.gragra = load(fileName);

		if (this.gragra != null) {

			prepareTransformProtocol();
			prepareTransform();
			
			transform(NN);
			
			String s = "\nUsed time: "+(System.currentTimeMillis()-this.startTime)+"ms";		
			System.out.println("\nTransformation time:"+(this.trafoEndTime-this.trafoStartTime));
			
			writeTransformProtocol(s);
			closeTransformProtocol();
			
			if (this.didTransformation) {
				if (outputFileName != null) {
					// save this.gragra					
					save(this.gragra, outputFileName);
					System.out.println("Output file: " + outputFileName);
				}
				if (this.writeLogFile)
					System.out.println("Protocol file: " + this.protocolFileName);
			} else
				System.out.println("Grammar:  " + filename //this.gragra.getName()
						+ "   could not perform any transformations!");
		} else
			System.out.println("Grammar:  " + filename + "   FAILED!");
		// close this.gragra
		
	}
	
	public static void main(String[] args) {
		if (args.length == 0) {
			CD2RDBM_FT.helpText();
		} else {
			handleInput(args);
			new CD2RDBM_FT(fileName, String.valueOf(NN));			
		}
	}

	static void handleInput(String[] args){
		String fn = "";
		NN = -1;
		for (int i=0; i<args.length; i++) {
			if(NN == -1) {
				String nn = args[i];
				try {
					NN = (Integer.valueOf(nn)).intValue();
					continue;
				} catch (NumberFormatException ex) {}
			}
			
			if (args[i].indexOf("-o") == 0) {
				if ((i + 1) < args.length) {
					i++;
					outputFileName = args[i];					
				}
				else
					outputFileName = "";
			}
			
			if(fn.equals("")) {
				if (args[i].endsWith(".ggx")) 
					fileName = args[i];
				else 
					fileName = args[i] + ".ggx";
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
			h.read_from_xml(fName);
			h.getTopObject(gra);
			gra.setFileName(fName);
			return gra;
		} else
			return null;
	}
	

	static void save(GraGra gra, String out) {
		if (outputFileName == null)
			return;
		
		if (out.equals(""))
			outputFileName = gra.getName() + "_out";
		
		if (!outputFileName.endsWith(".ggx")) 
			outputFileName = outputFileName + ".ggx";
		
		XMLHelper xmlh = new XMLHelper();
		xmlh.addTopObject(gra);
		xmlh.save_to_xml(outputFileName);
	}
	
	/**
	 * A subclass should replace this method
	 */
	void prepareTransform() {		
		if (this.gragra.getGraTraOptions().contains("ruleSequence")) {
			this.gratra = new RuleSequencesGraTraImpl();
			ruleSequence = true;
			System.out.println("Graph transformation by rule sequences ...");
		} else if (this.gragra.getGraTraOptions().contains("priority")) {
			this.gratra = new PriorityGraTraImpl();
			priority = true;
			System.out.println("Graph transformation by rule priority ...");
		} else if (this.gragra.getGraTraOptions().contains("layered")) {
			this.gratra = new LayeredGraTraImpl();
			layered = true;
			System.out.println("Layered graph transformation ...");
		} else {
			this.gratra = new DefaultGraTraImpl();
			System.out.println("Graph transformation  non-deterministically ...");
		}

		this.gragra.destroyAllMatches();
		
		this.gratra.setGraGra(this.gragra);
		this.gratra.setHostGraph(this.gragra.getGraph());

		MorphCompletionStrategy strategy = 
				CompletionStrategySelector.getDefault();
		
		if (this.gragra.getGraTraOptions().isEmpty()) {
			this.gragra.setGraTraOptions(strategy);
			this.gratra.setCompletionStrategy(strategy);
		} else if (this.gragra.getGraTraOptions().contains("showGraphAfterStep"))
				this.gragra.getGraTraOptions().remove("showGraphAfterStep");
		
		this.gratra.setGraTraOptions(this.gragra.getGraTraOptions());
//		System.out.println("Options:  " + this.gragra.getGraTraOptions());
//		System.out.println();

		addGraTraEventListener();		
	}

	/**
	 * A subclass should replace this method
	 */
	void addGraTraEventListener() {
		this.gratra.addGraTraListener(this);		
	}
	
	/**
	 * A subclass should replace this method
	 */
	void transform(int iterations) {

		for (int i=0; this.didTransformation && i<iterations; i++) {
			System.out.println("\n"+(i+1)+". iteration");
			this.didTransformation = false;
			doTransform();
			this.gratra.unsetStop();
		}
	}
	
	private void doTransform() {

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
			this.trafoStartTime=System.currentTimeMillis();
			((DefaultGraTraImpl) this.gratra).transform();
			this.trafoEndTime=System.currentTimeMillis();
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
		 * else if (this.msgGraTra == GraTraEvent.NO_COMPLETION) {
		 * //System.out.println("Rule : "+ ruleName+" ==> NO_COMPLETION"); }
		 * else if (this.msgGraTra == GraTraEvent.CANNOT_TRANSFORM){
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
		String dirName = this.gragra.getDirName();
		String fName = this.gragra.getName();
		if ((fName == null) || fName.equals(""))
				fName = this.gragra.getName();
		if (this.writeLogFile) {
			String log = ".log";
			openTransformProtocol(dirName, fName, log);
			String version = "Version:  AGG " + Version.getID() + "\n";
			writeTransformProtocol(version);
			String s = "Graph Grammar : " + this.gragra.getName();
			writeTransformProtocol(s);
			s = getRuleNames(this.gragra.getListOfRules());			
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
