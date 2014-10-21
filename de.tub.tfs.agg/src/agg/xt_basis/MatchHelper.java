/**
 * 
 */
package agg.xt_basis;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.Enumeration;

import agg.attribute.AttrContext;
import agg.attribute.AttrMapping;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.cons.AtomApplConstraint;
import agg.cons.EvalSet;
import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.util.Pair;
import agg.util.Triple;
import agg.xt_basis.csp.CompletionPropertyBits;



/**
 * @author olga
 *
 */
public final class MatchHelper {

	protected static String errorMsg = "";
	
	/**
	 * Check whether each object of the target graph of the specified application
	 * condition <code>cond</code> which does not have a pre-image in the source graph
	 * can be mapped (domain is not empty) into the target graph of the specified match.
	 * @param match
	 * @param cond
	 * @return true if domain is empty, otherwise false
	 */
	public static boolean isDomainOfApplCondEmpty(Match match, OrdinaryMorphism cond) {
		final Iterator<Node> en = cond.getTarget().getNodesSet().iterator();
		while (en.hasNext()) {
			Node go = en.next();
			if (!cond.getInverseImage(go).hasMoreElements()) {
				HashSet<GraphObject> v = match.getTarget().getTypeObjectsMap().get(go.convertToKey());
				if (v == null || v.isEmpty()) 
					return true;			
			}
		}
		final Iterator<Arc> en1 = cond.getTarget().getArcsSet().iterator();
		while (en1.hasNext()) {
			Arc go = en1.next();
			if (!cond.getInverseImage(go).hasMoreElements()) {
				if (go.isArc()) {
					String keystr = go.convertToKey();
					HashSet<GraphObject> v = match.getTarget().getTypeObjectsMap().get(keystr);
					if (v == null || v.isEmpty()) 
						return true;					
				}
			}
		}
		return false;
	}
	
	
	public static OrdinaryMorphism makeTestStep(
			final Match m2, 
			boolean allowVariables,
			boolean similarVariableName) {
		// make test step to check post conditions:
		// - type graph constraints ( edge type multiplicity )
		// - graph constraints
		errorMsg = "";
		try {
//			final TestStep s = new TestStep();
			final OrdinaryMorphism co_match = (OrdinaryMorphism) TestStep.execute(m2, allowVariables, similarVariableName);			
			return co_match;
		} catch (TypeException e) {	
//			System.out.println(e.getLocalizedMessage());
			errorMsg = e.getMessage();			
			return null;
		}
	}
	
