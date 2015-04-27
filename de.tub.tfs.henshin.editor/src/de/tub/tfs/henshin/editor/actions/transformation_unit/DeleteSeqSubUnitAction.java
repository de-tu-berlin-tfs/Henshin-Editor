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
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.DeleteAction;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.editor.commands.transformation_unit.RemoveTransformationUnitCommand;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.SequentialUnitEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.tree.SequentialUnitTreeEditPart;

/**
 * @author nam
 * 
 */
public class DeleteSeqSubUnitAction extends DeleteAction {

	/**
	 * An unique id for this {@link Action action}.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.transformation_unit.DeleteSeqSubUnitAction";

	private SequentialUnit parent;

	private List<Integer> models;

	/**
	 * @param part
	 */
	public DeleteSeqSubUnitAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText("Delete Sub Unit(s)");
		setToolTipText("Delete Selected Transformation Unit(s).");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_DELETE));

		models = new ArrayList<Integer>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.DeleteAction#run()
	 */
	@Override
	public void run() {
		CompoundCommand cmd = new CompoundCommand("Delete Sub Units");

		for (Integer idx : models) {
			int i = idx.intValue();

			Unit unit = parent.getSubUnits().get(i);
			Unit u = unit;

			while (i < parent.getSubUnits().size()) {
				u = parent.getSubUnits().get(i);

				if (u == unit) {
					cmd.add(new RemoveTransformationUnitCommand(parent, u, idx));
				} else {
					break;
				}

				i++;
			}
		}

		execute(cmd);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.DeleteAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		EditPart seqPart = null;
		
		models.clear();
		
		parent = null;

		if (selection.size() > 0) {
			if (selection.get(0) instanceof EditPart) {
				EditPart part = (EditPart) selection.get(0);

				if (part.getParent() != null) {
					seqPart = part.getParent();
				}
			}

		}

		if (seqPart instanceof SequentialUnitEditPart
				|| seqPart instanceof SequentialUnitTreeEditPart) {

			for (Object o : selection) {
				if (o instanceof EditPart) {
					EditPart part = (EditPart) o;

					if (part.getParent() == seqPart && part.getModel() instanceof Unit) {
						List<Integer> counters;

						if (seqPart instanceof SequentialUnitEditPart) {
							counters = ((SequentialUnitEditPart) seqPart)
									.getCounters();
						} else {
							counters = ((SequentialUnitTreeEditPart) seqPart)
									.getCounters();
						}

						models.add(counters.get(seqPart.getChildren().indexOf(
								part)));
					} else {
						return false;
					}
				}
			}

			parent = (SequentialUnit) seqPart.getModel();
		}

		return parent != null && !models.isEmpty();
	}
}
