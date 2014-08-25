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
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.commands.ExecuteFTRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.ExecuteOpRulesCommand;
import de.tub.tfs.henshin.tggeditor.commands.RemoveMarkersCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.GraphEditPart;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;


/**
 * The class ExecuteFTRuleAction executes a FT Rule. The class is shown in the context menu of the
 * Tree Editor and enabled when a graph is selected and FT Rules are available. The 
 * ExecuteFTRuleCommand is used.
 * @see ExecuteFTRuleCommand
 */
public class RemoveMarkersAction extends SelectionAction {

	/** The fully qualified class ID. */
	public static final String ID = "henshineditor.actions.RemoveMarkerAction";
	
	/** The Constant DESC for the description. */
	protected String DESC = "Remove Markers";

	/** The Constant TOOLTIP for the tooltip. */
	protected String TOOLTIP = "Remove all markers within the Graph";


	/**
	 * The graph on which the rules should be executed.
	 */
	protected Graph graph;

	/**
	 * Instantiates a new execute rule action.
	 *
	 * @param part the part in which the action shall be registered
	 */
	public RemoveMarkersAction(IWorkbenchPart part) {
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
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
		if(selecObject instanceof GraphEditPart)
				graph = ((GraphEditPart) selecObject).getCastedModel();
		return graph!=null;
	}

	
	/** Executed an {@link ExecuteFTRulesCommand}.
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		RemoveMarkersCommand command = new RemoveMarkersCommand(graph);
		execute(command);
	}


	
}
