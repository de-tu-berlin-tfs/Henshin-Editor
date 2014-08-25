/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions.execution;

import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteFTRulesCommand;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;


/**
 * The class ExecuteFTRuleAction executes a FT Rule. The class is shown in the context menu of the
 * Tree Editor and enabled when a graph is selected and FT Rules are available. The 
 * ExecuteFTRuleCommand is used.
 * @see ExecuteFTRuleCommand
 */
public abstract class ExecuteOpRulesAction extends SelectionAction {
	
	protected String name_OP_RULE_FOLDER;


	/** The Constant DESC for the description. */
	protected String DESC = "Execute OP Rules";

	/** The Constant TOOLTIP for the tooltip. */
	protected String TOOLTIP = "Execute all the operational Rules on the Graph";

	/**
	 * The list of ft {@link Rule}s in the henshin file.
	 */
	protected List<Rule> tRules = new Vector<Rule>();

	/**
	 * The graph on which the rules should be executed.
	 */
	protected Graph graph;

	/**
	 * The selected Model. Just needed to get all the graphs in the transformationsystem
	 */
	protected IndependentUnit model;


	/**
	 * Instantiates a new execute rule action.
	 *
	 * @param part the part in which the action shall be registered
	 */
	public ExecuteOpRulesAction(IWorkbenchPart part) {
		super(part);
	}


	/** Checks if clicked on a graph and there are TRules in the TGG Layoutsystem
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		graph = null;
		model = null;
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
		tRules.clear();		
		if ((selecObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selecObject;
			EObject o =  EcoreUtil.getRootContainer( (EObject) editpart.getModel());
			if (!(o instanceof Module))
				return false;
			Module m = (Module) o;
			IndependentUnit opRuleFolder = (IndependentUnit) m.getUnit(name_OP_RULE_FOLDER);
			if (editpart.getModel().equals(opRuleFolder)){
				model = opRuleFolder;
				return true;
			}
			if(opRuleFolder==null) return false;

			if (opRuleFolder.getSubUnits(true).contains(editpart.getModel())){
				if (editpart.getModel() instanceof IndependentUnit)
					model = (IndependentUnit) editpart.getModel();
				else {
					model = findContainer(opRuleFolder,editpart.getModel());

				}
				return true;
			} else {
				return false;
			}
		}
		return false;
	}


	private IndependentUnit findContainer(IndependentUnit opRuleFolder, Object obj) {
		for (Unit unit : opRuleFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				IndependentUnit u = findContainer((IndependentUnit) unit, obj);
				if (u != null)
					return u;
			} else if (unit.equals(obj))
				return opRuleFolder;
		}
		return null;
	}

	
	protected void getAllRules(List<Rule> units,IndependentUnit folder){
		for (Unit unit : folder.getSubUnits()) {
			if (unit instanceof IndependentUnit){
				getAllRules(units, (IndependentUnit) unit);
			} else {
				units.add((Rule) unit);
			}
		}
	}
	

	/**
	 * 
	 */
	protected void retrieveOPRules() {
		tRules.clear();
		getAllRules(tRules, model);
	}	
	
	/** Executed an {@link ExecuteFTRulesCommand}.
	 * @see org.eclipse.jface.action.Action#run()
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
		
		if (graph == null) {
			graph = DialogUtil.runGraphChoiceDialog(getWorkbenchPart().getSite()
					.getShell(), ((TGG) EcoreUtil.getRootContainer(model))
					.getInstances());
		}
		
		
		System.out.println(Arrays.deepToString(tRules.toArray()).replaceAll(",", ",\n"));
		
		CompoundCommand command = setCommand();
		execute(command);
	}

	protected abstract CompoundCommand setCommand();
	public void notifyNoRules(){
		DialogUtil.showWarningDialog("There are no operational rules for this action available.","Please generate operational rules first.");
	};
	
}
