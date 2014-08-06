package de.tub.tfs.henshin.tggeditor.actions.create.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.NodeEditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkEdgeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkUnspecifiedAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkUnspecifiedCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.rule.MarkUnspecifiedEdgeCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.AttributeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.EdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleAttributeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleEdgeEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


public class NewMarkerUnspecifiedAction extends SelectionAction {

	public static final String ID = "tggeditor.actions.create.NewMarkerUnspecifiedAction";
	private EObject model;
	
	public NewMarkerUnspecifiedAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("De/Mark with unspecified marking");
		setToolTipText("Mark or demark this node as a node with unspecified marking");
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

			if (editpart instanceof AttributeEditPart) {
				if (editpart instanceof RuleAttributeEditPart) {
					return false;
				}
				model = (Attribute) editpart.getModel();
				return true;
			}
			if (editpart instanceof NodeEditPart) {
				if (editpart instanceof RuleNodeEditPart) {
					return false;
				}
				model = (Node) editpart.getModel();
				return true;
			}

			if (editpart instanceof EdgeEditPart) {
				if (editpart instanceof RuleEdgeEditPart) {
					return false;
				}
				model = (Edge) editpart.getModel();
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		
		if (model instanceof Attribute) {
			Command command = new MarkUnspecifiedAttributeCommand((Attribute)model);
			super.execute(command);
		}
		if (model instanceof TNode) {
			Command command = new MarkUnspecifiedCommand((TNode)model);
			super.execute(command);
		}
		if (model instanceof Edge) {
			Command command = new MarkUnspecifiedEdgeCommand((Edge)model);
			super.execute(command);
		}
	}
}
