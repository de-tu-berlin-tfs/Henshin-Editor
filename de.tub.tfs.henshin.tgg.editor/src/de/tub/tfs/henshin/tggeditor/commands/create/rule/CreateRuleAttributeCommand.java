package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateAttributeCommand;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;


/**
 * The class CreateRuleEdgeCommand creates an edge in a rule. Better: it creates a lhs edge, rhs
 * edge and the mapping between the edge. Also the edgelayouts attributes will be set.
 */
public class CreateRuleAttributeCommand extends CreateAttributeCommand {

//	/** The attribute layout */
//	protected AttributeLayout attributeLayout;

	/** The boolean value whether the attribute is translated in the LHS (Translation Rule) */
	protected Boolean isLhsTranslated = null;

	/** The boolean value whether the attribute is translated in the RHS (Translation Rule) */
	protected Boolean isRhsTranslated = null;

	
	private TAttribute lhsAttr;

	private Node lhsNode;
	
	/**
	 * Instantiates a new creates the attribute command.
	 *
	 * @param node the source node
	 * @param requestingObject the requesting object
	 */
	public CreateRuleAttributeCommand(Node node, String value) {
		super(node, value);
		
	}

	
	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateEdgeCommand#execute()
	 */
	@Override
	public void execute() {
		super.execute();
		if (node.eContainer().eContainer() instanceof Rule) {
			// case: attribute is in RHS
			lhsNode = RuleUtil.getLHSNode(node);
			if (lhsNode == null) {
				// case: attribute is created
				((TAttribute) attribute).setMarkerType(RuleUtil.NEW);
				lhsAttr = null;
			} else {
				// case: attribute is preserved
				lhsAttr = TggFactory.eINSTANCE.createTAttribute();
				lhsAttr.setValue(value);
				lhsAttr.setType(type);
				lhsAttr.setNode(node);
				lhsNode.getAttributes().add(lhsAttr);
			}
		} else { 
			// case: attribute is in NAC
			lhsAttr = null;
		}

	
	
	}

	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateEdgeCommand#undo()
	 */
	@Override
	public void undo() {
		super.undo();
		if (lhsAttr != null)
			lhsNode.getAttributes().remove(lhsAttr);
	}	
	
	
}
