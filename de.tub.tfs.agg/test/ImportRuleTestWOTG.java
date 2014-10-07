

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrConditionMember;
import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrContext;
import agg.attribute.AttrInstance;
import agg.attribute.AttrType;
import agg.attribute.AttrTypeMember;
import agg.attribute.facade.impl.DefaultInformationFacade;
import agg.attribute.handler.AttrHandler;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.cons.AtomConstraint;
import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.CompletionStrategySelector;
import agg.xt_basis.DefaultGraTraImpl;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraTraEventListener;
import agg.xt_basis.GraTraOptions;
import agg.xt_basis.Graph;
import agg.xt_basis.LayeredGraTraImpl;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Morphism;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.StaticStep;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
//import agg.xt_basis.GraphObject;
//import agg.attribute.AttrInstanceMember;

/**
 * This class shows in generally usage of basic features of the AGG system and
 * import rules.
 * 
 * Usage: compile if necessary: javac test/ImportRuleTest.java run: java
 * test.ImportRuleTest Output files: Lovers_Rules.ggx, Lovers_Graph.ggx,
 * Lovers_Graph_layered.ggx, Lovers_Graph_trans.ggx
 */
public class ImportRuleTestWOTG implements GraTraEventListener {

	public ImportRuleTestWOTG() {

		createGraGraGraph();
		createGraGraRules();
		if (gragraGraphImportRules())
			gragraGraphTransform();
	}

	public static void main(String[] args) {
		ImportRuleTestWOTG importRuleTest = new ImportRuleTestWOTG();
	}

	/** Implements GraTraEventListener.graTraEventOccurred */
	public void graTraEventOccurred(GraTraEvent e) {
		// System.out.println("graTraEventOccurred "+e.getMessage());
		String ruleName = "";
		int msgGraTra = e.getMessage();
		if ((msgGraTra == GraTraEvent.INPUT_PARAMETER_NOT_SET)) {
		} else if ((msgGraTra == GraTraEvent.STEP_COMPLETED)) {
			runsCount++;
			ruleName = e.getMatch().getRule().getName();
			System.out.println(ruleName + "  is applied");

			showGraph(gragraGraph);

			System.out.println("Save transformed gragraGraph");
			String fn = gragraGraph.getName() + "_trans.ggx";
			gragraGraph.save(fn);
			System.out.println("runs: " + runsCount + "  Saved in  " + fn);

			if (runsCount == runsMax)
				gratraLayered.stop();
		} else if (msgGraTra == GraTraEvent.NO_COMPLETION) {
			ruleName = e.getMatch().getRule().getName();
			System.out.println(ruleName + "  has no more completion");
		} else if (msgGraTra == GraTraEvent.CANNOT_TRANSFORM) {
			ruleName = e.getMatch().getRule().getName();
			System.out.println(ruleName + "  cannot transform");
		} else if (msgGraTra == GraTraEvent.TRANSFORM_FINISHED) {
			System.out.println("Transformation is finished");
		}
	}

