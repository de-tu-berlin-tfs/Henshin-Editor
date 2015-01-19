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
 * 
 */
package de.tub.tfs.henshin.editor.ui.flow_diagram;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.editor.actions.flow_diagram.AddCompoundActivityChildAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ClearActivityContentAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.DeleteActivityContentAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ExecuteFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.FlowDiagram2UnitAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.RunFlowDiagramToolbarAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SetActivityContentAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SetFlowDiagramInputParameterAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.SetFlowDiagramOutputParameterAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.UnNestActivityAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateFlowDiagramAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateFlowDiagramToolbarAction;
import de.tub.tfs.henshin.editor.actions.flow_diagram.ValidateParameterMappingsAction;
import de.tub.tfs.henshin.editor.actions.rule.ValidateRuleToolBarAction;
import de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.FlowDiagramEditpartFactory;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;
import de.tub.tfs.henshin.model.layout.HenshinLayoutFactory;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * 
 * @author nam
 * 
 */
public class FlowDiagramPage extends MuvitorPage {

	private EContainerDescriptor parameters;

	/**
	 * @param view
	 */
	public FlowDiagramPage(MuvitorPageBookView view) {
		super(view);

		parameters = HenshinLayoutFactory.eINSTANCE
				.createEContainerDescriptor();
		parameters.setContainer(getModel());
	}

	/**
	 * @return the parameters
	 */
	public GraphicalViewer getParametersViewer() {
		return getViewers().get(1);
	}

	public GraphicalViewer getDiagramViewer() {
		return getViewers().get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.ui.MuvitorPage#createContextMenuProvider(org.eclipse
	 * .gef.EditPartViewer)
	 */
	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) {
		return new FlowDiagramContextMenuProvider(viewer, getActionRegistry());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.ui.MuvitorPage#createCustomActions()
	 */
	@Override
	protected void createCustomActions() {
		registerSharedActionAsHandler(ActionFactory.COPY.getId());
		registerSharedActionAsHandler(ActionFactory.CUT.getId());
		registerSharedActionAsHandler(ActionFactory.PASTE.getId());

		registerAction(new SetActivityContentAction(getEditor()));
		registerAction(new DeleteActivityContentAction(getEditor()));
		registerAction(new AddCompoundActivityChildAction(getEditor()));
		registerAction(new SetFlowDiagramOutputParameterAction(getEditor()));
		registerAction(new SetFlowDiagramInputParameterAction(getEditor()));

		registerSharedAction(ValidateParameterMappingsAction.ID);
		registerSharedAction(UnNestActivityAction.ID);
		registerSharedAction(ValidateRuleToolBarAction.ID);
		registerSharedAction(ExecuteFlowDiagramAction.ID);
		registerSharedAction(ValidateFlowDiagramAction.ID);
		registerSharedAction(ClearActivityContentAction.ID);
		registerSharedAction(FlowDiagram2UnitAction.ID);

		getToolBarManager().add(
				new RunFlowDiagramToolbarAction((FlowDiagram) getModel(),
						getEditor()));
		getToolBarManager().add(
				new ValidateFlowDiagramToolbarAction((FlowDiagram) getModel(),
						getEditor(), this));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.ui.MuvitorPage#customizeGraphicalViewerComposite(org
	 * .eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void customizeGraphicalViewerComposite(Composite viewComposite) {
		final SashForm sashForm = (SashForm) viewComposite;

		final Composite diagramViewerComposite = new Composite(viewComposite,
				SWT.BORDER);

		diagramViewerComposite.setLayout(new FormLayout());

		Control diagramViewer = sashForm.getChildren()[0];

		Composite parametersComposite = new Composite(viewComposite, SWT.BORDER);

		GridLayout compositeLayout = new GridLayout(1, true);

		compositeLayout.marginHeight = 0;
		compositeLayout.marginWidth = 0;
		compositeLayout.verticalSpacing = 0;

		parametersComposite.setLayout(compositeLayout);

		GridData paramLayoutData = new GridData(GridData.FILL_BOTH);
		GridData expandButtonLayoutData = new GridData(GridData.FILL_HORIZONTAL);

		Composite toolBar = new Composite(parametersComposite, SWT.BORDER);

		toolBar.setLayout(new FormLayout());

		Button hideButton = new Button(toolBar, SWT.ARROW | SWT.RIGHT
				| SWT.FLAT);

		hideButton.setToolTipText("Hide Parameters");

		final Button showButton = new Button(diagramViewerComposite, SWT.ARROW
				| SWT.LEFT);

		FormData showButtonLayoutData = new FormData();

		showButtonLayoutData.right = new FormAttachment(100);
		showButtonLayoutData.bottom = new FormAttachment(100);
		showButtonLayoutData.top = new FormAttachment(0);

		showButton.setLayoutData(showButtonLayoutData);
		showButton.setVisible(false);
		showButton.setToolTipText("Show Parameters");

		hideButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				sashForm.setMaximizedControl(diagramViewerComposite);

				showButton.setVisible(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		showButton.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				sashForm.setMaximizedControl(null);

				showButton.setVisible(false);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		FormData hideButtonLayoutData = new FormData();

		hideButtonLayoutData.right = new FormAttachment(100);
		hideButtonLayoutData.top = new FormAttachment(0);
		hideButtonLayoutData.bottom = new FormAttachment(100);

		hideButton.setLayoutData(hideButtonLayoutData);

		CLabel title = new CLabel(toolBar, SWT.NONE);

		title.setText("Parameters");
		title.setForeground(ColorConstants.gray);
		title.setFont(SWTResourceManager.getFont("Sans", 10, SWT.BOLD));//$NON-NLS-1$

		toolBar.setLayoutData(expandButtonLayoutData);

		Control parameter = sashForm.getChildren()[1];

		parameter.setLayoutData(paramLayoutData);
		parameter.setParent(parametersComposite);

		FormData diagramLayoutData = new FormData();

		diagramLayoutData.bottom = new FormAttachment(100);
		diagramLayoutData.top = new FormAttachment(0);
		diagramLayoutData.left = new FormAttachment(0);
		diagramLayoutData.right = new FormAttachment(100);

		diagramViewer.setLayoutData(diagramLayoutData);
		diagramViewer.setParent(diagramViewerComposite);

		sashForm.setWeights(new int[] { 3, 1 });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.ui.MuvitorPage#createEditPartFactory()
	 */
	@Override
	protected EditPartFactory createEditPartFactory() {
		return new FlowDiagramEditpartFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.ui.MuvitorPage#createPaletteRoot()
	 */
	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		return new FlowDiagramPaletteRoot();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.ui.MuvitorPage#getViewerContents()
	 */
	@Override
	protected EObject[] getViewerContents() {

		return new EObject[] { getModel(), parameters };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.muvitor.ui.MuvitorPage#getViewerWeights()
	 */
	@Override
	protected int[] getViewerSashWeights() {
		return new int[] { 3, 1 };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.ui.MuvitorPage#setupKeyHandler(org.eclipse.gef.KeyHandler
	 * )
	 */
	@Override
	protected void setupKeyHandler(KeyHandler kh) {
	}
}
