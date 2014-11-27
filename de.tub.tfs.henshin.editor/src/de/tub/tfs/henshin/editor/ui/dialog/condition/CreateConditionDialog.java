package de.tub.tfs.henshin.editor.ui.dialog.condition;

import org.eclipse.emf.henshin.model.BinaryFormula;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * The Class CreateConditionDialog.
 */
public class CreateConditionDialog extends Dialog implements
		ISelectionChangedListener {

	/** The title. */
	private String title;

	/** The main composite. */
	private Composite mainComposite;

	/** The tree composite. */
	private Composite treeComposite;

	/** The condition tree viewer. */
	private ConditionTreeViewer conditionTreeViewer;

	/**
	 * In this composite the first formula composite or the negated check box is
	 * shown.
	 */
	private Composite formulaNegatedComposite;

	/**
	 * Empty label initialized if negated check box exist. This is necessary to
	 * make the negated check box position straight to the tree.
	 */
	private Label placeholderLabel;

	/** The formula composite. */
	private FormulaComposite formulaComposite;

	/** The formula composite2. */
	private FormulaComposite formulaComposite2;

	/** The negated check box of application condition. */
	private Button negatedCheckbox;

	/** The ok button. */
	private Button okButton;

	/** The current tree node. */
	private FormulaTreeNode currentTreeNode;

	/**
	 * Instantiates a new creates the condition dialog.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param title
	 *            the title
	 */
	public CreateConditionDialog(Shell parentShell, String title) {
		super(parentShell);
		this.title = title;
	}

	/**
	 * Gets the formula.
	 * 
	 * @return the formula
	 */
	public Formula getFormula() {
		return conditionTreeViewer.getFirstFormula();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets
	 * .Shell)
	 */
	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);

		if (title != null) {
			shell.setText(title);
		}

		shell.setSize(600, 400);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	protected Control createDialogArea(Composite parent) {
		mainComposite = new Composite(parent, SWT.NONE);
		mainComposite.setLayout(new GridLayout(2, true));
		mainComposite
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		createTreeComposite();
		conditionTreeViewer.addSelectionChangedListener(this);

		formulaNegatedComposite = new Composite(mainComposite, SWT.NONE);
		formulaNegatedComposite.setLayout(new GridLayout());
		formulaNegatedComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true));

		createFirstFormulaComposite();

		formulaComposite2 = new FormulaComposite(mainComposite, SWT.NONE);
		formulaComposite2.addSelectionChangedListener(this);
		formulaComposite2.setVisible(false);

		return mainComposite;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#isResizable()
	 */
	@Override
	protected boolean isResizable() {
		return true;
	}

	/**
	 * Creates the tree composite on the left side of dialog.
	 */
	private void createTreeComposite() {
		// Create composite
		treeComposite = new Composite(mainComposite, SWT.NONE);
		treeComposite.setLayout(new GridLayout());
		treeComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 2));

		// Create tree label
		final Label treeLabel = new Label(treeComposite, SWT.NONE);
		treeLabel.setText("Condition Tree");

		// Create tree
		conditionTreeViewer = new ConditionTreeViewer(treeComposite);
		currentTreeNode = conditionTreeViewer.getRootNode();
	}

	/**
	 * Create the first formula composite on the top right side of dialog.
	 * Before initializing the first formula composite, checks first: If negated
	 * check box is not {@code null}, then dispose it first and set it to
	 * {@code null}.
	 */
	private void createFirstFormulaComposite() {
		if (negatedCheckbox != null) {
			negatedCheckbox.dispose();
			negatedCheckbox = null;
		}
		if (placeholderLabel != null){
			placeholderLabel.dispose();
			placeholderLabel = null;
		}
		if (formulaComposite == null) {
			// Creates first formula composite
			formulaComposite = new FormulaComposite(formulaNegatedComposite,
					SWT.NONE);
			formulaComposite.addSelectionChangedListener(this);

			formulaNegatedComposite.layout();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ISelectionChangedListener#selectionChanged(
	 * org.eclipse.jface.viewers.SelectionChangedEvent)
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		Object object = event.getSource();
		if (formulaComposite != null
				&& object == formulaComposite.getListViewer()) {
			Formula formula = formulaComposite.getSelectedFormula();
			if (formula != null) {
				if (currentTreeNode == null) {
					currentTreeNode = conditionTreeViewer.getRootNode();
				}
				new FormulaTreeNode(currentTreeNode, formula, true);
				conditionTreeViewer.refresh();
				conditionTreeViewer.expandAll();

				okButton.setEnabled(formula != null
						&& conditionTreeViewer.getRootNode().isComplete());

			}
		} else if (object == formulaComposite2.getListViewer()) {
			Formula formula = formulaComposite2.getSelectedFormula();
			if (formula != null) {
				if (currentTreeNode == null) {
					currentTreeNode = conditionTreeViewer.getRootNode();
				}
				new FormulaTreeNode(currentTreeNode, formula, true, true);
				conditionTreeViewer.refresh();
				conditionTreeViewer.expandAll();

				okButton.setEnabled(formula != null
						&& conditionTreeViewer.getRootNode().isComplete());
			}
		} else if (object == conditionTreeViewer) {
			currentTreeNode = conditionTreeViewer.getSelectedTreeNode();
			formulaComposite2.setVisible(false);

			if (currentTreeNode == null) {
				createFirstFormulaComposite();
				formulaComposite.updateListView(null);
			} else {
				final Formula currentFormula = currentTreeNode.getValue();
				if (!(currentFormula instanceof NestedCondition)) {
					createFirstFormulaComposite();
					formulaComposite.updateListView(currentFormula);
					formulaComposite2.updateListView(currentFormula);

					if (currentFormula instanceof BinaryFormula) {
						formulaComposite2.setVisible(true);
					}
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.Dialog#createButton(org.eclipse.swt.widgets
	 * .Composite, int, java.lang.String, boolean)
	 */
	@Override
	protected Button createButton(Composite parent, int id, String label,
			boolean defaultButton) {
		Button button = super.createButton(parent, id, label, defaultButton);
		if (id == OK) {
			okButton = button;
			okButton.setEnabled(false);
			conditionTreeViewer.setOkButton(okButton);
		}
		return button;
	}

}
