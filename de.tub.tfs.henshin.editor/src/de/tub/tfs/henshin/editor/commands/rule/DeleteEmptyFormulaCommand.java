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
package de.tub.tfs.henshin.editor.commands.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.UnaryFormula;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteEmptyFormulaCommand.
 */
public class DeleteEmptyFormulaCommand extends CompoundCommand {

	/** The to delete. */
	private Formula toDelete;

	/** The parent. */
	private EObject parent;

	/**
	 * Instantiates a new delete empty formula command.
	 * 
	 * @param toDelete
	 *            the to delete
	 * @param parent
	 *            the parent
	 */
	public DeleteEmptyFormulaCommand(Formula toDelete, EObject parent) {
		this.toDelete = toDelete;
		this.parent = parent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return toDelete != null && parent != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (parent instanceof Graph) {
			((Graph) parent).setFormula(null);
		} else if (parent instanceof Formula) {
			if (parent instanceof UnaryFormula) {
				((UnaryFormula) parent).setChild(null);
			} else if (parent instanceof BinaryFormula) {
				BinaryFormula binaryFormula = (BinaryFormula) parent;
				if (toDelete.equals(binaryFormula.getLeft())) {
					binaryFormula.setLeft(null);
				} else if (toDelete.equals(binaryFormula.getRight())) {
					binaryFormula.setRight(null);
				}
			}
		}

		add(new SimpleDeleteEObjectCommand(toDelete));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#undo()
	 */
	@Override
	public void undo() {
		if (parent instanceof Graph) {
			((Graph) parent).setFormula(toDelete);
		} else if (parent instanceof Formula) {
			if (parent instanceof UnaryFormula) {
				((UnaryFormula) parent).setChild(toDelete);
			} else if (parent instanceof BinaryFormula) {
				BinaryFormula binaryFormula = (BinaryFormula) parent;
				if (binaryFormula.getLeft() == null) {
					binaryFormula.setLeft(toDelete);
				} else if (binaryFormula.getRight() == null) {
					binaryFormula.setRight(toDelete);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.CompoundCommand#redo()
	 */
	@Override
	public void redo() {
		execute();
	}
}
