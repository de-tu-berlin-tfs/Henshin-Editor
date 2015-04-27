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
package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.CreateRuleNodeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.GraphXYLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;


public class RuleXYLayoutEditPolicy extends GraphXYLayoutEditPolicy {
	
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		Object newObject = request.getNewObject();
		if (newObject instanceof TNode){
			TripleGraph graph = (TripleGraph) getHost().getModel();
			Rectangle constraint = (Rectangle) getConstraintFor(request);
			Point location = new Point(constraint.x,constraint.y);
			TripleComponent nodeTripleComponent = GraphUtil.getTripleComponentForXCoordinate(((GraphEditPart)this.getHost()),location.x);
			
			CreateRuleNodeCommand c = new CreateRuleNodeCommand((TNode)newObject,graph,
					location, nodeTripleComponent);
			return c;
		}
		return null;
	}
	
}
