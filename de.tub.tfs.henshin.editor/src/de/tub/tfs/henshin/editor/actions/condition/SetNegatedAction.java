/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.condition;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.condition.SetNegatedCommand;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author angel
 * 
 */
public class SetNegatedAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.rule.condition.SetNegatedAction";

	private NestedCondition ac;

	/**
	 * @param part
	 */
	public SetNegatedAction(final IWorkbenchPart part) {
		super(part);

		setId(ID);
		setDescription("Set negated value of selected application condition.");
		setToolTipText("Set negated value of selected application condition.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		final List<?> selectedObjects = getSelectedObjects();

		if (selectedObjects.size() == 1) {
			final Object selected = selectedObjects.get(0);

			if (selected instanceof EditPart) {
				final Object model = ((EditPart) selected).getModel();

				if (model instanceof Graph) {
					Graph castedModel = (Graph) model;

					if (castedModel.eContainer() instanceof NestedCondition) {
						ac = (NestedCondition) castedModel.eContainer();

						if (ac.eContainer() instanceof Not) {
							setText("Remove Negation");
							setImageDescriptor(ResourceUtil.ICONS.EXIST
									.descr(16));
						} else {
							setText("Negate");
							setImageDescriptor(ResourceUtil.ICONS.NOT_EXIST
									.descr(16));
						}

						return true;
					}
				}
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
		execute(new SetNegatedCommand(ac));
	}
}
