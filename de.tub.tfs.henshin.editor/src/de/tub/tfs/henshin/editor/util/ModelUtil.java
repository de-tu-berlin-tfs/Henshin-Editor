package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
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
import org.eclipse.gef.EditPart;

import de.tub.tfs.henshin.editor.HenshinTreeEditor;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * The Class ModelUtil.
 */
public class ModelUtil {

	/**
	 * Gets the epackage references.
	 * 
	 * @param model
	 *            the model
	 * @param parent
	 *            the parent
	 * @return the e package references
	 */
	public static String getEPackageReferences(EPackage model,
			Module parent) {
		String errorMsg = "";

		Set<EObject> referencedGraphs = new HashSet<EObject>();
		Set<EObject> referencedRules = new HashSet<EObject>();
		for (EClassifier clazz : model.getEClassifiers()) {
			referencedGraphs.addAll(getEObjectsWithReference(parent, clazz,
					HenshinPackage.GRAPH));
			referencedRules.addAll(getEObjectsWithReference(parent, clazz,
					HenshinPackage.RULE));
		}

		for (EObject eObject : referencedGraphs) {
			if (!errorMsg.isEmpty()) {
				errorMsg += "\n";
			}
			errorMsg += "\tGraph [" + ((Graph) eObject).getName() + "]";
		}
		for (EObject eObject : referencedRules) {
			if (!errorMsg.isEmpty()) {
				errorMsg += "\n";
			}
			errorMsg += "\tRule [" + ((Rule) eObject).getName() + "]";
		}

		return errorMsg.isEmpty() ? null : errorMsg;
	}

