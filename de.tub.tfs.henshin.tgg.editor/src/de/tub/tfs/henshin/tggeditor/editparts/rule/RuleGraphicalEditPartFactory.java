package de.tub.tfs.henshin.tggeditor.editparts.rule;



import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.AttributeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.Divider;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;


/**
 * The EditPartFactory class of Rules.
 */
public class RuleGraphicalEditPartFactory implements EditPartFactory{
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof Rule) {
			return new RuleGraphicalEditPart((Rule) model);
		}

		if(model instanceof TripleGraph){
			return new GraphEditPart((TripleGraph) model);
		}
		if(model instanceof Node){
			if (((Node)model).eContainer().eContainer() instanceof NestedCondition)
				return new TNodeObjectEditPart((TNode) model);
			else
				return new RuleNodeEditPart((TNode) model);
		}
		if(model instanceof Attribute){
			if (context instanceof RuleNodeEditPart)
				return new RuleAttributeEditPart((Attribute) model);
			else 
				return new AttributeEditPart((Attribute) model);
		}
		if(model instanceof Edge){			
			if (context instanceof RuleNodeEditPart)
				return new RuleEdgeEditPart((Edge) model);
			else
				return new EdgeEditPart((Edge) model);
		}
		if (model instanceof Divider && context instanceof GraphEditPart) {
			return new DividerEditPart((Divider) model, (GraphEditPart) context);
		}
		if (model instanceof AttributeCondition){
			return new AttributeConditionGraphicalEditPart((AttributeCondition)model);
		}
		
		Assert.isTrue( model == null,"RuleGraphicalEditPartFactory could not create an EditPart for the model"+ model);
		return null;
	}

}