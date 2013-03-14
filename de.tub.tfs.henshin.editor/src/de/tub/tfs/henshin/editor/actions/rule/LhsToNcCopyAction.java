/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.rule;

import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.resource.ImageDescriptor;

import de.tub.tfs.henshin.editor.commands.rule.GraphCopyCommand;
import de.tub.tfs.henshin.editor.ui.rule.RulePage;
import de.tub.tfs.henshin.editor.util.IconUtil;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

/**
 * @author Johann
 * 
 */
public class LhsToNcCopyAction extends SelectionAction {

	public static final String ID = "henshineditor.actions.LhsToNcCopyAction";

	static private final String DESC = "Copy LHS to NC";

	static private final String TOOLTIP = "Copy LHS to NC";

	private final RulePage rulePage;

	/**
	 * @param part
	 */
	public LhsToNcCopyAction(MuvitorPageBookView part, RulePage rulePage) {
		super(part.getEditor());
		this.rulePage = rulePage;
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#getImageDescriptor()
	 */
	@Override
	public ImageDescriptor getImageDescriptor() {
		return IconUtil.getDescriptor("LN16.png");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return true;//rulePage.getAcGraph() != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		/*Not not = HenshinFactory.eINSTANCE.createNot();
		And and = HenshinFactory.eINSTANCE.createAnd();
		NestedCondition cond = HenshinFactory.eINSTANCE.createNestedCondition();
		and.setLeft(not);
		not.setChild(cond);
		and.setRight(value)*/
		
		final GraphCopyCommand command = new GraphCopyCommand(rulePage
				.getCastedModel().getLhs(), rulePage.getAcGraph(),
				((NestedCondition) rulePage.getAcGraph().eContainer()));
		execute(command);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		
		return isEnabled();
	}

}
