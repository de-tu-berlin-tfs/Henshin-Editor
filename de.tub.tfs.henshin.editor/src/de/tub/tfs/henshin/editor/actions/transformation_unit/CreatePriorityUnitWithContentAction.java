/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.Vector;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;
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
 * The Class CreatePriorityUnitWithContentAction.
 */
public class CreatePriorityUnitWithContentAction extends
		CreateTransformationUnitWithContentAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreatePriorityUnitWithContentAction";

	/**
	 * Instantiates a new creates the priority unit with content action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreatePriorityUnitWithContentAction(IWorkbenchPart part) {
		super(part);
		maxContentCount = -1;
		setId(ID);
		setText("Create priority unit with content");
		setToolTipText("Create priority unit with content");
		selectedTransUnits = new Vector<TransformationUnit>();
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
				HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				"priorityUnit");

		// asks the user for the new graph name, which has to be unique in this
		// TransfomationSystem
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"Priority unit name input",
				"Enter a name for the new priority unit:",
				defaultTransformationName,
				new NameEditValidator(
						transSys,
						HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
						true));
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateTransformationUnitWithContentCommand<PriorityUnit>(
					transSys, parentObject, PriorityUnit.class,
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
		return IconUtil.getDescriptor("priority16.png");
	}

}
