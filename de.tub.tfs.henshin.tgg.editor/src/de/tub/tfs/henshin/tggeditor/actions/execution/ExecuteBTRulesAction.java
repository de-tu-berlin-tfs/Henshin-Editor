package de.tub.tfs.henshin.tggeditor.actions.execution;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteBTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteFTRulesCommand;
import de.tub.tfs.henshin.tggeditor.editparts.tree.graphical.GraphTreeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.tree.rule.RuleTreeEditPart;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;


/**
 * The class ExecuteFTRuleAction executes a FT Rule. The class is shown in the context menu of the
 * Tree Editor and enabled when a graph is selected and FT Rules are available. The 
 * ExecuteFTRuleCommand is used.
 * @see ExecuteFTRuleCommand
 */
public class ExecuteBTRulesAction extends SelectionAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteBTRulesAction";

	/** The Constant DESC for the description. */
	static private final String DESC = "Execute BT Rules";

	/** The Constant TOOLTIP for the tooltip. */
	static private final String TOOLTIP = "Execute all the BT Rules on the Graph";

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
	public ExecuteBTRulesAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText(DESC);
		setDescription(DESC);
		setToolTipText(TOOLTIP);

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
			IndependentUnit ftFolder = (IndependentUnit) m.getUnit("BTRuleFolder");
			if (editpart.getModel().equals(ftFolder)){
				model = ftFolder;
				return true;
			}

			if (ftFolder.getSubUnits(true).contains(editpart.getModel())){
				if (editpart.getModel() instanceof IndependentUnit)
					model = (IndependentUnit) editpart.getModel();
				else {
					model = findContainer(ftFolder,editpart.getModel());

				}
				return true;
			} else {
				return false;
			}
				
		}
		return false;
	}


	private IndependentUnit findContainer(IndependentUnit ftFolder, Object obj) {
		for (Unit unit : ftFolder.getSubUnits()) {
			if (unit instanceof IndependentUnit) {
				IndependentUnit u = findContainer((IndependentUnit) unit, obj);
				if (u != null)
					return u;
			} else if (unit.equals(obj))
				return ftFolder;
		}

		return null;
		
		
	}

	
	private void getAllRules(List<Rule> units,IndependentUnit folder){
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
	protected void retrieveFTRules() {

		tRules.clear();
		
		getAllRules(tRules, model);
		
	}	
	
	/** Executed an {@link ExecuteBTRulesCommand}.
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (graph == null) {
			graph = DialogUtil.runGraphChoiceDialog(getWorkbenchPart().getSite()
					.getShell(), ((Module) EcoreUtil.getRootContainer(model))
					.getInstances());
		}
		
		retrieveFTRules();
		
//		ArrayList<Rule> ruleList = new ArrayList<Rule>();
//		for (Rule tr : tRules) {
//			ruleList.add(tr.getRule());
//		}
		
		System.out.println(Arrays.deepToString(tRules.toArray()).replaceAll(",", ",\n"));
		
		ExecuteBTRulesCommand fTRulesCommand = new ExecuteBTRulesCommand(graph, tRules);
		execute(fTRulesCommand);
	}
	
}
