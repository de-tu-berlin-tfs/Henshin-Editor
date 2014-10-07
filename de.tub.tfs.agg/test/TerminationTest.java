/**
 * 
 */


//import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.termination.TerminationLGTS;
import agg.termination.TerminationLGTSInterface;
import agg.termination.TerminationLGTSTypedByTypeGraph;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Rule;
import agg.xt_basis.RuleLayer;
import agg.xt_basis.Type;
import agg.xt_basis.TypeSet;


/**
 * This test shows how to use the classes of the termination check in AGG. 
 * For graph grammars with a type set
 * the class <code>agg.termination.TerminationLGTS</code> should be used,
 * for graph grammars with a type graph - 
 * <code>agg.termination.TerminationLGTSTypedByTypeGraph</code>.
 * 
 * @author olga
 *
 */
public class TerminationTest {

	protected GraGra gragra;
	protected TerminationLGTSInterface terminationLGTS;
	protected Hashtable<Rule, Integer> ruleTable;
	
	/**
	 * 
	 */
	public TerminationTest(String fname) {
		// create an empty GraGra
		this.gragra = BaseFactory.theFactory().createGraGra();
		
		if (loadGGXfile(fname)) {
		
			// create an empty termination object
			if (this.gragra.getTypeGraph() != null
					&& this.gragra.getLevelOfTypeGraphCheck() > TypeSet.DISABLED) {
				this.terminationLGTS = new TerminationLGTSTypedByTypeGraph();
			} else {
				this.terminationLGTS = new TerminationLGTS();				
			} 
			
			// set gragra
			this.terminationLGTS.setGrammar(this.gragra);
			
			// and check
			checkTermination();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {		
		if (args.length == 1) {							
			new TerminationTest(args[0]);				
		} 
	}

	private void checkTermination() {
	    if (this.gragra.isLayered()) {
	    	
	    	// use given rule layers, do not generate rule layer automatically
	    	this.terminationLGTS.setGenerateRuleLayer(false);
	    	
			this.initRuleLayer();			
			
			if (this.terminationLGTS.checkTermination() 
					&& this.terminationLGTS.isValid()) {
				// what are termination results
				terminationResults();
				System.out.println("\n! "+ this.terminationLGTS.getClass().getName()+": Given GraGra satisfies Termination Conditions!");
			} else {
				System.out.println();
				System.out.println(this.terminationLGTS.getErrorMessage());	
				System.out.println("\nX "+ this.terminationLGTS.getClass().getName()+": Given GraGra doesn't satisfy Termination Conditions!");
			} 
	    }
	}
	
	private void initRuleLayer() {
		RuleLayer ruleLayer = new RuleLayer(this.terminationLGTS.getListOfRules());
		
		this.ruleTable = new Hashtable<Rule, Integer>();
		this.ruleTable.putAll(ruleLayer.getRuleLayer());
		
		this.terminationLGTS.initRuleLayer(this.ruleTable);
	}
	
	/**
	 * We have three result tables: 
	 * resultTypeDeletion, resultDeletion, resultNondeletion.
	 * Each (enabled) rule of the grammar should be inside at least of one of these tables.
	 * Each table is a Hashtable<Integer, Pair<Boolean, Vector<Rule>>>,
	 * where an Integer is a layer number,
	 * the first element of a Pair is TRUE or FALSE result
	 * and the second element is a Vector of Rules for this result.
	 */
	private void terminationResults() {
		// table maps a Type to a layer on which it is created
		Hashtable<Object, Integer> 
		creationLayer = this.terminationLGTS.getCreationLayer();
		
		// table maps a Type to a layer on which it is deleted
		Hashtable<Object, Integer> 
		deletionLayer = this.terminationLGTS.getDeletionLayer();
		
		Hashtable<Integer, Pair<Boolean, Vector<Rule>>> 
		resultTypeDeletion = this.terminationLGTS.getResultTypeDeletion();		
		
		Hashtable<Integer, Pair<Boolean, Vector<Rule>>> 
		resultDeletion = this.terminationLGTS.getResultDeletion();
		
		Hashtable<Integer, Pair<Boolean, Vector<Rule>>> 
		resultNondeletion = this.terminationLGTS.getResultNondeletion();
		
		System.out.println("====> Show results ...");
		
		Enumeration<?> types = creationLayer.keys();
		while (types.hasMoreElements()) {
			Object t = types.nextElement();
			Integer layer = null;
			if (t instanceof Type) {
				layer = creationLayer.get(t);
				System.out.println("Type Creation layer: "+layer+" : Type: "+((Type)t).getName());
			} else if (t instanceof GraphObject) {
				layer = creationLayer.get(t);
				if (((GraphObject)t).isNode()) {
					System.out.println("Type Creation layer: "+layer+" : Type: "
							+((GraphObject)t).getType().getName());
				} else {
					System.out.println("Type Creation layer: "+layer+" : Type: "
							+((Arc)t).getSourceType().getName()	+"--"
							+((GraphObject)t).getType().getName() 	+"->"
							+((Arc)t).getTargetType().getName());
				}
			}
			if (layer != null) {
				Vector<Rule> rules = resultTypeDeletion.get(layer).second;
				Vector<String> rnames = getRuleNames(rules);
				System.out.println("TypeDeletion: \n"+rnames);
				
				rules = resultDeletion.get(layer).second;
				rnames = getRuleNames(rules);
				System.out.println("Deletion: \n"+rnames);
				
				rules = resultNondeletion.get(layer).second;
				rnames = getRuleNames(rules);
				System.out.println("NonDeletion: \n"+rnames);
			}
		}
		System.out.println();
		
		types = deletionLayer.keys();
		while (types.hasMoreElements()) {
			Object t = types.nextElement();
			Integer layer = null;
			if (t instanceof Type) {
				layer = deletionLayer.get(t);			
				System.out.println("Type Deletion layer: "+layer+" : Type: "+((Type)t).getName());
			} else if (t instanceof GraphObject) {
				layer = deletionLayer.get(t);
				if (((GraphObject)t).isNode()) {
					System.out.println("Type Deletion layer: "+layer+" : Type: "
							+((GraphObject)t).getType().getName());
				} else {
					System.out.println("Type Deletion layer: "+layer+" : Type: "
							+((Arc)t).getSourceType().getName()	+"--"
							+((GraphObject)t).getType().getName() 	+"->"
							+((Arc)t).getTargetType().getName());
				}
			}
			if (layer != null) {
				Vector<Rule> rules = resultTypeDeletion.get(layer).second;
				Vector<String> rnames = getRuleNames(rules);
				System.out.println("TypeDeletion: \n"+rnames);
				
				rules = resultDeletion.get(layer).second;
				rnames = getRuleNames(rules);
				System.out.println("Deletion: \n"+rnames);
				
				rules = resultNondeletion.get(layer).second;
				rnames = getRuleNames(rules);
				System.out.println("NonDeletion: \n"+rnames);
			}
		}
	}
	
	private Vector<String> getRuleNames(final Vector<Rule> rules) {
		final Vector<String> names = new Vector<String>();
		for (int i=0; i<rules.size(); i++) {
			names.add(rules.get(i).getName());
		}
		return names;
	}
	
	
	private boolean loadGGXfile(String fname) {
		if (fname.indexOf(".ggx") != -1) {
			System.out.println("File to load:  " + fname);			
			try {
				this.gragra.load(fname);
			} catch (Exception ex) {
				System.out.println("Loading file failed. "+ex.getLocalizedMessage());
				return false;
			}
			return true;
		} 
		return false;
	}
	
}