	private void createGraGraGraph() {
		System.out.println("Create gragraGraph  <Lovers-Graph> ...");
		gragraGraph = this.bf.createGraGra();
		gragraGraph.setName("Lovers_Graph");

		// Start graph
		Graph graph = gragraGraph.getGraph();
		graph.setName("HostGraph");
//		String graphName = graph.getName();
		/** Typing of gragraGraph */
		// Create a type with attr for nodes
		Type nodeType = gragraGraph.createNodeType(true);
		// Create a type without attr for edges
		Type arcType = gragraGraph.createArcType(false);

		nodeType.setStringRepr("Person");
		nodeType.setAdditionalRepr("[NODE]");
		String nodeTypeName = nodeType.getStringRepr();
		// String nodeAddRepr = nodeType.getAdditionalRepr();

		arcType.setStringRepr("loves");
		arcType.setAdditionalRepr("[EDGE]");
//		String arcTypeName = arcType.getStringRepr();
		// String arcAddRepr = arcType.getAdditionalRepr();

		/** Add type attributes */
		// Get attribute type of the node type
		AttrType attrType = nodeType.getAttrType();
		// Add attribute members
		AttrTypeMember memberName = attrType.addMember(this.javaHandler, "String",
				"name");
		AttrTypeMember memberAge = attrType
				.addMember(this.javaHandler, "int", "age");
		AttrTypeMember memberSex = attrType.addMember(this.javaHandler, "String",
				"sex");

		System.out.println(nodeTypeName + " : " + "["
				+ memberName.getTypeName() + "  " + memberName.getName() + "]"
				+ "[" + memberAge.getTypeName() + "  " + memberAge.getName()
				+ "]" + "[" + memberSex.getTypeName() + "  "
				+ memberSex.getName() + "]");

		// Get all defined types: element is Type
//		Enumeration<Type> types = gragraGraph.getTypes();

		/*
		 * // Create type graph Graph typeGraph =
		 * gragraGraph.getTypeSet().createTypeGraph();
		 * typeGraph.setName("TypeGraph"); // Create a type node Node nodeOfTG =
		 * null; try { nodeOfTG = typeGraph.createNode(nodeType); }
		 * catch(TypeException ex) { ex.printStackTrace(); } // Create a type
		 * edge with source and target by type node Arc edgeOfTG = null; try {
		 * edgeOfTG = typeGraph.createArc(arcType, (Node)nodeOfTG,
		 * (Node)nodeOfTG); } catch(TypeException ex) { ex.printStackTrace(); } //
		 * check the type graph against the types Collection errors =
		 * gragraGraph.getTypeSet().checkTypeGraph(); if(!errors.isEmpty())
		 * System.out.println("WARNING! Type graph of gragraGraph isn't
		 * correct"); // Set source multiplicity of the type edge
		 * arcType.setSourceMin(nodeType, nodeType, 0); // min multiplicity is 0
		 * arcType.setSourceMax(nodeType, nodeType, Type.UNDEFINED); // max
		 * multiplicity is * // Set target multiplicity of the type edge type
		 * arcType.setTargetMin(nodeType, nodeType, 0); // min multiplicity is 0
		 * arcType.setTargetMax(nodeType, nodeType, 1); // max multiplicity is 1 //
		 * System.out.println(">>> arctype target min :
		 * "+arcType.getTargetMin(nodeType, nodeType)); //
		 * System.out.println(">>> arctype target max :
		 * "+arcType.getTargetMax(nodeType, nodeType));
		 * 
		 * System.out.println("The type graph of gragraGraph is done"); //
		 * enable level of type graph check TypeSet.ENABLED_MAX: // the type
		 * graph is basicaly used, so all graphs can // only contain objects
		 * with types defined in the type graph. // Multiplicities in all graphs
		 * should satisfy max constraints.
		 * gragraGraph.getTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX); //
		 * another levels: //
		 * gragraGraph.getTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX_MIN); //
		 * gragraGraph.getTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED); //
		 * gragraGraph.getTypeSet().setLevelOfTypeGraphCheck(TypeSet.DISABLED);
		 */

		/** Create objects of the start graph */

		// Create nodes of the nodeType
		Node node1 = null, node2 = null, node3 = null, node4 = null;

		try {
			node1 = graph.createNode(nodeType);
		} catch (TypeException ex) {
			System.out.println("Create node1 failed! " + ex.getMessage());
		}

		try {
			node2 = graph.createNode(nodeType);
		} catch (TypeException ex) {
			System.out.println("Create node2 failed! " + ex.getMessage());
		}

		// Set attribute values
		AttrInstance attrInstance = null;
		ValueTuple vt = null;
		ValueMember vm = null;

		String name = "";
		String age = "";
		String sex = "";
		if (node1 != null) {
			attrInstance = node1.getAttribute();
			// Set values of attributes
			vt = (ValueTuple) attrInstance;
			vm = (ValueMember) vt.getMemberAt("name");
			name = "Otti";
			vm.setExprAsObject(name);
			vm = (ValueMember) vt.getMemberAt("age");
			age = "13";
			vm.setExprAsEvaluatedText(age);
			vm = (ValueMember) vt.getMemberAt("sex");
			sex = "m";
			vm.setExprAsObject(sex);
			vm.checkValidity();
		}
		if (node2 != null) {
			attrInstance = node2.getAttribute();
			vt = (ValueTuple) attrInstance;
			vm = (ValueMember) vt.getMemberAt("name");
			name = "Mary";
			vm.setExprAsObject(name);
			vm = (ValueMember) vt.getMemberAt("age");
			age = "10";
			vm.setExprAsEvaluatedText(age);
			vm = (ValueMember) vt.getMemberAt("sex");
			sex = "f";
			vm.setExprAsObject(sex);
			vm.checkValidity();
		}

		// Get a member of an attribute instance
		if (attrInstance != null) {
			// Get value tuple of an attribute instance
			vt = (ValueTuple) attrInstance;
			// Get a member of an attribute instance
//			AttrInstanceMember am = (AttrInstanceMember) attrInstance.getMemberAt("name");
			// Get value of an attribute member
			vm = (ValueMember) vt.getMemberAt("name");
		}

		// Create node3 by copy of node1
		try {
			node3 = graph.copyNode(node1);
		} catch (TypeException ex) {
			System.out.println("Create node3 failed! " + ex.getMessage());
		}

		// Set values of attribute members
		if (node3 != null) {
			attrInstance = node3.getAttribute();
			vt = (ValueTuple) attrInstance;
			vm = (ValueMember) vt.getMemberAt("name");
			name = "Lotti";
			vm.setExprAsObject(name);
			vm = (ValueMember) vt.getMemberAt("age");
			age = "50";
			vm.setExprAsEvaluatedText(age);
			vm = (ValueMember) vt.getMemberAt("sex");
			sex = "f";
			vm.setExprAsObject(sex);
			vm.checkValidity();
		}

		// Create node4 by copy of node2
		try {
			node4 = graph.copyNode(node2);
		} catch (TypeException ex) {
			System.out.println("Create node4 failed! " + ex.getMessage());
		}

		// Set values of attribute members
		if (node4 != null) {
			attrInstance = node4.getAttribute();
			vt = (ValueTuple) attrInstance;
			vm = (ValueMember) vt.getMemberAt("name");
			name = "Hans";
			vm.setExprAsObject(name);
			vm = (ValueMember) vt.getMemberAt("age");
			age = "60";
			vm.setExprAsEvaluatedText(age);
			vm = (ValueMember) vt.getMemberAt("sex");
			sex = "m";
			vm.setExprAsObject(sex);
			vm.checkValidity();
		}

		// Show start graph
		System.out.println("Graph:");
		Iterator<Node> en = graph.getNodesSet().iterator();
		while (en.hasNext()) {
			Node n = en.next();
			System.out.println(n.getAttribute().getValueAt("name") + "   "
					+ n.getAttribute().getValueAt("age") + "   "
					+ n.getAttribute().getValueAt("sex"));
		}

		// Destroy node1 - an example of usage
		// graph.destroyNode(node1);

		// Get nodes: elements of GraphObject
//		Enumeration<Node> nodes = graph.getNodes();

		// Get edges: elements of GraphObject
//		Enumeration<Arc> arcs = graph.getArcs();

		// Get all elements
//		Enumeration<GraphObject> elems = graph.getElements();

		gragraGraph.setGraTraOptions(this.strategy);

		/** Usage of graph constraints */
		System.out
				.println("\nCreate an atomic graphical consistency constraint (AGCC) of the grammar");
		// Class AtomConstraint is a subclass of the class OrdinaryMorphism
		AtomConstraint atomicGCC = gragraGraph.createAtomic("AtomicGCC");
		atomicGCC.setName("opposed");
		// atomicGCC.setMorphismCompletionStrategy(this.strategy);
		// This AGCC miens: a man loves a woman or otherwise.

		Node nl1 = null, nl2 = null, nr1 = null, nr2 = null;
		Arc al = null, ar = null;
		// Nodes, edges and morphism mapping are created similar to the rule
		// objects
		try {
			nl1 = atomicGCC.getSource().createNode(nodeType);
			nl2 = atomicGCC.getSource().createNode(nodeType);
			al = atomicGCC.getSource().createArc(arcType, nl1, nl2);
			nr1 = atomicGCC.getTarget().createNode(nodeType);
			nr2 = atomicGCC.getTarget().createNode(nodeType);
			ar = atomicGCC.getTarget().createArc(arcType, nr1, nr2);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}

		/* set attribute of atomic */
		ContextView aContext = (ContextView) atomicGCC.getAttrContext();
		VarTuple aVarTuple = (VarTuple) aContext.getVariables();

		// Define variables of type String
		String varTypeString = "String";
		String avarSex = "s";
		String avarSex1 = "s1";
		if (!aVarTuple.isDeclared(avarSex))
			aVarTuple.declare(this.javaHandler, varTypeString, avarSex);
		if (!aVarTuple.isDeclared(avarSex1))
			aVarTuple.declare(this.javaHandler, varTypeString, avarSex1);

		/*
		 * attrInstance = nl1.getAttribute(); attrInstance.setExprAt(avarSex,
		 * "sex"); // System.out.println(attrInstance);
		 * 
		 * attrInstance = nl2.getAttribute(); attrInstance.setExprAt(avarSex1,
		 * "sex"); // System.out.println(attrInstance);
		 */

		if (nr1 != null) {
		attrInstance = nr1.getAttribute();
		attrInstance.setExprAt(avarSex, "sex");
		// System.out.println(attrInstance);
		}
		if (nr2 != null) {
		attrInstance = nr2.getAttribute();
		attrInstance.setExprAt(avarSex1, "sex");
		// System.out.println(attrInstance);
		}
		// The morphism of AtomicGCC has to be total and injective
		if (nl1 != null && nr1 != null) {
			atomicGCC.addMapping(nl1, nr1);
		}
		if (nl2 != null && nr2 != null) {
			atomicGCC.addMapping(nl2, nr2);
		}
		if (al != null && ar != null) {
			atomicGCC.addMapping(al, ar);
		}

		// define attr. condition of atomic
		AttrConditionTuple acondTuple = aContext.getConditions();
		String aexpr = "!s.equals(s1)";
		acondTuple.addCondition(aexpr);

		// Check validity of atomic
		if (atomicGCC.isValid())
			System.out.println("AGCC  \"" + atomicGCC.getName()
					+ "\"  is  valid");
		else
			System.out.println("AGCC  \"" + atomicGCC.getName()
					+ "\"  is NOT  valid");

		// Check if the host graph of the grammar fulfills the AGCC
		if (atomicGCC.eval(gragraGraph.getGraph()))
			System.out.println("Start graph fulfills AGCC");
		else
			System.out.println("Start graph does not fulfill AGCC");

		// Get AGCCs of the grammar
//		Enumeration<AtomConstraint> atomics = gragraGraph.getAtomics();

		// Get AGCCs of the grammar as a Vector that can be used by a formula
		List<Evaluable> atomicsVec = gragraGraph.getListOfAtomicObjects();

		// Destroy an atomicGC - an example of usage
		// gragraGraph.destroyAtomic(atomicGC);

		// Create a formula above AGCCs
		Formula formula = gragraGraph.createConstraint("Formula");
		// Set formula's variables and operators
		String f = "1";
		formula.setFormula(atomicsVec, f);
		// Note: 1 is element atomicsVec[0]
		// This formula demands the AGCC "opposed".

		// An example with possible junctors:
		// Note: 1 is element atomicsVec[0],
		// 2 is element atomicsVec[1] and 3 is element atomicsVec[2]
		// String f = "!1 && (2 || 3)";

		// Check if defined formula is valid
		if (formula.isValid())
			System.out.println("Formula : " + f + " is valid");
		else
			System.out.println("Formula : " + f + " is not valid");

		// Check if the host graph of the grammar fulfills graph constraints
		// (formulas)
		if (gragraGraph.checkGraphConstraints(true))
			System.out.println("Start graph fulfills graph constraints");
		else
			System.out
					.println("Start graph does not fulfill graph constraints");

		// Get constraints (formulae) of the grammar
//		Enumeration<Formula> formulae = gragraGraph.getConstraints();

		// Destroy the formula
		// gragraGraph.destroyConstraint(formula);

		// Before transforming grammar please check whether it ready to
		// transform.
		// There would be checked: attribute settings in the LHS of the rules
		// and their attribute context: variables and attribute conditions.
		// Also marking of the variables and attribute conditions will be done
		// dependent of the usage (LHS, RHS, NAC),
		// that is needed for validation of conditions.
		// The method GraGra.isReadyToTransform() returns the failed rule.
		Pair<Object, String> pair = gragraGraph.isReadyToTransform();
		Object test = null;
		if (pair != null)
			test = pair.first;
		if (test != null) {
			if (test instanceof Graph) {
				System.out.println("Grammar  " + gragraGraph.getName()
						+ "  graph: " + gragraGraph.getGraph().getName()
						+ "  is not ready for transform");
			} else if (test instanceof AtomConstraint) {
				String s0 = "Atomic graph constraint  \""
						+ ((AtomConstraint) test).getAtomicName()
						+ "\" is not valid. "
						+ "\nPlease check: "
						+ "\n  - graph morphism ( injective and total )  "
						+ "\n  - attribute context ( variable and condition declarations ).";
				System.out.println(s0);
			}
		} else
			System.out.println("Grammar  " + gragraGraph.getName()
					+ "  is ready to transform");

	}

