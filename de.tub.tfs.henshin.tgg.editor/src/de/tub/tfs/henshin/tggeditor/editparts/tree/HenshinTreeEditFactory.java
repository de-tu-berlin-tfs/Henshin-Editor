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
package de.tub.tfs.henshin.tggeditor.editparts.tree;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Constraint;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.NestedConstraint;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Or;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.util.ExceptionUtil;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.AndTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.ConstraintTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.NestedConstraintTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.NotTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.constraint.OrTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.critical.CheckedRulePairFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.critical.CheckedRulePairFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.critical.CritPairTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.AttributeTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.EdgeTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolder;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.NodeTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.AttributeConditionTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.NACTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.ParameterTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;



public class HenshinTreeEditFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof ImportFolder){
			return new ImportFolderTreeEditPart((ImportFolder) model);
		}
		if(model instanceof ImportedPackage
				&& context instanceof  ImportFolderTreeEditPart){
			return new ImportTreeEditPart((ImportedPackage) model, ((ImportFolderTreeEditPart) context).getCastedModel().getTGGModel());
		}
		if(model instanceof EPackage
				&& context instanceof  ImportFolderTreeEditPart){
			return null;
		}
		if (model instanceof TGG) {
			return new TransformationSystemTreeEditPart(
					(TGG) model);
		}
		if (model instanceof GraphFolder){
			return new GraphFolderTreeEditPart((GraphFolder) model);
		}
		if (model instanceof ConstraintFolder) {
			return new ConstraintFolderTreeEditPart((ConstraintFolder) model );
		}
		if (model instanceof TripleGraph) {
			if(((TripleGraph) model).eContainer() instanceof NestedCondition){
				return new NACTreeEditPart((TripleGraph) model);
			}
			return new GraphTreeEditPart((TripleGraph) model); 
		}
		if (model instanceof Constraint) {
			return new ConstraintTreeEditPart((Constraint)model);
		}
		if (model instanceof NestedConstraint) {
			return new NestedConstraintTreeEditPart((NestedConstraint)model);
		}
		if (model instanceof Not) {
			return new NotTreeEditPart((Not)model);
		}
		if (model instanceof And) {
			return new AndTreeEditPart((And)model);
		}
		if (model instanceof Or) {
			return new OrTreeEditPart((Or)model);
		}
		if (model instanceof TNode) {
			return new NodeTreeEditPart((TNode) model); 
		}
		if (model instanceof Node) {
			 {ExceptionUtil.error("Node cannot be loaded in the tree, because it is not a TNode."); return null;}
		}
		if (model instanceof TEdge) {
			return new EdgeTreeEditPart((Edge) model); 
		}
		if (model instanceof Edge) {
			 {ExceptionUtil.error("Edge cannot be loaded in the tree, because it is not a TEdge."); return null;}
		}
		if (model instanceof TAttribute) {
			return new AttributeTreeEditPart((Attribute) model); 
		}
		//if (model instanceof RuleFolder){
		//	return new RuleFolderTreeEditPart((RuleFolder) model);
		//}
		if (model instanceof TGGRule){
			return new RuleTreeEditPart((Rule) model);
		}
		if (model instanceof Parameter){
			return new ParameterTreeEditPart((Parameter) model);
		}
		//if(model instanceof FTRuleFolder){
		//	return new FTRulesTreeEditPart((FTRuleFolder) model);
		//}
		if(model instanceof CheckedRulePairFolder) {
			return new CheckedRulePairFolderTreeEditPart((CheckedRulePairFolder) model); 
		}
		if(model instanceof CritPair) {
			return new CritPairTreeEditPart((CritPair) model);
		}
		if(model instanceof CheckedRulePairFolder) {
			return new CheckedRulePairFolderTreeEditPart((CheckedRulePairFolder)model);
		}
		if (model instanceof IndependentUnit){
			return new RuleFolderTreeEditPart((IndependentUnit)model);
		}
		if (model instanceof AttributeCondition){
			return new AttributeConditionTreeEditPart((AttributeCondition) model);
		}
		Assert.isTrue(model == null,
			"TreeEditPartFactory could not create an EditPart for model element "
			+ model);
		return null;
	}

}
