package tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.Command;

import tgg.CritPair;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tggeditor.TggAggInfo;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeTypes;
import tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.analysis.CriticalPair;

public class CheckConflictCommand extends Command {

	private Rule _firstRule;
	private Rule _secondRule;
	private TransformationSystem _trafo;
	private TGG layoutSystem;
	private TggAggInfo _aggInfo;

	public CheckConflictCommand(Rule firstRule, Rule secondRule, TggAggInfo aggInfo) {
		_aggInfo = aggInfo;
		_firstRule = firstRule;
		_secondRule = secondRule;
		_trafo = _firstRule.getTransformationSystem();
		layoutSystem = NodeUtil.getLayoutSystem(_firstRule);
	}
	
	@Override
	public boolean canExecute() {
		return (_firstRule != null && _secondRule != null && _trafo != null);
	}
	
	
	@Override
	public void execute() {
		//_aggInfo.isCritical(_firstRule, _secondRule);
		List<CriticalPair> critPairList = _aggInfo.getConflictOverlappings(_firstRule, _secondRule);
		if (critPairList != null && !critPairList.isEmpty()) {
			CriticalPair critPair = critPairList.get(0);
			System.out.println(critPairList.size());
			System.out.println(critPair.getOverlapping().getName());
			
			List<Mapping> mappingsOverToR1 = critPair.getMappingsOverlappingToRule1();
			List<Mapping> mappingsOverToR2 = critPair.getMappingsOverlappingToRule2();
			List<Mapping> mappingsR1ToR2 = critPair.getMappingsRule1ToRule2();
			Graph over = critPair.getOverlapping();
		
			CritPair newCrit = TGGFactory.eINSTANCE.createCritPair();
			newCrit.setOverlapping(over);
			newCrit.setRule1(critPair.getRule1());
			newCrit.setRule2(critPair.getRule2());
			newCrit.getMappingsOverToRule1().addAll(mappingsOverToR1);
			newCrit.getMappingsOverToRule2().addAll(mappingsOverToR2);
			newCrit.getMappingsRule1ToRule2().addAll(mappingsR1ToR2);
			
//			layoutSystem.getCritPairs().add(newCrit);
			_trafo.getInstances().add(over);
			
			
			changeToTGGGraph(over);
		
			System.out.println("Checking "+_firstRule.getName()+" with "+_secondRule.getName()+" finished.");
		}
		super.execute();
	}
	
	private void changeToTGGGraph(Graph graph) {
		//create NodeLayouts
		int  s=0, c=0, t = 0;
		for (Node n : graph.getNodes()) {
			System.out.println("Node: "+n.getName()+" : "+n.getType().getName());
			NodeLayout nL = NodeUtil.getNodeLayout(n);
			if (nL != null) {
				if (NodeUtil.isSourceNode(layoutSystem, n.getType())) {
					s++;
					nL.setX(GraphUtil.getMinXCoordinateForNodeGraphType(NodeTypes.getNodeGraphType(n)) +10*s);
					nL.setY(50*s);
				}
				else if (NodeUtil.isCorrespNode(layoutSystem, n.getType())) {
					c++;
					nL.setX(GraphUtil.getMinXCoordinateForNodeGraphType(NodeTypes.getNodeGraphType(n)) +10*c);
					nL.setY(50*c);
				}
				else {
					t++;
					nL.setX(GraphUtil.getMinXCoordinateForNodeGraphType(NodeTypes.getNodeGraphType(n)) +10+t);
					nL.setY(50*t);;
				}
			}
		}
	}
}
