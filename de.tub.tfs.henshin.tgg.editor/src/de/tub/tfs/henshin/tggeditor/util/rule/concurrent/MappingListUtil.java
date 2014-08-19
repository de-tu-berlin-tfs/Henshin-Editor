package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

//NEW
public class MappingListUtil {
	
	public static MappingList getInverseMappingList(MappingList mlist){
		MappingList invMappings = new MappingListImpl();
		for (Mapping ruleLMapping : mlist){
			Node image = ruleLMapping.getImage();
			Node origin = ruleLMapping.getOrigin();
			invMappings.add(image, origin);
		}
		return invMappings;
	}
}
