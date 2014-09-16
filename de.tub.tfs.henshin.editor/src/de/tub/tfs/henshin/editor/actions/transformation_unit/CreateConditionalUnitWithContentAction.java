/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.Vector;

import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.transformation_unit.CreateTransformationUnitWithContentCommand;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;

/**
 * The Class CreateConditionalUnitWithContentAction.
 */
public class CreateConditionalUnitWithContentAction extends
		CreateTransformationUnitWithContentAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateConditionalUnitWithContentAction";

	/**
	 * Instantiates a new creates the conditional unit with content action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateConditionalUnitWithContentAction(IWorkbenchPart part) {
		super(part);
		maxContentCount = 3;
		setId(ID);
		setText("Create conditional unit with content");
		setToolTipText("Create conditional unit with content");
		selectedTransUnits = new Vector<Unit>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		String defaultTransformationName = ModelUtil.getNewChildDistinctName(
				transSys,
				HenshinPackage.MODULE__UNITS,
				"conditionalUnit");

		// asks the user for the new graph name, which has to be unique in this
		// TransfomationSystem
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"Conditional unit name input",
				"Enter a name for the new conditional unit:",
				defaultTransformationName,
				new NameEditValidator(
						transSys,
						HenshinPackage.MODULE__UNITS,
						true));
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateTransformationUnitWithContentCommand<ConditionalUnit>(
					transSys, parentObject, ConditionalUnit.class,
					selectedTransUnits, dialog.getValue());
			if (command.canExecute()) {
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
		// TODO Bild ändern
		return IconUtil.getDescriptor("conditionalUnit18.png");
	}

}
