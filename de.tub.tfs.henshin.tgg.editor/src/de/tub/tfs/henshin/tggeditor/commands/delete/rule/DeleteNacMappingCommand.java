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
package de.tub.tfs.henshin.tggeditor.commands.delete.rule;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;


/**
 * The DeleteNacMappingCommand will be executed, if a DeleteNacMappingAction or 
 * a DeleteRuleNodeCommand is executed
 * it notifies the containing nodes to delete the mapping by themselves
 *  
 * @see de.tub.tfs.henshin.tggeditor.actions.DeleteNacMappingsAction
 * @see de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteRuleNodeCommand
 */
public class DeleteNacMappingCommand extends CompoundCommand {

	/**
	 * the mapping between a nac node and a rule node
	 */
	private Mapping nacMapping;
	/**
	 * the rule node of the rhs graph
	 */
	private Node node;
	/**
	 * the rule
	 */
	private Rule rule;

	/**
	 * the constructor 
	 * @param nacMapping the mapping betweens a nac node and a rule node
	 */
	public DeleteNacMappingCommand(Mapping nacMapping) {
		this.nacMapping = nacMapping;
		rule = (Rule)nacMapping.getOrigin().getGraph().eContainer();
		if (rule != null) {
			for (Mapping m : rule.getMappings()) {
				if (m.getOrigin() == nacMapping.getOrigin()) {
					this.node = m.getImage();
				}
			}
		}
		
		add(new SimpleDeleteEObjectCommand(nacMapping));
	}
	
	/**
	 * the method don't delete the mapping itself, but notifies the nodes 
	 * to delete their references to the mapping
	 * 
	 * @see de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart#notifyChanged()
	 */
	@Override
	public void execute() {
	
		/*
		 * notify the origin (rule node) and the image (nac node) of the mapping
		 * to delete the mapping in their editparts
		 */
		nacMapping.getImage().eNotify(new ENotificationImpl((InternalEObject) nacMapping.getImage(), Notification.REMOVE, HenshinPackage.MAPPING__IMAGE, nacMapping, null));
		node.eNotify(new ENotificationImpl((InternalEObject) node, Notification.REMOVE, HenshinPackage.MAPPING__ORIGIN, nacMapping, null));
		
		super.execute();
	}
	
	@Override
	public boolean canExecute() {
		return this.rule!=null && this.nacMapping!=null;
	}
	

}
