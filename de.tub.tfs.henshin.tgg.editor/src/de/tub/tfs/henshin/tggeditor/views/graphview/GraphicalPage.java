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
package de.tub.tfs.henshin.tggeditor.views.graphview;

import java.util.ArrayList;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.tggeditor.actions.AbstractTGGAction;
import de.tub.tfs.henshin.tggeditor.actions.AbstractTggActionFactory;
import de.tub.tfs.henshin.tggeditor.actions.EditAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.NewMarkerUnspecifiedAction;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphicalEditPartFactory;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class GraphicalPage extends MuvitorPage {

	protected GraphicalPaletteRoot graphPaletteRoot;

	public GraphicalPage(MuvitorPageBookView view) {
		super(view);
	}

	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) {
		return new GraphicalContextMenuProvider(viewer);
	}

	@Override
	protected void createCustomActions() {
        registerAction(new EditAttributeAction(getEditor()));
		registerAction(new CreateAttributeAction(getEditor()));
        registerSharedActionAsHandler(ActionFactory.COPY.getId());
        registerSharedActionAsHandler(ActionFactory.CUT.getId());
        registerSharedActionAsHandler(ActionFactory.PASTE.getId()); 
        IExtensionRegistry reg = Platform.getExtensionRegistry();
        IExtensionPoint ep = reg.getExtensionPoint("de.tub.tfs.henshin.tgg.editor.graph.actions");
        IExtension[] extensions = ep.getExtensions();
        for (int i = 0; i < extensions.length; i++) {
        	IExtension ext = extensions[i];
        	IConfigurationElement[] ce = 
        			ext.getConfigurationElements();
        	for (int j = 0; j < ce.length; j++) {

        		try {
        			AbstractTggActionFactory obj = (AbstractTggActionFactory) ce[j].createExecutableExtension("class");

        			registerAction(obj.createAction(getEditor()));

        		} catch (CoreException e) {
        		}


        	}
        }

        
	}

	@Override
	protected EditPartFactory createEditPartFactory() {
		return new GraphicalEditPartFactory();
	}

	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof Module)) {
			parent = parent.eContainer();
		}

		if (parent != null && parent instanceof Module) {
			graphPaletteRoot = new GraphicalPaletteRoot(
					(Module) parent);
		}
		return graphPaletteRoot;
	}

	@Override
	protected EObject[] getViewerContents() {
		return new EObject[] { getModel() };
	}

	@Override
	protected void setupKeyHandler(KeyHandler kh) {
		// TODO Auto-generated method stub

	}

	public Graph getCastedModel() {
		return (Graph) getModel();
	}
	
}
