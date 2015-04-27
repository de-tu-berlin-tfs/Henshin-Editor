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
package de.tub.tfs.henshin.tggeditor.editparts.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.interpreter.util.ExceptionUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeGraphicalEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleNodeXYLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;


/**
 * The class RuleNodeEditPart.
 */
public class RuleNodeEditPart extends TNodeObjectEditPart {

	/** The NACs mappings. (NACs mappings only!!)*/
	protected List<Mapping> mappings;

	/** the lhs node belongs to model (which is the rhs node) */
	TNode lhsNode;

	/** the rhs node (which is the model) */
	TNode rhsNode;

	/**
	 * Instantiates a new rule node edit part.
	 *
	 * @param model the model
	 */
	public RuleNodeEditPart(TNode model) {

		super(model);

		rhsNode = model;
		//NodeUtil.refreshIsMarked(rhsNode);
		
		mappings = new ArrayList<Mapping>();
		
		setRuleMapping(model);	
		setNacMappings(model);
		
		cleanUpRule();

	}
	
	
	private void cleanUpRule() {
		
		// remove node duplicates in LHS
		ArrayList<Node> lhsNodeList = RuleUtil
				.getAllLHSNodes(rhsNode);

		// remove duplicates
		while (lhsNodeList.size() > 1) {
			Node lhsNode = lhsNodeList.get(0);
			lhsNodeList.remove(0);
			SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(
					lhsNode);
			cmd.execute();
		}			
		
			
		
		// remove lhs node, if rule creates the node
		if( rhsNode.getMarkerType()!=null
				&& rhsNode.getMarkerType().equals(RuleUtil.NEW)){
			if (lhsNodeList.size()==1) 
			{
				Node lhsNode = lhsNodeList.get(0);
				lhsNodeList.remove(0);
				if(lhsNode.getGraph()!=null){
					// don't delete the node instead remove the marker
					rhsNode.setMarkerType(null);
					lhsNodeList.clear();
				}
				else // parent reference of node is missing, thus remove it directly
					rhsNode.getGraph().getRule().getLhs().getNodes().remove(lhsNode);
			}
		}
		
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		super.createFigure();	
		return figure;
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new RuleNodeXYLayoutEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeComponentEditPolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new NodeGraphicalEditPolicy());
	}
	
	
//	@Override
//	protected void refreshVisuals() {
//		super.refreshVisuals();
//		updateMarker();
//	}
	
