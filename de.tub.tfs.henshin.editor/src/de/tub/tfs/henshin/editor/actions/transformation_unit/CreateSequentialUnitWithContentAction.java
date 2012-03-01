/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.Vector;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.SequentialUnit;
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
 * The Class CreateSequentialUnitWithContentAction.
 */
public class CreateSequentialUnitWithContentAction extends
		CreateTransformationUnitWithContentAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.CreateSequentialUnitWithContentAction";

	/**
	 * Instantiates a new creates the sequential unit with content action.
	 * 
	 * @param part
	 *            the part
	 */
	public CreateSequentialUnitWithContentAction(IWorkbenchPart part) {
		super(part);
		maxContentCount = -1;
		setId(ID);
		setText("Create sequential unit with content");
		setToolTipText("Create sequential unit with content");
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
				"sequentialUnit");

		// asks the user for the new graph name, which has to be unique in this
		// TransfomationSystem
		InputDialog dialog = new InputDialog(
				getWorkbenchPart().getSite().getShell(),
				"Sequential unit name input",
				"Enter a name for the new sequential unit:",
				defaultTransformationName,
				new NameEditValidator(
						transSys,
						HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
						true));
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			Command command = new CreateTransformationUnitWithContentCommand<SequentialUnit>(
					transSys, parentObject, SequentialUnit.class,
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
		return IconUtil.getDescriptor("seqUnit18.png");
	}

}
