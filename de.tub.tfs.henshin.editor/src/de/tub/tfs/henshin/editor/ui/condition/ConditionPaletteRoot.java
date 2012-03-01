/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.condition;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.TransformationSystem;

import de.tub.tfs.henshin.editor.actions.rule.NodeMappingCreationTool;
import de.tub.tfs.henshin.editor.model.ModelCreationFactory;
import de.tub.tfs.henshin.editor.ui.graph.GraphPaletteRoot;
import de.tub.tfs.henshin.editor.util.IconUtil;

/**
 * Extended graph pallet root.
 * 
 * @author Angeline
 */
public class ConditionPaletteRoot extends GraphPaletteRoot {

	/**
	 * Instantiates a new condition pallet root.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 */
	public ConditionPaletteRoot(TransformationSystem transformationSystem) {
		super(transformationSystem);
		addToolEntry(rest, "Mapping", "Create Mapping",
				new ModelCreationFactory(Mapping.class),
				IconUtil.getDescriptor("mapping16.png"),
				IconUtil.getDescriptor("mapping24.png"),
				NodeMappingCreationTool.class);
	}

}