//	//@Override
//	protected void updateMarker() {
////		if (rhsNode==null) return;
////		NodeFigure figure = this.getNodeFigure();
////		if (rhsNode.getMarkerType() != null) {
////			if (rhsNode.getMarkerType().equals(RuleUtil.NEW)) {
////				// node marker type is "shall be created"
////				if (rhsNode.getMarkerType() != null)
////					figure.setMarked(true);
////			}
////			else if (rhsNode.getMarkerType().equals(RuleUtil.Translated)) {
////				// node marker type is "shall be translated"
////				if (rhsNode.getMarkerType() != null)
////					figure.setTranslated(true);
////			}
////		}  else {
////			figure.setTranslated(false);
////		}
//	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		
		if (notification.getNotifier() instanceof Node) {
			int featureId = notification.getFeatureID(HenshinPackage.class);
			int type = notification.getEventType();
			final Object newValue = notification.getNewValue();
			final Object oldValue = notification.getOldValue();	
			switch (type) {
				case Notification.SET:
				case Notification.UNSET:	
					refreshFigureName();
					refreshVisuals();
					break;
				case Notification.ADD:
				case Notification.ADD_MANY:
					if (type == Notification.ADD && newValue instanceof Mapping) {
						final Mapping m = (Mapping) newValue;
						// NAC mapping only
						if (this.mapping != null 
//								&& m.getOrigin() == this.mapping.getOrigin()
								&& m.getImage() != getModel()) {
							if (!mappings.contains(m)) {
								mappings.add(m);
								if (this.index == -1) 
									this.index = this.mapping.getOrigin().getGraph().getNodes().indexOf(this.mapping.getOrigin());
								refreshFigureName();
								refreshVisuals();
								break;
							}
						}
					}
				break;
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					if (type == Notification.REMOVE && oldValue instanceof Mapping) {
						final Mapping m = (Mapping) oldValue;	
						// NAC mapping only
						if (mappings.contains(m)
								&& m.getImage() != getModel()) {
							mappings.remove(m);
							if (mappings.size() == 0) {
								this.index = -1;
								refreshFigureName();
								refreshVisuals();
								break;
							}
						}
					}
				break;
			}
			
			switch (featureId) {
			case -1:
				refreshSourceConnections();
				refreshTargetConnections();
				refreshChildren();
				break;
			case HenshinPackage.NODE__NAME:
				if (lhsNode != null && (lhsNode.getName() == null || !lhsNode.getName().equals(getCastedModel().getName()))) {
					lhsNode.setName(getCastedModel().getName());
				}
				refreshFigureName();
				refreshVisuals();
				break;
			case HenshinPackage.NODE__TYPE:
			case TggPackage.TNODE__MARKER_TYPE: // is always triggered by case above
				refreshVisuals();
				break;
			case HenshinPackage.NODE__INCOMING:
				refreshTargetConnections();
				break;
			case HenshinPackage.NODE__OUTGOING:
				refreshSourceConnections();
				break;
			case HenshinPackage.NODE__ATTRIBUTES:
				refreshChildren();
				refreshVisuals();
				break;
			//case HenshinPackage.LAYOUT_ELEMENT__X:
			//case HenshinPackage.MARKED_ELEMENT__IS_MARKED:
				// duplicates to NODE__NAME
			case TggPackage.TNODE__X:
			case TggPackage.TNODE__Y:
				refreshVisuals();
				break;
			}
		}
		
		if (notification.getNotifier() instanceof Mapping
				&& this.mapping != null) {
			int type = notification.getEventType();
			final Object newValue = notification.getNewValue();
			final Object oldValue = notification.getOldValue();				
			switch (type) {
				case Notification.SET:
					if (newValue instanceof Node) {
						final Mapping m = (Mapping) notification.getNotifier();
						// NAC mapping only
						if (m.getOrigin() == this.mapping.getOrigin()
								&& m.getImage() != getModel()) {
							if (!mappings.contains(m)) {
								mappings.add(m);
							}
							if (this.index == -1) 
								this.index = this.mapping.getOrigin().getGraph().getNodes().indexOf(this.mapping.getOrigin());
							refreshFigureName();
							refreshVisuals();
							break;
						}
					}
				break;
				case Notification.ADD:
				case Notification.ADD_MANY:
					if (newValue instanceof Mapping) {
						final Mapping m = (Mapping) newValue;
						// NAC mapping only
						if (m.getOrigin() == this.mapping.getOrigin()
								&& m.getImage() != getModel()) {
							if (!mappings.contains(m)) {
								mappings.add(m);
								if (this.index == -1) 
									this.index = this.mapping.getOrigin().getGraph().getNodes().indexOf(this.mapping.getOrigin());
								refreshFigureName();
								refreshVisuals();
								break;
							}
						}
					}
				break;
				case Notification.UNSET:
					if (newValue instanceof Node) {
						final Mapping m = (Mapping) notification.getNotifier();
						// NAC mapping only
						if (mappings.contains(m)
								&& m.getOrigin() == this.mapping.getOrigin()
								&& m.getImage() != getModel()) {
							mappings.remove(m);
							if (mappings.size() == 0) {
								this.index = -1;
								refreshFigureName();
								refreshVisuals();
								break;
							}
						}
					}
				break;
				case Notification.REMOVE:
				case Notification.REMOVE_MANY:
					if (oldValue instanceof Mapping) {
						final Mapping m = (Mapping) oldValue;	
						// NAC mapping only
						if (mappings.contains(m)
								&& m.getOrigin() == this.mapping.getOrigin()
								&& m.getImage() != getModel()) {
							mappings.remove(m);
							if (mappings.size() == 0) {
								this.index = -1;
								refreshFigureName();
								refreshVisuals();
								break;
							}
						}
					}
				break;
			}
		}
	}

	/**
	 * iterates over all rule mappings and sets the right mapping and lhs node
	 * @param model the node
	 */
	private void setRuleMapping(TNode model) {
		if ( model.getGraph().getRule() == null)
			return;
		EList<Mapping> maps = model.getGraph().getRule().getMappings();
		for (Mapping m: maps) {
			if (m.getImage() == model && m.getOrigin() instanceof TNode) {
				this.mapping = m;
				lhsNode = (TNode) this.mapping.getOrigin();
				break;
			}
		}
	}
	
	/**
	 * gets the rule mapping
	 * @return the rule mapping
	 */
	public Mapping getRuleMapping() {
		return this.mapping;
	}
	
	@Override
	public Mapping getNacMapping() {
		{ExceptionUtil.error("NAC mapping was requested but not computed for this node."); return null;}
	}
	
	@Override
	protected void setNacMapping(Node model) {		
	}
	
	@Override
	public void setNacMapping(Mapping m) {		
	}
	
	/**
	 * sets the nac mappings belongs to model
	 * @param model the model
	 */
	private void setNacMappings(TNode model) {
//		NodeLayout layoutModel = getLayoutModel();
		if (getCastedModel().getGraph().eContainer() instanceof Rule
				//&& model.getIsMarked()!=null && model.getIsMarked()
				) {
			Formula f = ((Rule) getCastedModel().getGraph().eContainer()).getLhs().getFormula();
			if (f != null) {
				addNacMappings(f, model);
				if (this.mappings.size() > 0)
					this.index = model.getGraph().getNodes().indexOf(getModel());
			}
		}
	}
	
	/**
	 * adds nac mappings
	 * @param f the formula
	 * @param model the node
	 */
	private void addNacMappings(Formula f, TNode model) {
		if (f instanceof And) {
			if (((And)f).getLeft() instanceof And)
				addNacMappings(((And)f).getLeft(), model);
			else 
				addNacMaps(((And)f).getLeft(), model);
			
			if (((And)f).getRight() instanceof And) 
				addNacMappings(((And)f).getRight(), model);
			else 
				addNacMaps(((And)f).getRight(), model);
		}		
		else 
			addNacMaps(f, model);
	}
	
	/**
	 * helper method for addNacMappings
	 * @param f the Formula
	 * @param model the model
	 */
	private void addNacMaps(Formula f, Node model) {
		EList<Mapping> maps = ((NestedCondition)((Not)f).getChild()).getMappings();
		for (Mapping m : maps) {
			if (m.getOrigin() == RuleUtil.getLHSNode(model)) 
				this.mappings.add(m);
		}
	}
	
	/**
	 * adds a mapping to mappings
	 * @param m the new mapping
	 */
	public void addMapping(Mapping m) {
		if (!this.mappings.contains(m)) {
			this.mappings.add(m);
			registerAdapter(m);
		}
	}
	
	@Override
	protected void performOpen() {
		// do nothing
	}
	
}
