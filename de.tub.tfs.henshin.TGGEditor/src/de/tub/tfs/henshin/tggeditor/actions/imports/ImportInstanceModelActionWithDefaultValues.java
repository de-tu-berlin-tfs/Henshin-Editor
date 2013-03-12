package de.tub.tfs.henshin.tggeditor.actions.imports;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.ui.IWorkbenchPart;


public class ImportInstanceModelActionWithDefaultValues extends ImportInstanceModelAction {

	/** The Constant ID. */
	public static final String ID = "tggeditor.actions.ImportInstanceModelActionWithDefaultValue";

	
	
	public ImportInstanceModelActionWithDefaultValues(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Import instance (default values).");
		setToolTipText("Import an instance model with default values.");

	}

	/**
	 * @param feat
	 */
	@Override
	protected void createAttribute(EAttribute feat) {
		Attribute attr;
		if (feat.getEType().getName().equals("EFeatureMapEntry"))
			// do nothing, because this map summarizes all features
			return;

		if (!eObj.eIsSet(feat) && feat.getDefaultValue() == null) 
			// no value available, thus do not create an attribute
			return;

		// value is available
		
		// process attribute
		attr = HenshinFactory.eINSTANCE.createAttribute();
		attr.setNode(node);
		attr.setType((EAttribute) feat);

		if (attr.getType().getName().contains("name")
				&& !(eObj instanceof NamedElement)) {
			node.setName(attr.getValue());
		}

		if (feat.isMany()) {
			if (eObj.eIsSet(feat)) {
				// value of feature is set
				String valueString = eObj.eGet(feat).toString();
				// remove the square brackets of the array
				// TODO: remove only if the list contains one element
				if (valueString.length() > 1) {
					valueString = valueString.substring(1,
							valueString.length() - 1);
				}
				attr.setValue(valueString);
			} else {
				// feature is not set -> handle default values
				if (feat.getDefaultValue() != null) {
					attr.setValue(feat.getDefaultValue().toString());
				}
			}
		} else {
			// feature is single valued
			if (eObj.eIsSet(feat)) {
				attr.setValue(eObj.eGet(feat).toString());
			} else {
				// feature is not set -> handle default values
				if (feat.getDefaultValue() != null) {
					attr.setValue(feat.getDefaultValue().toString());
				}
			}
		}
	}


}
