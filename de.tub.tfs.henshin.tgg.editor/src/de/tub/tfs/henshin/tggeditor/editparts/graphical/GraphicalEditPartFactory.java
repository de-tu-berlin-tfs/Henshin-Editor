package de.tub.tfs.henshin.tggeditor.editparts.graphical;


import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleEdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;



public class GraphicalEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof TripleGraph) {
			return new GraphEditPart((TripleGraph) model);
		}
		if (model instanceof Node) {
			if (((Node) model).eContainer().eContainer() instanceof Rule) {
				if (((Graph)((Node) model).eContainer()) == ((Rule)((Node) model).eContainer().eContainer()).getRhs())
					return new RuleNodeEditPart((Node) model);
			}
			return new NodeObjectEditPart((Node) model);	
			
		}
		if (model instanceof Edge) {
			if (context.getParent() instanceof RuleGraphicalEditPart)
				return new RuleEdgeEditPart((Edge) model);
			else 
				return new EdgeEditPart((Edge)model);
		}
		if (model instanceof Divider  && context instanceof GraphEditPart) {
			return new DividerEditPart((Divider) model, (GraphEditPart) context);
		}
		if (model instanceof Attribute) {
			return new AttributeEditPart((Attribute) model);
		}
		assert model == null :
				"GraphEditPartFactory could not create an EditPart for the model elemen"+ model;
		return null;
	}

}
