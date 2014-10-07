package agg.cons;


import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.Node;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;

/**
 * This is a help class which can be used to define a boolean formula above some evaluable objects.
 * An evaluable object can be a graph constraint or a rule application condition.
 * They are the variables of a boolean formula. 
 * The possible operations are: AND, OR, NOT, FORALL. 
 * (FORALL is allowed for the rule application conditions only).<br>
 * 
 * In this class a formula is represented by a binary tree graph. 
 * The nodes are operations or names of the evaluable objects. 
 * The edges connect the nodes of a tree graph.<br>
 * 
 * An example how to use:<br>
 * <br>
 * FormulaGraph fg = new FormulaGraph();<br>
 * Node op = fg.setTop("AND");<br>                         
 * Node n1 = fg.connectAt(op, "AC1");<br>
 * op = fg.connectAt(op, "OR");<br>
 * fg.connectAt(op, "AC2");<br>
 * fg.connectAt(op, "AC3");<br>
 * fg.graph2formula();<br>
 * System.out.println("String by indx : "+fg.getFormulaTextByIndex());<br>
 * System.out.println("String by name : "+fg.getFormulaTextByName());<br>
 * <br>
 * Another possibility is to use the method <br>
 * <code>fg.connectAt(op, "AC1", evaluableObj);</code><br> 
 * where an Evaluable object should be defined. <br>
 * <code>fg.getFormula()</code> returns created Formula object.
 * 
 * @author olga
 *
 */
public class FormulaGraph {

	public static final String AND = "AND"; 
	public static final String OR = "OR";
	public static final String NOT = "NOT"; 
	public static final String FORALL = "FORALL";
	
	List<Evaluable> evals = new Vector<Evaluable>();
	List<String> vars = new Vector<String>();
	Formula formula;
	String fStr, fNameStr;
	
	Graph g;	
	Type not, and, or, forall, connectAt, refinedBy;	
	Node top;
	final HashMap<String,Type> name2type = new HashMap<String,Type>();
	final HashMap<String,Integer> name2indx = new HashMap<String,Integer>();	
	
	
	public FormulaGraph() {
		this.g = new Graph();
		createDefaultTypes(this.g.getTypeSet());
	}
	
	/*
	public FormulaGraph(List<Evaluable> evalObjs) {
		this();
		this.evals = evalObjs;
		if (this.evals != null) {
			this.vars = new Vector<String>();
			for (int i=0; i<this.evals.size(); i++) {
				this.vars.add(String.valueOf(i+1));
			}
			this.createTypes(this.vars);
		}
	}
	
	public FormulaGraph(List<Evaluable> evalObjs, List<String> objNames) {
		this();
		this.evals = evalObjs;
		this.vars = objNames;
		if (this.vars != null) {
			this.createTypes(this.vars);
		} 
		if (this.evals != null) {
			this.vars = new Vector<String>();
			for (int i=0; i<this.evals.size(); i++) {
				this.vars.add(String.valueOf(i+1));
			}
			this.createTypes(this.vars);
		}
	}
*/	
	
	private void createDefaultTypes(final TypeSet types) {
		// TODO create TRUE and FALSE node types
		
		// create node types which represent operators of a formula
		this.not = types.createNodeType(false);
		this.not.setStringRepr(FormulaGraph.NOT);
		//EditorConstants.ROUNDRECT, Color.RED);
		this.not.setAdditionalRepr(":ROUNDRECT:java.awt.Color[r=255,g=0,b=0]::[NODE]:"); 
		this.name2type.put(FormulaGraph.NOT, this.not);
		
		this.and = types.createNodeType(false);
		this.and.setStringRepr(FormulaGraph.AND);
		//EditorConstants.ROUNDRECT, Color.BLACK);
		this.and.setAdditionalRepr(":ROUNDRECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"); 
		this.name2type.put(FormulaGraph.AND, this.and);

		this.or = types.createNodeType(false);
		this.or.setStringRepr(FormulaGraph.OR);
		//EditorConstants.ROUNDRECT, Color.BLACK);
		this.or.setAdditionalRepr(":ROUNDRECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"); 
		this.name2type.put(FormulaGraph.OR, this.or);
				
		this.forall = types.createNodeType(false);
		this.forall.setStringRepr(FormulaGraph.FORALL);
		//EditorConstants.ROUNDRECT, Color.BLACK);
		this.forall.setAdditionalRepr(":ROUNDRECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"); 
		this.name2type.put(FormulaGraph.FORALL, this.forall);
		
		// create an edge type to connect nodes
		this.connectAt = types.createArcType(false);
		//EditorConstants.SOLID, Color.BLACK);	
		this.connectAt.setAdditionalRepr(":SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:"); 

		// create an edge type to refine nodes
		this.refinedBy = types.createArcType(false);
		//EditorConstants.SOLID, Color.BLUE);
		this.refinedBy.setAdditionalRepr(":SOLID_LINE:java.awt.Color[r=0,g=0,b=255]::[EDGE]:"); 
	}
	
