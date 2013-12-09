package de.tub.tfs.henshin.tggeditor.views.ruleview;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gef.tools.CreationTool;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.TGGModelCreationFactory;
import de.tub.tfs.henshin.tggeditor.tools.MarkerCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.MarkerUnspecifiedCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.RuleAttributeCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.RuleEdgeCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.RuleNodeCreationTool;
import de.tub.tfs.henshin.tggeditor.util.NodeTypes;
//import de.tub.tfs.muvitor.gef.palette.MappingCreationTool;
import de.tub.tfs.henshin.tggeditor.tools.MappingCreationTool;
import de.tub.tfs.muvitor.gef.palette.MuvitorPaletteRoot;

/**
 * A default palette root that can be used in graphical rule views. It features a
 * flexible convenient method to add new tool entries to palette groups.
 */

public class RuleGraphicalPaletteRoot extends MuvitorPaletteRoot {
	
	/** The graph tools. */
	protected PaletteGroup graphTools;
	
	/** The rest. */
	protected PaletteGroup controls;

	/** The transformation system. */
	protected Module transformationSystem;
	
	public RuleGraphicalPaletteRoot(Module transformationSystem) {
		this.transformationSystem = transformationSystem;
		
		graphTools = createGraphPalette();
		add(1,graphTools);
		
		controls = new PaletteGroup("Controls");
		add(controls);

		addToolEntry(controls, "Node", "Create Node", 
				new TGGModelCreationFactory(TNode.class), 
				null, 
				null, 
				RuleNodeCreationTool.class);
		
		addToolEntry(controls, "Edge", "Create Edge", 
			new TGGModelCreationFactory(Edge.class), 
			null, 
			null, 
			RuleEdgeCreationTool.class);
		
		addToolEntry(controls, "Attribute", "Create Attribute",
				new TGGModelCreationFactory(Attribute.class),
				null, 
				null, 
				RuleAttributeCreationTool.class);
		
		addToolEntry(controls, "++", "Marker for added node in RHS",
				new TGGModelCreationFactory(Mapping.class),
				null,
				null,
				MarkerCreationTool.class);

	
		addToolEntry(controls, "Mapping","Mapping for node",
				new TGGModelCreationFactory(Mapping.class),
				null,
				null,
				MappingCreationTool.class);			
		
		addToolEntry(controls, "mark [tr=?]", "Marks a NAC element with unspecified marking (arbitrary)",
				new TGGModelCreationFactory(Mapping.class),
				null,
				null,
				MarkerUnspecifiedCreationTool.class);
	}
	
	/**
	 * Creates the graph palette.
	 *
	 * @return the palette group
	 */
	private PaletteGroup createGraphPalette() {
		PaletteGroup graphToolsGroup = new PaletteGroup("Controls");
		for (EPackage ePackage : transformationSystem.getImports()) {
			final PaletteStack marqueeStack = new PaletteStack("", "", null); //$NON-NLS-1$
			for (EClass eClass : getNodeTypesVonEPackage(ePackage)) {
//				final ToolEntry entry = new ToolEntry(eClass.getName(),
//						"Create " + eClass.getName(), IconUtil.getDescriptor(
//								"node18.png"), IconUtil.getDescriptor(
//								"node25.png"), CreationTool.class) {
//				};
				final ToolEntry entry = new ToolEntry(eClass.getName(),
						"Create " + eClass.getName(), null, null, CreationTool.class) {
				};
				entry.setToolProperty(CreationTool.PROPERTY_CREATION_FACTORY,
						new TGGModelCreationFactory(Node.class, eClass));
				entry.setUserModificationPermission(PERMISSION_NO_MODIFICATION);
				marqueeStack.add(entry);
			}
			graphToolsGroup.add(marqueeStack);

		}
		return graphToolsGroup;
	}
	
	/**
	 * Gets the node types von e package.
	 *
	 * @param ePackage the e package
	 * @return the node types von e package
	 */
	protected List<EClass> getNodeTypesVonEPackage(EPackage ePackage){
		return NodeTypes.getNodeTypesOfEPackage(ePackage,false);
	}
	
	/**
	 * Refresh graph tools group.
	 */
	public void refreshGraphToolsGroup() {
		PaletteGroup tempGraphTools = createGraphPalette();
		remove(graphTools);
		graphTools = tempGraphTools;
		add(1,graphTools);
	}
	
}
