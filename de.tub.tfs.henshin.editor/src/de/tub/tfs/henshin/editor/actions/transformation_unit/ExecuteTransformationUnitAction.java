/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.ExecuteTransformationUnitCommand;
import de.tub.tfs.henshin.editor.editparts.graph.tree.GraphTreeEditPart;
import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.ui.dialog.ParemetersValueDialog;
import de.tub.tfs.henshin.editor.util.DialogUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.SendNotify;
import de.tub.tfs.henshin.editor.util.validator.ExpressionValidator;

/**
 * The Class ExecuteTransformationUnitAction.
 */
public class ExecuteTransformationUnitAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.ExecuteTransformationUnitAction"; //$NON-NLS-1$

	/** The Constant DESC. */
	static private final String DESC = "Execute Transformation Unit";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Execute Transformation Unit";

	/** The t unit. */
	protected Unit tUnit;

	/** The graph. */
	protected Graph graph;

	/**
	 * Instantiates a new execute transformation unit action.
	 * 
	 * @param part
	 *            the part
	 */
	public ExecuteTransformationUnitAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		tUnit = null;
		graph = null;

		// TODO: fix this
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}

		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if (editpart.getModel() instanceof Unit
					&& !(editpart.getModel() instanceof ConditionalUnitPart)
					&& !(editpart.getModel() instanceof Rule)) {
				tUnit = (Unit) editpart.getModel();
				setText("Execute");
				return true;
			}
			if (editpart instanceof GraphTreeEditPart) {
				graph = (Graph) editpart.getModel();
				setText(DESC);
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (graph == null) {
			graph = getGraph();
		}
		if (tUnit == null) {
			tUnit = getTransformationUnit();
		}

		if (graph != null && tUnit != null) {
			Map<String, List<ExpressionValidator>> variable2ExpressionValidators = getParameter2ExpessionValidators();
			if (variable2ExpressionValidators != null) {
				Map<String, Object> assignments = new HashMap<String, Object>();
				if (variable2ExpressionValidators.size() > 0) {
					ParemetersValueDialog vD = new ParemetersValueDialog(
							getWorkbenchPart().getSite().getShell(), SWT.NULL,
							variable2ExpressionValidators);
					vD.open();
					if (!vD.isCancel()) {
						assignments = vD.getAssigment();
					} else {
						return;
					}
				}
				Command command = new ExecuteTransformationUnitCommand(graph,
						tUnit, assignments);
				SendNotify.sendExecuteCommandNotify(graph);
				execute(command);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("play16.png");
	}

	/**
	 * Gets the graph.
	 * 
	 * @return the graph
	 */
	private Graph getGraph() {
		return DialogUtil.runGraphChoiceDialog(getWorkbenchPart().getSite()
				.getShell(), ((Module) tUnit.eContainer())
				.getInstances());
	}

	/**
	 * Gets the transformation unit.
	 * 
	 * @return the transformation unit
	 */
	private Unit getTransformationUnit() {
		Module transSystem = HenshinUtil.INSTANCE
				.getTransformationSystem(graph);
		List<Unit> list = new ArrayList<Unit>(
				transSystem.getUnits());
		return DialogUtil.runTransformationUnitChoiceDialog(getWorkbenchPart()
				.getSite().getShell(), list);
	}

	/**
	 * Gets the parameter2 expession validators.
	 * 
	 * @return the parameter2 expession validators
	 */
	private Map<String, List<ExpressionValidator>> getParameter2ExpessionValidators() {
		Map<String, List<ExpressionValidator>> parameter2Expression = new HashMap<String, List<ExpressionValidator>>();
		for (Parameter parameter : tUnit.getParameters()) {
			parameter2Expression.put(parameter.getName(),
					new ArrayList<ExpressionValidator>());
		}
		return parameter2Expression;
	}

}
