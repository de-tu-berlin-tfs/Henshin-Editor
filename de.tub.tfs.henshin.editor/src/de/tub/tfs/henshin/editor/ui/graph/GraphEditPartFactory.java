/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.graph;

import org.eclipse.core.runtime.Assert;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.AttributeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.EdgeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.GraphEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.RuleApplicationEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.UnitApplicationEditPart;
import de.tub.tfs.henshin.editor.internal.RuleApplicationEObject;
import de.tub.tfs.henshin.editor.internal.UnitApplicationEObject;

/**
 * A factory for creating {@link EditPart}s in {@link GraphView}.
 * 
 * @author Johann
 */
public class GraphEditPartFactory implements EditPartFactory {

	/**
	 * The {@link Graph} model object of the {@link GraphView}, for which this
	 * factory will be instantiatedO.
	 */
	private final Graph graph;

	/**
	 * Instantiates a new graph edit part factory with a given {@link Graph}
	 * model object.
	 * 
	 * @param graph
	 *            the graph
	 */
	public GraphEditPartFactory(Graph graph) {
		this.graph = graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.EditPartFactory#createEditPart(org.eclipse.gef.EditPart
	 * ,java.lang.Object)
	 */
	@Override
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof Edge) {
			return new EdgeEditPart((Edge) model);
		}

		else if (model instanceof Node) {
			return new NodeEditPart((Node) model);
		}

		else if (model instanceof Attribute) {
			return new AttributeEditPart((Attribute) model);
		}

		else if (model instanceof Graph) {
			return new GraphEditPart((Graph) model);
		}

		else if (model instanceof UnitApplicationEObject) {
			return new UnitApplicationEditPart(graph,
					(UnitApplicationEObject) model);
		}

		else if (model instanceof RuleApplicationEObject) {
			return new RuleApplicationEditPart((RuleApplicationEObject) model);
		}
		
		else {
			Assert.isTrue(model == null,
					"GraphEditPartFactory could not create an EditPart for the model element "
							+ model);
		}

		return null;
	}

}
