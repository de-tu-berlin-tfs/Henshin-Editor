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
package de.tub.tfs.henshin.editor.ui.flow_diagram;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.PageBookView;

import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * A {@link PageBookView view} to display {@link FlowDiagram flow diagram}s in
 * the workbench.
 * 
 * @author nam
 * 
 * @see MuvitorPageBookView
 */
public class FlowDiagramView extends MuvitorPageBookView {

	/**
	 * An unique ID for this view.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.ui.flow_diagram.FlowDiagramView";

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.ui.MuvitorPageBookView#calculatePartName()
	 */
	@Override
	protected String calculatePartName() {
		return "Flow Diagram: " + ((FlowDiagram) getModel()).getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.ui.MuvitorPageBookView#createPageForModel(org.eclipse
	 * .emf.ecore.EObject)
	 */
	@Override
	protected IPage createPageForModel(EObject forModel) {
		FlowDiagramPage p = new FlowDiagramPage(this);

		return p;
	}

}
