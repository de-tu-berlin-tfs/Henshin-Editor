package de.tub.tfs.henshin.tggeditor.actions.execution;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
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
public class ExecuteFTRulesAction extends SelectionAction {
	
	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.ExecuteFTRulesAction";

	/** The Constant DESC for the description. */
	static private final String DESC = "Execute FT Rules";

	/** The Constant TOOLTIP for the tooltip. */
	static private final String TOOLTIP = "Execute all the FT Rules on the Graph";

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
	private Object model;


	/**
	 * Instantiates a new execute rule action.
	 *
	 * @param part the part in which the action shall be registered
	 */
	public ExecuteFTRulesAction(IWorkbenchPart part) {
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
			model = editpart.getModel();
			if (editpart instanceof GraphTreeEditPart) {
				graph = (Graph) model;
				retrieveFTRules();
				
				return (tRules.size() > 0);
			}
			if (editpart instanceof RuleTreeEditPart) {
				Rule rule = (Rule) model;
				TGG tgg = NodeUtil.getLayoutSystem(rule);
				for (TRule tr : tgg.getTRules()) {
					if (tr.getRule() == rule) {
						return (tgg.getTRules().size() > 0);
					}
				}
				return false;
			}
		}
		return false;
	}


	/**
	 * 
	 */
	protected void retrieveFTRules() {
		Module module = null;
		if (graph.eContainer() instanceof Module) {
			module = (Module) graph.eContainer();
		}
		if (module != null) {
			EList<Unit> units = module.getUnits();
			for (Unit u : units) {
				if (u instanceof Rule
						&& ((Rule) u).getMarkerType() != null 
						&& ((Rule) u).getMarkerType().equals(
								RuleUtil.TGG_FT_RULE)) {
					tRules.add((Rule) u);
				}
			}
		}
	}	
	
	/** Executed an {@link ExecuteFTRulesCommand}.
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		if (graph == null) {
			DialogUtil.runGraphChoiceDialog(getWorkbenchPart().getSite()
					.getShell(), ((Module) ((Rule) model).eContainer())
					.getInstances());
		}
//		ArrayList<Rule> ruleList = new ArrayList<Rule>();
//		for (Rule tr : tRules) {
//			ruleList.add(tr.getRule());
//		}
		ExecuteFTRulesCommand fTRulesCommand = new ExecuteFTRulesCommand(graph, tRules);
		execute(fTRulesCommand);
	}
	
}
