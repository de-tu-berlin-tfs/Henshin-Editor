//package org.tempuri;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
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
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.PriorityGraTraImpl;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleSequencesGraTraImpl;
import agg.xt_basis.StaticStep;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;
import agg.xt_basis.agt.AmalgamatedRule;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.MultiRule;
import agg.xt_basis.agt.RuleScheme;


public class aggEngine implements GraTraEventListener {

	private static XMLHelper h;
	private static GraGra gragra;
	private static GraTra gratra;
	private static boolean layered = false, ruleSequence = false, priority = false;
	private final MorphCompletionStrategy strategy = CompletionStrategySelector.getDefault();

	@SuppressWarnings("unused")
	private static boolean didTransformation = false;
	private static String fileName;
	private static String outputFileName;
	private static boolean writeLogFile = false;

	public List<VarMember> outPar;
	public Hashtable<String,String> outParVal = new Hashtable<String,String>();
	public List<List<String>> nodeStruct = new ArrayList<List<String>>();

	public aggEngine() {}

	@Override
	public void graTraEventOccurred(GraTraEvent arg0) {

	}

	/////////////
	
	public boolean aggEngineGetAll(String filename, String rulename) throws IOException {
		fileName = filename;
		System.out.println("File name:  " + fileName);
		gragra = load(fileName);
		
		if (gragra != null) {			
			
			//olga
			gragra.setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
			createGraTra();
				
			// olga: rule scheme
			Rule r = gragra.getRule(rulename);
			System.out.println("The selected rule was:" + r.getName());
			if (r instanceof RuleScheme) {
				RuleScheme rs = (RuleScheme) r;
				//System.out.println("Rule is a RuleScheme, Name: " + rs.getName());
					
				KernelRule kr = (KernelRule) rs.getKernelRule();					
				System.out.println("The kernel graph was: " + kr.getImage() + " The kernel rule has nodes: " + kr.getImage().getNodesCount());
					
				for (int j=0; j < rs.getMultiRules().size(); j++) {
					MultiRule mr = (MultiRule) rs.getMultiRules().get(j);
				}
					
				if (doStepOfAmalgamatedRule(rs, gragra.getGraph(), gragra.getMorphismCompletionStrategy())) {
					System.out.println("RuleScheme, Name: " + rs.getName()+"  was applied");					
				}
			}
		}
		return true;
	}
	
	//olga: RuleScheme for amalgamation
	public boolean doStepOfAmalgamatedRule(RuleScheme rs, Graph g, MorphCompletionStrategy s) {
		AmalgamatedRule amalgamRule = rs.getAmalgamatedRule(g, s);
		if (amalgamRule != null) 
		{				
			Match match = amalgamRule.getMatch();
			System.out.println("The match of the amalgamated rule is valid");
			try {
				Morphism co = StaticStep.execute(match);
				System.out.println("RuleScheme " + match.getRule().getName() + " : step is done");
				didTransformation = true;
				System.out.println();
				HashSet<Node> nodeList = co.getOriginal().getNodesSet();
				Node node = null;				
				Iterator<Node> iterator = nodeList.iterator();
				
				while (iterator.hasNext())
				{
					List<String> nodeString = new ArrayList<String>();
					// olga:: here it is important to use the image object of the output co morphism, 
					// the image node is an element of the host graph and contains only exact values
					// ( no any variables of the rule)
					node = (Node) co.getImage(iterator.next());
					for (int j = 0; j < node.getAttribute().getNumberOfEntries(); j++)
					{
						if (!(node.getAttribute().getValueAsString(j).equals("")))
							nodeString.add(node.getAttribute().getValueAsString(j));
					}
					nodeStruct.add(nodeString);
				}
				
				rs.disposeAmalgamatedRule();
				return true;
			} catch (TypeException ex) {
				ex.printStackTrace();
				System.out.println("RuleScheme  " + match.getRule().getName() + " : step of match failed : " + ex.getMessage());
				rs.disposeAmalgamatedRule();
			}
		}
		return false;
	}

	
	
	//////////////
	
	public boolean aggResult(String filename, String rulename, List<String> list) throws IOException
	{
		fileName = filename;
		System.out.println("File name:  " + fileName);
		gragra = load(fileName);
		boolean done = false;
		if (gragra != null) {			
			
			//olga
			gragra.setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
			createGraTra();
			int count = -1;
			String name = "";
			for (int i = 0; i < gragra.getListOfEnabledRules().size(); i++)
			{
				name = gragra.getListOfEnabledRules().get(i).getName();
				//System.out.println("NAME: " + name);
				if (name.equals(rulename))
				{
					count = i;
					break;
				}
			}
			
			//System.out.println("The name of the rule was: " + rulename + "The found index is: " + count);
			if (count != -1) 			
			{
				Rule rule = gragra.getListOfEnabledRules().get(count);
				//setPars(rule);
				int parResult = setParsList(rule, list);
				//System.out.println("The list: " + list.get(0));
				
				if (parResult != -1)
				{
					done = doStep(rule);
					if (done) {
						System.out.println("Match found, Step was performed ");
					}
					else 
					{
						System.out.println("Match not found, Step was not performed");
					}					
				}
				else 
				{								
					this.result = "-1";
					done = false;
				}
			}			
		}
		return done;
	}
	