	private void createGraGraRules() {
		System.out.println("Create gragraRules  <Lovers-Rules> ...");
		gragraRules = this.bf.createGraGra();
		gragraRules.setName("Lovers_Rules");

		/** Typing of gragraRules */
		Type nodeType = gragraRules.createNodeType(true);
		Type arcType = gragraRules.createArcType(false);

		nodeType.setStringRepr("Person");
		nodeType.setAdditionalRepr("[NODE]");

		arcType.setStringRepr("loves");
		arcType.setAdditionalRepr("[EDGE]");

		/** Add type attributes */
		AttrType attrType = nodeType.getAttrType();
		AttrTypeMember memberName = attrType.addMember(this.javaHandler, "String",
				"name");
		AttrTypeMember memberAge = attrType
				.addMember(this.javaHandler, "int", "age");
		AttrTypeMember memberSex = attrType.addMember(this.javaHandler, "String",
				"sex");

		System.out.println(nodeType.getName() + " : " + "["
				+ memberName.getTypeName() + "  " + memberName.getName() + "]"
				+ "[" + memberAge.getTypeName() + "  " + memberAge.getName()
				+ "]" + "[" + memberSex.getTypeName() + "  "
				+ memberSex.getName() + "]");

		/*
		 * // Create a type graph Graph typeGraph =
		 * gragraRules.getTypeSet().createTypeGraph();
		 * typeGraph.setName("TypeGraph");
		 * 
		 * Node nodeOfTG = null; try { nodeOfTG =
		 * typeGraph.createNode(nodeType); } catch(TypeException ex) {
		 * ex.printStackTrace(); }
		 * 
		 * Arc edgeOfTG = null; try { edgeOfTG = typeGraph.createArc(arcType,
		 * (Node)nodeOfTG, (Node)nodeOfTG); } catch(TypeException ex) {
		 * ex.printStackTrace(); }
		 * 
		 * Collection errors = gragraRules.getTypeSet().checkTypeGraph();
		 * if(!errors.isEmpty()) System.out.println("WARNING! Type graph of
		 * gragraRules is not correct");
		 * 
		 * arcType.setSourceMin(nodeType, nodeType, 0); // min multiplicity is 0
		 * arcType.setSourceMax(nodeType, nodeType, Type.UNDEFINED); // max
		 * multiplicity is *
		 * 
		 * arcType.setTargetMin(nodeType, nodeType, 0); // min multiplicity is 0
		 * arcType.setTargetMax(nodeType, nodeType, 1); // max multiplicity is 1
		 * 
		 * gragraRules.getTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED_MAX);
		 * 
		 * System.out.println("The type graph of gragraRules is done");
		 */

		/** Create rule graph objects and rule morphism */

		Node nl1 = null, nl2 = null, nr1 = null, nr2 = null;
		AttrInstance attrInstance = null;
		ValueTuple vt = null;
		ValueMember vm = null;

		String name = "";
		String age = "";
		String sex = "";

		// Create rule1
		Rule rule1 = gragraRules.createRule();
		rule1.setName("NewPerson");
		System.out.println("Rule1: " + rule1.getName());

		// Get LHS, it remains empty
		Graph left = rule1.getLeft();

		// Get RHS
		Graph right = rule1.getRight();

		// variable tuple of the rule
//		VarTuple varTuple1 = (VarTuple) rule1.getAttrContext().getVariables();

		// variable n:String
//		String varType_String = "String";
//		String varName_n = "n";

		// Create two nodes in RHS
		try {
			nr1 = right.createNode(nodeType);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}
		// set attributes:
		if (nr1 != null) {
			attrInstance = nr1.getAttribute();
			vt = (ValueTuple) attrInstance;

			/*
			 * // an example for input parameter: // rule1 will use input
			 * parameter for setting attribute member name
			 * 
			 * if(!varTuple1.isDeclared("n")) varTuple1.declare(this.javaHandler,
			 * varType_String, varName_n);
			 * varTuple1.getVarMemberAt(varName_n).setInputParameter(true);
			 * attrInstance.setExprAt(varName_n, "name");
			 */

			vm = (ValueMember) vt.getMemberAt("name");
			name = "Jon";
			vm.setExprAsObject(name);
			vm = (ValueMember) vt.getMemberAt("age");
			age = "25";
			vm.setExprAsEvaluatedText(age);
			vm = (ValueMember) vt.getMemberAt("sex");
			sex = "m";
			vm.setExprAsObject(sex);

			vm.checkValidity();
			System.out.println(nr1.getAttribute().getValueAt("name") + "   "
					+ nr1.getAttribute().getValueAt("age") + "   "
					+ nr1.getAttribute().getValueAt("sex"));
		}

		/** NACs */
		// Create an negative application condition (NAC)
		// The left graph of the rule is the left graph of the NAC, too.
		// This NAC forbids to create equal person.
		OrdinaryMorphism nac1 = rule1.createNAC();
		nac1.setName("notSame");

		Node nn = null;

		// Get NAC graph:
		Graph nac1Graph = nac1.getTarget();
		try {
			nn = nac1Graph.createNode(nodeType);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}
		// set attributes
		if (nn != null) {
			attrInstance = nn.getAttribute();
			vt = (ValueTuple) attrInstance;

			// vm = (ValueMember) vt.getMemberAt("name");
			// name = "Jon";
			// vm.setExprAsObject(name);
			// //attrInstance.setExprAt(varName_n, "name");

			vm = (ValueMember) vt.getMemberAt("age");
			age = "25";
			vm.setExprAsEvaluatedText(age);
			vm.checkValidity();
			System.out.println("NAC does not allow more persons with age : "
					+ nn.getAttribute().getValueAt("age"));
		}
		// NAC morphism remains empty.

		// Create rule2 with (NAC), attribute conditions
		// This rule creates an edge between two nodes;
		// it uses variables and attribute conditions
		Rule rule2 = gragraRules.createRule();
		rule2.setName("SetRelation");
		System.out.println("Rule2: " + rule2.getName());

		left = rule2.getLeft();
		try {
			nl1 = left.createNode(nodeType);
			nl2 = left.createNode(nodeType);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}

		right = rule2.getRight();
		try {
			nr1 = right.createNode(nodeType);
			nr2 = right.createNode(nodeType);
			right.createArc(arcType, nr1, nr2);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}

		// Define a rule morphism
		if (nl1 != null && nl2 != null && nr1 != null && nr2 != null) {
			rule2.addMapping(nl1, nr1);
			rule2.addMapping(nl2, nr2);
		}

		/** variables in the rule attribute context */

		AttrContext ac2 = rule2.getAttrContext();
		VarTuple varTuple = (VarTuple) ac2.getVariables();

		// Define variables x and y of type int
		String varType_Int = "int";
		String varName_x = "x";
		String varName_y = "y";
		if (!varTuple.isDeclared(varName_x))
			varTuple.declare(this.javaHandler, varType_Int, varName_x);
		if (!varTuple.isDeclared(varName_y))
			varTuple.declare(this.javaHandler, varType_Int, varName_y);

		// Use the variable x as a value of the attr. member age
		// in the attribute instance of the node nl1
		if (nl1 != null) {
		attrInstance = nl1.getAttribute();
		attrInstance.setExprAt(varName_x, "age");
		// System.out.println(attrInstance);
		}
		// Use the variable y as a value of the attr. member age
		// in the attribute instance of the node nl2
		if (nl2 != null) {
		attrInstance = nl2.getAttribute();
		attrInstance.setExprAt(varName_y, "age");
		// System.out.println(attrInstance);
		}
		// Delete variable y - an example
		// VarMember var_y = varTuple.getVarMemberAt(varName_y);
		// var_y.delete();

		// List all variables
		System.out.println("Variables: ");
		VarTuple vars = (VarTuple) ac2.getVariables();
		for (int i = 0; i < vars.getNumberOfEntries(); i++) {
			VarMember v = vars.getVarMemberAt(i);
			System.out.println(i + ": name=" + v.getName() + "   value="
					+ v.getExprAsText());
		}

		/** conditions */
		AttrConditionTuple condTuple = ac2.getConditions();
		String expr = "(x>=16)&&(x<70)";
		condTuple.addCondition(expr);
		expr = "(y>=16)&&(y<70)";
		condTuple.addCondition(expr);

		// Note: all variables of a condition string have to be declared.
		if (condTuple.isDefinite())
			System.out.println("Conditions  are definite");
		else
			System.out.println("Conditions  are not definite");

		// List all conditions
		System.out.println("Conditions: ");
		for (int i = 0; i < condTuple.getNumberOfEntries(); i++) {
			AttrConditionMember cm = (AttrConditionMember) condTuple
					.getMemberAt(i);
			System.out.println(i + ": name=" + cm.getName() + "   value="
					+ cm.getExprAsText());
		}

		Iterator<Node> e;
		// Get graph objects (nodes) of the left rule side - an example of usage
		// System.out.println(rule2.getName()+" : left graph :");
		e = rule2.getLeft().getNodesSet().iterator();
		while (e.hasNext()) {
			Node n = e.next();
			 System.out.println(n);
			// System.out.println((ValueTuple)n.getAttribute()) ;
		}

		/** NAC */
		// This NAC does not allow an edge between two nodes.
		OrdinaryMorphism nac2 = rule2.createNAC();
		nac2.setName("onlySingle");

		Node nn1 = null, nn2 = null;
		Graph nac2Graph = nac2.getTarget();
		try {
			nn1 = nac2Graph.createNode(nodeType);
			nn2 = nac2Graph.createNode(nodeType);
			nac2Graph.createArc(arcType, nn1, nn2);
			nac2Graph.createArc(arcType, nn2, nn1);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}

		// NAC morphism
		if (nl1 != null && nl2 != null && nn1 != null && nn2 != null) {
			nac2.addMapping(nl1, nn1);
			nac2.addMapping(nl2, nn2);
		}

		// Create rule3
		// This rule deletes an edge between two nodes.
		Rule rule3 = gragraRules.createRule();
		rule3.setName("RemoveRelation");
		System.out.println("Rule2: " + rule3.getName());

		left = rule3.getLeft();
		try {
			nl1 = left.createNode(nodeType);
			nl2 = left.createNode(nodeType);
			left.createArc(arcType, nl1, nl2);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}

		right = rule3.getRight();
		try {
			nr1 = right.createNode(nodeType);
			nr2 = right.createNode(nodeType);
		} catch (TypeException ex) {
			ex.printStackTrace();
		}

		if (nl1 != null && nl2 != null && nr1 != null && nr2 != null) {
			rule3.addMapping(nl1, nr1);
			rule3.addMapping(nl2, nr2);
		}

		// Get rules
//		Enumeration<Rule> rules = gragraRules.getRules();

		// Before transforming grammar please check whether it ready to
		// transform.
		// There would be checked: attribute settings in the LHS of the rules
		// and their attribute context: variables and attribute conditions.
		// Also marking of the variables and attribute conditions will be done
		// dependent of the usage (LHS, RHS, NAC),
		// that is needed for validation of conditions.
		// The method GraGra.isReadyToTransform() returns the failed rule.
		Pair<Object, String> pair = gragraRules.isReadyToTransform();
		Object test = null;
		if (pair != null)
			test = pair.first;
		if (test != null) {
			if (test instanceof Rule) {
				String s0 = "Rule  \""
						+ ((Rule) test).getName()
						+ "\" : "
						+ ((Rule) test).getErrorMsg()
						+ "\nPlease check: \n  - attribute settings of the new objects of the RHS \n  - attribute context ( variable and condition declarations ) of this rule.\nThe grammar is not ready to transform.";
				System.out.println(s0);
			}
		} else
			System.out.println("Grammar  \"" + gragraRules.getName()
					+ "\"  is ready to transform");
	}

