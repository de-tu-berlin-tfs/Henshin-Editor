/**
 * ClassTableLabelProvider.java
 * created on 04.11.2012 23:16:01
 */
package de.tub.tfs.henshin.editor.wizards;

import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.util.Pair;

/**
 * @author huuloi
 */
public class SimpleTableLabelProvider 
extends LabelProvider 
implements ITableLabelProvider {


	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		return null;
	}


	@Override
	@SuppressWarnings("unchecked")
	public String getColumnText(Object element, int columnIndex) {
		Pair<ENamedElement, ENamedElement> pair = (Pair<ENamedElement, ENamedElement>) element;
		switch (columnIndex) {
		case 0:
			return pair.getFirst().getName();
		case 1:
			return pair.getSecond() != null ? pair.getSecond().getName() : "";
		default:
			return "";
		}
	}

}
