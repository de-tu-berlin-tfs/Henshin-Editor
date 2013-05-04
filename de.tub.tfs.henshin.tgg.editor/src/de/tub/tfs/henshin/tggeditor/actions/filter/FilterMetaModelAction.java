/**
 * FilterMetaModelAction.java
 * created on 14.02.2012 08:11:36
 */
package de.tub.tfs.henshin.tggeditor.actions.filter;

import java.util.Collection;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.commands.FilterMetaModelCommand;
import de.tub.tfs.henshin.tggeditor.util.IconUtil;
import de.tub.tfs.henshin.tggeditor.util.ModelUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.FilterMetaModelDialog;

/**
 * @author huuloi
 *
 */
public class FilterMetaModelAction extends SelectionAction {
	
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.FilterMetaModelAction";
	
	private TripleGraph graph;

	public FilterMetaModelAction(IWorkbenchPart part, TripleGraph graph) {
		super(part);
		this.graph = graph;
		setId(ID);
		setText("Filter model");
		setToolTipText("Choose a meta model to filter:");
	}

	@Override
	protected boolean calculateEnabled() {
		return ModelUtil.getEPackagesOfGraph(graph).size() > 1;
	}
	
	
	@Override
	public void run() {

		Collection<EPackage> usedEPackages = ModelUtil.getEPackagesOfGraph(graph);

		FilterMetaModelDialog dialog = new FilterMetaModelDialog(
				getWorkbenchPart().getSite().getShell(), 
				usedEPackages.toArray(new EPackage[usedEPackages.size()]), 
				"Filter model", 
				"Choose a meta model to filter:"
		);
		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK) {
			FilterMetaModelCommand command = new FilterMetaModelCommand(graph, dialog.getMetaModels());
			execute(command);
		}
	}
	
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("modelFilter16.png");
	}


}
