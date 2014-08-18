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
package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.util.SendNotify;


public class CreateNodeMappingCommand extends Command {
	
	private Node original; //node in LHS
	
//	private Node rhsNode; // node in RHS
	
	private Node image; // node in NAC
	
	private Mapping mapping;

	private List<Mapping> currentMappings;
	
	private List<Mapping> oldMappings;
	
	private int mappingNumber;

	@SuppressWarnings("unused")
	private boolean withMappingNumber;
	
//	private NodeLayout originLayout;
//	private NodeLayout imageLayout;

	TNodeObjectEditPart startEditPart, endEditPart;
	
	boolean created;
	
	
	/** Instantiates a new node mapping command. 
	 * 
	 * @param original
	 * @param mapping
	 */
	public CreateNodeMappingCommand(Node original, Mapping mapping){
		this.mapping = mapping;
		this.original = original;
		this.withMappingNumber = true;	
		mappingNumber = -1;
	}

	/** Instantiates a new node mapping command.
	 * 
	 * @param original
	 * @param image
	 * @param currentMappings
	 */	
	public CreateNodeMappingCommand(Node original, Node image, List<Mapping> currentMappings){
		this(original, HenshinFactory.eINSTANCE.createMapping());
		this.currentMappings = currentMappings;
		this.image = image;
		mappingNumber = -1;
	}

	/** Instantiates a new node mapping command.
	 * 
	 * @param original
	 * @param image
	 * @param currentMappings
	 * @param withMappingNumber
	 */
	public CreateNodeMappingCommand(Node original, Node image, List<Mapping> currentMappings, boolean withMappingNumber){
		this(original, HenshinFactory.eINSTANCE.createMapping());
		this.currentMappings = currentMappings;
		this.image = image;
		this.withMappingNumber = withMappingNumber;
		mappingNumber = -1;
	}
	
	@Override
	public boolean canExecute() {
		return this.currentMappings != null
				&& this.mapping != null
				&& this.original != null
				&& this.image != null;
	}
	
	@Override
	public boolean canUndo() {
		return this != null;
	}

	@Override
	public void execute() {
		Mapping nacM  = this.getExistingNacMapping();
		if (nacM == null) {
			if (oldMappings == null)
				oldMappings = new ArrayList<Mapping>();
			else
				oldMappings.clear();
			Iterator<Mapping> iter = currentMappings.iterator();
			while (iter.hasNext()) {
				final Mapping m = iter.next();
				if (m.getImage() == image || m.getOrigin() == original ) {
					oldMappings.add(m);
					iter.remove();
					SendNotify.sendRemoveMappingNotify(m);
				}
			}
		
			mapping.setOrigin(original);
			mapping.setImage(image);
			currentMappings.add(mapping);
			
			mappingNumber = original.getGraph().getNodes().indexOf(original);
			if (startEditPart != null) {
				startEditPart.setNumberForMapping(mappingNumber);
				((RuleNodeEditPart)startEditPart).addMapping(mapping);
				// TODO: add notify
                SendNotify.sendUpdateMappingNotify(startEditPart.getCastedModel()); 			}
			if (endEditPart != null) {
				endEditPart.setNumberForMapping(mappingNumber);
				endEditPart.setNacMapping(mapping);
				SendNotify.sendUpdateMappingNotify(mapping.getImage());
			}
			this.created = true;
		}
		else {
			// delete NAC mapping
			if (oldMappings == null)
				oldMappings = new ArrayList<Mapping>();
			else
				oldMappings.clear();
			oldMappings.add(nacM);
			this.mapping = nacM;
			currentMappings.remove(this.mapping);
			
			if (endEditPart != null) {
				endEditPart.setNumberForMapping(-1);
				endEditPart.setNacMapping(null);
				SendNotify.sendUpdateMappingNotify(mapping.getImage());
			}
			if (startEditPart != null) {
				if (getExistingNacMapping() == null) {
					this.startEditPart.setNumberForMapping(-1);
				}
				else {
					mappingNumber = original.getGraph().getNodes().indexOf(original);
					this.startEditPart.setNumberForMapping(mappingNumber);
				}
				// TODO: add notify
                SendNotify.sendUpdateMappingNotify(startEditPart.getCastedModel()); 
			}

			this.created = false;
		}
	}

	private Mapping getExistingNacMapping() {
		TreeIterator<EObject> iter = this.original.getGraph().getFormula().eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			if (o instanceof NestedCondition) {
				NestedCondition nc = (NestedCondition)o;
				for (Mapping m : nc.getMappings()) {
					if (m.getOrigin() == this.original && m.getImage() == this.image) {
						return m;
					}
				}
			}
		}
		return null;
	}
	

	@Override
	public void undo() {
		execute();
	}
	
	public void setImage(Node n){
		this.image = n;
	}
	
	public void setMappingNumber(int num){
		this.mappingNumber = num;
	}
	
	public int getMappingNumber(){
		return this.mappingNumber;
	}
	
	public Node getOrigial(){
		return this.original;
	}
		
	public void setActualMappings(List<Mapping> list){
		this.currentMappings = list;
	}
		
	public void setStartMappingEditPart(TNodeObjectEditPart ep) {
		this.startEditPart = ep;
	}
	
	public TNodeObjectEditPart getStartMappingEditPart() {
		return this.startEditPart;
	}
	
	public void setEndMappingEditPart(TNodeObjectEditPart ep) {
		this.endEditPart = ep;
	}
	
	public TNodeObjectEditPart getEndMappingEditPart() {
		return this.endEditPart;
	}
}
