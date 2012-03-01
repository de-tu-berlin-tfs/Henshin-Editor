/*
 * 
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.graph.CreateGraphCommand;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class CreateGraphAction.
 */
public class CreateGraphAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateGraphAction";

	/** The transformation system. */
	private TransformationSystem transformationSystem;

	/**
	 * Instantiates a new create graph action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateGraphAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Graph");
		setToolTipText("Creates a new instance graph.");
		setImageDescriptor(IconUtil.getDescriptor("graph18.png"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		transformationSystem = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object model = editpart.getModel();

				if (model instanceof TransformationSystem) {
					transformationSystem = (TransformationSystem) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(Graph.class) != null) {
					transformationSystem = (TransformationSystem) ((EContainerDescriptor) model)
							.getContainer();
				}
			}
		}

		return transformationSystem != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		String defaultGraphName = ModelUtil.getNewChildDistinctName(
				transformationSystem,
				HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES, "graph");

		InputDialog dialog = new InputDialog(getWorkbenchPart().getSite()
				.getShell(), "Graph Name Input",
				"Enter a name for the new instance graph:", defaultGraphName,
				new NameEditValidator(transformationSystem,
						HenshinPackage.TRANSFORMATION_SYSTEM__INSTANCES, true));

		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateGraphCommand(transformationSystem,
					dialog.getValue());

			execute(command);
		}
	}
}
