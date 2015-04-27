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

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.DeleteEdgeCommand;

/**
 * The class MarkEdgeCommand can mark an edge in a rule as new or not new. It makes
 * all the needed changes in the model of the rule and in the tgg layouts.
 * When executed it either deletes the lhs edge or it creates
 * a new lhs edge. Also the nodes are handled.
 */
public class MarkUnspecifiedEdgeCommand extends CompoundCommand {

	/**
	 * the rhs edge
	 */
	private Edge rhsEdge;
//	/**
//	 * the edge layout of lhs and rhs edge
//	 */
//	private EdgeLayout edgeLayout;

	/**
	 * the constructor 
	 * @param rhsEdge the rhs edge
	 */
	public MarkUnspecifiedEdgeCommand(Edge rhsEdge) {
		this.rhsEdge = rhsEdge;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#execute()
	 */
	@Override
	public void execute() {
		if (((TEdge) rhsEdge).getMarkerType() != null) {
			// edge is currently marked as new and shall be demarked
			demark();
		} 
		else {
	// edge is currently not marked, thus mark it 

			mark();
		}
	}

	/**
	 * 
	 */
	private void mark() {
		((TEdge) rhsEdge).setMarkerType(RuleUtil.TR_UNSPECIFIED);
	}


	/**
	 * 
	 */
	private void demark() {
				((TEdge) rhsEdge).setMarkerType(null);
			
	}


	
	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.CompoundCommand#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return rhsEdge!=null;
	}

	@Override
	public void redo() {
		execute();
	}

	@Override
	public void undo() {
		execute();
	}

}