	/**
	 * Gets the eobjects with reference.
	 * 
	 * @param transSys
	 *            the trans sys
	 * @param clazz
	 *            the clazz
	 * @param featureID
	 *            the feature id
	 * @return the e objects with reference
	 */
	private static Set<EObject> getEObjectsWithReference(
			Module transSys, EClassifier clazz, int featureID) {
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
			EList<Rule> rules = HenshinUtil.getRules(transSys);
			for (Rule rule : rules) {
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
	 * @param graph
	 *            the graph
	 * @param clazz
	 *            the clazz
	 * @param parent
	 *            the parent
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
	 * @param <Child_T>
	 *            the generic type
	 * @param obj
	 *            the obj
	 * @param childFeatureID
	 *            the child feature id
	 * @param nameBase
	 *            the name base
	 * @return the new child distinct name
	 */
	public static <Child_T extends NamedElement> String getNewChildDistinctName(
			EObject obj, int childFeatureID, String nameBase) {
		return getNewChildDistinctName(obj, childFeatureID, nameBase, "");
	}

	@SuppressWarnings("unchecked")
	public static <Child_T extends NamedElement> String getNewChildDistinctName(
			EObject obj, int childFeatureID, String nameBase,
			String nameAddition) {
		HashSet<String> forbiddenNames = new HashSet<String>();
		final EStructuralFeature eStructuralFeature = obj.eClass()
				.getEStructuralFeature(childFeatureID);
		for (Child_T instance : (Collection<Child_T>) obj
				.eGet(eStructuralFeature)) {
			forbiddenNames.add(instance.getName());
		}

		// generates a new distinct name
		int id = 0;
		String newName;
		do {
			newName = nameBase + id++ + nameAddition;
		} while (forbiddenNames.contains(newName));

		return newName;
	}

	public static List<Mapping> getMappings(final Formula formula) {
		if (formula != null) {
			if (formula instanceof NestedCondition) {
				return ((NestedCondition) formula).getMappings();
			} else if (formula instanceof UnaryFormula) {
				return getMappings(((UnaryFormula) formula).getChild());
			} else {
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
	 * @param graph
	 *            the graph
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
			} while (forbiddenNames.contains(distinctName));

			return distinctName;
		}

		throw new IllegalArgumentException("The given graph must not be null!");
	}

	/**
	 * Gets the exist names.
	 * 
	 * @param formula
	 *            the formula
	 * @param existNames
	 *            the exist names
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
		} else if (formula instanceof UnaryFormula) {
			getExistNames(((UnaryFormula) formula).getChild(), existNames);
		} else if (formula instanceof BinaryFormula) {
			getExistNames(((BinaryFormula) formula).getLeft(), existNames);
			getExistNames(((BinaryFormula) formula).getRight(), existNames);
		}

		return existNames;
	}

	/**
	 * Checks, if the given {@code graph} is a LHS.
	 * 
	 * @param graph
	 *            The graph to check.
	 * @return {@code true} if the given {@code graph} is a LHS, {@code false}
	 *         otherwise.
	 * 
	 * @deprecated use {@link Graph#isLhs()} instead.
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
	 * Gets a rule which contains the given {@code graph} and returns the LHS.
	 * 
	 * @param graph
	 *            A graph whose LHS is found.
	 * @return A LHS of the given {@code graph}.
	 */
	private static Graph getLhs(Graph graph) {
		if (graph != null) {
			EObject parent = graph.eContainer();
			while (parent != null && !(parent instanceof Rule)) {
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
	 * @param rule
	 *            the rule
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
	 * @param nacs
	 *            the nacs
	 * @param formula
	 *            the formula
	 * @return the all nacs
	 */
	private static void getAllNacs(List<NestedCondition> nacs, Formula formula) {
		if (nacs == null) {
			nacs = new ArrayList<NestedCondition>();
		}

		if (formula instanceof NestedCondition) {
			nacs.add((NestedCondition) formula);
		} else if (formula instanceof BinaryFormula) {
			BinaryFormula binaryFormula = (BinaryFormula) formula;
			getAllNacs(nacs, binaryFormula.getLeft());
			getAllNacs(nacs, binaryFormula.getRight());
		}
	}

	/**
	 * Gets the rule.
	 * 
	 * @param object
	 *            the object
	 * @return the rule
	 */
	public static Rule getRule(EObject object) {
		Rule rule = null;
		EObject f = object;
		while (!(f instanceof Rule) && f != null) {
			f = f.eContainer();
		}
		if (f != null) {
			rule = (Rule) f;
		}
		return rule;
	}

	/**
	 * @param rule
	 *            Rule to check.
	 * @param isLhs
	 *            This value is only evaluated if the given rule is used as
	 *            multi rule in amalgamation unit. Otherwise, this value is not
	 *            relevant.
	 * @return The mapping list.
	 */
	public static List<Mapping> getMappings(final Rule rule, final boolean isLhs) {
		return rule.getMappings();
	}

	/**
	 * Gets the root {@link Module} for a given model object.
	 * 
	 * @param eObject
	 *            the e object
	 * @return the root {@link Module}
	 * 
	 * @deprecated Use {@link HenshinUtil#getTransformationSystem(EObject)}
	 *             instead.
	 */
	public static Module getTransformationSystem(EObject model) {
		HenshinTreeEditor editor = (HenshinTreeEditor) IDUtil
				.getHostEditor(model);

		if (editor != null) {
			return editor.getModelRoot(Module.class);
		}

		return null;
	}

	/**
	 * @param editpart
	 * @param toFindType
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T find(EditPart editpart, Class<T> toFindType) {
		Assert.isLegal(toFindType != null);

		if (editpart == null) {
			return null;
		}

		Object model = editpart.getModel();

		if (toFindType.isInstance(model)) {
			return (T) model;
		}

		return find(editpart.getParent(), toFindType);
	}

	/**
	 * @param model
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends EObject> T findAncestor(EObject model,
			Class<T> type) {
		Assert.isLegal(type != null);

		if (model == null) {
			return null;
		}

		if (type.isInstance(model)) {
			return (T) model;
		}

		return findAncestor(model.eContainer(), type);
	}

	/**
	 * Convenient method to get the root {@link LayoutSystem}.
	 * 
	 * @param eobject
	 *            the eobject
	 * @return the layout system
	 */
	public static LayoutSystem getLayoutSystem(EObject eobject) {
		HenshinTreeEditor editor = (HenshinTreeEditor) IDUtil
				.getHostEditor(eobject);

		if (editor != null) {
			return editor.getModelRoot(LayoutSystem.class);
		}

		return null;
	}

	/**
	 * Convenient method to get the properly typed model root for a given model
	 * object being hosted in an {@link HenshinTreeEditor}.
	 * 
	 * @param model
	 *            a model object
	 * @param toFind
	 *            the type of the returned model root
	 * 
	 * @return the correctly typed model root or <code>null</code>, if the given
	 *         <code>model</code> is not hosted in an editor.
	 */
	public static <T extends EObject> T getModelRoot(EObject model,
			Class<T> toFind) {
		EObject search = model;

		while (search != null) {
			HenshinTreeEditor hostingEditor = (HenshinTreeEditor) IDUtil
					.getHostEditor(search);

			if (hostingEditor != null) {
				return hostingEditor.getModelRoot(toFind);
			}

			search = search.eContainer();
		}

		return null;
	}

	/**
	 * Searches in a given root model object's tree for all {@link EObject}s
	 * with a {@link EStructuralFeature} pointing to a given model object.
	 * 
	 * @param model
	 * @param rootModel
	 * @param refFeature
	 * 
	 * @return
	 */
	public static List<EObject> getReferences(EObject model, EObject rootModel,
			EStructuralFeature refFeature) {
		List<EObject> result = new LinkedList<EObject>();

		if (rootModel != null && refFeature != null) {
			TreeIterator<EObject> iter = rootModel.eAllContents();

			while (iter.hasNext()) {
				EObject toFind = iter.next();

				if (toFind.eClass().getEAllStructuralFeatures()
						.contains(refFeature)) {
					Object refObj = toFind.eGet(refFeature);

					if (refFeature.isMany()) {
						if (((List<?>) refObj).contains(model)) {
							result.add(toFind);
						}
					} else if (model != toFind && model == refObj) {
						result.add(toFind);
					}
				}
			}
		}

		return result;
	}

	/**
	 * @param model
	 * @param refType
	 * @param rootModel
	 * @param refFeature
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getReferences(EObject model, Class<T> refType,
			EObject rootModel, EStructuralFeature refFeature) {
		List<T> result = new LinkedList<T>();

		if (rootModel != null && refFeature != null) {
			TreeIterator<EObject> iter = rootModel.eAllContents();

			while (iter.hasNext()) {
				EObject toFind = iter.next();

				if (refType.isInstance(toFind)) {
					Object refObj = toFind.eGet(refFeature);

					if (refFeature.isMany()) {
						if (((List<?>) refObj).contains(model)) {
							result.add((T) toFind);
						}
					} else if (model != toFind && model == refObj) {
						result.add((T) toFind);
					}
				}
			}
		}

		return result;
	}
	
	public static Collection<EPackage> getEPackagesOfGraph(Graph graph) {
		
		if (graph != null) {
			
			Set<EPackage> ePackages = new HashSet<EPackage>();
			
			EList<Node> nodes = graph.getNodes();
			
			for (Node node : nodes) {
				ePackages.add(node.getType().getEPackage());
			}
			
			return ePackages;
		}
		return null;
	}
	
}
