package tggeditor;


import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;

import agg.attribute.AttrType;
import agg.attribute.AttrTypeMember;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.impl.javaExpr.JexHandler;
import agg.xt_basis.Type;

import tgg.NodeLayout;
import tgg.TGG;
import tggeditor.util.NodeUtil;

import de.tub.tfs.henshin.analysis.AggInfo;

public class TggAggInfo extends AggInfo {
	
	public TggAggInfo(TransformationSystem ts) {
		super(ts);
	}

	public void extendDueToTGG(TGG layoutSystem) {
		extendSrsNodeType(layoutSystem);
		extendFTRules(layoutSystem);
	}
	
	private void extendSrsNodeType(TGG layoutSystem) {
		AttrHandler handler = new JexHandler();
		
		for (EClass emfType : nodeTypeMap.keySet()) {
			if (NodeUtil.isSourceNode(layoutSystem, emfType)) {
				Type aggNodeType = nodeTypeMap.get(emfType);
				AttrType attr = aggNodeType.getAttrType();				
				AttrTypeMember member = attr.addMember(handler,"boolean" , "translated");
				if (member.getType() == null)
					System.out.println("DEBUG: " + member.getType());
				aggNodeType.getTypeGraphNodeObject().getAttribute().setExprValueAt("false", "translated");
				((agg.attribute.impl.ValueTuple)aggNodeType.getTypeGraphNodeObject().getAttribute()).showValue();
			}
		}
	}
	
	private void extendFTRules(TGG layoutSystem) {
		EList<Rule> rules = this.emfGrammar.getRules();
		for (Rule r : rules) {
			if (r.getName().startsWith("FT")) {
//				System.out.println("DEBUG: " + r.getName());
				EList<Node> rhsNodes = r.getRhs().getNodes();
				for (Node n : rhsNodes) {
					if (NodeUtil.isSourceNode(layoutSystem, n.getType())) {
						NodeLayout nl = NodeUtil.getNodeLayout(n);
						agg.xt_basis.Node aggNode = (agg.xt_basis.Node) this.henshinToAggConversionMap.get(n);
						if (nl != null && nl.getRhsTranslated() != null
								&& aggNode.getAttribute() != null && aggNode.getAttribute().getMemberAt("translated") != null) {
							aggNode.getAttribute().setExprValueAt(String.valueOf(nl.getRhsTranslated().booleanValue()), "translated");
							((agg.attribute.impl.ValueTuple)aggNode.getAttribute()).showValue();
							for (Mapping m : r.getMappings()) {
								if (m.getImage() == n) {
									Node nLhs = m.getOrigin();
									agg.xt_basis.Node aggNodeLhs = (agg.xt_basis.Node) this.henshinToAggConversionMap.get(nLhs);
									if (aggNodeLhs.getAttribute().getMemberAt("translated") != null) {
										aggNodeLhs.getAttribute().setExprValueAt(String.valueOf(nl.getLhsTranslated().booleanValue()), "translated");
										((agg.attribute.impl.ValueTuple)aggNodeLhs.getAttribute()).showValue();
									}
								}
							}
						}
					}
				}
			}
		}
	}

}
