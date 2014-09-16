import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.util.XMLHelper;
import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.DefaultGraTraImpl;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTra;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.LayeredGraTraImpl;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Morphism;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.PriorityGraTraImpl;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleSequencesGraTraImpl;
import agg.xt_basis.StaticStep;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;
//import java.io.File;
//import java.io.BufferedReader;
//import java.util.Enumeration;
//import java.util.Iterator;
//import java.util.List;
//import org.omg.Dynamic.Parameter;
//import agg.xt_basis.BaseFactory;
//import agg.xt_basis.GraphObject;
//import agg.xt_basis.Node;
//import agg.xt_basis.Type;
//import agg.xt_basis.TypeGraph;
//import agg.xt_basis.GraTraEvent;
//import agg.xt_basis.GraTraEventListener;
//import agg.xt_basis.Match;
//import agg.ruleappl.RuleSequence;
//import agg.util.Pair;
//import agg.attribute.AttrContext;
//import agg.attribute.impl.CondTuple;
//import agg.attribute.impl.ValueMember;
//import agg.attribute.impl.ValueTuple;
//import agg.attribute.impl.DeclTuple;
//import agg.convert.ConverterXML;


public class aggEngineTest implements GraTraEventListener {

	private static XMLHelper h;

	private static GraGra gragra;

	//private static GraGra impGraGra;
	
	//private static Graph impGraph;
	
	//private static Graph impTypeGraph;
	
	private static GraTra gratra;

	//private int msgGraTra;

	private static boolean layered = false, ruleSequence = false, priority = false;;

	@SuppressWarnings("unused")
	private static boolean didTransformation = false;

	private static String fileName;

	//private static String impFileName;

	private static String outputFileName;

	//private static String error;

	private static boolean writeLogFile = false;

	List<VarMember> outPar;
	Hashtable<String,String> outParVal = new Hashtable<String,String>();
	
	
	public aggEngineTest() {}

	@Override
	public void graTraEventOccurred(GraTraEvent arg0) {
		// TODO Auto-generated method stub

	}

	public aggEngineTest(String filename) throws IOException {
		fileName = filename;
		System.out.println("File name:  " + fileName);
		gragra = load(fileName);
		
		if (gragra != null) {			
//			int levelOfTGcheck = gragra.getLevelOfTypeGraphCheck();			
//			if (gragra.getTypeSet().hasInheritance()) {
//				if (levelOfTGcheck != TypeSet.DISABLED)
//					gragra.setLevelOfTypeGraphCheck(levelOfTGcheck);
//				else
//					gragra.setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
//			} else {
//				gragra.setLevelOfTypeGraphCheck(levelOfTGcheck);
//			}
			
			//olga
			gragra.setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
			createGraTra();
			
			for (int i = 0; i < gragra.getListOfEnabledRules().size(); i++)
			{
				//System.out.println("Rule No. " + (i) + " Rule Name: " + gragra.getListOfRules().get(i).getName());
				System.out.println("Rule No. " + (i) + " Rule Name: " + gragra.getListOfEnabledRules().get(i).getName());
			}

			System.out.println("Total Number of rules are: "+gragra.getListOfEnabledRules().size());
			int[] sequence = readSeq();
			Rule[] rule;			
			rule = new Rule[sequence.length];
			boolean done;
			for (int i = 0; i < sequence.length; i++)
			{
				rule[i] = gragra.getListOfEnabledRules().get(sequence[i]);
				setPars(rule[i]);
				done = doStep(rule[i]);
				if (done) {
					System.out.println("Match found, Step was performed ");
					
					//olga
					if (i < sequence.length-1)
						propagateOutParamToInParamOfRule(outParVal, gragra.getListOfEnabledRules().get(sequence[i+1]));
				}
				else 
				{
					System.out.println("Match not found, Step was not performed");
					System.out.println("The transformation cannot continue...");
					break;
				}
				System.out.println("The parameters values for " + rule[i].getName() + " are: " + rule[i].getInputParameters());
			}
			System.out.println("\n\n End of program");
		}
	}

	//olga
	void createGraTra() {
		createGraTraInstance(gragra);
		gratra.addGraTraListener(this);
		System.out.println("The gragra was set or not: " + gratra.setGraGra(gragra));
		
		gratra.getHostGraph();
	}
	
