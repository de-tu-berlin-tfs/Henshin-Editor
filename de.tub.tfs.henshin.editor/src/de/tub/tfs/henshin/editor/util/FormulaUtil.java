/**
 * 
 */
package de.tub.tfs.henshin.editor.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.EditPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.editparts.graph.tree.GraphTreeEditPart;
import de.tub.tfs.henshin.editor.editparts.rule.tree.LhsRhsTreeEditPart;
import de.tub.tfs.henshin.editor.ui.condition.ConditionView;
import de.tub.tfs.henshin.editor.ui.rule.RuleView;

/**
 * A helper class to calculate something in formula.
 * 
 * @author Angeline Warning
 */
public class FormulaUtil {

	/**
	 * If the given parent and child formula are not {@code null}, then checks
	 * if the child formula is the left child of the parent formula.
	 * 
	 * @param parentFormula
	 *            Parent formula.
	 * @param childFormula
	 *            Child formula
	 * @return {@code true} if the given child formula is the child formula of
	 *         the given parent formula on the left side.
	 */
	public static boolean isLeftChild(final BinaryFormula parentFormula,
			final Formula childFormula) {
		return parentFormula != null && childFormula != null
				&& parentFormula.getLeft() == childFormula;
	}

	/**
	 * If the given parent and child formula are not {@code null}, then checks
	 * if the child formula is the right child of the parent formula.
	 * 
	 * @param parentFormula
	 *            Parent formula.
	 * @param childFormula
	 *            Child formula
	 * @return {@code true} if the given child formula is the child formula of
	 *         the given parent formula on the right side.
	 */
	public static boolean isRightChild(final BinaryFormula parentFormula,
			final Formula childFormula) {
		return parentFormula != null && childFormula != null
				&& parentFormula.getRight() == childFormula;
	}

	/**
	 * Calculates the depth of the given {@code formula}
	 * 
	 * @param formula
	 *            A formula whose depth is calculated.
	 * @return Formula's depth.
	 */
	public static int getDepth(final Formula formula) {
		int depth = 0;

		if (formula != null) {
			if (formula instanceof NestedCondition) {
				depth += 1;
			} else if (formula instanceof UnaryFormula) {
				depth++;
				depth += getDepth(((UnaryFormula) formula).getChild());
			} else if (formula instanceof BinaryFormula) {
				depth++;
				depth += Math.max(
						getDepth(((BinaryFormula) formula).getLeft()),
						getDepth(((BinaryFormula) formula).getRight()));
			}
		}

		return depth;
	}

	/**
	 * Calculates the width of the given {@code formula}
	 * 
	 * @param formula
	 *            A formula whose width is calculated.
	 * @return Formula's width.
	 */
	public static int getWidth(final Formula formula) {
		int width = 0;

		if (formula != null) {
			if (formula instanceof NestedCondition) {
				width = 1;
			} else if (formula instanceof UnaryFormula) {
				width = 1;
				int childWidth = getWidth(((UnaryFormula) formula).getChild());
				if (childWidth > 1) {
					width = Math.max(width, childWidth);
				}
			} else if (formula instanceof BinaryFormula) {
				width = 2;
				int leftWidth = getWidth(((BinaryFormula) formula).getLeft());
				int rightWidth = getWidth(((BinaryFormula) formula).getRight());

				if (leftWidth > 1 || rightWidth > 1) {
					width = 2 * Math.max(leftWidth, rightWidth);
				}
			}
		}

		return width;
	}

	/**
	 * Returns the string representation of the given {@code formula}.
	 * 
	 * @param formula
	 *            A formula whose string representation is calculated.
	 * @return String representation of the given {@code formula} or
	 *         "Unknown formula type!" if the formula type is unidentified.
	 */
	public static String getText(Formula formula) {
		if (formula == null) {
			return null;
		}

		String text = "Unknown formula type!";

		if (formula instanceof NestedCondition) {
			Graph conclusion = ((NestedCondition) formula).getConclusion();
			if (conclusion != null) {
				text = conclusion.getName();
			}
		} else if (formula instanceof Not) {
			text = "NOT";
		} else if (formula instanceof And) {
			text = "AND";
		} else if (formula instanceof Or) {
			text = "OR";
		}

		return text;
	}

