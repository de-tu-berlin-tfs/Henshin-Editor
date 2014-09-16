/**
 * 
 */
package de.tub.tfs.henshin.editor.commands.rule;

import java.util.List;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleSetEFeatureCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The Class DeleteMappingCommand.
 * 
 * @author Johann
 */
public class DeleteMappingCommand extends CompoundCommand {

	private Mapping mapping;

	/**
	 * @param mapping
	 * @param forceDeleteOrgColor
	 */
	public DeleteMappingCommand(Mapping mapping, boolean forceDeleteOrgColor) {
		this.mapping =mapping;
		NodeLayout originLayout = HenshinLayoutUtil.INSTANCE.getLayout(mapping
				.getOrigin());

		NodeLayout imageLayout = HenshinLayoutUtil.INSTANCE.getLayout(mapping
				.getImage());

		List<Mapping> originMappings = ModelUtil.getReferences(
				mapping.getOrigin(), Mapping.class,
				HenshinUtil.INSTANCE.getTransformationSystem(mapping),
				HenshinPackage.Literals.MAPPING__ORIGIN);

		originMappings.remove(mapping);

		if (originLayout != null)
		if  (originMappings.isEmpty() || forceDeleteOrgColor) {
			add(new SimpleSetEFeatureCommand<NodeLayout, Integer>(originLayout,
					0, HenshinLayoutPackage.Literals.NODE_LAYOUT__COLOR));
		}
		if (imageLayout != null)
		add(new SimpleSetEFeatureCommand<NodeLayout, Integer>(imageLayout, 0,
				HenshinLayoutPackage.Literals.NODE_LAYOUT__COLOR));
		add(new SimpleDeleteEObjectCommand(mapping));
	}
	
	@Override
	public boolean canExecute() {
		if (!HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(mapping.getOrigin()) && HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(mapping.getImage()))
			return false;
		return super.canExecute();
	}

}
