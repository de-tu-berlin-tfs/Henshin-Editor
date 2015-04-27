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
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;

/**
 * The class MarkAttributeCommand can mark an attribute in a rule as new or not new. It makes
 * all the needed changes in the model of the rule and in the tgg layouts.
 * When executed it either deletes the lhs attribute or it creates
 * a new lhs attribute. Also the containing node is handled.
 */
public class MarkUnspecifiedAttributeCommand extends CompoundCommand {

	/**
	 * the rhs attribute
	 */
	private Attribute rhsAttribute;

	/**
	 * the constructor 
	 * @param rhsAttribute the rhs attribute
	 */
	public MarkUnspecifiedAttributeCommand(Attribute rhsAttribute) {
		this.rhsAttribute = rhsAttribute;
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (RuleUtil.TR_UNSPECIFIED.equals(((TAttribute) rhsAttribute)
				.getMarkerType())) {
			// attribute is currently marked and shall be demarked
			// remove marker
			((TAttribute) rhsAttribute).setMarkerType(null);

		} else {
			// attribute is currently not marked, thus mark it
			((TAttribute) rhsAttribute).setMarkerType(RuleUtil.TR_UNSPECIFIED);
		}
	}

	
	
	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		execute();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return true;
	}

}
