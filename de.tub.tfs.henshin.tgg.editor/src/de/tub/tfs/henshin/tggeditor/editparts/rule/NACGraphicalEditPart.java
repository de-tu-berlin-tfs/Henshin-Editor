/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.editparts.rule;

import org.eclipse.emf.henshin.model.Graph;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;


public class NACGraphicalEditPart extends GraphEditPart {

	public NACGraphicalEditPart(Graph model) {
		super((TripleGraph) model);
	}

	
}
