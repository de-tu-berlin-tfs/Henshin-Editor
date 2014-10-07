/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.analysis.CriticalPair;
import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.impl.NodeTypes;
import de.tub.tfs.henshin.tgg.interpreter.util.NodeUtil;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
import de.tub.tfs.henshin.tggeditor.TggAggInfo;
import de.tub.tfs.henshin.tggeditor.util.GraphUtil;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.henshin.tggeditor.util.TripleGraphMigration;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class CheckForCritPairCommand extends Command {

	private Rule _firstRule;
	private Rule _secondRule;
	private TGG _trafo;
	private TGG layoutSystem;
	private TggAggInfo _aggInfo;

	public CheckForCritPairCommand(Rule firstRule, Rule secondRule, TggAggInfo aggInfo) {
		_aggInfo = aggInfo;
		_firstRule = firstRule;
		_secondRule = secondRule;
		_trafo = (TGG)_firstRule.getModule();
		layoutSystem = GraphicalNodeUtil.getLayoutSystem(_firstRule);
	}
	
	@Override
	public boolean canExecute() {
		return (_firstRule != null && _secondRule != null && _trafo != null);
	}
	
	
	@Override
	public void execute() {
		//_aggInfo.isCritical(_firstRule, _secondRule);
		
		Rule first = RuleUtil.copyRule(_firstRule);
		Rule second = RuleUtil.copyRule(_secondRule);
		_aggInfo = new TggAggInfo(_trafo);
		_aggInfo.save("D:", "a1");
		_aggInfo.extendDueToTGG(layoutSystem);
		_aggInfo.save("D:", "a2");
//		List<CriticalPair> critPairList = _aggInfo.getConflictOverlappings(first, second);
List<CriticalPair> critPairList = _aggInfo.getConflictOverlappings(_firstRule, _secondRule);
		
		if (critPairList != null && !critPairList.isEmpty()) {
			
			for (CriticalPair critPair : critPairList) {
				
			List<Mapping> mappingsOverToR1 = critPair.getMappingsOverlappingToRule1();
			List<Mapping> mappingsOverToR2 = critPair.getMappingsOverlappingToRule2();
			List<Mapping> mappingsR1ToR2 = critPair.getMappingsRule1ToRule2();
			Graph over = critPair.getOverlapping();
		
			CritPair newCrit = TggFactory.eINSTANCE.createCritPair();
				newCrit.setName("CP"+(critPairList.indexOf(critPair)+1));
				TripleGraphMigration migration = new TripleGraphMigration(over);
				TripleGraph tOver = migration.getTGraph();
				//TripleGraph tOver = TggFactory.eINSTANCE.createTripleGraph();
				//tOver.getNodes().addAll(over.getNodes());
				//tOver.getEdges().addAll(over.getEdges());
				
				newCrit.setOverlapping(tOver);
			newCrit.setRule1(critPair.getRule1());
			newCrit.setRule2(critPair.getRule2());
			mappingsWithRedirectedTargets(mappingsOverToR1,migration.getNodes2TNodes());
			mappingsWithRedirectedTargets(mappingsOverToR2,migration.getNodes2TNodes());
			newCrit.getMappingsOverToRule1().addAll(mappingsOverToR1);
			newCrit.getMappingsOverToRule2().addAll(mappingsOverToR2);
			newCrit.getMappingsRule1ToRule2().addAll(mappingsR1ToR2);
			//newCrit.getCriticalObjects().addAll(critPair.getCriticalObjects());
			layoutSystem.getCritPairs().add(newCrit);
			
			_trafo.getInstances().add(tOver);
			
				updateTGGGraphLayout(tOver);
				
				// update critical objects to new TNodes
				List<EObject> criticalobjects = new Vector<EObject>();
				for (EObject o: _aggInfo.getCriticalObjects().get(critPair)){
					criticalobjects.add(migration.getNodes2TNodes().get(o));
				}
				
				markCriticalObjects(tOver, criticalobjects);
				
				System.out.println("Adding CritPair "+newCrit.getName()+" for "+_firstRule.getName()+" with "+_secondRule.getName()+" finished.");
		}
		} else {
			//Remove created Objects from copying rules from transformation system and tgg

			SimpleDeleteEObjectCommand c1 = new SimpleDeleteEObjectCommand(first);
			SimpleDeleteEObjectCommand c2 = new SimpleDeleteEObjectCommand(second);
			c1.execute();
			c2.execute();
		}
		super.execute();
	}
	
	private void mappingsWithRedirectedTargets(
			List<Mapping> mappings, HashMap<Node, TNode> nodes2tNodes) {
		// TODO Auto-generated method stub
		for(Mapping m: mappings){
			m.setImage(nodes2tNodes.get(m.getImage()));
		}
		return;
	}

	private void markCriticalObjects(Graph graph, List<EObject> criticalObjects) {
		if (!criticalObjects.isEmpty()) {
			for (EObject eObj : criticalObjects) {
				if (eObj instanceof Node && graph.getNodes().contains((Node)eObj)) {
//					NodeLayout nodeLayout = GraphicalNodeUtil.getNodeLayout((Node)eObj);
//					nodeLayout.setCritical(true);
				}
			}
		}
	}
	
	private void updateTGGGraphLayout(TripleGraph tGraph) {
		//create NodeLayouts
		int  s=0, c=0, t = 0;
		for (Node no : tGraph.getNodes()) {
			TNode n = (TNode) no;
			
			if (n != null) {
				//if (p.getCriticalObjects().contains(n))
				//	nL.setCritical(true);
				if (NodeUtil.isSourceNode(n)) {
					s++;
					n.setX(GraphUtil.getMinXCoordinateForNodeGraphType(NodeTypes.getTripleComponent(n)) +10*s);
					n.setY(50*s);
				}
				else if (NodeUtil.isCorrespondenceNode(n)) {
					c++;
					n.setX(GraphUtil.getMinXCoordinateForNodeGraphType(NodeTypes.getTripleComponent(n)) +10*c);
					n.setY(50*c);
				}
				else {
					t++;
					n.setX(GraphUtil.getMinXCoordinateForNodeGraphType(NodeTypes.getTripleComponent(n)) +10+t);
					n.setY(50*t);;
				}
			}
		}
		return;
	}
}
