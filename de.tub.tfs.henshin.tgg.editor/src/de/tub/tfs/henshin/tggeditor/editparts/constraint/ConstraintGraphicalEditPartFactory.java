package de.tub.tfs.henshin.tggeditor.editparts.constraint;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.gef.EditPart;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphicalEditPartFactory;

public class ConstraintGraphicalEditPartFactory extends GraphicalEditPartFactory {

	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof TripleGraph) {
			return new GraphEditPart((Graph)model);
		}
		if (model instanceof TNode) {
			return new NodeEditPart((TNode)model);
		}
		if (model instanceof TEdge) {
			return new EdgeEditPart((Edge)model);
		}
		if (model instanceof TAttribute)
			return new AttributeEditPart((TAttribute)model);
		return super.createEditPart(context, model);
	}

}
