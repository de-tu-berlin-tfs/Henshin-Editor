/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.imports;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.imports.ImportEcorModelCommand;
import de.tub.tfs.henshin.tggeditor.dialogs.resource.ImportEMFModelDialog;
import de.tub.tfs.henshin.tggeditor.editparts.tree.TransformationSystemTreeEditPart;


public class ImportEMFModelAction extends SelectionAction {

	public ImportEMFModelAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Import EMF Model");
		setToolTipText("Dialog to import EMF Models");
	}

	public static final String ID = "tggeditor.actions.imports.ImportEMFModelAction";
	private Module transSys;
	
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if ((selecObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selecObject;
			if ((editpart instanceof TransformationSystemTreeEditPart)) {
				transSys = (Module) editpart.getModel();
				return true;
			}
		}
		return false;
	}
	
	
	
	@Override
	public void run() {
		ImportEMFModelDialog dialog = new ImportEMFModelDialog(
				getWorkbenchPart().getSite().getShell(), "");

		dialog.setMultipleSelection(true);
		dialog.open();

		if (dialog.getReturnCode() == Window.OK) {
			List<EPackage> ePackages = new ArrayList<EPackage>();
			Object[] selections = dialog.getResult();
			if (selections != null) {
				for (Object object : selections) {
					if (object instanceof EPackage) {
						ePackages.add((EPackage) object);
					}
				}
				if (ePackages.size() > 0) {
					ImportEcorModelCommand command = new ImportEcorModelCommand(
							transSys, ePackages);
					execute(command);
				}
			}		
		}
	}
   
}
