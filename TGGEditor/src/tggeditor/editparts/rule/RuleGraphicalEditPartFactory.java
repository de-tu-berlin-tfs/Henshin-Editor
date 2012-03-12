package tggeditor.editparts.rule;



import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import tgg.GraphLayout;
import tggeditor.editparts.graphical.AttributeEditPart;
import tggeditor.editparts.graphical.DividerEditPart;
import tggeditor.editparts.graphical.EdgeEditPart;
import tggeditor.editparts.graphical.GraphEditPart;
import tggeditor.editparts.graphical.NodeObjectEditPart;

/**
 * The EditPartFactory class of Rules.
 */
public class RuleGraphicalEditPartFactory implements EditPartFactory{
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof Rule) {
			return new RuleGraphicalEditPart((Rule) model);
		}

		if(model instanceof Graph){
			return new GraphEditPart((Graph) model);
		}
		if(model instanceof Node){
			if (((Node)model).eContainer().eContainer() instanceof NestedCondition)
				return new NodeObjectEditPart((Node) model);
			else
				return new RuleNodeEditPart((Node) model);
		}
		if(model instanceof Attribute){
			return new AttributeEditPart((Attribute) model);
		}
		if(model instanceof Edge){			
			if (context instanceof RuleNodeEditPart)
				return new RuleEdgeEditPart((Edge) model);
			else
				return new EdgeEditPart((Edge) model);
		}
		if (model instanceof GraphLayout && context instanceof GraphEditPart) {
			return new DividerEditPart((GraphLayout) model, (GraphEditPart) context);
		}

		
		Assert.isTrue( model == null,"RuleGraphicalEditPartFactory could not create an EditPart for the model"+ model);
		return null;
	}

}