	public boolean doStep(Rule r)
	{
		Match match = gratra.createMatch(r);
		System.out.println("The match was: " + match.getRule().getName().toString());
		
		
		//olga
		while (match.nextCompletion()) 
		{
			if (match.isValid()) 
			{
				try {
					Morphism co = StaticStep.execute(match);
					System.out.println("Rule  " + match.getRule().getName() + " : step is done");
					didTransformation = true;
					Graph graph = co.getOriginal();
					Graph graph2 = co.getImage();
					System.out.println();
//					System.out.println("The image parameters are: " + graph.toString());
//					System.out.println("The OUTPUT parameters are: " + graph2.toString());
					
					// olga
					outPar = getOutputParameters(r, outPar);
					for (int i = 0; i<outPar.size(); i++) {
						VarMember p = outPar.get(i);
						String parVal = getValueOfOutputParameter(p, r, (OrdinaryMorphism) co);
						this.outParVal.put(p.getName(), parVal);
					}
					
					((OrdinaryMorphism)co).dispose();
					return true;
				} catch (TypeException ex) {
					ex.printStackTrace();
					gragra.destroyMatch(match);
					System.out.println("Rule  " + match.getRule().getName() + " : step of match failed : " + ex.getMessage());
				}
			} 
			else
				System.out.println("Rule  " + match.getRule().getName() + " : a match completion is not valid; try to find the next ones");
		}
		System.out.println("Rule  " + match.getRule().getName() + " : match could not be found");
		return false;
		//gragra.destroyMatch(match);
		// match.clear();
	}
	
	public int[] readSeq()
	{
		System.out.println("Please Enter the size of the sequence: ");
		Scanner in = new Scanner(System.in); 
		int size = in.nextInt(); 
		System.out.println("The size you entered is " + size); 
		if (size > 0)
		{
			int[] seq = new int[size]; 
			System.out.println("Enter the array: ");   
			int j = 0; 
			while (j < size) 
			{     
				System.out.print("Enter Rule No. "+ (j+1) + ": ");     
				seq[j] = in.nextInt();     
				++j; 
			} 
			//in.close();
			return seq;
		}
		else
		{
			System.out.println("The size of the sequence is not appropriate");
			int[] seq = null;
			return seq ;
		}					
	}
	
	void setPars(Rule r) throws IOException 
	{
		System.out.println("\n TAKING INPUTS to APPLY Rule: " + r.getName() + "\n");
		List<String> list = r.getInputParameterNames();
		System.out.println("The parameters for " + r.getName() + " are: " + list);
//		System.out.println("The parameters values for " + r.getName() + " are: " + r.getInputParameters());
		System.out.println("The parameters values:");
		for (int i=0; i<list.size(); i++) {
			String  pname = list.get(i);
			VarMember var = (VarMember) ((VarTuple) r.getAttrContext().getVariables()).getMemberAt(pname);
			if (var.isSet())
				System.out.println(pname+" is set to value: " +var.getExprAsText());
			else
				System.out.println(pname+" is not set");
		}

		
		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		
		for (int i=0; i<vars.getNumberOfEntries(); i++) {
			VarMember var = (VarMember) vars.getMemberAt(i);
			if (var.isInputParameter()) {
					//olga: 
				if (!var.isSet()) {
					System.out.println(var.getName()+"  is already input parameter of rule: "+r.getName());
					
					System.out.println("\n \n Please Enter the value for input parameter for rule -- " + r.getName() + " --, parameter name -- " + var.getName() + " --: ");
					Scanner scan = new Scanner(System.in);
					String value = scan.nextLine();
					System.out.println("The given input was: " + value);
					var.setInputParameter(true);
					var.setExprAsText(value);
					//in1.close();
					System.out.println(var.getName()+"  is now input parameter of rule: "+r.getName());
				}
				else {
					System.out.println(var.getExprAsText()+" is value of the INPUT parameter: "+var.getName()+"  of rule: "+r.getName());
				}
			}				
		}		
		//in.close();
	}
	
	
	void setInputParameter(Rule r) {
		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		for (int i=0; i<vars.getNumberOfEntries(); i++) {
			VarMember var = (VarMember) vars.getMemberAt(i);
			if (var.isInputParameter())
				System.out.println(var.getName()+"  is already input parameter of rule: "+r.getName());
			else {
				// this var should be an input parameter
				var.setInputParameter(true);
				System.out.println(var.getName()+"  is now input parameter of rule: "+r.getName());
			}				
		}		
	}
	
	
	// olga
	List<VarMember> getOutputParameters(Rule r, List<VarMember> list) {
		if (list == null)
			list = new Vector<VarMember>();
		else
			list.clear();
		
		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		for (int i=0; i<vars.getNumberOfEntries(); i++) {
			VarMember var = (VarMember) vars.getMemberAt(i);
			if (var.isOutputParameter() ) {
				System.out.println(var.getName()+"  is OUTPUT parameter of rule: "+r.getName());
				list.add(var);
			}
//			else {
				// if this var should be an output parameter
//				var.setOutputParameter(true);
//				System.out.println(var.getName()+"  is now OUTPUT parameter of rule: "+r.getName());
//			}
		}
		return list;
	}
	
