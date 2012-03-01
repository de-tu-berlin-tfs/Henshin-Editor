/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.rule;

import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;

import de.tub.tfs.henshin.editor.commands.rule.GraphCopyCommand;
import de.tub.tfs.henshin.editor.ui.rule.RulePage;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * The Class LhsToRhsCopyAction.
 */
public class LhsToRhsCopyAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.LhsToRhsCopyAction";

	private static final String TEXT = "Copy LHS to RHS";

	/** The rule page. */
	private final RulePage rulePage;

	/**
	 * Instantiates a new lhs to rhs copy action.
	 * 
	 * @param part
	 *            the part
	 * @param rulePage
	 *            the rule page
	 */
	public LhsToRhsCopyAction(MuvitorPageBookView part, RulePage rulePage) {
		super(part.getEditor());
		this.rulePage = rulePage;

		setId(ID);
		setText(TEXT);
		setDescription(TEXT);
		setToolTipText(TEXT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand command = new CompoundCommand();

		command.add(new GraphCopyCommand(rulePage.getCastedModel().getLhs(),
				rulePage.getCastedModel().getRhs(), rulePage.getCastedModel()));

		execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("LR16.png");
	}

}
