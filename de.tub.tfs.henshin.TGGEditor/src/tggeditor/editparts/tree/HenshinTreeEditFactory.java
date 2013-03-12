package tggeditor.editparts.tree;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import tgg.CritPair;
import tggeditor.editparts.tree.critical.CheckedRulePairFolder;
import tggeditor.editparts.tree.critical.CheckedRulePairFolderTreeEditPart;
import tggeditor.editparts.tree.critical.CheckedRulePairFolderTreeEditPart;
import tggeditor.editparts.tree.critical.CritPairTreeEditPart;
import tggeditor.editparts.tree.graphical.AttributeTreeEditPart;
import tggeditor.editparts.tree.graphical.EdgeTreeEditPart;
import tggeditor.editparts.tree.graphical.GraphFolder;
import tggeditor.editparts.tree.graphical.GraphFolderTreeEditPart;
import tggeditor.editparts.tree.graphical.GraphTreeEditPart;
import tggeditor.editparts.tree.graphical.NodeTreeEditPart;
import tggeditor.editparts.tree.rule.FTRules;
import tggeditor.editparts.tree.rule.FTRulesTreeEditPart;
import tggeditor.editparts.tree.rule.NACTreeEditPart;
import tggeditor.editparts.tree.rule.ParameterTreeEditPart;
import tggeditor.editparts.tree.rule.RuleFolder;
import tggeditor.editparts.tree.rule.RuleFolderTreeEditPart;
import tggeditor.editparts.tree.rule.RuleTreeEditPart;


public class HenshinTreeEditFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if(model instanceof ImportFolder){
			return new ImportFolderTreeEditPart((ImportFolder) model);
		}
		if(model instanceof EPackage
				&& context instanceof  ImportFolderTreeEditPart){
			return new ImportTreeEditPart((EPackage) model, ((ImportFolderTreeEditPart) context).getCastedModel().getTGGModel());
		}
		if (model instanceof Module) {
			return new TransformationSystemTreeEditPart(
					(Module) model);
		}
		if (model instanceof GraphFolder){
			return new GraphFolderTreeEditPart((GraphFolder) model);
		}
		if (model instanceof Graph) {
			if(((Graph) model).eContainer() instanceof NestedCondition){
				return new NACTreeEditPart((Graph) model);
			}
			return new GraphTreeEditPart((Graph) model); 
		}
		if (model instanceof Node) {
			return new NodeTreeEditPart((Node) model); 
		}
		if (model instanceof Edge) {
			return new EdgeTreeEditPart((Edge) model); 
		}
		if (model instanceof Attribute) {
			return new AttributeTreeEditPart((Attribute) model); 
		}
		if (model instanceof RuleFolder){
			return new RuleFolderTreeEditPart((RuleFolder) model);
		}
		if (model instanceof Rule){
			return new RuleTreeEditPart((Rule) model);
		}
		if (model instanceof Parameter){
			return new ParameterTreeEditPart((Parameter) model);
		}
		if(model instanceof FTRules){
			return new FTRulesTreeEditPart((FTRules) model);
		}
		if(model instanceof CheckedRulePairFolder) {
			return new CheckedRulePairFolderTreeEditPart((CheckedRulePairFolder) model); 
		}
		if(model instanceof CritPair) {
			return new CritPairTreeEditPart((CritPair) model);
		}
		if(model instanceof CheckedRulePairFolder) {
			return new CheckedRulePairFolderTreeEditPart((CheckedRulePairFolder)model);
		}
		Assert.isTrue(model == null,
			"TreeEditPartFactory could not create an EditPart for model element "
			+ model);
		return null;
	}

}
