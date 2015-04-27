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
package de.tub.tfs.henshin.editor.editparts.graph.tree;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.HenshinEditPolicy;
import de.tub.tfs.henshin.editor.editparts.TreeContainerEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.GraphClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.graph.GraphComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.graph.GraphPropertySource;
import de.tub.tfs.henshin.editor.ui.condition.ConditionView;
import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * The Class GraphTreeEditPart.
 */
public class GraphTreeEditPart extends AdapterTreeEditPart<Graph> implements
		IDirectEditPart {

	/**
	 * Constructs a new {@link GraphTreeEditPart}.
	 * 
	 * @param model
	 *            the model {@link Graph}.
	 */
	public GraphTreeEditPart(Graph model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.GRAPH__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(
				HenshinUtil.INSTANCE.getTransformationSystem(getCastedModel()),
				HenshinPackage.MODULE__INSTANCES,
				getCastedModel(), true);
	}

	/**
	 * Finds a condition view that showing the deleted formula and closes it.
	 */
	public void closeConditionViewsShowing(final Formula formula) {
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final List<ConditionView> conditionViews = FormulaUtil
				.getOpenedConditionViews(formula);
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow()
				.getActivePage();

		for (final ConditionView conditionView : conditionViews) {
			page.hideView(conditionView);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new GraphComponentEditPolicy());

		installEditPolicy(HenshinEditPolicy.CLIPBOARD_ROLE,
				new GraphClipboardEditPolicy());
		
		installEditPolicy(EditPolicy.TREE_CONTAINER_ROLE,
				new TreeContainerEditPolicy());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		if (getCastedModel().getName() == null) {
			return new String();
		}
		return getCastedModel().getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<Object> getModelChildren() {
		List<Object> list = new ArrayList<Object>();

		if (getCastedModel().getFormula() != null) {
			list.add(getCastedModel().getFormula());
		}

		list.addAll(getCastedModel().getNodes());
		list.addAll(getCastedModel().getEdges());

		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterTreeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		if (Notification.REMOVING_ADAPTER == notification.getEventType()) {
			return;
		}

		refresh();

		if (notification.getNewValue() instanceof Node) {
			EditPart childPart = findEditPartFor(notification.getNewValue());

			childPart.getViewer().select(childPart);
		}

		// If a formula is deleted, then closes all showing condition views
		if (notification.getOldValue() instanceof Formula) {
			closeConditionViewsShowing((Formula) notification.getOldValue());
		}

		int eventType = notification.getEventType();

		if (eventType == Notification.RESOLVE) {
			performOpen();
		}

		super.notifyChanged(notification);
	}

	/**
	 * Loads an image from the icon package to show in the tree view for this
	 * edit part.
	 * 
	 * @return An icon as {@link Image} for this edit part's {@link TreeItem} or
	 *         <code>null</code>, if loading failed for some reasons.
	 */
	@Override
	protected Image getImage() {
		return ResourceUtil.ICONS.GRAPH.img(18);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new GraphPropertySource(getCastedModel());
	}
}
