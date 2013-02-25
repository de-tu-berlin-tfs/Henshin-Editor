package tggeditor.actions.imports;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import tgg.NodeLayout;
import tgg.TGG;
import tggeditor.TreeEditor;
import tggeditor.commands.imports.ImportEcorModelCommand;
import tggeditor.commands.setType.SetSourceModellCommand;
import tggeditor.dialogs.resource.ImportEMFModelDialog;
import tggeditor.editparts.tree.ImportFolderTreeEditPart;
import tggeditor.util.NodeTypes;
import tggeditor.util.NodeUtil;

/**
 * The class imports a source model.
 */
public class ImportSourceAction extends SelectionAction {
	
	public static final String ID = "tggeditor.actions.imports.ImportSourceAction";
	/**
	 * The transformation system in which the source model is imported.
	 */
	private Module transSys;

	/**
	 * Constructor
	 * @param part
	 * @param style
	 */
	public ImportSourceAction(IWorkbenchPart part, int style) {
		super(part, style);
		setId(ID);
		setText("Import Source");
		setToolTipText("Import Source");		
	}

	/**
	 * Constructor
	 * @param part
	 */
	public ImportSourceAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Import Source");
		setToolTipText("Import Source");
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
	 * This method run the import action.
	 */	
	@Override
	public void run() {
		ImportEMFModelDialog dialog = new ImportEMFModelDialog(
				getWorkbenchPart().getSite().getShell(), "Source");

		dialog.setMultipleSelection(false);
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
					TGG tgg = ((TreeEditor) getWorkbenchPart()).getLayout();

					boolean packageCorrect = true;
					if (tgg.getSource()!=null) {
						List<EClass> nodeTypes = NodeTypes.getNodeTypesOfEPackage(ePackages.get(0), false);

						List<String> nodeTypeNames = new ArrayList<String>();
						for (EClass ec : nodeTypes) {
							nodeTypeNames.add(ec.getName());
						}
						
						for (NodeLayout nL : tgg.getNodelayouts()) {
							EClass nodeType = nL.getNode().getType();
							String nodeTypeName = nodeType.getName();
							if (NodeUtil.isSourceNode(tgg, nodeType) && !nodeTypeNames.contains(nodeTypeName)) {
								packageCorrect = false;
							}
						}
						if (!packageCorrect) {
							Shell shell = new Shell();
							MessageDialog.openError(shell, 
									"Package import failed", 
									"The Package "+ePackages.get(0).getName()+" could not be imported"
									+", because some node types are not included or you have selected a wrong package.");
							shell.dispose();
						} else {
							//remove old package
							transSys.getImports().remove(tgg.getSource());
							tgg.setSource(null);
							
							ImportEcorModelCommand command = new ImportEcorModelCommand(
									transSys, ePackages);
							
							command.add(new SetSourceModellCommand(ePackages.get(0), tgg));
							execute(command);
							
							Shell shell = new Shell();
							MessageDialog.openInformation(shell, "Package imported", 
									"Please close and reopen the tgg file to take the import effect");
							shell.dispose();
						}
					}
					else {
						ImportEcorModelCommand command = new ImportEcorModelCommand(
								transSys, ePackages);
						
						command.add(new SetSourceModellCommand(ePackages.get(0), tgg));
						execute(command);
					}
				}
			}		
		}
	}
}
