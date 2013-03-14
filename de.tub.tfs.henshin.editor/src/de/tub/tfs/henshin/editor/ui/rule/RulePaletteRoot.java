/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.rule;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Parameter;

import de.tub.tfs.henshin.editor.actions.rule.NodeMappingCreationTool;
import de.tub.tfs.henshin.editor.actions.transformation_unit.tools.ParameterCreationTool;
import de.tub.tfs.henshin.editor.model.ModelCreationFactory;
import de.tub.tfs.henshin.editor.ui.graph.GraphPaletteRoot;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.NodeTypes;

/**
 * The Class RulePalletRoot.
 * 
 * @author Johann Regelpalette wird hier erweitert.
 */
public class RulePaletteRoot extends GraphPaletteRoot {

	/**
	 * Instantiates a new rule pallet root.
	 * 
	 * @param transformationSystem
	 *            the transformation system
	 */
	public RulePaletteRoot(Module transformationSystem) {
		super(transformationSystem);
		addToolEntry(rest, "Mapping", "Create Mapping",
				new ModelCreationFactory(Mapping.class),
				IconUtil.getDescriptor("mapping16.png"),
				IconUtil.getDescriptor("mapping24.png"),
				NodeMappingCreationTool.class);

		addToolEntry(rest, "Parameter", "Create Parameter",
				new ModelCreationFactory(Parameter.class),
				IconUtil.getDescriptor("parameter16.png"),
				IconUtil.getDescriptor("parameter20.png"),
				ParameterCreationTool.class);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.ui.graphs.GraphPalletRoot#getNodeTypesVonEPackage(org.eclipse
	 * .emf.ecore.EPackage)
	 */
	@Override
	protected List<EClass> getNodeTypesOfEPackage(EPackage ePackage) {
		return NodeTypes.getNodeTypesOfEPackage(ePackage, true);
	}

}