	public static boolean isDanglingPoint(final Match match, final GraphObject obj, final GraphObject img) {
		if (match.getCompletionStrategy().getProperties().get(
				CompletionPropertyBits.DANGLING)) {
			if (obj.isNode() && match.getRule().getImage(obj) == null) {
				if (((Node) obj).getNumberOfArcs() != ((Node) img).getNumberOfArcs()) {	
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check Dangling condition for nodes to delete.
	 * All edges of the image node must have a pre-image in the match.
	 * @param match
	 * @return error message if check failed, otherwise - empty string
	 */
	public static String isDanglingSatisfied(final Match match) {
		errorMsg = "";
		if (match.getCompletionStrategy().getProperties().get(
				CompletionPropertyBits.DANGLING)) {
			
			final Iterator<Node> objects = match.getRule().getSource().getNodesSet().iterator();
			while (objects.hasNext()) {
				final Node x = objects.next();
				if (match.getRule().getImage(x) == null) {
					final Node y = (Node) match.getImage(x);
					if (y != null) {
						if (match.getCompletionStrategy().getProperties().get(
								CompletionPropertyBits.INJECTIVE)) {
							if (x.getNumberOfArcs() < y.getNumberOfArcs()) {					
								errorMsg = "Dangling condition isn't satisfied! ( node: "+x.getType().getName()+" )";
								return errorMsg;
							}
						}
						else { // match is non-injective	
							Iterator<Arc> arcs = y.getIncomingArcs();
							while (arcs.hasNext()) {
								Enumeration<GraphObject> en = match.getInverseImage(arcs.next());
								if (!en.hasMoreElements()) {
									errorMsg = "Dangling condition isn't satisfied! ( node: "+x.getType().getName()+" )";
									return errorMsg;
								}
							}
							arcs = y.getOutgoingArcs();
							while (arcs.hasNext()) {
								Enumeration<GraphObject> en = match.getInverseImage(arcs.next());
								if (!en.hasMoreElements()) {
									errorMsg = "Dangling condition isn't satisfied! ( node: "+x.getType().getName()+" )";
									return errorMsg;
								}
							}
						}
					}
				}
			}
		} 
		return "";
	}
	
	/**
	 * Check Identification condition for non-injective match objects.
	 * They are must be preserved. 
	 * @param match
	 * @return error message if check failed, otherwise - empty string
	 */
	public static String isIdentSatisfied(final Match match) {
		errorMsg = "";
		if (!match.getCompletionStrategy().getProperties().get(
				CompletionPropertyBits.INJECTIVE)
				&& match.getCompletionStrategy().getProperties().get(
						CompletionPropertyBits.IDENTIFICATION)) {
			
			final Rule itsRule = match.getRule();
			// check nodes first
			final Iterator<Node> nodes = itsRule.getOriginal().getNodesSet().iterator();
			while (nodes.hasNext()) {
				int k = 0;
				boolean preserved = true;
				
				final Enumeration<GraphObject> 
				en = match.getInverseImage(match.getImage(nodes.next()));
				int del = 0;
				while (en.hasMoreElements()) {
					final GraphObject xx = en.nextElement();
					k++;
					if (itsRule.getImage(xx) == null) {
						preserved = false;
						del++;
					}
				}
				if (k > 1 && !preserved && k > del) {
					errorMsg = "Identification condition isn't satisfied!";
					return errorMsg;
				}
			}
			// now check arcs
			final Iterator<Arc> arcs = itsRule.getOriginal().getArcsSet().iterator();
			while (arcs.hasNext()) {
				int k = 0;
				boolean preserved = true;
				
				final Enumeration<GraphObject> 
				en = match.getInverseImage(match.getImage(arcs.next()));
				int del = 0;
				while (en.hasMoreElements()) {
					final GraphObject xx = en.nextElement();
					k++;
					if (itsRule.getImage(xx) == null) {
						preserved = false;
						del++;
					}
				}
				if ((k > 1) && !preserved && k > del) {
					errorMsg = "Identification condition isn't satisfied!";
					return errorMsg;
				}
			}
		}
		return "";
	}
	
	/**
	 * 
	 * @param rule
	 * @param testComatch
	 * @param testMatch
	 * @return  error message if check failed, otherwise - empty string
	 */
	public static String isConsistent(final Rule rule, 
			final OrdinaryMorphism testComatch, 
			final OrdinaryMorphism testMatch) {
		// check consistency
		errorMsg = "";
		Vector<Formula> constraints = rule.getConstraints();
		if (!constraints.isEmpty()) {
			boolean good = true;
			for (int i = 0; i < constraints.size(); i++) {
				Formula f = constraints.get(i);
				if (!f.isEnabled())
					continue;

				Vector<Evaluable> vec = new Vector<Evaluable>();
				String form = f.getAsString(vec);
				// do copy of vec into v
				Vector<Evaluable> v = new Vector<Evaluable>();
				for (int j = 0; j < vec.size(); j++) {
					EvalSet evalset = (EvalSet) vec.get(j);
					Vector<Object> set = evalset.getSet();
					Vector<Object> v1 = new Vector<Object>();
					for (int k = 0; k < set.size(); k++) {
						Vector<Object> es = ((EvalSet) set.get(k)).getSet();
						AtomApplConstraint aaConstr = new AtomApplConstraint(es);
						v1.add(aaConstr);
					}
					v.add(new EvalSet(v1));
				}

				Formula f1 = new Formula(true);
				f1.setFormula(v, form);
				boolean b = f1.eval(new Pair<Morphism, Morphism>(testComatch, testMatch));
				good &= b;
				if (!good) {
					errorMsg = "Post application condition of the rule  "
						+ rule.getName()
						+"  failed";				
					break;
				}
			} 
			return errorMsg;
		} 
		return "";
	}

	/**
	 * 
	 * @param match
	 * @return error message if check failed, otherwise - empty string
	 */
	public static String attributesOfGlueObjectsCorrect(final Match match) {
		errorMsg = "";
		if (!match.getCompletionStrategy().getProperties().get(
				CompletionPropertyBits.INJECTIVE)){
			
			// check attributes :
			// attributes of glue object must have the same value as of keep
			// object
			attrsOfGlueObjectsCorrect(match, match.getImage().getNodesSet().iterator());
			attrsOfGlueObjectsCorrect(match, match.getImage().getArcsSet().iterator());
		}
		return errorMsg;
	}

	private static String attrsOfGlueObjectsCorrect(
			final Match match,
			final Iterator<?> elems) {

		// check attributes :
		// attributes of glue object must have the same value as of keep
		// object
		while (elems.hasNext()) {
			GraphObject go = (GraphObject) elems.next();
			if (go.getAttribute() == null)
				continue;

			final Enumeration<GraphObject> invers = match.getInverseImage(go);
			final Vector<GraphObject> origs = new Vector<GraphObject>(2);
			while (invers.hasMoreElements()) {
				origs.addElement(invers.nextElement());
			}
			if (origs.size() <= 1)
				continue;

			final Vector<GraphObject> ruleImgs = new Vector<GraphObject>(2);			
			for (int i = 0; i < origs.size(); i++) {
				GraphObject ruleImg = match.getRule().getImage(origs.elementAt(i));
				if (ruleImg != null)
					ruleImgs.addElement(ruleImg);
			}
			if (ruleImgs.size() <= 1)
				continue;

			GraphObject ruleImg = ruleImgs.elementAt(0);
			if (ruleImg.getAttribute() == null)
				continue;

			ValueTuple imgAttrs = (ValueTuple) ruleImg.getAttribute();
			GraphObject ruleOrig = match.getRule().getInverseImage(ruleImg).nextElement();
			
			for (int i=1; i<ruleImgs.size(); i++) {
				GraphObject otherRuleImg = ruleImgs.elementAt(i);
				if (otherRuleImg.getAttribute() == null)
					continue;

				boolean replace1 = true;
 
				ValueTuple otherAttrs = (ValueTuple) otherRuleImg.getAttribute();
				GraphObject otherOrig = match.getRule().getInverseImage(otherRuleImg).nextElement();

				for (int j = 0; j < imgAttrs.getNumberOfEntries(); j++) {
					ValueMember vmImg = imgAttrs.getValueMemberAt(j);
					if (vmImg != null) {
						String attrName = vmImg.getName();
						ValueMember 
						vmOtherImg = otherAttrs.getValueMemberAt(attrName);
						if (vmOtherImg != null) {
							if (vmImg.getExpr() == null) 
								continue;
							
							if (vmOtherImg.getExpr() != null) {
								if (!vmImg.getExprAsText().equals(
											vmOtherImg.getExprAsText())) {
									
									boolean attrFailed = vmImg.getExpr().isComplex()
															&& vmOtherImg.getExpr().isComplex();									
									if (!attrFailed) {
										attrFailed = vmImg.getExpr().isConstant()
														&& vmOtherImg.getExpr().isConstant();
										if (attrFailed) {
											ValueMember 
											govm = ((ValueTuple) go.getAttribute()).getValueMemberAt(attrName);
											if (!vmImg.getExprAsText().equals(govm.getExprAsText()))
												replace1 = false;
											else 
												attrFailed = false;
											if (attrFailed
													&& !vmOtherImg.getExprAsText().equals(govm.getExprAsText())) {
												replace1 = true;
												attrFailed = true;
											}
											else
												attrFailed = false;							
										}
									}
																		
									if (!attrFailed) {
										boolean varchanged1 = false;								
										if (vmImg.getExpr().isVariable()) {				
											ValueTuple origAttrs = (ValueTuple) ruleOrig.getAttribute();
											if(origAttrs != null) {
												ValueMember 
												vmRuleOrig = origAttrs.getValueMemberAt(attrName);
												if((vmRuleOrig != null)
														&& vmRuleOrig.isSet()
														&& vmRuleOrig.getExpr().isVariable()
														&& !vmRuleOrig.getExprAsText().equals(
																vmImg.getExprAsText())) {
													if (!isVariableUsed(vmImg, origs)) {
														varchanged1 = true;
														replace1 = true;
													}
												}
											}
										} 
										boolean varchanged2 = false;
										if (vmOtherImg.getExpr().isVariable()) {
											ValueTuple otherOrigAttrs = (ValueTuple) otherOrig.getAttribute();
											if(otherOrigAttrs != null) {
												ValueMember 
												vmOtherOrig = otherOrigAttrs.getValueMemberAt(attrName);
												if((vmOtherOrig != null)
														&& vmOtherOrig.isSet()
														&& vmOtherOrig.getExpr().isVariable()
														&& !vmOtherOrig.getExprAsText().equals(
																vmOtherImg.getExprAsText())) {
													if (!isVariableUsed(vmOtherImg, origs)) {
														varchanged2 = true;
														replace1 = false;
													}
												}
											}
										}
										attrFailed = varchanged1 && varchanged2;
									}
									
									if (attrFailed) {
										errorMsg = "The rule  <"
											+ match.getRule().getName()+ ">  "
											+ "can produce an attr. conflict \nfor objects of the type  "
											+ "<"+ ruleImg.getType().getName()+ "> "
											+ " and "
											+ "<"+ otherRuleImg.getType().getName()+ "> "
											+ "  \nwhich change the value of the same attribute.";
										return errorMsg;
									}
								}
							} 						
						}
					}
				}
				
				if ((i<ruleImgs.size()) && replace1) {
					ruleImg = otherRuleImg;
					imgAttrs = (ValueTuple) ruleImg.getAttribute();
					ruleOrig = match.getRule().getInverseImage(ruleImg).nextElement();
				}

			}
		}
		return "";
	}
	
	private static boolean isVariableUsed(final ValueMember vm, 
			final Vector<GraphObject> objs) {
		boolean varUsed = false;
		for (int i = 1; i < objs.size(); i++) {
			GraphObject go = objs.elementAt(i);						
			ValueTuple goAttr = (ValueTuple) go.getAttribute();
			if (go.getAttribute() == null)
				continue;
			ValueMember goAttrvm = goAttr.getValueMemberAt(vm.getName());
			if (goAttrvm != null 
				&& goAttrvm.isSet() 
				&& goAttrvm.getExprAsText().equals(vm.getExprAsText())) {
				varUsed = true;
				break;
			}			
		}
		return varUsed;
}
	
	/**
	 * Checks multiplicity of node resp. edge types due to the type graph of
	 * the given match.
	 * @param match
	 * @return Error string if check failed, otherwise - empty string.
	 */
	public static String isTypeMultiplicitySatisfied(final Match match) {
		errorMsg = "";
		int tgchecklevel = match.getSource().getTypeSet().getLevelOfTypeGraphCheck();
		if (tgchecklevel <= TypeSet.ENABLED) {
			return "";
		}
		
		final Rule itsRule = match.getRule();
		match.clearErrorMsg();
				
		List<String> typesNeedMultiplicityCheck = itsRule.getTypesWhichNeedMultiplicityCheck();
		if (typesNeedMultiplicityCheck.isEmpty()) {
			return "";
		}
		
		final List<Node> nodestodelete = new Vector<Node>(); // LHS objects
		final List<Node> nodestocreate = new Vector<Node>(); // RHS objects

		final List<Arc> arcstodelete = new Vector<Arc>();	// LHS objects		
		final List<Arc> arcstocreate = new Vector<Arc>();	// RHS objects
		
		final List<Arc> insOfDelNode = new Vector<Arc>();	// Host graph objects	
		final List<Arc> outsOfDelNode = new Vector<Arc>();	// Host graph objects	
		
		final List<GraphObject> nodestoglue = new Vector<GraphObject>();
		
		// get nodes to delete: 
		Iterator<?> en = itsRule.getLeft().getNodesSet().iterator();
		while (en.hasNext()) {
			final Node go = (Node) en.next();
			if (itsRule.getImage(go) == null) {
				nodestodelete.add(go);
				
				final Node n = (Node) match.getImage(go);
				insOfDelNode.addAll(n.getIncomingArcsSet());
				outsOfDelNode.addAll(n.getOutgoingArcsSet());
			}
		}
		// get arcs to delete only, source and target preserved
		en = itsRule.getLeft().getArcsSet().iterator();
		while (en.hasNext()) {
			Arc go = (Arc) en.next();
			if (itsRule.getImage(go) == null) {
				if (itsRule.getImage(go.getSource()) != null 
						&& itsRule.getImage(go.getTarget()) != null) 
				{
					arcstodelete.add(go);
				}
			}
		}
		
		// get nodes to create: 
		en = itsRule.getRight().getNodesSet().iterator();
		while (en.hasNext()) {
			GraphObject go = (GraphObject) en.next();
			if (!itsRule.getInverseImage(go).hasMoreElements()) {
				nodestocreate.add((Node)go);
			}
		}
		// get arcs (with preserved source or target) to create 
		en = itsRule.getRight().getArcsSet().iterator();
		while (en.hasNext()) {
			final Arc go = (Arc) en.next();
			if (!itsRule.getInverseImage(go).hasMoreElements()
					 && (itsRule.getInverseImage(go.getSource()).hasMoreElements() 
							 || itsRule.getInverseImage(go.getTarget()).hasMoreElements()) ) {				
				arcstocreate.add(go);
			}
		}
						
		// check nodes to delete  
		// check only node type multiplicity		
		int vecSize = nodestodelete.size();
		for (int i = 0; i < vecSize; i++) {
			final Node go = nodestodelete.get(i);
			if (go.isNode()) {
				if (!checkNodeMultiplicity(
						typesNeedMultiplicityCheck,
						match, go, i + 1,
						nodestodelete, nodestocreate, 
						nodestoglue, tgchecklevel))
					return errorMsg;
				
				vecSize = nodestodelete.size();
			} 
		}
		
		// check remained nodes to create
		vecSize = nodestocreate.size();
		for (int i = 0; i < vecSize; i++) {
			final Node go = nodestocreate.get(i);
			if (!checkNodeMaxMultiplicity(
					typesNeedMultiplicityCheck,
					match, go, i + 1, 
					nodestocreate, 
					nodestoglue, tgchecklevel))
				return errorMsg;
			
			vecSize = nodestocreate.size();
		}
			
		// check edge multiplicity due to edge to delete
		if (!checkEdgeMultiplicityDueToEdgeToDelete(
				typesNeedMultiplicityCheck,
				match, 
				insOfDelNode,
				outsOfDelNode,
				arcstodelete, 
				arcstocreate, nodestoglue, tgchecklevel)) {				
			return errorMsg;
		}

//		List<Arc> arcstocreatecopy = new Vector<Arc>(arcstocreate);
		
		// check remained arcs to create
		// these arcs must preserve source and target
		if (!checkEdgeTargetMaxMultiplicity(
				typesNeedMultiplicityCheck,
				match, arcstocreate, tgchecklevel)) {				
			return errorMsg;
		}
		if (!checkEdgeSourceMaxMultiplicity(
				typesNeedMultiplicityCheck,
				match, arcstocreate, tgchecklevel)) {				
			return errorMsg;
		}

		return "";		
	}

	private static boolean checkEdgeMultiplicityDueToEdgeToDelete(
			final List<String> typesNeedMultiplicityCheck,
			final Match match,
			final List<Arc> incomsofnodetodelete, // Host graph objects
			final List<Arc> outcomsofnodetodelete, // Host graph objects
			final List<Arc> arcstodelete, // LHS objects
			final List<Arc> arcstocreate, // RHS objects
			final List<GraphObject> nodestoglue, 
			int tgchecklevel) {
						
		final Map<String, List<Arc>> type2newarcs = new Hashtable<String, List<Arc>>();	
		// type2newarcs contains RHS arcs
		// sorted by Arc.convertToKey() string which contains
		// name of source and target type, too
		
		for (int i=0; i<arcstocreate.size(); i++) {
			// Arc of RHS 
			final Arc a = arcstocreate.get(i);
			
//			final String typekey = a.convertToKey();
			
			final List<String> typekeys = a.convertToKeyParentExtended();
			for (int l=0; l<typekeys.size(); l++) {
				final String typekey = typekeys.get(l);
				
				List<Arc> list = type2newarcs.get(typekey);
				if (list == null) {
					list = new Vector<Arc>();
					type2newarcs.put(typekey, list);
				}
				list.add(a);
			}
		}
		
		final Map<String, List<Arc>> type2delarcs = new Hashtable<String, List<Arc>>(); 
		final Map<String, List<Arc>> type2delarcs2 = new Hashtable<String, List<Arc>>(); 
		// type2delarcs will contain edges of the HostGraph
		// sorted by type.convertToKey()  which contains
		// name of source and target type, too
	
		// arcs to delete with source and target to preserve
		for (int i=0; i<arcstodelete.size(); i++) {
			// Arc of LHS
			final Arc da = arcstodelete.get(i);	
			
			final String typekey = da.convertToKey();
			
			// Arc of Host Graph
			final Arc a = (Arc) match.getImage(da);
						
//			final List<String> typekeys = da.convertToKeyParentExtended();
//			for (int l=0; l<typekeys.size(); l++) {
//				final String typekey = typekeys.get(l);
				
				if (typesNeedMultiplicityCheck.contains(typekey)) {
					List<Arc> list = type2delarcs.get(typekey);				
					if (list == null) {
						list = new Vector<Arc>();
						type2delarcs.put(typekey, list);
					}
					list.add(a);
					
					List<Arc> list2 = type2delarcs2.get(typekey);				
					if (list2 == null) {
						list2 = new Vector<Arc>();
						type2delarcs2.put(typekey, list2);
					}
					list2.add(a);
				}
//			}
		}
		
		for (int i=0; i<incomsofnodetodelete.size(); i++) {
			// Arc of Host Graph
			final Arc a = incomsofnodetodelete.get(i);	
			
			final String typekey = a.convertToKey();
				
				if (typesNeedMultiplicityCheck.contains(typekey)) {
					List<Arc> list = type2delarcs.get(typekey);				
					if (list == null) {
						list = new Vector<Arc>();
						type2delarcs.put(typekey, list);
					}
					list.add(a);
				}

		}
		// check the edge type target multiplicity
		if (!checkEdgeTargetMinMaxMultiplicity(match, type2delarcs, 
							type2newarcs, arcstocreate, tgchecklevel)) {
			return false;
		}
		
		
		for (int i=0; i<outcomsofnodetodelete.size(); i++) {
			// Arc of Host Graph
			final Arc a = outcomsofnodetodelete.get(i);				
			
			final String typekey = a.convertToKey();
			
				if (typesNeedMultiplicityCheck.contains(typekey)) {
					List<Arc> list = type2delarcs2.get(typekey);				
					if (list == null) {
						list = new Vector<Arc>();
						type2delarcs2.put(typekey, list);
					}
					list.add(a);
				}

		}
		// check the edge type source multiplicity
		if (!checkEdgeSourceMinMaxMultiplicity(match, type2delarcs2, 
							type2newarcs, arcstocreate, tgchecklevel)) {
			return false;
		}
		
		return true;
	}
	
	private static boolean checkEdgeTargetMinMaxMultiplicity(
			final Match match,
			final Map<String, List<Arc>> type2delarcs,
			final Map<String, List<Arc>> type2newarcs,
			final List<Arc> arcstocreate, 
			int tgchecklevel) {
		
		errorMsg = "";
		final List<Triple<Node, Type, Type>> srcNodes = new Vector<Triple<Node, Type, Type>>();
		final List<Integer> ndel = new Vector<Integer>();
		final List<Pair<Integer,Integer>> tarMinMax = new Vector<Pair<Integer,Integer>>();
		
		Iterator<String> iter = type2delarcs.keySet().iterator();
		while (iter.hasNext()) {
			final String typekey = iter.next();
		
			final List<Arc> incoms = type2delarcs.get(typekey);
			for (int j = 0; j<incoms.size(); j++) {
				final Arc a = incoms.get(j); // edge in host graph
				int indx = getIndexOfNode(srcNodes, (Node)a.getSource(), a.getType(), a.getTargetType());
				
				if (indx >= 0) {
					int nn = ndel.get(indx).intValue()+1;
					ndel.add(indx, new Integer(nn));						
					ndel.remove(indx+1);						
				} else {					
					final List<Arc> newarcs = type2newarcs.get(typekey);
					int nn = 0;
					if (newarcs != null) {
						if (match.getInverseImage(a.getSource()).hasMoreElements()) {
							nn = getCountOfArcsWithTypeAndSource(typekey, 
								match.getRule().getImage(match.getInverseImage(a.getSource()).nextElement()), 
								newarcs, arcstocreate);
						}
					}
				
					srcNodes.add(new Triple<Node, Type, Type>((Node)a.getSource(), a.getType(), a.getTargetType()));
					indx = srcNodes.size()-1;
					
					if (nn > 0) nn--;
					else nn = 1;						
					ndel.add(new Integer(nn));
									
					int tarMin = a.getType().getTargetMin(a.getSource().getType(),
							a.getTarget().getType());
					int tarMax = a.getType().getTargetMax(a.getSource().getType(),
							a.getTarget().getType());
					tarMinMax.add(new Pair<Integer,Integer>(new Integer(tarMin), new Integer(tarMax)));
				}
				
				Type targetNodeType = a.getTarget().getType(); 
				
				Vector<Arc> vec = match.getTarget().getTypeSet().getTypeGraph().getArcs(a.getType());
				if (vec != null) {
					if (vec.size() == 1) {
						Arc typeArc = vec.get(0);
						targetNodeType = typeArc.getTarget().getType(); 
					} else {
						for (int k=0; k<vec.size(); k++) {
							Arc typeArc = vec.get(k);
							if (typeArc.getSourceType().isParentOf(a.getSourceType())
									&& typeArc.getTargetType().isParentOf(a.getTargetType())) {
								targetNodeType = typeArc.getTarget().getType(); 
								break;
							}
						}
					}			
				}
				int nn = ndel.get(indx).intValue();
				final Node src = srcNodes.get(indx).first;					
				int outs = src.getNumberOfOutgoingArcs(a.getType(), targetNodeType);
				
				if (tgchecklevel > TypeSet.ENABLED_MAX
						&& tarMinMax.get(indx).first.intValue() > 0
						&& (outs - nn) < tarMinMax.get(indx).first.intValue()) {
					if (match.getRule().getImage(match.getInverseImage(a.getSource()).nextElement()) != null) {
						// source node preserved, the edge deleted
						errorMsg = "Target multiplicity of edge type failed!"
							+ "\nType  \"" + a.getType().getName() + "\""
							+ " - target minimum failed.";
						return false;
					}
				}
				else if (tarMinMax.get(indx).second.intValue() != TypeSet.UNDEFINED
						&& (outs - nn) > tarMinMax.get(indx).second.intValue()) {
					errorMsg = "Target multiplicity of edge type failed!"
						+ "\nType  \"" + a.getType().getName() + "\""
						+ " - target maximum failed.";
					return false;
				}
			}
			
			srcNodes.clear();
			ndel.clear();
			tarMinMax.clear();

		}
		return true;
	}
	
	private static boolean checkEdgeSourceMinMaxMultiplicity(
			final Match match,
			final Map<String, List<Arc>> type2delarcs,
			final Map<String, List<Arc>> type2newarcs,
			final List<Arc> arcstocreate, 
			int tgchecklevel) {
		
		errorMsg = "";
		final List<Triple<Node, Type, Type>> tarNodes = new Vector<Triple<Node, Type, Type>>();
		final List<Integer> ndel = new Vector<Integer>();
		final List<Pair<Integer,Integer>> srcMinMax = new Vector<Pair<Integer,Integer>>();
		
		Iterator<String> iter = type2delarcs.keySet().iterator();
		while (iter.hasNext()) {
			final String typekey = iter.next();
			final List<Arc> outcoms = type2delarcs.get(typekey);
			for (int j = 0; j<outcoms.size(); j++) {
				final Arc a = outcoms.get(j);

				int indx = getIndexOfNode(tarNodes, (Node)a.getTarget(), a.getType(), a.getSourceType());
				if (indx >= 0) {
					int nn = ndel.get(indx).intValue()+1;
					ndel.set(indx, new Integer(nn));											
				} else {					
					final List<Arc> newarcs = type2newarcs.get(typekey);
					int nn = 0;
					if (newarcs != null && newarcs.size() > 0) {
						// check inverse image of a match object because dangling condition can be OFF
						if (match.getInverseImage(a.getTarget()).hasMoreElements()) {
							nn = getCountOfArcsWithTypeAndTarget(typekey,  	
								match.getRule().getImage(match.getInverseImage(a.getTarget()).nextElement()), 
								newarcs, arcstocreate);
						} 
					}
					
					tarNodes.add(new Triple<Node,Type,Type>((Node)a.getTarget(), a.getType(), a.getSourceType()));
					indx = tarNodes.size()-1;
					
					if (nn > 0) nn--;
					else nn = 1;						
					ndel.add(new Integer(nn));
									
					int srcMin = a.getType().getSourceMin(a.getSource().getType(),
							a.getTarget().getType());
					int srcMax = a.getType().getSourceMax(a.getSource().getType(),
							a.getTarget().getType());
					srcMinMax.add(new Pair<Integer,Integer>(new Integer(srcMin), new Integer(srcMax)));
				}
				
				Type sourceNodeType = a.getSource().getType(); 
				
				final Vector<Arc> vec = match.getTarget().getTypeSet().getTypeGraph().getArcs(a.getType());
				if (vec != null) {
					if (vec.size() == 1) {
						Arc typeArc = vec.get(0);
						sourceNodeType = typeArc.getSource().getType(); 
					} else {
						for (int k=0; k<vec.size(); k++) {
							Arc typeArc = vec.get(k);
							if (typeArc.getSourceType().isParentOf(a.getSourceType())
									&& typeArc.getTargetType().isParentOf(a.getTargetType())) {
								sourceNodeType = typeArc.getSource().getType(); 
								break;
							}
						}
					}								
				}
				int nn = ndel.get(indx).intValue();
				final Node tar = tarNodes.get(indx).first;
				int ins = tar.getNumberOfIncomingArcs(a.getType(), sourceNodeType);
				
				if (tgchecklevel > TypeSet.ENABLED_MAX
						&& srcMinMax.get(indx).first.intValue() > 0
						&& (ins - nn) < srcMinMax.get(indx).first.intValue()) {
					if (match.getRule().getImage(match.getInverseImage(a.getTarget()).nextElement()) != null) {
						// target node is preserved, the edge will be deleted
						errorMsg = "Source multiplicity of edge type failed!"
							+ "\nType  \"" + a.getType().getName() + "\""
							+ " - source minimum failed.";
						return false;
					}
				}
				else if (srcMinMax.get(indx).second.intValue() != TypeSet.UNDEFINED
						&& (ins - nn) > srcMinMax.get(indx).second.intValue()) {
					errorMsg = "Source multiplicity of edge type failed!"
						+ "\nType  \"" + a.getType().getName() + "\""
						+ " - source maximum failed.";
					return false;
				}
			}
			
			tarNodes.clear();
			ndel.clear();
			srcMinMax.clear();

		}
		return true;
	}
	
	private static int getCountOfArcsWithTypeAndSource(
			final String typekey,
			final GraphObject source, 
			final List<Arc> newarcs,
			final List<Arc> allnewarcs) {
		int nn = 0;
		if (newarcs != null && source != null) {
			for (int k=0; k<newarcs.size(); k++) {
				final Arc arc = newarcs.get(k);
				if (arc.getSource() == source
//						|| arc.getSource().compareTo(source)
						) {
					if (allnewarcs.contains(arc)) {
						nn++;
					}
				}
			}
		}
		return nn;
	}
	
	private static int getCountOfArcsWithTypeAndTarget(
			final String typekey,
			final GraphObject target, 
			final List<Arc> newarcs,
			final List<Arc> allnewarcs) {
		int nn = 0;
		if (newarcs != null && target != null) {
			for (int k=0; k<newarcs.size(); k++) {
				final Arc arc = newarcs.get(k);
				if (arc.getTarget() == target 						
//						|| arc.getTarget().compareTo(target)
						) {	
					if (allnewarcs.contains(arc)) {
						nn++;
					}
				}
			}
		}
		return nn;
	}
	
	
	private static boolean checkEdgeTargetMaxMultiplicity(
			final List<String> typesNeedMultiplicityCheck,
			final Match match,
			final List<Arc> arcstocreate, 
			int tgchecklevel) {
		
		errorMsg = "";
		final List<Triple<Node, Type, Type>> srcNodes = new Vector<Triple<Node, Type, Type>>();
		final List<Integer> nnew = new Vector<Integer>();
		final List<Integer> tarMax = new Vector<Integer>();
		
		final Map<String, List<Arc>> type2newarcs = new Hashtable<String, List<Arc>>();	// RHS objects
		// type2newarcs contains arcs sorted by type.convertToKey() string which contains
		// name of source and target type, too
		
		for (int i=0; i<arcstocreate.size(); i++) {
			final Arc a = arcstocreate.get(i);
			final String typekey = a.convertToKey();
			List<Arc> list = type2newarcs.get(typekey);
			if (list == null) {
				list = new Vector<Arc>();
				type2newarcs.put(typekey, list);
			}
			list.add(a);
		}

		for (int i=0; i<arcstocreate.size(); i++) {
			final Arc a = arcstocreate.get(i);
			
			// Arc a must preserve its source or target
			if (!match.getRule().getInverseImage(a.getSource()).hasMoreElements()
					|| !typesNeedMultiplicityCheck.contains(a.convertToKey())
			) {
				continue;
			}
			
//			final String typekey = a.convertToKey();
			final Node src = (Node) match.getImage(
					match.getRule().getInverseImage(a.getSource()).nextElement());
			if (src == null) {
				continue;
			}
			
			int indx = getIndexOfNode(srcNodes, src, a.getType(), a.getTargetType());
			if (indx >= 0) {
				int nn = nnew.get(indx).intValue()+1;
				nnew.set(indx, new Integer(nn));												
			} else {	
//				final List<Arc> newarcs = type2newarcs.get(typekey);
//				int nn = getCountOfArcsWithTypeAndSource(typekey, 
//						a.getSource(), 
//						newarcs, arcstocreate);	
				
				srcNodes.add(new Triple<Node, Type, Type>(src, a.getType(), a.getTargetType()));
				indx = srcNodes.size()-1;
					
				nnew.add(new Integer(1)); //(nn));
									
				int max = a.getType().getTargetMax(a.getSource().getType(),
													a.getTarget().getType());
				tarMax.add(new Integer(max));
			}
				
			Type targetNodeType = a.getTarget().getType(); 

			Vector<Arc> vec = match.getTarget().getTypeSet().getTypeGraph().getArcs(a.getType());
			if (vec != null) {
				if (vec.size() == 1) {
					Arc typeArc = vec.get(0);
					targetNodeType = typeArc.getTarget().getType(); 
				} else {
					for (int k=0; k<vec.size(); k++) {
						Arc typeArc = vec.get(k);
						if (typeArc.getSourceType().isParentOf(a.getSourceType())
								&& typeArc.getTargetType().isParentOf(a.getTargetType())) {
							targetNodeType = typeArc.getTarget().getType(); 
							break;
						}
					}
				}			
			}		
			int nn = nnew.get(indx).intValue();
						
			List<Arc> outarcs = src.getOutgoingArcs(a.getType(), targetNodeType);
			int outs = outarcs.size();
			for (int k=0; k<outarcs.size(); k++) {
				Arc outarc = outarcs.get(k);
				if (match.getInverseImage(outarc).hasMoreElements()
						&& match.getRule().getImage(match.getInverseImage(outarc).nextElement()) == null) {						
					outs--;
				}
				else if (match.getTarget().getTypeSet().isOutgoingArcOfClan(
						src.getType(),
						outarc.getType(), 
						outarc.getTarget().getType())) {
					
					if (match.getInverseImage(outarc).hasMoreElements()
							&& match.getRule().getImage(match.getInverseImage(outarc).nextElement()) == null) {						
						outs--;
					}
				} 
			}			
			
//			System.out.println("MatchHelper:  Target Max: "
//					+src.getType().getName()+"   "
//					+a.getType().getName()+"   "+outs+"   "+nn);
			
			if (tarMax.get(indx).intValue() != TypeSet.UNDEFINED
						&& (outs + nn) > tarMax.get(indx).intValue()) {
				errorMsg = "Target multiplicity of edge type failed!"
						+ "\nType  \"" + a.getType().getName() + "\""
						+ " - target maximum failed.";
				return false;
			}
			
			if (i < 0) break;
		}
		return true;
	}
	
	private static boolean checkEdgeSourceMaxMultiplicity(
			final List<String> typesNeedMultiplicityCheck,
			final Match match,
			final List<Arc> arcstocreate, 
			int tgchecklevel) {

		errorMsg = "";
		final List<Triple<Node, Type, Type>> tarNodes = new Vector<Triple<Node, Type, Type>>();
		final List<Integer> nnew = new Vector<Integer>();
		final List<Integer> srcMax = new Vector<Integer>();
		
		final Map<String, List<Arc>> type2newarcs = new Hashtable<String, List<Arc>>();	// RHS objects
		// type2newarcs will contain arcs  by type.convertToKey() string 
		// with name of source and target type, too
		// arcs of arcstocreate are arcs of the RHS of a rule
		for (int i=0; i<arcstocreate.size(); i++) {
			final Arc a = arcstocreate.get(i);
			final String typekey = a.convertToKey();
			List<Arc> list = type2newarcs.get(typekey);
			if (list == null) {
				list = new Vector<Arc>();
				type2newarcs.put(typekey, list);
			}
			list.add(a);
		}
		
		for (int i=0; i<arcstocreate.size(); i++) {
			final Arc a = arcstocreate.get(i);
			// Arc a must preserve its source and target
			if ( !match.getRule().getInverseImage(a.getTarget()).hasMoreElements()
					|| !typesNeedMultiplicityCheck.contains(a.convertToKey()) ) {
				continue;
			}
			
//			final String typekey = a.convertToKey();
			// target node in a host graph
			final Node tar = (Node) match.getImage(
					match.getRule().getInverseImage(a.getTarget()).nextElement());	
			if (tar == null) {
				continue;
			}
			// tarNodes, tar in a host graph, a in the RHS
			int indx = getIndexOfNode(tarNodes, tar, a.getType(), a.getSourceType());
			if (indx >= 0) {
				int nn = nnew.get(indx).intValue()+1;
				nnew.set(indx, new Integer(nn));						
			} else {		
//				final List<Arc> newarcs = type2newarcs.get(typekey);				
//				int nn = getCountOfArcsWithTypeAndTarget(typekey, 
//						a.getTarget(), 
//						newarcs, arcstocreate);
				
				tarNodes.add(new Triple<Node, Type, Type>(tar, a.getType(), a.getSourceType()));
				indx = tarNodes.size()-1;
				nnew.add(new Integer(1)); //nn));
									
				int max = a.getType().getSourceMax(a.getSource().getType(),
													a.getTarget().getType());
				srcMax.add(new Integer(max));
			}
					
			Type sourceNodeType = a.getSource().getType(); 
			
			final Vector<Arc> vec = match.getTarget().getTypeSet().getTypeGraph().getArcs(a.getType());
			if (vec != null) {
				if (vec.size() == 1) {
					Arc typeArc = vec.get(0);
					sourceNodeType = typeArc.getSource().getType(); 
				} else {
					for (int k=0; k<vec.size(); k++) {
						Arc typeArc = vec.get(k);
						if (typeArc.getSourceType().isParentOf(a.getSourceType())
								&& typeArc.getTargetType().isParentOf(a.getTargetType())) {
							sourceNodeType = typeArc.getSource().getType(); 
							break;
						}
					}
				}				
			}
			int nn = nnew.get(nnew.size()-1).intValue();
			List<Arc> inarcs = tar.getIncomingArcs(a.getType(), sourceNodeType);
			int ins = inarcs.size();
			
			for (int k = 0; k<inarcs.size(); k++) {			
				Arc inarc = inarcs.get(k);
				if (match.getInverseImage(inarc).hasMoreElements()
						&& match.getRule().getImage(match.getInverseImage(inarc).nextElement()) == null) { 						
					ins--;
				}
				else if (match.getTarget().getTypeSet().isIncomingArcOfClan(
						tar.getType(),
						inarc.getType(), 
						inarc.getSource().getType())) {
						
					if (match.getInverseImage(inarc).hasMoreElements()
							&& match.getRule().getImage(match.getInverseImage(inarc).nextElement()) == null) { 						
						ins--;
					}
				}			
			}
//
//			System.out.println("MatchHelper:  Source Max: "
//					+tar.getType().getName()+"   "
//					+a.getType().getName()+"   " +ins+"   "+nn);
			
			if (srcMax.get(indx).intValue() != TypeSet.UNDEFINED
						&& (ins + nn) > srcMax.get(indx).intValue()) {
				errorMsg = "Source multiplicity of edge type failed!"
						+ "\nType  \"" + a.getType().getName() + "\""
						+ " - source maximum failed.";
				return false;
			}
			
			if (i < 0) break;
		}
		return true;
	}

	private static int getIndexOfNode(
			final List<Triple<Node, Type, Type>>list, 
			final Node nodeOfArc, 
			final Type arcType,
			final Type otherType) {
		
		// if p.first is the source of an arc of type p.second == arcType,
		// then p.third is the target of the arc, or wise versa;
		// p.first and p.third are nodes inside of a host graph,
		// but in CPA - a graph which can be the LHS | RHS graph of a rule.
		
		for (int i=0; i<list.size(); i++) {
			final Triple<Node, Type, Type> p = list.get(i);
			
			if (p.first == nodeOfArc
					&& p.second == arcType) {
				
				if (p.third.isInClanOf(otherType)) {
					return i;
				}
				
//				if (otherType.isParentOf(p.third)) {
//					return i;
//				} 
//				// this is in CPA only!
//				else if (!p.first.getContext().isCompleteGraph()
//						&& p.third.isParentOf(otherType)) {
//					return i;
//				}
			}					
		}
		return -1;
	}
	
	private static boolean checkNodeMultiplicity(
			final List<String> typesNeedMultiplicityCheck,
			final Match match,
			final Node go, 
			int startindex,
			final List<Node> todelete, 
			final List<Node> tocreate, 
			final List<GraphObject> nodestoglue,
			int tgchecklevel) {
		
		errorMsg = "";
		if (!typesNeedMultiplicityCheck.contains(go.getType().convertToKey())) {
			return true;			
		}
			
		int min = go.getType().getSourceMin();
		int max = go.getType().getSourceMax();
		int count = 0;
					
		if (match.getTarget().getTypeObjectsMap().get(go.getType().convertToKey()) != null) {
			count = match.getTarget().getTypeObjectsMap().get(go.getType().convertToKey()).size();
		}
		
		count--;
		for (int j = startindex; j < todelete.size(); j++) {
			Node n = todelete.get(j);
			if (go.getType().isRelatedTo(n.getType())) {	
				count--;
				todelete.remove(n);
				j--;
			}
		}
		
		for (int i = 0; i < tocreate.size(); i++) {
			Node n = tocreate.get(i);
			if (go.getType().isRelatedTo(n.getType())) {
				count++;
				tocreate.remove(n);
				i--;
			}
		}
		
		if (max != TypeSet.UNDEFINED
				&& count > max) {
			errorMsg = "Node type multiplicity failed!" + "\nType  \""
						+ go.getType().getName() + "\"" + " - maximum failed.";
			return false;
		}
		if (tgchecklevel > TypeSet.ENABLED_MAX
				&& min > 0
				&& count < min) {
			errorMsg = "Node type multiplicity failed!" + "\nType  \""
						+ go.getType().getName() + "\"" + " - minimum failed.";
			return false;
		}
		
		return true;
	}

	private static boolean checkNodeMaxMultiplicity(
			final List<String> typesNeedMultiplicityCheck,
			final Match match,
			final Node go, 
			int startindex,
			final List<Node> tocreate, 
			final List<GraphObject> nodesofglue, 
			int tgchecklevel) {
		
		errorMsg = "";
		if (!typesNeedMultiplicityCheck.contains(go.getType().convertToKey())) {
			return true;			
		}
		
		int max = go.getType().getSourceMax();
		if (max == TypeSet.UNDEFINED)
			return true;
		
		int count = 0;
		if (match.getTarget().getTypeObjectsMap().get(go.getType().convertToKey()) != null) {	
			count = match.getTarget().getTypeObjectsMap().get(go.getType().convertToKey()).size();
		}
		count++;
		
		for (int c = startindex; c < tocreate.size(); c++) {
			final Node goc = tocreate.get(c);
			if (go.getType().isRelatedTo(goc.getType())) {
				count++;
				tocreate.remove(goc);
				c--;
			}
		}

		if (count > max) {
			errorMsg = "Node type multiplicity failed!" + "\nType  \""
					+ go.getType().getName() + "\"" + " - maximum failed.";
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unused")
	private static boolean checkConstValueWhenLeftAttrUnset(
			final OrdinaryMorphism match, 
			final OrdinaryMorphism cond) {
		boolean result = true;
		final Enumeration<GraphObject> dom = cond.getDomain();
		while (dom.hasMoreElements() && result) {
			final GraphObject goL = dom.nextElement();
			final GraphObject imgC = cond.getImage(goL);
			final GraphObject imgG = match.getImage(goL);

			if (goL.getAttribute() != null 
					&& imgC.getAttribute() != null
					&& imgG.getAttribute() != null) {
				
				final ValueTuple vtL = (ValueTuple) goL.getAttribute();
				final ValueTuple vtC = (ValueTuple) imgC.getAttribute();
				final ValueTuple vtG = (ValueTuple) imgG.getAttribute();
				for (int i = 0; i < vtL.getNumberOfEntries(); i++) {
					final ValueMember vmL = vtL.getValueMemberAt(i);
					final ValueMember vmC = vtC.getValueMemberAt(vmL.getName());
					final ValueMember vmG = vtG.getValueMemberAt(vmL.getName());
					if (!vmL.isSet()
							&& vmC.isSet() && vmC.getExpr().isConstant() 
							&& vmG.isSet() && vmG.getExpr().isConstant()
							&& !vmG.getExprAsText().equals(vmC.getExprAsText())) {
						result = false;
						break;						
					}
				}
			}			
		}
		return result;
	}
	
	private static boolean checkConstantAttrValueFromSourceToTarget(
					final OrdinaryMorphism morph) {
		boolean result = true;
		final Enumeration<GraphObject> dom = morph.getDomain();
		while (dom.hasMoreElements() && result) {
			final GraphObject go = dom.nextElement();
			final GraphObject img = morph.getImage(go);
			
			if (go.getAttribute() != null 
					&& img.getAttribute() != null) {
			
				final ValueTuple vt1 = (ValueTuple) go.getAttribute();
				final ValueTuple vt2 = (ValueTuple) img.getAttribute();
				for (int i = 0; i < vt1.getNumberOfEntries(); i++) {
					final ValueMember vm1 = vt1.getValueMemberAt(i);
					final ValueMember vm2 = vt2.getValueMemberAt(vm1.getName());
					if (vm1.isSet()) { 
						if (vm1.getExpr().isConstant()
								&& vm2 != null && vm2.isSet() 
								&& !vm1.getExprAsText().equals(vm2.getExprAsText())) {
							result = false;
							break;
						}
					}
				}
			}			
		}
		return result;
	}
	
	public static boolean checkVariableToNullMappping(final Match match) {
		if (!match.getSource().isAttributed())
			return true;

		final Rule itsRule = match.getRule();		
		final Vector<ValueMember> varToNull = new Vector<ValueMember>(2);
		
		final VarTuple vars = (VarTuple) match.getAttrContext().getVariables();
		final Enumeration<GraphObject> en = match.getDomain();
		while (en.hasMoreElements()) {
			final GraphObject obj = en.nextElement();
			if (obj.getAttribute() == null)
				continue;
			final GraphObject img = match.getImage(obj);
			final ValueTuple vt = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < vt.getNumberOfEntries(); i++) {
				final ValueMember vmObj = vt.getEntryAt(i);
				if (vmObj.isSet() && vmObj.getExpr().isVariable()) {
					final ValueMember vmImg = ((ValueTuple) img.getAttribute())
							.getEntryAt(i);
					String nullStr = "null";
					if (vmImg.isSet() && vmImg.getExprAsText().equals(nullStr)) {
						varToNull.add(vars.getEntryAt(vmObj.getExprAsText()));
					}
				}
			}
		}
		
		final Iterator<Node> elems = itsRule.getRight().getNodesSet().iterator();
		while (elems.hasNext()) {
			final GraphObject obj = elems.next();
			if (obj.getAttribute() == null)
				continue;
			final ValueTuple vt = (ValueTuple) obj.getAttribute();
			for (int j = 0; j < vt.getNumberOfEntries(); j++) {
				final ValueMember vm = vt.getEntryAt(j);
				if (vm.isSet() && vm.getExpr().isComplex()) {
					final Vector<String> exprVars = vm.getAllVariableNamesOfExpression();
					for (int i = 0; i < varToNull.size(); i++) {
						final VarMember var = (VarMember) varToNull.get(i);
						if (exprVars.contains(var.getName())) {
							return false;
						}
					}
				}
			}
		}
		final Iterator<Arc> elems1 = itsRule.getRight().getArcsSet().iterator();
		while (elems1.hasNext()) {
			final GraphObject obj = elems1.next();
			if (obj.getAttribute() == null)
				continue;
			final ValueTuple vt = (ValueTuple) obj.getAttribute();
			for (int j = 0; j < vt.getNumberOfEntries(); j++) {
				final ValueMember vm = vt.getEntryAt(j);
				if (vm.isSet() && vm.getExpr().isComplex()) {
					final Vector<String> exprVars = vm.getAllVariableNamesOfExpression();
					for (int i = 0; i < varToNull.size(); i++) {
						final VarMember var = (VarMember) varToNull.get(i);
						if (exprVars.contains(var.getName())) {
							return false;
						}
					}
				}
			}
		}
				
		final CondTuple ct = (CondTuple) match.getAttrContext().getConditions();
		for (int j = 0; j < ct.getNumberOfEntries(); j++) {
			final CondMember cm = (CondMember) ct.getEntryAt(j);
			final Vector<String> exprVars = cm.getAllVariables();
			for (int i = 0; i < varToNull.size(); i++) {
				final VarMember var = (VarMember) varToNull.get(i);
				if ((cm.getExprAsText().indexOf(var.getName() + "==null") == -1)
						&& exprVars.contains(var.getName())) {
				}
			}
		}
		return true;
	}

	public static void checkSourceTargetCompatibilityOfEdge(
			final Match match,
			final GraphObject orig, final GraphObject image) 
	throws BadMappingException {
		if (orig.isArc()) {
			try {
				checkEdgeSourceTargetCompatibility(match, orig, image);
			} catch (BadMappingException ex) {				
				throw ex;
			}
			
			// check : rule edge image is a loop and
	 		// match edge image is a loop - OK,
			// otherwise - FAILED!	
			if (match.getRule().getImage(orig) != null) {
				if ((match.getRule().getImage(((Arc) orig).getSource()) 
								== match.getRule().getImage(((Arc) orig).getTarget()))
						&& (((Arc) image).getSource() 
								!= ((Arc) image).getTarget())) {
					errorMsg = "Edge loop: rule- and match-mapping must be source and target compatible.";
					System.out.println(errorMsg);
					System.out.println("required: "+(match.getRule().getImage(((Arc) orig).getSource()) 
							+"  ==  "+match.getRule().getImage(((Arc) orig).getTarget())));
					System.out.println("required: "+(((Arc) image).getSource() 
							+" == "+((Arc) image).getTarget()));
					
					throw new BadMappingException(errorMsg);
				}
			}
		}		
	}

	public static void checkEdgeSourceTargetCompatibility(
			final OrdinaryMorphism morph,
			final GraphObject orig, 
			final GraphObject image) 
	throws BadMappingException {
		errorMsg = "";
		if (orig.isArc() && ((Arc)orig).isDirected()) {
			// check compatibility of mapping with source and target functions:
			final GraphObject aSrc = ((Arc) orig).getSource();
			final GraphObject aTar = ((Arc) orig).getTarget();
			boolean mappingFailed = false;
			if (morph.getImage(aSrc) != null) {
				if (((Arc) image).getSource() != morph.getImage(aSrc)) {
					errorMsg = "Edge mapping must be source compatible.";
//					System.out.println(errorMsg);
//					System.out.println("required: "+ ((Arc) image).getSource()
//							+"  ==  "+morph.getImage(aSrc));
					
					throw new BadMappingException(errorMsg);
				}
			}
			else {
				mappingFailed = true;
			}
			if (morph.getImage(aTar) != null) {
				if (((Arc) image).getTarget() != morph.getImage(aTar)) {
					errorMsg = "Edge mapping must be target compatible.";
//					System.out.println(errorMsg);
//					System.out.println("required: "+ ((Arc) image).getTarget()
//							+"  ==  "+morph.getImage(aTar));
					
					throw new BadMappingException(errorMsg);
				}
			}
			else {
				mappingFailed = true;
			}
			if (mappingFailed){
				errorMsg = "Edge mapping: source and target nodes of an edge must be mapped before.";
				throw new BadMappingException(errorMsg);
			}
		}
	}
	
	/*
	 * Checks whether the parallel arcs of the given Type 
	 * and the specified source and target Node are possible.<br>
	 * The nodes must be contained in the source graph of the given Match. 
	 * The rule of the given Match creates the specified arc.<br>
	 */
	public boolean isParallelArcAllowed(
			final Match m, final Type arct, final Node src, final Node tar) {
		
		if (!m.getOriginal().getTypeSet().isArcParallel()
//				&& m.getRule().isArcCreating(src, arc.getType(), tar)
				) {
			if (!src.hasArc(arct, tar)) {
				// check the graph m.getImage() already contains a such arc
				if (((Node)m.getImage(src)).hasArc(arct, (Node)m.getImage(tar))) {
						return false;
				}
			}
			else if (!m.getRule().isArcDeleting(src, arct, tar)) {
				return false;
			}
		}
		
		return true;
	}
	
/*
	private int getCountOfNodesToGlue(
			final Rule rule,
			final Type nodetype, 
			final Vector<GraphObject> nodesofglue) {
		int c = 0;
		for (int i = 0; i < nodesofglue.size(); i++) {
			GraphObject go = nodesofglue.get(i);
			if (go.getType().isRelatedTo(nodetype)) {
				c--;
				Enumeration<GraphObject> e = rule.getInverseImage(go);
				while (e.hasMoreElements()) {
					e.nextElement();
					c++;
				}
			}
		}
		return c;
	}

	
	private int getCountOfEdgesToGlue(final Match match, final Arc rhsEdge) {
		int c = 0;
		final Vector<GraphObject> srcVec = new Vector<GraphObject>(2);
		final Enumeration<GraphObject> e = match.getRule().getInverseImage(rhsEdge.getSource());
		while (e.hasMoreElements()) {
			srcVec.add(e.nextElement());
		}
		final Vector<GraphObject> tarVec = new Vector<GraphObject>(2);
		final Enumeration<GraphObject> e1 = match.getRule().getInverseImage(rhsEdge.getTarget());
		while (e.hasMoreElements()) {
			tarVec.add(e.nextElement());
		}
		
		if (srcVec.size() == 1 && tarVec.size() == 1) {
			return 1;
		}
		
		if (srcVec.size() > 1) {
			for (int i = 0; i < srcVec.size(); i++) {
				GraphObject go = srcVec.get(i);
				c = c + ((Node)match.getImage(go)).getNumberOfOutgoingArcs(
								rhsEdge.getType(),
								rhsEdge.getTarget().getType());
			}
		} 
//		else if (srcVec.size() == 1 && tarVec.size() > 1) {}
		
		return c;
	}
*/
	
	/**
	 * Computes an overlapping set of the graphs: - this.source with
	 * other.source if other instance of OrdinaryMorphism and left is true, or -
	 * this.target with other.target if other instance of OrdinaryMorphism and
	 * left is false, or - this.sorce with other if other instance of Graph and
	 * left is true, or - this.target with other if other instance of Graph and
	 * left is false, or with respect of the rules if this and other are
	 * instances of Rule.
	 * 
	 * @param union
	 *            If true - the overlappings contain disjunion, too. Enumeration
	 *            elements are of type <code>Pair</code>.
	 * @return A set of overlappings.
	 */
	public static Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(
			final OrdinaryMorphism morph,
			final Object other, boolean left, boolean union) {
		return getOverlappingsVector(morph, other, left, union).elements();
	}

	public static Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappingsVector(
			final OrdinaryMorphism morph,
			final Object other, 
			boolean left,
			boolean union) {
		
		final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		oSet = new Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		
		int minGraphSize = 1;
		if (union)
			minGraphSize = 0;

		Graph left2 = null;

		if ((other instanceof OrdinaryMorphism) && left)
			left2 = ((OrdinaryMorphism) other).getSource();
		else if ((other instanceof OrdinaryMorphism) && !left)
			left2 = ((OrdinaryMorphism) other).getTarget();
		else if (other instanceof Graph)
			left2 = (Graph) other;
		
		if (left2 != null) {
			int sizeOfInclusions = left2.getSize();
			while ((sizeOfInclusions >= (minGraphSize))) {
				Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
				overlapping = getOverlappings(morph, other, left,
									sizeOfInclusions, union);
	
				while (overlapping.hasMoreElements()) {
					oSet.addElement(overlapping.nextElement());
				}
				sizeOfInclusions--;
			}
		}
		return oSet;
	}

	/**
	 * Computes an overlapping set of the graphs: - this.source with
	 * other.source if other instance of OrdinaryMorphism and left is true, or -
	 * this.target with other.target if other instance of OrdinaryMorphism and
	 * left is false, or - this.sorce with other if other instance of Graph and
	 * left is true, or - this.target with other if other instance of Graph and
	 * left is false, or with respect of the rules if this and other are
	 * instances of Rule.
	 * 
	 * @param sizeOfInclusions
	 *            The size of graph object inclusions.
	 * @param union
	 *            If true - the overlappings contain disjunion, too. Enumeration
	 *            elements are of type <code>Pair</code>.
	 * @return A set of overlappings.
	 */
	public static Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(
			final OrdinaryMorphism morph,
			final Object other, boolean left,
			int sizeOfInclusions, boolean union) {
		return getOverlappingsVector(morph, other, left, sizeOfInclusions, union)
				.elements();
	}

	public static Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappingsVector(
			final OrdinaryMorphism morph,
			final Object otherObj, 
			boolean left,
			int sizeOfInclusions, 
			boolean union) {		

		final Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		oSet = new Vector<Pair<OrdinaryMorphism, OrdinaryMorphism>>();
		
		Graph other = null;
		if ((otherObj instanceof OrdinaryMorphism) && left)
			other = ((OrdinaryMorphism) otherObj).getSource();
		else if ((otherObj instanceof OrdinaryMorphism) && !left)
			other = ((OrdinaryMorphism) otherObj).getTarget();
		else if (otherObj instanceof Graph)
			other = (Graph) otherObj;
		if (other == null)
			return oSet;
		
		Vector<OrdinaryMorphism> subs = new Vector<OrdinaryMorphism>();
		Enumeration<GraphObject> itsGOs = null;

		if (left)
			itsGOs = morph.getSource().getElements();
		else
			itsGOs = morph.getTarget().getElements();
		
		final Vector<GraphObject> itsGOSet = new Vector<GraphObject>();
		boolean nextMatch = true;
		int minGraphSize;

		if (union)
			minGraphSize = 0;
		else
			minGraphSize = 1;

		while (itsGOs.hasMoreElements()) {
			itsGOSet.addElement(itsGOs.nextElement());
		}
		if (sizeOfInclusions >= minGraphSize) {
			boolean withIsomorphicInclusions = true;
			subs = BaseFactory.theFactory().generateAllSubgraphsWithInclusionsOfSize(
					morph.getSource(),
					sizeOfInclusions, itsGOSet, subs, 
					withIsomorphicInclusions, null, false);
		} else {
			itsGOSet.clear();
			return oSet;
		}

		Completion_InjCSP strategy = new Completion_InjCSP();

		for (int j = 0; j < subs.size(); j++) {
			OrdinaryMorphism h = subs.elementAt(j);

			Pair<Rule, Pair<OrdinaryMorphism, OrdinaryMorphism>> rulePair = (BaseFactory
					.theFactory()).constructIsomorphicRule(h);
			Rule hr = rulePair.first;

			OrdinaryMorphism isoRHShr = rulePair.second.second;
			
			MorphCompletionStrategy localStrategy = new Completion_InjCSP();

			Match testm = (BaseFactory.theFactory()).createMatch(hr, other,
					true, "1");
			testm.getTarget().setCompleteGraph(false);
			testm.setCompletionStrategy(localStrategy, true);

			OrdinaryMorphism rStar = null;
			Match m = null;
			nextMatch = true;
			while (nextMatch) {
				nextMatch = testm.nextCompletion();
				if (nextMatch) {
					rStar = other.isomorphicCopy();
					if (rStar != null) {
						m = (BaseFactory.theFactory()).createMatch(hr, rStar.getTarget());
						m.doCompose(testm, rStar);
						m.adaptAttrContextValues(testm.getAttrContext());

						// step.execute throws a TypeException when
						// resulting graph does broke the multiplicity condition
						// so we catch this exception and try another match
						try {
							// Variables in work graph are allowed
							OrdinaryMorphism ms = (OrdinaryMorphism) TestStep.execute(m, true);
							if (ms != null) {
								ms.getTarget().setCompleteGraph(false);
								OrdinaryMorphism mStar = isoRHShr.compose(ms); 			
								ms.dispose();
								ms = null;
								if (mStar != null) {
									Rule r1 = null;
									Match m1 = null;
									Pair<OrdinaryMorphism, OrdinaryMorphism> p = null;
									if (morph instanceof Rule) {
										r1 = (Rule) morph;
										m1 = BaseFactory.theFactory().createMatch(
												r1, mStar.getTarget());
										m1.setCompletionStrategy(strategy, true);
										if (!m1.nextCompletion()
												|| !m1.isValid(true)) {
											BaseFactory.theFactory()
													.destroyMorphism(m1);
											m1 = null;
										}
									}
									Rule r2 = null;
									Match m2 = null;
									if (otherObj instanceof Rule) {
										r2 = (Rule) otherObj;
										m2 = BaseFactory.theFactory().createMatch(
												r2, mStar.getTarget());
										m2.setCompletionStrategy(strategy, true);
										if (!m2.nextCompletion()
												|| !m2.isValid(true)) {
											BaseFactory.theFactory()
													.destroyMorphism(m2);
											m2 = null;
										}
									}
									if ((morph instanceof Rule)
											&& (otherObj instanceof Rule)) {
										if ((m1 != null) && (m2 != null)) {
											p = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
													mStar, rStar);
											boolean isIsomorphic = false;
											if (!isIsomorphicOverlapping(oSet, p))
												oSet.addElement(p);
											else
												isIsomorphic = true;
											BaseFactory.theFactory()
													.destroyMorphism(m1);
											m1 = null;
											BaseFactory.theFactory()
													.destroyMorphism(m2);
											m2 = null;
											if (isIsomorphic)
												p = null;
										}
									} else if (otherObj instanceof Rule) {
										if (m2 != null) {
											p = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
													mStar, rStar);
											boolean isIsomorphic = false;
											if (!isIsomorphicOverlapping(oSet, p))
												oSet.addElement(p);
											else
												isIsomorphic = true;
											BaseFactory.theFactory()
													.destroyMorphism(m2);
											m2 = null;
											if (isIsomorphic)
												p = null;
										}
									} else if (morph instanceof Rule) {
										if (m1 != null) {
											p = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
													mStar, rStar);
											boolean isIsomorphic = false;
											if (!isIsomorphicOverlapping(oSet, p)) {
												oSet.addElement(p);
											} else
												isIsomorphic = true;
											BaseFactory.theFactory()
													.destroyMorphism(m1);
											m1 = null;
											if (isIsomorphic)
												p = null;
										}
									} else {
										p = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
												mStar, rStar);
										if (!isIsomorphicOverlapping(oSet, p)) {
											oSet.addElement(p);
										} else
											p = null;
									}
									if (p == null) {
										BaseFactory.theFactory().destroyMorphism(
												mStar);
										mStar = null;
										BaseFactory.theFactory().destroyMorphism(
												rStar);
										rStar = null;
									}
								} else {
									BaseFactory.theFactory().destroyMorphism(rStar);
									rStar = null;
								}
							}
						} catch (TypeException e) {}
					}
				}
			} // while
			subs.removeElement(h);
			j--;

			testm.dispose();
			testm = null;

			// dispose helpers
			localStrategy = null;
			OrdinaryMorphism h1 = rulePair.second.first;
			OrdinaryMorphism h2 = rulePair.second.second;
			Graph tmp3 = h.getSource();
			h1.dispose();
			h2.dispose();
			hr.dispose();
			h.dispose();
			tmp3.dispose();
			tmp3 = null;
			hr = null;
			h = null;
			h1 = null;
			h2 = null;
			rulePair = null;
		}
		deleteTransientContextVariables(morph, morph.getSource());
		deleteTransientContextVariables(morph, morph.getTarget());
		return oSet;
	}

	private static boolean isIsomorphicOverlapping(
			final Vector<Pair<OrdinaryMorphism,OrdinaryMorphism>> overlapGraphs,
			final Pair<OrdinaryMorphism,OrdinaryMorphism> overlapPair) {
		final Graph overlapGraph = overlapPair.first.getTarget();
		for (int j = 0; j < overlapGraphs.size(); j++) {
			Pair<OrdinaryMorphism,OrdinaryMorphism> pj = overlapGraphs.elementAt(j);
			Graph gj = pj.first.getTarget();
			if (gj.isIsomorphicTo(overlapGraph)) {
				if (pj.first.isIsomorphicTo(overlapPair.first)
						&& pj.second.isIsomorphicTo(overlapPair.second))
					return true;
			}
		}
		return false;
	}

	public static void deleteTransientContextVariables(
			final OrdinaryMorphism morph,
			final Graph g) {
		VarTuple vars = (VarTuple) morph.getAttrContext().getVariables();
		Iterator<?> e = g.getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject obj = (GraphObject) e.next();
			if (obj.getAttribute() == null)
				continue;
			ValueTuple value = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < value.getNumberOfEntries(); i++) {
				ValueMember valuem = value.getValueMemberAt(i);
				if (valuem.isTransient()) {
					vars.getTupleType().deleteMemberAt(valuem.getExprAsText());
					valuem.setExpr(null);
				}
			}
		}
		e = g.getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject obj = (GraphObject) e.next();
			if (obj.getAttribute() == null)
				continue;
			ValueTuple value = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < value.getNumberOfEntries(); i++) {
				ValueMember valuem = value.getValueMemberAt(i);
				if (valuem.isTransient()) {
					vars.getTupleType().deleteMemberAt(valuem.getExprAsText());
					valuem.setExpr(null);
				}
			}
		}
	}

	
	public static Vector<CondMember> getConditionsOfNAC(final OrdinaryMorphism nacStar,
			final OrdinaryMorphism nac) {
		final Vector<CondMember> condsNAC = new Vector<CondMember>();
		final VarTuple vart = (VarTuple) nacStar.getAttrContext().getVariables();
//		vart.showVariables();
		if (vart.getSize() == 0)
			return condsNAC;

		final CondTuple condt = (CondTuple) nacStar.getAttrContext().getConditions();
		final Vector<String> varNames = nac.getImage().getVariableNamesOfAttributes();
		final Vector<VarMember> varsOfNAC = new Vector<VarMember>();
		for (int k = 0; k < vart.getSize(); k++) {
			final VarMember vm = vart.getVarMemberAt(k);
			if (varNames.contains(vm.getName())) {
				if (!varsOfNAC.contains(vm))
					varsOfNAC.addElement(vm);
			}
		}
		for (int k = 0; k < condt.getSize(); k++) {
			final CondMember cm = condt.getCondMemberAt(k);
			final Vector<String> condVars = cm.getAllVariables();
			for (int l = 0; l < varsOfNAC.size(); l++) {
				final VarMember vm = varsOfNAC.elementAt(l);
				if (((cm.getMark() == CondMember.NAC) || (cm.getMark() == CondMember.NAC_LHS))
						&& condVars.contains(vm.getName())) {
					if (!condsNAC.contains(cm)) {
						condsNAC.addElement(cm);
					}
				}
			}
		}
		return condsNAC;
	}

	public static Vector<CondMember> getConditionsOfPAC(final OrdinaryMorphism pacStar,
			final OrdinaryMorphism pac) {
		final Vector<CondMember> condsPAC = new Vector<CondMember>();
		final VarTuple vart = (VarTuple) pacStar.getAttrContext().getVariables();
		if (vart.getSize() == 0)
			return condsPAC;

		final CondTuple condt = (CondTuple) pacStar.getAttrContext().getConditions();
		final Vector<String> varNames = pac.getImage().getVariableNamesOfAttributes();
		final Vector<VarMember> varsOfPAC = new Vector<VarMember>();
		for (int k = 0; k < vart.getSize(); k++) {
			VarMember vm = vart.getVarMemberAt(k);
			if (varNames.contains(vm.getName())) {
				if (!varsOfPAC.contains(vm))
					varsOfPAC.addElement(vm);
			}
		}
		for (int k = 0; k < condt.getSize(); k++) {
			final CondMember cm = condt.getCondMemberAt(k);
			final Vector<String> condVars = cm.getAllVariables();
			for (int l = 0; l < varsOfPAC.size(); l++) {
				final VarMember vm = varsOfPAC.elementAt(l);
				if (((cm.getMark() == CondMember.PAC) || (cm.getMark() == CondMember.PAC_LHS))
						&& condVars.contains(vm.getName())) {
					if (!condsPAC.contains(cm)) {
						condsPAC.addElement(cm);
					}
				}
			}
		}
		return condsPAC;
	}
	
	/**
	 * @param nacStar
	 * @param nac
	 * @param condsNAC
	 * @return
	 * 		<code>true</code> if all attribute condition of the given NAC are satisfied, <code>false</code>.
	 */
	public static boolean isAttrConditionOfNACSatisfied(final NACStarMorphism nacStar,
			final OrdinaryMorphism nac, 
			final Vector<CondMember> condsNAC) {
		
//		System.out.println("MatchHelper.isAttrConditionOfNACSatisfied ");
//		((VarTuple)getAttrContext().getVariables()).showVariables();
		
		((VarTuple)nacStar.getAttrContext().getVariables()).propagateValueFromParent();

//		((VarTuple)nacStar.getAttrContext().getVariables()).showVariables();
		
		errorMsg = "NAC  \"" + nac.getName() + "\"  failed.";
		boolean nacCondTrue = false;
		for (int k = 0; k < condsNAC.size(); k++) {
			CondMember cm = condsNAC.elementAt(k);
			if (cm.areVariablesSet()) {
				if (cm.isEnabled() && cm.isTrue()) {
					nacCondTrue = true;
					errorMsg = errorMsg + "\n(Attribute condition  [ "
							+ cm.getExprAsText() + " ]  is satisfied.)";
				} else {
					nacCondTrue = false;
					break;
				}
			} else {
				nacCondTrue = true;
			}
		}
//		System.out.println(nacCondTrue);
		if (!nacCondTrue) {
			errorMsg = "";
			return false;
		}
		return true;
	}
	
	/**
	 * @param pacStar
	 * @param pac
	 * @param condsPAC
	 * @return
	 * 		<code>true</code> if all attribute condition of the given PAC are satisfied, <code>false</code>.
	 */
	public static boolean isAttrConditionOfPACSatisfied(final PACStarMorphism pacStar,
			final OrdinaryMorphism pac, 
			final Vector<CondMember> condsPAC) {
//		((VarTuple)getAttrContext().getVariables()).showVariables();
		
		((VarTuple)pacStar.getAttrContext().getVariables()).propagateValueFromParent();

		// ((VarTuple)pacStar.getAttrContext().getVariables()).showVariables();
		
		errorMsg = "PAC  \"" + pac.getName() + "\"  failed.";
		boolean pacCondTrue = true;
		for (int k = 0; k < condsPAC.size(); k++) {
			CondMember cm = condsPAC.elementAt(k);
			if (cm.areVariablesSet()) {
				if (cm.isEnabled() && !cm.isTrue()) {
					pacCondTrue = false;
					errorMsg = errorMsg + "\n(Attribute condition  [ "
							+ cm.getExprAsText() + " ]  is not satisfied.)";
					break;
				}
			}
		}
		if (pacCondTrue)
			errorMsg = "";
		
		return pacCondTrue;
	}
	
	public static final Morphism checkNACStar(final NACStarMorphism aNACstar,
			final OrdinaryMorphism nac,
			final Match match,
			boolean withVars) {
		// first add mapping of nodes and edges from match
		if (!makePartialNACStar(aNACstar, nac, match)) {
			return null;
		}
		
//		System.out.println("match::  context");
//		((VarTuple)match.getAttrContext().getVariables()).showVariables();
//		System.out.println("NAC::  context");
//		((VarTuple)nac.getAttrContext().getVariables()).showVariables();

//		aNACstar.adaptAttrContextValues(match.getAttrContext());
		
//		System.out.println("NACstar::  context");
//		((VarTuple)aNACstar.getAttrContext().getVariables()).showVariables();
		
		// now that we have a commuting nacstar, try to totalize it:
		Morphism result = null;
		
		if (aNACstar.tryToApplyAttrExpr()) {
			if (!withVars)
				result = makeNACStarCompletion(aNACstar, nac, match);
			else
				result = makeNACStarCompletionWithVars(aNACstar, nac, match);			
		} else {
			errorMsg = "NAC \"" + nac.getName() + "\"  cannot be checked.\n"
						+"An attribute expression of it is not avaluable.";
		}
		aNACstar.resetAttrValueAsExpr();
		return result;
	}
	
	
	/*
	 * If clan morphism of NAC morphism 
	 * into a graph exists, then NAC is found and match has failed.
	 */
	/*
	private boolean checkClanMorphismOfNACStar(
			final NACStarMorphism aNACstar,
			final OrdinaryMorphism nac, 
			final Match match) {
		boolean result = true;
		Iterator<Node> nacNodes = nac.getSource().getNodesSet().iterator();
		while (nacNodes.hasNext() && result) {
			GraphObject leftObj = nacNodes.next();
			GraphObject nacObj = nac.getImage(leftObj);
			if (nacObj != null) {
				GraphObject nacObjImg = aNACstar.getImage(nacObj);
				GraphObject graphObj = match.getImage(leftObj);
				if (nacObjImg == graphObj) {									
					if (leftObj.getType().isParentOf(nacObj.getType())
							&& leftObj.getType().isParentOf(graphObj.getType())
							&& !nacObj.getType().isParentOf(match.getImage(leftObj).getType())) {
						System.out.println("left:  "+leftObj.getType().getName()
								+"   nac:  "+nacObj.getType().getName()
								+"   graph:  "+match.getImage(leftObj).getType().getName());
						result = false;
					}									
				}
			}
		}
		return result;
	}
	*/
	
	private static boolean makePartialNACStar(
			final NACStarMorphism aNACstar,
			final OrdinaryMorphism nac, 
			final Match match) {
//		System.out.println(nac.getName());
//		aNACstar.getCompletionStrategy().showProperties();
		
		((VarTuple)aNACstar.getAttrContext().getVariables()).propagateValueFromParent();
//		((VarTuple)aNACstar.getAttrContext().getVariables()).showVariables();
		
		Iterator<Node> nacNodes = nac.getSource().getNodesSet().iterator();
		while (nacNodes.hasNext()) {
			GraphObject leftObj = nacNodes.next();
			GraphObject nacObj = nac.getImage(leftObj);
			if (nacObj != null) {
				GraphObject nacObjImg = aNACstar.getImage(nacObj);
				if (nacObjImg != null) {
					if (nacObjImg != match.getImage(leftObj))
						return false;
					
					continue;
				}
				try {
					aNACstar.addMapping(nacObj, match.getImage(leftObj));
				} catch (BadMappingException ex) {					
					return false;
				}
			}
		}
		
		Iterator<Arc> nacArcs = nac.getSource().getArcsSet().iterator();
		while (nacArcs.hasNext()) {
			GraphObject leftObj = nacArcs.next();
			GraphObject src = ((Arc) leftObj).getSource();
			GraphObject tar = ((Arc) leftObj).getTarget();
			GraphObject nacObj = nac.getImage(leftObj);
			if (nacObj != null) {
				GraphObject nacObjSrc = nac.getImage(src);
				GraphObject nacObjTar = nac.getImage(tar);
				if (nacObjSrc != null && nacObjTar != null) {
					GraphObject nacObjImg = aNACstar.getImage(nacObj);
					if (nacObjImg != null) {
						if (nacObjImg != match.getImage(leftObj))
							return false;
						
						continue;
					}
					try {
						aNACstar.addMapping(nacObj, match.getImage(leftObj));
					} catch (BadMappingException ex) {
						return false;
					}
				}
			}
		}
		
		if (aNACstar.getSize() > 0) {
			if (!aNACstar.checkConstants()) {
				return false;
			}
			
//			((VarTuple)match.getAttrContext().getVariables()).showVariables();
//			((VarTuple)aNACstar.getAttrContext().getVariables()).showVariables();
			
			aNACstar.setPartialMorphismCompletion(true);
		}
		return true;
	}

	private static Morphism makeNACStarCompletion(
			final NACStarMorphism nacStar,
			final OrdinaryMorphism nac, 
			final Match match) {
						
		errorMsg = "";	
		Morphism result = null;
		boolean attrCondsExist = !((CondTuple) match.getAttrContext()
											.getConditions()).isEmpty();		
		Vector<CondMember> condsNAC = getConditionsOfNAC(nacStar, nac);
		boolean attrCondsNacExist = !condsNAC.isEmpty();

		if (nacStar.isTotal()) {
			// now check attribute conditions
			if (!attrCondsExist) {
				if (//checkConstValueWhenLeftAttrUnset(match, nac) &&
						 checkConstantAttrValueFromSourceToTarget(nacStar)) {
					result = nacStar;
				}
			}
			else if (!attrCondsNacExist
					|| isAttrConditionOfNACSatisfied(nacStar, nac, condsNAC)) {
				result = nacStar;
			} 
			unsetVariablesOfNAC(match.getAttrContext(), nac);
			unsetVariablesOfNAC(nacStar.getAttrContext(), nac);
			nacStar.propagateValueFromParentAsInputParameter((VarTuple) 
						match.getAttrContext().getVariables(), false);		
			return result;
		}

		// NACstar is NOT total, so next completion is needed		
		while (nacStar.nextCompletion()) {
//			if (!nacStar.getCompletionStrategy().hasRelatedInstanceVarMap()
//					|| match.getTarget().isAttributed()) {
//				if (checkNACStarCodomain(nacStar, nac, match)) {
//					result = nacStar;
//				} 
//			} else {
//				result = nacStar;
//			}
			if (checkNACStarCodomain(nacStar, nac, match)) {
				result = nacStar;
			}
			if (result != null) {
				// now check attribute conditions
				if (!attrCondsExist || !attrCondsNacExist
						|| isAttrConditionOfNACSatisfied(nacStar, nac, condsNAC)) {
					break;
				} 
				else {
					result = null;
				}
			}
			unsetVariablesOfNAC(match.getAttrContext(), nac);
			unsetVariablesOfNAC(nacStar.getAttrContext(), nac);
		} 
		
		unsetVariablesOfNAC(match.getAttrContext(), nac);
		unsetVariablesOfNAC(nacStar.getAttrContext(), nac);
		nacStar.propagateValueFromParentAsInputParameter((VarTuple) 
				match.getAttrContext().getVariables(), false);

		return result;
	}
	
	private static Morphism makeNACStarCompletionWithVars(
			final NACStarMorphism nacStar,
			final OrdinaryMorphism nac, 
			final Match match) {

		errorMsg = "";
		Morphism result = null;

		boolean attrCondsExist = !((CondTuple) match.getAttrContext()
												.getConditions()).isEmpty();		
		Vector<CondMember> condsNAC = getConditionsOfNAC(nacStar, nac);
		boolean attrCondsNacExist = !condsNAC.isEmpty();

		if (nacStar.isTotal()) {
			result = nacStar;
			// now check attribute conditions
			if (!attrCondsExist) {
				if (checkConstantAttrValueFromSourceToTarget(nacStar)) {
					result = nacStar;
				} 
			} 
			else if (!attrCondsNacExist
					|| isAttrConditionOfNACSatisfied(nacStar, nac, condsNAC)) {
				result = nacStar;
			} 
			unsetVariablesOfNAC(match.getAttrContext(), nac);
			unsetVariablesOfNAC(nacStar.getAttrContext(), nac);
			nacStar.propagateValueFromParentAsInputParameter((VarTuple) 
					match.getAttrContext().getVariables(), false);
			return result;			
		}

		// NACstar is NOT total, so next completion is needed
		while (nacStar.nextCompletion()) {			
			if (checkNACStarCodomain(nacStar, nac, match)) {
				result = nacStar;
			}
			
			if (result != null) {
				// now check attribute conditions
				if (!attrCondsExist || !attrCondsNacExist
						|| isAttrConditionOfNACSatisfied(nacStar, nac, condsNAC)) {
					break;
				} else {
					result = null;
				}
			}
			unsetVariablesOfNAC(match.getAttrContext(), nac);
			unsetVariablesOfNAC(nacStar.getAttrContext(), nac);
		} 
		unsetVariablesOfNAC(match.getAttrContext(), nac);
		unsetVariablesOfNAC(nacStar.getAttrContext(), nac);
		nacStar.propagateValueFromParentAsInputParameter((VarTuple) 
					match.getAttrContext().getVariables(), false);		
		return result;
	}
	
	
	// check injectivity and commutativity of
	// NAC Star co-domain ( N -> G must be injective)
	private static boolean checkNACStarCodomain(
			final NACStarMorphism aNACstar,
			final OrdinaryMorphism nac, 
			final Match match) {		

//		List<GraphObject> gos = new Vector<GraphObject>(match.getSource().getNodesCount());
		Iterator<?> e = aNACstar.getSource().getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject nacObj = (GraphObject) e.next();
			if (nac.hasInverseImage(nacObj)) {
				// check commutativity
				GraphObject leftObj = nac.getInverseImage(nacObj).nextElement();
				GraphObject nacStarImgObj = aNACstar.getImage(nacObj);
				GraphObject graphObj = match.getImage(leftObj);
				if (nacStarImgObj != graphObj) {
					return false;
				} 
//				else if (aNACstar.getInverseImageList(graphObj).size() > 1) {
//					return false;
//				}
//				gos.add(graphObj);
			} 
			else {
				// check injectivity
				GraphObject nacStarImgObj = aNACstar.getImage(nacObj);
				if (match.hasInverseImage(nacStarImgObj)) {
					// co-domain of match contains additional nacStar obj
					return false;
				}
			}
		}
		// check injectivity of rest of m 
