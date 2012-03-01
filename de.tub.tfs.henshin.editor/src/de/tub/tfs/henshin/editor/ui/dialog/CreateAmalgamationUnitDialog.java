/**
 * 
 */
package de.tub.tfs.henshin.editor.ui.dialog;

import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;

/**
 * @author angel
 * 
 */
public final class CreateAmalgamationUnitDialog extends Dialog {

	/** The title. */
	private String title;

	/** The ok button. */
	private Button okButton;

	private TransformationSystem transSys;

	private Text unitNameText;
	private String unitName;

	private Button newRuleRB;
	private Label ruleNameLabel;
	private Text ruleNameText;
	private String ruleName;

	private Button definedRuleRB;
	private List definedRuleList;
	private ListViewer definedRuleListViewer;
	private Rule definedRule;

	/**
	 * Instantiates a new creates the edge dialog.
	 * 
	 * @param parentShell
	 *            the parent shell
	 * @param graph
	 *            the graph
	 * @param title
	 *            the title
	 */
	public CreateAmalgamationUnitDialog(Shell parentShell, String title,
			TransformationSystem transSys) {
		super(parentShell);
		this.title = title;
		this.transSys = transSys;
	}

	/**
	 * @return Unit name of an amalgamation unit to create.
	 */
	public String getUnitName() {
		return unitName;
	}

	/**
	 * @return Rule name of an kernel rule to create.
	 */
	public String getRuleName() {
		return ruleName;
	}

	/**
	 * @return A defined rule to set as kernel rule.
	 */
	public Rule getDefinedRule() {
		return definedRule;
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
		shell.layout();
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
			okButton.setEnabled(isComplete());
		}
		return button;
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
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Group unitNameGroup = new Group(composite, SWT.NONE);
		unitNameGroup.setLayout(new GridLayout(2, false));
		unitNameGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false));
		unitNameGroup.setText("Unit Name");

		unitNameText = new Text(unitNameGroup, SWT.BORDER);
		unitName = ModelUtil.getNewChildDistinctName(transSys,
				HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				"amalgamationUnit");
		unitNameText.setText(unitName);
		unitNameText
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		final Group kernelRuleGroup = new Group(composite, SWT.NONE);
		kernelRuleGroup.setLayout(new GridLayout(2, false));
		kernelRuleGroup.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		kernelRuleGroup.setText("Kernel Rule");

		newRuleRB = new Button(kernelRuleGroup, SWT.RADIO);
		newRuleRB.setText("Create a new rule as kernel rule.");
		newRuleRB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false,
				2, 1));
		newRuleRB.setSelection(true);

		ruleNameLabel = new Label(kernelRuleGroup, SWT.NONE);
		ruleNameLabel.setText("Rule name : ");
		ruleNameLabel.setVisible(true);

		ruleNameText = new Text(kernelRuleGroup, SWT.BORDER);
		ruleName = ModelUtil.getNewChildDistinctName(transSys,
				HenshinPackage.TRANSFORMATION_SYSTEM__RULES, "rule");
		ruleNameText.setText(ruleName);
		ruleNameText
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		ruleNameText.setVisible(true);

		definedRuleRB = new Button(kernelRuleGroup, SWT.RADIO);
		definedRuleRB.setText("Add defined rule as kernel rule.");
		definedRuleRB.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				false, 2, 1));
		definedRuleRB.setSelection(false);

		definedRuleList = new List(kernelRuleGroup, SWT.BORDER);
		definedRuleList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 2, 1));
		definedRuleList.setVisible(false);
		definedRuleListViewer = new ListViewer(definedRuleList);
		definedRuleListViewer.setLabelProvider(new LabelProvider() {
			@Override
			public String getText(Object element) {
				if (element instanceof Rule) {
					return ((Rule) element).getName();
				}
				return super.getText(element);
			}

			@Override
			public Image getImage(Object element) {
				return IconUtil.getDescriptor("Rule16.png").createImage();
			}
		});
		for (Rule rule : transSys.getRules()) {
			definedRuleListViewer.add(rule);
		}

		addListener();

		unitNameText.setFocus();
		unitNameText.selectAll();

		return composite;
	}

	/**
	 * Adds the listener.
	 */
	private void addListener() {
		unitNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				unitName = unitNameText.getText();
				okButton.setEnabled(isComplete());
			}
		});

		newRuleRB.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ruleNameLabel.setVisible(newRuleRB.getSelection());
				ruleNameText.setVisible(newRuleRB.getSelection());
				if (ruleNameText.isVisible()) {
					ruleNameText.selectAll();
					ruleNameText.setFocus();
				}
				okButton.setEnabled(isComplete());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		ruleNameText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				ruleName = ruleNameText.getText();
				okButton.setEnabled(isComplete());
			}
		});

		definedRuleRB.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				definedRuleList.setVisible(definedRuleRB.getSelection());
				if (definedRuleList.isVisible()
						&& !transSys.getRules().isEmpty()) {
					definedRuleList.setSelection(0);
					definedRule = transSys.getRules().get(0);
				}
				okButton.setEnabled(isComplete());
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}
		});

		definedRuleListViewer
				.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						Object selection = definedRuleListViewer.getSelection();
						if (selection != null
								&& selection instanceof StructuredSelection) {
							selection = ((StructuredSelection) selection)
									.getFirstElement();
							if (selection instanceof Rule) {
								definedRule = (Rule) selection;
								okButton.setEnabled(isComplete());
							}
						}
					}
				});
	}

	/**
	 * 
	 */
	private boolean isComplete() {
		boolean complete = true;
		if (newRuleRB.getSelection()) {
			complete &= ruleName != null && ruleName.length() > 0;
			definedRule = null;
		} else if (definedRuleRB.getSelection()) {
			complete &= definedRule != null;
			ruleName = null;
		}
		return complete && unitName != null && unitName.length() > 0;
	}
}
