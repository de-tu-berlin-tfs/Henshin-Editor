package tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.Command;

import tgg.CritPair;
import tgg.EdgeLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tggeditor.util.EdgeUtil;
import tggeditor.util.GraphUtil;
import tggeditor.util.NodeTypes;
import tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.analysis.AggInfo;
import de.tub.tfs.henshin.analysis.CriticalPair;

public class CheckConflictCommand extends Command {

	private Rule _firstRule;
	private Rule _secondRule;
	private TransformationSystem _trafo;
	private TGG layoutSystem;

	public CheckConflictCommand(Rule firstRule, Rule secondRule) {
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
		AggInfo aggInfo = new AggInfo(_trafo);
		aggInfo.isCritical(_firstRule, _secondRule);
		aggInfo.save("./", "tgg2agg.ggx");
		List<CriticalPair> critPairList = aggInfo.getConflictOverlappings(_firstRule, _secondRule);
		CriticalPair critPair = critPairList.get(0);
		System.out.println(critPairList.size());
		System.out.println(critPair.getOverlapping().getName());
		
		List<Mapping> mappingsOverToR1 = critPair.getMappingsOverlappingToRule1();
		List<Mapping> mappingsOverToR2 = critPair.getMappingsOverlappingToRule2();
		List<Mapping> mappingsR1ToR2 = critPair.getMappingsRule1ToRule2();
		Graph over = critPair.getOverlapping();

//		layoutSystem.getCritPairs()aggInfo;
		
		CritPair newCrit = TGGFactory.eINSTANCE.createCritPair();
		newCrit.setOverlapping(over);
		newCrit.setRule1(critPair.getRule1());
		newCrit.setRule2(critPair.getRule2());
		newCrit.getMappingsOverToRule1().addAll(mappingsOverToR1);
		newCrit.getMappingsOverToRule2().addAll(mappingsOverToR2);
		newCrit.getMappingsRule1ToRule2().addAll(mappingsR1ToR2);
		
		layoutSystem.getCritPairs().add(newCrit);
		
		changeToTGGGraph(over);

		System.out.println("Checking "+_firstRule.getName()+" with "+_secondRule.getName());
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
//			nL.setNode(n);
//			layoutSystem.getNodelayouts().add(nL);			
		}
		
		//create EdgeLayout
		for (Edge e : graph.getEdges()) {
			System.out.println("Edge: "+e.getType().getName());
			EdgeLayout eL = EdgeUtil.getEdgeLayout(e);
			System.out.println("");
//			eL.setRhsedge(e);
//			layoutSystem.getEdgelayouts().add(eL);
		}
				
	}
}