//		Enumeration<GraphObject> mcodom = match.getCodomain();
//		while (mcodom.hasMoreElements()) {
//			GraphObject go = mcodom.nextElement();
//			if (go.isNode() && !gos.contains(go)) {
//				if (match.getInverseImageList(go).size() > 1)
//					return false;
//			}
//		}
			
		e = aNACstar.getSource().getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject nacObj = (GraphObject) e.next();
			
			if (nac.hasInverseImage(nacObj)) {
				// check commutativity
				GraphObject leftObj = nac.getInverseImage(nacObj).nextElement();
				GraphObject nacStarImgObj = aNACstar.getImage(nacObj);
				GraphObject graphObj = match.getImage(leftObj);
				if (nacStarImgObj != graphObj) {
					return false;
				}
			} 
			else {
				// check injectivity
				GraphObject nacStarImgObj = aNACstar.getImage(nacObj);
				if (match.hasInverseImage(nacStarImgObj)) {
					// co-domain of match contains additional nacStar obj
					return false;
				}
			}
		}
		
		return true;
	}
		
		
	public static final Morphism checkPACStar(final PACStarMorphism aPACstar,
			final OrdinaryMorphism pac, 
			final Match match,
			boolean withVars) {
		// first add mapping of nodes and edges from match
		if (!makePartialPACStar(aPACstar, pac, match)) {
			return null;
		}
		
//		aPACstar.adaptAttrContextValues(match.getAttrContext());
		
		// now that we have a commuting pacstar, try to totalize it:
		Morphism result = null;
		
		if (aPACstar.tryToApplyAttrExpr()) {
			if (!withVars)
				result = makePACStarCompletion(aPACstar, pac, match);
			else
				result = makePACStarCompletionWithVars(aPACstar, pac, match);			
		} else {
			errorMsg = "PAC \"" + pac.getName() + "\"  cannot be checked.\n"
			+"An attribute expression of it is not avaluable.";
		}
		aPACstar.resetAttrValueAsExpr();
		return result;
	}

	// check injectivity and commutativity of
	// PAC Star co-domain ( P -> G must be injective)
	private static boolean checkPACStarCodomain(
			final PACStarMorphism aPACstar,
			final OrdinaryMorphism pac, 
			final Match match) {
		
//		List<GraphObject> gos = new Vector<GraphObject>(match.getSource().getNodesCount());
		Iterator<?> e = aPACstar.getSource().getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject pacObj = (GraphObject) e.next();
			if (pac.getInverseImage(pacObj).hasMoreElements()) {
				// check commutativity
				GraphObject leftObj = pac.getInverseImage(pacObj).nextElement();
				GraphObject pacStarImgObj = aPACstar.getImage(pacObj);
				GraphObject graphObj = match.getImage(leftObj);
				if (pacStarImgObj != graphObj) {
					return false;
				}
//				else if (aPACstar.getInverseImageList(graphObj).size() > 1) {
//					return false;
//				}
//				gos.add(graphObj);
			} 
			else {
				// check injectivity
				GraphObject pacStarImgObj = aPACstar.getImage(pacObj);
				if (match.getInverseImage(pacStarImgObj).hasMoreElements()) {
					return false;
				}
			}
		}
		// check injectivity of rest of m 
