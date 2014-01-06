package de.tub.tfs.henshin.tggeditor.actions.execution;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class ExecuteCCRulesToolBarAction extends ExecuteCCRulesAction {
	

	protected String name_OP_RULE_FOLDER = "CCRuleFolder";

	/**
	 * Instantiates a new execute rule tool bar rule action.
	 *
	 * @param part the part
	 * @param page the graph page
	 */
	public ExecuteCCRulesToolBarAction(MuvitorPageBookView part, GraphicalPage page) {
		super(part.getEditor());
		graph=page.getCastedModel();
	}
	
	/* (non-Javadoc)
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		return true;
	}
	
	/* (non-Javadoc) 
	 * @see tggeditor.actions.execution.ExecuteFTRulesAction#run()
	 */
	@Override
	public void run() {
		model = null;
		tRules.clear();		
		EObject o =  EcoreUtil.getRootContainer( (EObject) graph);
		if (!(o instanceof Module))
			return;
		Module m = (Module) o;
		model = (IndependentUnit) m.getUnit(name_OP_RULE_FOLDER);
		retrieveOPRules();
		if (tRules.isEmpty()){
			notifyNoRules();
			return;
		}
		super.run();
	}

}
