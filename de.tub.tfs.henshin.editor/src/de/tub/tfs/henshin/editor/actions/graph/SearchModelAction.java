/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 * SearchModelAction.java
 * created on 18.03.2012 17:05:30
 */
package de.tub.tfs.henshin.editor.actions.graph;

import java.util.Collection;
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.interfaces.Constants;
import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.ui.dialog.FilterMetaModelDialog;
import de.tub.tfs.henshin.editor.ui.graph.GraphView;
import de.tub.tfs.henshin.editor.util.HenshinSelectionUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;

/**
 * @author huuloi
 *
 */
public class SearchModelAction extends SelectionAction {

	public static final String ID = "de.tub.tfs.henshin.editor.actions.graph.SearchModelAction";
	
	private Graph graph;
	
	public SearchModelAction(IWorkbenchPart part, Graph graph) {
		
		super(part);
		setId(ID);
		setText(Messages.MODEL_SEARCH);
		setToolTipText(Messages.MODEL_SEARCH_DESC);
		this.graph = graph;
	}
	
	@Override
	protected boolean calculateEnabled() {
		
		return graph != null && graph.getNodes().size() > 0;
	}

	@Override
	public void run() {
		

		GraphView graphView = HenshinSelectionUtil.getInstance().getActiveGraphView(graph);
		Shell shell = graphView.getSite().getShell();
		Collection<EPackage> usedEPackages = ModelUtil.getEPackagesOfGraph(graph);
		FilterMetaModelDialog dialog = new FilterMetaModelDialog(
				shell, 
				usedEPackages.toArray(new EPackage[usedEPackages.size()]), 
				Messages.SEARCH_FOR_META_MODEL, 
				Messages.SEARCH_FOR_META_MODEL_DESC
		);
		dialog.open();
		
		if (dialog.getReturnCode() == Window.OK) {
			List<EPackage> metaModels = dialog.getMetaModels();
			if (metaModels.size() == 1) {
				EPackage searchForEpackage = metaModels.get(0);
				List<NodeEditPart> nodeEditParts = HenshinSelectionUtil.getInstance().getNodeEditParts(graph);
				for (NodeEditPart nodeEditPart : nodeEditParts) {
					if (EcoreUtil.equals(nodeEditPart.getCastedModel().getType().getEPackage(), searchForEpackage)) {
						nodeEditPart.getFigure().setBackgroundColor(ColorConstants.lightBlue);
					}
					else if (!EcoreUtil.equals(nodeEditPart.getCastedModel().getType().getEPackage(), searchForEpackage) && nodeEditPart.getFigure().getBackgroundColor() != nodeEditPart.getDefaultColor()) {
						nodeEditPart.getFigure().setBackgroundColor(nodeEditPart.getDefaultColor());
					}
				}
			}
			
			// refresh
			((GraphEditPart)graphView.getCurrentGraphPage().getCurrentViewer().getEditPartRegistry().get(graph)).refresh();
		}
	}
	
	@Override
	public ImageDescriptor getImageDescriptor() {
		return ResourceUtil.ICONS.MODEL_SEARCH_TOOL.descr(Constants.SIZE_16);
	}
}
