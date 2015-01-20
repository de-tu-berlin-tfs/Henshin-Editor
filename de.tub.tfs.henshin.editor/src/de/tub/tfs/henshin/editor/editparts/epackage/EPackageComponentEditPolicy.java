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
package de.tub.tfs.henshin.editor.editparts.epackage;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import de.tub.tfs.henshin.editor.commands.transSys.DeleteEPackageCommand;
import de.tub.tfs.henshin.editor.util.HenshinUtil;

/**
 * The Class EPackageComponentEditPolicy.
 * 
 * @author Angeline
 */
public class EPackageComponentEditPolicy extends ComponentEditPolicy {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editpolicies.ComponentEditPolicy#createDeleteCommand(
	 * org.eclipse.gef.requests.GroupRequest)
	 */
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		EPackageTreeEditpart host = (EPackageTreeEditpart) getHost();
		EPackage ePackage = host.getCastedModel();
		Module rootModel = (Module) ((EditPart) host)
				.getParent().getParent().getModel();

		if (HenshinUtil.INSTANCE.getEPackageReferences(ePackage, rootModel)
				.isEmpty()) {

			return new DeleteEPackageCommand(ePackage, rootModel,
					HenshinPackage.MODULE__IMPORTS);
		}

		return null;
	}
}
