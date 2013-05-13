/**
 * ClassEditingSupport.java
 * created on 20.11.2012 23:19:05
 */
package de.tub.tfs.henshin.editor.wizards;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import de.tub.tfs.henshin.editor.util.Pair;

/**
 * @author huuloi
 */
public class SimpleEditingSupport<E extends EObject> extends EditingSupport {
	
	private ComboBoxViewerCellEditor cellEditor = null;
	
	private IWizard wizard;

	public SimpleEditingSupport(
		ColumnViewer viewer, 
		List<E> input, 
		LabelProvider labelProvider,
		IWizard wizard
	) {
		super(viewer);
		cellEditor = new ComboBoxViewerCellEditor((Composite) getViewer().getControl(), SWT.READ_ONLY);
		cellEditor.setLabelProvider(labelProvider);
		cellEditor.setContentProvider(new ArrayContentProvider());
		cellEditor.setInput(input);
		this.wizard = wizard;
	}


	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}


	@Override
	protected boolean canEdit(Object element) {
		return true;
	}


	@Override
	@SuppressWarnings("unchecked")
	protected Object getValue(Object element) {
		if (element instanceof Pair) {
			Pair<E, E> data = (Pair<E, E>) element;
			return data.getSecond();
		}
		return null;
	}


	@Override
	@SuppressWarnings("unchecked")
	protected void setValue(Object element, Object value) {
		Pair<E, E> data = (Pair<E, E>) element;
		E newValue = (E) value;
		if ((data.getSecond() == null && newValue != null) ||
			(data.getSecond() != null &&  !data.getSecond().equals(newValue))
		) {
			data.setSecond(newValue);
			getViewer().refresh();
			wizard.getContainer().updateButtons();
		}
	}

}
