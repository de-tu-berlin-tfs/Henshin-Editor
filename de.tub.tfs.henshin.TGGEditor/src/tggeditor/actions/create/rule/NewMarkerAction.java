package tggeditor.actions.create.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import tgg.TGG;
import tgg.TRule;
import tggeditor.commands.create.rule.MarkAttributeCommand;
import tggeditor.commands.create.rule.MarkCommand;
import tggeditor.commands.create.rule.MarkEdgeCommand;
import tggeditor.editparts.rule.RuleAttributeEditPart;
import tggeditor.editparts.rule.RuleEdgeEditPart;
import tggeditor.editparts.rule.RuleNodeEditPart;
import tggeditor.util.NodeUtil;

public class NewMarkerAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.NewMarkerAction";
	private EObject model;
	
	public NewMarkerAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("De/Mark As New");
		setToolTipText("Mark or demark this Node as a new Node");
	}
	
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if (selecObject instanceof EditPart) {
			EditPart editpart = (EditPart) selecObject;

			if (editpart instanceof RuleAttributeEditPart) {
				model = (Attribute) editpart.getModel();

				TGG tgg = NodeUtil.getLayoutSystem(model);
				List<Rule> list = new ArrayList<Rule>();
				for (TRule tr : tgg.getTRules()) {
					list.add(tr.getRule());
				}
				if (list.contains(((Attribute)model).getNode().getGraph().getRule())) return false;
				
				return true;
			}
			if (editpart instanceof RuleNodeEditPart) {
				model = (Node) editpart.getModel();
				
				TGG tgg = NodeUtil.getLayoutSystem(model);
				List<Rule> list = new ArrayList<Rule>();
				for (TRule tr : tgg.getTRules()) {
					list.add(tr.getRule());
				}
				if (list.contains(((Node)model).getGraph().getRule())) return false;
				
				return true;
			}
			
			if (editpart instanceof RuleEdgeEditPart) {
				model = (Edge) editpart.getModel();

				TGG tgg = NodeUtil.getLayoutSystem(model);
				List<Rule> list = new ArrayList<Rule>();
				for (TRule tr : tgg.getTRules()) {
					list.add(tr.getRule());
				}
				if (list.contains(((Edge)model).getGraph().getRule())) return false;
				
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		
		if (model instanceof Attribute) {
			Command command = new MarkAttributeCommand((Attribute)model);
			super.execute(command);
		}
		if (model instanceof Node) {
			Command command = new MarkCommand((Node)model);
			super.execute(command);
		}
		if (model instanceof Edge) {
			Command command = new MarkEdgeCommand((Edge)model);
			super.execute(command);
		}
	}
}
