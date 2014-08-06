package de.tub.tfs.henshin.tggeditor.editparts.graphical;


import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.ExceptionUtil;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleEdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;



public class GraphicalEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof TripleGraph) {
			return new GraphEditPart((TripleGraph) model);
		}
		if (model instanceof Graph) {
			ExceptionUtil.error("Graph shall be created, but model is not a triple graph");
			return null;
		}
		if (model instanceof TNode) {
			if (((TNode) model).eContainer().eContainer() instanceof Rule) {
				if (((Graph)((TNode) model).eContainer()) == ((Rule)((TNode) model).eContainer().eContainer()).getRhs())
					return new RuleNodeEditPart((TNode) model);
			}
			return new TNodeObjectEditPart((TNode) model);	
			
		}
		if (model instanceof Node) {
			//ExceptionUtil.error("Node shall be created, but model is not a TNode");
			return new TNodeObjectEditPart((TNode) model);
		}
		if (model instanceof TEdge) {
			if (context.getParent() instanceof RuleGraphicalEditPart)
				return new RuleEdgeEditPart((Edge) model);
			else 
				return new EdgeEditPart((Edge)model);
		}
		if (model instanceof Divider  && context instanceof GraphEditPart) {
			return new DividerEditPart((Divider) model, (GraphEditPart) context);
		}
		if (model instanceof TAttribute) {
			return new AttributeEditPart((TAttribute) model);
		}
		assert model == null :
				"GraphEditPartFactory could not create an EditPart for the model elemen"+ model;
		return null;
	}

}
