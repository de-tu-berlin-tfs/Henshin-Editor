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
package de.tub.tfs.henshin.editor.ui.condition;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.editor.actions.graph.CreateAttributeAction;
import de.tub.tfs.henshin.editor.actions.rule.DeleteMappingAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateParameterAction;
import de.tub.tfs.henshin.editor.editparts.rule.RuleEditPartFactory;
import de.tub.tfs.henshin.editor.ui.rule.RuleContextMenuProvider;
import de.tub.tfs.henshin.editor.util.FormulaUtil;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class ConditionPage.
 * 
 * @author Angeline Warning
 */
public class ConditionPage extends MuvitorPage {

	public static int PREMISE_INDEX = 0;
	public static int CONDITION_INDEX = 1;
	/** The condition view. */
	private ConditionView conditionView;

	/** The pallet root. */
	private ConditionPaletteRoot palletRoot;

	/**
	 * Konstruktor erhält MuvitorPageBookView.
	 * 
	 * @param view
	 *            MuvitorPageBookView
	 */
	public ConditionPage(MuvitorPageBookView view) {
		super(view);
		this.conditionView = (ConditionView) view;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seemuvitorkit.ui.MuvitorPage#createContextMenuProvider(org.eclipse.gef.
	 * EditPartViewer)
	 */
	@Override
	protected ContextMenuProviderWithActionRegistry createContextMenuProvider(
			EditPartViewer viewer) {
		return new RuleContextMenuProvider(viewer, getActionRegistry());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createCustomActions()
	 */
	@Override
	protected void createCustomActions() {
		registerSharedActionAsHandler(ActionFactory.COPY.getId());
		registerSharedActionAsHandler(ActionFactory.CUT.getId());
		registerSharedActionAsHandler(ActionFactory.PASTE.getId());
		registerAction(new CreateAttributeAction(getEditor()));
		registerAction(new DeleteMappingAction(getEditor()));
		registerAction(new CreateParameterAction(getEditor()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createEditPartFactory()
	 */
	@Override
	protected EditPartFactory createEditPartFactory() {
		return new RuleEditPartFactory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createPaletteRoot()
	 */
	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		EObject parent = getCastedModel().eContainer();
		while (parent != null && !(parent instanceof Rule)) {
			parent = parent.eContainer();
		}

		if (parent != null && parent instanceof Rule) {
			palletRoot = new ConditionPaletteRoot(
					((Rule) parent).getModule());
		}

		return palletRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#getViewerContents()
	 */
	@Override
	protected EObject[] getViewerContents() {
		return new EObject[] { FormulaUtil.getPremise(getCastedModel()),
				getCastedModel() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorPage#setupKeyHandler(org.eclipse.gef.KeyHandler)
	 */
	@Override
	protected void setupKeyHandler(KeyHandler kh) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.ui.MuvitorPage#notifyChanged(org.eclipse.emf.common.notify
	 * .Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		final int eventId = notification.getEventType();
		// ensure we have a visible Formula
		if (featureId == HenshinPackage.FORMULA
				&& (eventId == Notification.SET || eventId == Notification.ADD || eventId == Notification.REMOVE)) {
			conditionView.createPageForModel(getCastedModel());
		}
	}

	/**
	 * Returns casted model if model is from type formula, otherwise returns
	 * {@code null}.
	 * 
	 * @return A formula model or {@code null} if model is not from type
	 *         formula.
	 */
	public Formula getCastedModel() {
		if (getModel() instanceof Formula) {
			return (Formula) getModel();
		}

		return null;
	}

	/**
	 * Refresh pallets.
	 */
	public void refreshPallets() {
		palletRoot.refreshGraphToolsGroup();
	}

}
