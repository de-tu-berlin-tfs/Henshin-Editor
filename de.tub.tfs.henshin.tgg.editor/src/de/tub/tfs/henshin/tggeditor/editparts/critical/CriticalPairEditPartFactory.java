package de.tub.tfs.henshin.tggeditor.editparts.critical;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.AttributeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.DividerEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.NodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleEdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleGraphicalEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;


public class CriticalPairEditPartFactory implements EditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof TripleGraph) {
			return new GraphEditPart((TripleGraph) model);
		}
		if(model instanceof Node){
			if (((Node)model).eContainer().eContainer() instanceof Rule)
				return new RuleNodeEditPart((Node) model);
			else
				return new NodeObjectEditPart((Node) model);
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
//		if (model instanceof GraphLayout && context instanceof GraphEditPart) {
//			return new DividerEditPart((GraphLayout) model, (GraphEditPart) context);
//		}
		if (model instanceof Rule){
			return new RuleGraphicalEditPart((Rule) model);
		}
		Assert.isTrue( model == null,"CriticalPairEditPartFactory could not create an EditPart for the model"+ model);
		return null;
	}

}
