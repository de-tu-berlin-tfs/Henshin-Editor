/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.henshin.model.Attribute;

import de.tub.tfs.henshin.tgg.TAttribute;


public class AttributeUtil {








	
			//NEW
			public static void setAttributeMarker(Attribute attribute, String markerType) {
				if (attribute instanceof TAttribute)
				((TAttribute) attribute).setMarkerType(markerType);
			}
			//NEW
			public static String getAttributeMarker(Attribute attribute) {
				if (attribute instanceof TAttribute)
				return ((TAttribute) attribute).getMarkerType();
				return null;
			}
	
}