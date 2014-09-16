/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.transformation_unit;

import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.ui.graph.GraphPage;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;

/**
 * The Class ExecuteTransformationUnitToolBarAction.
 */
public class ExecuteTransformationUnitToolBarAction extends
		ExecuteTransformationUnitAction {

	/** The index. */
	private int index = -1;

	/**
	 * Instantiates a new execute transformation unit tool bar action.
	 * 
	 * @param part
	 *            the part
	 * @param tUnitPage
	 *            the t unit page
	 */
	public ExecuteTransformationUnitToolBarAction(IWorkbenchPart part,
			TransUnitPage tUnitPage) {
		super(part);
		tUnit = tUnitPage.getCastedModel();
		index = 0;
	}

	/**
	 * Instantiates a new execute transformation unit tool bar action.
	 * 
	 * @param part
	 *            the part
	 * @param page
	 *            the page
	 */
	public ExecuteTransformationUnitToolBarAction(IWorkbenchPart part,
			GraphPage page) {
		super(part);
		graph = page.getCastedModel();
		index = 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.CreateNACAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.actions.rule.ExecuteRuleAction#run()
	 */
	@Override
	public void run() {
		switch (index) {
		case 0:
			graph = null;
			break;
		case 1:
			tUnit = null;
			break;
		}
		
		super.run();
	}

}
