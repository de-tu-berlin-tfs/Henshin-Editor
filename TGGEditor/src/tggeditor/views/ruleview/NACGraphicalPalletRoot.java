package tggeditor.views.ruleview;

import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.muvitor.gef.palette.MappingCreationTool;

import tggeditor.TGGModelCreationFactory;
import tggeditor.views.graphview.GraphicalPaletteRoot;

public class NACGraphicalPalletRoot extends GraphicalPaletteRoot {

	//protected PaletteGroup controls;	

	public NACGraphicalPalletRoot(Module transformationSystem) {
		super(transformationSystem);
		addToolEntry(super.controls, 
				"Mappping",
				"Create Mapping", 
				new TGGModelCreationFactory(NestedCondition.class),
				null, 
				null,
				MappingCreationTool.class);		
	}
	
}