	/**
	 *  Returns the graph.
	 */
	public Graph getGraph() {
		return this.g;
	}

	/**
	 *  Returns the generated Formula instance or null.
	 */
	public Formula getFormula() {
		return this.formula;
	}
	
	/**
	 * Returns a formula string containing indexes of variables (not the names!).
	 * This formula string can be used to create a new Formula instance.
	 */
	public String getFormulaTextByIndex() {
		if ("".equals(this.fStr))
			return "true";
		else
			return this.fStr;
	}
	
	/**
	 * Returns a formula string containing names of variables.
	 * Please note: This formula string can not be used 
	 * to create a new Formula instance.
	 * Use method <code>getFormulaTextByIndex()</code>.
	 */
	public String getFormulaTextByName() {
		if ("".equals(this.fStr))
			return "true";
		else
			return this.fNameStr;
	}	
	
	/**
	 * Set and returns the top object of the graph.
	 * The given String name is the name of an operation or an <code>Evaluable</code> object.<br>
	 * An operation is: <br>
	 * FormulaGraph.AND | FormulaGraph.OR | FormulaGraph.NOT | FormulaGraph.FORALL
	 */
	public Node setTop(final String name) {
		Type t = getNodeType(name);
		if (t != null) {
			try {
				this.top = this.g.createNode(t);
				return this.top;
			} catch (TypeException ex) {}
		}
		return null;
	}
	
	/**
	 * Set and returns the top object of the graph.
	 * The given String name is the name of an operation or an <code>Evaluable</code> object.
	 * In case of an operation the <code>Evaluable</code> object will be ignored.
	 * An operation is: <br>
	 * FormulaGraph.AND | FormulaGraph.OR | FormulaGraph.NOT | FormulaGraph.FORALL 
	 */
	public Node setTop(final String name, final Evaluable obj) {
		Type t = getNodeType(name);
		if (t != null) {
			try {
				this.top = this.g.createNode(t);
				if (obj != null && !this.isOpType(t)) {
					this.evals.add(obj);
				}
				return this.top;
			} catch (TypeException ex) {}
		}
		return null;
	}
	
