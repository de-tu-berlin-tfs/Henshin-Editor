package de.tub.tfs.henshin.editor.actions.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.CreateTransformationUnitCommand;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

/**
 * The Class CreateRuleAction.
 */
public class CreateRuleAction extends SelectionAction {

	/** The Constant ID for this Action. */
	public static final String ID = "henshineditor.actions.CreateRuleAction";

	/**
	 * The containing transformation system of this Editparts rule model object.
	 */
	private Module transformationSystem;

	/**
	 * Instantiates a new create rule action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateRuleAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Rule");
		setImageDescriptor(IconUtil.getDescriptor("Rule16.png"));
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

				if (model instanceof Module) {
					transformationSystem = (Module) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(Unit.class) != null) {
					transformationSystem = (Module) ((EContainerDescriptor) model)
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
		final String defaultRuleName = ModelUtil.getNewChildDistinctName(
				transformationSystem,
				HenshinPackage.MODULE__UNITS, "rule");

		// asks the user for the new rule name, which has to be unique in the
		// containing TransfomationSystem
		final InputDialog dialog = new InputDialog(getWorkbenchPart().getSite()
				.getShell(), "Rule Name Input",
				"Enter a name for the new rule:", defaultRuleName,
				new NameEditValidator(transformationSystem,
						HenshinPackage.MODULE__UNITS, true));

		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateTransformationUnitCommand<Rule>(
					transformationSystem,
					HenshinFactory.eINSTANCE.createRule(), dialog.getValue(),
					HenshinPackage.Literals.MODULE__UNITS);

			execute(command);
		}
	}
}
