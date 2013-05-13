/**
 * RefreshWizardPage.java
 * created on 03.11.2012 14:29:39
 */
package de.tub.tfs.henshin.editor.wizards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EClassifier;
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
public class RefreshClassWizardPage extends WizardPage {
	
	public static final String ID = "de.tub.tfs.henshin.editor.ui.wizard.RefreshClassWizardPage"; //$NON-NLS-1$

	private TableViewer tableViewer;
	

	public RefreshClassWizardPage() {
		super(ID);
		setTitle(Messages.REFRESH_CLASS_PAGE_TITLE);
		setDescription(Messages.REFRESH_CLASS_PAGE_DESC);
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
		
		TableViewerColumn changedClassTableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		changedClassTableColumn.getColumn().setResizable(true);
		changedClassTableColumn.getColumn().setText(Messages.CHANGED_CLASS);
		
		TableViewerColumn addedClassTableColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		addedClassTableColumn.getColumn().setResizable(true);
		addedClassTableColumn.getColumn().setText(Messages.ADDED_CLASS);
		SimpleEditingSupport<EClassifier> editingSupport = new SimpleEditingSupport<EClassifier>(
			addedClassTableColumn.getViewer(), 
			getCastedWizard().getAddedClasses(), 
			new SimpleLabelProvider(), 
			getWizard()
		);
		addedClassTableColumn.setEditingSupport(editingSupport);
		
		tableColumnLayout.setColumnData(changedClassTableColumn.getColumn(), new ColumnWeightData(300));
		tableColumnLayout.setColumnData(addedClassTableColumn.getColumn(), new ColumnWeightData(300));
		
		
		tableViewer.setContentProvider(new ArrayContentProvider());
		tableViewer.setLabelProvider(new SimpleTableLabelProvider());
		tableViewer.setInput(getInput());
	}
	
	
	private List<Pair<EClassifier, EClassifier>> getInput() {
		List<Pair<EClassifier, EClassifier>> input = new ArrayList<Pair<EClassifier,EClassifier>>();
		for (EClassifier classifier : getCastedWizard().getRemovedClasses()) {
			Pair<EClassifier, EClassifier> pair = new Pair<EClassifier, EClassifier>(classifier, null);
			input.add(pair);
		}
		return input;
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean isPageComplete() {
		Set<EClassifier> renamedClass = new HashSet<EClassifier>();
		for (int i = 0; i < getCastedWizard().getRemovedClasses().size(); i++) {
			Object element = tableViewer.getElementAt(i);
			if (element instanceof Pair) {
				EClassifier second = (EClassifier) ((Pair<EClassifier, EClassifier>) element).getSecond();
				if (second != null && !renamedClass.contains(second)) {
					renamedClass.add(second);
					setErrorMessage(null);
				}
				else if (second != null) {
					setErrorMessage("You can't rename two different EClasses with the same new name.");
					return false;
				}
			}
		}
		return true;
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Pair<EClassifier, EClassifier>> getValue() {
		if (tableViewer != null) {
			return (List<Pair<EClassifier, EClassifier>>) tableViewer.getInput();
		}
		return null;
	}

	
	private RefreshWizard getCastedWizard() {
		return (RefreshWizard) getWizard();
	}
	
}
