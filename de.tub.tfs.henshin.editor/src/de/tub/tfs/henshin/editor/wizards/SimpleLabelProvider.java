/**
 * ClassLabelProvider.java
 * created on 05.11.2012 11:49:38
 */
package de.tub.tfs.henshin.editor.wizards;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.jface.viewers.LabelProvider;

/**
 * @author huuloi
 */
public class SimpleLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		if (element instanceof ENamedElement) {
			return ((ENamedElement) element).getName();
		}
		return super.getText(element);
	}
}
