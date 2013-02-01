/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateFlowDiagramCommand;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * An {@link Action} class for creating {@link FlowDiagram}s.
 * 
 * @author nam
 */
public class CreateFlowDiagramAction extends SelectionAction {

	/**
	 * An unique id for this action.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.createFlowDiagram";

	/**
	 * The containing {@link FlowControlSystems} of the new {@link FlowDiagram}
	 */
	private FlowControlSystem flowControlSystem;

	/**
	 * Inherited constructor.
	 * 
	 * @see SelectionAction#SelectionAction(IWorkbenchPart)
	 */
	public CreateFlowDiagramAction(IWorkbenchPart part) {
		super(part);

		setText("Flow Diagram");
		setId(ID);
		setImageDescriptor(ResourceUtil.ICONS.FLOW_DIAGRAM.descr(16));
		setDescription("Creates a new Control Flow Diagram");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		flowControlSystem = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object model = editpart.getModel();

				if (model instanceof Module) {
					flowControlSystem = FlowControlUtil.INSTANCE
							.getFlowControlSystem((EObject) model);
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(FlowDiagram.class) != null) {
					flowControlSystem = (FlowControlSystem) ((EContainerDescriptor) model)
							.getContainer();
				}
			}
		}

		return flowControlSystem != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		InputDialog dialog = new InputDialog(getWorkbenchPart().getSite()
				.getShell(), "Flow Diagam Name Input",
				"Enter a name for the new diagram:",
				ModelUtil.getNewChildDistinctName(flowControlSystem,
						FlowControlPackage.FLOW_CONTROL_SYSTEM__UNITS,
						"flow diagram"), new NameEditValidator(
						flowControlSystem,
						FlowControlPackage.FLOW_CONTROL_SYSTEM__UNITS, true,
						flowControlSystem,
						FlowControlPackage.FLOW_DIAGRAM__NAME));

		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateFlowDiagramCommand(dialog.getValue(),
					flowControlSystem);

			execute(command);
		}

	}
}
