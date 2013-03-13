package de.tub.tfs.henshin.tggeditor.actions.imports;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tggeditor.TreeEditor;
import de.tub.tfs.henshin.tggeditor.commands.imports.ImportEcorModelCommand;
import de.tub.tfs.henshin.tggeditor.commands.setType.SetImportedPackageCommand;
import de.tub.tfs.henshin.tggeditor.dialogs.resource.ImportEMFModelDialog;
import de.tub.tfs.henshin.tggeditor.editparts.tree.ImportFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;


/**
 * The class imports a target model.
 */
public class ImportTargetAction extends SelectionAction {
	
	public static final String ID = "tggeditor.actions.imports.ImportTargetAction";

	/**
	 * The transformation system in which the source model is imported.
	 */
	private Module transSys;

	/**
	 * Constructor
	 * @param part
	 * @param style
	 */
	public ImportTargetAction(IWorkbenchPart part, int style) {
		super(part, style);
		setId(ID);
		setText("Import Target");
		setToolTipText("Import Target");
	}

	/**
	 * Constructor
	 * @param part
	 */
	public ImportTargetAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Import Target");
		setToolTipText("Import Target");		
	}

	/**
	 * Calculate if this action is enabled.
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */	
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1){
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		
		if ((selectedObject instanceof EditPart)){
			EditPart editpart = (EditPart) selectedObject;
			if ((editpart instanceof ImportFolderTreeEditPart)){
				transSys = (Module) editpart.getParent().getModel();
				return true;
			}
		}
		return false;
	}

	/**
	 * Run the import action.
	 */	
	@Override
	public void run() {
		ImportEMFModelDialog dialog = new ImportEMFModelDialog(
				getWorkbenchPart().getSite().getShell(), "Target");
		
		dialog.setMultipleSelection(false);
		dialog.open();
		
		if(dialog.getReturnCode() == Window.OK){
			List<EPackage> ePackages = new ArrayList<EPackage>();
			Object[] selection = dialog.getResult();
			if(selection != null){
				for(Object object: selection){
					if(object instanceof EPackage){
						ePackages.add((EPackage)object);
					}
				}
			}
			if (ePackages.size() > 0) {
				TGG tgg = ((TreeEditor) getWorkbenchPart()).getLayout();

//				boolean packageCorrect = true;
//				if (tgg.getTarget()!=null) {
//					List<EClass> nodeTypes = NodeTypes.getNodeTypesOfEPackage(ePackages.get(0), false);

//					List<String> nodeTypeNames = new ArrayList<String>();
//					for (EClass ec : nodeTypes) {
//						nodeTypeNames.add(ec.getName());
//					}
					
//					for (NodeLayout nL : tgg.getNodelayouts()) {
//						EClass nodeType = nL.getNode().getType();
//						String nodeTypeName = nodeType.getName();
//						if (NodeUtil.isTargetNode(tgg, nodeType) && !nodeTypeNames.contains(nodeTypeName)) {
//							packageCorrect = false;
//						}
//					}
//					if (!packageCorrect) {
//						Shell shell = new Shell();
//						MessageDialog.openError(shell, 
//								"Package import failed", 
//								"The Package "+ePackages.get(0).getName()+" could not be imported"
//								+", because the some node types are not included or you have selected a wrong package.");
//						shell.dispose();
//					} else {
						//altes Package entfernen
////						transSys.getImports().remove(tgg.getTarget());
////						tgg.setTarget(null);
						
//						ImportEcorModelCommand command = new ImportEcorModelCommand(
//								transSys, ePackages);
						
//						command.add(new SetTargetModellCommand(ePackages.get(0), tgg));
//						execute(command);
						
//						Shell shell = new Shell();
//						MessageDialog.openInformation(shell, "Package imported", 
//								"Please close and reopen the tgg file to take the import effect");
//						shell.dispose();
//					}
//				}
//				else {
				CompoundCommand cmd = new CompoundCommand();
					
				cmd.add(new SetImportedPackageCommand(ePackages.get(0), tgg, TripleComponent.TARGET));
				cmd.add(new ImportEcorModelCommand(transSys, ePackages));
				execute(cmd);
//				}
			}		
		}		
	}
	
}