	/**
	 * Adds and returns a new node of the graph.
	 * The given String name is the name of an operation or an <code>Evaluable</code> object.<br>
	 * A new node is connected to the specified Node node.
	 */
	public Node connectAt(final Node node, final String name) {
		Node n = null;
		if (node != null) {
			Type t = getNodeType(name);
			try {
				n = this.g.createNode(t);
				this.g.createArc(this.connectAt, node, n);
			} catch (TypeException ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}
		return n;
	}
	
	/**
	 * Adds and returns a new node of the graph.
	 * The given String name is the name of an operation or an <code>Evaluable</code> object.<br>
	 * In case of an operation the <code>Evaluable</code> object will be ignored.
	 * A new node is connected to the specified Node node.
	 */
	public Node connectAt(final Node node, final String name, final Evaluable obj) {
		Node n = null;
		if (node != null) {
			Type t = getNodeType(name);
			try {
				n = this.g.createNode(t);
				this.g.createArc(this.connectAt, node, n);
				if (obj != null && !this.isOpType(t)) {
					this.evals.add(obj);
				}
			} catch (TypeException ex) {
				System.out.println(ex.getLocalizedMessage());
			}
		}
		return n;
	}
	
	
	/**
	 * Updates the formula string and object representation of the formula graph.
	 * It has to be called before calling 
	 * <code>getFormulaTextByIndex()</code>,
	 * <code>getFormulaTextByName()</code>,
	 * <code>getFormula</code>.
	 */
	public void graph2formula() {
		if (this.top != null) {
			Pair<String,String> p = graph2text(this.top);
			this.fNameStr = p.first;
			this.fStr = p.second;
			
			if (this.evals.size() > 0)
				this.formula = new Formula(this.evals, p.second);
		} 
	}	
	
	private Type getNodeType(final String name) {
		Type t = this.name2type.get(name);
		if (t == null) {
			t = this.createType(name);
		}
		return t;
	}
	
	private Type createType(final String name) {
		Type t = this.g.getTypeSet().createNodeType(false);
		t.setStringRepr(name);
		t.setAdditionalRepr(":RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:"); 
		this.name2type.put(name, t);
		this.vars.add(name);
		this.name2indx.put(name, this.vars.size());
		return t;
	}
	
	/*
	private void createTypes(List<String> vars) {
		for (int i=0; i<vars.size(); i++) {
			String name = vars.get(i);
			Type t = this.name2type.get(name);
			if (t == null) {
				t = this.createType(name);
			}
		}
	}
	*/

	/**
	 * Converts recursively its graph to a formula string.<br>
	 * Result pair of strings contains two representations: 
	 * the first string contains the names,
	 * the second string - the indexes of the variables.
	 * The start node has to be the top node of the graph.
	 */
	private Pair<String,String> graph2text(final Node n) {
		Node n1 = null;			
		String s1 = "";
		String s2 = "";
		int c = n.getNumberOfOutgoingArcs();
		switch (c) {
		case 0:
			s1 = n.getType().getName();
			if (this.name2indx.get(n.getType().getName()) != null) {
				s2 = String.valueOf(this.name2indx.get(n.getType().getName()).intValue());
			} else {
				s2 = n.getType().getName();
			}
			break;
		case 1:
			if (n.getOutgoingArcs().next().getContextUsage() == -1) {
				s1 = n.getType().getName();
				if (this.name2indx.get(n.getType().getName()) != null) {
					s2 = String.valueOf(this.name2indx.get(n.getType().getName()).intValue());
				} else {
					s2 = n.getType().getName();
				}
				break;
			}
			
			n1 = (Node)n.getOutgoingArcs().next().getTarget();
			if (n.getType().getName().equals("NOT")) {
				Pair<String,String> p = graph2text(n1);
				s1 = " !"+p.first;
				s2 = " !"+p.second;
			}
			else if (n.getType().getName().equals("FORALL")) {
				Pair<String,String> p1 = graph2text(n1);
				s1 = " FORALL"+"("+p1.first+")";
				s2 = " A"+"("+p1.second+")";
			}
			else {
				Pair<String,String> p = graph2text(n1);
				s1 = n.getType().getName()+"("+p.first+")";
				s2 = n.getType().getName()+"("+p.second+")";
			}
			break;
		case 2:
			Iterator<Arc> outs = n.getOutgoingArcs();
			n1 = (Node)outs.next().getTarget();
			Node n2 = (Node)outs.next().getTarget();
			if (n.getType().getName().equals("AND")) {
				Pair<String,String> p1 = graph2text(n1);
				Pair<String,String> p2 = graph2text(n2);
				s1 = "("+p1.first+" & "+p2.first+")";
				s2 = "("+p1.second+" & "+p2.second+")";				
			}
			else if (n.getType().getName().equals("OR")) {
				Pair<String,String> p1 = graph2text(n1);
				Pair<String,String> p2 = graph2text(n2);
				s1 = "("+p1.first+" | "+p2.first+")";
				s2 = "("+p1.second+" | "+p2.second+")";
			}
			break;
		}		
		return new Pair<String,String>(s1, s2);
	}
	
	
//	private Node refineBy(final Node node, final String name) {
//		Node n = null;
//		if (node != null) {
//			Type t = getNodeType(name);
//			if (!isOpType(t)) {
//				try {
//					n = this.g.createNode(t);
//					this.g.createArc(this.refType, node, n);
//				} catch (TypeException ex) {}
//			}
//		}
//		return n;
//	}
	
	private boolean isOpType(final Type t) {
		return (t == this.and || t == this.or
					|| t == this.not || t == this.forall)? true : false;
		
	}

	/*
	public static void main(String argv[]) {		
		List<Evaluable> evalTest = new Vector<Evaluable>();
		evalTest.add(new AtomTest(1));	evalTest.add(new AtomTest(3));
		evalTest.add(new AtomTest(2));	evalTest.add(new AtomTest(4));	
		
		FormulaGraph fg = new FormulaGraph();
		Node op = fg.setTop("AND");
		
		Node n1 = fg.connectAt(op, "AC1");
		op = fg.connectAt(op, "OR");
		n1 = fg.connectAt(op, "AC2");
		op = fg.connectAt(op, "AND");
		fg.connectAt(op, "AC3");
		fg.connectAt(op, "AC4");
		
		// test with evaluable objects
//		Node n1 = fg.connectAt(op, "AC1", evalTest.get(0));
//		op = fg.connectAt(op, "OR");
//		n1 = fg.connectAt(op, "AC2", evalTest.get(1));
//		op = fg.connectAt(op, "AND");
//		fg.connectAt(op, "AC3", evalTest.get(2));
//		fg.connectAt(op, "AC4", evalTest.get(3));
		
		System.out.println(fg.g.showGraph());
		
		fg.graph2formula();		
		
		if (fg.formula != null)
			System.out.println("Formula (valid) : "+fg.formula.isValid()+"   "+fg.formula);
		System.out.println("by indx : "+fg.getFormulaTextByIndex());
		System.out.println("by name : "+fg.getFormulaTextByName());
	}
	

	static class AtomTest implements Evaluable {
		int val;

		public AtomTest(int i) {
			this.val = i;
		}

		public boolean eval(java.lang.Object o) {
			return this.val % 4 == 0;
		}

		public boolean eval(java.lang.Object o, int tick) {
			return tick % 4 == 0;
		}

		public boolean eval(java.lang.Object o, boolean negaition) {
			return this.val % 4 == 0;
		}

		public boolean eval(java.lang.Object o, int tick, boolean negaition) {
			return tick % 4 == 0;
		}

		public boolean evalForall(Object o, int tick) {
			return false;
		}
	}
	*/
}

