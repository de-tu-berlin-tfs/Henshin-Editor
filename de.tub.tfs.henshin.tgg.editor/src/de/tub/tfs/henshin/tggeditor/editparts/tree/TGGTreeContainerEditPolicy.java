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
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.TreeContainerEditPolicy;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.CreateRequest;

import de.tub.tfs.henshin.tggeditor.commands.move.MoveEObjectCommand;


/**
 * @author Johann
 *
 */
@SuppressWarnings("deprecation")
public class TGGTreeContainerEditPolicy extends TreeContainerEditPolicy {

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.TreeContainerEditPolicy#getAddCommand(org.eclipse.gef.requests.ChangeBoundsRequest)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Command getAddCommand(ChangeBoundsRequest request) {
		EObject host=(EObject) getHost().getModel();
		List<?> editparts = request.getEditParts();
		if (editparts.size() == 1) {
			EObject child = (EObject) ((EditPart)editparts.get(0)).getModel();
			
			if ((host instanceof Rule || host instanceof IndependentUnit) && (child instanceof Rule || child instanceof IndependentUnit)){
				EList<EObject> list = (EList<EObject>) host.eContainer().eGet(host.eContainingFeature());
				Module m = (Module) EcoreUtil.getRootContainer(host);
				if (host instanceof IndependentUnit){
					list = (EList)((IndependentUnit) host).getSubUnits();
				}else
				for (Unit u : m.getUnits()) {
					if (u instanceof IndependentUnit){
						if (((IndependentUnit) u).getSubUnits().contains(host)){
							list = (EList) ((IndependentUnit) u).getSubUnits();
							break;
						}
					}
						
				}
				
				EList<EObject> oldList = (EList<EObject>) child.eContainer().eGet(child.eContainingFeature());
				for (Unit u : m.getUnits()) {
					if (u instanceof IndependentUnit){
						if (((IndependentUnit) u).getSubUnits().contains(child)){
							oldList = (EList) ((IndependentUnit) u).getSubUnits();
							break;
						}
					}
						
				}
				if (child instanceof IndependentUnit && (((IndependentUnit) child).getName().equals("FTRuleFolder") || ((IndependentUnit) child).getName().equals("RuleFolder")//))
					//NEW
						|| ((IndependentUnit) child).getName().equals("ITRuleFolder")|| ((IndependentUnit) child).getName().equals("CCRuleFolder")|| ((IndependentUnit) child).getName().equals("BTRuleFolder")))
					return null;
				return new MoveEObjectCommand(list,oldList, child, list.indexOf(host));
			}
			if (host.eContainer()==child.eContainer() && host.eContainingFeature()==child.eContainingFeature()){				
				EList<EObject> list = (EList<EObject>) host.eContainer().eGet(host.eContainingFeature());
				EList<EObject> oldList = (EList<EObject>) child.eContainer().eGet(child.eContainingFeature());
				return new MoveEObjectCommand(list,oldList, child, list.indexOf(host));
			}
		}
		
		
		return null;
	}
	
	
	/**
	 * Returns a Command for moving the children within the container.
	 * 
	 * @param request
	 *            the Request to move
	 * @return Command <code>null</code> or a Command to perform the move
	 */
	@Override
	protected Command getMoveChildrenCommand(ChangeBoundsRequest request) {
		EObject host=(EObject) getHost().getModel();
		List<?> editparts = request.getEditParts();
		if (editparts.size() == 1) {
			EObject child = (EObject) ((EditPart)editparts.get(0)).getModel();
			if (host.eContainer()==child.eContainer() && host.eContainingFeature()==child.eContainingFeature()){
				EList<EObject> list = (EList<EObject>) host.eContainer().eGet(host.eContainingFeature());
				EList<EObject> oldList = (EList<EObject>) child.eContainer().eGet(child.eContainingFeature());
				return new MoveEObjectCommand(list,oldList, child, list.indexOf(host));
			}
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.gef.editpolicies.TreeContainerEditPolicy#getCreateCommand(org.eclipse.gef.requests.CreateRequest)
	 */
	@Override
	protected Command getCreateCommand(CreateRequest request) {
		return null;
	}



}