	private boolean gragraGraphImportRules() {
		System.out.println("gragraGraphImportRules...");
		// import other type graph
		if (gragraRules.getTypeGraph() != null) {
			if (!gragraGraph.importTypeGraph(gragraRules.getTypeGraph(), false)) // true))
				System.out.println("import other type graph  FAILED!");
		}
		// import other rules
		int count = 0;
		for (int i = 0; i < gragraRules.getListOfRules().size(); i++) {
			Rule r = gragraRules.getListOfRules().get(i);
			if (!gragraGraph.addImportRule(r))
				System.out.println("import  rule: " + r.getName()
						+ "   FAILED!");
			else
				count++;
		}
		if (count != gragraRules.getListOfRules().size())
			return false;
		
		return true;
	}

	private void gragraGraphTransform() {
		Pair<Object, String> pair = gragraGraph.isReadyToTransform(true);
		Object test = null;
		if (pair != null)
			test = pair.first;
		if (test != null) {
			if (test instanceof Graph) {
				System.out.println("Grammar  " + gragraGraph.getName()
						+ "  graph: " + gragraGraph.getGraph().getName()
						+ "  is not ready for transform");
			} else if (test instanceof AtomConstraint) {
				String s0 = "Atomic graph constraint  \""
						+ ((AtomConstraint) test).getAtomicName()
						+ "\" is not valid. "
						+ "\nPlease check: "
						+ "\n  - graph morphism ( injective and total )  "
						+ "\n  - attribute context ( variable and condition declarations ).";
				System.out.println(s0);
			} else if (test instanceof Rule) {
				String s0 = "Rule  \""
						+ ((Rule) test).getName()
						+ "\" : "
						+ ((Rule) test).getErrorMsg()
						+ "\nPlease check: \n  - attribute settings of the new objects of the RHS \n  - attribute context ( variable and condition declarations ) of this rule.\nThe grammar is not ready to transform.";
				System.out.println(s0);
			}
			System.out.println("Grammar  " + gragraGraph.getName()
					+ "  CANNOT TRANSFORM!");
			return;
		} 
		System.out.println("Grammar  " + gragraGraph.getName()
					+ "  is ready to transform");

		System.out.println("Matching and graph transformation ");

		// Get all completion strategies
//		Enumeration<MorphCompletionStrategy> strategies = CompletionStrategySelector.getStrategies();

		// default strategy is injective, with dangling condition (DPO), with
		// NACs.
		System.out.println(this.strategy);
		this.strategy.showProperties();

		/* an example to set / clear strategy properties */
		/*
		 * BitSet activebits = this.strategy.getProperties();
		 * activebits.clear(CompletionPropertyBits.INJECTIVE);
		 * activebits.clear(CompletionPropertyBits.DANGLING);
		 * activebits.clear(CompletionPropertyBits.IDENTIFICATION);
		 * activebits.clear(CompletionPropertyBits.NAC);
		 * System.out.println(this.strategy.getProperties());
		 * this.strategy.showProperties();
		 * activebits.set(CompletionPropertyBits.INJECTIVE);
		 * activebits.set(CompletionPropertyBits.DANGLING); //
		 * activebits.set(CompletionPropertyBits.IDENTIFICATION);
		 * activebits.set(CompletionPropertyBits.NAC);
		 * System.out.println(this.strategy.getProperties());
		 * this.strategy.showProperties();
		 */

		// Set graph transformation options
		Vector<String> gratraOptions = new Vector<String>();
		gratraOptions.add("CSP");
		gratraOptions.add("injective");
		gratraOptions.add("dangling");
		gratraOptions.add("NACs");
		gratraOptions.add("consistency");
		gragraGraph.setGraTraOptions(gratraOptions);

		// Set file name and save grammar
//		String fn = gragraGraph.getName() + ".ggx";
		// gragraGraph.save(fn);
		// System.out.println("Grammar "+gragraGraph.getName()+" saved in "+fn);

		System.out.println("Continuing ...");

		Match match = null;

		// an example to applay a rule
		List<Rule> rules = gragraGraph.getListOfRules();
		Rule rule1 = rules.get(0);
		System.out.println("Try to apply  rule1: " + rule1.getName());
		int num = 4;
		for (int i = 0; i <= num; i++) {
			// Create an empty match; check if the match is complete
			System.out.println("Rule1  " + rule1.getName()
					+ "    >> create match");
			match = gragraRules.createMatch(rule1);
			// Set strategy of match completion
			match.setCompletionStrategy(this.strategy, true);

			/*
			 * test output of match attribute context (variables)
			 * System.out.println("Match attribute context::"); AttrContext ac =
			 * match.getAttrContext(); for (int i = 0; i <
			 * ac.getVariables().getNumberOfEntries(); i++) { am =
			 * (AttrInstanceMember) ac.getVariables().getMemberAt(i);
			 * System.out.println("Variable "+i+": name="+am.getName()+"
			 * value="+am.getExprAsText()); }
			 */

			// rule1 uses input parameter, so it will be set
			VarTuple avt = (VarTuple) match.getAttrContext().getVariables();
			while (!avt.areInputParametersSet()) {
				VarMember vmem = avt.getVarMemberAt("n");
				String aName = "Jon_" + i;
				if (vmem.isInputParameter() && !vmem.isSet()) {
					vmem.setExprAsObject(aName);
					vmem.checkValidity();
					System.out.println(vmem.getExpr() + "   " + vmem);
				}
			}

			if (match.nextCompletion()) {
				if (match.isValid()) {
//					Step step = new Step();
					try {
						Morphism co = StaticStep.execute(match);
						System.out
								.println("Rule1  " + match.getRule().getName()
										+ " : step is done");
						((OrdinaryMorphism)co).dispose();
					} catch (TypeException ex) {
						ex.printStackTrace();
						gragraGraph.destroyMatch(match);
						System.out.println("Rule1  "
								+ match.getRule().getName()
								+ " : match failed : " + ex.getMessage());
					}
				} else
					System.out.println("Rule1  " + match.getRule().getName()
							+ " : match is not valid");
			}
			gragraGraph.destroyMatch(match);
			// match.clear();

			showGraph(gragraGraph);

			// gragraGraph.save(fn);
			// System.out.println("After apply rule1 gragraGraph saved in "+fn);
		}

		// an example to apply rule2
		Rule rule2 = rules.get(1);
		boolean doIt = true; // false;
		if (doIt) {
			System.out.println("Apply  rule2  " + rule2.getName()
					+ " so long as possible");

			System.out.println("Rule2  " + rule2.getName()
					+ "    >> create match");
			match = gragraGraph.createMatch(rule2);
			match.setCompletionStrategy(this.strategy, true);
			while (match.nextCompletion()) {

				/*
				 * test output of attribute conditions: ac =
				 * match.getAttrContext(); ContextView cv = (ContextView) ac;
				 * AttrConditionTuple condTuple = (AttrConditionTuple)
				 * cv.getConditions(); for (int i = 0; i <
				 * condTuple.getNumberOfEntries(); i++) { AttrConditionMember cm =
				 * (AttrConditionMember) condTuple.getMemberAt(i);
				 * System.out.println("Condition "+i+": name="+cm.getName()+"
				 * val="+cm.getExprAsText()); } System.out.println("Condition is
				 * satisfied :" + condTuple.isTrue());
				 */

				/*
				 * test output of variables with its values for (int i = 0; i <
				 * ac.getVariables().getNumberOfEntries(); i++) { am =
				 * (AttrInstanceMember) ac.getVariables().getMemberAt(i);
				 * System.out.println("Variable "+i+": name="+am.getName()+"
				 * value="+am.getExprAsText()); }
				 */

				System.out.println("Rule2 : match is complete");
				if (match.isValid()) {
					System.out.println("Rule2 :  match is valid");
//					Step step = new Step();
					try {
						StaticStep.execute(match);
						System.out
								.println("Rule2  " + match.getRule().getName()
										+ " : step is done");
					} catch (TypeException ex) {
						ex.printStackTrace();
						gragraGraph.destroyMatch(match);
						System.out.println("Rule2  "
								+ match.getRule().getName()
								+ " : match failed! " + ex.getMessage());
					}
				} else
					System.out.println("Rule2  " + match.getRule().getName()
							+ " : match is not valid");
			}
			System.out.println("Rule  " + match.getRule().getName()
					+ " : match has no more completion");
			gragraGraph.destroyMatch(match);

			showGraph(gragraGraph);

			// gragraGraph.save(fn);
			// System.out.println("After apply rule2 gragraGraph saved in "+fn);
		}

		Rule rule3 = rules.get(2);

		// Create a transformation unit

		// if you want to use rule layers (layered graph transformation)
		// add option "layered".
		gratraOptions.add("layered");
		gratraOptions.add(GraTraOptions.BREAK_ALL_LAYER);
		gragraGraph.setGraTraOptions(gratraOptions);
		// Set layer : rule1 "NewPerson" layer 0
		// rule2 "SetRelation" layer 2
		// rule3 "RemoveRelation" layer 1

		rule1.setLayer(0);
		rule2.setLayer(2);
		rule3.setLayer(1);

		if (gratraOptions.contains("layered")) {
			String s = gragraGraph.getName() + "_layered";
			gragraGraph.setName(s);
//			fn = gragraGraph.getName() + ".ggx";
			// gragraGraph.save(fn);
			// System.out.println("gragraGraph layered saved in "+fn);
			// Create layered graph transformation
			gratraLayered = new LayeredGraTraImpl();
			gratraLayered.setGraGra(gragraGraph);
			gratraLayered.setHostGraph(gragraGraph.getGraph());
			gratraLayered.setCompletionStrategy(this.strategy);
			gratraLayered.setGraTraOptions(gragraGraph.getGraTraOptions());
			gratraLayered.addGraTraListener(this);
			System.out.println("\n### Transform layered  gragra:  "
					+ gragraGraph.getName());
			gratraLayered.transform();
		} else {
			// Create default non-deterministic graph transformation
//			fn = gragraGraph.getName() + ".ggx";
			// gragraGraph.save(fn);
			// System.out.println("gragraGraph saved in "+fn);
			// Create default graph transformation
			gratraDefault = new DefaultGraTraImpl();
			gratraDefault.setGraGra(gragraGraph);
			gratraDefault.setHostGraph(gragraGraph.getGraph());
			gratraDefault.setCompletionStrategy(this.strategy);
			gratraDefault.setGraTraOptions(gragraGraph.getGraTraOptions());
			gratraDefault.addGraTraListener(this);
			System.out.println("\n###Transform  :  " + gragraGraph.getName());
			gratraDefault.transform();
		}
	}

