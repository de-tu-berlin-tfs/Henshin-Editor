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
package de.tub.tfs.henshin.editor.editparts.graph.graphical;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.FreeformLayer;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.interpreter.RuleApplication;
import org.eclipse.emf.henshin.model.Graph;

import de.tub.tfs.henshin.editor.figure.graph.TraceFreeformLayer;
import de.tub.tfs.henshin.editor.internal.UnitApplicationEObject;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class UnitApplicationEditPart.
 */
public class UnitApplicationEditPart extends
		AdapterGraphicalEditPart<UnitApplicationEObject> {

	/** The old index. */
	private int oldIndex;

	/** The graph. */
	private final Graph graph;

	/** The rule application2 edit part. */
	private Map<RuleApplication, RuleApplicationEditPart> ruleApplication2EditPart;

	/**
	 * Instantiates a new unit application edit part.
	 * 
	 * @param graph
	 * 
	 * @param model
	 *            the model
	 */
	public UnitApplicationEditPart(Graph graph, UnitApplicationEObject model) {
		super(model);
		this.graph = graph;
//		oldIndex = model.getUnitApplication().getAppliedRules().size() - 1;
		ruleApplication2EditPart = new HashMap<RuleApplication, RuleApplicationEditPart>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		FreeformLayer layer = new TraceFreeformLayer(getCastedModel()
				.getUnitApplication().getUnit().getName());
		return layer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
	}

	/**
	 * Update figure.
	 */
	protected void updateFigure() {
		final FreeformLayer layer = (FreeformLayer) getFigure();
		getFigure().setBackgroundColor(ColorConstants.white);
		if (getSelected() == 0) { // not selected
			layer.setBorder(new LineBorder(1));
			layer.setForegroundColor(SWTResourceManager.getColor(0, 0, 0));
		} else { // selected
			layer.setBorder(new LineBorder(2));
			layer.setForegroundColor(SWTResourceManager.getColor(150, 0, 0));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		updateFigure();
		getFigure().repaint();
		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		updateFigure();
		super.activate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#fireSelectionChanged
	 * ()
	 */
	@Override
	protected void fireSelectionChanged() {
		super.fireSelectionChanged();
		refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
//		for (RuleApplication appl : getCastedModel().getUnitApplication()
//				.getAppliedRules()) {
//			list.add(new RuleApplicationEObject(appl));
//		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		setCurrentRuleApplication(-1);
	}

	/**
	 * Sets the current rule application.
	 * 
	 * @param index
	 *            the new current rule application
	 */
	public void setCurrentRuleApplication(int index) {
//		if (oldIndex < index) {
//			for (int i = oldIndex; i < index; i++) {
//				RuleApplicationEditPart editPart = getEditPart(getCastedModel()
//						.getUnitApplication().getAppliedRules().get(i + 1));
//				editPart.getCastedModel().getRuleApplication().redo();
//				SendNotify.sendTransformationRedoNotify(graph);
//				// getCastedModel().refreshEdges();
//				editPart.getCastedModel().setExecuted(true);
//				editPart.refreshVisuals();
//			}
//		}
//		if (oldIndex > index) {
//			for (int i = oldIndex; i > index; i--) {
//				RuleApplicationEditPart editPart = getEditPart(getCastedModel()
//						.getUnitApplication().getAppliedRules().get(i));
//				editPart.getCastedModel().getRuleApplication().undo();
//				SendNotify.sendTransformationUndoNotify(graph);
//				// getCastedModel().refreshEdges();
//				editPart.getCastedModel().setExecuted(false);
//				editPart.refreshVisuals();
//			}
//		}
//		oldIndex = index;
	}

	/**
	 * Gets the edits the part.
	 * 
	 * @param ruleApplication
	 *            the rule application
	 * @return the edits the part
	 */
	private RuleApplicationEditPart getEditPart(RuleApplication ruleApplication) {
		if (!ruleApplication2EditPart.containsKey(ruleApplication)) {
			for (Object o : getChildren()) {
				if (o instanceof RuleApplicationEditPart) {
					RuleApplicationEditPart editPart = (RuleApplicationEditPart) o;
					ruleApplication2EditPart.put(editPart.getCastedModel()
							.getRuleApplication(), editPart);
				}
			}
		}
		return ruleApplication2EditPart.get(ruleApplication);
	}
}
