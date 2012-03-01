/**
 * UnNestActivityAction.java
 *
 * Created 30.12.2011 - 17:31:39
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.flow_diagram.UnNestActivityCommand;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;

/**
 * @author nam
 * 
 */
public class UnNestActivityAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.UnNestActivityAction";

	private List<Activity> models;

	/**
	 * @param part
	 */
	public UnNestActivityAction(IWorkbenchPart part) {
		super(part);

		setText("Detach");
		setId(ID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		models = new LinkedList<Activity>();

		EObject container = null;

		for (Object o : selectedObjects) {
			if (o instanceof EditPart) {
				Object model = ((EditPart) o).getModel();

				if (model instanceof Activity) {
					EObject modelContainer = ((Activity) model).eContainer();

					if (container == null) {
						container = modelContainer;
					} else if (container != modelContainer) {
						return false;
					}

					models.add((Activity) model);
				}
			}
		}

		return !models.isEmpty() && container instanceof CompoundActivity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand cmd = new CompoundCommand("Detach Activities");

		for (Activity a : models) {
			cmd.add(new UnNestActivityCommand(a));
		}

		execute(cmd);
	}
}
