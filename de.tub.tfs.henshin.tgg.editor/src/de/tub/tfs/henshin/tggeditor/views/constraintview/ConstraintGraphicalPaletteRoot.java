package de.tub.tfs.henshin.tggeditor.views.constraintview;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.palette.PaletteGroup;

import de.tub.tfs.henshin.tggeditor.TGGModelCreationFactory;
import de.tub.tfs.henshin.tggeditor.tools.EdgeCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.MappingCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.constraint.AttributeCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.constraint.NodeCreationTool;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;

public class ConstraintGraphicalPaletteRoot extends MuvitorPaletteRoot {

	protected PaletteGroup controls;
	protected Module transformationSystem;
	
	public ConstraintGraphicalPaletteRoot(Module transformationSystem) {
		this.transformationSystem = transformationSystem;
		
		controls = new PaletteGroup("Controls");
		add(controls);
		addToolEntry(controls, "Node", "Create Node", 
				new TGGModelCreationFactory(Node.class), 
				null, 
				null, 
				NodeCreationTool.class);
		addToolEntry(controls, "Edge", "Create Edge", 
			new TGGModelCreationFactory(Edge.class), 
			null, 
			null, 
			EdgeCreationTool.class);
		addToolEntry(controls, "Attribute", "Create Attribute",
				new TGGModelCreationFactory(Attribute.class),
				null, 
				null, 
				AttributeCreationTool.class);
		addToolEntry(controls, "Mapping","Mapping for node",
				new TGGModelCreationFactory(Mapping.class),
				null,
				null,
				MappingCreationTool.class);
	}
	
}
