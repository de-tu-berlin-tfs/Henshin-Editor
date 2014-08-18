package de.tub.tfs.henshin.tggeditor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.RuleUtil;


/**
 * The Class ModelUtil.
 */
public class ModelUtil {

	/**
	 * Gets the epackage references.
	 *
	 * @param model the model
	 * @param parent the parent
	 * @return the e package references
	 */
	
	public static EPackage getSourceModel(Module parent) {
		for (EPackage epackage : parent.getImports()) {
			if (epackage.getName().contains("source"))
				return epackage;
		}
		return null;
	}
	
	public static EPackage getTargetModel(Module parent) {
		for (EPackage epackage : parent.getImports()) {
			if (epackage.getName().contains("target"))
				return epackage;
		}
		return null;
	}
	
	public static EPackage getCorrespModel(Module parent) {
		for (EPackage epackage : parent.getImports()) {
			if (epackage.getName().contains("corresp"))
				return epackage;
		}
		return null;
	}
	
	public static String getEPackageReferences(
			EPackage model, TGG parent) {
		String errorMsg = "";

		Set<EObject> referencedGraphs = new HashSet<EObject>();
		Set<EObject> referencedRules = new HashSet<EObject>();
		for (EClassifier clazz : model.getEClassifiers()) {
			referencedGraphs.addAll(getEObjectsWithReference(parent, clazz, TggPackage.TRIPLE_GRAPH));
			referencedRules.addAll(getEObjectsWithReference(parent, clazz, HenshinPackage.RULE));
		}
		
		for (EObject eObject : referencedGraphs) {
			if (! errorMsg.isEmpty()) {
				errorMsg += "\n";
			}
			errorMsg += "\tGraph [" + ((Graph) eObject).getName() + "]";			
		}
		for (EObject eObject : referencedRules) {
			if (! errorMsg.isEmpty()) {
				errorMsg += "\n";
			}
			errorMsg += "\tRule [" + ((Rule) eObject).getName() + "]";			
		}

		return errorMsg.isEmpty() ? null : errorMsg;
	}
	
	/**
	 * Gets the eobjects with reference.
	 *
	 * @param transSys the trans sys
	 * @param clazz the clazz
	 * @param featureID the feature id
	 * @return the e objects with reference
	 */
	private static Set<EObject> getEObjectsWithReference(TGG transSys, 
			EClassifier clazz, int featureID) {
		Set<EObject> referencedEObjects = new HashSet<EObject>();
		switch (featureID) {
			case HenshinPackage.GRAPH:
				for (Graph graph : transSys.getInstances()) {
					if (isReferenced(graph, clazz, transSys)) {
						referencedEObjects.add(graph);
					}
				}
				break;
			case HenshinPackage.RULE:
				for (Rule rule : ModelUtil.getRules(transSys)) {
					if (isReferenced(rule.getLhs(), clazz, transSys)) {
						referencedEObjects.add(rule);
					}
					if (isReferenced(rule.getRhs(), clazz, transSys)) {
						referencedEObjects.add(rule);
					}
				}
				break;
			default:
				break;
		}
		return referencedEObjects;
	}

	/**
	 * Checks if is referenced.
	 *
	 * @param graph the graph
	 * @param clazz the clazz
	 * @param parent the parent
	 * @return true, if is referenced
	 */
	private static boolean isReferenced(Graph graph, EClassifier clazz, 
			Module parent) {
		for (Node node : graph.getNodes()) {
			String type = node.getType().getName();
			if (clazz.getName().equals(type)) {
				return true;
			}
		}
	
		return false;
	}


	/**
	 * Gets the new child distinct name.
	 *
	 * @param <Child_T> the generic type
	 * @param obj the obj
	 * @param childFeatureID the child feature id
	 * @param nameBase the name base
	 * @return the new child distinct name
	 */
	public static <Child_T extends NamedElement> String getNewChildDistinctName(
			EObject obj, int childFeatureID, String nameBase) {
		return getNewChildDistinctName(obj,childFeatureID,nameBase,"");
	}
	
	
	@SuppressWarnings("unchecked")
	public static <Child_T extends NamedElement> String getNewChildDistinctName(
			EObject obj, int childFeatureID, String nameBase,String nameAddition) {
		HashSet<String> forbiddenNames = new HashSet<String>();
		final EStructuralFeature eStructuralFeature = obj.eClass().getEStructuralFeature(
				childFeatureID);
		for (Child_T instance : (Collection<Child_T>) obj.eGet(eStructuralFeature)) {
			forbiddenNames.add(instance.getName());
		}

		// generates a new distinct name
		int id = 0;
		String newName;
		do {
			newName = nameBase + id++ +nameAddition;
		}
		while (forbiddenNames.contains(newName));

		return newName;
	}

