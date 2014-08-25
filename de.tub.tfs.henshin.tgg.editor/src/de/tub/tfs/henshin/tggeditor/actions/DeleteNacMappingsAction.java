/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions;

import java.util.List;

import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.commands.delete.rule.DeleteNacMappingCommand;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editparts.rule.RuleNodeEditPart;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;


/**
 * this action can be executed from the context menu of a nac node or a rule node
 * it is only enabled if the node has nac mappings
 * 
 */

public class DeleteNacMappingsAction extends SelectionAction {
	/**
	 * fully qualified class ID
	 */
	public static final String ID = "tggeditor.actions.DeleteNacMappingsAction";
	/**
	 * the selected Node 
	 */
	private TNode node;
	/**
	 * the Editpart of the selected Node
	 */
	private EditPart editpart;
	/**
	 * the nac mapping
	 * only set, if the selected node is a nac node 
	 */
	private Mapping mapping;
	
	/**
	 * the constructor
	 * @param part 
	 */
	
	public DeleteNacMappingsAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Delete NAC Mappings");
		setToolTipText("Delete all NAC mappings of this node");
	}
	/**
	 * calculate if the action can be executed
	 * @return either the action can be run or not 
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selecObject = selectedObjects.get(0);
				
		if (selecObject instanceof EditPart) {
			editpart = (EditPart) selecObject;
			/*
			 * decide if a rule node or a nac node is selected
			 */
			if (editpart instanceof RuleNodeEditPart) {
				node = (TNode) editpart.getModel();
				Mapping mapping = RuleUtil.getRHSNodeMapping(node);
				if(mapping == null) return false;
				Node origin = mapping.getOrigin();
				return NodeUtil.hasNodeNacMapping(origin);
			}
			
			if (selecObject instanceof TNodeObjectEditPart) {
				if (((TNodeObjectEditPart) selecObject).getCastedModel().eContainer().eContainer() instanceof NestedCondition) {
					node =(TNode) editpart.getModel();
					
					this.mapping = NodeUtil.getNodeNacMapping((NestedCondition)node.eContainer().eContainer(),node);
					
					return mapping != null;
				}
			}
		}
		return false;
	}
	
	/**
	 * execution of the action
	 * can be executed only if calculateEnabled returns true
	 */
	@Override
	public void run() {
		
		/*
		 * a rule node has possibly more than on nac mapping
		 * find out if it's a rule node or a nac node
		 */
		if (editpart instanceof RuleNodeEditPart) {
			// gets alls nac mappings from the rule node
			List<Mapping>  nacMappings = NodeUtil.getNodeNacMappings(node); 
			// all of them has to be deleted
			for (Mapping nacMapping : nacMappings) {
				DeleteNacMappingCommand command = new DeleteNacMappingCommand(nacMapping);
				command.execute();
				
			}
		}	
		/*
		 * a nac node only has one mapping to a rule node
		 * only this one has to be deleted
		 */
		else {
			
			
			DeleteNacMappingCommand command = new DeleteNacMappingCommand(mapping);
			command.execute();
		}
		super.run();
	}
	
	

}
