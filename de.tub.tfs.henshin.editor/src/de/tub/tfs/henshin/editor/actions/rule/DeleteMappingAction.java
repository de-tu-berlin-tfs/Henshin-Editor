/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.rule.DeleteMappingCommand;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.tree.NodeTreeEditPart;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * The Class DeleteMappingAction.
 */
public class DeleteMappingAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.DeleteMappingAction";

	/** The Constant DESC. */
	static private final String DESC = "Delete Mapping";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Cycle through all NACs of the active Rule";

	/** The node. */
	private Node node;

	/** The current mappings. */
	private List<Mapping> currentMappings;

	/**
	 * Instantiates a new delete mapping action.
	 * 
	 * @param part
	 *            the part
	 */
	public DeleteMappingAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand command = new CompoundCommand();

		for (Mapping m : currentMappings) {
			command.add(new DeleteMappingCommand(m, node == m.getOrigin()));
		}

		execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if ((editpart instanceof NodeTreeEditPart)
					|| (editpart instanceof NodeEditPart)) {
				node = (Node) editpart.getModel();
				if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(node))
					return false;
				List<Mapping> mappings = new ArrayList<Mapping>();
				Graph parentNode = node.getGraph();
				if (parentNode != null) {
					EObject grandParentNode = parentNode.eContainer();
					if (grandParentNode instanceof NestedCondition) {
						Rule rule = ModelUtil.getRule(grandParentNode);
						buildMappings(rule.getLhs().getFormula(), mappings);
					} else {
						if (grandParentNode instanceof Rule) {
							mappings.addAll(((Rule) grandParentNode)
									.getMappings());
							if (parentNode == ((Rule) grandParentNode).getLhs()) {
								Graph lhs = parentNode;
								Formula formula = lhs.getFormula();
								if (formula != null) {
									buildMappings(formula, mappings);
								}
							}
						} else {
							return false;
						}
					}

					currentMappings = new ArrayList<Mapping>();
					for (Mapping m : mappings) {
						if (!currentMappings.contains(m)) {
							if (m.getOrigin() == node || m.getImage() == node) {
								currentMappings.add(m);
							}

							else {
								for (Mapping cm : new ArrayList<Mapping>(
										currentMappings)) {
									if (m.getOrigin() == cm.getOrigin()
											|| m.getImage() == cm.getOrigin()
											|| m.getOrigin() == cm.getImage()
											|| m.getImage() == cm.getImage()) {
										currentMappings.add(m);
									}
								}
							}
						}
					}

					if (currentMappings.size() > 0) {
						
						return true;
					}
				}
			}
		}

		return false;
	}

	/**
	 * Builds the mappings.
	 * 
	 * @param formula
	 *            the formula
	 * @param result
	 *            the result
	 */
	private void buildMappings(Formula formula, List<Mapping> result) {
		if (result == null) {
			result = new ArrayList<Mapping>();
		}

		if (formula instanceof NestedCondition) {
			final NestedCondition ac = (NestedCondition) formula;
			result.addAll(ac.getMappings());
			if (ac.getConclusion() != null
					&& ac.getConclusion().getFormula() != null) {
				buildMappings(ac.getConclusion().getFormula(), result);
			}
		} else if (formula instanceof UnaryFormula) {
			buildMappings(((UnaryFormula) formula).getChild(), result);
		} else if (formula instanceof BinaryFormula) {
			buildMappings(((BinaryFormula) formula).getLeft(), result);
			buildMappings(((BinaryFormula) formula).getRight(), result);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("mapping16.png");
	}

}
