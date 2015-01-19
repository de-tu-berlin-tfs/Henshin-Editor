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
package de.tub.tfs.henshin.editor.commands.transformation_unit;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.util.SendNotify;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;

/**
 * The Class RemoveTransformationUnitCommand.
 * 
 * @author Johann
 */
public class RemoveTransformationUnitCommand extends Command {

	/** The parent. */
	final private Unit parent;

	/** The delete unit. */
	final private Unit deleteUnit;

	/** The feature. */
	private final EStructuralFeature feature;

	/** The index. */
	private int index;

	/** The parameter mappings. */
	private List<ParameterMapping> parameterMappings;

	public RemoveTransformationUnitCommand(final Unit parent,
			final Unit unit, int idx) {
		super();

		this.deleteUnit = unit;
		this.feature = TransformationUnitUtil.getSubUnitsFeature(parent);
		this.parameterMappings = new Vector<ParameterMapping>();

		if (parent instanceof ConditionalUnitPart) {
			this.parent = ((ConditionalUnitPart) parent).getModel();
		} else {
			this.parent = parent;
		}

		index = idx;
	}

	/**
	 * Instantiates a new removes the transformation unit command.
	 * 
	 * @param parent
	 *            the parent
	 * @param deletedUnit
	 *            the deleted unit
	 */
	public RemoveTransformationUnitCommand(Unit parent,
			Unit deletedUnit) {

		this(parent, deletedUnit, -1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return feature != null && deleteUnit != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		Iterator<ParameterMapping> iter = parent.getParameterMappings()
				.iterator();
		while (iter.hasNext()) {
			ParameterMapping parameterMapping = iter.next();
			if (parameterMapping.getSource().getUnit() == deleteUnit
					|| parameterMapping.getTarget().getUnit() == deleteUnit) {
				parameterMappings.add(parameterMapping);
				iter.remove();
			}
		}

		if (feature.isMany()) {
			EList<Unit> list = (EList<Unit>) parent
					.eGet(feature);
			if (index < 0) {
				index = list.indexOf(deleteUnit);
			}

			list.remove(index);
		} else {
			parent.eSet(feature, null);
		}
		SendNotify.sendSetTransformationUnitNotify(deleteUnit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		if (feature.isMany()) {
			EList<Unit> list = (EList<Unit>) parent
					.eGet(feature);
			list.add(index, deleteUnit);
		} else {
			parent.eSet(feature, deleteUnit);
		}
		parent.getParameterMappings().addAll(parameterMappings);
		SendNotify.sendSetTransformationUnitNotify(deleteUnit);

	}

}
