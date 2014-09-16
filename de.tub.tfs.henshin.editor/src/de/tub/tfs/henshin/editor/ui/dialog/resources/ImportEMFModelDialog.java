/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.dialog.resources;

import java.util.ArrayList;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.presentation.EcoreActionBarContributor.ExtendedLoadResourceAction.RegisteredPackageDialog;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class ImportEMFModelDialog.
 * 
 * @author nam
 */
public class ImportEMFModelDialog extends RegisteredPackageDialog implements
		SelectionListener {

	/** The imported. */
	private EPackage imported = null;

	/**
	 * Instantiates a new import emf model dialog.
	 * 
	 * @param arg0
	 *            the arg0
	 */
	public ImportEMFModelDialog(Shell arg0) {
		super(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.dialogs.SelectionDialog#createButtonsForButtonBar(org.
	 * eclipse.swt.widgets.Composite)
	 */
	@Override
	protected void createButtonsForButtonBar(Composite parent) {
		Button fileSystemBt = new Button(parent, SWT.PUSH);
		GridData layoutData = new GridData(SWT.BEGINNING, SWT.FILL, true, true,
				2, 1);

		fileSystemBt.setLayoutData(layoutData);
		fileSystemBt.setText("Workspace...");

		fileSystemBt.addSelectionListener(this);

		((GridLayout) parent.getLayout()).numColumns++;
		((GridLayout) parent.getLayout()).numColumns++;
		super.createButtonsForButtonBar(parent);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.dialogs.ElementListSelectionDialog#computeResult()
	 */
	@Override
	protected void computeResult() {
		ArrayList<EPackage> imported = new ArrayList<EPackage>();

		if (this.imported != null) {
			imported.add(this.imported);
		} else {
			for (Object pkg : getSelectedElements()) {
				EPackage e = EPackage.Registry.INSTANCE
						.getEPackage((String) pkg);
				imported.add(e);
			}
		}

		setResult(imported);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse
	 * .swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt
	 * .events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		ResourcesDialog sysDiag = new ResourcesDialog(getShell(), null,
				"Import EMF Model", "Select a resource with an EMF model.",
				SWT.OPEN);

		sysDiag.addFileExtensions(new String[] { "ecore" });
		sysDiag.open();

		if (sysDiag.getReturnCode() == OK) {
			String fileLoc = sysDiag.getResult().getAbsolutePath();
			ResourceSet resSet = new ResourceSetImpl();
			Resource src = resSet.getResource(URI.createFileURI(fileLoc), true);
			if (!src.getContents().isEmpty()
					&& src.getContents().get(0) instanceof EPackage)
				src.setURI(URI.createURI(((EPackage) src.getContents().get(0))
						.getNsURI()));
			try {
				imported = (EPackage) src.getContents().get(0);
				okPressed();
			} catch (ClassCastException e1) {
				MessageDialog.openError(getShell(), "Save File Error",
						"No EMF model package found in: \n" + fileLoc + ".");
			}
		}
	}
}