	public static List<Mapping> getMappings(final Formula formula) {
		if (formula != null) {
			if (formula instanceof NestedCondition) {
				return ((NestedCondition) formula).getMappings();
			} 
			else if (formula instanceof UnaryFormula) {
				return getMappings(((UnaryFormula) formula).getChild());
			} 
			else {
				final List<Mapping> result = new ArrayList<Mapping>();
				result.addAll(getMappings(((BinaryFormula) formula).getLeft()));
				result.addAll(getMappings(((BinaryFormula) formula).getRight()));
				return result;
			}
		}
		return Collections.emptyList();
	}
	
	/**
	 * Gets the distinct nc name.
	 *
	 * @param graph the graph
	 * @return the distinct nc name
	 */
	public static String getDistinctNCName(final Graph graph) {
		if (graph != null) {
			final Graph lhs = getLhs(graph);
			final Formula formula = lhs.getFormula();
			final Set<String> forbiddenNames = getExistNames(formula, null);
			
			// Generates a distinct name
			final String nameBase = "AC";
			int i = 0;
			String distinctName;
			do {
				distinctName = nameBase + i++;
			} while(forbiddenNames.contains(distinctName));
			
			return distinctName;
		}
		
		throw new IllegalArgumentException("The given graph must not be null!");
	}
	
	/**
	 * Gets the exist names.
	 *
	 * @param formula the formula
	 * @param existNames the exist names
	 * @return the exist names
	 */
	static Set<String> getExistNames(Formula formula, Set<String> existNames) {
		if (existNames == null) {
			existNames = new HashSet<String>();
		}
		
		if (formula instanceof NestedCondition) {
			NestedCondition ac = (NestedCondition) formula;
			Graph conclusion = ac.getConclusion();
			if (conclusion != null) {
				existNames.add(conclusion.getName());
				existNames.addAll(getExistNames(conclusion.getFormula(), null));
			}
		}
		else if (formula instanceof UnaryFormula) {
			getExistNames(((UnaryFormula) formula).getChild(), existNames);
		}
		else if (formula instanceof BinaryFormula) {
			getExistNames(((BinaryFormula) formula).getLeft(), existNames);
			getExistNames(((BinaryFormula) formula).getRight(), existNames);
		}
		
		return existNames;
	}
	
	/**
	 * Checks, if the given {@code graph} is a LHS.
	 * @param graph The graph to check.
	 * @return {@code true} if the given {@code graph} is a LHS, 
	 * {@code false} otherwise.
	 */
	public static boolean isLhs(final Graph graph) {
		if (graph != null) {
			final Rule rule = getRule(graph);
			if (rule != null) {
				return graph.equals(rule.getLhs());
			}
		}
		return false;
	}
	
