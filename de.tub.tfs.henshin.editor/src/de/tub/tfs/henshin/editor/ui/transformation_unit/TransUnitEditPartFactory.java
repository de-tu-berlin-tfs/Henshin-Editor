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
package de.tub.tfs.henshin.editor.ui.transformation_unit;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.ConditionalUnitAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.ConditionalUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.ConditionalUnitPartAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.IndependentUnitAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.IndependentUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.LoopUnitAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.LoopUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.PriorityUnitAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.PriorityUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.RuleAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.RuleUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.SequentialUnitAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.SequentialUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.SequentialUnitSubEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.SubUnitLayoutEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.TransformationUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.TransformationUnitPartAsSubUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.parameter.ParameterEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.parameter.ParameterMappingEditPart;
import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.model.layout.SubUnitLayout;

/**
 * A factory for creating TransUnitEditPart objects.
 */
public class TransUnitEditPartFactory implements EditPartFactory {

	/** The trans unit page. */
	private final TransUnitPage transUnitPage;

	/**
	 * Instantiates a new trans unit edit part factory.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 */
	public TransUnitEditPartFactory(TransUnitPage transUnitPage) {
		this.transUnitPage = transUnitPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart,
	 * java.lang.Object)
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof SubUnitLayout) {
			if (context instanceof SequentialUnitEditPart) {
				return new SequentialUnitSubEditPart(transUnitPage,
						(Unit) context.getModel(),
						(SubUnitLayout) model);
			}

			return new SubUnitLayoutEditPart((SubUnitLayout) model);
		}

		if (model instanceof Rule) {
			if (context instanceof TransformationUnitEditPart<?>
					|| context instanceof TransformationUnitPartAsSubUnitEditPart<?>) {
				return new RuleAsSubUnitEditPart(transUnitPage,
						(Unit) context.getModel(), (Rule) model);
			}
			return new RuleUnitEditPart(transUnitPage, (Rule) model);
		}

		if (model instanceof LoopUnit) {
			if (context instanceof TransformationUnitEditPart<?>
					|| context instanceof TransformationUnitPartAsSubUnitEditPart<?>) {
				return new LoopUnitAsSubUnitEditPart(transUnitPage,
						(Unit) context.getModel(),
						(LoopUnit) model);
			}

			return new LoopUnitEditPart(transUnitPage, (LoopUnit) model);
		}

		if (model instanceof SequentialUnit) {
			if (context instanceof TransformationUnitEditPart<?>
					|| context instanceof ConditionalUnitPartAsSubUnitEditPart) {
				return new SequentialUnitAsSubUnitEditPart(transUnitPage,
						(Unit) context.getModel(),
						(SequentialUnit) model);
			}
			return new SequentialUnitEditPart(transUnitPage,
					(SequentialUnit) model);
		}
		if (model instanceof IndependentUnit) {
			if (context instanceof TransformationUnitEditPart<?>
					|| context instanceof ConditionalUnitPartAsSubUnitEditPart) {
				return new IndependentUnitAsSubUnitEditPart(transUnitPage,
						(Unit) context.getModel(),
						(IndependentUnit) model);
			}
			return new IndependentUnitEditPart(transUnitPage,
					(IndependentUnit) model);
		}
		if (model instanceof PriorityUnit) {
			if (context instanceof TransformationUnitEditPart<?>
					|| context instanceof ConditionalUnitPartAsSubUnitEditPart) {
				return new PriorityUnitAsSubUnitEditPart(transUnitPage,
						(Unit) context.getModel(),
						(PriorityUnit) model);
			}
			return new PriorityUnitEditPart(transUnitPage, (PriorityUnit) model);
		}
		if (model instanceof ConditionalUnit) {
			if (context instanceof TransformationUnitEditPart<?>
					|| context instanceof ConditionalUnitPartAsSubUnitEditPart) {
				return new ConditionalUnitAsSubUnitEditPart(transUnitPage,
						(Unit) context.getModel(),
						(ConditionalUnit) model);
			}
			return new ConditionalUnitEditPart(transUnitPage,
					(ConditionalUnit) model);
		}

		if (model instanceof Parameter) {
			ParameterEditPart editPart = new ParameterEditPart(
					(Parameter) model);
			if (context instanceof TransformationUnitEditPart<?>
					|| context instanceof ConditionalUnitPartAsSubUnitEditPart) {
				((TransformationUnitEditPart<?>) context)
						.getParameter2EditPart().put((Parameter) model,
								editPart);
			}
			return editPart;
		}
		if (model instanceof ParameterMapping) {
			return new ParameterMappingEditPart((ParameterMapping) model);
		}
		if (model instanceof ConditionalUnitPart) {
			return new ConditionalUnitPartAsSubUnitEditPart(
					(ConditionalUnitPart) model);
		}

		Assert.isTrue(model == null,
				"GraphEditPartFactory could not create an EditPart for the model element "
						+ model);
		return null;
	}

}
