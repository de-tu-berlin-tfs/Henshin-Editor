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
package de.tub.tfs.henshin.editor.ui.transformation_unit;

import java.util.HashMap;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.editor.actions.transformation_unit.AddTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateConditionalUnitWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateIndependentUnitWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateParameterAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreatePriorityUnitWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.CreateSequentialUnitWithContentAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveDownTransformationUnitAction;
import de.tub.tfs.henshin.editor.actions.transformation_unit.MoveUpTransformationUnitAction;
import de.tub.tfs.henshin.editor.commands.transformation_unit.SetActivatedCommand;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.TransformationUnitEditPart;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;
import de.tub.tfs.muvitor.ui.MuvitorPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class TransUnitPage.
 */
public class TransUnitPage extends MuvitorPage {

	/** The unit2 edit part. */
	private HashMap<Unit, TransformationUnitEditPart<?>> unit2EditPart;

	/** The trans unit palette root. */
	private TransUnitPaletteRoot transUnitPaletteRoot;

	/** The parent. */
	private EObject parent;

	/** The sub element. */
	private EObject subElement;

	/** The element2parent. */
	private HashMap<Unit, Unit> element2parent;

	/**
	 * Instantiates a new trans unit page.
	 * 
	 * @param view
	 *            the view
	 */
	public TransUnitPage(MuvitorPageBookView view) {
		super(view);
		this.unit2EditPart = new HashMap<Unit, TransformationUnitEditPart<?>>();
		parent = getModel();
		subElement = null;
		element2parent = new HashMap<Unit, Unit>();
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
		return new TransUnitContexMenuProvider(viewer, getActionRegistry());
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
		registerAction(new CreateSequentialUnitWithContentAction(getEditor()));
		registerAction(new CreateIndependentUnitWithContentAction(getEditor()));
		registerAction(new CreatePriorityUnitWithContentAction(getEditor()));
		registerAction(new CreateConditionalUnitWithContentAction(getEditor()));

		registerAction(new AddTransformationUnitAction(getEditor()));
		registerAction(new MoveUpTransformationUnitAction(getEditor()));
		registerAction(new MoveDownTransformationUnitAction(getEditor()));
		registerAction(new CreateParameterAction(getEditor()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createEditPartFactory()
	 */
	@Override
	protected EditPartFactory createEditPartFactory() {
		return new TransUnitEditPartFactory(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#createPaletteRoot()
	 */
	@Override
	protected MuvitorPaletteRoot createPaletteRoot() {
		transUnitPaletteRoot = new TransUnitPaletteRoot();
		return transUnitPaletteRoot;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.ui.MuvitorPage#getViewerContents()
	 */
	@Override
	protected EObject[] getViewerContents() {
		return new EObject[] { parent, subElement };
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

	/**
	 * Gets the casted model.
	 * 
	 * @return the casted model
	 */
	public Unit getCastedModel() {
		return (Unit) getModel();
	}

	/**
	 * Next trans unit.
	 * 
	 * @param parent
	 *            the parent
	 * @param transUnit
	 *            the trans unit
	 */
	public void nextTransUnit(Unit parent,
			Unit transUnit) {
		if (parent != null && transUnit != null) {
			element2parent.put(transUnit, parent);
		}
		this.parent = parent;
		subElement = transUnit;
		setViewersContents(0, parent);
		setViewerVisibility(0, parent != null);
		setViewersContents(1, subElement);
		setViewerVisibility(1, subElement != null);
		if (subElement != null) {
			unit2EditPart.get(parent).showParameterMapping(
					unit2EditPart.get(subElement));
		} else {
			unit2EditPart.get(parent).hideParameterMapping();
		}
	}

	/**
	 * Reset view.
	 */
	public void resetView() {
		nextTransUnit(getCastedModel(), null);
	}

	/**
	 * Back view.
	 * 
	 * @param transUnit
	 *            the trans unit
	 */
	public void backView(Unit transUnit) {
		if (element2parent.containsKey(transUnit)) {
			nextTransUnit(element2parent.get(transUnit), transUnit);
		} else {
			nextTransUnit(transUnit, null);
		}
	}

	/**
	 * Gets the unit2 edit part.
	 * 
	 * @return the unit2 edit part
	 */
	public synchronized HashMap<Unit, TransformationUnitEditPart<?>> getUnit2EditPart() {
		return unit2EditPart;
	}

	/**
	 * Sets the activated.
	 * 
	 * @param tUnit
	 *            the t unit
	 * @param activated
	 *            the activated
	 */
	public void setActivated(Unit tUnit, boolean activated) {
		Command command = new SetActivatedCommand(tUnit, activated);
		if (command.canExecute()) {
			CommandStack commandStack = (CommandStack) getEditor().getAdapter(
					CommandStack.class);
			commandStack.execute(command);
		}
	}

}