	/**
	 * Gets a rule which contains the given {@code graph}
	 * and returns the LHS.
	 * @param graph A graph whose LHS is found.
	 * @return A LHS of the given {@code graph}.
	 */
	private static Graph getLhs(Graph graph) {
		if (graph != null) {
			EObject parent = graph.eContainer();
			while (parent != null && ! (parent instanceof Rule)) {
				parent = parent.eContainer();
			}
			
			if (parent != null && parent instanceof Rule) {
				return ((Rule) parent).getLhs();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the all nacs.
	 *
	 * @param rule the rule
	 * @return the all nacs
	 */
	public static List<NestedCondition> getAllNacs(Rule rule) {
		List<NestedCondition> result = new ArrayList<NestedCondition>();
		Graph lhs = rule.getLhs();
		Formula formula = lhs.getFormula();
		getAllNacs(result, formula);
		return result;
	}
	
	/**
	 * Gets the all nacs.
	 *
	 * @param nacs the nacs
	 * @param formula the formula
	 * @return the all nacs
	 */
	private static void getAllNacs(List<NestedCondition> nacs, Formula formula) {
		if (nacs == null) {
			nacs = new ArrayList<NestedCondition>();
		}
		
		if (formula instanceof NestedCondition) {
			nacs.add((NestedCondition) formula);
		}
		else if (formula instanceof BinaryFormula) {
			BinaryFormula binaryFormula = (BinaryFormula) formula;
			getAllNacs(nacs, binaryFormula.getLeft());
			getAllNacs(nacs, binaryFormula.getRight());
		}
	}
	
	
	/**
	 * Gets the trans system.
	 *
	 * @param eObject the e object
	 * @return the trans system
	 */
	public static Module getTransSystem(EObject eObject){
		EObject eo = eObject;
		while (! (eo instanceof Module)){
			if (eo.eContainer() == null){
				return null;
			}
			eo =eo.eContainer();
		}
		return (Module) eo;
	}
	
	
	/**
	 * Gets the rule.
	 *
	 * @param object the object
	 * @return the rule
	 */
	public static Rule getRule(EObject object){
		Rule rule=null;
		EObject f=object;
		while (!(f instanceof Rule) && f!=null){
			f=f.eContainer();
		}
		if (f!=null){
			rule = (Rule) f; 
		}
		return rule;
	}
	
	
	/**
	 * @param rule Rule to check.
	 * @param isLhs This value is only evaluated if the given rule is used
	 * as multi rule in amalgamation unit. Otherwise, this value is not relevant.
	 * @return The mapping list.
	 */
	/*public static List<Mapping> getMappings(final Rule rule, final boolean isLhs) {
		final AmalgamationUnit aUnit = getAmalgamationUnit(rule);
		if (aUnit == null) {
			return rule.getMappings();
		}
		else {
			if (isLhs) {
				return aUnit.getLhsMappings();	
			}
			else {
				return aUnit.getRhsMappings();
			}
		}
	}*/
	
	
	/**
	 * Checks, if the given {@code rule} is used as multi rule in amalgamation unit
	 * and returns the unit or {@code null} otherwise.
	 * @param rule Rule to check.
	 * @return Amalgamation unit which use the given {@code rule} as multi rule.
	 */
	/*public static AmalgamationUnit getAmalgamationUnit(final Rule rule) {
		if (rule != null && rule.eContainer() instanceof Module) {
			final Module transSystem = 
				(Module) rule.eContainer();
			for (Unit unit : transSystem.getTransformationUnits()) {
				if (unit instanceof AmalgamationUnit) {
					final AmalgamationUnit aUnit = (AmalgamationUnit) unit;
					if (aUnit.getKernelRule() == rule) {
						return aUnit;
					}
					for (Rule multiRule : aUnit.getMultiRules()) {
						if (rule == multiRule) {
							return aUnit;
						}
					}
				}
			}
		}
		
		return null;
	}*/
	
	/*public static AmalgamationUnit getAmalgamationUnit(final Graph graph) {
		if (graph != null && graph.eContainer() instanceof Rule) {
			final Rule rule = (Rule) graph.eContainer();
			return getAmalgamationUnit(rule);
		}
		return null;
	}*/
	
	/*public static boolean graphInKernelRule(final Graph graph) {
		if (graph != null && graph.eContainer() instanceof Rule) {
			final Rule rule = (Rule) graph.eContainer();
			final AmalgamationUnit aUnit = getAmalgamationUnit(rule);
			if (aUnit != null) {
				final Rule kernelRule = aUnit.getKernelRule();
				return kernelRule != null
						&& (graph == kernelRule.getLhs()
								|| graph == kernelRule.getRhs());
			}
		}
		
		return false;
	}*/
	
	/*public static boolean graphInMultiRule(final Graph graph) {
		if (graph != null && graph.eContainer() instanceof Rule) {
			final Rule rule = (Rule) graph.eContainer();
			final AmalgamationUnit aUnit = getAmalgamationUnit(rule);
			if (aUnit != null) {
				for (Rule multiRule : aUnit.getMultiRules()) {
					if (graph == multiRule.getLhs()
							|| graph == multiRule.getRhs()) {
						return true;
					}
				}
			}
		}
		
		return false;
	}*/

	/*public static boolean nodeInKernelRule(final Node node) {
		if (node != null) {
			final Graph graph = (Graph) node.eContainer();
			return graphInKernelRule(graph);
		}
		
		return false;
	}*/
	
	/**
	 * Checks, if the given {@code node} is an image node in multi rule
	 * and the origin node in kernel rule.
	 * @param node Node to check.
	 * @return {@code true} if the given {@code node} is an image node
	 * in multi rule with origin node in kernel rule, {@code false} otherwise.
	 */
//	public static boolean imageNodeInMultiRule(final Node node) {
//		if (node != null) {
//			final Graph graph = (Graph) node.eContainer();
//			final AmalgamationUnit aUnit = getAmalgamationUnit(graph);
//			if (aUnit != null) {
//				for (Mapping mapping : aUnit.getLhsMappings()) {
//					if (mapping.getImage() == node) {
//						return true;
//					}
//				}
//				for (Mapping mapping : aUnit.getRhsMappings()) {
//					if (mapping.getImage() == node) {
//						return true;
//					}
//				}
//			}
//		}
//		return false;
//	}
	
	/**
	 * Checks, if the given node is in multi rule and is not mapped with
	 * a node in kernel rule.
	 * @param node Node to check.
	 * @return {@code true} if the given node is in multi rule and is not 
	 * mapped with a node in kernel rule, {@code false} otherwise.
	 */
//	public static boolean noMappedNodeInMultiRule(
//			final AmalgamationUnit aUnit, final Node node) {
//		if (aUnit != null) {
//			for (Mapping mapping : aUnit.getLhsMappings()) {
//				if (mapping.getOrigin() == node
//						|| mapping.getImage() == node) {
//					return false;
//				}
//			}
//			
//			for (Mapping mapping : aUnit.getRhsMappings()) {
//				if (mapping.getOrigin() == node
//						|| mapping.getImage() == node) {
//					return false;
//				}
//			}
//		}
//		return true;
//	}
//	
//	public static boolean mappingComplete(final AmalgamationUnit aUnit) {
//		final int kernelMultiNodeCount = getKernelMultiNodeCount(aUnit);
//		if (kernelMultiNodeCount != 
//				aUnit.getLhsMappings().size() + aUnit.getRhsMappings().size()) {
//			return false;
//		}
//		
//		return true;
//	}
//	
//	private static int getKernelMultiNodeCount(final AmalgamationUnit aUnit) {
//		int size = 0;
//		for (Rule rule : aUnit.getMultiRules()) {
//			size += rule.getLhs().getNodes().size();
//			size += rule.getRhs().getNodes().size();
//		}
//		return size;
//	}
//	
//	public static List<Node> getImageNodesInMulti(final Node nodeInKernel) {
//		final List<Node> imageNodes = new ArrayList<Node>();
//		if (nodeInKernel != null) {
//			final Graph graph = (Graph) nodeInKernel.eContainer();
//			if (ModelUtil.graphInKernelRule(graph)) {
//				final AmalgamationUnit aUnit = ModelUtil
//						.getAmalgamationUnit(graph);
//				if (aUnit != null) {
//					List<Mapping> mappings = aUnit.getLhsMappings();
//					if (!ModelUtil.isLhs(graph)) {
//						mappings = aUnit.getRhsMappings();
//					}
//
//					for (Mapping mapping : mappings) {
//						if (mapping.getOrigin() == nodeInKernel) {
//							imageNodes.add(mapping.getImage());
//						}
//					}
//				}
//			}
//		}
//		return imageNodes;
//	}
//	
//	public static Node getOriginNodeFromKernel(final Node imageNode) {
//		if (imageNode != null) {
//			final Graph graph = (Graph) imageNode.eContainer();
//			if (! nodeInKernelRule(imageNode)) {
//				final AmalgamationUnit aUnit = ModelUtil
//						.getAmalgamationUnit(graph);
//				if (aUnit != null) {
//					List<Mapping> mappings = aUnit.getLhsMappings();
//					if (! ModelUtil.isLhs(graph)) {
//						mappings = aUnit.getRhsMappings();
//					}
//
//					for (Mapping mapping : mappings) {
//						if (mapping.getImage() == imageNode) {
//							return mapping.getOrigin();
//						}
//					}
//				}
//			}
//		}
//		return null;
//	}
	
	public static boolean isOpRule(Rule rule) {

		if (rule instanceof TGGRule
				&& !RuleUtil.TGG_RULE.equals(((TGGRule) rule).getMarkerType()))
			return true;
		return false;
	}
	

	
	public static EList<Rule> getRules(Module m) {
		EList<Rule> rules = new BasicEList<Rule>();
		for (Unit unit : m.getUnits()) {
			if (unit instanceof Rule) {
				rules.add((Rule) unit);
			}
		}
		return ECollections.unmodifiableEList(rules);
	}
}