	@SuppressWarnings("unused")
	private boolean generatePostApplicationConditionOfRule(GraGra gragra,
			Rule rule) {
		// Convert AGCCs to Post Application Consistency Constraints for the
		// rule
		System.out.println("Create Post Application Constraints of rule : "
				+ rule.getName());
		Vector<Formula> allFormulae = new Vector<Formula>();
		Enumeration<Formula> enumForm = gragra.getConstraints();
		while (enumForm.hasMoreElements()) {
			Formula form = enumForm.nextElement();
			allFormulae.addElement(form);
		}
		rule.setUsedFormulas(allFormulae);
		String error = rule.convertUsedFormulas();
		System.out.println(error);
		if (!error.equals(""))
			return false;
		
		return true;
	}

	public static void showGraph(GraGra gragra) {
		System.out.println("\nGraph: " + gragra.getGraph().getName() + " {");
		Iterator<?> e = gragra.getGraph().getArcsSet().iterator();
		while (e.hasNext()) {
			Arc arc = (Arc) e.next();
			Node src = (Node) arc.getSource();
			Node trg = (Node) arc.getTarget();
			System.out.println(src.getAttribute().getValueAt("name") + " ---"
					+ arc.getType().getStringRepr() + "---> "
					+ trg.getAttribute().getValueAt("name"));
		}
		e = gragra.getGraph().getNodesSet().iterator();
		while (e.hasNext()) {
			Node node = (Node) e.next();
			if (node.getIncomingArcsSet().isEmpty()
					&& node.getOutgoingArcsSet().isEmpty())
				System.out.println(node.getAttribute().getValueAt("name"));
		}
		System.out.println(" }\n");
	}

	private final static int runsMax = 6;

	private static int runsCount;

	private final BaseFactory bf = BaseFactory.theFactory();

	private final AttrHandler javaHandler = DefaultInformationFacade.self()
			.getJavaHandler();

	private final MorphCompletionStrategy strategy = CompletionStrategySelector
			.getDefault();

	private static DefaultGraTraImpl gratraDefault;

	private static LayeredGraTraImpl gratraLayered;

	private static GraGra gragraRules, gragraGraph;

}
