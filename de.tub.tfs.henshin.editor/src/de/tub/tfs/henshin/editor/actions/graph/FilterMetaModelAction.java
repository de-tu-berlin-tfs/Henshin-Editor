/**
 * FilterMetaModelAction.java
 * created on 14.02.2012 08:11:36
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.Collection;
import java.util.List;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.graph.FilterMetaModelCommand;
import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.dialog.FilterMetaModelDialog;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author huuloi
 *
 */
public class FilterMetaModelAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.FilterMetaModelAction";
	
	private Graph graph;

	public FilterMetaModelAction(IWorkbenchPart part) {
		
		super(part);
		setId(ID);
		setText(Messages.FILTER_META_MODEL);
		setToolTipText(Messages.FILTER_META_MODEL_DESC);
	}

	@Override
	protected boolean calculateEnabled() {

		boolean result = false;
		
		List<?> selectedObjects = getSelectedObjects();
		
		if (selectedObjects.size() == 1) {
			Object selected = selectedObjects.get(0);
			
			if (selected instanceof EditPart) {
				EditPart host = (EditPart) selected;
				Object hostModel = host.getModel();
				
				if (hostModel instanceof Graph) {
					graph = (Graph) hostModel;
					
					if (graph != null) {
						result = ModelUtil.getEPackagesOfGraph(graph).size() > 1;
					}
				}
			}
		}
		
		return result;
	}
	
	
	@Override
	public void run() {
		

		Collection<EPackage> usedEPackages = ModelUtil.getEPackagesOfGraph(graph);

		FilterMetaModelDialog dialog = new FilterMetaModelDialog(
				getWorkbenchPart().getSite().getShell(), 
				usedEPackages.toArray(new EPackage[usedEPackages.size()]), 
				Messages.FILTER_META_MODEL, 
				Messages.FILTER_META_MODEL_DESC
		);
		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK) {
			FilterMetaModelCommand command = new FilterMetaModelCommand(graph, dialog.getMetaModels());
			execute(command);
		}
	}
	
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return ResourceUtil.ICONS.EPACKAGE_FILTER.descr(Constants.SIZE_16);
	}


}
