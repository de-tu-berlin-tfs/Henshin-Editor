/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.rule;

import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.rule.AddMultiRuleCommand;

/**
 * The Class ExecuteRuleAction.
 */
public class AddMultiRuleAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.rule.AddMultiRuleAction"; //$NON-NLS-1$

	/** The Constant DESC. */
	static private final String DESC = "Add a Multi Rule";

	/** The Constant TOOLTIP. */
	static private final String TOOLTIP = "Add a Multi Rule";

	/** The rule. */
	protected Rule rule;

	/** The status. */
	private Status status = new Status(IStatus.ERROR, "My Plug-in ID", 0, "",
			null);

	/**
	 * Instantiates a new execute rule action.
	 * 
	 * @param part
	 *            the part
	 */
	public AddMultiRuleAction(IWorkbenchPart part) {
		super(part);

		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		rule = null;
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
		if ((selectedObject instanceof EditPart)) {
			Object model = ((EditPart) selectedObject).getModel();

			if (model instanceof Rule) {
				rule = (Rule) model;
			}
		}

		return rule != null ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (rule == null) {
			rule = getRule();
		}
		
		if (rule != null) {
			EcoreUtil.Copier copier = new EcoreUtil.Copier();
			Rule multiRule = (Rule) copier.copy(rule);
			copier.copyReferences();
			multiRule.setName("multirule_for_" + rule.getName());
			execute(new AddMultiRuleCommand(rule, multiRule, copier));
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	/*
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("play16.png");
	}
	*/

	/**
	 * Gets the rule.
	 * 
	 * @return the rule
	 */
	private Rule getRule() {
		return rule;
	}

}