	/**
	 * Finds out the premise of the given {@code formula}.
	 * 
	 * @param formula
	 *            A formula whose premise is found out.
	 * @return Premise of the given {@code formula} or {@code null} if such
	 *         premise doesn't exist.
	 */
	public static Graph getPremise(Formula formula) {
		if (formula != null) {
			EObject parent = formula.eContainer();
			while (parent != null && !(parent instanceof Graph)) {
				parent = parent.eContainer();
			}

			if (parent != null && parent instanceof Graph) {
				return (Graph) parent;
			}

		}

		return null;
	}

	/**
	 * Checks, if the given {@code formula} is valid. Valid means: If the given
	 * {@code formula} is an unary formula, then this formula has to have a
	 * child. If the given {@code formula} is a binary formula, then this
	 * formula has to have a left and right child.
	 * 
	 * @param formula
	 *            A formula to check.
	 * @return {@code true} if the given formula is valid, {@code false}
	 *         otherwise.
	 */
	public static boolean isValid(Formula formula) {
		boolean valid = false;

		if (formula != null) {
			if (formula instanceof NestedCondition) {
				valid = true;
			} else if (formula instanceof UnaryFormula) {
				valid = ((UnaryFormula) formula).getChild() != null;
			} else if (formula instanceof BinaryFormula) {
				valid = ((BinaryFormula) formula).getLeft() != null
						&& ((BinaryFormula) formula).getRight() != null;
			}
		}

		return valid;
	}

	/**
	 * Finds opened condition views which represent the given {@code formula} as
	 * a model or child model. If such views are found, then returns it.
	 * Otherwise, returns an empty list.
	 * 
	 * @return Opened condition views represent the given {@code formula} or an
	 *         empty list if such views can't be found.
	 */
	public static List<ConditionView> getOpenedConditionViews(Formula formula) {
		final List<ConditionView> conditionViews = new ArrayList<ConditionView>();
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		if (page != null) {
			final IViewReference[] viewReferences = page.getViewReferences();
			for (int i = 0; i < viewReferences.length; i++) {
				final IViewPart viewPart = viewReferences[i].getView(false);
				if (viewPart instanceof ConditionView) {
					final ConditionView currentView = (ConditionView) viewPart;
					final Formula viewModel = currentView.getCastedModel();
					if (contains(formula, viewModel)) {
						conditionViews.add(currentView);
					}
				}
			}
		}

		return conditionViews;
	}