	//olga: propagate value of OUTPUT parameters of rule r1 to INPUT parameters of rule r2
	void propagateOutParamToInParamOfRule(Hashtable<String,String> outPars, Rule r) {
		if (outPars != null && !outPars.isEmpty()) {
			VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
			for (int i = 0; i<vars.getNumberOfEntries(); i++) {
				VarMember var = vars.getVarMemberAt(i);
				String outVal = outPars.get(var.getName());
				if (outVal != null && var.isInputParameter()) {
					var.setExprAsText(outVal);
				}
			}
		}
	}
	
	// olga: use comatch after step ia done to get values of OUTPUT parameters of a rule
	String getValueOfOutputParameter(VarMember p, Rule r, OrdinaryMorphism comatch) {
		if (p != null && r != null && comatch != null) {		
			Enumeration<GraphObject> rightObjs = comatch.getDomain();
			while (rightObjs.hasMoreElements()) {
				GraphObject obj = rightObjs.nextElement();
				if (obj.getAttribute() != null) {
					ValueTuple vt = (ValueTuple) obj.getAttribute();
					for (int i=0; i<vt.getNumberOfEntries(); i++) {
						ValueMember vm = vt.getEntryAt(i);
						if (vm.isSet() && vm.getExpr().isVariable()
								&& vm.getExprAsText().equals(p.getName())) {
							// we found an object obj inside of the RHS which uses the output parameter p,
							// now we will find an appropriate graph object 
							// and to get the value of the output parameter
							GraphObject go = comatch.getImage(obj);
							ValueTuple vt_go = (ValueTuple) go.getAttribute();
							ValueMember vm_go = vt_go.getEntryAt(vm.getName());
							String parVal = vm_go.getExprAsText();
							System.out.println(parVal+"  is value of OUTPUT parameter: --"+p.getName()+"--  of rule: "+r.getName());
							return parVal;
						}
					}
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) throws IOException {
		String vers = System.getProperty("java.version");
		if (vers.compareTo("1.4.2") < 0) {
			System.out.println("WARNING : Swing must be run with the "
					+ "1.4.2 version of the JVM.");
		}
		// new aggEngineTest("C:\\localapp\\aggEngine_V202\\Examples_V164\\BasisUsing\\calcWithPar.ggx");
//		 new aggEngineTest("C:\\localapp\\aggEngine_V202\\Examples_V164\\BasisUsing\\gtvmtExample.ggx");	
		 
		 // olga
		 new aggEngineTest("C:\\Users\\olga\\Desktop\\Tamim\\gtvmtExample.ggx");		
	}
	
	public static GraGra load(String fName) {
		if (fName.endsWith(".ggx")) {
			h = new XMLHelper();
			if (h.read_from_xml(fName)) {
	
				// create a gragra
				GraGra gra =  new GraGra(true);
				h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			} 
			return null;
		} 
		return null;
	}	

	static void createGraTraInstance(GraGra grammar) {
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
	}
	
	public static void transform(GraGra grammar, GraTraEventListener l) {
		if (grammar == null)
			return;
		
		System.out.println(grammar.getGraTraOptions().toString());
		createGraTraInstance(grammar);

		gratra.addGraTraListener(l);
		gratra.setGraGra(grammar);
		gratra.setHostGraph(grammar.getGraph());
		gratra.enableWriteLogFile(writeLogFile);

		MorphCompletionStrategy strategy = CompletionStrategySelector.getDefault();
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

		if (priority)
			((PriorityGraTraImpl) gratra).transform();
		else if (layered)
			((LayeredGraTraImpl) gratra).transform();
		else if (ruleSequence)
			((RuleSequencesGraTraImpl) gratra).transform();
		else
			((DefaultGraTraImpl) gratra).transform();
	}

	public static void save(GraGra gra, String outFileName) {
		if (outFileName.equals(""))
			outputFileName = gra.getName() + "_out.ggx";
		else if (outFileName.equals("_out.ggx"))
			outputFileName = fileName.substring(0, fileName.length() - 4) + "_out.ggx";
		else if (outFileName.indexOf(".ggx") == -1)
			outputFileName = outFileName.concat(".ggx");
		else if (outFileName.equals(fileName))
			outputFileName = fileName.substring(0, fileName.length() - 4) + "_out.ggx";
		else {
			outputFileName = outFileName;
		}
		if (outputFileName.endsWith(".ggx")) {
			XMLHelper xmlh = new XMLHelper();
			xmlh.addTopObject(gra);
			xmlh.save_to_xml(outputFileName);
		}

	}
}