	public boolean applyRule(String rulename, List<String> list ) 
	{
		boolean done = false;
		String name = "";
		int count = -1;
		System.out.println("The rulename is: " + rulename);
		for (int i = 0; i < gragra.getListOfEnabledRules().size(); i++)
		{
			name = gragra.getListOfEnabledRules().get(i).getName();
			//System.out.println("NAME: " + name);
			if (name.equals(rulename))
			{
				count = i;
				break;
			}
		}
		
		System.out.println("The name of the rule was: " + rulename + "The found index is: " + count);
		int ParSetting = 0;
		if (count != -1) 			
		{
			Rule rule = gragra.getListOfEnabledRules().get(count);
			//setPars(rule);
			try {
				System.out.println("I got up to here...");
				ParSetting = setParsList(rule, list);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				done = false;
				e.printStackTrace();
			}
			//System.out.println("The list: " + list.get(0));
			if (ParSetting != -1)
				done = doStep(rule);
			else {
				done = false;
				this.result = "-1";
			}
		}
		return done;
	}
	
	public aggEngine(String filename) throws IOException {
		fileName = filename;
		System.out.println("File name:  " + fileName);
		gragra = load(fileName);
		
		if (gragra != null) {			
			
			//olga
			gragra.setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
			createGraTra();
			
			for (int i = 0; i < gragra.getListOfEnabledRules().size(); i++)
			{
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

	void createGraTra() {
		createGraTraInstance(gragra);
		gratra.addGraTraListener(this);
		System.out.println("The gragra was set or not: " + gratra.setGraGra(gragra));
		
		gratra.getHostGraph();
	}
	
	public String result = null;
	
	public boolean doStep(Rule r)
	{
/*		Vector<String> gratraOptions = new Vector<String>();
		gratraOptions.add("CSP");
		gratraOptions.add("injective");
		gratraOptions.add("dangling");
		gratraOptions.add("NACs");
		gratraOptions.add("PACs");
		gratraOptions.add("GACs");
		gratraOptions.add("consistency");
		gragra.setGraTraOptions(gratraOptions);
*/		
		Match match = gratra.createMatch(r);
		//System.out.println("The match was: " + match.getRule().getName().toString());
//		gragra.setGraTraOptions(this.strategy);
//		match.setCompletionStrategy(this.strategy, true);
//		System.out.println(this.strategy);
//		this.strategy.showProperties();
		//olga
		while (match.nextCompletion()) 
		{
			if (match.isValid()) 
			{
				try {
					Morphism co = StaticStep.execute(match);
					System.out.println("\nRule  " + match.getRule().getName() + " : step is done");
					didTransformation = true;
					//Graph graph = co.getOriginal();
					//Graph graph2 = co.getImage();
					//System.out.println(co.getImage().showGraph());
					System.out.println();
					
					// olga
					outPar = getOutputParameters(r, outPar);
					System.out.println("The number of output parameters were: " + outPar.size());
					for (int i = 0; i<outPar.size(); i++) {
						VarMember p = outPar.get(i);
						String parVal = getValueOfOutputParameter(p, r, (OrdinaryMorphism) co);
						System.out.println("The result was: " + parVal);
						result = parVal;
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
		this.result = "-1";
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

	public int setParsList(Rule r, List<String> inList) throws IOException 
	{		
		int result = 0;
		int count = 0;
		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		System.out.println("The list had: " + inList.size() + " parameters");
		for (int j = 0; j < vars.getNumberOfEntries(); j++) 
		{
			VarMember var = (VarMember) vars.getMemberAt(j);
			if (var.isInputParameter()) {
				count++;
			}			
		}
		
		System.out.println("AGG expects: " + count + " parameters");
		
		for (int i = 0; i < inList.size(); i++)
		{
			System.out.println("The parameter: " + i + " was: " + inList.get(i));
		}
		
		if (inList.size() != count)
			return -1;
		else 
		{
			for (int i=0; i<vars.getNumberOfEntries();i++) {
				VarMember var = (VarMember) vars.getMemberAt(i);
				//System.out.println("number entries " + vars.getNumberOfEntries() + " var: " + vars.getMemberAt(i).getName() + " is input: " + var.isInputParameter() + " is Set: " + var.isSet());
				String value = null;
				if (var.isInputParameter()) {
					if (!var.isSet()) {
						//olga:: next line is not needed
//						var.setInputParameter(true);
						value = inList.get(i);						
						var.setExprAsText(value);
						result = 1;
					}
					//olga::
					else if (!var.getExprAsText().equals(value)) {
						result = -1;
						break;
					}
				}				
			}		
		}
		return result;
	}

	
	public int setParsList1(Rule r, List<String> inList) throws IOException 
	{		
		int result = 0;
		int count = 0;
		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		System.out.println("The list had: " + inList.size() + " parameters");
		for (int j = 0; j < vars.getNumberOfEntries(); j++) 
		{
			VarMember var = (VarMember) vars.getMemberAt(j);
			if (var.isInputParameter()) {
				count++;
			}			
		}
		
		System.out.println("AGG expects: " + count + " parameters");
		if (inList.size() != count)
			return 0;
		else 
		{
			for (int i=0; i<vars.getNumberOfEntries();i++) {
				VarMember var = (VarMember) vars.getMemberAt(i);
				String value = null;
				if (var.isInputParameter()) {
					if (!var.isSet()) {
						if (inList.get(i).contains("0") || inList.get(i).contains("1") || inList.get(i).contains("2") || inList.get(i).contains("3") || inList.get(i).contains("4") || inList.get(i).contains("5") || inList.get(i).contains("6") || inList.get(i).contains("7") || inList.get(i).contains("8") || inList.get(i).contains("9"))
							value = inList.get(i);
						else 
							value = "\"" + inList.get(i) + "\"";
						var.setInputParameter(true);
						var.setExprAsText(value);
						result = 1;
					}
					else {
						result = 0;
					}
				}				
			}		
		}
		return result;
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

