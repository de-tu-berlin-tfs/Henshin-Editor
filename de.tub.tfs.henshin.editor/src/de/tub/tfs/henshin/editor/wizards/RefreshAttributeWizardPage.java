/**
 * RefreshAttributeWizardPage.java
 * created on 03.11.2012 20:02:42
 */
package de.tub.tfs.henshin.editor.wizards;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.Pair;

/**
 * @author huuloi
 */
public class RefreshAttributeWizardPage extends WizardPage {
	
	public static final String ID = "de.tub.tfs.henshin.editor.ui.wizard.RefreshAttributeWizardPage"; //$NON-NLS-1$
	
	private TableViewer tableViewer;
	
	
	public RefreshAttributeWizardPage() {
		super(ID);
		setTitle(Messages.REFRESH_ATTRIBUTE_PAGE_TITLE);
		setDescription(Messages.REFRESH_ATTRIBUTE_PAGE_DESC);
	}


	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);
		TableColumnLayout tableColumnLayout = new TableColumnLayout();
		container.setLayout(tableColumnLayout);
		
		setControl(container);
		
		this.tableViewer = new TableViewer(container, SWT.FULL_SELECTION | SWT.BORDER);

		Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableViewerColumn changedAttributeTableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		changedAttributeTableColumn.getColumn().setResizable(true);
		changedAttributeTableColumn.getColumn().setText(Messages.CHANGED_CLASS);
		
		TableViewerColumn addedAttributeTableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		addedAttributeTableColumn.getColumn().setResizable(true);
		addedAttributeTableColumn.getColumn().setText(Messages.ADDED_CLASS);
		SimpleEditingSupport<EStructuralFeature> editingSupport = new SimpleEditingSupport<EStructuralFeature>(
			addedAttributeTableColumn.getViewer(),
			getCastedWizard().getAddedAttributes(),
			new SimpleLabelProvider(),
			getWizard()
		);
		addedAttributeTableColumn.setEditingSupport(editingSupport);
		
		tableColumnLayout.setColumnData(changedAttributeTableColumn.getColumn(), new ColumnWeightData(300));
		tableColumnLayout.setColumnData(addedAttributeTableColumn.getColumn(), new ColumnWeightData(300));
		
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new SimpleTableLabelProvider());
		tableViewer.setInput(getInput());
	}
	
	
	private List<Pair<EStructuralFeature, EStructuralFeature>> getInput() {
		List<Pair<EStructuralFeature, EStructuralFeature>> input = new ArrayList<Pair<EStructuralFeature,EStructuralFeature>>();
		for (EStructuralFeature feature : getCastedWizard().getRemovedAttributes()) {
			Pair<EStructuralFeature, EStructuralFeature> pair = new Pair<EStructuralFeature, EStructuralFeature>(feature, null);
			input.add(pair);
		}
		return input;
	}


	@SuppressWarnings("unchecked")
	public List<Pair<EStructuralFeature, EStructuralFeature>> getValue() {
		if (tableViewer != null) {
			return (List<Pair<EStructuralFeature, EStructuralFeature>>) tableViewer.getInput();
		}
		return null;
	}
	
	
	private RefreshWizard getCastedWizard() {
		return (RefreshWizard) getWizard();
	}
	
}
