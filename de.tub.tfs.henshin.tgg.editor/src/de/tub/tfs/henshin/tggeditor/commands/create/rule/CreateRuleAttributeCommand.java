package de.tub.tfs.henshin.tggeditor.commands.create.rule;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.AttributeLayout;
import de.tub.tfs.henshin.tgg.NodeLayout;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateAttributeCommand;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateEdgeCommand;
import de.tub.tfs.henshin.tggeditor.util.AttributeUtil;
import de.tub.tfs.henshin.tggeditor.util.EdgeUtil;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
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

	
	Attribute lhsAttribute;
	Graph lhsGraph;
	Node lhsNode;
	Rule rule;

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
	
		rule = node.getGraph().getRule();

		Mapping nodeMapping = RuleUtil.getRHSNodeMapping(node);
		
		// attributeLayout = AttributeUtil.getAttributeLayout(attribute, layout);

//		if (attributeLayout == null) {
//			attributeLayout = TggFactory.eINSTANCE.createAttributeLayout();
//			NodeLayout nodeLayout=NodeUtil.getNodeLayout(node);
//			nodeLayout.getAttributeLayouts().add(attributeLayout);
//		}

//		if (attributeLayout != null) {
//			attributeLayout.setRhsattribute(attribute);
//		}

		
		// case: containing node is preserved, thus attribute is put into LHS and RHS as a preserved attribute
		if (nodeMapping != null) { 
			
			this.lhsAttribute = HenshinFactory.eINSTANCE.createAttribute();
			this.lhsAttribute.setNode(nodeMapping.getOrigin());
			this.lhsAttribute.getNode().getAttributes().add(lhsAttribute);
			this.lhsAttribute.setType(attribute.getType());
//			attributeLayout.setLhsattribute(lhsAttribute);
			attribute.setMarkerType(RuleUtil.NEW);
			attribute.setIsMarked(false);

			lhsGraph = rule.getLhs();
		}
		else { // attribute is put into RHS as a new attribute created by the rule
			attribute.setMarkerType(RuleUtil.NEW);
			attribute.setIsMarked(true);
		}
		
//		attributeLayout.setLhsTranslated(isLhsTranslated);
//		attributeLayout.setRhsTranslated(isRhsTranslated);

		super.execute();
	
	
	}

	/* (non-Javadoc)
	 * @see tggeditor.commands.create.CreateEdgeCommand#undo()
	 */
	@Override
	public void undo() {
		// FIXME: check, whether the marking has to be updated here as well - use mark command
		super.undo();
		if (!attribute.getIsMarked()) {
			lhsNode.getAttributes().remove(lhsAttribute);
		}
	}	
	
	
}