	/**
	 * Finds opened condition view which represent the given {@code formula} as
	 * a model. If such view is found, then returns it. Otherwise, returns an
	 * empty list.
	 * 
	 * @return Opened condition view represent the given {@code formula} or an
	 *         empty list if such view can't be found.
	 */
	public static ConditionView getOpenedConditionView(Formula formula) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		if (page != null) {
			final IViewReference[] viewReferences = page.getViewReferences();
			for (int i = 0; i < viewReferences.length; i++) {
				final IViewPart viewPart = viewReferences[i].getView(false);
				if (viewPart instanceof ConditionView) {
					final ConditionView currentView = (ConditionView) viewPart;
					final Formula viewModel = currentView.getCastedModel();
					if (formula == viewModel) {
						return (ConditionView) viewPart;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Contains.
	 * 
	 * @param formula
	 *            the formula
	 * @param toFind
	 *            the to find
	 * @return true, if successful
	 */
	private static boolean contains(Formula formula, Formula toFind) {
		if (formula == toFind) {
			return true;
		} else if (formula instanceof NestedCondition) {
			return contains(((NestedCondition) formula).getConclusion()
					.getFormula(), toFind);
		} else if (formula instanceof UnaryFormula) {
			return contains(((UnaryFormula) formula).getChild(), toFind);
		} else if (formula instanceof BinaryFormula) {
			return contains(((BinaryFormula) formula).getLeft(), toFind)
					|| contains(((BinaryFormula) formula).getRight(), toFind);
		}

		return false;
	}

	/**
	 * Finds condition and rule views which show the given {@code formula}. If
	 * such views are found, then returns it. Otherwise, returns an empty list.
	 * 
	 * @return Condition or rule view that show the given {@code formula} or an
	 *         empty list if such views can't be found.
	 */
	public static List<IViewPart> getViewsShowing(Formula formula) {
		final List<IViewPart> views = new ArrayList<IViewPart>();
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		if (page != null) {
			final IViewReference[] viewReferences = page.getViewReferences();
			for (int i = 0; i < viewReferences.length; i++) {
				final IViewPart viewPart = viewReferences[i].getView(true);
				if (viewPart instanceof ConditionView) {
					final ConditionView currentView = (ConditionView) viewPart;
					final Formula viewModel = currentView.getCastedModel();
					if (contains(viewModel, formula)) {
						views.add(currentView);
					}
				} else if (viewPart instanceof RuleView) {
					final RuleView currentView = (RuleView) viewPart;
					final Rule viewModel = currentView.getCastedModel();
					if (contains(viewModel.getLhs().getFormula(), formula)) {
						views.add(currentView);
					}
				}
			}
		}

		return views;
	}

	/**
	 * Finds all opened views. If such views are found, then returns it.
	 * Otherwise, returns an empty list.
	 * 
	 * @return All opened views or an empty list if no view is opened.
	 */
	public static List<IViewPart> getViewsShowing() {
		final List<IViewPart> views = new ArrayList<IViewPart>();
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		if (page != null) {
			final IViewReference[] viewReferences = page.getViewReferences();
			for (int i = 0; i < viewReferences.length; i++) {
				final IViewPart viewPart = viewReferences[i].getView(true);
				views.add(viewPart);
			}
		}

		return views;
	}

	public static EditPart getPremiseEditPart(final EditPart formulaEditPart) {
		if (formulaEditPart != null) {
			EditPart parentEditPart = formulaEditPart.getParent();
			while (parentEditPart != null
					&& (!(parentEditPart instanceof LhsRhsTreeEditPart) && !(parentEditPart instanceof GraphTreeEditPart))) {
				parentEditPart = parentEditPart.getParent();
			}

			if (parentEditPart != null
					&& (parentEditPart instanceof LhsRhsTreeEditPart || parentEditPart instanceof GraphTreeEditPart)) {
				return parentEditPart;
			}
		}

		return null;
	}

	/**
	 * Gets the distinct name of AC's conclusion.
	 * 
	 * @param ac
	 *            application condition whose conclusion name is generated.
	 * @return A distinct name of AC's conclusion within the root formula.
	 */
	public static String getDistinctACName(final NestedCondition ac) {
		if (ac != null) {
			final Formula root = getRootFormula(ac);
			final Set<String> forbiddenNames = ModelUtil.getExistNames(root,
					null);

			// Generates a distinct name
			final String nameBase = "AC";
			int i = 0;
			String distinctName;
			do {
				distinctName = nameBase + i++;
			} while (forbiddenNames.contains(distinctName));

			return distinctName;
		}

		return null;
	}

	private static Formula getRootFormula(final NestedCondition ac) {
		EObject rootFormula = ac.eContainer();
		while (rootFormula.eContainer() != null
				&& rootFormula.eContainer() instanceof Formula) {
			rootFormula = rootFormula.eContainer();
		}

		if (rootFormula != null && rootFormula instanceof Formula) {
			return (Formula) rootFormula;
		}

		return ac;
	}

	/**
	 * Finds a condition view that showing the deleted formula and closes it.
	 */
	public static void closeConditionViewsShowing(final Formula formula) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final List<ConditionView> conditionViews = FormulaUtil
				.getOpenedConditionViews(formula);
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();
		for (final ConditionView conditionView : conditionViews) {
			page.hideView(conditionView);
		}
	}

	/**
	 * Close the given view.
	 * 
	 * @param view
	 *            View to close.
	 */
	public static void closeView(ConditionView view) {
		if (view != null) {
			final IWorkbench workbench = PlatformUI.getWorkbench();
			final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
					.getActivePage();
			page.hideView(view);
		}
	}

	/**
	 * @param formula
	 *            Formula to check.
	 * @return {@code true} if the given formula has at least one child.
	 */
	public static boolean hasChild(Formula formula) {
		if (formula instanceof UnaryFormula) {
			return ((UnaryFormula) formula).getChild() != null;
		} else if (formula instanceof BinaryFormula) {
			return ((BinaryFormula) formula).getLeft() != null
					|| ((BinaryFormula) formula).getRight() != null;
		}
		return false;
	}
}