//		Enumeration<GraphObject> mcodom = match.getCodomain();
//		while (mcodom.hasMoreElements()) {
//			GraphObject go = mcodom.nextElement();
//			if (go.isNode() && !gos.contains(go)) {
//				if (match.getInverseImageList(go).size() > 1)
//					return false;
//			}
//		}
		
		e = aPACstar.getSource().getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject pacObj = (GraphObject) e.next();
			if (pac.getInverseImage(pacObj).hasMoreElements()) {
				// check commutativity
				GraphObject leftObj = pac.getInverseImage(pacObj).nextElement();
				GraphObject pacStarImgObj = aPACstar.getImage(pacObj);
				GraphObject graphObj = match.getImage(leftObj);
				if (pacStarImgObj != graphObj) {
					return false;
				}
			} 
			else {
				// check injectivity
				GraphObject pacStarImgObj = aPACstar.getImage(pacObj);
				if (match.getInverseImage(pacStarImgObj).hasMoreElements()) {
					return false;
				}
			}
		}
		return true;
	}

	
	private static Morphism makePACStarCompletion(
			final PACStarMorphism pacStar,
			final OrdinaryMorphism pac, 
			final Match match) {
		
		errorMsg = "";
		Morphism result = null;

//		((CondTuple) match.getAttrContext().getConditions()).showConditions();
		boolean attrCondsExist = !((CondTuple) match.getAttrContext()
										.getConditions()).isEmpty();		
		Vector<CondMember> condsPAC = getConditionsOfPAC(pacStar, pac);
		boolean attrCondsPacExist = !condsPAC.isEmpty();

		if (pacStar.isTotal()) {
			// now check attribute conditions
			if (!attrCondsExist) {
				if (checkConstantAttrValueFromSourceToTarget(pacStar)) {
					result = pacStar;
				}
			}
			else if (!attrCondsPacExist 
					|| isAttrConditionOfPACSatisfied(pacStar, pac, condsPAC)) {
				result = pacStar;
			}
			unsetVariablesOfPAC(match.getAttrContext(), pac);
			unsetVariablesOfPAC(pacStar.getAttrContext(), pac);
			pacStar.propagateValueFromParentAsInputParameter((VarTuple) 
						match.getAttrContext().getVariables(), false);
			return result;
		}

		// PACstar is NOT total, so next completion is needed
		while (pacStar.nextCompletion()) {
//			if (!pacStar.getCompletionStrategy().hasRelatedInstanceVarMap() 
//					|| match.getTarget().isAttributed()) {
//				if (checkPACStarCodomain(pacStar, pac, match)) {
//					result = pacStar;
//				}
//			} else {
//				result = pacStar;
//			}
			if (checkPACStarCodomain(pacStar, pac, match)) {
				result = pacStar;
			}
			if (result != null) {
				// now check attribute conditions
				if (!attrCondsExist || !attrCondsPacExist
						|| isAttrConditionOfPACSatisfied(pacStar, pac, condsPAC)) {
					break;
				} else {
					result = null;
				}
			}
			unsetVariablesOfPAC(match.getAttrContext(), pac);
			unsetVariablesOfPAC(pacStar.getAttrContext(), pac);
		}
		
		unsetVariablesOfPAC(match.getAttrContext(), pac);
		unsetVariablesOfPAC(pacStar.getAttrContext(), pac);
		pacStar.propagateValueFromParentAsInputParameter((VarTuple) match.getAttrContext().getVariables(), false);
		
		return result;
	}

	

	private static Morphism makePACStarCompletionWithVars(final PACStarMorphism pacStar,
			final OrdinaryMorphism pac, final Match match) {
		
		errorMsg = "";
		Morphism result = null;

		boolean attrCondsExist = !((CondTuple) match.getAttrContext()
												.getConditions()).isEmpty();		
		Vector<CondMember> condsPAC = getConditionsOfPAC(pacStar, pac);
		boolean attrCondsPacExist = !condsPAC.isEmpty();

		if (pacStar.isTotal()) {
			result = pacStar;
			// now check attribute conditions
			if (!attrCondsExist) {
				if (checkConstantAttrValueFromSourceToTarget(pacStar)) {
					result = pacStar;
				} 
			} 
			else if (!attrCondsPacExist
					|| isAttrConditionOfPACSatisfied(pacStar, pac, condsPAC)) {
				result = pacStar;
			} 
			unsetVariablesOfPAC(match.getAttrContext(), pac);
			unsetVariablesOfPAC(pacStar.getAttrContext(), pac);
			pacStar.propagateValueFromParentAsInputParameter((VarTuple) 
						match.getAttrContext().getVariables(), false);
			return null;
		}

		// PACstar is NOT total, so next completion is need
		while (pacStar.nextCompletion()) {
			if (checkPACStarCodomain(pacStar, pac, match)) {
				result = pacStar;
			}

			if (result != null) {
				// now check attribute conditions
				if (!attrCondsExist || !attrCondsPacExist
						|| isAttrConditionOfPACSatisfied(pacStar, pac, condsPAC)) {
					break;
				} else {
					result = null;
				}
			}
			unsetVariablesOfPAC(match.getAttrContext(), pac);
			unsetVariablesOfPAC(pacStar.getAttrContext(), pac);
		}
		unsetVariablesOfPAC(match.getAttrContext(), pac);
		unsetVariablesOfPAC(pacStar.getAttrContext(), pac);
		pacStar.propagateValueFromParentAsInputParameter((VarTuple) 
					match.getAttrContext().getVariables(), false);	
		return result;
	}

	private static boolean makePartialPACStar(final PACStarMorphism aPACstar,
			final OrdinaryMorphism pac, final Match match) {

		((VarTuple)aPACstar.getAttrContext().getVariables()).propagateValueFromParent();
//		((VarTuple)aPACstar.getAttrContext().getVariables()).showVariables();
		
		Iterator<Node> pacNodes = pac.getSource().getNodesSet().iterator();
		while (pacNodes.hasNext()) {
			GraphObject leftObj = pacNodes.next();
			GraphObject pacObj = pac.getImage(leftObj);
			if (pacObj != null) {
				GraphObject pacObjImg = aPACstar.getImage(pacObj);
				if (pacObjImg != null) {
					if (pacObjImg != match.getImage(leftObj))
						return false;
					
					continue;
				}
				try {
					aPACstar.addMapping(pacObj, match.getImage(leftObj));
				} catch (BadMappingException ex) {
					return false;
				} 
			}
		}
		Iterator<Arc> pacArcs = pac.getSource().getArcsSet().iterator();
		while (pacArcs.hasNext()) {
			GraphObject leftObj = pacArcs.next();
			GraphObject src = ((Arc) leftObj).getSource();
			GraphObject tar = ((Arc) leftObj).getTarget();
			GraphObject pacObj = pac.getImage(leftObj);
			if (pacObj != null) {
				GraphObject pacObjSrc = pac.getImage(src);
				GraphObject pacObjTar = pac.getImage(tar);
				if (pacObjSrc != null && pacObjTar != null) {
					GraphObject pacObjImg = aPACstar.getImage(pacObj);
					if (pacObjImg != null) {
						if (pacObjImg != match.getImage(leftObj))
							return false;
						
						continue;
					}
					try {
						aPACstar.addMapping(pacObj, match.getImage(leftObj));
					} catch (BadMappingException ex) {
						return false;
					}
				}				
			}
		}
		if (aPACstar.getSize() > 0) {
			if (!aPACstar.checkConstants()) {
				return false;
			}
			aPACstar.setPartialMorphismCompletion(true);
		}
		return true;
	}

	
	protected static void unsetVariablesOfNAC(final AttrContext attrContext, 
			final OrdinaryMorphism nac) {
		final VarTuple vars = (VarTuple)attrContext.getVariables();
		final Vector<String> nacVars = nac.getTarget().getVariableNamesOfAttributes();
		for (int i = 0; i < vars.getSize(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			if (nacVars.contains(vm.getName())
					&& (vm.getMark() == VarMember.NAC)
					&& !vm.isInputParameter()) {
				((ContextView)attrContext).removeValue(vm.getName());
			}
		}
	}

	protected static void unsetVariablesOfPAC(final AttrContext attrContext, 
			final OrdinaryMorphism pac) {
		final VarTuple vars = (VarTuple)attrContext.getVariables();
		final Vector<String> pacVars = pac.getTarget().getVariableNamesOfAttributes();
		for (int i = 0; i < vars.getSize(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			if (pacVars.contains(vm.getName())
					&& (vm.getMark() == VarMember.PAC)
					&& !vm.isInputParameter()) {
				((ContextView)attrContext).removeValue(vm.getName());
			}
		}
	}
	
	public static NACStarMorphism createNACstar(final OrdinaryMorphism nac, final Match match) {
		// System.out.println("Match.createNACstar ...
		// "+((Completion_NAC)this.getCompletionStrategy()).getSourceStrategy());

		NACStarMorphism aNACstar = new NACStarMorphism(nac.getImage(), match
				.getImage(), match.getAttrManager().newContext(
				AttrMapping.MATCH_MAP, match.getAttrContext()), match);

		aNACstar.itsNAC = nac;
		aNACstar.setName("NACstar");

		MorphCompletionStrategy strat = new Completion_InjCSP();
//		 strat.showProperties();

		aNACstar.setCompletionStrategy(strat, true);
		
		return aNACstar;
	}

	public static NACStarMorphism createNACstar(final OrdinaryMorphism nac, final Match match,
			boolean withVars) {
		if (withVars) {
			// ((AttrTupleManager)AttrTupleManager.getDefaultManager()).setVariableContext(true);
			ContextView attrcontxt = (ContextView) match.getAttrManager()
					.newContext(AttrMapping.PLAIN_MAP, match.getAttrContext());
			attrcontxt.setVariableContext(true);
			((ContextView) ((CondTuple) attrcontxt.getConditions())
					.getContext()).setVariableContext(true);

			NACStarMorphism aNACstar = new NACStarMorphism(nac.getImage(), match
					.getImage(), attrcontxt, match);
			
			aNACstar.itsNAC = nac;
			aNACstar.setName("NACstar");

			MorphCompletionStrategy strat = new Completion_InjCSP();
//			strat.showProperties();
			 
			aNACstar.setCompletionStrategy(strat, true);
			return aNACstar;
		} 
		return createNACstar(nac, match);
	}

	
	public static PACStarMorphism createPACstar(final OrdinaryMorphism pac, final Match match) {
		final PACStarMorphism aPACstar = new PACStarMorphism(pac.getImage(), match
				.getImage(), match.getAttrManager().newContext(
				AttrMapping.MATCH_MAP, match.getAttrContext()), match);

		aPACstar.itsPAC = pac;
		aPACstar.setName("PACstar");

		MorphCompletionStrategy strat = new Completion_InjCSP();
//		 strat.showProperties();

		aPACstar.setCompletionStrategy(strat, true);
		return aPACstar;
	}

	public static  PACStarMorphism createPACstar(final OrdinaryMorphism pac, final Match match,
			boolean withVars) {
		if (withVars) { // ((AttrTupleManager)AttrTupleManager.getDefaultManager()).setVariableContext(true);
			ContextView attrcontxt = (ContextView) match.getAttrManager()
					.newContext(AttrMapping.PLAIN_MAP, match.getAttrContext());
			attrcontxt.setVariableContext(true);
			((ContextView) ((CondTuple) attrcontxt.getConditions())
					.getContext()).setVariableContext(true);

			PACStarMorphism aPACstar = new PACStarMorphism(pac.getImage(), match
					.getImage(), attrcontxt, match);

			aPACstar.itsPAC = pac;
			aPACstar.setName("PACstar");

			MorphCompletionStrategy strat = new Completion_InjCSP();
//			 strat.showProperties();
			 
			aPACstar.setCompletionStrategy(strat, true);
			return aPACstar;
		} 
		
		return createPACstar(pac, match);
	}

	public static PACStarMorphism createNestedACstar(
			final NestedApplCond ac, 
			final OrdinaryMorphism relMorph) {
		
		final PACStarMorphism star = new PACStarMorphism(ac.getImage(), relMorph
				.getImage(), relMorph.getAttrManager().newContext(
				AttrMapping.MATCH_MAP, relMorph.getAttrContext()), relMorph);

		star.itsPAC = ac;
		star.setName("NestedACstar");

		MorphCompletionStrategy strat = new Completion_InjCSP();
//		strat.showProperties();

		star.setCompletionStrategy(strat, true);
		return star;
	}
	
	public static final Morphism checkGACStar(
			final PACStarMorphism acStar,
			final NestedApplCond acMorph, 
			final OrdinaryMorphism relatedMorph,
			boolean withVars) {
		 
		if (!makePartialNestedACStar(acStar, acMorph, relatedMorph)) {			
			return null;
		}
		
//		acStar.adaptAttrContextValues(match.getAttrContext());
		
		// try to complete it
		Morphism result = null;	
		if (acStar.tryToApplyAttrExpr()) {
//			if (!withVars)
				result = makeGACStarCompletion(acStar, acMorph, relatedMorph);
//			else
//				result = makeNestedACStarCompletionWithVars(acStar, ac, relatedMorph);
		} else {
			errorMsg = "Appl Cond \"" + acMorph.getName() + "\"  cannot be checked.\n"
			+"An attribute expression of it is not avaluable.";
		}
		acStar.resetAttrValueAsExpr();
		return result;
	}
		
	private static boolean makePartialNestedACStar(
			final PACStarMorphism acStar,
			final OrdinaryMorphism ac, 
			final OrdinaryMorphism match) {

		((VarTuple)acStar.getAttrContext().getVariables()).propagateValueFromParent();
//		((VarTuple)acStar.getAttrContext().getVariables()).showVariables();
		
		Iterator<Node> pacNodes = ac.getSource().getNodesSet().iterator();
		while (pacNodes.hasNext()) {
			GraphObject leftObj = pacNodes.next();
			GraphObject pacObj = ac.getImage(leftObj);
			if (pacObj != null) {
				GraphObject pacObjImg = acStar.getImage(pacObj);
				if (pacObjImg != null) {
					if (pacObjImg != match.getImage(leftObj))
						return false;
					
					continue;
				}
				try {
					acStar.addMapping(pacObj, match.getImage(leftObj));
				} catch (BadMappingException ex) {
					return false;
				} 
			}
		}
		Iterator<Arc> pacArcs = ac.getSource().getArcsSet().iterator();
		while (pacArcs.hasNext()) {
			GraphObject leftObj = pacArcs.next();
			GraphObject src = ((Arc) leftObj).getSource();
			GraphObject tar = ((Arc) leftObj).getTarget();
			GraphObject pacObj = ac.getImage(leftObj);
			if (pacObj != null) {
				GraphObject pacObjSrc = ac.getImage(src);
				GraphObject pacObjTar = ac.getImage(tar);
				if (pacObjSrc != null && pacObjTar != null) {
					GraphObject pacObjImg = acStar.getImage(pacObj);
					if (pacObjImg != null) {
						if (pacObjImg != match.getImage(leftObj))
							return false;
						
						continue;
					}
					try {
						acStar.addMapping(pacObj, match.getImage(leftObj));
					} catch (BadMappingException ex) {
						return false;
					}
				}				
			}
		}
		if (acStar.getSize() > 0) {
			if (!acStar.checkConstants()) {
				return false;
			}
			acStar.setPartialMorphismCompletion(true);
		}
		return true;
	}
	
	private static Morphism makeGACStarCompletion(
			final PACStarMorphism acStar,
			final NestedApplCond acMorph, 
			final OrdinaryMorphism relatedMorph) {
		
		errorMsg = "";
		OrdinaryMorphism result = null;
//		boolean attrCondsExist = !((CondTuple) relatedMorph.getAttrContext()
//										.getConditions()).isEmpty();		
		Vector<CondMember> condsAC = getConditionsOfNestedAC(acStar, acMorph);
		boolean attrCondsACexist = !condsAC.isEmpty();
		
		if (acStar.isTotal()) {
			// check attribute conditions and (nested) appl conds
			if (checkConstantAttrValueFromSourceToTarget(acStar)
					&& (!attrCondsACexist 
							|| isAttrConditionOfNestedACSatisfied(acStar, acMorph, condsAC))) {
				acMorph.setCoMorphism(acStar);
				if (acMorph.evalFormula(relatedMorph.getTarget())) {
//					System.out.println(acMorph.getName()+"     "+acMorph.getFormulaText()+"    TRUE");
					result = acStar;
				} else {
//					System.out.println(acMorph.getName()+"     "+acMorph.getFormulaText()+"    FALSE");
					result = null;
					acMorph.setCoMorphism(null);
				}
			}			
			unsetVariablesOfNestedAC(relatedMorph.getAttrContext(), acMorph);
			unsetVariablesOfNestedAC(acStar.getAttrContext(), acMorph);
			acStar.propagateValueFromParentAsInputParameter((VarTuple) 
							relatedMorph.getAttrContext().getVariables(), false);			
			return result;
		}
		
		boolean complFound = false;
		// ACstar is NOT total, so next completion is needed
		while (acStar.nextCompletion()) {
			if (checkGACStarCodomain(acStar, acMorph)) {
				// check attribute conditions  and (nested) appl conds
				if ((!attrCondsACexist || isAttrConditionOfNestedACSatisfied(acStar, acMorph, condsAC))) {
					complFound = true;
					acMorph.setCoMorphism(acStar);	
					if (acMorph.evalFormula(acStar.getTarget())) {
//						System.out.println(acMorph.getName()+"     "+acMorph.getFormulaText()+"    TRUE");
						result = acStar;
						acMorph.setCoMorphism(result);						
						if (!acMorph.forall) 
							break;
					} else {
//						System.out.println(acMorph.getName()+"     "+acMorph.getFormulaText()+"    FALSE");
						result = null;
						acMorph.setCoMorphism(null);
						if (acMorph.forall)
							break;
					}
				} 
							
				unsetVariablesOfNestedAC(relatedMorph.getAttrContext(), acMorph);
				unsetVariablesOfNestedAC(acStar.getAttrContext(), acMorph);
			}
		}
		unsetVariablesOfNestedAC(relatedMorph.getAttrContext(), acMorph);
		unsetVariablesOfNestedAC(acStar.getAttrContext(), acMorph);
		acStar.propagateValueFromParentAsInputParameter((VarTuple) 
				relatedMorph.getAttrContext().getVariables(), false);	
		if (!complFound) {
			if (acMorph.forall)		
				result = acStar;
		}
		return result;
	}

	public static Vector<CondMember> getConditionsOfNestedAC(final OrdinaryMorphism acStar,
			final OrdinaryMorphism ac) {
		final Vector<CondMember> condsAC = new Vector<CondMember>();
		final VarTuple vart = (VarTuple) acStar.getAttrContext().getVariables();
		if (vart.getSize() == 0)
			return condsAC;

		final CondTuple condt = (CondTuple) acStar.getAttrContext().getConditions();
		final Vector<String> varNames = ac.getImage().getVariableNamesOfAttributes();
		final Vector<VarMember> varsOfAC = new Vector<VarMember>();
		for (int k = 0; k < vart.getSize(); k++) {
			VarMember vm = vart.getVarMemberAt(k);
			if (varNames.contains(vm.getName())) {
				if (!varsOfAC.contains(vm))
					varsOfAC.addElement(vm);
			}
		}
		for (int k = 0; k < condt.getSize(); k++) {
			final CondMember cm = condt.getCondMemberAt(k);
			final Vector<String> condVars = cm.getAllVariables();
			for (int l = 0; l < varsOfAC.size(); l++) {
				final VarMember vm = varsOfAC.elementAt(l);
				if (((cm.getMark() == CondMember.PAC) || (cm.getMark() == CondMember.PAC_LHS)
						|| (cm.getMark() == CondMember.NAC) || (cm.getMark() == CondMember.NAC_LHS))
						&& condVars.contains(vm.getName())) {
					if (!condsAC.contains(cm)) {
						condsAC.addElement(cm);
					}
				}
			}
		}
		return condsAC;
	}
	
	public static boolean isAttrConditionOfNestedACSatisfied(final PACStarMorphism acStar,
			final OrdinaryMorphism ac, 
			final Vector<CondMember> condsAC) {
//		((VarTuple)getAttrContext().getVariables()).showVariables();
		
		((VarTuple)acStar.getAttrContext().getVariables()).propagateValueFromParent();

		// ((VarTuple)pacStar.getAttrContext().getVariables()).showVariables();
		
		errorMsg = "Appl Cond  \"" + ac.getName() + "\"  failed.";
		boolean acCondTrue = true;
		for (int k = 0; k < condsAC.size(); k++) {
			CondMember cm = condsAC.elementAt(k);
			if (cm.areVariablesSet()) {
				if (cm.isEnabled() && !cm.isTrue()) {
					acCondTrue = false;
					errorMsg = errorMsg + "\n(Attribute condition  [ "
							+ cm.getExprAsText() + " ]  is not satisfied.)";
					break;
				}
			}
		}
		if (acCondTrue)
			errorMsg = "";
		
		return acCondTrue;
	}
	
	@SuppressWarnings("unused")
	private static boolean checkACStarCodomain(
			final PACStarMorphism acStar,
			final OrdinaryMorphism acMorph, 
			final OrdinaryMorphism match) {
		Iterator<?> e = acStar.getSource().getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject acObj = (GraphObject) e.next();
			if (acMorph.getInverseImage(acObj).hasMoreElements()) {
				// check commutativity
				GraphObject leftObj = acMorph.getInverseImage(acObj).nextElement();
				GraphObject acStarImgObj = acStar.getImage(acObj);
				GraphObject graphObj = match.getImage(leftObj);
				if (acStarImgObj != graphObj) {
					return false;
				}
			} 
			else {
				// check injectivity
				GraphObject acStarImgObj = acStar.getImage(acObj);
				if (match.getInverseImage(acStarImgObj).hasMoreElements()) {
					return false;
				}
			}
		}
		e = acStar.getSource().getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject pacObj = (GraphObject) e.next();
			if (acMorph.getInverseImage(pacObj).hasMoreElements()) {
				// check commutativity
				GraphObject leftObj = acMorph.getInverseImage(pacObj).nextElement();
				GraphObject acStarImgObj = acStar.getImage(pacObj);
				GraphObject graphObj = match.getImage(leftObj);
				if (acStarImgObj != graphObj) {
					return false;
				}
			} 
//			else {
//				// check injectivity
//				GraphObject acStarImgObj = acStar.getImage(pacObj);
//				if (match.getInverseImage(acStarImgObj).hasMoreElements()) {
//					return false;
//				}
//			}
		}
		return true;
	}
	
	private static boolean checkElemOfNestedCodomain(
			final GraphObject go,
			final NestedApplCond acMorph,
			final PACStarMorphism acStar) {
		
		// check related co-domain
		if (acMorph.getRelatedMorphism().getCodomainObjects().contains(acStar.getImage(go))) {
			return false;
		}
		// check parent related co-domain	
		NestedApplCond  p = acMorph.getParent();
		while (p != null) {
			if (p.getRelatedMorphism().getCodomainObjects().contains(acStar.getImage(go))) {				
				return false;
			}
			p = p.getParent();	
		}				
		return true;
	}
	
	private static boolean checkGACStarCodomain(
			final PACStarMorphism acStar,
			final NestedApplCond acMorph) {
		
		boolean result = true; 
		// check nodes of ac(L)\L) at (G\match(L)
		Iterator<Node> elems = acMorph.getTarget().getNodesSet().iterator();
		while(elems.hasNext() && result) {			
			GraphObject go = elems.next();
			if (!acMorph.getInverseImage(go).hasMoreElements()) {
				result = checkElemOfNestedCodomain(go, acMorph, acStar);
			}
		}
		// check edges of ac(L)\L) at (G\match(L)
		Iterator<Arc> elems1 = acMorph.getTarget().getArcsSet().iterator();
		while(elems1.hasNext() && result) {
			GraphObject go = elems1.next();
			if (!acMorph.getInverseImage(go).hasMoreElements()) {
				result = checkElemOfNestedCodomain(go, acMorph, acStar);
			}
		}
		return result;
	}
	
	protected static void unsetVariablesOfNestedAC(final AttrContext attrContext, 
			final OrdinaryMorphism ac) {
		final VarTuple vars = (VarTuple)attrContext.getVariables();
		final Vector<String> acVars = ac.getTarget().getVariableNamesOfAttributes();
		for (int i = 0; i < vars.getSize(); i++) {
			VarMember vm = vars.getVarMemberAt(i);
			if (acVars.contains(vm.getName())
					&& ((vm.getMark() == VarMember.PAC)
							|| (vm.getMark() == VarMember.NAC))
					&& !vm.isInputParameter()) {
				((ContextView)attrContext).removeValue(vm.getName());
			}
		}
	}
}
