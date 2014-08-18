/*******************************************************************************
 * Copyright (c) 2012, 2013 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editpolicies.rule;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteNACCommand;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.GraphComponentEditPolicy;


public class NACComponentEditPolicy extends GraphComponentEditPolicy {

	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		return new DeleteNACCommand((Graph) getHost().getModel());
	}

}
